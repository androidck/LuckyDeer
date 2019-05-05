package com.lucky.sharingfunction.weixinshare.entity;

/**
 * 微信实体
 *
 * @author wangxiangyi
 * @date 2018/09/30
 */
public class WeiXinEntity {
    /**
     * 自定义状态码
     */
    private int errCode;
    /**
     * 自定义状态
     */
    private String errStr;
    /**
     * 标志
     */
    private String openId;
    private String transaction;

    public int getCode() {
        return errCode;
    }

    public void setCode(int errCode) {
        this.errCode = errCode;
    }

    public String getStr() {
        return errStr;
    }

    public void setStr(String errStr) {
        this.errStr = errStr;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "WeiXinEntity{" +
                "errCode=" + errCode +
                ", errStr='" + errStr + '\'' +
                ", openId='" + openId + '\'' +
                ", transaction='" + transaction + '\'' +
                '}';
    }
}
