package com.lucky.model.request.mine;

import com.lucky.model.request.BaseReq;

/**
 * 更多设置请求参数
 *
 * @author wangxiangyi
 * @date 2018/11/30
 */
public class MoreSettingReq extends BaseReq {
    /**
     * 是否下级查看手机号<p>
     * 1：允许<p>
     * 2：不允许
     */
    private String extendOne;

    public String getExtendOne() {
        return extendOne;
    }

    public void setExtendOne(String extendOne) {
        this.extendOne = extendOne;
    }
}
