package com.xiangbohua.chat.server.user;

import com.xiangbohua.chat.common.tool.StorageUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiangbohua
 * @date 2020/8/18 18:36
 */
public class Friend {
    private static String filePath = "/friends";

    private static Map<String, List<String>> userFriends = new HashMap<>();

    static {
        StorageUtil.initPath(filePath);
    }

    public static void loadUserFriends() {
        List<File> files = StorageUtil.allFileName(filePath);

        for (File path : files) {
            String fileName = path.getName();
            String[] names = fileName.replace(".txt", "").split("_");
            if (names.length == 2) {
                try {
                    List<String> friends = getAllFriends(names[1]);
                    userFriends.put(names[1], friends);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



    }

    private static String getFilePath(String userName) {
        return filePath+ "/friends_"+userName+".txt";
    }

    public static void addFriends(String userName, String newFriends) throws IOException {
        String fullPt = getFilePath(userName);
        List<String> allFriends = getAllFriends(userName);
        if (allFriends.stream().anyMatch(x->x.equals(newFriends))) {
            return;
        }
        StorageUtil.appendLine(fullPt, newFriends);
    }

    public static List<String> getAllFriends(String userName) throws IOException {
        String fullPt = getFilePath(userName);
        return StorageUtil.loadAllLine(fullPt);
    }
}
