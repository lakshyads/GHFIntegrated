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
import com.questpirates.greathomesfurniture.ARMainActivity;
import com.questpirates.greathomesfurniture.MainActivity;
import com.questpirates.greathomesfurniture.R;
import com.questpirates.greathomesfurniture.ScanVision.IntelliVisionActivity;
import com.questpirates.greathomesfurniture.ScanVision.ScanQRActivity;
import com.questpirates.greathomesfurniture.WelcomeActivity;
import com.questpirates.greathomesfurniture.arcore.ArCoreHome;
import com.questpirates.greathomesfurniture.utils.SocketInstance;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

public class SocketService extends Service {

    public static final String BROADCAST_COLOR = "HELLO";
    public static final String BROADCAST_MAIN = "MAIN";
    public static final String BROADCAST_MAINCHAIRS = "MCHAIRS";
    public static final String BROADCAST_MAINDESKS = "MDESKS";
    private Socket socket;
    private ActivityManager am;
    private ComponentName cn;
    Handler handler;
    Intent i;

    public SocketService() {

    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        //checkActivity();

        Log.d("SocketService", "Started");

        // Get socket instance from SocketInstance class and connect to it
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
        socket.on("Open Activity", socketListener);
    }

    // Listener for socket event - socketListener
    private Emitter.Listener socketListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];

            Handler requestSuccessHandler = new Handler(Looper.getMainLooper());
            requestSuccessHandler.post(() -> {
                String intentName = "";
                try {
                    intentName = data.getString("intentName");
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
                    String color = null;
                    try {
                        color = data.getString("color");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    i = new Intent(BROADCAST_COLOR);
                    i.putExtra("color", color);
                    sendBroadcast(i);
                    Log.d("SocketServiceColor", intentName + color);
                }

                am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
                cn = am.getRunningTasks(1).get(0).topActivity;
                Log.d("ActivityName", cn.getClassName());
                Fragment fr = MainActivity.fragmentManager.findFragmentById(R.id.fragment_container);
                String fragmentName = fr.getClass().getSimpleName();
                Log.d("FragmentName", fragmentName);

                String activityName = "";
                try {
                    activityName = data.getString("activityName");
                } catch (JSONException e) {
                    Log.d("ActivityNameError", "Error " + e);
                }


                switch (intentName.toLowerCase()) {

                    // open Activities
                    case "open-activity": //Code to open AR Activity
                        switch (activityName.toLowerCase()) {
                            case "ar":
                                if ((!cn.getShortClassName().equalsIgnoreCase(".ArCoreHome"))) {
                                    Intent act = new Intent(getApplicationContext(), ArCoreHome.class);
                                    act.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(act);
                                }
                                break;
                            case "iv":
                                if ((!cn.getShortClassName().equalsIgnoreCase(".IntelliVisionActivity"))) {
                                    Intent act = new Intent(getApplicationContext(), IntelliVisionActivity.class);
                                    act.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(act);
                                }
                                break;
                            case "qr":
                                if ((!cn.getShortClassName().equalsIgnoreCase(".ScanQRActivity"))) {
                                    Intent act = new Intent(getApplicationContext(), ScanQRActivity.class);
                                    act.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(act);
                                }
                                break;
                            case "cart": //Code to open Main Activity - Cart Fragment
                                if ((!cn.getShortClassName().equalsIgnoreCase(".MainActivity"))) {
                                    Intent act = new Intent(getApplicationContext(), MainActivity.class);
                                    act.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    act.putExtra("fragment", "cart");
                                    startActivity(act);
                                } else {
                                    i = new Intent(BROADCAST_MAIN);
                                    i.putExtra("fragment", "cart");
                                    sendBroadcast(i);
                                }
                                break;
                            case "home": //Code to open Main Activity - Home Fragment
                                if ((!cn.getShortClassName().equalsIgnoreCase(".MainActivity"))) {
                                    Intent act = new Intent(getApplicationContext(), MainActivity.class);
                                    act.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    act.putExtra("fragment", "home");
                                    startActivity(act);
                                } else {
                                    i = new Intent(BROADCAST_MAIN);
                                    i.putExtra("fragment", "home");
                                    sendBroadcast(i);
                                }
                                break;
                            case "profile": //Code to open Main Activity - Profile Fragement
                                if ((!cn.getShortClassName().equalsIgnoreCase(".MainActivity"))) {
                                    Intent act = new Intent(getApplicationContext(), MainActivity.class);
                                    act.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    act.putExtra("fragment", "profile");
                                    startActivity(act);
                                } else {
                                    i = new Intent(BROADCAST_MAIN);
                                    i.putExtra("fragment", "profile");
                                    sendBroadcast(i);
                                }
                                break;
                            case "chat": //Code to open Main Activity - chat Fragement
                                if ((!cn.getShortClassName().equalsIgnoreCase(".MainActivity"))) {
                                    Intent act = new Intent(getApplicationContext(), MainActivity.class);
                                    act.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    act.putExtra("fragment", "chat");
                                    startActivity(act);
                                } else {
                                    i = new Intent(BROADCAST_MAIN);
                                    i.putExtra("fragment", "chat");
                                    sendBroadcast(i);
                                }
                                break;
                            case "show chairs": //Main Activity - Home Fragement - Chairs tab
                                if ((!cn.getShortClassName().equalsIgnoreCase(".MainActivity"))) {
                                    Intent act = new Intent(getApplicationContext(), MainActivity.class);
                                    act.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    act.putExtra("fragment", "homechairs");
                                    startActivity(act);
                                } else {
                                    i = new Intent(BROADCAST_MAINCHAIRS);
                                    i.putExtra("fragment", "homechairs");
                                    sendBroadcast(i);
                                }
                                break;
                            case "show desks": //Main Activity - Home Fragement - desks tab
                                if ((!cn.getShortClassName().equalsIgnoreCase(".MainActivity"))) {
                                    Intent act = new Intent(getApplicationContext(), MainActivity.class);
                                    act.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    act.putExtra("fragment", "homedesks");
                                    startActivity(act);
                                } else {
                                    i = new Intent(BROADCAST_MAINDESKS);
                                    i.putExtra("fragment", "homedesks");
                                    sendBroadcast(i);
                                }
                                break;
                            case "show tables": //Main Activity - Home Fragement - tables tab
                                if ((!cn.getShortClassName().equalsIgnoreCase(".MainActivity"))) {
                                    Intent act = new Intent(getApplicationContext(), MainActivity.class);
                                    act.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    act.putExtra("fragment", "hometables");
                                    startActivity(act);
                                } else {
                                    i = new Intent(BROADCAST_MAIN);
                                    i.putExtra("fragment", "hometables");
                                    sendBroadcast(i);
                                }
                                break;
                        }

                        break;


                    // intent - actions - do something in opened activities
                    case "check-warrenty": //respond some random warranty info
                        break;
                    case "check-productinfo": //respond some random product info
                        break;
                    case "change-obj-color": //change AR renderable item color in AR activity
                        break;
                    case "connect-liveagent": //connect to live agent, trigger email
                        break;
                    case "check-price": ////respond some random price
                        break;
                    case "whats-oncart": //respond with contents of cart
                        break;




                    default:
                        Toast.makeText(getApplicationContext(), "Nothing Here!!!!", Toast.LENGTH_LONG).show();
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
