package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileOperation {
    public static List<File> traverseFolder(String path) {
        List<File> fileList = new ArrayList<File>();
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return fileList;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        traverseFolder(file2.getAbsolutePath());
                    } else {
                        fileList.add(file2);
                        // System.out.println("文件:" + file2.getAbsolutePath());
                    }
                }
            }        } else {
            System.out.println("文件不存在!");
        }
        return fileList;
    }
}
