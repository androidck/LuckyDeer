package com.lucky.model.request.quickpay;

import com.lucky.model.request.BaseReq;

/**
 * 修改信用卡账单日和还款日请求参数
 *
 * @author wangxiangyi
 * @date 2018/10/24
 */
public class UpdataCardReq extends BaseReq {
    /**
     * 信用卡ID
     */
    private String id;

    /**
     * 需要修改的类型 <p>
     * 1：账单日<p>
     * 2：还款日
     */
    private String type;
    /**
     * 需要修改的内容
     */
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}