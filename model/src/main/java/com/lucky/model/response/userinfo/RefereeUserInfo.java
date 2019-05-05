package com.lucky.model.response.userinfo;

import java.io.Serializable;

/**
 * 推荐人信息
 *
 * @author wangxiangyi
 * @date 2018/11/2
 */
public class RefereeUserInfo implements Serializable {

    /**
     * 推荐人id
     */
    private String refereeId;
    /**
     * 推荐人头像
     */
    private String userHead;
    /**
     * 推荐人编号
     */
    private String userNo;
    /**
     * 推荐人手机号
     */
    private String phone;
    /**
     * 是否显示全的推荐人手机号
     */
    private String isAllphone = "0";
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 等级名称
     */
    private String levelName;

    public String getRefereeId() {
        return refereeId;
    }

    public void setRefereeId(String refereeId) {
        this.refereeId = refereeId;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String isAllphone() {
        return isAllphone;
    }

    public void setIsAllphone(String isAllphone) {
        this.isAllphone = isAllphone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
