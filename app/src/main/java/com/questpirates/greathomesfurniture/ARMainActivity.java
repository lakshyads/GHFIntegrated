package com.questpirates.greathomesfurniture;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.questpirates.greathomesfurniture.myServices.SocketService;
import com.questpirates.greathomesfurniture.utils.SocketInstance;
//import com.questpirates.greathomesfurniture.utils.SocketInstance;

//import io.socket.client.Socket;
//import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

public class ARMainActivity extends AppCompatActivity {
    private ArFragment fragment;
    private Uri selectedObject;
    private Anchor anchor;
    private Boolean isAnchor = false;
   /* private Socket socket;*/

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_r_main);

        // Color change button
        Button colorBtn = findViewById(R.id.colorBtn);
        colorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColorEvent("blue");
            }
        });

       /* // Instantiate socket connection
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
        socket.on("Change renderable color", socketListener);*/

        // Emit socket event
        /*sendButton.setOnCLickListener{
            mSocket.emit(“EventName”,dataObject));
        }*/

        // Initialize Fragment Element as ARFragment
        fragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arfragment);

        // Intent intent = getIntent();
        selectedObject = getIntent().getData();
        Log.d("BindView", "SFB AFTER" + selectedObject);
        Toast.makeText(this, "SFB: " + selectedObject, Toast.LENGTH_SHORT).show();

        if (selectedObject == null) {
            Toast.makeText(this, "URI IS NULL", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "URI IS " + selectedObject, Toast.LENGTH_SHORT).show();
        }

        fragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {

                    if (plane.getType() != Plane.Type.HORIZONTAL_UPWARD_FACING) {
                        return;
                    }

                    anchor = hitResult.createAnchor();
                    isAnchor = true;

                    placeObject(fragment, anchor, selectedObject, null);
                }
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

       /* socket.disconnect();
        socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);*/
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeObject(ArFragment fragment, Anchor anchor, Uri model, Color color) {
        ModelRenderable.builder()
                .setSource(Objects.requireNonNull(fragment.getContext()), model)
                .build()
                .thenAccept(renderable -> addNodeToScene(fragment, anchor, renderable, color
                ))
                .exceptionally((throwable -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(throwable.getMessage())
                            .setTitle("Error!");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return null;
                }));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addNodeToScene(ArFragment fragment, Anchor anchor, Renderable renderable, Color color) {
        if (color != null) {
            renderable = changeNodeColor(
                    renderable, color);
        }
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setName("renderable");
        TransformableNode transformableNode = new TransformableNode(fragment.getTransformationSystem());
        transformableNode.setRenderable(renderable);
        transformableNode.setParent(anchorNode);
        fragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
    }


    private void removeNodesFromScene(ArFragment fragment, Anchor anchor) {
        List<Node> fragmentChildren = fragment.getArSceneView().getScene().getChildren();
        for (Node node : fragmentChildren) {
            if (node.getName().equalsIgnoreCase("renderable"))
                fragment.getArSceneView().getScene().removeChild(node);
        }
    }

    /* Change the color of renderable */
    private Renderable changeNodeColor(Renderable renderable, Color color) {
        Renderable renderableCopy = renderable.makeCopy();
        try {
            Material changedMaterial = (renderableCopy.getMaterial()).makeCopy();
            changedMaterial.setFloat3("baseColorTint", color);
            renderableCopy.setMaterial(changedMaterial);
        } catch (Exception err) {
            Log.d("color change error", Objects.requireNonNull(err.getMessage()));
            Log.d("changeNodeColor stacktrace", err.toString());
            Toast.makeText(getApplicationContext(), "color change exception " + err.getMessage(), Toast.LENGTH_LONG).show();
        }
        return renderableCopy;
    }

    public void changeColorEvent(String colorString) {
        if (isAnchor) {
            Color c = new Color(android.graphics.Color.GRAY);
            switch (colorString.toUpperCase()) {
                case "BLUE":
                    c = new Color(android.graphics.Color.BLUE);
                    break;
                case "BLACK":
                    c = new Color(android.graphics.Color.BLACK);
                    break;
                case "GREEN":
                    c = new Color(android.graphics.Color.GREEN);
                    break;
                case "GRAY":
                    c = new Color(android.graphics.Color.GRAY);
                    break;
                case "RED":
                    c = new Color(android.graphics.Color.RED);
                    break;
                case "WHITE":
                    c = new Color(android.graphics.Color.WHITE);
                    break;
                case "BROWN":
                    c = new Color(android.graphics.Color.rgb(210,105,30));
                    break;
                default:
            }
            Log.d("changeColorEvent","IN");
            removeNodesFromScene(fragment, anchor);
            // placeObject(fragment, anchor, selectedObject, new Color(android.graphics.Color.argb(1, 137, 196, 244)));
            placeObject(fragment, anchor, selectedObject, c);
        }


    }

    public void switchh(Intent intent){
    String c = intent.getStringExtra("color");
        changeColorEvent(c);
    }

   /* // Listener for socket event - socketListener
    private Emitter.Listener socketListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String intentName = "";
                    String color = "";
                    try {
                        intentName = data.getString("intentName");
                        color = data.getString("color");
                        Log.d("Socket Data", String.valueOf(data));
                    } catch (JSONException e) {
                        Log.d("socket-data-parse-error", Objects.requireNonNull(e.getMessage()));
                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                        builder.setMessage(e.getMessage())
                                .setTitle("Error!");
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                    if (intentName.equalsIgnoreCase("change-obj-color")) {
                        changeColorEvent(color);
                    }
                    // add the message to view
                    //addMessage(username, message);
                }
            });
//            finish();
        }
    };*/

    /*// Listener for socket connection error - onConnectError
    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Unable to connect to NodeJS server", Toast.LENGTH_LONG).show();
                }
            });
        }
    };*/

    private BroadcastReceiver broadcastReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switchh(intent);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(SocketService.BROADCAST_COLOR));
    }
}
