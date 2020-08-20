package com.xiangbohua.chat.server.socket;

import com.xiangbohua.chat.common.tool.Output;
import com.xiangbohua.chat.server.common.Helper;
import com.xiangbohua.chat.server.handler.HandlerContainer;
import com.xiangbohua.chat.server.handler.IMessageHandler;
import com.xiangbohua.chat.server.handler.LogoutHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 * @author xiangbohua
 * @date 2020/8/18 11:36
 */
public class MessageServer {
    int port;
    boolean started;
    ServerSocket serverSocket;
    Thread messageThread;
    MessageRunner messageRunner;

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
    public void startServer() throws Exception {
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
        this.messageRunner = new MessageRunner();
        this.messageThread = new Thread(messageRunner);

        this.messageThread.start();
    }

    public void sendMessage(Socket socket, byte[] messageBytes) throws IOException {
        if (Objects.isNull(messageBytes)) {
            return;
        }
        OutputStream outputStream = socket.getOutputStream();

        outputStream.write(messageBytes);
        outputStream.write("\r\n".getBytes());
        outputStream.flush();
    }

    boolean isClosing = false;

    public void stopServer() throws IOException {
        isClosing = true;
        this.messageRunner.stopReceive();
        this.messageThread.interrupt();
    }

    public class MessageRunner implements Runnable {
        Socket socket;

        @Override
        public void run() {
            Output.debug("Message server is listening：" + port);
            try {
                this.socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                started = true;
                boolean getError = false;
                while (!Thread.currentThread().isInterrupted()) {
                    byte[] received = new byte[1024];
                    inputStream.read(received);
                    String messageReceived = new String(received);
                    Output.debug(messageReceived);
                    if (getError) {
                        this.socket = serverSocket.accept();
                        inputStream = socket.getInputStream();
                        getError = false;
                    }
                    try {
                        IMessageHandler handler = HandlerContainer.getHandler(received);

                        if (handler == null) {
                            sendMessage(socket, "Action not support".getBytes());
                        } else {
                            sendMessage(socket, handler.handlerMessage(received));
                        }
                        if (handler instanceof LogoutHandler) {
                            break;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println("消息读取错误");
                        getError = true;
                        if (isClosing) {
                            break;
                        }
                    }
                }
                Output.debug("Message server is closing!");
                inputStream.close();
                socket.close();
                serverSocket.close();
                Output.debug("Message server was closed!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void stopReceive() throws IOException {
            if (socket != null) {
                this.socket.shutdownInput();
            }
            serverSocket.close();
        }
    }

}
