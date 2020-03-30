package com.questpirates.greathomesfurniture.utils;

import android.app.Application;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

//import io.socket.client.IO;
//import io.socket.client.Socket;

import java.net.URISyntaxException;

public class SocketInstance extends Application{
    private static final String URL = "https://ghf-backend.herokuapp.com";
    private Socket mSocket;
    {
        try {
            IO.Options opts = new IO.Options();
            opts.port = 3000;
           // opts.query = SocketQuery.getQUERY();
            mSocket = IO.socket(URL,opts);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
