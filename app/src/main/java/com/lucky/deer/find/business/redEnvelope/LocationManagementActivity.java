package com.lucky.deer.find.business.redEnvelope;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.PoiItem;
import com.google.gson.Gson;
import com.lucky.mapfeatures.map.ShowMapView;
import com.lucky.mapfeatures.overlay.MapFeatures;
import com.lucky.mapfeatures.positioning.MapLocationListener;
import com.lucky.mapfeatures.positioning.PositioningFeatures;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.find.business.redEnvelope.adapter.SearchForListInfoAdapter;
import com.lucky.deer.find.business.redEnvelope.adapter.TabRedEnvelopeRangeAdapter;
import com.lucky.deer.util.KeyboardUtil;
import com.lucky.model.common.SearchForListInfoEntity;
import com.lucky.model.response.find.redEnvelope.RedEnvelopeCollectionRangeBean;
import com.lucky.model.response.find.redEnvelope.RedEnvelopeCollectionRangeEntity;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

/**
 * 地图检索页面
 *
 * @author wangxiangyi
 * @date 2019/03/27
 */
public class LocationManagementActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.et_search_for)
    EditText etSearchFor;
    @BindView(R.id.btn_start_search_for)
    TextView btnStartSearchFor;
    /**
     * 地图
     */
    @BindView(R.id.mv_map)
    MapView mvMap;
    /**
     * 选项卡
     */
    @BindView(R.id.rv_tab_list)
    RecyclerView rvTabList;
    /**
     * 检索列表
     */
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    /**
     *
     */
    private ShowMapView showMapView;
    /**
     * 地图配置
     */
    private AMap aMap;
    /**
     * 红包范围
     */
    private TabRedEnvelopeRangeAdapter mTabAdapter;
    /**
     * 附近区域
     */
    private SearchForListInfoAdapter mAdapter;
    /**
     * 键盘监听
     */
    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;
    private AMapLocation mAmapLocation;
    /**
     * 选择范围信息
     */
    private RedEnvelopeCollectionRangeBean mSelectRangeInfo;

    private boolean isSelect = true;

    @Override
    protected int initLayout() {
        return R.layout.activity_location_management;
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
        //左边添加返回按钮
        topBar.addLeftImageButton(R.mipmap.back, 0)
                .setOnClickListener(v -> {
                            if (mvMap.getVisibility() == View.GONE) {
                                setData();
                                rvTabList.setVisibility(View.VISIBLE);
                                mvMap.setVisibility(View.VISIBLE);
                            } else {
                                overridePendingTransition(false, true);
                            }
                        }
                );
        /*红包范围*/
        rvTabList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mTabAdapter = new TabRedEnvelopeRangeAdapter();
        rvTabList.setAdapter(mTabAdapter);
        if (getSerializableData() != null) {
            RedEnvelopeCollectionRangeEntity tabData = (RedEnvelopeCollectionRangeEntity) getSerializableData();
            if (tabData.getScopeRestrictions() != null && tabData.getScopeRestrictions().size() > 0) {
                if (tabData.getScopeRestrictions().size() > 1) {
                    /*设置默认范围*/
                    tabData.getScopeRestrictions().get(0).setSelect(true);
                    mSelectRangeInfo = tabData.getScopeRestrictions().get(0);
                }
                mTabAdapter.setNewData(tabData.getScopeRestrictions());
            }
        }
        /*设置布局列表*/
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new SearchForListInfoAdapter();
        rvList.setAdapter(mAdapter);
        /*添加下划线*/
        rvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initListener() {
        /*键盘监听*/
        mOnGlobalLayoutListener = KeyboardUtil.observeSoftKeyboard(mActivity, (softKeyboardHeight, visible) -> {
            if (visible) {
                mAdapter.setNewData(new ArrayList<>());
                rvTabList.setVisibility(View.GONE);
                mvMap.setVisibility(View.GONE);
            }
        });
        /*范围item监听*/
        mTabAdapter.setOnItemClickListener((adapter, view, position) -> {
            for (RedEnvelopeCollectionRangeBean datum : mTabAdapter.getData()) {
                datum.setSelect(false);
            }
            /*获取选择的范围信息*/
            mSelectRangeInfo = mTabAdapter.getData().get(position);
            mSelectRangeInfo.setSelect(true);
            mTabAdapter.notifyDataSetChanged();
        });
        /*点击列表监听*/
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (isSelect) {
                isSelect = false;
                /*设置单选*/
                for (SearchForListInfoEntity datum : mAdapter.getData()) {
                    if (datum.isSelect()) {
                        datum.setSelect(false);
                    }
                }
                mAdapter.getData().get(position).setSelect(true);
                mAdapter.notifyDataSetChanged();
                /*设置位置点位*/
                MapFeatures.getInstance().setPointCover(mContext,
                        aMap,
                        new LatLng(mAdapter.getData().get(position).getLatitude(),
                                mAdapter.getData().get(position).getLongitude()),
                        R.mipmap.poi_marker_pressed,
                        false);
                Observable.timer(500, TimeUnit.MILLISECONDS)
                        .subscribe(aLong -> {
                            /*同调用者一样 需要一个意图 把数据封装起来*/
                            Intent data = new Intent();
                            if (mSelectRangeInfo != null) {
                                mAdapter.getData().get(position).setSelectRangeInfo(mSelectRangeInfo);
                            }
                            data.putExtra(mEntity, mAdapter.getData().get(position));
                            setResult(Activity.RESULT_OK, data);
                            finish();
                        });
            }
        });
    }


    @Override
    protected void initData() {
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
                            /*设置中心点*/
                            MapFeatures.getInstance().setMapCenterPoint(aMap, new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
                            mAmapLocation = amapLocation;
                            setData();
                        }
                    }

                    @Override
                    public void onLocationFailure() {
                        PositioningFeatures.getInstance(mContext).stopLocation();
                    }
                });
    }

    /**
     * 设置周边信息
     */
    public void setData() {
        showLoadingDialog();
        mAdapter.setNewData(new ArrayList<>());
        if (mAmapLocation != null) {
            MapFeatures.getInstance().setRetrieveSurrounding(mContext, aMap, mAmapLocation, poiItems -> {
                System.out.println("附近信息==" + new Gson().toJson(poiItems));
                dismissLoadingDialog();
                List<SearchForListInfoEntity> list = new ArrayList<>();
                if (poiItems != null && poiItems.size() > 0) {
                    for (PoiItem poiItem : poiItems) {
                        SearchForListInfoEntity entity = new SearchForListInfoEntity();
                        if (poiItem != null) {
                            /*检索标题*/
                            entity.setTitle(poiItem.getTitle());
                            /*检索地址*/
                            entity.setSnippet(poiItem.getSnippet());
                            /*纬度*/
                            entity.setLatitude(poiItem.getLatLonPoint().getLatitude());
                            /*经度*/
                            entity.setLongitude(poiItem.getLatLonPoint().getLongitude());
                            /*城市编码*/
                            entity.setAdCode(poiItem.getAdCode());
                            /*是否显示单选框*/
                            entity.setShow(true);
                        }
                        list.add(entity);
                    }
                    mAdapter.setNewData(list);
                }
            });
        }
    }

    @OnClick(R.id.btn_start_search_for)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etSearchFor.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, "请输入搜索地点");
            return;
        }
        MapFeatures.getInstance()
                .searchQuery(mContext,
                        aMap,
                        etSearchFor.getText().toString().trim(),
                        mAmapLocation,
                        poiItems -> {
                            List<SearchForListInfoEntity> list = new ArrayList<>();
                            if (poiItems != null && poiItems.size() > 0) {
                                for (PoiItem poiItem : poiItems) {
                                    SearchForListInfoEntity entity = new SearchForListInfoEntity();
                                    if (poiItem != null) {
                                        /*检索标题*/
                                        entity.setTitle(poiItem.getTitle());
                                        /*检索地址*/
                                        entity.setSnippet(poiItem.getSnippet());
                                        /*纬度*/
                                        entity.setLatitude(poiItem.getLatLonPoint().getLatitude());
                                        /*经度*/
                                        entity.setLongitude(poiItem.getLatLonPoint().getLongitude());
                                        /*是否显示单选框*/
                                        entity.setShow(false);
                                    }
                                    list.add(entity);
                                }
                                mAdapter.setNewData(list);
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
        KeyboardUtil.removeSoftKeyboardObserver(mActivity, mOnGlobalLayoutListener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        return super.onKeyDown(keyCode, event);
    }
}
