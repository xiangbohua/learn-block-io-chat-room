package com.xiangbohua.chat.server.handler;

/**
 * @author xiangbohua
 * @date 2020/8/18 16:02
 */
public class LogoutHandler implements IMessageHandler{
    @Override
    public String getHandlerType() {
        return "logout";
    }

    @Override
    public byte[] handlerMessage(byte[] messageBytes) {
        return new byte[0];
    }

    @Override
    public String getHelp() {
        return "logout";
    }
}
