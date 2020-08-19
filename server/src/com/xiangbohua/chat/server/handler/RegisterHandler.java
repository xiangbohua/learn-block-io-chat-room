package com.xiangbohua.chat.server.handler;

import com.xiangbohua.chat.common.tool.MessageUtil;
import com.xiangbohua.chat.server.sys.SysUser;

import java.io.IOException;

/**
 * @author xiangbohua
 * @date 2020/8/18 17:10
 */
public class RegisterHandler implements IMessageHandler {
    @Override
    public String getHandlerType() {
        return "reg";
    }

    @Override
    public byte[] handlerMessage(byte[] messageBytes) {
        String[] messages = MessageUtil.splitMsg(messageBytes);

        checkMessage(messages, 3);

        String userName = messages[1];
        String pass = messages[2];

        try {
            SysUser.regUser(userName, pass);

            return "Registered. You can login now".getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return ("Registered failed :" + e.getMessage()).getBytes();
        }

    }

    @Override
    public String getHelp() {
        return "reg:[user name]:[password]";
    }
}
