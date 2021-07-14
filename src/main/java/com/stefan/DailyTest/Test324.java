package com.stefan.DailyTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class Test324 {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Son son = new Son();
        Method method = son.getClass().getMethod("setName", String.class);
        method.invoke(son, "stefan");
        System.out.println(son.getName());
        System.out.println(((Type)son.getClass()).getTypeName());
    }
}
