package com.lucky.model.request.home.repayment;

import com.lucky.model.request.BaseReq;

/**
 * 申请开卡请求参数
 *
 * @author wangxiangyi
 * @date 2018/12/25
 */
public class OpenCardReq extends BaseReq {
    /**
     * 信用卡id
     */
    public String creditId;
    /**
     * 通道ID
     */
    public String channelId;
    /*********************************提交计划*************************************/
   /**
     * 计划id
     */
    public String planId;

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

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}
