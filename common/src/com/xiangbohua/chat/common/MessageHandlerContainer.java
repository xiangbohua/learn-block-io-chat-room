package com.xiangbohua.chat.common;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiangbohua
 * @date 2020/8/17 18:04
 */
public class MessageHandlerContainer {

    public static Map<Integer, IMessageHandler> handlers = new HashMap<>();

    static {
        addHandler(1, new ChatMessageReceived());
        addHandler(2, new RequestLoginHandler());
        addHandler(3, new RequestNewPortHandler());
    }


    public static void addHandler(Integer type, IMessageHandler handler) {
        synchronized (handlers) {
            if (!handlers.containsKey(type)) {
                handlers.put(type, handler);
            }
        }
    }

    public static IMessageHandler getHandler(Integer type) {
        synchronized (handlers) {
            return handlers.get(type);
        }
    }

}

