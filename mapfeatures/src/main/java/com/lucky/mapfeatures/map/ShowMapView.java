package com.lucky.mapfeatures.map;

import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;

/**
 * 显示地图
 *
 * @author wangxiangyi
 * @date 2018/11/12
 */
public class ShowMapView {

    private static ShowMapView instance;
    private AMap mMap;
    /**
     * 是否显示路况（默认显示）
     */
    private boolean mIsShow = true;
    /**
     * 是否显示位置（默认不显示）
     */
    private boolean mIsEnabled = false;
    /**
     * 标准地图模式
     */
    public static int MAP_TYPE_NORMAL = 1;
    /**
     * 卫星地图模式
     */
    public static int MAP_TYPE_SATELLITE = 2;
    /**
     * 夜景模式
     */
    public static int MAP_TYPE_NIGHT = 3;
    /**
     * 地图模式（默认标准地图模式）
     */
    private int mMapType = AMap.MAP_TYPE_NORMAL;
    /**
     * 显示小兰点模式
     */
    private MyLocationStyle myLocationStyle;
    /**
     * 只定位一次。
     */
    public static int LOCATION_TYPE_SHOW = MyLocationStyle.LOCATION_TYPE_SHOW;
    /**
     * 定位一次，且将视角移动到地图中心点。
     */
    public static int LOCATION_TYPE_LOCATE = MyLocationStyle.LOCATION_TYPE_LOCATE;
    /**
     * 连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
     */
    public static int LOCATION_TYPE_FOLLOW = MyLocationStyle.LOCATION_TYPE_FOLLOW;
    /**
     * 连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
     */
    public static int LOCATION_TYPE_MAP_ROTATE = MyLocationStyle.LOCATION_TYPE_MAP_ROTATE;
    /**
     * 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
     */
    public static int LOCATION_TYPE_LOCATION_ROTATE = MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE;
    /**
     * 连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
     */
    public static int LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER = MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER;
    /**
     * 连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
     */
    public static int LOCATION_TYPE_FOLLOW_NO_CENTER = MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER;
    /**
     * 连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
     */
    public static int LOCATION_TYPE_MAP_ROTATE_NO_CENTER = MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER;

    public static ShowMapView getInstance() {
        if (instance == null) {
            synchronized (ShowMapView.class) {
                if (instance == null) {
                    instance = new ShowMapView();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化地图
     *
     * @param view               地图View
     * @param savedInstanceState 保存实例状态
     * @return
     */
    public ShowMapView initMap(MapView view, Bundle savedInstanceState) {
        /*此方法必须重写*/
        view.onCreate(savedInstanceState);
        mMap = view.getMap();
        return this;
    }

    /**
     * 是否显示实时交通状况
     *
     * @param isShow 是否显示路况
     * @return
     */
    public ShowMapView setTrafficEnabled(boolean isShow) {
        mIsShow = isShow;
        return this;
    }

    /**
     * 是否显示我的位置
     *
     * @param isEnabled 是否显示我的位置
     * @return
     */
    public ShowMapView setMyLocationEnabled(boolean isEnabled) {
        mIsEnabled = isEnabled;
        return this;
    }

    /**
     * 地图模式
     *
     * @param mapType 地图模式可选类型:<p>
     *                MAP_TYPE_NORMAL：标准地图模式<p>
     *                MAP_TYPE_SATELLITE：卫星地图模式<p>
     *                MAP_TYPE_NIGHT：夜景模式
     * @return
     */
    public ShowMapView setMapType(int mapType) {
        switch (mapType) {
            case 1:
                /*正常地图模式*/
                mMapType = AMap.MAP_TYPE_NORMAL;
                break;
            case 2:
                /*卫星地图模式*/
                mMapType = AMap.MAP_TYPE_SATELLITE;
                break;
            case 3:
                /*夜景模式*/
                mMapType = AMap.MAP_TYPE_NIGHT;
                break;
            default:
        }
        return this;
    }

    /**
     * 设置小蓝点类型
     *
     * @param locationType 设置小蓝点类型：
     *                     LOCATION_TYPE_SHOW：只定位一次。<p>
     *                     LOCATION_TYPE_LOCATE：定位一次，且将视角移动到地图中心点。<p>
     *                     LOCATION_TYPE_FOLLOW：连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）<p>
     *                     LOCATION_TYPE_MAP_ROTATE：连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）<p>
     *                     LOCATION_TYPE_LOCATION_ROTATE：连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。<p>
     *                     以下三种模式从5.1.0版本开始提供：<p>
     *                     LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER：连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。<p>
     *                     LOCATION_TYPE_FOLLOW_NO_CENTER：连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。<p>
     *                     LOCATION_TYPE_MAP_ROTATE_NO_CENTER：连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。<p>
     * @return
     */
    public ShowMapView setMyLocationStyle(int locationType) {
        /*初始化定位蓝点样式类*/
        myLocationStyle = new MyLocationStyle();
        /* 设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。*/
        myLocationStyle.interval(2000);
        /**
         *  LOCATION_TYPE_SHOW：只定位一次。<p>
         *  LOCATION_TYPE_LOCATE：定位一次，且将视角移动到地图中心点。<p>
         *  LOCATION_TYPE_FOLLOW：连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）<p>
         *  LOCATION_TYPE_MAP_ROTATE：连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）<p>
         *  LOCATION_TYPE_LOCATION_ROTATE：连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。<p>
         *  以下三种模式从5.1.0版本开始提供：
         *  LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER：连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
         *  LOCATION_TYPE_FOLLOW_NO_CENTER：连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
         *  LOCATION_TYPE_MAP_ROTATE_NO_CENTER：连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
         */
        myLocationStyle.myLocationType(locationType);
        /*设置定位蓝点的Style*/
        mMap.setMyLocationStyle(myLocationStyle);
        return this;
    }

    /**
     * 创建地图
     *
     * @return
     */
    public AMap create() {
        /*显示实时交通状况*/
        mMap.setTrafficEnabled(mIsShow);
        /*地图模式*/
        mMap.setMapType(mMapType);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        mMap.setMyLocationEnabled(mIsEnabled);
        return mMap;
    }

    /**
     * 方法必须重写
     */
    public void onResume(MapView mapView) {
        if (mapView != null) {
            mapView.onResume();
        }
    }

    /**
     * 方法必须重写
     */
    public void onPause(MapView mapView) {
        if (mapView != null) {
            mapView.onPause();
        }
    }

    /**
     * 方法必须重写
     */
    public void onSaveInstanceState(MapView mapView, Bundle outState) {
        if (mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
    }

    /**
     * 方法必须重写
     */
    public void onDestroy(MapView mapView) {
        if (mapView != null) {
            mapView.onDestroy();
        }
    }
}
