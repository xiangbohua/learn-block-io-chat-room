package com.xiangbohua.chat.client.handler;

import com.xiangbohua.chat.common.tool.MessageUtil;

/**
 * @author xiangbohua
 * @date 2020/8/20 14:02
 */
public class LogoutHandler implements IMessageHandler {
    @Override
    public String getHandlerType() {
        return "logout";
    }

    @Override
    public String handleMessage(byte[] message) {

        return new String(message);
    }

    @Override
    public String buildCommand(String... args) {
        return MessageUtil.buildMsg(this.getHandlerType(), args[1]);
    }
}
