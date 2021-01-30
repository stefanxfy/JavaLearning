package com.stefan.DailyTest;

import fai.comm.util.Param;
import fai.comm.util.Str;

import java.util.*;

public class Test20200511 {
    public static void main(String[] args) {
        String matchRule = "命中[Mbps大于阈值]规则,判定值[2581.01],指标[Mbps],条件{>2500};";
        String[] ruleArr = matchRule.split(";");
        for (String rule : ruleArr) {
            String[] columnArr = rule.split(",");
            String metricName = null;
            double value = 0;
            for (String column : columnArr) {
                if (column.startsWith("指标")) {
                    System.out.println(column);
                    metricName = column.substring(column.indexOf("[") + 1, column.indexOf("]"));
                }
                if (column.startsWith("判定值")) {
                    value = Double.parseDouble(column.substring(column.indexOf("[") + 1, column.indexOf("]")));
                }
            }
            if (Str.isEmpty(metricName)) {
                continue;
            }
            System.out.println(metricName);
            System.out.println(value);
            System.out.println(rule);
        }
    }

    public static void handle() {
        try {
            if (pre(-1) != 0) {
                System.out.println("-----");
                return;
            }
            doing(0);
        } finally {
            post();
        }
    }

    public static int pre(int rt) {
        System.out.println("pre....");
        return rt;
    }

    public static int doing(int rt) {
        System.out.println("doing....");
        return rt;
    }

    public static void post() {
        System.out.println("post....");
    }
}
