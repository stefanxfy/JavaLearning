package com.stefan.DailyTest;

import java.io.File;
import java.io.IOException;

public class Test0612 {
    public static void main(String[] args) throws IOException {
        File file = new File("../LocalStrings_zh_CN.properties");
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getPath());
        System.out.println(file.getCanonicalFile().getPath());

        String userDir = System.getProperty("user.dir");
        System.out.println(userDir);
    }
}
