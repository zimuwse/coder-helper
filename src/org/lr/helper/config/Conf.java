package org.lr.helper.config;

import com.intellij.ide.util.PropertiesComponent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zimuwse
 * @time: 2018-01-22 09:23
 * @description: base conf class
 */
public class Conf {
    private static PropertiesComponent component;

    private static Map<String, String> map;

    private final static boolean DEBUG = false;

    static {
        if (DEBUG)
            map = new HashMap<>();
        else
            component = PropertiesComponent.getInstance();
    }

    public static boolean isDebug() {
        return DEBUG;
    }

    public static String get(String key) {
        if (DEBUG)
            return map.get(key);
        else
            return component.getValue(key);
    }

    public static void set(String key, String value) {
        if (DEBUG)
            map.put(key, value);
        else
            component.setValue(key, value);
    }
}
