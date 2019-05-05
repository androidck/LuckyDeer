package com.lucky.model.request.quickpay;

import com.lucky.model.request.BaseReq;

/**
 * 银行卡参数
 *
 * @author wangxiangyi
 * @date 2018/10/24
 */
public class AddCardReq extends BaseReq {
    /**
     * 储蓄卡id
     */
    private String debitCardId;
    /**
     * 信用卡id
     */
    private String id;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 信用卡昵称
     */
    private String creditAlias;
    /**
     * 银行卡卡号
     */
    private String carNumber;
    /**
     * 银行卡卡号(查询背景样式使用)
     */
    private String bankCarNum;
    /**
     * 银行预留手机号
     */
    private String phone;
    /**
     * 安全码
     */
    private String cvv;
    /**
     * 银行名称
     */
    private String branch_bank;
    /**
     * 有效期 4位
     * 格式：MMYY
     */
    private String effectiveDate;
    /**
     * 银行卡照
     */
    private String photo;
    /**
     * 账单日
     */
    private String statementDate;
    /**
     * 还款日
     */
    private String repaymentDate;

    public String getDebitCardId() {
        return debitCardId;
    }

    public void setDebitCardId(String debitCardId) {
        this.debitCardId = debitCardId;
    }

    public String getCreditId() {
        return id;
    }

    public void setCreditId(String creditId) {
        this.id = creditId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCreditAlias() {
        return creditAlias;
    }

    public void setCreditAlias(String creditAlias) {
        this.creditAlias = creditAlias;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getBankCarNum() {
        return bankCarNum;
    }

    public void setBankCarNum(String bankCarNum) {
        this.bankCarNum = bankCarNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getBranch_bank() {
        return branch_bank;
    }

    public void setBranch_bank(String branch_bank) {
        this.branch_bank = branch_bank;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStatementDate() {
        return statementDate;
    }

    public void setStatementDate(String statementDate) {
        this.statementDate = statementDate;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }
}
