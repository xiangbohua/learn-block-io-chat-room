package com.xiangbohua.chat.client;

import com.xiangbohua.chat.client.handler.HandlerContainer;
import com.xiangbohua.chat.client.handler.IMessageHandler;
import com.xiangbohua.chat.client.socket.SocketClient;
import com.xiangbohua.chat.client.sys.ClientState;
import com.xiangbohua.chat.client.tool.Conf;
import com.xiangbohua.chat.client.tool.Input;
import com.xiangbohua.chat.common.tool.Output;

import java.io.IOException;

/**
 * @author xiangbohua
 * @date 2020/8/18 10:02
 */
public class ClientApplication {
    public static void main(String[] args) {
        HandlerContainer.warmUp();

        Input inputScanner = new Input();
        inputScanner.setHandler(x->{
            SocketClient loginClient = new SocketClient(Conf.getConf("server"), Conf.getIntConf("port"));
            String[] loginPara = x.split(" ");
            if (loginPara.length != 3) {
                return "Please type your action and parameters!";
            }
            String action = loginPara[0];
            String userName = loginPara[1];
            String pass = loginPara[2];
            try {
                loginClient.connect();
            } catch (IOException e) {

            } catch (Exception e) {
                return "Login failed:" + e.getMessage();
            }
            try {
                IMessageHandler handler = HandlerContainer.getHandler(action);
                String data = handler.buildCommand(userName, pass);
                String result = loginClient.sendData(data.getBytes(), handler);
                loginClient.close();

                return result;
            } catch (IOException e) {
                e.printStackTrace();
                return "Login failed:" + e.getMessage();
            }
        });
        Output.showMessage("Using login [name] [pass] to login!");
        Output.showMessage("Or Using reg [name] [pass] to register!");
        inputScanner.startInput();

        Input messageInput = new Input();

        messageInput.setHandler(x->{
            SocketClient loginClient = new SocketClient(Conf.getConf("server"), ClientState.getUserPort());
            String[] loginPara = x.split(" ");
            if (loginPara.length == 0) {
                return "Please type your action and parameters!";
            }
            String action = loginPara[0];
            try {
                loginClient.connect();
            } catch (IOException e) {

            } catch (Exception e) {
                return "Login failed:" + e.getMessage();
            }
            try {
                IMessageHandler handler = HandlerContainer.getHandler(action);
                String data = handler.buildCommand(loginPara);
                return loginClient.sendData(data.getBytes(), handler);
            } catch (IOException e) {
                e.printStackTrace();
                return "Login failed:" + e.getMessage();
            }
        });
        messageInput.startInput();
    }





}
