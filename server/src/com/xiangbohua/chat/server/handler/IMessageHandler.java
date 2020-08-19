package com.xiangbohua.chat.server.handler;

/**
 * @author xiangbohua
 * @date 2020/8/17 17:58
 */
public interface IMessageHandler {

    String getHandlerType();

    byte[] handlerMessage(byte[] messageBytes);

    String getHelp();

    default void checkMessage(String[] msgs, int lengthRequire) {
        if (msgs.length < lengthRequire) {
            throw new IllegalArgumentException("Wrong message format");
        }
    }

}
