package com.questpirates.greathomesfurniture.bottomNavFrags;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.questpirates.greathomesfurniture.Adapters.chatMessageAdapter;
import com.questpirates.greathomesfurniture.CVAssistant.TextToSpeechh;
import com.questpirates.greathomesfurniture.R;
import com.questpirates.greathomesfurniture.pojo.chatMessagePojo;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class AssistantFragment extends Fragment {

    View view, ourChatView;
    EditText chatBox;
    ImageButton sendChat;
    FloatingActionButton voicechat;
    String userMessage;
    ListView chatlist;
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private String VOICE_DATA = "";

    private Bot ourBot;
    private static Chat ourChat;
    private chatMessageAdapter chatMsgAdapter;


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
        view = inflater.inflate(R.layout.fragment_assistant, null);
        chatlist = view.findViewById(R.id.chatdatascroll);
        chatBox = view.findViewById(R.id.chattext);
        sendChat = view.findViewById(R.id.sendchatbot);
        voicechat = view.findViewById(R.id.voicebot);


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


        chatMsgAdapter = new chatMessageAdapter(getContext(), new ArrayList<chatMessagePojo>());
        chatlist.setAdapter(chatMsgAdapter);


        sendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = chatBox.getText().toString();
                String resp = ourChat.multisentenceRespond(msg);
                if ((msg).trim().equals("")) {
                    Toast.makeText(getContext(), "Type Something to send the message", Toast.LENGTH_LONG).show();
                    return;
                }

                chatReqRes(msg, resp);

                chatBox.getText().clear();
                return;
            }
        });


        /////////////////////////////////
        voicechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speechToText();
            }
        });

        boolean sdAvail = isSDCardAvailable();

        AssetManager assetManager = getResources().getAssets();
        File sdCardfile = new File(Environment.getExternalStorageDirectory().toString() + "/GHFBot/bots/GHFBot");
        System.out.println("SDCARD OUT " + sdCardfile.getPath());

        boolean makeFile = sdCardfile.mkdirs();

        if (sdCardfile.exists()) {
            //read the data
            try {
                boolean res = copyAssetFolder(getContext(),
                        "GHFBot",
                        Environment.getExternalStorageDirectory().toString() + "/GHFBot/bots/GHFBot");

                System.out.println("COPY RESULT " + res + "");


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //getWorking Directory
        MagicStrings.root_path = Environment.getExternalStorageDirectory().toString() + "/GHFBot";
        System.out.println("ROOTPATH " + MagicStrings.root_path);
        AIMLProcessor.extension = new PCAIMLProcessorExtension();

        ourBot = new Bot("GHFBot", MagicStrings.root_path, "chat");
        ourChat = new Chat(ourBot);


        return view;
    }

    private void chatReqRes(String request, String response) {

        sendChatMethod(request);
        botReply(response);

    }

    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean copyAssetFolder(Context context, String srcName, String dstName) {
        try {
            boolean result = true;
            String fileList[] = context.getAssets().list(srcName);
            if (fileList == null) return false;

            if (fileList.length == 0) {
                result = copyAssetFile(context, srcName, dstName);
            } else {
                File file = new File(dstName);
                result = file.mkdirs();
                for (String filename : fileList) {
                    result &= copyAssetFolder(context, srcName + File.separator + filename, dstName + File.separator + filename);
                }
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean copyAssetFile(Context context, String srcName, String dstName) {
        try {
            InputStream in = context.getAssets().open(srcName);
            File outFile = new File(dstName);
            OutputStream out = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void botReply(String resp) {
        chatMessagePojo cmp = new chatMessagePojo(false, resp);
        chatMsgAdapter.add(cmp);
    }


    private void sendChatMethod(final String msg) {
        chatMessagePojo cmp = new chatMessagePojo(true, msg);
        chatMsgAdapter.add(cmp);
    }

    /////////////// VOICE BOT ????????????????????????
    public void speechToText() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getContext(), "Sorry! Your device doesnt support speech input",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == Activity.RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    VOICE_DATA = (result.get(0));

                    chatBox.getText().clear();
                    //chatBox.setText(VOICE_DATA);
                    //sendChatMethod(VOICE_DATA);

                    String resp = ourChat.multisentenceRespond(VOICE_DATA);
                    chatReqRes(VOICE_DATA, resp);

                    new TextToSpeechh().textToSpeech(resp, getContext());

                }
                break;
            }

        }
    }


}
