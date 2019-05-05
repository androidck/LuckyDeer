package com.lucky.model.request.home.repayment;

import com.lucky.model.request.BaseReq;

/**
 * 完美养卡制定参数
 *
 * @author wangxiangyi
 * @date 2019/02/26
 */
public class PerfectMakingPlansReq extends BaseReq {
    /**
     * 信用卡主键
     */
    private String creditCardId;
    /**
     * 开始日期年月日
     */
    private String strBillDate;
    /**
     * 结束日期年月日
     */
    private String strRepaymentDate;
    /**
     * 用户选择结束日期年月日,多个日期逗号分割
     */
    private String chooseRepaymentDate;
    /**
     * 计划养卡金额
     */
    private String planRepaymentMoney;
    /**
     * 计划养卡笔数
     */
    private String repaymentNum;
    /**
     * 养卡类型<p>
     * 1：日期养卡<p>
     * 2：完美养卡
     */
    private String repaymentType;
    /**
     * 用户所在城市，选填
     */
    private String city;

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

    public String getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
