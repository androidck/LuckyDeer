package com.lucky.model.common.dialog;

/**
 * 自定义
 * Created by Administrator on 2018/4/14.
 */

public class Day {

    private String days;

    private String year;

    private String month;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public Day(String days, String year, String month, boolean isSelect) {
        this.days = days;
        this.year = year;
        this.month = month;
        this.isSelect = isSelect;
    }

    public Day(String days, String year, String month) {
        this.days = days;
        this.year = year;
        this.month = month;
    }


    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    //    private boolean isSelect = true;
    private boolean isSelect;


    public boolean getIsSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public String toString() {
        return "Day{" +
                "days='" + days + '\'' +
                ", year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }
}
