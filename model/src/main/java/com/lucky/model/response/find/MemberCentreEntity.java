package com.lucky.model.response.find;

import java.io.Serializable;
import java.util.List;

/**
 * 会员中心功能
 *
 * @author wangxiangyi
 * @date 2018/11/15
 */
public class MemberCentreEntity implements Serializable {

    /**
     * userNo : 10000007
     * userHead : http://pgij574mb.bkt.clouddn.com/FmIqUrgIfXD7fO0pKVOIF7ESTKJK
     * nickName : 它可以接受自
     * integralCalculus : 0
     * membersLevelId : 111
     * levelName : 青铜
     * levelRatio : 0.25
     */
    /**
     * 会员账号
     */
    private String userNo;
    /**
     * 会员头像
     */
    private String userHead;
    /**
     * 会员昵称
     */
    private String nickName;
    /**
     * 积分
     */
    private String integralCalculus;
    /**
     * 会员等级id
     */
    private String membersLevelId;
    /**
     * 会员等级名称
     */
    private String levelName;
    /**
     * 会员等级
     */
    private double levelRatio;
    /**
     * 会员购买信息
     */
    private List<PurchaseMemberEntity> list;



    private String  beginLevel;

    private String endLevel;

    public String getBeginLevel() {
        return beginLevel;
    }

    public void setBeginLevel(String beginLevel) {
        this.beginLevel = beginLevel;
    }

    public String getEndLevel() {
        return endLevel;
    }

    public void setEndLevel(String endLevel) {
        this.endLevel = endLevel;
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

    public double getLevelRatio() {
        return levelRatio;
    }

    public void setLevelRatio(double levelRatio) {
        this.levelRatio = levelRatio;
    }

    public List<PurchaseMemberEntity> getList() {
        return list;
    }

    public void setList(List<PurchaseMemberEntity> list) {
        this.list = list;
    }
}
