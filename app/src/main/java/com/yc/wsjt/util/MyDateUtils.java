package com.yc.wsjt.util;

import java.util.Calendar;

public class MyDateUtils {

    //24小时制
    public static int getTwentyFourHour() {
        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);//时
        return mHour;
    }

    //12小时制
    public static int getCurrentHour() {
        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR);//时
        return mHour;
    }

    public static int getCurrentMinute() {
        Calendar c = Calendar.getInstance();
        int mMinute = c.get(Calendar.MINUTE);//分
        return mMinute;
    }

    public static int getCurrentSecond() {
        Calendar c = Calendar.getInstance();
        int mSecond = c.get(Calendar.SECOND);//秒
        return mSecond;
    }
}
