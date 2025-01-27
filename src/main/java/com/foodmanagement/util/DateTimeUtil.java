package com.foodmanagement.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    public static String getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

    public static String getDateTimeString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = new Date();
        return formatter.format(date);
    }
}