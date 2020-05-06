package com.stefan.designPattern.factory;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class CarFactory3 {
    private static Map<String, ICar> carPool = new HashMap<String, ICar>();

    private static final String PACKAGE_NAME = "com.stefan.designPattern.factory";
}
