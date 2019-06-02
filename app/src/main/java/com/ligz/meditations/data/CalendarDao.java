package com.ligz.meditations.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ligz.meditations.model.Calendar;

import java.util.ArrayList;
import java.util.List;

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

    public Calendar getCalendarByTime(int year, int month, int day) {
        List<Calendar> calendars = new ArrayList<>();
        String[] values = {String.valueOf(year), String.valueOf(month), String.valueOf(day)};
        SQLiteDatabase db = cHelper.getReadableDatabase();
        Cursor cursor = db.query(CalendarDBConfig.CALENDAR_TABLE_NAME, null,
                String.format("%s=? and %s=? and %s=?", CalendarDBConfig.CAL_YEAR,CalendarDBConfig.CAL_MONTH, CalendarDBConfig.CAL_DAY),
                values, null, null, null);
        Calendar calendar = new Calendar();
        if (cursor.moveToFirst()) {
            calendar.setId(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_ID)));
            calendar.setTime(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_TIME)));
            calendar.setYear(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_YEAR)));
            calendar.setMonth(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_MONTH)));
            calendar.setDay(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_DAY)));
            calendar.setScoreForenoon(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_SCORE_FORENOON)));
            calendar.setScoreAfternoon(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_SCORE_AFTERNOON)));
            calendar.setScoreNight(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_SCORE_NIGHT)));
            calendar.setScoreDay(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_SCORE_DAY)));
        }
        cursor.close();
        db.close();
        cHelper.close();
        return calendar;
    }

    public List<Calendar> getAllCalendar() {
        List<Calendar> calendars = new ArrayList<>();
        SQLiteDatabase db = cHelper.getReadableDatabase();
        Cursor cursor = db.query(CalendarDBConfig.CALENDAR_TABLE_NAME, null, null, null, null, null, null);
        Calendar calendar;
        while (cursor.moveToNext()) {
            calendar = new Calendar();
            calendar.setId(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_ID)));
            calendar.setTime(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_TIME)));
            calendar.setYear(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_YEAR)));
            calendar.setMonth(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_MONTH)));
            calendar.setDay(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_DAY)));
            calendar.setScoreForenoon(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_SCORE_FORENOON)));
            calendar.setScoreAfternoon(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_SCORE_AFTERNOON)));
            calendar.setScoreNight(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_SCORE_NIGHT)));
            calendar.setScoreDay(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_SCORE_DAY)));
            calendars.add(calendar);
        }
        cursor.close();
        db.close();
        cHelper.close();
        return calendars;
    }


}
