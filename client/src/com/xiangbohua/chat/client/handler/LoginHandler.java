package com.xiangbohua.chat.client.handler;

import com.xiangbohua.chat.client.sys.ClientState;
import com.xiangbohua.chat.common.tool.Output;
import com.xiangbohua.chat.common.tool.MessageUtil;

/**
 * @author xiangbohua
 * @date 2020/8/19 14:28
 */
public class LoginHandler implements IMessageHandler {
    @Override
    public String getHandlerType() {
        return "login";
    }

    @Override
    public String handleMessage(byte[] message) {
        String[] result = MessageUtil.splitMsg(message);

        Integer port = 0;
        try {
            port = Integer.parseInt(result[0].trim());
        } catch (Exception ex) {
            return "Login failed :" + ex.getMessage();
        }
        if (port > 0) {
            Output.showMessage("Login succeed and connection was established!" + port);
            ClientState.setUserPort(port);

            return "quit";
        } else {
            return "Login failed:" + result[1];
        }

    }

    @Override
    public String buildCommand(String... args) {
        return MessageUtil.buildMsg(this.getHandlerType(), args[0], args[1]);
    }
}
