package jvmLearning;

public class ContextClassLoaserDemo {
    public static void main(String[] args) {
        Thread thread = new Thread();
        System.out.println(thread.getContextClassLoader());
        thread.setContextClassLoader(new DIYContextClassLoader());
        System.out.println(thread.getContextClassLoader());

    }
    static class DIYContextClassLoader extends ClassLoader {
        //TODO  测试
    }
}
