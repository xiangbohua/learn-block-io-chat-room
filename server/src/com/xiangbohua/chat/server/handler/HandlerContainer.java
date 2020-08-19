package com.xiangbohua.chat.server.handler;


import com.xiangbohua.chat.common.tool.ClassUtil;

import java.util.*;

/**
 * @author xiangbohua
 * @date 2020/8/17 18:04
 */
public class HandlerContainer {

    public static Map<String, IMessageHandler> handlers = new HashMap<>();

    public static void warmUp() {
        synchronized (handlers) {
            String lookupPackage = "com.xiangbohua.chat.server.handler";
            List<String> classNames = ClassUtil.getClassName(lookupPackage);
            for (String cName : classNames) {
                try {
                    cName = cName.replace('/', '.');
                    cName = cName.substring(cName.indexOf(lookupPackage));
                    Class c = Class.forName(cName);
                    if (Arrays.stream(c.getInterfaces()).anyMatch(x->"IMessageHandler".equals(x.getSimpleName()))) {
                        IMessageHandler handler = (IMessageHandler)c.newInstance();
                        addHandler(handler.getHandlerType(), handler);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<IMessageHandler> getAllHandlers() {
        return new ArrayList<>(handlers.values());
    }

    public static void addHandler(String type, IMessageHandler handler) {
        synchronized (handlers) {
            if (!handlers.containsKey(type)) {
                handlers.put(type, handler);
            }
        }
    }

    public static IMessageHandler getHandler(String type) {
        if (type == null) {
            return null;
        }
        synchronized (handlers) {
            return handlers.get(type);
        }
    }

    public static IMessageHandler getHandler(byte[] msgByte) {
        return HandlerContainer.getHandler(com.xiangbohua.chat.common.tool.MessageUtil.getAction(msgByte));
    }
}

