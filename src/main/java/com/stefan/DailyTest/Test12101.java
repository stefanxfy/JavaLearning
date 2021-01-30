package com.stefan.DailyTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Test12101 {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
//        Class clazz = Class.forName("com.stefan.DailyTest.Son");
//        User user = (User) clazz.newInstance();
//        for (Field declaredField : clazz.getDeclaredFields()) {
//            declaredField.setAccessible(true);
//            declaredField.set(user, "son----");
//        }
//        Son son = (Son) user;
//        System.out.println(son.getName());
//        String[] arr = {"-f", "test", "3", "4"};
//        //copyOfRange  [)
//        for (String s : Arrays.copyOfRange(arr, 0, 2)) {
//            System.out.println(s);
//        }
        String s = "n%s: d1=%s;";
        double d1 = 12.0;
        System.out.println(String.format(s, "hhh", d1));
    }
}
