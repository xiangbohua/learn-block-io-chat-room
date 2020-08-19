package com.xiangbohua.chat.server.handler;

import com.xiangbohua.chat.common.tool.MessageUtil;
import com.xiangbohua.chat.server.sys.SysUser;
import com.xiangbohua.chat.server.user.Friend;

/**
 * @author xiangbohua
 * @date 2020/8/19 09:48
 */
public class AddFriendHandler implements IMessageHandler {
    @Override
    public String getHandlerType() {
        return "addfrd";
    }

    @Override
    public byte[] handlerMessage(byte[] messageBytes) {
        String[] msgs = MessageUtil.splitMsg(messageBytes);
        checkMessage(msgs, 3);

        String yourName = msgs[1];
        String friendName = msgs[2];

        try {
            boolean current = SysUser.checkExisted(yourName);
            if (!current) {
                throw new Exception("Your are not registered");
            }
            boolean friend = SysUser.checkExisted(friendName);
            if (!friend) {
                throw new Exception("Your friend was not registered");
            }

            Friend.addFriends(yourName, friendName);
            return "Added".getBytes();
        } catch (Exception e) {
            e.printStackTrace();
            return ("Error occurred when add friend :" + e.getMessage()).getBytes();
        }
    }

    @Override
    public String getHelp() {
        return "addfrd:[your name]:[friend name you want]";
    }
}
