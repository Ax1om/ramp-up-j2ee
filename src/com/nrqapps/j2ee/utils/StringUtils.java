package com.nrqapps.j2ee.utils;

/**
 * Created by Mauricio Enriquez on 8/25/17.
 * NrqApps © 2017
 */
public class StringUtils {
    public static Boolean isEmpty(String string){
        return string == null || string.equals("");
    }
}
