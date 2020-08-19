package com.xiangbohua.chat.common.tool;

import java.io.*;
import java.util.*;

/**
 * @author xiangbohua
 * @date 2020/8/18 16:47
 */
public class StorageUtil {

    static String basePath = StorageUtil.class.getResource("/").getPath() + "storage";

    static {
        File file = new File(basePath);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    static String getRelatePath(String relatedPath) {
        return basePath + relatedPath;
    }

    public static List<String> loadAllLine(String relatedPath) throws IOException {
        File file = new File(getRelatePath(relatedPath));
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        }
        return Collections.emptyList();
    }

    public static void initPath(String path) {
        File file = new File(getRelatePath(path));
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static void appendLine(String relatedPath, String line) throws IOException {
        File file = new File(getRelatePath(relatedPath));
        if (!file.exists()) {
            file.createNewFile();
        }

        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        long length = randomAccessFile.length();
        randomAccessFile.seek(length);
        randomAccessFile.writeBytes(line+"\r\n");
        randomAccessFile.close();
    }

    public static List<File> allFileName(String relatedPath) {
        File file = new File(getRelatePath(relatedPath));
        List<File> files = Collections.emptyList();
        if (!file.exists()) {
            return files;
        }
        if (!file.isDirectory()) {
            return files;
        }
        if (Objects.isNull(file.listFiles())) {
            return files;
        }
        return Arrays.asList(file.listFiles());
    }

    public static void createOrFillFile(String relatedPath, String newContent) throws IOException {
        String filePath = getRelatePath(relatedPath);
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        DataOutputStream outputStream = new DataOutputStream(fileOutputStream);
        outputStream.writeUTF(newContent);
        outputStream.close();
        fileOutputStream.close();
    }
}
