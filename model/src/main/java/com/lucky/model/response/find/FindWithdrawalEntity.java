package com.lucky.model.response.find;

import java.io.Serializable;

/**
 * 提现页面状态
 *
 * @author wangxiangyi
 * @date 2018/11/19
 */
public class FindWithdrawalEntity implements Serializable {

    /**
     * 提现状态：<p>
     * 1：未审核<p>
     * 2：已审核<p>
     * 3：审核失败<p>
     * 4：已转账<p>
     * 5：转账失败 已转账为转账成功
     */
    private String withdrawStatus;
    /**
     * 提现金额
     */
    private String withdrawMoney;
    /**
     * 银行卡号
     */
    private String carNumber;
    /**
     * 开户行名称
     */
    private String openBank;
    /**
     * 手续费
     */
    private String transferPoundage;
    /**
     * 预计到账时间
     */
    private String paymentDate;
    /**
     * 转账备注（状态为5的时候返回）
     */
    private String transferRamarks;
    /**
     * 审核备注（状态为3的时候返回）
     */
    private String auditRemarks;

    public String getWithdrawStatus() {
        return withdrawStatus;
    }

    public void setWithdrawStatus(String withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }

    public String getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(String withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getTransferPoundage() {
        return transferPoundage;
    }

    public void setTransferPoundage(String transferPoundage) {
        this.transferPoundage = transferPoundage;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getTransferRamarks() {
        return transferRamarks;
    }

    public void setTransferRamarks(String transferRamarks) {
        this.transferRamarks = transferRamarks;
    }

    public String getAuditRemarks() {
        return auditRemarks;
    }

    public void setAuditRemarks(String auditRemarks) {
        this.auditRemarks = auditRemarks;
    }
}
