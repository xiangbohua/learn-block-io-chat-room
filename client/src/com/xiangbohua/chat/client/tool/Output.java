package com.xiangbohua.chat.client.tool;

/**
 * @author xiangbohua
 * @date 2020/8/19 14:08
 */
public class Output {
    public static void showMessage(String message) {
        System.out.println(message);
    }
    public static void newLine() {
        System.out.println("\r\n");
    }

    public static void showMessage(int leftBlank, String message) {
        StringBuilder paddingBland = new StringBuilder();
        for (int i = 0; i < leftBlank; i++) {
            paddingBland.append(" ");
        }
        System.out.println(paddingBland.append(message));
    }
}
