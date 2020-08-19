package com.xiangbohua.chat.server.sys;

import com.xiangbohua.chat.common.tool.StorageUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiangbohua
 * @date 2020/8/18 16:47
 */
public class SysUser {
    static String filePath = "/sys";
    static String fileName = "/users.txt";

    private final static Map<String, String> allUser = new HashMap<>();

    static {
        StorageUtil.initPath(filePath);
    }


    public static void loadUsers() {
        synchronized (allUser) {
            try {
                List<String> users = StorageUtil.loadAllLine(filePath + fileName);
                users.forEach(x->{
                    String[] s = x.split(" ");
                    if (s.length == 2) {
                        allUser.put(s[0], s[1]);
                    }
                });

            } catch (IOException e) {
                System.out.println("Error occurred when load users");
                e.printStackTrace();
            }
        }
    }


    public static void regUser(String userName, String pass) throws IOException {
        userName = userName.trim();
        pass = pass.trim();
        synchronized (allUser) {
            if (!allUser.containsKey(userName)) {
                try {
                    StorageUtil.appendLine(filePath + fileName, userName + " " + pass);
                    allUser.put(userName, pass);
                } catch (IOException e) {
                    System.out.println("Error occurred when register new user:" + userName);
                    e.printStackTrace();
                    throw e;
                }
            }
        }
    }

    public static void login(String userName, String pass) throws Exception {
        userName = userName.trim();
        pass = pass.trim();
        if (allUser.containsKey(userName)) {
            String savedPass = allUser.get(userName);
            if (!savedPass.equals(pass)) {
                throw new Exception("Passport error");
            }
        } else {

            throw new Exception("No registered user.");
        }
    }

    public static boolean checkExisted(String userName) {
        return allUser.containsKey(userName);
    }


}
