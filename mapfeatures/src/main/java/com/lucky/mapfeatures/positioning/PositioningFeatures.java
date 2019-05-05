package com.lucky.mapfeatures.positioning;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.dykj.requestcore.util.PermissionsUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定位功能
 *
 * @author wangxiangyi
 * @date 2018/11/12
 */
public class PositioningFeatures {
    private static PositioningFeatures instance;
    private static Context mContext;

    /**
     * 声明mlocationClient对象
     */
    private AMapLocationClient mlocationClient;
    /**
     * 声明mLocationOption对象
     */
    private AMapLocationClientOption mLocationOption = null;
    /**
     * 是否是需要连续定位<p>
     * true：定位一次<p>
     * false：连续定位
     */
    private boolean mIsOnceLocation;
    /**
     * 设置定位间隔时间，单位为秒（默认为五秒）
     */
    private int mSecond = 2;
    /**
     * 设置场景<p>
     * 1：业务员定位<p>
     * 2：定位回掉
     */
    private int mScenesUsed = 0;
    /**
     * 高精度模式
     */
    private static int HIGHT_ACCURACY = 0;
    /**
     * 低功耗模式
     */
    private static int BATTERY_SAVING = 1;
    /**
     * 仅设备模式
     */
    private static int DEVICE_SENSORS = 2;
    /**
     * 设置定位模式为高精度模式
     */
    private AMapLocationClientOption.AMapLocationMode mMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;


    public static PositioningFeatures getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            synchronized (PositioningFeatures.class) {
                if (instance == null) {
                    instance = new PositioningFeatures();
                }
            }
        }
        return instance;
    }

    /**
     * 设置定位间隔时间
     *
     * @param second 间隔时间（单位：秒）
     * @return
     */
    public PositioningFeatures setInterval(int second) {
        mSecond = second;
        return this;
    }

    /**
     * 设置使用场景
     *
     * @param scenesUsed 设置场景<p>
     *                   1：业务员定位<p>
     *                   2：定位回掉（普通回掉）
     *                   3：金融服务定位回掉
     * @return
     */
    public PositioningFeatures setScenesUsed(int scenesUsed) {
        mScenesUsed = scenesUsed;
        return this;
    }

    /**
     * 是否是需要连续定位<p>
     * true：定位一次<p>
     * false：连续定位
     *
     * @return
     */
    public PositioningFeatures setOnceLocation(boolean isOnceLocation) {
        mIsOnceLocation = isOnceLocation;
        return this;
    }

    /**
     * 设置定位模式为高精度模式<p>
     * HIGHT_ACCURACY：高精度模式<p>
     * BATTERY_SAVING：低功耗模式<p>
     * DEVICE_SENSORS：仅设备模式
     *
     * @return
     */
    public PositioningFeatures setLocationMode(int mode) {
        switch (mode) {
            case 0:
                /*高精度模式*/
                mMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;
                break;
            case 1:
                /*低功耗模式*/
                mMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving;
                break;
            case 2:
                /*仅设备模式*/
                mMode = AMapLocationClientOption.AMapLocationMode.Device_Sensors;
                break;
            default:
        }
        return this;
    }

    /**
     * 设置定位监听
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public PositioningFeatures setLocationListener(final MapLocationListener listener) {
        if (mLocationOption != null) {
            /*设置定位监听*/
            mlocationClient.setLocationListener(amapLocation -> {
                if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                    if (listener != null) {
                        listener.onLocationSuccess(amapLocation, mScenesUsed);
                    }
                    Logger.i("定位信息：\n" + new Gson().toJson(amapLocation));
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
                    Date date = new Date(amapLocation.getTime());
                    /*定位时间*/
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                } else {
                    if (listener != null) {
                        listener.onLocationFailure();
                    }
                    Logger.e(new Throwable("定位失败"), "ErrCode：" + amapLocation.getErrorCode() + "\nerrInfo：" + amapLocation.getErrorInfo());
                }
            });
        }
        return this;
    }

    /**
     * 设置定位参数
     *
     * @return
     */
    @SuppressLint("CheckResult")
    public PositioningFeatures create() {
        PermissionsUtils
                .getInstance(mContext)
                .setPermissionsRequest(Manifest.permission.ACCESS_COARSE_LOCATION
                        , Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        if (mLocationOption == null) {
                            mlocationClient = new AMapLocationClient(mContext);
                            /*初始化定位参数*/
                            mLocationOption = new AMapLocationClientOption();
                        }
                        // 使用连续定位
                        mLocationOption.setOnceLocation(mIsOnceLocation);
                        /*设置定位间隔,单位毫秒,默认为2000ms*/
                        mLocationOption.setInterval(mSecond * 1000);
                        /*设置定位模式*/
                        mLocationOption.setLocationMode(mMode);
                        /*设置参数*/
                        mlocationClient.setLocationOption(mLocationOption);
                    } else {
                        PermissionsUtils.getInstance(mContext).openSettingDetail();
                    }
                });
        return this;
    }

    /**
     * 开启定位功能<p>
     * 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
     * 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
     * 在定位结束后，在合适的生命周期调用onDestroy()方法<p>
     * 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除<p>
     */
    public void startLocation() {
        /* 启动定位*/
        if (mlocationClient != null) {
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    public void stopLocation() {
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
        }
    }

}
