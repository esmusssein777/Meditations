package com.ligz.meditations.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 某一日评分的实体类
 * Created by ligz
 */
public class CalendarData implements Serializable {
    private Integer id;//id
    private String scoreDay;//一天的评分，按,分割
    private Integer time;//时间
    private Integer year;//年
    private Integer month;//月
    private Integer day;//日

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScoreDay() {
        return scoreDay;
    }

    public void setScoreDay(String scoreDay) {
        this.scoreDay = scoreDay;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CalendarData that = (CalendarData) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (scoreDay != null ? !scoreDay.equals(that.scoreDay) : that.scoreDay != null)
            return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;
        if (month != null ? !month.equals(that.month) : that.month != null) return false;
        return day != null ? day.equals(that.day) : that.day == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (scoreDay != null ? scoreDay.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CalendarData{" +
                "id=" + id +
                ", scoreDay='" + scoreDay + '\'' +
                ", time=" + time +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
