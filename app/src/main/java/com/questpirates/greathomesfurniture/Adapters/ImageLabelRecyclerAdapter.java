package com.questpirates.greathomesfurniture.Adapters;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.questpirates.greathomesfurniture.MainActivity;
import com.questpirates.greathomesfurniture.R;
import com.questpirates.greathomesfurniture.myServices.SocketService;

import java.text.DecimalFormat;
import java.util.List;


public class ImageLabelRecyclerAdapter extends RecyclerView.Adapter<ImageLabelRecyclerAdapter.ViewHolder> {
    private List<String> listdata;

    // RecyclerView recyclerView;
    public ImageLabelRecyclerAdapter(List<String> listdata) {
        this.listdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.recycler_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        String[] data = listdata.get(position).split("@");

        holder.textViewHead.setText(data[0]);

        float per = Float.parseFloat(data[1]);
        DecimalFormat df = new DecimalFormat("#.00");
        per = Float.parseFloat(df.format(per));


        if (per >= 0 && per <= 20) {

            holder.textView.setTextColor(Color.parseColor("#eb4034"));

        } else if (per >= 21 && per <= 40) {

            holder.textView.setTextColor(Color.parseColor("#eb7a34"));

        } else if (per >= 41 && per <= 60) {

            holder.textView.setTextColor(Color.parseColor("#ebd934"));

        } else if (per >= 61 && per <= 80) {

            holder.textView.setTextColor(Color.parseColor("#dfeb34"));

        } else if (per >= 81 && per <= 100) {

            holder.textView.setTextColor(Color.parseColor("#59eb34"));

        } else {

            holder.textView.setTextColor(Color.parseColor("#000000"));

        }


        holder.textView.setText(per + " %");


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityManager am = (ActivityManager) view.getContext().getSystemService(Context.ACTIVITY_SERVICE);
                ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
                Log.d("ActivityName", cn.getClassName());
                Fragment fr = MainActivity.fragmentManager.findFragmentById(R.id.fragment_container);
                String fragmentName = fr.getClass().getSimpleName();
                Log.d("FragmentName", fragmentName);

                Toast.makeText(view.getContext(), "click on item: " + listdata.get(position), Toast.LENGTH_LONG).show();

                String[] data = listdata.get(position).split("@");
                String productName = (data[0]).toLowerCase();

                if (productName.equalsIgnoreCase("chairs") ||
                        productName.equalsIgnoreCase("chair")) {
                    if ((!cn.getShortClassName().equalsIgnoreCase(".MainActivity"))) {
                        Intent act = new Intent(view.getContext(), MainActivity.class);
                        act.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        act.putExtra("fragment", "homechairs");
                        view.getContext().startActivity(act);
                    } else {
                        Intent i = new Intent(SocketService.BROADCAST_MAINCHAIRS);
                        i.putExtra("fragment", "homechairs");
                        view.getContext().sendBroadcast(i);
                    }

                } else if (productName.equalsIgnoreCase("desks") ||
                        productName.equalsIgnoreCase("desk")) {

                    if ((!cn.getShortClassName().equalsIgnoreCase(".MainActivity"))) {
                        Intent act = new Intent(view.getContext(), MainActivity.class);
                        act.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        act.putExtra("fragment", "homedesks");
                        view.getContext().startActivity(act);
                    } else {
                        Intent i = new Intent(SocketService.BROADCAST_MAINCHAIRS);
                        i.putExtra("fragment", "homedesks");
                        view.getContext().sendBroadcast(i);
                    }

                } else if (productName.equalsIgnoreCase("tables") ||
                        productName.equalsIgnoreCase("table")) {
                    if ((!cn.getShortClassName().equalsIgnoreCase(".MainActivity"))) {
                        Intent act = new Intent(view.getContext(), MainActivity.class);
                        act.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        act.putExtra("fragment", "hometables");
                        view.getContext().startActivity(act);
                    } else {
                        Intent i = new Intent(SocketService.BROADCAST_MAINTABLES);
                        i.putExtra("fragment", "hometables");
                        view.getContext().sendBroadcast(i);
                    }
                } else {
                    Toast.makeText(view.getContext(), "Product Not found/Currently not available in Catalog!!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewHead;
        public TextView textView;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewHead = (TextView) itemView.findViewById(R.id.Head);
            this.textView = (TextView) itemView.findViewById(R.id.sub);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear);
        }
    }
}