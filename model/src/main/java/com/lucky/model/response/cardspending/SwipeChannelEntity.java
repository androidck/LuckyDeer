package com.lucky.model.response.cardspending;

import java.io.Serializable;

/**
 * 获取通道实体
 *
 * @author wangxiangyi
 * @date 2018/10/18
 */
public class SwipeChannelEntity implements Serializable {
    /**
     * 通道ID
     */
    private String channelId;
    /**
     * 通道名称
     */
    private String channelName;
    /**
     * 单笔交易上限
     */
    private String channelSingleLimitUp;
    /**
     * 费率
     */
    private String rate;
    /**
     * 通道类型 1 刷卡 2 代还
     */
    private String channelType;
   /**
     * 商户类型，直接展示这个字段即可
     */
    private String extendedField1;
    /**
     * 银行名称列表
     */
    private String supportBanks;
    /**
     * 是否选中
     */
    private boolean isChecked;
    /**
     * 单笔手续费
     */
    private String fee;
    /**
     * 积分标识<p>
     * 1：有积分<p>
     * 2：无积分
     */
    private String scoreFlag;


    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelSingleLimitUp() {
        return channelSingleLimitUp;
    }

    public void setChannelSingleLimitUp(String channelSingleLimitUp) {
        this.channelSingleLimitUp = channelSingleLimitUp;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getExtendedField1() {
        return extendedField1;
    }

    public void setExtendedField1(String extendedField1) {
        this.extendedField1 = extendedField1;
    }

    public String getSupportBanks() {
        return supportBanks;
    }

    public void setSupportBanks(String supportBanks) {
        this.supportBanks = supportBanks;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getScoreFlag() {
        return scoreFlag;
    }

    public void setScoreFlag(String scoreFlag) {
        this.scoreFlag = scoreFlag;
    }
}
