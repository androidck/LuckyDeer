package com.lucky.model.response.mine;

import java.io.Serializable;

/**
 * 交易记录列表
 *
 * @author wangxiangyi
 * @date 2018/11/29
 */
public class TransactionRecordList implements Serializable {
    /**
     * 通道名称
     */
    private String channelName;
    /**
     * 交易流水号（根据前台需要进行展示）
     */
    private String consumptiongSeq;
    /**
     * 交易金额（元）
     */
    private String tradingAmount;
    /**
     * 信用卡id
     */
    private String creditId;
    /**
     * 交易卡号 （根据于英设计进行展示）
     */
    private String creditCard;
    /**
     * 交易状态：<p>
     * 1：交易成功<p>
     * 2：交易失败<p>
     * 3：交易申请中<p>
     * 4：其他 其他的时候展示extendedField1
     */
    private String tradingStatus;
    /**
     * 交易异常
     */
    private String extendedField1;
   /**
     * 交易时间
     */
    private String tradingDate;
    /**
     * 银行名称
     */
    private String bankName;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getConsumptiongSeq() {
        return consumptiongSeq;
    }

    public void setConsumptiongSeq(String consumptiongSeq) {
        this.consumptiongSeq = consumptiongSeq;
    }

    public String getTradingAmount() {
        return tradingAmount;
    }

    public void setTradingAmount(String tradingAmount) {
        this.tradingAmount = tradingAmount;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getTradingStatus() {
        return tradingStatus;
    }

    public void setTradingStatus(String tradingStatus) {
        this.tradingStatus = tradingStatus;
    }

    public String getExtendedField1() {
        return extendedField1;
    }

    public void setExtendedField1(String extendedField1) {
        this.extendedField1 = extendedField1;
    }

    public String getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(String tradingDate) {
        this.tradingDate = tradingDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
