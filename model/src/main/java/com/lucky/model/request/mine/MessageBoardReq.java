package com.lucky.model.request.mine;

import com.lucky.model.request.BaseReq;

/**
 * 设置是否查看留言
 *
 * @author wangxingyi
 * @date 2018/11/30
 */
public class MessageBoardReq extends BaseReq {
    /**
     * 菜单id
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
