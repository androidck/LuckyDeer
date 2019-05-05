package com.lucky.model.response.userinfo;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author wangxiangyi
 * @date 2018/09/20
 */
public class UserInfo implements Serializable {

    /**
     * 用户id
     */
    private String id;
    /**
     * 用户头像
     */
    private String userHead;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 更新时间
     */
    private String updateDate;
    /**
     * 公司id
     */
    private String companyId;
    /**
     * 用户号
     */
    private String userNo;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 性别：<p>
     * 1：男<p>
     * 2：女
     */
    private String sex;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 登录账户
     */
    private String loginAccount;
    /**
     * 登录状态：<p>
     * 0：未登录<p>
     * 1：登录成功
     */
    private String loginStatus = "0";
    /**
     * 登记国<br/>
     * 1.注册成功<br/>
     * 2.完善信息第一步成功<br/>
     * 3.完善信息第二步成功<br/>
     * 4.提交完成<br/>
     * 5.进件成功<br/>
     * 6.进件失败
     */
    private int registerState;
    /**
     * 是否开启日期还款<p>
     * 还款状态<p>
     * 0.否（不开启） <p>
     * 1.是（开启）
     */
    private String isOpenDateRepayment;
    /**
     * 注册设备型号
     */
    private String registerDeviceModel;
    /**
     * 注册设备Imel
     */
    private String registerDeviceImel;
    /**
     * 注册设备类型
     */
    private String registerDeviceType;
    /**
     * 加密密码
     */
    private String loginPasswordSalt;
    /**
     * 登录密码
     */
    private String loginPassword;
    /**
     * 加密付款密码
     */
    private String paymentPasswordSalt;
    /**
     * 上次登录日期
     */
    private String lastLoginDate;
    /**
     * 推荐代码
     */
    private String recommendCode;
    /**
     * 二维码
     */
    private String qrCode;
    /**
     * 推荐网址
     */
    private String recommendUrl;
    /**
     * 注册方式
     */
    private String registerWay;
    /**
     * 用户类型<p>
     * 1：普通用户 <p>
     * 2：业务员
     */
    private String userType;

    /**
     * 用户类型<p>
     * true：开始接单 <p>
     * false：关闭接单
     */
    private boolean isStartOrdering;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public int getRegisterState() {
        return registerState;
    }

    public void setRegisterState(int registerState) {
        this.registerState = registerState;
    }

    public String getIsOpenDateRepayment() {
        return isOpenDateRepayment;
    }

    public void setIsOpenDateRepayment(String isOpenDateRepayment) {
        this.isOpenDateRepayment = isOpenDateRepayment;
    }

    public String getRegisterDeviceModel() {
        return registerDeviceModel;
    }

    public void setRegisterDeviceModel(String registerDeviceModel) {
        this.registerDeviceModel = registerDeviceModel;
    }

    public String getRegisterDeviceImel() {
        return registerDeviceImel;
    }

    public void setRegisterDeviceImel(String registerDeviceImel) {
        this.registerDeviceImel = registerDeviceImel;
    }

    public String getRegisterDeviceType() {
        return registerDeviceType;
    }

    public void setRegisterDeviceType(String registerDeviceType) {
        this.registerDeviceType = registerDeviceType;
    }

    public String getLoginPasswordSalt() {
        return loginPasswordSalt;
    }

    public void setLoginPasswordSalt(String loginPasswordSalt) {
        this.loginPasswordSalt = loginPasswordSalt;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getPaymentPasswordSalt() {
        return paymentPasswordSalt;
    }

    public void setPaymentPasswordSalt(String paymentPasswordSalt) {
        this.paymentPasswordSalt = paymentPasswordSalt;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getRecommendUrl() {
        return recommendUrl;
    }

    public void setRecommendUrl(String recommendUrl) {
        this.recommendUrl = recommendUrl;
    }

    public String getRegisterWay() {
        return registerWay;
    }

    public void setRegisterWay(String registerWay) {
        this.registerWay = registerWay;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isStartOrdering() {
        return isStartOrdering;
    }

    public void setStartOrdering(boolean startOrdering) {
        isStartOrdering = startOrdering;
    }
}
