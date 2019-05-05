package com.lucky.model.response.home.cardLife;

import java.io.Serializable;

/**
 * 卡生活页面实体
 *
 * @author wangxiangyi
 * @date 2019/03/19
 */
public class CardLifeEntity implements Serializable {

    /**
     * totalBalance : 16.28
     * feMoney : 18.78
     * yjMoney : 0
     * integralCalculus : 0
     * refereeUser : 无心
     * refereePhone : 15634123808
     * isSelectLowerPhone : 3
     * refereeExtendOne : 3
     */
    /**
     * 总资产
     */
    private String totalBalance;
    /**
     * 分润
     */
    private String feMoney;
    /**
     * 佣金
     */
    private String yjMoney;
    /**
     * 积分
     */
    private String integralCalculus;
    /**
     * 推荐人姓名
     */
    private String refereeUser;
    /**
     * 推荐人手机号
     */
    private String refereePhone;
    /**
     * 是否允许上下级查看我的手机号<br>
     * 1：允许<br>
     * 2：不允许<br>
     * 3：禁止用户操作
     */
    private String isSelectLowerPhone;
    /**
     * 是否可以查看推荐人手机号<br>
     * 1：允许<br>
     * 2：不允许<br>
     * 3：禁止用户操作
     */
    private String refereeExtendOne;

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getFeMoney() {
        return feMoney;
    }

    public void setFeMoney(String feMoney) {
        this.feMoney = feMoney;
    }

    public String getYjMoney() {
        return yjMoney;
    }

    public void setYjMoney(String yjMoney) {
        this.yjMoney = yjMoney;
    }

    public String getIntegralCalculus() {
        return integralCalculus;
    }

    public void setIntegralCalculus(String integralCalculus) {
        this.integralCalculus = integralCalculus;
    }

    public String getRefereeUser() {
        return refereeUser;
    }

    public void setRefereeUser(String refereeUser) {
        this.refereeUser = refereeUser;
    }

    public String getRefereePhone() {
        return refereePhone;
    }

    public void setRefereePhone(String refereePhone) {
        this.refereePhone = refereePhone;
    }

    public String getIsSelectLowerPhone() {
        return isSelectLowerPhone;
    }

    public void setIsSelectLowerPhone(String isSelectLowerPhone) {
        this.isSelectLowerPhone = isSelectLowerPhone;
    }

    public String getRefereeExtendOne() {
        return refereeExtendOne;
    }

    public void setRefereeExtendOne(String refereeExtendOne) {
        this.refereeExtendOne = refereeExtendOne;
    }
}
