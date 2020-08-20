package com.xiangbohua.chat.client.tool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author xiangbohua
 * @date 2020/8/19 17:54
 */
public class Conf {
    static Properties pro;
    static {
        pro = new Properties();
        try {
            File file = new File("/config.properties");
            InputStream in = Conf.class.getClassLoader().getResourceAsStream(file.getName());
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getConf(String confKey) {
        return pro.getProperty(confKey);
    }

    public static int getIntConf(String confKey) {
        String c = pro.getProperty(confKey);
        return Integer.parseInt(c);
    }

}
