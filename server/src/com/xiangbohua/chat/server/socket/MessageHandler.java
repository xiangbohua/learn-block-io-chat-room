package com.xiangbohua.chat.server.socket;

/**
 * @author xiangbohua
 * @date 2020/8/18 11:24
 */
@FunctionalInterface
public interface MessageHandler {

    String onMessage(byte[] data);

}
