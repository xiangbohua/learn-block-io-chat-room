package com.xiangbohua.chat.server.common;

import java.util.Collections;
import java.util.List;

/**
 * @author xiangbohua
 * @date 2020/8/18 15:58
 */
public class ChatHistory {

    public static void appendHistory(String sessionId, ChatMessage message) {

    }

    public static List<ChatMessage> loadHistory(String sessionId, int loadCount) {
        return Collections.emptyList();
    }

}
