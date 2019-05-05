package com.lucky.model.response.find;

import java.io.Serializable;
import java.util.List;

/**
 * 商机列表信息
 *
 * @author wangxiangyi
 * @date 2019/03/29
 */
public class BusinessList implements Serializable {


    /**
     * createDate : 2019-03-29 11:35:19
     * id : b5943caf175c489087968a6848ec8832
     * userId : 3bc781a68cbd4728843b8112fc9673e9
     * context : 嗯、嗯
     * rechargeAmount : 50000
     * totalMoney : 45000
     * surplusMoney : 45000
     * redEnvelopesNumber : 1
     * surplusRedEnvelopesNumber : 1
     * locationName : 禧悦东方酒店松露自助餐厅
     * longitude : 117.125064
     * latitude : 36.681897
     * citycode : 370102
     * scopeRequirement : 1
     * preciseCondition : -1
     * redEnvelopeState : 1
     * paymentStatus : 1
     * browseVolume : 0
     * rechargeAmountY : 500.00
     * totalMoneyY : 450.00
     * surplusMoneyY : 450.00
     * nickName : Fggg
     * userHead : http://img.minmai1688.com/crop_photo
     * enclosures : [{"createDate":"2019-03-29 11:35:19"}]
     */
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 红包广告id
     */
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 红包广告内容
     */
    private String context;
    /**
     * 充值金额 （分）
     */
    private String rechargeAmount;
    /**
     * 实际红包金额
     */
    private String totalMoney;
    /**
     * 剩余金额
     */
    private String surplusMoney;
    /**
     * 红包个数
     */
    private String redEnvelopesNumber;
    /**
     * 剩余红包个数
     */
    private String surplusRedEnvelopesNumber;
    /**
     * 位置名称
     */
    private String locationName;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 城市编码
     */
    private String citycode;
    /**
     * 范围要求
     */
    private String scopeRequirement;
    /**
     * 精度要求
     */
    private String preciseCondition;
    /**
     * 红包状态
     */
    private String redEnvelopeState;
   /**
     * 领取状态
     */
    private String receivingRemarks;
    /**
     * 支付方式
     */
    private String paymentMethod;
    /**
     * 支付状态
     */
    private String paymentStatus;
    /**
     * 支付宝交易号
     */
    private String tradeNo;
    /**
     * 订单状态
     */
    private String orederNo;
    /**
     * 浏览量
     */
    private String browseVolume;
    /**
     * 充值金额（元）
     */
    private String rechargeAmountY;
    /**
     * 实际红包金额 （元 ）
     */
    private String totalMoneyY;
    /**
     * 距离
     */
    private String distance;
    /**
     *
     */
    private String surplusMoneyY;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户头像
     */
    private String userHead;
    /**
     * 附件图片list
     */
    private List<EnclosuresBean> enclosures;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(String rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getSurplusMoney() {
        return surplusMoney;
    }

    public void setSurplusMoney(String surplusMoney) {
        this.surplusMoney = surplusMoney;
    }

    public String getRedEnvelopesNumber() {
        return redEnvelopesNumber;
    }

    public void setRedEnvelopesNumber(String redEnvelopesNumber) {
        this.redEnvelopesNumber = redEnvelopesNumber;
    }

    public String getSurplusRedEnvelopesNumber() {
        return surplusRedEnvelopesNumber;
    }

    public void setSurplusRedEnvelopesNumber(String surplusRedEnvelopesNumber) {
        this.surplusRedEnvelopesNumber = surplusRedEnvelopesNumber;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getScopeRequirement() {
        return scopeRequirement;
    }

    public void setScopeRequirement(String scopeRequirement) {
        this.scopeRequirement = scopeRequirement;
    }

    public String getPreciseCondition() {
        return preciseCondition;
    }

    public void setPreciseCondition(String preciseCondition) {
        this.preciseCondition = preciseCondition;
    }

    public String getRedEnvelopeState() {
        return redEnvelopeState;
    }

    public void setRedEnvelopeState(String redEnvelopeState) {
        this.redEnvelopeState = redEnvelopeState;
    }

    public String getReceivingRemarks() {
        return receivingRemarks;
    }

    public void setReceivingRemarks(String receivingRemarks) {
        this.receivingRemarks = receivingRemarks;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOrederNo() {
        return orederNo;
    }

    public void setOrederNo(String orederNo) {
        this.orederNo = orederNo;
    }

    public String getBrowseVolume() {
        return browseVolume;
    }

    public void setBrowseVolume(String browseVolume) {
        this.browseVolume = browseVolume;
    }

    public String getRechargeAmountY() {
        return rechargeAmountY;
    }

    public void setRechargeAmountY(String rechargeAmountY) {
        this.rechargeAmountY = rechargeAmountY;
    }

    public String getTotalMoneyY() {
        return totalMoneyY;
    }

    public void setTotalMoneyY(String totalMoneyY) {
        this.totalMoneyY = totalMoneyY;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getSurplusMoneyY() {
        return surplusMoneyY;
    }

    public void setSurplusMoneyY(String surplusMoneyY) {
        this.surplusMoneyY = surplusMoneyY;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public List<EnclosuresBean> getEnclosures() {
        return enclosures;
    }

    public void setEnclosures(List<EnclosuresBean> enclosures) {
        this.enclosures = enclosures;
    }
}
