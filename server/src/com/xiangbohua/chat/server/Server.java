package com.xiangbohua.chat.server;

import com.xiangbohua.chat.server.socket.LoginServer;
import com.xiangbohua.chat.server.sys.SysUser;
import com.xiangbohua.chat.server.user.Chat;
import com.xiangbohua.chat.server.user.ChatSession;
import com.xiangbohua.chat.server.user.Friend;

/**
 * @author xiangbohua
 * @date 2020/8/17 18:09
 */
public class Server {

    LoginServer socketServer = new LoginServer(8189);


    public void start() {
        try {
            SysUser.loadUsers();
            Friend.loadUserFriends();
            Chat.loadUserChats();
            ChatSession.loadSessionInfo();

            this.socketServer.startServer();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("启动Server失败");
        }
    }


    private String processMessage(byte[] message) {
        String messageStr = new String(message);
        return "消息已发送，您发送的消息是：" + messageStr;
    }
}
