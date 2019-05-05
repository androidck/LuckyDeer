package com.lucky.model.response.home;

import java.io.Serializable;

/**
 * 我的分润或提现明细页面实体
 *
 * @author wangxiangyi
 * @date 2018/11/15
 */
public class DividedOrWithdrawalDetailsList implements Serializable {
    /**
     * {
     *             "id": "505a1ef33cdf44ff98820f56f191aeed",
     *             "transactionType": "刷卡",
     *             "operationBalance": 0.99,
     *             "tradingAmount": 98756,
     *             "phone": "15854855277",
     *             "createDate": 1548062104000
     *         }
     */

    /**
     * 用户id
     */
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 提现金额
     */
    private String withdrawMoney;
    /**
     * 提现状态
     */
    private String withdrawStatus;
    /**
     * 审核人
     */
    private String auditPeople;
    /**
     * 审核时间
     */
    private long auditDate;
    /**
     * 审核备注
     */
    private String auditRemarks;
    /**
     * 转账类型
     */
    private String transferType;
    /**
     * 转账金额
     */
    private String transferMoney;
    /**
     * 转账人
     */
    private String transferPeople;
    /**
     * 提现时间
     */
    private long transferDate;
    /**
     *
     */
    private String transferRamarks;
    /**
     * 真是姓名
     */
    private String realName;
    /**
     * 卡号
     */
    private String carNumber;
    /**
     * 预留手机号/分润信息(用户账号)
     */
    private String phone;
    /**
     * 银行id
     */
    private String bankId;
    /**
     * 开户行cvn
     */
    private String openBankCode;
    /**
     * 开户行
     */
    private String openBank;
    /**
     * 付款收据
     */
    private String paymentReceipt;
    /**
     * 创建时间
     */
    private long createDate;
    /**
     * 更新时间
     */
    private long updateDate;
    /**
     * 更新通道
     */
    private String updateBy;
    /**
     *
     */
    private String delFlag;
    /**
     *
     */
    private String withdrawMoneyY;
    /**
     *
     */
    private String transferMoneyY;
    /**
     * 转账手续费
     */
    private String transferPoundageY;

    /*****************************分润信息参数***********************************/
    /**
     * 类型
     */
    private String transactionType;
    /**
     * 金额
     */
    private String operationBalance;
    /**
     * 消费金额
     */
    private String tradingAmount;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(String withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public String getWithdrawStatus() {
        return withdrawStatus;
    }

    public void setWithdrawStatus(String withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }

    public String getAuditPeople() {
        return auditPeople;
    }

    public void setAuditPeople(String auditPeople) {
        this.auditPeople = auditPeople;
    }

    public long getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(long auditDate) {
        this.auditDate = auditDate;
    }

    public String getAuditRemarks() {
        return auditRemarks;
    }

    public void setAuditRemarks(String auditRemarks) {
        this.auditRemarks = auditRemarks;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferMoney() {
        return transferMoney;
    }

    public void setTransferMoney(String transferMoney) {
        this.transferMoney = transferMoney;
    }

    public String getTransferPeople() {
        return transferPeople;
    }

    public void setTransferPeople(String transferPeople) {
        this.transferPeople = transferPeople;
    }

    public long getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(long transferDate) {
        this.transferDate = transferDate;
    }

    public String getTransferRamarks() {
        return transferRamarks;
    }

    public void setTransferRamarks(String transferRamarks) {
        this.transferRamarks = transferRamarks;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getOpenBankCode() {
        return openBankCode;
    }

    public void setOpenBankCode(String openBankCode) {
        this.openBankCode = openBankCode;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getPaymentReceipt() {
        return paymentReceipt;
    }

    public void setPaymentReceipt(String paymentReceipt) {
        this.paymentReceipt = paymentReceipt;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getWithdrawMoneyY() {
        return withdrawMoneyY;
    }

    public void setWithdrawMoneyY(String withdrawMoneyY) {
        this.withdrawMoneyY = withdrawMoneyY;
    }

    public String getTransferMoneyY() {
        return transferMoneyY;
    }

    public void setTransferMoneyY(String transferMoneyY) {
        this.transferMoneyY = transferMoneyY;
    }

    public String getTransferPoundageY() {
        return transferPoundageY;
    }

    public void setTransferPoundageY(String transferPoundageY) {
        this.transferPoundageY = transferPoundageY;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getOperationBalance() {
        return operationBalance;
    }

    public void setOperationBalance(String operationBalance) {
        this.operationBalance = operationBalance;
    }

    public String getTradingAmount() {
        return tradingAmount;
    }

    public void setTradingAmount(String tradingAmount) {
        this.tradingAmount = tradingAmount;
    }
}
