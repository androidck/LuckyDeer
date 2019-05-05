package com.lucky.model.response.find;

import java.io.Serializable;

/**
 * 领取红包的人数信息
 *
 * @author wangxiangyi
 * @date 2019/03/29
 */
public class ReceiveRedEnvelopesUserVOListBean implements Serializable {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户头像
     */
    private String userHead;
    /**
     * 用户领取金额
     */
    private String money;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
