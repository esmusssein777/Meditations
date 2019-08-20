package com.ligz.meditations.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ligz.meditations.model.CalendarData;

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

    /**
     * 新增一条记录
     * @param calendarData
     * @return
     */
    public int addCalendar(CalendarData calendarData) {
        SQLiteDatabase db = cHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CalendarDBConfig.CAL_TIME, calendarData.getTime());
        values.put(CalendarDBConfig.CAL_YEAR, calendarData.getYear());
        values.put(CalendarDBConfig.CAL_MONTH, calendarData.getMonth());
        values.put(CalendarDBConfig.CAL_DAY, calendarData.getDay());
        values.put(CalendarDBConfig.CAL_SCORE_DAY, calendarData.getScoreDay());
        long row = db.insert(CalendarDBConfig.CALENDAR_TABLE_NAME, null, values);
        db.close();
        cHelper.close();
        return row > 0 ? 1 : 0;
    }

    /**
     * 更新一天的记录
     * @param calendarData
     * @return
     */
    public int updateCalendar(CalendarData calendarData) {
        SQLiteDatabase db = cHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CalendarDBConfig.CAL_TIME, calendarData.getTime());
        values.put(CalendarDBConfig.CAL_YEAR, calendarData.getYear());
        values.put(CalendarDBConfig.CAL_MONTH, calendarData.getMonth());
        values.put(CalendarDBConfig.CAL_DAY, calendarData.getDay());
        values.put(CalendarDBConfig.CAL_SCORE_DAY, calendarData.getScoreDay());
        long row = db.update(CalendarDBConfig.CALENDAR_TABLE_NAME, values, String.format("%s=?", CalendarDBConfig.CAL_ID), new String[]{String.valueOf(calendarData.getId())});
        db.close();
        cHelper.close();
        return row > 0 ? 1 : 0;
    }

    /**
     * 获取某一天的记录
     * @param year
     * @param month
     * @param day
     * @return
     */
    public CalendarData getCalendarByTime(int year, int month, int day) {
        List<CalendarData> calendars = new ArrayList<>();
        String[] values = {String.valueOf(year), String.valueOf(month), String.valueOf(day)};
        SQLiteDatabase db = cHelper.getReadableDatabase();
        Cursor cursor = db.query(CalendarDBConfig.CALENDAR_TABLE_NAME, null,
                String.format("%s=? and %s=? and %s=?", CalendarDBConfig.CAL_YEAR,CalendarDBConfig.CAL_MONTH, CalendarDBConfig.CAL_DAY),
                values, null, null, null);
        CalendarData calendarData = new CalendarData();
        if (cursor.moveToFirst()) {
            calendarData.setId(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_ID)));
            calendarData.setTime(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_TIME)));
            calendarData.setYear(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_YEAR)));
            calendarData.setMonth(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_MONTH)));
            calendarData.setDay(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_DAY)));
            calendarData.setScoreDay(cursor.getString(cursor.getColumnIndex(CalendarDBConfig.CAL_SCORE_DAY)));
        }
        cursor.close();
        db.close();
        cHelper.close();
        return calendarData;
    }

    /**
     * 获取所有的记录
     * @return
     */
    public List<CalendarData> getAllCalendar() {
        List<CalendarData> calendars = new ArrayList<>();
        SQLiteDatabase db = cHelper.getReadableDatabase();
        Cursor cursor = db.query(CalendarDBConfig.CALENDAR_TABLE_NAME, null, null, null, null, null, null);
        CalendarData calendarData;
        while (cursor.moveToNext()) {
            calendarData = new CalendarData();
            calendarData.setId(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_ID)));
            calendarData.setTime(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_TIME)));
            calendarData.setYear(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_YEAR)));
            calendarData.setMonth(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_MONTH)));
            calendarData.setDay(cursor.getInt(cursor.getColumnIndex(CalendarDBConfig.CAL_DAY)));
            calendarData.setScoreDay(cursor.getString(cursor.getColumnIndex(CalendarDBConfig.CAL_SCORE_DAY)));
            calendars.add(calendarData);
        }
        cursor.close();
        db.close();
        cHelper.close();
        return calendars;
    }

    public boolean deleteCalendar(int year, int month, int day) {
        SQLiteDatabase db = cHelper.getWritableDatabase();
        int row =db.delete(CalendarDBConfig.CALENDAR_TABLE_NAME,
                String.format("%s=? and %s=? and %s=?", CalendarDBConfig.CAL_YEAR,CalendarDBConfig.CAL_MONTH, CalendarDBConfig.CAL_DAY),
                new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(day)});
        db.close();
        cHelper.close();
        return row != 0;
    }
}
