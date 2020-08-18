package com.xiangbohua.chat.server.socket;

import com.xiangbohua.chat.common.Helper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author xiangbohua
 * @date 2020/8/18 11:36
 */
public class MessageServer {
    int port;
    boolean started;
    ServerSocket serverSocket;
    Thread messageThread;

    public MessageServer() {
        this.port = Helper.getNewPort();
    }

    public MessageServer(int port) {
        this.port = port;
    }

    public void renewPort(int newPort) {
        this.port = newPort;
    }

    public int getPort() {
        return this.port;
    }

    public boolean getStarted() {
        return this.started;
    }
    public void startServer(MessageHandler handler) throws Exception {
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

        this.messageThread = new Thread(() -> {
            System.out.println("端口：" + port);
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                started = true;
                while (true) {
                    byte[] received = new byte[1024];
                    inputStream.read(received);
                    String messageReceived = new String(received);
                    System.out.println(messageReceived);
                    String message = handler.onMessage(received);
                    sendMessage(socket, message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        this.messageThread.start();
    }

    public void sendMessage(Socket socket, String message) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        byte[] msgByte = message.getBytes();

        outputStream.write(msgByte);
        outputStream.flush();
    }
}
