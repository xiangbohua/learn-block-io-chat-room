package com.xiangbohua.chat.client.tool;

import com.xiangbohua.chat.client.InputHandler;

import java.util.Scanner;

/**
 * @author xiangbohua
 * @date 2020/8/18 14:41
 */
public class Input {

    public void startInput(InputHandler handler) {
        String input = "";
        while (!"quit".equals(input)) {
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            input = handler.onInput(input);
        }
    }

}
