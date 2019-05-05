package com.lucky.model.common.date;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 签到日历实体
 *
 * @author wangxiangyi
 * @date 2019/02/21
 */
public class CheckInCalendarEntity implements MultiItemEntity {
    /**
     * 当前日期
     */
    private String currentDate;
    /**
     * 星期
     */
    private String week;
    /**
     * 区间日期
     */
    private String intervalDate;
    /**
     * 是否签到
     */
    private boolean isCheckIn;
    /**
     * 是否显示签到控件
     */
    private boolean isShowCheckInControl;
    /**
     * 设置类型
     */
    private int itemType;

    public CheckInCalendarEntity() {
    }

    public CheckInCalendarEntity(int itemType, String week) {
        this.itemType = itemType;
        this.week = week;
    }

    public CheckInCalendarEntity(int itemType, boolean isShowCheckInControl, String intervalDate) {
        this.itemType = itemType;
        this.isShowCheckInControl = isShowCheckInControl;
        this.intervalDate = intervalDate;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getIntervalDate() {
        return intervalDate;
    }

    public void setIntervalDate(String intervalDate) {
        this.intervalDate = intervalDate;
    }

    public boolean isCheckIn() {
        return isCheckIn;
    }

    public void setCheckIn(boolean checkIn) {
        isCheckIn = checkIn;
    }

    public boolean isShowCheckInControl() {
        return isShowCheckInControl;
    }

    public void setShowCheckInControl(boolean showCheckInControl) {
        isShowCheckInControl = showCheckInControl;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
