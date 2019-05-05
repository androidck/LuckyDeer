package com.lucky.model.response.home.pepayment;

import java.io.Serializable;

/**
 * 代还开卡接口 返回参数
 *
 * @author wangxiangyi
 * @date 2019/01/04
 */
public class OpenCardConfirmEntity implements Serializable {

    /**
     * 商户号
     */
    private String chMerCode;
    /**
     * 应答码 0000 表示成功，其他都是失败
     */
    private String resCode;
    /**
     * 应答描述信息
     */
    private String resMsg;

    public String getChMerCode() {
        return chMerCode;
    }

    public void setChMerCode(String chMerCode) {
        this.chMerCode = chMerCode;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }
}
