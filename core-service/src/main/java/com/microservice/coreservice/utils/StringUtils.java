package com.microservice.coreservice.utils;

public class StringUtils {

    public static boolean isNullOrBlank(String str) {

        if(str != null && str.trim().length() > 0) {
            return false;
        }
        return true;
    }
}
