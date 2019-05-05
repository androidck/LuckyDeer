package com.lucky.model.request.quickpay;

import com.lucky.model.request.BaseReq;

/**
 * 查询限额金额
 *
 * @author wangxiangyi
 * @date 2018/11/26
 */
public class QueryBankLimitReq extends BaseReq {

    /**
     * 信用卡id
     */
    private String creditCardId;
    /**
     * 通道id
     */
    private String channelId;

    public String getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(String creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
