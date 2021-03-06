package com.xiangbohua.chat.server.socket;

import com.xiangbohua.chat.common.tool.MessageUtil;
import com.xiangbohua.chat.common.tool.Output;
import com.xiangbohua.chat.server.common.Helper;
import com.xiangbohua.chat.server.handler.HandlerContainer;
import com.xiangbohua.chat.server.handler.IMessageHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

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

        System.out.println("端口："+this.port);

        Socket socket = this.serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        boolean hasError = false;
        while (true) {
            if (hasError) {
                socket = this.serverSocket.accept();
                inputStream = socket.getInputStream();
                hasError = false;
            }
            try {
                byte[] received = new byte[1024];
                Output.debug("Login server is listening !");
                inputStream.read(received);
                String messageReceived = new String(received);

                String[] msg = MessageUtil.splitMsg(received);

                String errorMessage = null;
                if (msg.length == 0) {
                    errorMessage = "Message format error";
                    this.sendMessage(socket, errorMessage.getBytes());
                    continue;
                }
                IMessageHandler handler = HandlerContainer.getHandler(msg[0].trim());
                if (handler == null) {
                    errorMessage = "Unknown command:" + msg[0];
                    this.sendMessage(socket, errorMessage.getBytes());
                    continue;
                }
                Output.debug("Action found :" + handler.getHandlerType());

                byte[] result = null;
                if (errorMessage != null) {
                    result = errorMessage.getBytes();
                } else {
                    result = handler.handlerMessage(received);
                }

                this.sendMessage(socket, result);
            } catch (SocketException e) {
                Output.debug("Some user disconnected:" + e.getMessage());
                hasError = true;
            } catch (Exception ex) {
                Output.debug("Known ex" + ex.getMessage());
            }
        }
    }

    public void renewPort(int newPort) {
        this.port = newPort;
    }

    public void sendMessage(Socket socket, byte[] messageByte) throws IOException {
        OutputStream outputStream = socket.getOutputStream();

        outputStream.write(messageByte);
        outputStream.write("\r\n".getBytes());
        outputStream.flush();
    }



}
