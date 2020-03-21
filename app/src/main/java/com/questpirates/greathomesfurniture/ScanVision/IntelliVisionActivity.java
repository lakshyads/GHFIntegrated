package com.questpirates.greathomesfurniture.ScanVision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.questpirates.greathomesfurniture.Adapters.ImageLabelRecyclerAdapter;
import com.questpirates.greathomesfurniture.R;

import java.util.ArrayList;
import java.util.List;

public class IntelliVisionActivity extends AppCompatActivity {

    public static Bitmap bmp = null;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private FirebaseVisionImage firebaseVisionImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intelli_vision);

        //******************* INIT ACTION BAR **********************//

        // Inflate your custom layout
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.custom_action_bar, null);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;

        TextView actionTV = actionBarLayout.findViewById(R.id.header);

        // Set up your ActionBar
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayShowHomeEnabled(false);
        //actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(actionBarLayout, layoutParams);
        actionTV.setText("INTELLI VISION.");

        //********* START CAMERA TO CAPTURE FOR IMAGE LABEL *****************//

        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            Bitmap bb = getScaledDownBitmap(imageBitmap, height, false);

            //imageView.setImageBitmap(bb);
            Toast.makeText(getApplicationContext(), "image bitmap", Toast.LENGTH_LONG).show();

            labelBitmap(bb);

//            Intent i = new Intent(IntelliVisionActivity.this, IntelliVisionImageLabeller.class);
//            i.putExtra("image", bb);
//            finish();
//            startActivity(i);
        }
    }


    public static Bitmap getScaledDownBitmap(Bitmap bitmap, int threshold, boolean isNecessaryToKeepOrig) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = width;
        int newHeight = height;

        if (width > height && width > threshold) {
            newWidth = threshold;
            newHeight = (int) (height * (float) newWidth / width);
        }

        if (width > height && width <= threshold) {
            //the bitmap is already smaller than our required dimension, no need to resize it
            return bitmap;
        }

        if (width < height && height > threshold) {
            newHeight = threshold;
            newWidth = (int) (width * (float) newHeight / height);
        }

        if (width < height && height <= threshold) {
            //the bitmap is already smaller than our required dimension, no need to resize it
            return bitmap;
        }

        if (width == height && width > threshold) {
            newWidth = threshold;
            newHeight = newWidth;
        }

        if (width == height && width <= threshold) {
            //the bitmap is already smaller than our required dimension, no need to resize it
            return bitmap;
        }

        return getResizedBitmap(bitmap, newWidth, newHeight, isNecessaryToKeepOrig);
    }

    private static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight, boolean isNecessaryToKeepOrig) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        if (!isNecessaryToKeepOrig) {
            bm.recycle();
        }
        return resizedBitmap;
    }

    public void labelBitmap(Bitmap bmp) {

        if (bmp == null) {
            Toast.makeText(getApplicationContext(), "no Bitmap", Toast.LENGTH_LONG).show();
            return;
        }

        ImageView iv = findViewById(R.id.image);
        iv.setImageBitmap(bmp);
        //iv.setImageResource(R.drawable.amazonrivetfurniture);

        //bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.amazonrivetfurniture);

        firebaseVisionImage = FirebaseVisionImage.fromBitmap(bmp);

        FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance()
                .getOnDeviceImageLabeler();

        labeler.processImage(firebaseVisionImage)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                        Log.d("TAG", "Success");
                        //Map<String, String> data = new HashMap<>();
                        List<String> list = new ArrayList<>();
                        for (FirebaseVisionImageLabel firebaseVision : labels) {
                            // Logging through the list of labels returned from the API and Log them
                            Log.d("TAG", "Item Name ${firebaseVision.Text}" + firebaseVision.getText());
                            Log.d("TAG", "Item Name ${firebaseVision.entity}" + firebaseVision.getEntityId());
                            Log.d("TAG", "Confidence ${firebaseVision.confidence}" + firebaseVision.getConfidence());

                            float per = firebaseVision.getConfidence() * 100;

                            //data.put(firebaseVision.getText(), per + "%");
                            list.add(firebaseVision.getText() + "@" + per);
                        }
                        //System.out.println(res);
                        //res = "";
//                        for (Map.Entry<String, String> entry : data.entrySet()) {
//                            res = res + entry.getKey() + " : " + entry.getValue() + "\n";
//                        }
                        // tv.setText(res);

                        popRecycle(list);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "Failure " + e);
                    }
                });

    }

    public void popRecycle(List<String> data) {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ImageLabelRecyclerAdapter adapter = new ImageLabelRecyclerAdapter(data);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);

    }
}
