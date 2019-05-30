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
    /**
     * 评分的细则是
     * [null||""=没有颜色=没有评分]
     * [1=绿色=完成目标]
     * [2=黄色=休息]
     * [3=红色=未完成目标]
     * [4=灰色=比如评了上午下午没有评就是灰色]
     */
    String CAL_SCORE_FORENOON = "score_forenoon";//上午的评分
    String CAL_SCORE_AFTERNOON = "score_afternoon";//下午的评分
    String CAL_SCORE_NIGHT = "score_night";//晚上的评分
    String CAL_SCORE_DAY = "score_day";//一整天的评分

    String CALENDAR_TABLE_NAME = "calendar";

    String CREATE_CALENDAR_TABLE_SQL = "CREATE TABLE " + CALENDAR_TABLE_NAME + "("
            + CAL_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CAL_TIME + "DATETIME NULL DEFAULT NULL, "
            + CAL_SCORE_FORENOON + "INTEGER, "
            + CAL_SCORE_AFTERNOON + "INTEGER, "
            + CAL_SCORE_NIGHT + "INTEGER, "
            + CAL_SCORE_DAY + "INTEGER" + ")";

    String DROP_CALENDAR_TABLE_SQL = "DROP TABLE" + CALENDAR_TABLE_NAME;



}
