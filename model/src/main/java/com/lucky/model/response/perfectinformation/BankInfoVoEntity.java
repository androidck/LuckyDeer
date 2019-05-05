package com.lucky.model.response.perfectinformation;

import java.io.Serializable;

/**
 * 总行信息
 *
 * @author wangxiangyi
 * @date 2018/10/19
 */
public class BankInfoVoEntity implements Serializable {
    /**
     * 银行id
     */
    private String id;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行名称缩写
     */
    private String bankShortName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankShortName() {
        return bankShortName;
    }

    public void setBankShortName(String bankShortName) {
        this.bankShortName = bankShortName;
    }
}
