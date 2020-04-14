package com.stefan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("1", 12);

        List<String>  list = new ArrayList<String>();
        list.add("12");
        list.add("13");
        list.add(0, "14");
        System.out.println(list);
    }
}
