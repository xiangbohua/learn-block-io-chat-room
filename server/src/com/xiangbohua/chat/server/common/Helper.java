package com.xiangbohua.chat.server.common;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;

/**
 * @author xiangbohua
 * @date 2020/8/18 11:42
 */
public class Helper {

    public static int getNewPort() {
        Random ra = new Random();
        int port = ra.nextInt(20000);
        return port;
    }

    public static boolean isPortUsing(int port) {
        try {
            Socket socket = new Socket((String)null, port);
            socket.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
}
