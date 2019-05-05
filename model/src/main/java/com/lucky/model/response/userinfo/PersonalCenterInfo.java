package com.lucky.model.response.userinfo;

import java.io.Serializable;

/**
 * 个人中心信息
 *
 * @author wangxiangyi
 * @date 2018/11/2
 */
public class PersonalCenterInfo implements Serializable {
    /**
     * 编号
     */
    private String userNo;
    /**
     * 用户头像
     */
    private String userHead;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 积分
     */
    private String integralCalculus;
    /**
     * 用户等级id
     */
    private String membersLevelId;
    /**
     * 等级名称
     */
    private String levelName;
    /**
     * 推荐人
     */
    private String refereeUser;
    /**
     * 更多设置是否向上下级显示手机号开关<p>
     * 1 ：是 <p>
     * 2 ：否
     */
    private String extendOne;
    /**
     * 是否允许上下级查看手机号<p>
     * 1 ：是 <p>
     * 2 ：否
     */
    private String refereeExtendOne;
    /**
     * 推荐人手机号
     */
    private String refereePhone;
    /**
     * 储蓄卡数量
     */
    private String debitCardCount;
    /**
     * 信用卡数量
     */
    private String creditCardCount;
    /**
     * 客服电话
     */
    private String telephone;
    /**
     * 待办数量
     */
    private String needTo;
    /**
     * 是否可以信息补录<p>
     * 1：可以 <p>
     * 2：不可以
     */
    private String extendTwo;

    /**
     * 注册状态
     */
    private String registerState;

    public String getRegisterState() {
        return registerState;
    }

    public void setRegisterState(String registerState) {
        this.registerState = registerState;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

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

    public String getIntegralCalculus() {
        return integralCalculus;
    }

    public void setIntegralCalculus(String integralCalculus) {
        this.integralCalculus = integralCalculus;
    }

    public String getMembersLevelId() {
        return membersLevelId;
    }

    public void setMembersLevelId(String membersLevelId) {
        this.membersLevelId = membersLevelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getRefereeUser() {
        return refereeUser;
    }

    public void setRefereeUser(String refereeUser) {
        this.refereeUser = refereeUser;
    }

    public String getExtendOne() {
        return extendOne;
    }

    public void setExtendOne(String extendOne) {
        this.extendOne = extendOne;
    }

    public String getRefereeExtendOne() {
        return refereeExtendOne;
    }

    public void setRefereeExtendOne(String refereeExtendOne) {
        this.refereeExtendOne = refereeExtendOne;
    }

    public String getRefereePhone() {
        return refereePhone;
    }

    public void setRefereePhone(String refereePhone) {
        this.refereePhone = refereePhone;
    }

    public String getDebitCardCount() {
        return debitCardCount;
    }

    public void setDebitCardCount(String debitCardCount) {
        this.debitCardCount = debitCardCount;
    }

    public String getCreditCardCount() {
        return creditCardCount;
    }

    public void setCreditCardCount(String creditCardCount) {
        this.creditCardCount = creditCardCount;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNeedTo() {
        return needTo;
    }

    public void setNeedTo(String needTo) {
        this.needTo = needTo;
    }

    public String getExtendTwo() {
        return extendTwo;
    }

    public void setExtendTwo(String extendTwo) {
        this.extendTwo = extendTwo;
    }

    @Override
    public String toString() {
        return "PersonalCenterInfo{" +
                "userNo='" + userNo + '\'' +
                ", userHead='" + userHead + '\'' +
                ", nickName='" + nickName + '\'' +
                ", integralCalculus='" + integralCalculus + '\'' +
                ", membersLevelId='" + membersLevelId + '\'' +
                ", levelName='" + levelName + '\'' +
                ", refereeUser='" + refereeUser + '\'' +
                ", debitCardCount='" + debitCardCount + '\'' +
                ", creditCardCount='" + creditCardCount + '\'' +
                ", telephone='" + telephone + '\'' +
                ", needTo='" + needTo + '\'' +
                '}';
    }
}
