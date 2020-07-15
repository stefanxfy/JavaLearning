package com.stefan.designPattern.composite;

public abstract class CFileSystemNode {
    protected String path;

    public CFileSystemNode(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public abstract int countNumOfFiles();
    public abstract long countSizeOfFiles();
}
