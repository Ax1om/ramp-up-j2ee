package com.nrqapps.j2ee.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Mauricio Enriquez on 8/25/17.
 * NrqApps © 2017
 */
public class PropertiesUtil {
    private static final Properties properties = new Properties();
    static {
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/nrqapps/j2ee/config/messages.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProp(String key) {
        return properties.getProperty(key);
    }
}
