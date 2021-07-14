package com.stefan.DailyTest;

import com.google.gson.Gson;

public class MyInheritableThreadLocal<T> extends InheritableThreadLocal<T>{
    protected T childValue(T parentValue) {
        System.out.println("MyInheritableThreadLocal。。。");
        // 深拷贝
        Gson gson = new Gson();
        String s = gson.toJson(parentValue);
        return (T)gson.fromJson(s, parentValue.getClass());
    }
}
