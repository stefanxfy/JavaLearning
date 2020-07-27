package com.stefan.DailyTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test20200511 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        for (int i = 0; i < list.size(); i+=3) {
            int endIndex = (i + 3) > list.size() ? list.size() : (i + 3);
            List<Integer> aids = list.subList(i, endIndex);
            System.out.println(aids);
        }
        System.out.println(list.subList(7, 11));

    }
}
