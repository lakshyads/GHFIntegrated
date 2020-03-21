package com.questpirates.greathomesfurniture.bottomNavFrags;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.questpirates.greathomesfurniture.R;
import com.questpirates.greathomesfurniture.ScanVision.IntelliVisionActivity;
import com.questpirates.greathomesfurniture.ScanVision.ScanQRActivity;
import com.questpirates.greathomesfurniture.arcore.ArCoreHome;

import java.util.Objects;

public class ScanFragment extends Fragment {

    private View view;
    private FloatingActionButton intelliV, agumV, qrV;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        view = inflater.inflate(R.layout.fragment_scan, null);



        intelliV = view.findViewById(R.id.intellifab);
        agumV = view.findViewById(R.id.arfab);
        qrV = view.findViewById(R.id.qrcodefab);

        intelliV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerIntelliVision();
            }
        });

        agumV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerAgumVision();
            }
        });

        qrV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerQRVision();
            }
        });


        return view;
    }

    private void triggerQRVision() {
        Toast.makeText(getContext(), "Clicked on QR Vision", Toast.LENGTH_SHORT).show();
        //Call QR Code in Scan Vision Package
        Intent i = new Intent(getContext(), ScanQRActivity.class);
        startActivity(i);
    }

    private void triggerAgumVision() {
        Toast.makeText(getContext(), "Clicked on Agumented Vision", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getContext(), ArCoreHome.class);
        startActivity(i);
    }

    private void triggerIntelliVision() {
        Toast.makeText(getContext(), "Clicked on Intelligent Vision", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getContext(), IntelliVisionActivity.class);
        startActivity(i);
    }
//
//    @Override
//
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == MY_CAMERA_REQUEST_CODE) {
//
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
//
//            } else {
//
//                // getContext().finish();
////                finishAffinity();
//
//                Toast.makeText(getContext(), "Camera Permission needed for this to work. Please Grant", Toast.LENGTH_LONG).show();
//
//            }
//
//        }
//    }//end onRequestPermissionsResult
}
