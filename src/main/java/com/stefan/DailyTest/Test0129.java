package com.stefan.DailyTest;

import java.util.*;

public class Test0129 {
    public static void main(String[] args) {
        int i = 0;
        if (++i > 0) {
            System.out.println("++i < 2");
        } else if (i == 1) {
            System.out.println(i);
        }

//        test1();
//        test();
        Son son = new Son();
        System.out.println(Integer.toBinaryString(son.hashCode()).length());
        System.out.println(son.hashCode());

    }

    private static void test1() {
        Set<String> wordSet = new HashSet<String>();
        wordSet.add("产品经理");
        wordSet.add("产品总监");
        wordSet.add("程序员");
        Map map= init(wordSet);
        System.out.println(map);
    }

    private static void test() {
        GfwKWFilter gfwKWFilter = new GfwKWFilter();
        Set<String> wordSet = new HashSet<String>();
        wordSet.add("产品经理");
        wordSet.add("产品总监");
        wordSet.add("程序员");
        gfwKWFilter.init(wordSet);
//        Set<String> set = gfwKWFilter.getWord("中国好哈哈哈哈哈人-hhhc", 2);
        Set<String> set = gfwKWFilter.getWord("中非人", 2);

        System.out.println(set);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map init(Set<String> words) {
        Map wordMap = new HashMap(words.size());
        for (String word : words) {
            Map nowMap = wordMap;
            for (int i = 0; i < word.length(); i++) {
                // 转换成char型
                char keyChar = word.charAt(i);
                // 获取
                Object tempMap = nowMap.get(keyChar);
                // 如果存在该key，直接赋值
                if (tempMap != null) {
                    nowMap = (Map) tempMap;
                }
                // 不存在则，则构建一个map，
                else {
                    // 设置标志位
                    Map<String, Integer> newMap = new HashMap<String, Integer>();
                    newMap.put("isEnd", i == word.length() - 1 ? 1 : 0);
                    // 添加到集合
                    nowMap.put(keyChar, newMap);
                    nowMap = newMap;
                }
            }
        }
        return wordMap;
    }

    public static Map reInit2(Set<String> words) {
        Map wordMap = new HashMap(words.size());
        for (String word : words) {
            Map nowMap = wordMap;
            for (int i = 0; i < word.length(); i++) {
                // 转换成char型
                char keyChar = word.charAt(i);
                Map<String, Integer> newMap = new HashMap<String, Integer>();
                newMap.put("isEnd", i == word.length() - 1 ? 1 : 0);
                // 添加到集合
                nowMap.put(keyChar, newMap);
                nowMap = newMap;
//                System.out.println("wordMap=" + wordMap);
            }
        }
        return wordMap;
    }
}
