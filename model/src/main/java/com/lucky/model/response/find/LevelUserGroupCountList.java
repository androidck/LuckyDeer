package com.lucky.model.response.find;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * 等级信息列表
 *
 * @author wangxiangyi
 * @date 2018/11/16
 */
public class LevelUserGroupCountList implements MultiItemEntity, Serializable {

    /**
     * 等级id
     */
    private String levelId;
    /**
     * 等级图片
     */
    private String levelIco;
    /**
     * 等级名称
     */
    private String levelName;
    /**
     * 等级人数
     */
    private String levelCount;
    /**
     * 商户名称
     */
    private String businessName;
  /**
     * 商户推荐人类型<p>1：直推<p>2：间推传
     */
    private String type;
    /***************************************************公共参数**********************************************/
    /**
     * 类型
     */
    private int itemType;

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getLevelIco() {
        return levelIco;
    }

    public void setLevelIco(String levelIco) {
        this.levelIco = levelIco;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelCount() {
        return levelCount;
    }

    public void setLevelCount(String levelCount) {
        this.levelCount = levelCount;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
