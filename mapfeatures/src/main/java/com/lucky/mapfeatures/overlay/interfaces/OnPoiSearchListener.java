package com.lucky.mapfeatures.overlay.interfaces;

import com.amap.api.services.core.PoiItem;

import java.util.List;

public interface OnPoiSearchListener {
    void onPoiSearched(List<PoiItem> poiItems);
}
