package com.stefan.DailyTest;

public class Test1024 {
    public static void main(String[] args) {
        while (true) {
            try {
                String s = "zj";
                int i = Integer.parseInt(s);
            } catch (Exception e) {
                throw e;
            } finally {
                System.out.println("----");
            }
        }
    }
}
