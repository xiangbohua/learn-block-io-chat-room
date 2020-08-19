package com.xiangbohua.chat.server.msg;

import com.xiangbohua.chat.common.tool.ClassUtil;
import com.xiangbohua.chat.server.common.MessageType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiangbohua
 * @date 2020/8/19 11:36
 */
public class MessageSenderContainer {

    private static Map<String, IMessageSender> senders = new HashMap<>();

    static {
        String lookupPackage = "com.xiangbohua.chat.server.msg";
        List<String> classNames = ClassUtil.getClassName(lookupPackage);
        for (String cName : classNames) {
            try {
                cName = cName.replace('/', '.');
                cName = cName.substring(cName.indexOf(lookupPackage));
                Class c = Class.forName(cName);
                if (Arrays.stream(c.getInterfaces()).anyMatch(x->"IMessageSender".equals(x.getSimpleName()))) {
                    IMessageSender handler = (IMessageSender)c.newInstance();
                    addSender(handler.supportType().toString(), handler);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }

    }

    public static IMessageSender getByType(MessageType type) {
        if (senders.containsKey(type.toString())) {
            return senders.get(type.toString());
        }
        return null;
    }

    public static void addSender(String type, IMessageSender sender) {
        if (!senders.containsKey(type)) {
            senders.put(type, sender);
        }
    }
}
