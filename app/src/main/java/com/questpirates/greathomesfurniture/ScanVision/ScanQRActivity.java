package com.questpirates.greathomesfurniture.ScanVision;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.questpirates.greathomesfurniture.ItemFullActivity;
import com.questpirates.greathomesfurniture.R;
import com.questpirates.greathomesfurniture.SFBPojo;

import java.util.HashMap;

public class ScanQRActivity extends AppCompatActivity {
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    SurfaceView surfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

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
        actionTV.setText("QR VISION.");




        surfaceView = findViewById(R.id.qr_camera_view);

        barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.QR_CODE)
                        .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(15.0f)
                .setRequestedPreviewSize(1600, 1024)
                .setAutoFocusEnabled(true)
                .build();

        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startpassCamera();
            }
        });

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                startpassCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                // final ArrayList<Barcode> bc = detections.getDetectedItems();

                Log.e("BARCODE SIZE", "" + barcodes.size());
                //Toast.makeText(getApplicationContext(),"BARCODE SIZE : "+ barcodes.size(),Toast.LENGTH_LONG).show();

                //String str = "nulll";
                if (barcodes.size() != 0) {
                    playBeepforPass();

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            String s = barcodes.valueAt(0).displayValue;
                            Log.e("BARCODE VALUE", "" + s);
                            if(s.equalsIgnoreCase("GHF0123CHAIR")){
                                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("prodName", "Premium Wood Chair");
                                hashMap.put("prodPrice", "20000");
                                hashMap.put("prodImg", R.drawable.brown_chair_1);
                                hashMap.put("prodSFB", "chair.sfb");
                                SFBPojo.setSFBFile("chair.sfb");
                                Intent intent = new Intent(getApplicationContext(), ItemFullActivity.class);
                                intent.putExtra("map", hashMap);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(), "Item not available", Toast.LENGTH_LONG).show();
                            }

                            cameraSource.stop();
                        }
                    });
                }

            }


        });


    }

    public void startpassCamera(){
        try {
            cameraSource.start(surfaceView.getHolder());
        } catch (SecurityException ie) {
            Log.e("CAMERA PERMISSION", ie.getMessage());
        }catch (Exception e){
            Log.e("CAMERA SOURCE IO", e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraSource.release();
        barcodeDetector.release();
    }

    public void playBeepforPass() {

        try {
            MediaPlayer m = new MediaPlayer();
            if (m.isPlaying()) {
                m.stop();
                m.release();
                m = new MediaPlayer();
            }

            AssetFileDescriptor descriptor = getAssets().openFd("sounds/beep.mp3");
            m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            m.prepare();
            m.setVolume(0.3f, 0.3f);
            m.setLooping(false);
            m.start();

            Vibrator v = (Vibrator) ScanQRActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(100);

            m.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
