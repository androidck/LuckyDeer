package com.lucky.model.response.find;

import java.io.Serializable;

/**
 * 视频列表
 *
 * @author wangxiangyi
 * @date 2018/11/20
 */
public class VideoListEntity implements Serializable {
    /**
     * 招聘信息唯一id
     */
    private String id;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 更新时间
     */
    private String updateDate;
    /**
     * 标题
     */
    private String title;
    /**
     * 视频描述
     */
    private String desc;
    /**
     * 上传时间
     */
    private String uploadTime;
    /**
     * 封面
     */
    private String cover;
    /**
     * 添加视频入口
     */
    private int addCover;
    /**
     * 视频地址
     */
    private String videoUrl;

    /**
     * 是否被选中
     */
    private boolean isChecked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getAddCover() {
        return addCover;
    }

    public void setAddCover(int addCover) {
        this.addCover = addCover;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
