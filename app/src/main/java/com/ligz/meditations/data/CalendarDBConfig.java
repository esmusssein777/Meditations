package com.ligz.meditations.data;

/**
 * 存放常量
 * Created by ligz
 */
public interface CalendarDBConfig {
    int DATABASE_VERSION = 1;

    String DATABASE_NAME = "CalendarDB";

    String CAL_ID = "id";//主键id
    String CAL_TIME = "time";//时间 HH:mm:ss
    String CAL_YEAR = "year";
    String CAL_MONTH = "month";
    String CAL_DAY = "day";
    /**
     * 评分的细则是
     * [null||""=没有颜色=没有评分]
     * [1=绿色=完成目标]
     * [2=红色=未完成目标]
     * [3=黄色=休息]
     */
    String CAL_SCORE_DAY = "score_day";//一整天的评分

    String CALENDAR_TABLE_NAME = "calendar";

    String CREATE_CALENDAR_TABLE_SQL = "CREATE TABLE " + CALENDAR_TABLE_NAME + " ("
            + CAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CAL_TIME + " INTEGER, "
            + CAL_YEAR + " INTEGER, "
            + CAL_MONTH + " INTEGER, "
            + CAL_DAY + " INTEGER, "
            + CAL_SCORE_DAY + " VARCHAR(48)" + ")";

    String DROP_CALENDAR_TABLE_SQL = "DROP TABLE " + CALENDAR_TABLE_NAME;



}
