package com.lucky.model.response.cardspending;

import java.io.Serializable;

/**
 * 查询限额金额
 *
 * @author wangxiangyi
 * @date 2018/11/26
 */
public class QueryBankLimitEntity implements Serializable {

    private String createDate;
    private String updateDate;
    private String id;
    private String bankId;
    private String cardType;
    private String tradingLimitUp;
    private String tradingLimitLow;
    private String dailyLimit;
    private String monthLimit;
    private String channelId;
    /**
     * 交易最大限额
     */
    private String tradingLimitUpY;
    /**
     * 交易最小限额
     */
    private String tradingLimitLowY;
    /**
     * 每日限额
     */
    private String dailyLimitY;
    /**
     * 每月限额
     */
    private String monthLimitY;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getTradingLimitUp() {
        return tradingLimitUp;
    }

    public void setTradingLimitUp(String tradingLimitUp) {
        this.tradingLimitUp = tradingLimitUp;
    }

    public String getTradingLimitLow() {
        return tradingLimitLow;
    }

    public void setTradingLimitLow(String tradingLimitLow) {
        this.tradingLimitLow = tradingLimitLow;
    }

    public String getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(String dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public String getMonthLimit() {
        return monthLimit;
    }

    public void setMonthLimit(String monthLimit) {
        this.monthLimit = monthLimit;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTradingLimitUpY() {
        return tradingLimitUpY;
    }

    public void setTradingLimitUpY(String tradingLimitUpY) {
        this.tradingLimitUpY = tradingLimitUpY;
    }

    public String getTradingLimitLowY() {
        return tradingLimitLowY;
    }

    public void setTradingLimitLowY(String tradingLimitLowY) {
        this.tradingLimitLowY = tradingLimitLowY;
    }

    public String getDailyLimitY() {
        return dailyLimitY;
    }

    public void setDailyLimitY(String dailyLimitY) {
        this.dailyLimitY = dailyLimitY;
    }

    public String getMonthLimitY() {
        return monthLimitY;
    }

    public void setMonthLimitY(String monthLimitY) {
        this.monthLimitY = monthLimitY;
    }
}
