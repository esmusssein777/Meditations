package com.ligz.meditations.data;

import android.content.Context;

/**
 * 关于日历评价的dao层
 * Created by ligz
 */
public class CalendarDao {
    private CalendarSQLiteHelper cHelper;

    private CalendarDao(Context context) {
        cHelper = new CalendarSQLiteHelper(context);
    }

    public static CalendarDao getInstance(Context context) {
        return new CalendarDao(context);
    }
}
