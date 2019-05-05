package com.lucky.model.request.find.business.redEnvelope;


import com.lucky.model.request.BaseReq;

/**
 * 发红包请求参数
 *
 * @author wangxiangyi
 * @date 2019/03/29
 */
public class RedHandEnvelopesReq extends BaseReq {
    /**
     * 广告内容
     */
    private String context;
    /**
     * 充值金额
     */
    private String rechargeAmount;
    /**
     * 红包个数
     */
    private String redEnvelopesNumber;
    /**
     * 位置名称
     */
    private String locationName;
    /**
     * 经度
     */
    private double longitude;
    /**
     * 纬度
     */
    private double Latitude;
    /**
     * 城市编码
     */
    private String cityCode;
    /**
     * 范围要求
     */
    private String scopeRequirement;
    /**
     * 精确要求
     */
    private String preciseCondition;

    /**
     * 附件图片list
     */
    private String enclosureList;
    /**
     * 附件类型<br/>
     * 1：图片
     */
    private String type;
    /**
     * 附件
     */
    private String enclosures;
    /**
     * 附件排序
     */
    private String enclosureSort;

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

    public String getRedEnvelopesNumber() {
        return redEnvelopesNumber;
    }

    public void setRedEnvelopesNumber(String redEnvelopesNumber) {
        this.redEnvelopesNumber = redEnvelopesNumber;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
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

    public String getEnclosureList() {
        return enclosureList;
    }

    public void setEnclosureList(String enclosureList) {
        this.enclosureList = enclosureList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEnclosures(String s) {
        return enclosures;
    }

    public void setEnclosures(String enclosures) {
        this.enclosures = enclosures;
    }

    public String getEnclosureSort() {
        return enclosureSort;
    }

    public void setEnclosureSort(String enclosureSort) {
        this.enclosureSort = enclosureSort;
    }
}
