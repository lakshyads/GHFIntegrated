package com.questpirates.greathomesfurniture.myServices;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.questpirates.greathomesfurniture.MainActivity;
import com.questpirates.greathomesfurniture.R;
import com.questpirates.greathomesfurniture.utils.SocketInstance;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

public class SocketService extends Service {

    private Socket socket;
    Handler handler;

    public SocketService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        Log.d("ActivityName", cn.getClassName());
        Fragment fr = MainActivity.fragmentManager.findFragmentById(R.id.fragment_container);
        String fragmentName = fr.getClass().getSimpleName();
        Log.d("FragmentName", fragmentName);

        checkActivity();

        Log.d("SocketService", "Started");

        try {
            SocketInstance app = (SocketInstance) getApplication();
            socket = app.getSocket();
            socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
            socket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
            socket.connect();

        } catch (Exception e) {
            Log.d("socket connection error", Objects.requireNonNull(e.getMessage()));
            e.printStackTrace();
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setMessage(e.getMessage())
                    .setTitle("Error!");
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        // Listen socket event
        socket.on("Change renderable color", socketListener);
    }

    // Listener for socket event - socketListener
    private Emitter.Listener socketListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];

            Handler requestSuccessHandler = new Handler(Looper.getMainLooper());
            requestSuccessHandler.post(() -> {
                String intentName = "";
                String color = "";
                try {
                    intentName = data.getString("intentName");
                    color = data.getString("color");
                    Log.d("SocketService", String.valueOf(data));
                } catch (JSONException e) {
                    Log.d("socket-data-parse-error", Objects.requireNonNull(e.getMessage()));
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setMessage(e.getMessage())
                            .setTitle("Error!");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                if (intentName.equalsIgnoreCase("change-obj-color")) {
                    //changeColorEvent(color);
                    Log.d("SocketService", intentName + "");
                }
                switch (intentName.toLowerCase()){

                    // open Activities
                    case "open-ar" : //Code to open AR Activity
                    break;
                    case "open-iv" : //Code to open Intelligent Vision Activity
                        break;
                    case "open-qr" : //Code to open QR Activity
                        break;
                    case "open-cart" : //Code to open Main Activity - Cart Fragment
                        break;
                    case "open-home" : //Code to open Main Activity - Home Fragment
                        break;
                    case "open-profile" : //Code to open Main Activity - Profile Fragement
                        break;
                    case "open-chat" : //Code to open Main Activity - chat Fragement
                        break;

                    // do tasks & ask queations
                    case "check-warrenty" : //respond some random warranty info
                        break;
                    case "check-productinfo" : //respond some random product info
                        break;
                    case "change-color" : //change item color
                        break;
                    case "connect-liveagent" : //connect to live agent, trigger email
                        break;
                    case "check-price" : ////respond some random price
                        break;
                    case "whats-oncart" : //respond with contents of cart
                        break;
                    case "show-chairs" : //Main Activity - Home Fragement - Chairs tab
                        break;
                    case "show-desks" : //Main Activity - Home Fragement - desks tab
                        break;
                    case "show-tables" : //Main Activity - Home Fragement - tables tab
                        break;



                    default: Toast.makeText(getApplicationContext(),"Nothing Here!!!!", Toast.LENGTH_LONG).show();
                }
                // add the message to view
                //addMessage(username, message);
            });

        }
    };


    // Listener for socket connection error - onConnectError
    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            Handler requestErrorHandler = new Handler(Looper.getMainLooper());
            requestErrorHandler.post(() ->
                    Toast.makeText(getApplicationContext(), "Unable to connect to NodeJS server", Toast.LENGTH_LONG).show()
            );
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();

        socket.disconnect();
        socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);

        handler = null;

        Log.d("SocketService", "Stopped");
    }

    public void checkActivity() {
        handler = new Handler();
        ActivityRunnable activityRunnable = new ActivityRunnable();
        handler.postDelayed(activityRunnable, 500);
    }

    private class ActivityRunnable implements Runnable {
        @Override
        public void run() {
            ActivityManager manager = (ActivityManager) getApplicationContext()
                    .getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> runningTasks = manager.getRunningTasks(1);
            if (runningTasks != null && runningTasks.size() > 0) {
                ComponentName componentName = runningTasks.get(0).topActivity;
                Log.d("VAL", componentName.getShortClassName());

                // Here you can get the TopActivity for every 500ms
                if (componentName.getShortClassName().equals(".MainActivity")) {
                    Fragment fr = MainActivity.fragmentManager.findFragmentById(R.id.fragment_container);
                    String fragmentName = fr.getClass().getSimpleName();
                    Log.d("MainActivityFragmentName", fragmentName);
                }
                handler.postDelayed(this, 500);
            }
        }
    }


}
