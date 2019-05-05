package com.lucky.mapfeatures.navigation;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.NaviPara;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * 启动第三方导航
 *
 * @author wangxiangyi
 * @date 2018/12/13
 */
public class StartThirdPartyNavigation {


    private static StartThirdPartyNavigation instances;
    private static Context mContext;
    /**
     * 高德地图包名
     */
    private String gaoDePackageName = "com.autonavi.minimap";
    /**
     * 百度地图包名
     */
    private String baiDuPackageName = "com.baidu.BaiduMap";
    /**
     * 谷歌地图包名
     */
    private String guGePackageName = "com.google.android.apps.maps";
    /**
     * 腾讯地图包名
     */
    private String tengXinPackageName = "com.tencent.map";
    /**
     * 纬度
     */
    private double latitude;
    /**
     * 经度
     */
    private double longitude;
    /**
     * 检索目标
     */
    private String title;
    private Marker mMarker;

    public static StartThirdPartyNavigation getInstance(Context context) {
        mContext = context;
        if (instances == null) {
            synchronized (StartThirdPartyNavigation.class) {
                if (instances == null) {
                    instances = new StartThirdPartyNavigation();
                }
            }
        }
        return instances;
    }

    public StartThirdPartyNavigation setData(Marker marker) {
        mMarker = marker;
        if (marker != null) {
            title = marker.getTitle();
            if (marker.getPosition() != null) {
                /*纬度*/
                latitude = marker.getPosition().latitude;
                /*经度*/
                longitude = marker.getPosition().longitude;
            }
        }
        return this;
    }

    public StartThirdPartyNavigation dialog() {
        new QMUIBottomSheet
                .BottomListSheetBuilder(mContext)
                .addItem("高德地图")
                .addItem("百度地图")
                .addItem("腾讯地图")
                .addItem("谷歌地图")
                .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                    dialog.dismiss();
                    switch (position) {
                        case 0:
                            startGaoDeMapNavigation();
                            break;
                        case 1:
                            startBaiduMapNavigation();
                            break;
                        case 2:
                            startTengXunMapNavigation();
                            break;
                        case 3:
                            startGuGeMapNavigation();
                            break;
                        default:
                    }
                })
                .build()
                .show();
        return this;
    }

    /**
     * 百度地图导航
     */
    public void startBaiduMapNavigation() {
        /*传入指定包名应用*/
        if (isAvilible(getContext(), baiDuPackageName)) {
            try {
//                          intent = Intent.getIntent("intent://map/direction?origin=latlng:34.264642646862,108.95108518068|name:我家&destination=大雁塔&mode=driving®ion=西安&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                Intent intent = Intent.getIntent("intent://map/direction?" +
                        /*此处不传值默认选择当前位置*/
                        //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点
                        /*终点*/
                        "destination=latlng:" + latitude + "," + longitude + "|name:" + title +
                        /*导航路线方式*/
                        "&mode=driving&" +
                        "region=北京" +
                        "&src=慧医#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                /*//启动调用*/
                getContext().startActivity(intent);
            } catch (URISyntaxException e) {
                Logger.e(e.getMessage());
            }
        } else {//未安装
            //显示手机上所有的market商店
            Toast.makeText(getContext(), "您尚未安装百度地图", Toast.LENGTH_LONG).show();
            /*market为路径，id为包名*/
            Uri uri = Uri.parse("market://details?id=" + baiDuPackageName);
            getContext().startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }

    /**
     * 高德地图导航
     */
    public void startGaoDeMapNavigation() {
        // 构造导航参数
        NaviPara naviPara = new NaviPara();
        // 设置终点位置
        naviPara.setTargetPoint(mMarker.getPosition());
        // 设置导航策略，这里是避免拥堵
        naviPara.setNaviStyle(NaviPara.DRIVING_AVOID_CONGESTION);
        // 调起高德地图导航
        try {
            AMapUtils.openAMapNavi(naviPara, getContext());
        } catch (com.amap.api.maps.AMapException e) {
            // 如果没安装会进入异常，调起下载页面
            AMapUtils.getLatestAMapApp(getContext());
        }
    }

    /**
     * 腾讯地图导航
     */
    public void startTengXunMapNavigation() {
        if (isAvilible(getContext(), tengXinPackageName)) {
            Intent naviIntent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("qqmap://map/routeplan?type=drive&from=&fromcoord=&to=" + title + "&tocoord=" + latitude + "," + longitude + "&policy=0&referer=appName"));
            getContext().startActivity(naviIntent);
        } else {
            Toast.makeText(getContext(), "您尚未安装腾讯地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=" + tengXinPackageName);
            getContext().startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }

    }

    /**
     * 谷歌地图导航
     */
    public void startGuGeMapNavigation() {
        if (isAvilible(getContext(), guGePackageName)) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude + ", + Sydney +Australia");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage(guGePackageName);
            getContext().startActivity(mapIntent);
        } else {
            Toast.makeText(getContext(), "您尚未安装谷歌地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=" + guGePackageName);
            getContext().startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }

    private static Context getContext() {
        return mContext;
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName 应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }
}
