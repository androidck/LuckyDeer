package com.lucky.model.common;

import com.lucky.model.response.find.redEnvelope.RedEnvelopeCollectionRangeBean;

import java.io.Serializable;

/**
 * 检索信息实体
 *
 * @author wangxiangyi
 * @date 2019/03/07
 */
public class SearchForListInfoEntity implements Serializable {


    /**
     * 检索标题
     */
    private String title;
    /**
     * 检索地址
     */
    private String snippet;
    /**
     * 纬度
     */
    private double latitude;
    /**
     * 经度
     */
    private double longitude;
    /**
     * 城市编码
     */
    private String adCode;
    /**
     * 选择范围信息
     */
    private RedEnvelopeCollectionRangeBean selectRangeInfo;
    /**
     * 是否选中<br/>
     * true：选中<br/>
     * false：未选中
     */
    private boolean isSelect;
    /**
     * 是否显示单选框<br/>
     * true：显示<br/>
     * false：未显示
     */
    private boolean isShow;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public RedEnvelopeCollectionRangeBean getSelectRangeInfo() {
        return selectRangeInfo;
    }

    public void setSelectRangeInfo(RedEnvelopeCollectionRangeBean selectRangeInfo) {
        this.selectRangeInfo = selectRangeInfo;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
