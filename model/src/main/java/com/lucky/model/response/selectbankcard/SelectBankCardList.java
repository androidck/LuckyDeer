package com.lucky.model.response.selectbankcard;

import java.io.Serializable;

/**
 * 选择银行卡列表
 *
 * @author wangxiangyi
 * @date 2018/10/10
 */
public class SelectBankCardList implements Serializable {
    /**
     * 传值标记
     * 1：选择信用卡
     * 2：选择储蓄卡
     */
    private int flag;
    /**
     * 传值标记
     * 1：选择信用卡
     * 2：选择储蓄卡
     */
    private String debitCardType;
    /**
     * 进入选择银行卡页面标识
     */
    private String enterAisleFlag;

    /**
     * 储蓄卡id
     */
    private String debitCardId;
    /**
     * 信用卡id
     */
    private String creditId;
    /**
     * 银行卡logo
     */
    private String logo;
    /**
     * 银行卡彩色logo
     */
    private String bankColorLogo;
    /**
     * 银行卡名称
     */
    private String bankName;
    /**
     * 银行卡类型
     */
    private String type;
    /**
     * 银行卡账号
     */
    private String carNumber;
    /**
     * 预留手机号
     */
    private String realName;
    /**
     * 预留手机号
     */
    private String phone;
    /**
     * 银行背景虚化logo图片
     */
    private String bankBackground;
    /**
     * 银行背景颜色
     */
    private String backgroundColor;
    /**
     * 默认使用(1:默认使用)
     */
    private String isDefault = "0";
    /**
     * 银行卡昵称
     */
    private String creditAlias;
/**
     * 账单日
     */
    private String billDate;
/**
     * 还款日
     */
    private String repaymentDate;

    /*****************************提现页面****************************/
    /**
     * 提现金额
     */
    private String money;

    public String getDebitCardId() {
        return debitCardId;
    }

    public void setDebitCardId(String debitCardId) {
        this.debitCardId = debitCardId;
    }

    public String getDebitCardType() {
        return debitCardType;
    }

    public void setDebitCardType(String debitCardType) {
        this.debitCardType = debitCardType;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getEnterAisleFlag() {
        return enterAisleFlag;
    }

    public void setEnterAisleFlag(String enterAisleFlag) {
        this.enterAisleFlag = enterAisleFlag;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBankColorLogo() {
        return bankColorLogo;
    }

    public void setBankColorLogo(String bankColorLogo) {
        this.bankColorLogo = bankColorLogo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBankBackground() {
        return bankBackground;
    }

    public void setBankBackground(String bankBackground) {
        this.bankBackground = bankBackground;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String isDefault() {
        return isDefault;
    }

    public void isDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getCreditAlias() {
        return creditAlias;
    }

    public void setCreditAlias(String creditAlias) {
        this.creditAlias = creditAlias;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

}
