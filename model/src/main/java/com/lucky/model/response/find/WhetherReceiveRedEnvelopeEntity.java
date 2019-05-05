package com.lucky.model.response.find;

import java.io.Serializable;

/**
 * 是否领取红包返回参数
 *
 * @author wangxiangyi
 * @date 2019/03/29
 */
public class WhetherReceiveRedEnvelopeEntity implements Serializable {
    /**
     * 是否可以领取红包<br/>
     * 1：可以领取 <br/>
     * 0：不可以进行领取
     */
    private String isReceiptConditions;

    public String isReceiptConditions() {
        return isReceiptConditions;
    }

    public void setIsReceiptConditions(String isReceiptConditions) {
        this.isReceiptConditions = isReceiptConditions;
    }


}
