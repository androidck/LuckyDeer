package com.lucky.mapfeatures.positioning;

import com.amap.api.location.AMapLocation;

/**
 * 定位监听
 *
 * @author wangxiangyi
 * @date 2018/11/12
 */
public interface MapLocationListener {
    /**
     * 成功
     *
     * @param amapLocation
     */
    void onLocationSuccess(AMapLocation amapLocation,int scenesUsed);

    /**
     * 失败
     */
    void onLocationFailure();
}
