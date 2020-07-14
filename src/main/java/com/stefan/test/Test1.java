package com.stefan.test;

import java.io.DataInputStream;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class Test1 {
    public static void main(String[] args) {
        Favorites favorites = new Favorites();
        favorites.put(Integer.class, 12);
        int val = favorites.get(Integer.class);
        System.out.println(val);
        System.out.println(Integer.TYPE);
        System.out.println(Integer.BYTES);
        favorites.putBatch(Integer.class, 12, 13);
        System.out.println(favorites.get(Integer.class));


    }
}
