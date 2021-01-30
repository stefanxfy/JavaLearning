package com.stefan.DailyTest;

import java.lang.reflect.Field;

public class Test1210 {
    public static void main(String[] args) {
        try {
            Class clazzUser = Class.forName("java_reflex.User");
            Field[] fields = clazzUser.getDeclaredFields();
            for (Field field : fields) {
//                field.getAnnotation()
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
