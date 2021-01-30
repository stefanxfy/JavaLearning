package com.stefan.DailyTest;

public class Test1023 {
    public static void main(String[] args) {
//        FaiList<String> list = new FaiList<String>();
//        list.add("1");
//        System.out.println(isListEmpty(list));
//
//        String m = "monitor.faisco.biz";
//        System.out.println(m.endsWith(".faisco.biz"));
//        int gap = 60;
//        System.out.println(1604045158 % 5*gap);
        String s = "tomcat-itils";
        System.out.println(s.substring("tomcat-".length()));

    }

    public static boolean isListEmpty(FaiList<? extends Object> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }
}
