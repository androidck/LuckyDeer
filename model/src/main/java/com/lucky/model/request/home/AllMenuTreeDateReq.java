package com.lucky.model.request.home;

import com.lucky.model.request.BaseReq;

/**
 * 获取所有菜单参数
 *
 * @author wangxingyi
 * @date 2018/11/09
 */
public class AllMenuTreeDateReq extends BaseReq {
    /**
     * 菜单id
     */
    private String parentId;
    /**
     *
     * 需要返回的层级数（比如你传1），则只根据父id返回该菜单下子菜单，不包括子菜单下的菜单
     */
    private String level;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
