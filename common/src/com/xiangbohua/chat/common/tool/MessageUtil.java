package com.xiangbohua.chat.common.tool;


/**
 * @author xiangbohua
 * @date 2020/8/18 17:12
 */
public class MessageUtil {

    public static String[] splitMsg(byte[] msgBytes) {
        return splitMsg(new String(msgBytes));
    }

    public static String[] splitMsg(String msg) {
        if (null == msg) {
            return null;
        }
        return msg.split(":");
    }

    public static String getAction(String[] message) {
        if (message == null || message.length == 0) {
            return null;
        }
        return message[0];
    }

    public static String getAction(byte[] msBytes) {
        return getAction(splitMsg(msBytes));
    }

    public static String getAction(String message) {
        return splitMsg(message)[0];
    }

}
