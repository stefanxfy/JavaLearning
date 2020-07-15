package com.stefan.designPattern.composite;

import java.util.ArrayList;
import java.util.List;

public class CDirectory extends CFileSystemNode{
    private List<CFileSystemNode> nodeList = new ArrayList<CFileSystemNode>();
    public CDirectory(String path) {
        super(path);
    }

    @Override
    public int countNumOfFiles() {
        int num = 0;
        for (CFileSystemNode cFileSystemNode : nodeList) {
            num += cFileSystemNode.countNumOfFiles();
        }
        return num;
    }

    @Override
    public long countSizeOfFiles() {
        int size = 0;
        for (CFileSystemNode cFileSystemNode : nodeList) {
            size += cFileSystemNode.countSizeOfFiles();
        }
        return size;
    }

    public void addSubNode(CFileSystemNode fileOrDir) {
        nodeList.add(fileOrDir);
    }

    public void removeSubNode(CFileSystemNode fileOrDir) {
        int size = nodeList.size();
        int i = 0;
        for (; i < size; ++i) {
            if (nodeList.get(i).getPath().equalsIgnoreCase(fileOrDir.getPath())) {
                break;
            }
        }
        if (i < size) {
            nodeList.remove(i);
        }
    }
}
