package com.lucky.model.response.home.pepayment.perfect;

import java.io.Serializable;

/**
 * 制定账单详情
 *
 * @author wangxiangyi
 * @date 2019/02/26
 */
public class PerfectBillPlanListEntity implements Serializable {

    /**
     * 制定时间
     */
    private long executionTime;
    /**
     * 消费或养卡金额
     */
    private String money;
    /**
     * 执行状态
     */
    private String executionStatus;
    /**
     * 接口失败原因
     */
    private String exceptionDescribe;

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(String executionStatus) {
        this.executionStatus = executionStatus;
    }

    public String getExceptionDescribe() {
        return exceptionDescribe;
    }

    public void setExceptionDescribe(String exceptionDescribe) {
        this.exceptionDescribe = exceptionDescribe;
    }
}
