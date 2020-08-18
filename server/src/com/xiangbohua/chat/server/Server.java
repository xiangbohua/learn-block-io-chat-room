package com.xiangbohua.chat.server;

import com.xiangbohua.chat.common.Helper;
import com.xiangbohua.chat.server.socket.LoginServer;
import com.xiangbohua.chat.server.socket.MessageServer;
import com.xiangbohua.chat.server.socket.UserInfo;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiangbohua
 * @date 2020/8/17 18:09
 */
public class Server {

    LoginServer socketServer = new LoginServer(8189);

    Map<String, UserInfo> userInfo = new HashMap<>();
    Map<String, MessageServer> userMessageServer = new HashMap<>();

    Thread messageThread;


    public void start() {
        try {
            this.socketServer.startServer(this::processLogin);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("启动Server失败");
        }
    }

    private int processLogin(byte[] bytes) {
        String loginMessage = new String(bytes);

        String[] split = loginMessage.split(":");

        String action = split[0];
        String userName = split[1];

        if (!userMessageServer.containsKey(userName)) {
            if ("login".equals(action.trim())) {
                MessageServer server = this.createMessageServerForUser();
                this.userMessageServer.put(userName, server);
                try {
                    server.startServer(this::processMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (userMessageServer.containsKey(userName)) {
            return userMessageServer.get(userName).getPort();
        }
        return -1;
    }

    private MessageServer createMessageServerForUser() {
        int port = Helper.getNewPort();
        MessageServer server = new MessageServer(port);
        int retryCount = 0;
        int maxRetryCount = 3;

        while (!Helper.isPortUsing(port) && retryCount <  maxRetryCount) {
            port = Helper.getNewPort();
        }
        return server;
    }


    private String processMessage(byte[] message) {
        return null;
    }
}
