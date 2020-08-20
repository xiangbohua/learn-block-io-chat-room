package com.xiangbohua.chat.client.handler;

import com.xiangbohua.chat.common.tool.Output;
import com.xiangbohua.chat.common.tool.MessageUtil;

/**
 * @author xiangbohua
 * @date 2020/8/19 14:28
 */
public class RegHandler implements IMessageHandler {
    @Override
    public String getHandlerType() {
        return "reg";
    }

    @Override
    public String handleMessage(byte[] message) {
        Output.showMessage(new String(message));
        return "";
    }

    @Override
    public String buildCommand(String... args) {
        return MessageUtil.buildMsg(this.getHandlerType(), args[0], args[1]);
    }
}
