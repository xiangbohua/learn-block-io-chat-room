package com.xiangbohua.chat.server.msg;

import com.xiangbohua.chat.server.common.ChatMessage;
import com.xiangbohua.chat.server.common.MessageType;

/**
 * @author xiangbohua
 * @date 2020/8/19 11:34
 */
public class TxtMessageSender implements IMessageSender {

    @Override
    public MessageType supportType() {
        return MessageType.TXT;
    }

    @Override
    public String sendMessage(String toUser, ChatMessage message) {
        return null;
    }
}
