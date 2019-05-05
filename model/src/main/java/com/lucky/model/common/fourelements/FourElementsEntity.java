package com.lucky.model.common.fourelements;

/**
 * 四要素认账返回实体
 *
 * @author wangxiangyi
 * @date 2019/01/30
 */
public class FourElementsEntity {

    /**
     * 银行名称
     */
    private String bankname;
    /**
     * 银行卡名称
     */
    private String cardname;
    /**
     * 卡类型
     */
    private String cardtype;

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }
}
