package com.lucky.model.response.home;

import java.io.Serializable;

/**
 * 轮播广告图实体
 *
 * @author wangxiangyi
 * @date 2019/01/21
 */
public class QueryBannerEntity implements Serializable {
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 更新时间
     */
    private String updateDate;
    /**
     * id
     */
    private String id;
    /**
     * 轮播图片
     */
    private String imageUrl;
    /**
     * 图片宽度
     */
    private int imageWidth;
    /**
     * 图片高度
     */
    private int imageHeight;
    /**
     * 标题
     */
    private String title;
    /**
     * 图片跳转链接
     */
    private String link;
    /**
     *
     */
    private String extId;
    /**
     * 类型
     */
    private String type;
    /**
     *
     */
    private int sort;
    /**
     * 是否显示
     */
    private String isShow;
    /**
     *
     */
    private String displayPage;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getDisplayPage() {
        return displayPage;
    }

    public void setDisplayPage(String displayPage) {
        this.displayPage = displayPage;
    }
}
