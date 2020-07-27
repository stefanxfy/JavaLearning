package com.stefan.designPattern.proxy;

import java.io.IOException;

public class FileStreamToolProxy2 extends FileStreamTool {

    @Override
    public void copy(String fromPath, String toPath) throws IOException {
        long s = System.currentTimeMillis();
        super.copy(fromPath, toPath);
        long e = System.currentTimeMillis();
        System.out.println("copy cost time=" + (e - s));
    }
}
