package com.stefan.DailyTest;

public class Test0702 {
    public static void main(String[] args) {
/*        for (Class<?> anInterface : TestHandler.class.getInterfaces()) {
            System.out.println(anInterface);
        }
        for (Class<?> anInterface : TestHandler.class.getSuperclass().getInterfaces()) {
            System.out.println(anInterface);
        }
        System.out.println(TestHandler.class.getSuperclass().getSuperclass().equals(java.lang.Object.class));*/
        boolean isImplIJobHandler = false;
        Class clz = TestHandler.class;
        do {
            System.out.println("clz=" + clz.getName());
            Class<?>[] interfaces = clz.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                if (anInterface == IjobHandler.class) {
                    isImplIJobHandler = true;
                    break;
                }
            }
        } while (!(clz = clz.getSuperclass()).equals(Object.class));
        System.out.println("isImplIJobHandler=" + isImplIJobHandler);
    }
}
