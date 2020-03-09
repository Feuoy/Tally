package com.feuoy.tally.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;


public class DateUtil {


    // 返回现在的yyyy-MM-dd
    public static String getNowYearMonthDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }


    // 传入timeStamp，返回HH:mm
    public static String timeStampLongToHourMinute(long timeStamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(new Date(timeStamp));
    }


    // 传入"yyyy-MM-dd"，返回yyyy-MM-dd
    public static Date dateStrToYearMonthDay(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }


    // 传入"yyyy-MM-dd"，返回“y年m月d日”
    public static String dateStrToChineseYearMonthDay(String dateStr) {
        String[] months = {
                "1 月", "2 月", "3 月",
                "4 月", "5 月", "6 月",
                "7 月", "8 月", "9 月",
                "10 月", "11 月", "12 月"
        };

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStrToYearMonthDay(dateStr));

        int year = calendar.get(Calendar.YEAR);
        int monthIndex = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return year + " 年 " + months[monthIndex] + " " + day + " 日";
    }


    // 传入"yyyy-MM-dd"，返回“周几”
    public static String dateStrToWeekday(String dateStr) {
        String[] weekdays = {
                "周 日", "周 一", "周 二",
                "周 三", "周 四",
                "周 五", "周 六"
        };

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStrToYearMonthDay(dateStr));

        int index = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        return weekdays[index];
    }


    // 传入"yyyy-MM-dd"链表，返回days
    public static Calendar[] datesToDays(LinkedList<String> dates) {
        Calendar[] days = new Calendar[dates.size()];

        for (int i = 0; i < dates.size(); i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateStrToYearMonthDay(dates.get(i)));
            days[i] = calendar;
        }

        return days;
    }


}