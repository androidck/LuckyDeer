package com.lucky.model.response.find.redEnvelope;

import java.io.Serializable;

/**
 * 红包信息
 *
 * @author wangxiangyi
 * @date 2019/03/29
 */
public class PageBean implements Serializable {
    /**
     * 领取时间
     */
    private String receiveTime;
    /**
     * 发红包人昵称
     */
    private String emitPeopleNickName;
    /**
     * 收款金额
     */
    private String receiveMoney;
    /**
     * 广告内容
     */
    private String context;
    /**
     * 广告主键
     */
    private String advertId;
    /**
     * 领取金额(元)
     */
    private String receiveMoneyY;


    /*****************************************************************************/
    /**
     * issueTime : 2019-03-29 19:44:10.0
     * issueMoney : 0.90
     * issueMoneyY : 0.009
     */
    /**
     * 发放时间
     */
    private String issueTime;
    /**
     * 发布金额
     */
    private String issueMoney;
    /**
     * 发放金额(元)
     */
    private String issueMoneyY;

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getEmitPeopleNickName() {
        return emitPeopleNickName;
    }

    public void setEmitPeopleNickName(String emitPeopleNickName) {
        this.emitPeopleNickName = emitPeopleNickName;
    }

    public String getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(String receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getAdvertId() {
        return advertId;
    }

    public void setAdvertId(String advertId) {
        this.advertId = advertId;
    }

    public String getReceiveMoneyY() {
        return receiveMoneyY;
    }

    public void setReceiveMoneyY(String receiveMoneyY) {
        this.receiveMoneyY = receiveMoneyY;
    }

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    public String getIssueMoney() {
        return issueMoney;
    }

    public void setIssueMoney(String issueMoney) {
        this.issueMoney = issueMoney;
    }

    public String getIssueMoneyY() {
        return issueMoneyY;
    }

    public void setIssueMoneyY(String issueMoneyY) {
        this.issueMoneyY = issueMoneyY;
    }
}
