package com.xiangbohua.chat.client.socket;

import com.xiangbohua.chat.client.handler.IMessageHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author xiangbohua
 * @date 2020/8/18 10:05
 */
public class SocketClient {
    int port;
    Socket socket;

    public SocketClient(int port) {
        this.port = port;
    }

    public void connect() throws IOException {
        if (socket == null) {
            socket = new Socket((String) null, port);
        }
        socket.connect(new InetSocketAddress("", this.port));
    }

    public String sendData(byte[] sendingData, IMessageHandler handler) throws IOException {
        if (this.socket == null || !this.socket.isConnected()) {
            this.connect();
        }

        OutputStream send = this.socket.getOutputStream();
        send.write(sendingData);
        send.flush();

        byte[] receivedData = new byte[2014];
        InputStream receive = this.socket.getInputStream();
        int result = receive.read(receivedData);
        if (result >= 0) {
            return handler.handleMessage(receivedData);
        }
        return "";
    }

    public void close() throws IOException {
        this.socket.close();
    }

}
