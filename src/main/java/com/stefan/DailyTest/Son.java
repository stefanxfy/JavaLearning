package com.stefan.DailyTest;

public class Son extends AbstractUser{
    protected String name = "Son";

    public Son() {
        backgroundProcessorDelay = 10;
    }

    public String getName() {
        return super.getName();
    }

    @Override
    public void start() {
        super.startInternal();
    }
}
