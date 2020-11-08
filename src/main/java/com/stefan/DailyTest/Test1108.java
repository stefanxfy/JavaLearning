package com.stefan.DailyTest;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Test1108 {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        System.out.println(addr.getHostName());
        System.out.println(addr.getHostAddress());
        System.out.println(addr.getAddress());
        System.out.println(addr.getCanonicalHostName());
    }
}
