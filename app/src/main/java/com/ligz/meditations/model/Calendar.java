package com.ligz.meditations.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ligz
 */
public class Calendar implements Serializable {
    private int id;
    private int scoreForenoon;
    private int scoreAfternoon;
    private int scoreNight;
    private int scoreDay;
    private int time;
    private int year;
    private int month;
    private int day;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getScoreForenoon() {
        return scoreForenoon;
    }

    public void setScoreForenoon(int scoreForenoon) {
        this.scoreForenoon = scoreForenoon;
    }

    public int getScoreAfternoon() {
        return scoreAfternoon;
    }

    public void setScoreAfternoon(int scoreAfternoon) {
        this.scoreAfternoon = scoreAfternoon;
    }

    public int getScoreNight() {
        return scoreNight;
    }

    public void setScoreNight(int scoreNight) {
        this.scoreNight = scoreNight;
    }

    public int getScoreDay() {
        return scoreDay;
    }

    public void setScoreDay(int scoreDay) {
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
