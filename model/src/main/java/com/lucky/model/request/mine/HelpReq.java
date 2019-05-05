package com.lucky.model.request.mine;

import com.lucky.model.request.BaseReq;

/**
 * 帮助请求接口参数
 *
 * @author wangxiangyi
 * @date 2019/03/15
 */
public class HelpReq extends BaseReq {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
