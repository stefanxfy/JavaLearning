package com.stefan.DailyTest;

import cn.hutool.core.util.ReUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test16 {
    public static void main(String[] args) {
        String s = "\"Gang worker#0 (Parallel GC Threads)\" os_prio=0 tid=0x00007fb5fc01c000 nid=0x36592 runnable\n" +
                "++++++++++++++++++++ pid=222611;hex=0x36593;cpu=0.0 ++++++++++++++++++++\n" +
                "\"Gang worker#1 (Parallel GC Threads)\" os_prio=0 tid=0x00007fb5fc01e000 nid=0x36593 runnable\n" +
                "++++++++++++++++++++ pid=222612;hex=0x36594;cpu=0.0 ++++++++++++++++++++\n";
        System.out.println(TimeUnit.NANOSECONDS.convert(1, TimeUnit.MILLISECONDS));
//        System.out.println(String.format("ssh faier@%s -p 50805 'bash -l -c \" python /home/faier/script/oss/tool/jstackAnalyzer.py -p %d \" '", "serv18.faisvr.cc", 123819));

        String line = "\"xfy\",\"stefan\",";
        //("([^"]*)")
        //                    line.replace(s, "<font color=\"#FF00FF\">" + s + "</font>");
        List<String> matchStr = ReUtil.findAll("(\"([^\"]*)\")", s, 0, new ArrayList<String>());
        for (String s1 : matchStr) {
            s = s.replace(s1, "<font color=\"#FF00FF\">" + s1 + "</font>");
        }
        System.out.println(s);

        System.out.println();
    }
}
