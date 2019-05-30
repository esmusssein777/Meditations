package com.ligz.meditations.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库的操作
 * Created by ligz
 */
public class CalendarSQLiteHelper  extends SQLiteOpenHelper {
    public CalendarSQLiteHelper(Context context) {
        super(context, CalendarDBConfig.DATABASE_NAME, null, CalendarDBConfig.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CalendarDBConfig.CREATE_CALENDAR_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            sqLiteDatabase.execSQL(CalendarDBConfig.DROP_CALENDAR_TABLE_SQL);
            onCreate(sqLiteDatabase);
        }
    }
}
