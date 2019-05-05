package com.lucky.model.response.perfectinformation;

import java.io.Serializable;

/**
 * 获取支行名称信息
 *
 * @author wangxiangyi
 * @date 2018/10/18
 */
public class BankBranchEntity implements Serializable {
    /**
     * 银行名称
     */
    private String name;
    /**
     * 上一级别银行id
     */
    private String pId;
    /**
     * 支行银行id
     */
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
