package com.xiangbohua.chat.server.common;

import java.time.LocalDateTime;

/**
 * @author xiangbohua
 * @date 2020/8/18 15:44
 */
public class ChatMessage {

    public String sessionId;

    public String sendUser;

    public String contentType;

    public boolean hasRead;

    public byte[] content;

    public LocalDateTime createdAt;

}
