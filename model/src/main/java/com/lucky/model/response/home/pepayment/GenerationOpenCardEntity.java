package com.lucky.model.response.home.pepayment;

import java.io.Serializable;

/**
 * 计划详情列表
 *
 * @author wangxiangyi
 * @date 2018/12/14
 */
public class GenerationOpenCardEntity implements Serializable {
    /**
     * 开卡状态：<p>
     * T：开卡成功<p>
     * F：开卡失败
     */
    private String status;
    /**
     * 消息
     */
    private String message;
    /**
     * 是否已经绑定的标识，只有已经绑定的才会返回
     */
    private String trxstatus;
    /**
     * 开卡网页
     */
    private String html;
    /**
     * 短信流水号
     */
    private String tradeNo;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrxstatus() {
        return trxstatus;
    }

    public void setTrxstatus(String trxstatus) {
        this.trxstatus = trxstatus;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
