package com.xiangbohua.chat.server.handler;

/**
 * @author xiangbohua
 * @date 2020/8/18 16:06
 */
public class OpenChatHandler implements IMessageHandler {
    @Override
    public String getHandlerType() {
        return "openct";
    }

    @Override
    public byte[] handlerMessage(byte[] messageBytes) {
        return new byte[0];
    }

    @Override
    public String getHelp() {
        return "openct:[session id]";
    }
}
