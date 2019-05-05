package com.lucky.model.request.find;

import com.lucky.model.request.BaseReq;

/**
 * 上传视频
 *
 * @author wangxiangyi
 * @date 2018/11/20
 */
public class PostVideoReq extends BaseReq {

    /**
     * 视频标题
     */
    private String title;
    /**
     * 视频描述
     */
    private String desc;
    /**
     * 视频封面
     */
    private String cover;
    /**
     * 视频地址
     */
    private String videoUrl;
    /*删除视频接口*/
    /**
     * 视频id
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
