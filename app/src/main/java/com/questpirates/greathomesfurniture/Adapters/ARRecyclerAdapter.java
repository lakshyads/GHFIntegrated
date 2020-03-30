package com.questpirates.greathomesfurniture.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.questpirates.greathomesfurniture.ARMainActivity;
import com.questpirates.greathomesfurniture.ItemFullActivity;
import com.questpirates.greathomesfurniture.R;

import java.util.HashMap;
import java.util.Map;


public class ARRecyclerAdapter extends RecyclerView.Adapter<ARRecyclerAdapter.ViewHolder> {
    private Map<String, Integer> mapdata;
    private Map<String, Integer> mapdata_copy;
    int mapdatasize;

    // RecyclerView recyclerView;
    public ARRecyclerAdapter(Map<String, Integer> mapdata) {
        this.mapdata = mapdata;
        this.mapdata_copy = mapdata;
        this.mapdatasize = this.mapdata.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.recycler_homerow, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            String key = "";
            Integer val = 0;
            Log.d("BindView", "map size " + mapdata.size());
            for (Map.Entry<String, Integer> entry : mapdata.entrySet()) {
                key = entry.getKey();
                val = entry.getValue();
                break;
            }

            if (key.equals("") || val == 0) {
                return;
            }

            mapdata.remove(key);

            String z[] = key.split("@");
            for (String a : z) {
                Log.d("Key", a);
            }
            final String prodName = z[0];
            final String prodPrice = z[1];
            final String valObj = z[2];
            final String valSFB = z[3];
            final int drawable = val;

            holder.textViewHead.setImageResource(val);
            holder.prodHead.setText(prodName);
            holder.prodPrice.setText("₹. " + prodPrice);

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Toast.makeText(view.getContext(), "click on item: " + prodName, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(view.getContext(), ARMainActivity.class);
                    //i.putExtra("image", valSFB);
                    i.setData(Uri.parse(valSFB));
                    Log.d("BindView", "SFB BEFORE " + valSFB);
                    Toast.makeText(view.getContext(), "SFB selected: " + valSFB, Toast.LENGTH_LONG);
                    view.getContext().startActivity(i);
                }
            });
        } catch (Exception e) {
            Log.d("BindView", "Error " + e);
        }

    }

    //Make sure the postions are same
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mapdatasize;
        //listdata.size();
    }

//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView textViewHead;
        public TextView prodHead;
        public TextView prodPrice;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewHead = (ImageView) itemView.findViewById(R.id.Head);
            this.prodHead = (TextView) itemView.findViewById(R.id.pname);
            this.prodPrice = (TextView) itemView.findViewById(R.id.price);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear);
        }
    }
}