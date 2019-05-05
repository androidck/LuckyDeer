package com.lucky.model.request.find;

import com.lucky.model.request.BaseReq;

/**
 * 商户详情请求参数
 *
 * @author wangxiangyi
 * @date 2018/12/3
 */
public class BusinessDetailsReq extends BaseReq {
    private String levelId;
    /**
     * 商户推荐人类型<p>1：直推<p>2：间推传
     */
    private String type;

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
