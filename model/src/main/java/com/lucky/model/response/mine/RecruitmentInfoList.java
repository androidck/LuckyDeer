package com.lucky.model.response.mine;

import java.io.Serializable;

/**
 * 招聘信息
 *
 * @author wangxiangyi
 * @date 2018/12/19
 */
public class RecruitmentInfoList implements Serializable {

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
     * 招聘人
     */
    private String recruitMan;
    /**
     * 招聘了类别
     */
    private String recruitJob;
    /**
     * 招聘需求
     */
    private String recruitRequirement;
    /**
     * 招聘人数
     */
    private String recruitNumber;
    /**
     * 联系电话
     */
    private String recruitPhone;
    /**
     * 工作地点
     */
    private String recruitPosition;
    /**
     * 发布时间
     */
    private String recruitPublishTime;
    /**
     * 状态<p>
     * 1：审核中<p>
     * 2：已发布<p>
     * 3：未审核通过
     */
    private String status;

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

    public String getRecruitMan() {
        return recruitMan;
    }

    public void setRecruitMan(String recruitMan) {
        this.recruitMan = recruitMan;
    }

    public String getRecruitJob() {
        return recruitJob;
    }

    public void setRecruitJob(String recruitJob) {
        this.recruitJob = recruitJob;
    }

    public String getRecruitRequirement() {
        return recruitRequirement;
    }

    public void setRecruitRequirement(String recruitRequirement) {
        this.recruitRequirement = recruitRequirement;
    }

    public String getRecruitNumber() {
        return recruitNumber;
    }

    public void setRecruitNumber(String recruitNumber) {
        this.recruitNumber = recruitNumber;
    }

    public String getRecruitPhone() {
        return recruitPhone;
    }

    public void setRecruitPhone(String recruitPhone) {
        this.recruitPhone = recruitPhone;
    }

    public String getRecruitPosition() {
        return recruitPosition;
    }

    public void setRecruitPosition(String recruitPosition) {
        this.recruitPosition = recruitPosition;
    }

    public String getRecruitPublishTime() {
        return recruitPublishTime;
    }

    public void setRecruitPublishTime(String recruitPublishTime) {
        this.recruitPublishTime = recruitPublishTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
