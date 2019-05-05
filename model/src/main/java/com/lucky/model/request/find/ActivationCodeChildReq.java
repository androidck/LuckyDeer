package com.lucky.model.request.find;


import com.lucky.model.request.BaseReq;

/**
 * 我的激活码子页面接口
 *
 * @author wangxiangyi
 * @date 2019/03/28
 */
public class ActivationCodeChildReq extends BaseReq {
    /**
     * 查询类型<br>
     * all:全部<br>
     * notUse:未使用<br>
     * alreadyUsed:已使用
     */
    private String queryType;

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }
}
