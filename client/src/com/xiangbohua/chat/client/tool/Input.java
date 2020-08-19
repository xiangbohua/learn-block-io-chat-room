package com.xiangbohua.chat.client.tool;

import com.xiangbohua.chat.client.inp.InputHandler;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author xiangbohua
 * @date 2020/8/18 14:41
 */
public class Input {

    private InputHandler handler;

    public void setHandler(InputHandler newHandler) {
        this.handler = newHandler;
    }

    public void startInput() {
        String input = "";
        String quitFlag = "quit";
        Scanner scanner = new Scanner(System.in);
        while (!quitFlag.equals(input)) {
            try {
                input = scanner.nextLine();

                if ("".equals(input.trim())) {
                    continue;
                }

                if (quitFlag.equals(input)) {
                    break;
                }
                input = handler.onInput(input);
                if (input != null && input != "") {
                    Output.showMessage(input);
                }
                if (quitFlag.equals(input)) {
                    break;
                }
            } catch (NoSuchElementException ex) {
            }
        }
        Output.showMessage("Bye Bye");
    }

}
