package com.ligz.meditations.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ligz.meditations.model.Calendar;

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

    public int addCalendar(Calendar calendar) {
        SQLiteDatabase db = cHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CalendarDBConfig.CAL_TIME, calendar.getTime());
        values.put(CalendarDBConfig.CAL_YEAR, calendar.getYear());
        values.put(CalendarDBConfig.CAL_MONTH, calendar.getMonth());
        values.put(CalendarDBConfig.CAL_DAY, calendar.getDay());
        values.put(CalendarDBConfig.CAL_SCORE_FORENOON, calendar.getScoreForenoon());
        values.put(CalendarDBConfig.CAL_SCORE_AFTERNOON, calendar.getScoreAfternoon());
        values.put(CalendarDBConfig.CAL_SCORE_NIGHT, calendar.getScoreNight());
        values.put(CalendarDBConfig.CAL_SCORE_DAY, calendar.getScoreDay());
        long row = db.insert(CalendarDBConfig.CALENDAR_TABLE_NAME, null, values);
        db.close();
        cHelper.close();
        return row > 0 ? 1 : 0;
    }

    public int updateCalendar(Calendar calendar) {
        SQLiteDatabase db = cHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CalendarDBConfig.CAL_TIME, calendar.getTime());
        values.put(CalendarDBConfig.CAL_YEAR, calendar.getYear());
        values.put(CalendarDBConfig.CAL_MONTH, calendar.getMonth());
        values.put(CalendarDBConfig.CAL_DAY, calendar.getDay());
        values.put(CalendarDBConfig.CAL_SCORE_FORENOON, calendar.getScoreForenoon());
        values.put(CalendarDBConfig.CAL_SCORE_AFTERNOON, calendar.getScoreAfternoon());
        values.put(CalendarDBConfig.CAL_SCORE_NIGHT, calendar.getScoreNight());
        values.put(CalendarDBConfig.CAL_SCORE_DAY, calendar.getScoreDay());
        long row = db.update(CalendarDBConfig.CALENDAR_TABLE_NAME, values, null, null);
        db.close();
        cHelper.close();
        return row > 0 ? 1 : 0;
    }


}
