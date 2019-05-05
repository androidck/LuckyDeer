package com.lucky.model.request.userinfo;

import com.lucky.model.request.BaseReq;

/**
 * 个人中心参数
 *
 * @author wangxinagyi
 * @date 2018/11/2
 */
public class MineInfoReq extends BaseReq {
    /***************************修改用户头像*************************/
    /**
     * 用户头像
     */
    private String userHead;
    /***************************修改昵称*************************/
    /**
     * 用户昵称
     */
    private String nickName;
    /***************************给推荐人留言*************************/
    /**
     * 推荐人id
     */
    private String refereeId;
    /**
     * 留言内容
     */
    private String leaveMessageContent;
    /***************************修改用户手机号*************************/
    /**
     * 验证码Id
     */
    private String codeId;
    /**
     * 验证码
     */
    private String code;
    /**
     * 手机号
     */
    private String phone;
    /***************************是否开始接单*************************/
    /**
     * 是否开始接单<p>
     * 1：是<p>
     * 2：否
     */
    private String receiptFlag;
    /**
     * 实时位置（格式：经度，维度）
     */
    private String realTimePosition;

    /***************************用户修改密码*************************/
    /**
     * 旧密码 （md5加密后的）
     */
    private String oldPwd;
    /***************************设置支付密码*************************/
    /**
     * 支付密码（md5加密后的）
     */
    private String paymentPassword;
    /**
     * 新密码 （md5加密后的）
     */
    private String newPwd;
    /***************************修改支付密码*************************/
    /**
     * 旧支付密码（md5加密后的）
     */
    private String oldPaymentPassword;
    /**
     * 新支付密码（md5加密后的）
     */
    private String newPaymentPassword;
    /***************************意见反馈信息*************************/
    /**
     * 反馈内容
     */
    private String feedbackContent;

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRefereeId() {
        return refereeId;
    }

    public void setRefereeId(String refereeId) {
        this.refereeId = refereeId;
    }

    public String getLeaveMessageContent() {
        return leaveMessageContent;
    }

    public void setLeaveMessageContent(String leaveMessageContent) {
        this.leaveMessageContent = leaveMessageContent;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReceiptFlag() {
        return receiptFlag;
    }

    public void setReceiptFlag(String receiptFlag) {
        this.receiptFlag = receiptFlag;
    }

    public String getRealTimePosition() {
        return realTimePosition;
    }

    public void setRealTimePosition(String realTimePosition) {
        this.realTimePosition = realTimePosition;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getPaymentPassword() {
        return paymentPassword;
    }

    public void setPaymentPassword(String paymentPassword) {
        this.paymentPassword = paymentPassword;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getOldPaymentPassword() {
        return oldPaymentPassword;
    }

    public void setOldPaymentPassword(String oldPaymentPassword) {
        this.oldPaymentPassword = oldPaymentPassword;
    }

    public String getNewPaymentPassword() {
        return newPaymentPassword;
    }

    public void setNewPaymentPassword(String newPaymentPassword) {
        this.newPaymentPassword = newPaymentPassword;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }
}
