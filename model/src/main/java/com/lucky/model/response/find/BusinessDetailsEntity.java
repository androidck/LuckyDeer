package com.lucky.model.response.find;

import java.io.Serializable;

/**
 * 商户详情返回参数
 *
 * @author wangxiangyi
 * @date 2018/12/3
 */
public class BusinessDetailsEntity implements Serializable {
    /**
     * 等级id
     */
    private String levelId;
    /**
     * 会员名称
     */
    private String levelName;
    /**
     * 头像
     */
    private String userHead;
    /**
     * 账号
     */
    private String userNo;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 性别：<p>
     * 1：男<p>
     * 2：女
     */
    private String sex;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 手机号是否向上下级展示<p>
     * 1：是<p>
     * 2：否
     */
    private String isShowPhone;
    /**
     * 是否实名制认证<p>
     * 0 未实名认证<p>
     * 1 已实名认证
     */
    private String isRealNameAuth;
    /**
     * 推荐人手机号
     */
    private String refereePhone;

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String isShowPhone() {
        return isShowPhone;
    }

    public void setIsShowPhone(String isShowPhone) {
        this.isShowPhone = isShowPhone;
    }

    public String getIsShowPhone() {
        return isShowPhone;
    }

    public String getIsRealNameAuth() {
        return isRealNameAuth;
    }

    public void setIsRealNameAuth(String isRealNameAuth) {
        this.isRealNameAuth = isRealNameAuth;
    }

    public String getRefereePhone() {
        return refereePhone;
    }

    public void setRefereePhone(String refereePhone) {
        this.refereePhone = refereePhone;
    }
}
