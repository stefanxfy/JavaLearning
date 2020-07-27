package com.stefan.DailyTest;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;

public class Test20200610 {
    public void clear(List list) {
        list.clear();
    }
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("vcsvdskvndkl");
        list.add("fncsfufhuwenvd");
        Test20200610 test20200610 = new Test20200610();
//        test20200610.clear(list);
        System.out.println(list.size());
    }
}
