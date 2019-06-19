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

    public static int getCurrentSolt() {
        int solt = 0;
        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);//时
        if (mHour >= 0 && mHour < 5) {
            solt = 0;
        }
        if (mHour >= 5 && mHour < 8) {
            solt = 1;
        }
        if (mHour >= 8 && mHour < 12) {
            solt = 2;
        }
        if (mHour >= 12 && mHour < 13) {
            solt = 3;
        }
        if (mHour >= 13 && mHour < 18) {
            solt = 4;
        }
        if (mHour >= 18 && mHour < 19) {
            solt = 5;
        }
        if (mHour >= 19 && mHour < 24) {
            solt = 6;
        }
        return solt;
    }

}
