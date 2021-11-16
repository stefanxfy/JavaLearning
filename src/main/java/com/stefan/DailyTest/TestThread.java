package com.stefan.DailyTest;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author stefan
 * @date 2021/8/24 17:53
 */
public class TestThread {
    public static void main(String[] args) throws Exception {
        String s = "http://gfw.aaa.cn/?serviceTicket=st-08a49238-e3c8-4ace-a305-e7e9c2f0e242#/home/keyWordContactDetails";
        int i = s.indexOf("?serviceTicket");
        System.out.println(s.indexOf("?serviceTicket"));
        System.out.println(s.substring(0, i));

        List<String> list = new ArrayList<>();
        list.add("AtuGroupSvr");
        list.add("WXAstOssSvr");
        list.add("GtuGroupSvr");
        list.add("ZtuGroupSvr");
        Collections.sort(list);
        System.out.println(list);
        Map<String, String> map = new TreeMap<>();
        for (String s1 : list) {
            map.put(s1, s1);
        }
        for (String s1 : map.keySet()) {
            System.out.println(s1);
        }

    }
}
