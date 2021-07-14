package com.stefan.DailyTest;

import java.io.FileInputStream;
import java.io.IOException;

public class Test068 {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("C:\\study\\myStudy\\JavaLearning\\src\\main\\java\\com\\stefan\\DailyTest\\LocalStrings_zh_CN.properties");
        byte[] bytes = new byte[1024];
        int len = 0 ;
        StringBuilder stringBuilder = new StringBuilder();
        while ((len=inputStream.read(bytes)) != -1) {
            String str = new String(bytes, 0, len);
            stringBuilder.append(str);
            System.out.println(getEncoding(str));
//            break;

        }
        String s = stringBuilder.toString();
        System.out.println(getEncoding(s));
    }


    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是GB2312
                String s = encode;
                return s; //是的话，返回“GB2312“，以下代码同理
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是ISO-8859-1
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是UTF-8
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是GBK
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "未知";
    }
}
