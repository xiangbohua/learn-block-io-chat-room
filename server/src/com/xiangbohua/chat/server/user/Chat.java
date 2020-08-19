package com.xiangbohua.chat.server.user;

import com.xiangbohua.chat.common.tool.StorageUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author xiangbohua
 * @date 2020/8/18 15:53
 */
public class Chat {

    private static String filePath = "/chats";

    private static Map<String, List<String>> userChats = new HashMap<>();

    static {
        StorageUtil.initPath(filePath);
    }
    public static Map<String, List<String>> getAllUserChats() {
        return userChats;
    }

    public static void loadUserChats() {
        List<File> files = StorageUtil.allFileName(filePath);

        for (File path : files) {
            String fileName = path.getName();
            String[] names = fileName.replace(".txt", "").split("_");
            if (names.length == 2) {
                try {
                    List<String> friends = getAllChats(names[1]);
                    userChats.put(names[1], friends);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String getFilePath(String userName) {
        return filePath + "/chat_" + userName + ".txt";
    }

    public static void newChat(String userName, String chatSessionId) throws IOException {

        List<String> oneUserChats = userChats.get(userName);

        if (Objects.isNull(oneUserChats)) {
            oneUserChats = new ArrayList<>();
            userChats.put(userName, oneUserChats);
        }
        String oldChat = oneUserChats.stream().filter(x->x.equals(chatSessionId)).findFirst().orElse(null);
        if (!Objects.isNull(oldChat)) {
            oneUserChats.add(chatSessionId);
            String fullPt = getFilePath(userName);
            StorageUtil.appendLine(fullPt, chatSessionId);
        }
    }

    public static List<String> getAllChats(String userName) throws IOException {
        String fullPt = getFilePath(userName);
        return StorageUtil.loadAllLine(fullPt);
    }

}
