package com.stefan.DailyTest;

import cn.hutool.core.util.ReUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test17 {
    public static void main(String[] args) throws IOException {
/*        String jstackStoragePath   = "D:/tmp/mc_jstack/FaiSessionSvr/serv2.faisvr.cc/20200901/1598943601.jstack";
        File file = new File("D:/tmp/mc_jstack/FaiSessionSvr/serv2.faisvr.cc/20200901/1598943601.jstack");
        String dirpath = jstackStoragePath.substring(0, jstackStoragePath.lastIndexOf("/"));
        File dir = new File(dirpath);
        if (!dir.exists()) {
            System.out.println(dir.mkdirs());
        }
        file.createNewFile();*/
        File dir = new File("D:/tmp/mc_jstack/FaiSessionSvr/serv2.faisvr.cc");
        List<File> list = Arrays.asList(dir.listFiles());
        Collections.sort(list);
        for (File s : list) {
            System.out.println("----");
            System.out.println(s.getPath());
        }
/*
        List<String> list = new ArrayList<String>();
        list.add("16:10:11");
        list.add("15:10:11");
        list.add("16:11:33");
        list.add("14:10:11");
        list.add("16:11:11");
        list.add("16:11:13");
        Collections.sort(list);
        System.out.println(list);
        String line = "\"Gang worker#0 (Parallel GC Threads)\" os_prio=0 tid=0x00007fb5fc01c000 nid=0x36592 runnable";
        List<String> reList = ReUtil.findAll("(\"([^\"]*)\")", line, 0, new ArrayList<String>());
        System.out.println(reList);
        if (reList != null && !reList.isEmpty()) {
            for (String s : reList) {
                String ss = "<font color=\"fjdfjl\">" + s + "</font>";
                line = line.replaceAll(s, ss);
                CharSequence sequence = s;
                System.out.println(sequence);
            }
        }
*//*        int index = line.indexOf("#");
        if (index >= 0) {
            String sub = line.substring(index);
            System.out.println("sub::" + sub);
            line = line.replace(sub, "<font color=\"#0066FF\">" + sub + "</font>");
        }*//*
        line = line + "<br>";
        System.out.println(line);
        System.out.println(list);
        System.out.println(list.subList(2, list.size()));*/
    }
}
