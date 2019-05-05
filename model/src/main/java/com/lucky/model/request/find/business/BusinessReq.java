package com.lucky.model.request.find.business;

import com.lucky.model.request.BaseReq;

/**
 * 商机参数
 *
 * @author wangxiangyi
 * @date 2019/03/29
 */
public class BusinessReq extends BaseReq {
    /**
     * 经度
     */
    private double longitude;
    /**
     * 纬度
     */
    private double latitude;
    /*****************************抢红包接口***************************************/
    /**
     * 红包广告id
     */
    private String redEnvelopeAdvertisingId;
    /**
     * 城市编码
     */
    private String cityCode;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getRedEnvelopeAdvertisingId() {
        return redEnvelopeAdvertisingId;
    }

    public void setRedEnvelopeAdvertisingId(String redEnvelopeAdvertisingId) {
        this.redEnvelopeAdvertisingId = redEnvelopeAdvertisingId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
