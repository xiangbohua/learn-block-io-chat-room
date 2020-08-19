package com.xiangbohua.chat.server.handler;

import com.xiangbohua.chat.common.tool.MessageUtil;
import com.xiangbohua.chat.server.common.ChatMessage;
import com.xiangbohua.chat.server.user.ChatSession;

import java.time.LocalDateTime;

/**
 * @author xiangbohua
 * @date 2020/8/17 18:00
 */
public class MessageReceivedHandler implements IMessageHandler {

    @Override
    public String getHandlerType() {
        return "msg";
    }

    @Override
    public byte[] handlerMessage(byte[] messageBytes) {
        String[] msgs = MessageUtil.splitMsg(messageBytes);
        checkMessage(msgs, 4);
        String senderName = msgs[1];
        String sessionId = msgs[2];
        String contentType = msgs[3];


        int contentStartIndex = 0;
        int foundIndex = 0;
        for (byte b : messageBytes) {
            if (b == ':') {
                foundIndex ++;
            }
            contentStartIndex ++;
            if (foundIndex == 4) {
                break;
            }
        }
        int messageContentLength = messageBytes.length - contentStartIndex;
        byte[] messageContent = new byte[messageContentLength];
        System.arraycopy(messageBytes, contentStartIndex, messageContent, 0, messageContentLength);

        ChatMessage msg = new ChatMessage();
        msg.sessionId = sessionId;
        msg.contentType = contentType;
        msg.createdAt = LocalDateTime.now();
        msg.sendUser = senderName;
        msg.content = messageContent;

        String result = "";
        ChatSession chatSession = ChatSession.getInstance(sessionId);
        if (chatSession == null) {
            result = "Chat session not found. You can pick up a friends and start a chat session";
        } else {
            try {
                chatSession.sendMessage(senderName, msg);
                result = "Send succeed!";
            } catch (Exception e) {
                e.printStackTrace();
                result = "Send faild:" + e.getMessage();
            }
        }
        return result.getBytes();
    }


    @Override
    public String getHelp() {
        return "msg:[you name]:[session id]:[message type]:[message content]";
    }
}
