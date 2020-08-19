package com.xiangbohua.chat.server.handler;

import com.xiangbohua.chat.common.tool.MessageUtil;

import java.util.List;

/**
 * @author xiangbohua
 * @date 2020/8/18 17:36
 */
public class HelpHandler implements IMessageHandler {
    @Override
    public String getHandlerType() {
        return "help";
    }

    @Override
    public byte[] handlerMessage(byte[] messageBytes) {
        String[] msgs = MessageUtil.splitMsg(messageBytes);

        String message = "";
        if (msgs.length == 1) {
            List<IMessageHandler> handlers = HandlerContainer.getAllHandlers();
            StringBuilder sb = new StringBuilder();
            handlers.forEach(x->{
                sb.append(x.getHandlerType());
                sb.append(":");
                sb.append(x.getHelp());
                sb.append("\r\n");
            });
            message = sb.toString();
        } else {
            String action = msgs[1];
            IMessageHandler handler = HandlerContainer.getHandler(action);
            if (handler == null) {
                message = "Action you want to know was not existed";
            }
        }

        return message.getBytes();
    }

    @Override
    public String getHelp() {
        return "help:[action?]";
    }
}
