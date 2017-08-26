package com.nrqapps.j2ee.utils;

/**
 * Created by Mauricio Enriquez on 8/25/17.
 * NrqApps © 2017
 */
public class StringUtils {
    public static boolean isEmpty(String string){
        return string == null || string.equals("");
    }

    public static boolean isNaturalNumber(String string) {
        return string.matches("\\d+");
    }

    public static boolean isDateFormated(String string) {
        return string.matches("[\\d]{4}-(0[1-9]|1[0-2])-([0-2][1-9]|[1-3]0|31)");
    }
}
