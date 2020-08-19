package com.xiangbohua.chat.server;

import com.xiangbohua.chat.server.common.UserInfo;
import com.xiangbohua.chat.server.socket.MessageServer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiangbohua
 * @date 2020/8/18 15:07
 */
public class SystemState {


    static Map<String, UserInfo> userInfos = new HashMap<>();
    static Map<String, MessageServer> userMessagePorts = new HashMap<>();


    public static Map<String, UserInfo> getUserInfos() {
        return userInfos;
    }

    public static int countUser() {
        return userInfos.size();
    }

    public static boolean hasUser(String userName) {
        return userInfos.containsKey(userName);
    }

    public static void putUser(String userName, UserInfo userInfo) {
        if (!userInfos.containsKey(userName)) {
            userInfos.put(userName, userInfo);
        }
    }

    public static void putUserServer(String userName, MessageServer userServer) {
        if (!userMessagePorts.containsKey(userName)) {
            userMessagePorts.put(userName, userServer);
        }
    }

    public static boolean hasUserMessagePort(String userName) {
        return userMessagePorts.containsKey(userName);
    }

    public static int getUserServerPort(String userName) {
        if (hasUserMessagePort(userName)) {
            return userMessagePorts.get(userName).getPort();
        }
        return -1;
    }

    public static MessageServer getUserServer(String userName) {
        if (userMessagePorts.containsKey(userName)) {
            return userMessagePorts.get(userName);
        }
        return null;
    }



}
