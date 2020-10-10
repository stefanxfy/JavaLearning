package com.stefan.DailyTest;

import cn.hutool.core.util.ReUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Test1010 {
    public static void main(String[] args) {
        GfwKWFilter gfwKWFilter = new GfwKWFilter();
        Set<String> stringSet = new HashSet<>();
        stringSet.add("世界第一");
        stringSet.add("世界");
        stringSet.add("界");

        gfwKWFilter.init(stringSet);
        String s = "我是世界第，谁是世界第一";
        System.out.println(gfwKWFilter.getWord(s, 2));
        String kw = "世|世界第一|世界|我是";
    }
}
