package com.xiangbohua.chat.server.handler;

import com.xiangbohua.chat.common.tool.MessageUtil;
import com.xiangbohua.chat.server.user.Friend;

import java.io.IOException;
import java.util.List;

/**
 * @author xiangbohua
 * @date 2020/8/18 18:34
 */
public class ListFriendHandler implements IMessageHandler {
    @Override
    public String getHandlerType() {
        return "friends";
    }

    @Override
    public byte[] handlerMessage(byte[] messageBytes) {
        String[] msgs = MessageUtil.splitMsg(messageBytes);

        checkMessage(msgs, 2);

        String userName = msgs[1];

        String result = "";
        try {
            List<String> allFriends = Friend.getAllFriends(userName);
            StringBuilder sb = new StringBuilder();
            allFriends.forEach(x->{
                sb.append(x);
                sb.append("\r\n");
            });
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.getBytes();
    }

    @Override
    public String getHelp() {
        return "friends";
    }
}
