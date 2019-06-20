package com.ligz.meditations.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ligz
 */
public class CalendarData implements Serializable {
    private int id;//id
    private String scoreDay;//一天的评分，按,分割
    private int time;//时间
    private int year;//年
    private int month;//月
    private int day;//日

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScoreDay() {
        return scoreDay;
    }

    public void setScoreDay(String scoreDay) {
        this.scoreDay = scoreDay;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
