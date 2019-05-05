package com.lucky.model.request.quickpay;

import com.lucky.model.request.BaseReq;

/**
 * 刷卡交易接口参数
 *
 * @author wangxiangyi
 * @date 2018/10/23
 */
public class CreateQuickpayReq extends BaseReq {
    /**
     * 通道ID
     * 是否必填：必填
     */
    private String channelId;
    /**
     * 刷卡金额
     * 是否必填：必填
     */
    private String money;
    /**
     * 储蓄卡ID
     * 是否必填：必填
     */
    private String debitCardId;
    /**
     * 信用卡ID
     * 是否必填：必填
     */
    private String creditCardId;
    /**
     * 前台跳转地址
     * 是否必填：必填
     */
    private String returnUrl;
    /**
     * 城市名称
     * 是否必填：必填
     */
    private String city;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDebitCardId() {
        return debitCardId;
    }

    public void setDebitCardId(String debitCardId) {
        this.debitCardId = debitCardId;
    }

    public String getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(String creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
