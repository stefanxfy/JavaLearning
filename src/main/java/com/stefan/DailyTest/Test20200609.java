package com.stefan.DailyTest;

public class Test20200609 {
    public static void main(String[] args) {
        String s = "sss\\nsdsss\\nfsddfwswd";
        System.out.println(s.split("\n").length);
        System.out.println(s.split("\\\\n").length);

    }
}
