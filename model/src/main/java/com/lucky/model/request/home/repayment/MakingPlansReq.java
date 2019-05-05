package com.lucky.model.request.home.repayment;

import com.lucky.model.request.BaseReq;

/**
 * 日期还款：制定计划
 *
 * @author wangxiangyi
 * @date 2018/12/24
 */
public class MakingPlansReq extends BaseReq {
    /**
     * 信用卡主键
     */
    private String creditCardId;
    /**
     * 账单日期年月日（日期格式：yyyy_MM_dd）
     */
    private String strBillDate;
    /**
     * 还款日期年月日（日期格式：yyyy_MM_dd）
     */
    private String strRepaymentDate;
    /**
     * 用户选择还款日期年月日,多个日期逗号分割（日期格式：yyyy_MM_dd）
     */
    private String chooseRepaymentDate;
    /**
     * 计划还款金额
     */
    private String planRepaymentMoney;
    /**
     * 计划还款笔数
     */
    private String repaymentNum;
    /**
     * 用户所在城市，选填
     */
    private String city;
    /**
     * 还款类型<p>
     * 1：日期还款
     */
    private String repaymentType;

    public String getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(String creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getStrBillDate() {
        return strBillDate;
    }

    public void setStrBillDate(String strBillDate) {
        this.strBillDate = strBillDate;
    }

    public String getStrRepaymentDate() {
        return strRepaymentDate;
    }

    public void setStrRepaymentDate(String strRepaymentDate) {
        this.strRepaymentDate = strRepaymentDate;
    }

    public String getChooseRepaymentDate() {
        return chooseRepaymentDate;
    }

    public void setChooseRepaymentDate(String chooseRepaymentDate) {
        this.chooseRepaymentDate = chooseRepaymentDate;
    }

    public String getPlanRepaymentMoney() {
        return planRepaymentMoney;
    }

    public void setPlanRepaymentMoney(String planRepaymentMoney) {
        this.planRepaymentMoney = planRepaymentMoney;
    }

    public String getRepaymentNum() {
        return repaymentNum;
    }

    public void setRepaymentNum(String repaymentNum) {
        this.repaymentNum = repaymentNum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType;
    }
}
