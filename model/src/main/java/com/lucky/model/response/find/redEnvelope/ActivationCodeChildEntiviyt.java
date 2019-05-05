package com.lucky.model.response.find.redEnvelope;


import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * 我的激活码子页面返回值
 *
 * @author wangxiangyi
 * @date 2019/03/28
 */
public class ActivationCodeChildEntiviyt implements Serializable, MultiItemEntity {

    /**
     * createDate : 2019-03-28 10:32:12
     * updateDate : 2019-03-28 10:32:15
     * id : 1015
     * code : WE1115
     * levelId : 222
     * companyId : 66b163de67ea43aba793ab6c5ad4b3a0
     * codeStatus : 2
     * receiveUserId : 3bc781a68cbd4728843b8112fc9673e9
     * useCodeUserId : c263ac9d674e46558f89d1cb76cb9951
     * useTime : 2019-03-28 10:32:05
     * companyName : 一路金服
     * receiveName : 000987
     * levelName : VIP会员
     * useCodeUserName : 000635
     * useCodeUserNickName : 值得拥有
     * useCodeUserPhone : 18231712823
     */

    private int itemType;

    private String createDate;
    private String updateDate;
    private String id;
    /***
     * 升级码code
     */
    private String code;
    private String levelId;
    private String companyId;
    /**
     * 升级码状态1. 未使用 2.已使用
     */
    private String codeStatus;
    private String receiveUserId;
    private String useCodeUserId;
    /**
     * 使用时间
     */
    private String useTime;
    private String companyName;
    private String receiveName;
    /**
     * 升级码等级名称
     */
    private String levelName;
    private String useCodeUserName;
    /**
     * 使用人昵称
     */
    private String useCodeUserNickName;
    /**
     * 使用人手机号
     */
    private String useCodeUserPhone;
    /**
     * 使用人头像
     */
    private String useCodeUserHead;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCodeStatus() {
        return codeStatus;
    }

    public void setCodeStatus(String codeStatus) {
        this.codeStatus = codeStatus;
    }

    public String getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getUseCodeUserId() {
        return useCodeUserId;
    }

    public void setUseCodeUserId(String useCodeUserId) {
        this.useCodeUserId = useCodeUserId;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getUseCodeUserName() {
        return useCodeUserName;
    }

    public void setUseCodeUserName(String useCodeUserName) {
        this.useCodeUserName = useCodeUserName;
    }

    public String getUseCodeUserNickName() {
        return useCodeUserNickName;
    }

    public void setUseCodeUserNickName(String useCodeUserNickName) {
        this.useCodeUserNickName = useCodeUserNickName;
    }

    public String getUseCodeUserPhone() {
        return useCodeUserPhone;
    }

    public void setUseCodeUserPhone(String useCodeUserPhone) {
        this.useCodeUserPhone = useCodeUserPhone;
    }

    public String getUseCodeUserHead() {
        return useCodeUserHead;
    }

    public void setUseCodeUserHead(String useCodeUserHead) {
        this.useCodeUserHead = useCodeUserHead;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
