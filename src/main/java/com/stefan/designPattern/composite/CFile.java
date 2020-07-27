package com.stefan.designPattern.composite;

import java.io.File;

public class CFile extends CFileSystemNode{
    public CFile(String path) {
        super(path);
    }

    @Override
    public int countNumOfFiles() {
        return 1;
    }

    @Override
    public long countSizeOfFiles() {
        File file = new File(path);
        System.out.println(path);
        if (file.exists()) {
            return file.length();
        }
        return 0;
    }
}
