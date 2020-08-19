package com.xiangbohua.chat.client.inp;

/**
 * @author xiangbohua
 * @date 2020/8/18 14:41
 */
@FunctionalInterface
public interface InputHandler {
    String onInput(String inputString);
}
