package com.xiangbohua.chat.client.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author xiangbohua
 * @date 2020/8/18 10:05
 */
public class SocketClient {
    int port;
    boolean started;
    Socket socket;

    public SocketClient(int port) {
        this.port = port;
    }

    public void connect() throws IOException {
        if (socket == null) {
            socket = new Socket((String) null, port);
        }

        socket.connect(new InetSocketAddress("", this.port));

        InputStream inputStream = socket.getInputStream();

        //socket.getChannel()

    }


}
