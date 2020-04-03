package com.questpirates.greathomesfurniture;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AlertDialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("ALERT", "onCreate: Alert Dialog Activitu");
        //displayAlert();

        String s = "Thanks for the interest\n An email has been triggered and notified to Store. An agent will contact you shortly.\n" +
                "If you want more support click on OK to chat with the agent";
        showSuccess(s, getApplicationContext());
    }

    private void displayAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setInverseBackgroundForced(true);
        builder.setMessage("You Message here").setCancelable(
                false).setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // code here
                        AlertDialogActivity.this.finish();
                        dialog.cancel();
                    }
                }).setNegativeButton("Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        AlertDialogActivity.this.finish();
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alert.show();
    }


    public void showSuccess(final String msg, Context c) {
//        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
//        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.my_dialog, null, false);
        //Now we need an AlertDialog.Builder object
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        builder.setCancelable(false);


        //finally creating the alert dialog and displaying it
        final android.app.AlertDialog alertDialog = builder.create();
        //alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
        Button bOK = dialogView.findViewById(R.id.buttonOk);
        Button bD = dialogView.findViewById(R.id.buttonDismiss);
        TextView t = dialogView.findViewById(R.id.msg);
        t.setText(msg);
        bD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogActivity.this.finish();
                alertDialog.dismiss();
            }
        });
        bOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CHAT = "CHAT";
                AlertDialogActivity.this.finish();
                // if ((!cn.getShortClassName().equalsIgnoreCase(".MainActivity"))) {
                Intent act = new Intent(getApplicationContext(), MainActivity.class);
                act.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                act.putExtra("fragment", "chatscreen");
                startActivity(act);
            }
        });


    }


}