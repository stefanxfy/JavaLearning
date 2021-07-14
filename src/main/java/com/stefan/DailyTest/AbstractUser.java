package com.stefan.DailyTest;

public abstract class AbstractUser implements User{
    protected String name = null;
    protected int backgroundProcessorDelay = -1;

    protected void startInternal() {
        if (backgroundProcessorDelay > 0) {
            System.out.println("启动后台线程");
        }

    }
    @Override
    public void play() {
        System.out.println("AbstractUser play.....");
        LogTest.log();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
