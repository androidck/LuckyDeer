package com.lucky.model.response.home;

import java.io.Serializable;

/**
 * 全部文章列表返回实体
 *
 * @author wangxiangyi
 * @date 2019/03/16
 */
public class FindArticleEntity implements Serializable {

    /**
     * remarks : 招聘了
     * createDate : 2018-11-12 09:24:02
     * updateDate : 2019-03-15 18:14:27
     * id : 05c8e44aab3849408e8b05fd022d9999
     * titile : 招聘信息
     * summary : 教育辅导机构招聘老师
     * content :
     */
    /**
     * 备注
     */
    private String remarks;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 更新时间
     */
    private String updateDate;
    /**
     * 主键
     */
    private String id;
    /**
     * 标题
     */
    private String titile;
    /**
     * 简介
     */
    private String summary;
    /**
     * 内容
     */
    private String content;
    /**
     * 文章图片
     */
    private String coverPhoto;

    /**
     * 阅读次数
     */
    private String readCount;

    /**
     * 是否置顶1是0否
     */
    private String isTop;
    /**
     * 是否推荐1是0否
     */
    private String isRecommend;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getReadCount() {
        return readCount;
    }

    public void setReadCount(String readCount) {
        this.readCount = readCount;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }
}
