package com.questpirates.greathomesfurniture.bottomNavFrags;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.questpirates.greathomesfurniture.Adapters.HomeRecyclerAdapter;
import com.questpirates.greathomesfurniture.Adapters.ImageLabelRecyclerAdapter;
import com.questpirates.greathomesfurniture.MainActivity;
import com.questpirates.greathomesfurniture.R;
import com.questpirates.greathomesfurniture.dataBlock.SomeData;
import com.questpirates.greathomesfurniture.myServices.SocketService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    SearchView searchView;
    TextView tvall, tvchairs, tvdesks, tvtables, tvcouch, tvbeds, tvothers;

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
        View view = inflater.inflate(R.layout.fragment_home, null);



        tvall = view.findViewById(R.id.txtall);
        tvall.setTextColor(Color.parseColor("#000000"));
        tvchairs = view.findViewById(R.id.txtchairs);
        tvdesks = view.findViewById(R.id.txtdesks);
        tvtables = view.findViewById(R.id.txttables);
        tvcouch = view.findViewById(R.id.txtcouch);
        tvbeds = view.findViewById(R.id.txtbeds);
        tvothers = view.findViewById(R.id.txtoth);

        initTextView(view);

        //Initially Load all Data
        popHomeRecycle(new SomeData().Allproducts, view);

        MainActivity mm = (MainActivity)getActivity();
        String val = mm.getItemValue();
        if(!val.equalsIgnoreCase("")){
            changeTabs(view,val);
        }

        return view;
    }

    private void initTextView(final View view) {



        tvall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"ALL",Toast.LENGTH_LONG).show();
                setAllTxtViewGray();
                popHomeRecycle(new SomeData().Allproducts, view);
                tvall.setTextColor(Color.parseColor("#000000"));
            }
        });
        tvchairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            changeTabs(view,"chairs");
            }
        });
        tvcouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllTxtViewGray();
                popHomeRecycle(new SomeData().Couchproducts, view);
                tvcouch.setTextColor(Color.parseColor("#000000"));
            }
        });
        tvothers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllTxtViewGray();
                popHomeRecycle(new SomeData().Othproducts, view);
                tvothers.setTextColor(Color.parseColor("#000000"));
            }
        });
        tvbeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllTxtViewGray();
                popHomeRecycle(new SomeData().Bedproducts, view);
                tvbeds.setTextColor(Color.parseColor("#000000"));
            }
        });
        tvtables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              changeTabs(view,"tables");
            }
        });
        tvdesks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTabs(view,"desks");
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

    public void changeTabs(View view, String tabName){
        switch (tabName.toLowerCase()){
            case "chairs":
                setAllTxtViewGray();
                popHomeRecycle(new SomeData().Chairproducts, view);
                tvchairs.setTextColor(Color.parseColor("#000000"));
                break;
            case "desks" :
                setAllTxtViewGray();
                popHomeRecycle(new SomeData().Deskproducts, view);
                tvdesks.setTextColor(Color.parseColor("#000000"));
                break;
            case "tables" :
                setAllTxtViewGray();
                popHomeRecycle(new SomeData().Tableproducts, view);
                tvtables.setTextColor(Color.parseColor("#000000"));
        }
    }

    public void popHomeRecycle(Map<String, Integer> data, View v) {


        RecyclerView recyclerView = v.findViewById(R.id.homelist);
        HomeRecyclerAdapter adapter = new HomeRecyclerAdapter(data);
        adapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

    }

}
