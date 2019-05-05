package com.lucky.model.response.cardspending;

/**
 * 刷卡交易接口返回数据实体
 */
public class CreateQuickpayEntity {


    /**
     * 支付类型<br>
     * 1：代表开通快捷支付<br>
     * 2：代表交易
     */
    private String createPayType;
    /**
     * 对应的链接
     */
    private String codeUrl;
    /**
     * 是否开通标识<p>
     * true：开通 <p>
     * false：未开通
     */
    private boolean openQuickPayFlag;
    /**
     * 交易状态 <p>
     * PAID：交易成功<p>
     * WAITING：需要开卡<p>
     * CANCELED_OR_TIMEOUT： 交易取消或超时 其他代表未交易
     */
    private String payState;
    /**
     * 默认储蓄卡ID
     */
    private String defaultDebitId;

    public String getCreatePayType() {
        return createPayType;
    }

    public void setCreatePayType(String createPayType) {
        this.createPayType = createPayType;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public boolean getOpenQuickPayFlag() {
        return openQuickPayFlag;
    }

    public void setOpenQuickPayFlag(boolean openQuickPayFlag) {
        this.openQuickPayFlag = openQuickPayFlag;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public String getDefaultDebitId() {
        return defaultDebitId;
    }

    public void setDefaultDebitId(String defaultDebitId) {
        this.defaultDebitId = defaultDebitId;
    }
}

