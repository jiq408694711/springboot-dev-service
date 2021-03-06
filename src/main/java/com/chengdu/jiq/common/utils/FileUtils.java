package com.chengdu.jiq.common.utils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by jiyiqin on 2017/9/17.
 */
public class FileUtils {

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}
