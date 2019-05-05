package com.lucky.deer.home.map;

import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.lucky.mapfeatures.map.ShowMapView;
import com.lucky.mapfeatures.overlay.MapFeatures;
import com.lucky.mapfeatures.positioning.MapLocationListener;
import com.lucky.mapfeatures.positioning.PositioningFeatures;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.KeyConstant;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;

/**
 * 征信柜台
 *
 * @author wangxiangyi
 * @date 2018/11/12
 */
public class CreditCounterActivity extends BaseActivity {


    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.mv_map)
    MapView mvMap;
    private ShowMapView showMapView;
    private AMap aMap;


    @Override
    protected int initLayout() {
        return R.layout.activity_credit_counter;
    }

    @Override
    protected void getSavedInstanceState(Bundle savedInstanceState) {
        showMapView = ShowMapView.getInstance()
                .initMap(mvMap, savedInstanceState)
                .setMapType(ShowMapView.MAP_TYPE_NORMAL)
                .setMyLocationStyle(ShowMapView.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER)
                .setMyLocationEnabled(true);
        aMap = showMapView.create();
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_map);
    }

    @Override
    protected void initData() {
//        MapFeatures.getInstance().doSearchQuery(mContext, aMap, "征信");
        showLoadingDialog(R.string.dialog_getting_targeting);
        /*提交数据时开启定位*/
        isOpenIntervalPositioning(mContext,
                false,
                KeyConstant.POSITIONING_SCENE_CREDIT_COUNTER,
                new MapLocationListener() {
                    @Override
                    public void onLocationSuccess(AMapLocation amapLocation, int scenesUsed) {
                        dismissLoadingDialog();
                        if (scenesUsed == KeyConstant.POSITIONING_SCENE_CREDIT_COUNTER) {
                            MapFeatures.getInstance().searchQuery(mContext, aMap, "征信", amapLocation);
                        }
                    }

                    @Override
                    public void onLocationFailure() {
                        PositioningFeatures.getInstance(mContext).stopLocation();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (showMapView != null) {
            showMapView.onResume(mvMap);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (showMapView != null) {
            showMapView.onPause(mvMap);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (showMapView != null) {
            showMapView.onSaveInstanceState(mvMap, outState);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (showMapView != null) {
            showMapView.onDestroy(mvMap);
        }
    }
}
