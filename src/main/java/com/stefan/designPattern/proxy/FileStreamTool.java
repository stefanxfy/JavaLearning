package com.stefan.designPattern.proxy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileStreamTool implements Tool{

    @Override
    public void copy(String fromPath, String toPath) throws IOException {
        FileInputStream inputStream = new FileInputStream(fromPath);
        FileOutputStream outputStream = new FileOutputStream(toPath);
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
            outputStream.flush();
        }
        outputStream.close();
        inputStream.close();
        System.out.println("FileStreamTool::copy from " + fromPath + " to " + toPath + " ok !");
    }
}
