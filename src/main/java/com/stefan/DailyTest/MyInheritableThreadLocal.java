package com.stefan.DailyTest;


public class MyInheritableThreadLocal<T> extends InheritableThreadLocal<T>{
    @Override
    protected T childValue(T parentValue) {
        System.out.println("MyInheritableThreadLocal。。。");
/*        // 深拷贝
        Gson gson = new Gson();
        String s = gson.toJson(parentValue);
        return (T)gson.fromJson(s, parentValue.getClass());*/
        return null;
    }
}
