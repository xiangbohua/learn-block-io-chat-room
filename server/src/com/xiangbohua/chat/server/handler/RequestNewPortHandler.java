package com.xiangbohua.chat.server.handler;

/**
 * @author xiangbohua
 * @date 2020/8/17 18:03
 */
public class RequestNewPortHandler implements IMessageHandler {
    @Override
    public String getHandlerType() {
        return "newpt";
    }

    @Override
    public byte[] handlerMessage(byte[] messageBytes) {
        return new byte[0];
    }

    @Override
    public String getHelp() {
        return null;
    }
}
