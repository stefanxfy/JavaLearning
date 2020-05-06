package com.stefan.designPattern.proxy;

import java.io.*;

public class BufferFileTool implements Tool{
    @Override
    public void copy(String fromPath, String toPath) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fromPath));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(toPath));
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = bis.read(bytes)) != -1) {
            bos.write(bytes, 0 , len);
            bos.flush();
        }
        bos.close();
        bis.close();
        System.out.println("BufferFileTool::copy from " + fromPath + " to " + toPath + " ok !");

    }
}
