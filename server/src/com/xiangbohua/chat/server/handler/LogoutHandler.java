package com.xiangbohua.chat.server.handler;

import com.xiangbohua.chat.common.tool.MessageUtil;
import com.xiangbohua.chat.server.SystemState;
import com.xiangbohua.chat.server.socket.MessageServer;

import java.io.IOException;

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

        String[] msgs = MessageUtil.splitMsg(messageBytes);
        checkMessage(msgs, 2);

        String userName = msgs[1];

        try {
            SystemState.removeUserServer(userName);

            return "Logout succeed.".getBytes();
        } catch (Exception e) {

            return ("Logout error:" + e.getMessage()).getBytes();
        }

    }

    @Override
    public String getHelp() {
        return "logout";
    }
}
