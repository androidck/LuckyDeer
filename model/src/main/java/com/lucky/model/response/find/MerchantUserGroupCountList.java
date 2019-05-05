package com.lucky.model.response.find;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * 等级信息列表
 *
 * @author wangxiangyi
 * @date 2018/11/16
 */
public class MerchantUserGroupCountList extends AbstractExpandableItem<LevelUserGroupCountList> implements MultiItemEntity, Serializable {
    /**
     * 商户名称
     */
    private String businessName;
    /**
     * 商户人数
     */
    private String businessNumberPeople;

    /***************************************************公共参数**********************************************/
    /**
     * 类型
     */
    private int itemType;
    /**
     *
     */
    private int level;
    /**
     * 是否是最后一条
     */
    private boolean isLastOne;

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessNumberPeople() {
        return businessNumberPeople;
    }

    public void setBusinessNumberPeople(String businessNumberPeople) {
        this.businessNumberPeople = businessNumberPeople;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isLastOne() {
        return isLastOne;
    }

    public void setLastOne(boolean lastOne) {
        isLastOne = lastOne;
    }
}
