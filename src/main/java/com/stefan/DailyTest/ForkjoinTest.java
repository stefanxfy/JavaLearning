package com.stefan.DailyTest;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForkjoinTest {
    public static void main(String[] args) {
        String s = "18814129392@139.com,634686817@qq.com,815360296@qq.com,765788057@qq.com,13570921760@139.com,15920384644@139.com,307793000@qq.com,625033413@qq.com,865819002@qq.com,785562850@qq.com";
        for (String s1 : s.split(",")) {
            System.out.println(s1 +"=" + isEmail(s1));
        }

/*        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(new RecursiveAction() {
            @Override
            protected void compute() {

            }
        });*/
    }

    public static boolean isEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile(
                "^[a-zA-Z0-9][a-zA-Z0-9_=\\&\\-\\.\\+]*[a-zA-Z0-9]*@[a-zA-Z0-9][a-zA-Z0-9_=\\-\\.]+[a-zA-Z0-9]$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }
}
