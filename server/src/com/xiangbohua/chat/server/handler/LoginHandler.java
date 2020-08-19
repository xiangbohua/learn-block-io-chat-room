package com.xiangbohua.chat.server.handler;

import com.xiangbohua.chat.common.tool.MessageUtil;
import com.xiangbohua.chat.server.SystemState;
import com.xiangbohua.chat.server.common.Helper;
import com.xiangbohua.chat.server.socket.MessageServer;
import com.xiangbohua.chat.server.sys.SysUser;

/**
 * @author xiangbohua
 * @date 2020/8/17 18:03
 */
public class LoginHandler implements IMessageHandler {

    @Override
    public String getHandlerType() {
        return "login";
    }

    @Override
    public byte[] handlerMessage(byte[] messageBytes) {
        byte[] resultPort;
        try {

            String[] split = MessageUtil.splitMsg(messageBytes);

            checkMessage(split, 3);
            String userName = split[1];
            String pass = split[2];

            SysUser.login(userName, pass);

            if (!SystemState.hasUserMessagePort(userName)) {
                MessageServer server = this.createMessageServerForUser();
                SystemState.putUserServer(userName, server);
                server.startServer();
            }
            resultPort = (SystemState.getUserServerPort(userName) + ":login  succeed").getBytes();
        } catch (Exception ex) {
            ex.printStackTrace();
            resultPort = ("-1" + ":" + ex.getMessage()).getBytes();
        }
        return resultPort;
    }

    @Override
    public String getHelp() {
        return null;
    }


    private MessageServer createMessageServerForUser() {
        int port = Helper.getNewPort();

        int retryCount = 0;
        int maxRetryCount = 3;

        while (Helper.isPortUsing(port) && retryCount <  maxRetryCount) {
            port = Helper.getNewPort();
            retryCount ++;
        }
        MessageServer server = new MessageServer(port);
        return server;
    }

}
