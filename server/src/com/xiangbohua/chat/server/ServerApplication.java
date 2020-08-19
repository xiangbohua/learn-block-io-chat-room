package com.xiangbohua.chat.server;

import com.xiangbohua.chat.server.handler.HandlerContainer;

/**
 * @author xiangbohua
 * @date 2020/8/17 17:12
 */
public class ServerApplication {
    public static void main(String[] args) {
        HandlerContainer.warmUp();

        Server server = new Server();
        server.start();
    }
}

