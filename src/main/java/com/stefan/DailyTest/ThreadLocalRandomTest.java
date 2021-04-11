package com.stefan.DailyTest;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomTest {
    public static void main(String[] args) {
        // seed的生成
        Random random = new Random();
        random.nextInt();

        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        tlr.nextInt();
    }

    private static final long multiplier = 0x5DEECE66DL;
    private static final long addend = 0xBL;
    private static final long mask = (1L << 48) - 1;

}
