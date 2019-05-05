package com.lucky.model.request.home.cardLife;


import com.lucky.model.request.BaseReq;

/**
 * 提现页面请求参数
 *
 * @author wangxiangyi
 * @date 2018/11/15
 */
public class FindWithdrawalReq extends BaseReq {
    /**
     * 授权码参数
     */
    private String code;
    /**
     * 储金卡id
     */
    private String debitCardId;
    /**
     * 提现金额
     */
    private String money;


    /*****************************获取提现状态*********************************/
    /**
     * 唯一标识
     */
    private String id;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDebitCardId() {
        return debitCardId;
    }

    public void setDebitCardId(String debitCardId) {
        this.debitCardId = debitCardId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
