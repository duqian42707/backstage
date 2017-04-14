package com.dqv5.backstage.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dq on 2017/4/14.
 */
public class DateTimeUtil {

    public static String formatToStringYMDHms(Date date) {
        return formatToString("yyyy-MM-dd HH:mm:ss", date);
    }

    public static String formatToStringYMD(Date date) {
        return formatToString("yyyy-MM-dd", date);
    }

    public static String formatToStringHms(Date date) {
        return formatToString("HH:mm:ss", date);
    }

    public static String formatToStringHm(Date date) {
        return formatToString("HH:mm", date);
    }

    public static Date formatToDateYMDHms(String str) {
        return formatToDate("yyyy-MM-dd HH:mm:ss", str);
    }

    public static Date formatToDateYMD(String str) {
        return formatToDate("yyyy-MM-dd", str);
    }

    public static Date formatToDateHms(String str) {
        return formatToDate("HH:mm:ss", str);
    }

    public static Date formatToDateHm(String str) {
        return formatToDate("HH:mm", str);
    }


    public static String formatToString(String pattern, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date formatToDate(String pattern, String str) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
