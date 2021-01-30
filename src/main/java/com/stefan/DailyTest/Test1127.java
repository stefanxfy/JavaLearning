package com.stefan.DailyTest;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Test1127 {
    public static void main(String[] args) {
/*        Throwable thb = new Throwable();
        StackTraceElement[] stacks = thb.getStackTrace();
        StackTraceElement elem = stacks[0];
        String className = elem.getClassName();
        String methodName = elem.getMethodName();
        int lineNumber = elem.getLineNumber();
        System.out.println("className:" + className);
        System.out.println("methodName:" + methodName);
        System.out.println("lineNumber:" + lineNumber);*/
//
//        AbstractUser user = new AbstractUser();
//        user.play();
//        System.out.println(HandleResultEnum.FAIL.toString().equals(new String("FAIL")));
/*        List<String> list = new ArrayList<String>();
        list.add("123");

        list.add(0, "234");
        System.out.println(list);*/

        ByteBuffer headBuf = ByteBuffer.allocate(32);
        headBuf.putShort((short)32);
        headBuf.putShort((short)1);
        headBuf.putShort((short)3);
        headBuf.putShort((short)2);
        headBuf.putInt(4);

        for (byte b : headBuf.array()) {
            System.out.println(b);
        }
        int d = (int) Math.ceil(19 / 10D);
        System.out.println(d);

        int i = 111;
        long ii = (long)i;
        System.out.println(ii);

    }
}
