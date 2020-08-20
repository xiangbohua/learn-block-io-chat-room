package com.xiangbohua.chat.client.handler;

/**
 * @author xiangbohua
 * @date 2020/8/20 14:41
 */
public class HelpHandler implements IMessageHandler {
    @Override
    public String getHandlerType() {
        return "help";
    }

    @Override
    public String handleMessage(byte[] message) {
        return new String(message);
    }

    @Override
    public String buildCommand(String... args) {
        return String.join(":", args);
    }
}
