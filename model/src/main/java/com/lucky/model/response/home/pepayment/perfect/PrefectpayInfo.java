package com.lucky.model.response.home.pepayment.perfect;

/**
 * 完美养卡计划实体
 *
 * @author wangxiangyi
 * @date 2019/02/21
 */
public class PrefectpayInfo {
    /**
     * 养卡类型<p>
     * 0：消费<p>
     * 1:养卡
     */
    private int repayType;
    /**
     * 上午消费时间
     */
    private long amTime;
    /**
     * 上午消费金额
     */
    private String amPaymoney;
    /**
     * 上午消费状态
     */
    private String amPaymoneyStatus;
    /**
     * 下午消费时间
     */
    private long pmTime;
    /**
     * 下午消费金额
     */
    private String pmPaymoney;
    /**
     * 下午消费状态
     */
    private String pmPaymoneyStatus;
    /**
     * 养卡时间
     */
    private long returnTime;
    /**
     * 养卡金额
     */
    private String returnMoney;
    /**
     * 养卡状态
     */
    private String returnStatus;
    /**
     * 接口失败原因
     */
    private String exceptionDescribe;

    public PrefectpayInfo(int repayType) {
        this.repayType = repayType;
    }

    /**
     * 养卡信息
     *
     * @param repayType    养卡类型
     * @param returnTime   养卡时间
     * @param returnMoney  养卡钱数
     * @param returnStatus 养卡状态
     */
    public PrefectpayInfo(int repayType, long returnTime, String returnMoney, String returnStatus, String exceptionDescribe) {
        this.repayType = repayType;
        this.returnTime = returnTime;
        this.returnMoney = returnMoney;
        this.returnStatus = returnStatus;
        this.exceptionDescribe = exceptionDescribe;
    }

    public int getRepayType() {
        return repayType;
    }

    public void setRepayType(int repayType) {
        this.repayType = repayType;
    }

    public long getAmTime() {
        return amTime;
    }

    public void setAmTime(long amTime) {
        this.amTime = amTime;
    }

    public String getAmPaymoney() {
        return amPaymoney;
    }

    public void setAmPaymoney(String amPaymoney) {
        this.amPaymoney = amPaymoney;
    }

    public String getAmPaymoneyStatus() {
        return amPaymoneyStatus;
    }

    public void setAmPaymoneyStatus(String amPaymoneyStatus) {
        this.amPaymoneyStatus = amPaymoneyStatus;
    }

    public long getPmTime() {
        return pmTime;
    }

    public void setPmTime(long pmTime) {
        this.pmTime = pmTime;
    }

    public String getPmPaymoney() {
        return pmPaymoney;
    }

    public void setPmPaymoney(String pmPaymoney) {
        this.pmPaymoney = pmPaymoney;
    }

    public String getPmPaymoneyStatus() {
        return pmPaymoneyStatus;
    }

    public void setPmPaymoneyStatus(String pmPaymoneyStatus) {
        this.pmPaymoneyStatus = pmPaymoneyStatus;
    }

    public long getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(long returnTime) {
        this.returnTime = returnTime;
    }

    public String getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(String returnMoney) {
        this.returnMoney = returnMoney;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getExceptionDescribe() {
        return exceptionDescribe;
    }

    public void setExceptionDescribe(String exceptionDescribe) {
        this.exceptionDescribe = exceptionDescribe;
    }
}
