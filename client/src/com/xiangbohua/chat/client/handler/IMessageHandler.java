package com.xiangbohua.chat.client.handler;

/**
 * @author xiangbohua
 * @date 2020/8/18 14:49
 */
public interface IMessageHandler {
    String getHandlerType();
    String handleMessage(byte[] message);
}
