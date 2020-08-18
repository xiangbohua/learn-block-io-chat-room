package com.xiangbohua.chat.common;

/**
 * @author xiangbohua
 * @date 2020/8/17 17:58
 */
public interface IMessageHandler {

    void processMessage(byte[] msg);

}
