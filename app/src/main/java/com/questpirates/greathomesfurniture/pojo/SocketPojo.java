package com.questpirates.greathomesfurniture.pojo;

import com.github.nkzawa.socketio.client.Socket;

public class SocketPojo {

    private static Socket socket;

    public static Socket getSocket() {
        return socket;
    }

    public static void setSocket(Socket socket) {
        SocketPojo.socket = socket;
    }
}
