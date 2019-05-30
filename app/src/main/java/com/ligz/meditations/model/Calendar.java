package com.ligz.meditations.model;

import java.util.Date;

/**
 * Created by ligz
 */
public class Calendar {
    private int id;
    private Date time;
    private int scoreForenoon;
    private int scoreAfternoon;
    private int scoreNight;
    private int scoreDay;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
}
