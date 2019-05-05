package com.lucky.model.response.home.pepayment;

import java.io.Serializable;

/**
 * 历史还款菜单实体
 *
 * @author wangxingyi
 * @date 2018/12/11
 */
public class HistoryPlanList implements Serializable {
    /**
     * 计划唯一id
     */
    private String id;
    /**
     * 信用卡id
     */
    private String creditId;
    /**
     * 通道id
     */
    private String channelId;
    /**
     * 账单日
     */
    private String billDate;
    /**
     * 还款日
     */
    private String repaymentDate;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 还款笔数
     */
    private String repaymentNum;
    /**
     * 已还笔数
     */
    private String alreadyRepaymentNum;
    /**
     * 开始时间
     */
    private String planRunTime;
    /**
     * 状态：<p>
     * 1：执行中<p>
     * 2：已完成<p>
     * 3：用户撤销<p>
     * 4：异常关闭<p>
     * 5：制定中<p>
     * 6：执行异常<p>
     * 7.未完全成功<p>
     * 8.终止
     */
    private String status;
    /**
     * 还款类型<p>
     * 1：日期还款
     */
    private String repaymentType;
    /**
     * 计划还款金额
     */
    private String planRepaymentMoney;
    /**
     * 未知
     */
    private String calculationMoney;
    /**
     * 需预留金额
     */
    private String keepMoney;
    /**
     * 用户选择还款时间，用于计算开始时间和结束时间
     */
    private String chooseRepaymentDate;
    /**
     * 结束时间
     */
    private String planStopTime;
    /**
     * 银行icon（图标）
     */
    private String extendOne;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行卡号
     */
    private String carNumber;
    /**
     * 实际消费金额
     */
    private String actualRepaymentMoney;
    /**
     * 消费金钱
     */
    private String consumptionMoney;
    /**
     * 账单还款
     */
    private String billRepaymentMoney;
    /**
     * 还款钱
     */
    private String repaymentMoney;
    /**
     * 手续费
     */
    private String planFee;
    /**
     * 创建时间（提交时间）
     */
    private String createDate;
    /**
     * 已还款金额
     */
    private String alreadyRepaymentMoney;
    /**
     * 已消费金额
     */
    private String alreadyConsumptionMoney;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRepaymentNum() {
        return repaymentNum;
    }

    public void setRepaymentNum(String repaymentNum) {
        this.repaymentNum = repaymentNum;
    }

    public String getAlreadyRepaymentNum() {
        return alreadyRepaymentNum;
    }

    public void setAlreadyRepaymentNum(String alreadyRepaymentNum) {
        this.alreadyRepaymentNum = alreadyRepaymentNum;
    }

    public String getPlanRunTime() {
        return planRunTime;
    }

    public void setPlanRunTime(String planRunTime) {
        this.planRunTime = planRunTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType;
    }

    public String getPlanRepaymentMoney() {
        return planRepaymentMoney;
    }

    public void setPlanRepaymentMoney(String planRepaymentMoney) {
        this.planRepaymentMoney = planRepaymentMoney;
    }

    public String getCalculationMoney() {
        return calculationMoney;
    }

    public void setCalculationMoney(String calculationMoney) {
        this.calculationMoney = calculationMoney;
    }

    public String getKeepMoney() {
        return keepMoney;
    }

    public void setKeepMoney(String keepMoney) {
        this.keepMoney = keepMoney;
    }

    public String getChooseRepaymentDate() {
        return chooseRepaymentDate;
    }

    public void setChooseRepaymentDate(String chooseRepaymentDate) {
        this.chooseRepaymentDate = chooseRepaymentDate;
    }

    public String getPlanStopTime() {
        return planStopTime;
    }

    public void setPlanStopTime(String planStopTime) {
        this.planStopTime = planStopTime;
    }

    public String getExtendOne() {
        return extendOne;
    }

    public void setExtendOne(String extendOne) {
        this.extendOne = extendOne;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getActualRepaymentMoney() {
        return actualRepaymentMoney;
    }

    public void setActualRepaymentMoney(String actualRepaymentMoney) {
        this.actualRepaymentMoney = actualRepaymentMoney;
    }

    public String getConsumptionMoney() {
        return consumptionMoney;
    }

    public void setConsumptionMoney(String consumptionMoney) {
        this.consumptionMoney = consumptionMoney;
    }

    public String getBillRepaymentMoney() {
        return billRepaymentMoney;
    }

    public void setBillRepaymentMoney(String billRepaymentMoney) {
        this.billRepaymentMoney = billRepaymentMoney;
    }

    public String getRepaymentMoney() {
        return repaymentMoney;
    }

    public void setRepaymentMoney(String repaymentMoney) {
        this.repaymentMoney = repaymentMoney;
    }

    public String getPlanFee() {
        return planFee;
    }

    public void setPlanFee(String planFee) {
        this.planFee = planFee;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getAlreadyRepaymentMoney() {
        return alreadyRepaymentMoney;
    }

    public void setAlreadyRepaymentMoney(String alreadyRepaymentMoney) {
        this.alreadyRepaymentMoney = alreadyRepaymentMoney;
    }

    public String getAlreadyConsumptionMoney() {
        return alreadyConsumptionMoney;
    }

    public void setAlreadyConsumptionMoney(String alreadyConsumptionMoney) {
        this.alreadyConsumptionMoney = alreadyConsumptionMoney;
    }
}
