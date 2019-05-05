package com.lucky.model.request.home.repayment;

import com.lucky.model.request.BaseReq;

/**
 * 代还开卡接口 返回参数
 *
 * @author wangxiangyi
 * @date 2019/01/04
 */
public class OpenCardConfirmReq extends BaseReq {
    /**
     * 信用卡ID
     */
    private String creditId;
    /**
     * 通道ID
     */
    private String channelId;
    /**
     * 短信流水号
     */
    private String tradeNo;
    /**
     * 短信验证码
     */
    private String smscode;

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getSmscode() {
        return smscode;
    }

    public void setSmscode(String smscode) {
        this.smscode = smscode;
    }
}
