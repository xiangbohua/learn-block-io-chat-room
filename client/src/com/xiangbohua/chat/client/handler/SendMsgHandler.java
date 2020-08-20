package com.xiangbohua.chat.client.handler;

/**
 * @author xiangbohua
 * @date 2020/8/20 14:11
 */
public class SendMsgHandler implements IMessageHandler {
    @Override
    public String getHandlerType() {
        return "msg";
    }

    @Override
    public String handleMessage(byte[] message) {
        return "Sent";
    }

    @Override
    public String buildCommand(String... args) {
        return String.join(":", args);
    }
}
