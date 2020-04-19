package com.stefan.designPattern.builder;

public class Screen {
    private int type;

    protected Screen(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
