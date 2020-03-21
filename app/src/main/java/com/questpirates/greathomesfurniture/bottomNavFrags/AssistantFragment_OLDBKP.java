//package com.questpirates.greathomesfurniture.bottomNavFrags;
//
//import android.app.Activity;
//import android.content.ActivityNotFoundException;
//import android.content.Intent;
//import android.os.Bundle;
//import android.speech.RecognizerIntent;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.questpirates.greathomesfurniture.CVAssistant.TextToSpeechh;
//import com.questpirates.greathomesfurniture.R;
//import com.questpirates.greathomesfurniture.dataBlock.chatHolderBlock;
//
//import java.util.ArrayList;
//import java.util.Locale;
//
//public class AssistantFragment_OLDBKP extends Fragment {
//
//    View view, ourChatView;
//    EditText chatBox;
//    ImageButton sendChat;
//    FloatingActionButton voicechat;
//    String userMessage;
//    LinearLayout chatdata;
//    private static final int REQ_CODE_SPEECH_INPUT = 100;
//    private String VOICE_DATA = "";
//
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//
//        //just change the fragment_dashboard
//        //with the fragment you want to inflate
//        //like if the class is HomeFragment it should have R.layout.home_fragment
//        //if it is DashboardFragment it should have R.layout.fragment_dashboard
//        //just change the fragment_dashboard
//        //with the fragment you want to inflate
//        //like if the class is HomeFragment it should have R.layout.home_fragment
//        //if it is DashboardFragment it should have R.layout.fragment_dashboard
//        view = inflater.inflate(R.layout.fragment_assistant, null);
//        chatdata = view.findViewById(R.id.chatdata);
//        chatBox = view.findViewById(R.id.chattext);
//        sendChat = view.findViewById(R.id.sendchatbot);
//        voicechat = view.findViewById(R.id.voicebot);
//
//        Log.d("ARRAYLIST", "LENGTH " + chatHolderBlock.chatList.size());
//        Log.d("ARRAYLIST", "DATA " + chatHolderBlock.chatList);
//        for (int i = 0; i < chatHolderBlock.chatList.size(); i++) {
//            String val = chatHolderBlock.chatList.get(i);
//            if (val.contains("user@")) {
//                sendChatMethod(val.replace("user@", ""));
//            } else if (val.contains("rose@")) {
//                sendChatResponse(val.replace("rose@", ""));
//            }
//        }
//
//        sendChat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if ((chatBox.getText().toString()).trim().equals("")) {
//                    Toast.makeText(getContext(), "Type Something to send the message", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                userMessage = (chatBox.getText().toString()).trim();
//                sendChatMethod("user@" + userMessage);
//                chatBox.getText().clear();
//                return;
//            }
//        });
//
//
//        /////////////////////////////////
//        voicechat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                speechToText();
//            }
//        });
//
//
//        return view;
//    }
//
//    private void sendChatMethod(final String msg) {
//        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
//        ViewGroup viewGroup = chatdata;
//        View chatlayout = getLayoutInflater().inflate(R.layout.chatbyus, viewGroup, false);
//        TextView tv = chatlayout.findViewById(R.id.message_body);
//        tv.setText(msg);
//        chatdata.addView(chatlayout);
//        ourChatView = chatlayout;
//
//        //chatHolderBlock.chatList.add(msg);
//
//        // sendChatResponse("rose@" + "some Response");
//    }
//
//
//    private void sendChatResponse(final String msg) {
//        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
//        ViewGroup viewGroup = chatdata;
//        View chatlayout = getLayoutInflater().inflate(R.layout.chatbytheirs, viewGroup, false);
//        TextView tv = chatlayout.findViewById(R.id.message_body);
//        tv.setText(msg);
//        chatdata.addView(chatlayout);
//        ourChatView = chatlayout;
//
//        //chatHolderBlock.chatList.add(msg);
//    }
//
//    /////////////// VOICE BOT ????????????????????????
//    public String speechToText() {
//        String res = "";
//
//        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");
//        try {
//            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
//        } catch (ActivityNotFoundException a) {
//            Toast.makeText(getContext(), "Sorry! Your device doesnt support speech input",
//                    Toast.LENGTH_SHORT).show();
//        }
//
//        return res;
//    }
//
//    /**
//     * Receiving speech input
//     */
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case REQ_CODE_SPEECH_INPUT: {
//                if (resultCode == Activity.RESULT_OK && null != data) {
//
//                    ArrayList<String> result = data
//                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    VOICE_DATA = (result.get(0));
//
//                    chatBox.getText().clear();
//                    //chatBox.setText(VOICE_DATA);
//                    sendChatMethod(VOICE_DATA);
//                    new TextToSpeechh().textToSpeech("Received your text : " + VOICE_DATA,getContext());
//
//                }
//                break;
//            }
//
//        }
//    }
//
//}
