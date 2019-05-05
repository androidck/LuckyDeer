package com.lucky.mapfeatures.overlay;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.lucky.mapfeatures.R;
import com.lucky.mapfeatures.navigation.StartThirdPartyNavigation;
import com.lucky.mapfeatures.overlay.interfaces.OnPoiSearchListener;
import com.lucky.mapfeatures.positioning.MapLocationListener;
import com.lucky.mapfeatures.positioning.PositioningFeatures;

import java.util.List;

/**
 * 地图上面功能
 *
 * @author wangxiangyi
 * @date 2018/11/12
 */
public class MapFeatures {
    private static MapFeatures instance;
    private AMapLocation mAmapLocation;

    public static MapFeatures getInstance() {
        if (instance == null) {
            synchronized (MapFeatures.class) {
                if (instance == null) {
                    instance = new MapFeatures();
                }
            }
        }
        return instance;
    }

    /**
     * 设置位置点
     *
     * @param context 上下文
     * @param latLngs 经纬度
     * @return
     */
    public void setPointCover(Context context, AMap aMap, LatLng latLngs, int showImg, boolean isDraggable) {
        setMapCenterPoint(aMap, latLngs);
        aMap.clear();
        aMap.addMarker(new MarkerOptions()
                .position(latLngs)
                .icon(BitmapDescriptorFactory.fromBitmap(
                        BitmapFactory.decodeResource(context.getResources(), showImg)))
                .draggable(isDraggable));

    }

    /**
     * 开始进行poi搜索
     *
     * @param context
     * @param aMap    地图参数
     * @param keyWord 搜索字符串
     */
    public MapFeatures doSearchQuery(final Context context, final AMap aMap, String keyWord, int scenesUsed) {
        return doSearchQuery(context, aMap, keyWord, "", scenesUsed);
    }


    /**
     * 开始进行poi搜索
     *
     * @param context
     * @param aMap       地图参数
     * @param keyWord    搜索字符串
     * @param searchType poi搜索类型
     */
    public MapFeatures doSearchQuery(final Context context, final AMap aMap, String keyWord, String searchType, int scenesUsed) {
        return doSearchQuery(context, aMap, keyWord, searchType, "", scenesUsed);
    }

    /**
     * 开始进行poi搜索
     *
     * @param context
     * @param aMap       地图参数
     * @param keyWord    搜索字符串
     * @param searchType poi搜索类型
     * @param city       要搜索的城市
     */
    public MapFeatures doSearchQuery(final Context context, final AMap aMap, String keyWord, String searchType, String city, int scenesUsed) {
        if (TextUtils.isEmpty(city)) {
            PositioningFeatures.getInstance(context)
                    .setScenesUsed(scenesUsed)
                    .setOnceLocation(true)
                    .create()
                    .setLocationListener(new MapLocationListener() {
                        @Override
                        public void onLocationSuccess(AMapLocation amapLocation, int scenesUsed) {
                            mAmapLocation = amapLocation;
                            switch (scenesUsed) {
                                /*柜台征信*/
                                case 10004:
//                                    searchQuery(context, aMap, keyWord, searchType, amapLocation.getCity());
                                    break;
                                default:
                                    break;
                            }
                        }

                        @Override
                        public void onLocationFailure() {

                        }
                    }).startLocation();
        } else {
//            searchQuery(context, aMap, keyWord, searchType, city);
        }
        return this;
    }

    /**
     * 检索周边信息
     *
     * @param context
     * @param aMap         地图参数
     * @param amapLocation 定位信息
     */
    public void setRetrieveSurrounding(Context context, final AMap aMap, AMapLocation amapLocation, OnPoiSearchListener listener) {
        final PoiSearch.Query query = new PoiSearch.Query("", "餐饮服务|购物服务|生活服务", amapLocation.getCity());
        /*设置每页最多返回多少条poiitem*/
        query.setPageSize(100);
        /*设置查第一页*/
        query.setPageNum(0);
        PoiSearch poiSearch = new PoiSearch(context, query);
        /*设置周边搜索的中心点以及半径*/
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude()), 1000));
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult result, int rCode) {
                if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                    /*搜索poi的结果*/
                    if (result != null && result.getQuery() != null) {
                        /*是否是同一条*/
                        if (result.getQuery().equals(query)) {
                            // 取得搜索到的poiitems有多少页，取得第一页的poiitem数据，页数从数字0开始
                            List<PoiItem> poiItems = result.getPois();
                            if (poiItems != null && poiItems.size() > 0) {
                                /*清理之前的图标*/
                                aMap.clear();
                                if (listener != null) {
                                    listener.onPoiSearched(poiItems);
                                }
                            } else {
                                Toast.makeText(context, "没有搜索到相关信息", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(context, "没有搜索到相关信息", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(context, rCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();
    }


    /**
     * 开始进行poi搜索
     *
     * @param context
     * @param aMap         地图参数
     * @param keyWord      搜索字符串
     * @param amapLocation 定位信息
     */
    public void searchQuery(final Context context, final AMap aMap, String keyWord, AMapLocation amapLocation) {
        searchQuery(context, aMap, keyWord, "", amapLocation, null);
    }

    /**
     * 开始进行poi搜索
     *
     * @param context
     * @param aMap         地图参数
     * @param keyWord      搜索字符串
     * @param amapLocation 定位信息
     * @param listener     回掉参数
     */
    public void searchQuery(final Context context, final AMap aMap, String keyWord, AMapLocation amapLocation, OnPoiSearchListener listener) {
        searchQuery(context, aMap, keyWord, "餐饮服务|购物服务|生活服务", amapLocation, listener);
    }

    /**
     * 开始进行poi搜索
     *
     * @param context
     * @param aMap         地图参数
     * @param keyWord      搜索字符串
     * @param searchType   poi搜索类型
     * @param amapLocation 定位信息
     */
    public void searchQuery(final Context context, final AMap aMap, String keyWord, String searchType, AMapLocation amapLocation, OnPoiSearchListener listener) {
        final PoiSearch.Query query = new PoiSearch.Query(keyWord, searchType, amapLocation.getCity());
        /*设置每页最多返回多少条poiitem*/
        query.setPageSize(100);
        /*设置查第一页*/
        query.setPageNum(0);

        PoiSearch poiSearch = new PoiSearch(context, query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult result, int rCode) {
                if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                    /*搜索poi的结果*/
                    if (result != null && result.getQuery() != null) {
                        /*是否是同一条*/
                        if (result.getQuery().equals(query)) {
                            // 取得搜索到的poiitems有多少页，取得第一页的poiitem数据，页数从数字0开始
                            List<PoiItem> poiItems = result.getPois();
                            if (poiItems != null && poiItems.size() > 0) {
                                /*清理之前的图标*/
                                aMap.clear();
                                if (listener != null) {
                                    listener.onPoiSearched(poiItems);
                                } else {
                                    PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
                                    poiOverlay.removeFromMap();
                                    poiOverlay.addToMap();
                                    poiOverlay.zoomToSpan();
                                    if (amapLocation != null) {
                                        /*设置中心点*/
                                        setMapCenterPoint(aMap, new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
                                    }
                                    /*添加点击marker监听事件*/
                                    aMap.setOnMarkerClickListener(marker -> {
                                        if (TextUtils.isEmpty(marker.getTitle())) {
                                            return true;
                                        } else {
                                            marker.showInfoWindow();
                                            return false;
                                        }
                                    });
                                    /* 添加显示infowindow监听事件*/
                                    aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
                                        @Override
                                        public View getInfoWindow(Marker marker) {
                                            View view = LayoutInflater.from(context).inflate(R.layout.dialog_map_info, null);
                                            TextView title = view.findViewById(R.id.tv_map_title);
                                            title.setText(marker.getTitle());
                                            TextView content = view.findViewById(R.id.tv_map_content);
                                            content.setText(marker.getSnippet());
                                            ImageView ivStartNavigation = view.findViewById(R.id.iv_start_navigation);
                                            if (TextUtils.isEmpty(marker.getTitle())) {
                                                ivStartNavigation.setVisibility(View.GONE);
                                            } else {
                                                ivStartNavigation.setVisibility(View.VISIBLE);
                                            }
                                            // 调起腾讯地图app
                                            ivStartNavigation.setOnClickListener(v -> {
                                                StartThirdPartyNavigation.getInstance(context).setData(marker).dialog();
                                            });
                                            return view;
                                        }

                                        @Override
                                        public View getInfoContents(Marker marker) {
                                            return null;
                                        }
                                    });
                                }
                            } else {
                                Toast.makeText(context, "没有搜索到相关信息", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(context, "没有搜索到相关信息", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(context, rCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();

    }

    /**
     * 设置地图的中心点
     */
    public void setMapCenterPoint(final AMap aMap, LatLng marker1) {
        if (aMap != null) {
            setZoomRatio(aMap, 17);
            /* 设置中心点*/
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
        }
    }

    /**
     * 设置缩放比例
     *
     * @param aMap 地图
     * @param v    缩放比例
     */
    public void setZoomRatio(final AMap aMap, float v) {
        if (aMap != null) {
            aMap.moveCamera(CameraUpdateFactory.zoomTo(v));
        }
    }

}
