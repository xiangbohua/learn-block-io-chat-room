package com.xiangbohua.chat.client.handler;

import com.xiangbohua.chat.client.tool.Output;
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
    public String handlerMessage(byte[] message) {
        String[] result = MessageUtil.splitMsg(message);

        Integer port = 0;
        try {
            port = Integer.parseInt(result[0].trim());
        } catch (Exception ex) {
            return "Login failed :" + ex.getMessage();
        }
        Output.showMessage("Login succeed and connection was established!");
        return "quit";
    }
}
