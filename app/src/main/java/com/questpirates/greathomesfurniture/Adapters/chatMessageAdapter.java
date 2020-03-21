package com.questpirates.greathomesfurniture.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.questpirates.greathomesfurniture.R;
import com.questpirates.greathomesfurniture.pojo.chatMessagePojo;

import java.util.List;

public class chatMessageAdapter extends ArrayAdapter<chatMessagePojo> {

    private static final int MY_MESSAGE = 0;
    private static final int BOT_MESSAGE = 1;


    public chatMessageAdapter(@NonNull Context context, List<chatMessagePojo> data) {
        super(context, R.layout.chatbyus,data);
    }

    @Override
    public int getViewTypeCount() {
        //  return super.getViewTypeCount();
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        chatMessagePojo item = getItem(position);

        if (item.isMine()) {
            System.out.println("MY MESSAGE " + MY_MESSAGE);
            return MY_MESSAGE;
        } else {
            System.out.println("BOT MESSAGE " + BOT_MESSAGE);
            return BOT_MESSAGE;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        int viewType = getItemViewType(position);

        if (viewType == MY_MESSAGE) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chatbyus, parent, false);

            TextView userMessage = convertView.findViewById(R.id.message_body);
            userMessage.setText(getItem(position).getChatContent());

        } else if (viewType == BOT_MESSAGE) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chatbytheirs, parent, false);

            TextView botMessage = convertView.findViewById(R.id.message_body);
            botMessage.setText(getItem(position).getChatContent());
        }

//        convertView.findViewById(R.id.chatlayout).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });

        //return super.getView(position, convertView, parent);
        return convertView;
    }
}
