package com.lucky.model.response.home.pepayment;

import java.io.Serializable;

/**
 * 消费详情
 *
 * @author wangxiangyi
 * @date 2018/12/14
 */
public class ConsumeList implements Serializable {
    /**
     * 消费金额
     */
    private String consumptionMoney;
    /**
     * 消费时间
     */
    private String consumptionTime;
    /**
     * 还款时间
     */
    private String repaymentTime;
    /**
     * 预还款时间
     */
    private String billRepaymentTime;
    /**
     * 是否以消费<p>
     * 1成功<p>
     * 2失败<p>
     * 4未执行
     */
    private String consumptionStatus;
    /**
     * 是否已还款状态<p>
     * 1：已还款<p>
     * 2：还款失败<p>
     * 4：待执行
     */
    private String status;
    /**
     * 实际还款金额
     */
    private String repaymentMoney;

    public String getConsumptionMoney() {
        return consumptionMoney;
    }

    public void setConsumptionMoney(String consumptionMoney) {
        this.consumptionMoney = consumptionMoney;
    }

    public String getConsumptionTime() {
        return consumptionTime;
    }

    public void setConsumptionTime(String consumptionTime) {
        this.consumptionTime = consumptionTime;
    }

    public String getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(String repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public String getBillRepaymentTime() {
        return billRepaymentTime;
    }

    public void setBillRepaymentTime(String billRepaymentTime) {
        this.billRepaymentTime = billRepaymentTime;
    }

    public String getConsumptionStatus() {
        return consumptionStatus;
    }

    public void setConsumptionStatus(String consumptionStatus) {
        this.consumptionStatus = consumptionStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRepaymentMoney() {
        return repaymentMoney;
    }

    public void setRepaymentMoney(String repaymentMoney) {
        this.repaymentMoney = repaymentMoney;
    }
}
