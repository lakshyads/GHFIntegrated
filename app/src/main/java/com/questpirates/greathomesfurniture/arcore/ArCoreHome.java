package com.questpirates.greathomesfurniture.arcore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.questpirates.greathomesfurniture.Adapters.ARRecyclerAdapter;
import com.questpirates.greathomesfurniture.Adapters.HomeRecyclerAdapter;
import com.questpirates.greathomesfurniture.R;
import com.questpirates.greathomesfurniture.dataBlock.SomeData;

import java.util.Map;

public class ArCoreHome extends AppCompatActivity {

    TextView tvall, tvchairs, tvdesks, tvtables, tvcouch, tvbeds, tvothers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_core_home);

        tvall = findViewById(R.id.txtall);
        tvall.setTextColor(Color.parseColor("#000000"));
        tvchairs = findViewById(R.id.txtchairs);
        tvdesks = findViewById(R.id.txtdesks);
        tvtables = findViewById(R.id.txttables);
        tvcouch = findViewById(R.id.txtcouch);
        tvbeds = findViewById(R.id.txtbeds);
        tvothers = findViewById(R.id.txtoth);

        initTextView();

        //Initially Load all Data
        popHomeRecycle(new SomeData().Allproducts);



    }


    private void initTextView() {



        tvall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"ALL",Toast.LENGTH_LONG).show();
                setAllTxtViewGray();
                popHomeRecycle(new SomeData().Allproducts);
                tvall.setTextColor(Color.parseColor("#000000"));
            }
        });
        tvchairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllTxtViewGray();
                popHomeRecycle(new SomeData().Chairproducts);
                tvchairs.setTextColor(Color.parseColor("#000000"));
            }
        });
        tvcouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllTxtViewGray();
                popHomeRecycle(new SomeData().Couchproducts);
                tvcouch.setTextColor(Color.parseColor("#000000"));
            }
        });
        tvothers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllTxtViewGray();
                popHomeRecycle(new SomeData().Othproducts);
                tvothers.setTextColor(Color.parseColor("#000000"));
            }
        });
        tvbeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllTxtViewGray();
                popHomeRecycle(new SomeData().Bedproducts);
                tvbeds.setTextColor(Color.parseColor("#000000"));
            }
        });
        tvtables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllTxtViewGray();
                popHomeRecycle(new SomeData().Tableproducts);
                tvtables.setTextColor(Color.parseColor("#000000"));
            }
        });
        tvdesks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllTxtViewGray();
                popHomeRecycle(new SomeData().Deskproducts);
                tvdesks.setTextColor(Color.parseColor("#000000"));
            }
        });
    }

    private void setAllTxtViewGray() {
        tvall.setTextColor(Color.parseColor("#D5D1D1"));
        tvchairs.setTextColor(Color.parseColor("#D5D1D1"));
        tvdesks.setTextColor(Color.parseColor("#D5D1D1"));
        tvtables.setTextColor(Color.parseColor("#D5D1D1"));
        tvcouch.setTextColor(Color.parseColor("#D5D1D1"));
        tvbeds.setTextColor(Color.parseColor("#D5D1D1"));
        tvothers.setTextColor(Color.parseColor("#D5D1D1"));
    }

    public void popHomeRecycle(Map<String, Integer> data) {


        RecyclerView recyclerView = findViewById(R.id.homelist);
        ARRecyclerAdapter adapter = new ARRecyclerAdapter(data);
        adapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(adapter);

    }
}
