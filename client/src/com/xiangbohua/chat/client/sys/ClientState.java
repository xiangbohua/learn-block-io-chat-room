package com.xiangbohua.chat.client.sys;

import com.xiangbohua.chat.client.socket.SocketClient;
import com.xiangbohua.chat.client.tool.Input;

/**
 * @author xiangbohua
 * @date 2020/8/19 14:46
 */
public class ClientState {

    private static int userPort = 0;

    private static SocketClient messageClient;

    public static void connectMsgServer() {

    }

    public static int getUserPort() {
        return userPort;
    }

    public static void setUserPort(int port) {
        userPort = port;
    }


}
