package com.lucky.model.response.home;

import java.io.Serializable;

/**
 * 获取注册状态实体
 *
 * @author wangxiangyi
 * @date 2019/01/06
 */
public class QueryRegisterStateEntity implements Serializable {

    /**
     * registerState : 5
     * isOpenDateRepayment : 0
     */
    /**
     * 注册状态
     */
    private String registerState;
    /**
     * 是否开启日期还款<p>
     * 还款状态<p>
     * 0.否（不开启） <p>
     * 1.是（开启）
     */
    private String isOpenDateRepayment;

    public String getRegisterState() {
        return registerState;
    }

    public void setRegisterState(String registerState) {
        this.registerState = registerState;
    }

    public String getIsOpenDateRepayment() {
        return isOpenDateRepayment;
    }

    public void setIsOpenDateRepayment(String isOpenDateRepayment) {
        this.isOpenDateRepayment = isOpenDateRepayment;
    }
}
