package com.lucky.model.response.find;

import com.lucky.model.response.find.LevelUserGroupCountList;

import java.io.Serializable;
import java.util.List;

/**
 * 发现页面数据实体
 */
public class FindEntity implements Serializable {
    /**
     * 总资产
     */
    private String totalBalance;
    /**
     * 分润
     */
    private String feMoney;
    /**
     * 佣金
     */
    private String yjMoney;
    /**
     * 商户人数
     */
    private String countByNum;
    /**
     * 直推商户名称
     */
    private String directPushTitle;
    /**
     * 直推商户总人数
     */
    private String directPushByNum;
    /**
     * 直推商户列表信息
     */
    public List<LevelUserGroupCountList> directPushLevelGroupList;
    /**
     * 间推商户名称
     */
    private String indirectPushTitle;
    /**
     * 间推商户总人数
     */
    private String indirectPushByNum;
    /**
     * 间推商户列表信息
     */
    public List<LevelUserGroupCountList> indirectPushLevelGroupList;

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getFeMoney() {
        return feMoney;
    }

    public void setFeMoney(String feMoney) {
        this.feMoney = feMoney;
    }

    public String getYjMoney() {
        return yjMoney;
    }

    public void setYjMoney(String yjMoney) {
        this.yjMoney = yjMoney;
    }

    public String getCountByNum() {
        return countByNum;
    }

    public void setCountByNum(String countByNum) {
        this.countByNum = countByNum;
    }

    public String getDirectPushTitle() {
        return directPushTitle;
    }

    public void setDirectPushTitle(String directPushTitle) {
        this.directPushTitle = directPushTitle;
    }

    public String getDirectPushByNum() {
        return directPushByNum;
    }

    public void setDirectPushByNum(String directPushByNum) {
        this.directPushByNum = directPushByNum;
    }

    public List<LevelUserGroupCountList> getDirectPushLevelGroupList() {
        return directPushLevelGroupList;
    }

    public void setDirectPushLevelGroupList(List<LevelUserGroupCountList> directPushLevelGroupList) {
        this.directPushLevelGroupList = directPushLevelGroupList;
    }

    public String getIndirectPushTitle() {
        return indirectPushTitle;
    }

    public void setIndirectPushTitle(String indirectPushTitle) {
        this.indirectPushTitle = indirectPushTitle;
    }

    public String getIndirectPushByNum() {
        return indirectPushByNum;
    }

    public void setIndirectPushByNum(String indirectPushByNum) {
        this.indirectPushByNum = indirectPushByNum;
    }

    public List<LevelUserGroupCountList> getIndirectPushLevelGroupList() {
        return indirectPushLevelGroupList;
    }

    public void setIndirectPushLevelGroupList(List<LevelUserGroupCountList> indirectPushLevelGroupList) {
        this.indirectPushLevelGroupList = indirectPushLevelGroupList;
    }
}
