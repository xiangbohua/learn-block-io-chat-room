package com.xiangbohua.chat.client;

import com.xiangbohua.chat.client.handler.HandlerContainer;
import com.xiangbohua.chat.client.handler.IMessageHandler;
import com.xiangbohua.chat.client.socket.SocketClient;
import com.xiangbohua.chat.client.tool.Input;
import com.xiangbohua.chat.client.tool.Output;
import com.xiangbohua.chat.common.tool.MessageUtil;

import java.io.IOException;

/**
 * @author xiangbohua
 * @date 2020/8/18 10:02
 */
public class ClientApplication {
    public static void main(String[] args) {

        Input inputScanner = new Input();
        inputScanner.setHandler(x->{
            SocketClient loginClient = new SocketClient(8189);
            String[] loginPara = x.split(" ");
            if (loginPara.length != 2) {
                return "Please type your name and pass!";
            }
            String userName = loginPara[0];
            String pass = loginPara[1];
            try {
                loginClient.connect();
            } catch (IOException e) {

            } catch (Exception e) {
                return "Login failed:" + e.getMessage();
            }
            try {
                return loginClient.sendData(MessageUtil.buildMsg("login", userName, pass).getBytes(), HandlerContainer.getHandler("login"));
            } catch (IOException e) {
                e.printStackTrace();
                return "Login failed:" + e.getMessage();
            }
        });
        Output.showMessage("Using [name] [pass] to login!");
        inputScanner.startInput();
    }





}
