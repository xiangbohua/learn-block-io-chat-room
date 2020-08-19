package com.xiangbohua.chat.server.msg;

import com.xiangbohua.chat.server.common.ChatMessage;
import com.xiangbohua.chat.server.common.MessageType;

/**
 * @author xiangbohua
 * @date 2020/8/19 11:33
 */
public interface IMessageSender {
    MessageType supportType();

    String sendMessage(String toUser, ChatMessage message);
}
