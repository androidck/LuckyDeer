package com.lucky.model.common.dialog;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.util.List;

/**
 * 数据实体类
 *
 * @author wangxiangyi
 * @date 2019/01/14
 */
public class SelectRepaymentDateEntity extends SectionEntity {
    /**
     * 日期
     */
    private String date;
    /**
     * 年
     */
    private String year;
    /**
     * 月份
     */
    private String month;
    /**
     * 天数
     */
    private String day;
    /**
     * 是否隐藏全选按钮<p>
     * true：显示全选按钮<p>
     * false：隐藏全选按钮
     */
    private boolean isHideSelectAll;
    /**
     * 是否是全选（全选按钮）：<p>
     * true：全部取消<p>
     * false：全选
     */
    private boolean isAllSelect;
    /**
     * 是否选择（日期）：<p>
     * true：全选<p>
     * false：取消
     */
    private boolean isSelect;

    /**
     * 每个月的名单
     */
    private List<SelectRepaymentDateEntity> oneMonthList;

    public SelectRepaymentDateEntity(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SelectRepaymentDateEntity(boolean isHeader, String header, Boolean isHideSelectAll) {
        super(isHeader, header);
        this.isHideSelectAll = isHideSelectAll;
    }

    public SelectRepaymentDateEntity(boolean isHeader, String header, String date) {
        super(isHeader, header);
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isHideSelectAll() {
        return isHideSelectAll;
    }

    public void setHideSelectAll(boolean hideSelectAll) {
        isHideSelectAll = hideSelectAll;
    }

    public boolean isAllSelect() {
        return isAllSelect;
    }

    public void setAllSelect(boolean allSelect) {
        isAllSelect = allSelect;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public List<SelectRepaymentDateEntity> getOneMonthList() {
        return oneMonthList;
    }

    public void setOneMonthList(List<SelectRepaymentDateEntity> oneMonthList) {
        this.oneMonthList = oneMonthList;
    }
}
