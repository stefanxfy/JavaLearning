package com.stefan.annotationLearning;

import java.lang.reflect.Field;

public class Parser {
    public static  void parser1() {
        User u = new User();
        u.setName("zsf");
        try {
            Class userClass = Class.forName("com.stefan.annotationLearning.User");
            Class lcClass = Class.forName("com.stefan.annotationLearning.LengthCheck");
            Field[] fields = userClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(lcClass)) {
                    LengthCheck lc = (LengthCheck) field.getAnnotation(lcClass);
                    field.setAccessible(true);
                    String str = (String) field.get(u);
                    System.out.println(str);
                    int length = str.length();
                    if (lc.max() >= length && lc.min() <= length) {
                        System.out.println("字符串长度符合要求");
                    } else {
                        System.out.println("长度为" + lc.min() + "~" + lc.max());
                    }

                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public static  void parser2() {
        try {
            Class dataAnn = Class.forName("com.stefan.annotationLearning.Data");
            Class stuCls = Student.class;
            if (stuCls.isAnnotationPresent(dataAnn)) {
                Data data = (Data) stuCls.getAnnotation(dataAnn);
                System.out.println(data.vlaue());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        parser1();
    }
}
