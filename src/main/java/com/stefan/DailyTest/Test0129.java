package com.stefan.DailyTest;

import java.util.*;

public class Test0129 {
    public static void main(String[] args) {
//        test1();
        test();
    }

    private static void test1() {
        Set<String> words = new HashSet<>();
        words.add("中国人");
        words.add("中人");
        words.add("中间");
        words.add("人民");
        Map map= reInit(words);
        System.out.println(map);
        Map map2= reInit2(words);
//        System.out.println(map2);
    }

    private static void test() {
        GfwKWFilter gfwKWFilter = new GfwKWFilter();
        Set<String> words = new HashSet<>();
        words.add("中人-x");
        words.add("中*人");
        words.add("cd");
        gfwKWFilter.init(words);
//        Set<String> set = gfwKWFilter.getWord("中国好哈哈哈哈哈人-hhhc", 2);
        Set<String> set = gfwKWFilter.getWord("中国x人", 2);

        System.out.println(set);
    }

    public static Map reInit(Set<String> words) {
        Map wordMap = new HashMap(words.size());
        for (String word : words) {
            Map nowMap = wordMap;
            System.out.println("---关键词: " + word);
            for (int i = 0; i < word.length(); i++) {
                // 转换成char型
                char keyChar = word.charAt(i);
                System.out.println("keyChar: " + keyChar);
                // 获取
                Object tempMap = nowMap.get(keyChar);
                // 如果存在该key，直接赋值
                if (tempMap != null) {
                    nowMap = (Map) tempMap;
                    System.out.println("already existed keyChar: " + keyChar) ;
                    System.out.println("nowMap:  " + nowMap);
                }
                // 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                else {
                    // 设置标志位
                    Map<String, String> newMap = new HashMap<String, String>();
                    newMap.put("isEnd", "0");
                    // 添加到集合
                    nowMap.put(keyChar, newMap);
                    System.out.println("nowMap:  " + nowMap);
                    nowMap = newMap;
                }
                // 最后一个
                if (i == word.length() - 1) {
                    nowMap.put("isEnd", "1");
                }
                System.out.println("nowMap:  " + nowMap);
                System.out.println("wordMap: " + wordMap);
                System.out.println("---");
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
