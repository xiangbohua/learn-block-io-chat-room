package com.xiangbohua.chat.server.socket;

import com.xiangbohua.chat.common.Helper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.Random;

/**
 * @author xiangbohua
 * @date 2020/8/17 17:13
 */
public class LoginServer {
    int port;
    boolean started;
    ServerSocket serverSocket;

    public LoginServer() {
        this.port = Helper.getNewPort();
    }

    public LoginServer(int port) {
        this.port = port;
    }

    public void startServer(LoginHandler handler) throws Exception {
        if (started) {
            return;
        }
        if (this.serverSocket == null) {
            try {
                this.serverSocket = new ServerSocket(this.port);
            } catch (IOException ex) {
                throw new Exception("启动服务失败，未知Server");
            }
        }

        System.out.println("端口："+this.port);
        Socket socket = this.serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        while (true) {
            byte[] received = new byte[1024];
            inputStream.read(received);
            String messageReceived = new String(received);
            System.out.println(messageReceived);
            int userPort = handler.onLogin(received);
            this.sendMessage(socket, "" + userPort);
        }
    }

    public void renewPort(int newPort) {
        this.port = newPort;
    }


    public void sendMessage(Socket socket, String message) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        byte[] msgByte = message.getBytes();

        outputStream.write(msgByte);
        outputStream.flush();
    }



}
