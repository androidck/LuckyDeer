package com.lucky.model.request.mine;

import com.lucky.model.request.BaseReq;

/**
 * 招聘信息
 *
 * @author wangxiangyi
 * @date 2018/12/19
 */
public class RecruitmentInfoReq extends BaseReq {
    /**
     * 招聘职位类型
     */
    public String recruitJob;
    /**
     * 招聘要求（描述）
     */
    public String recruitRequirement;
    /**
     * 招聘人数
     */
    public String recruitNumber;
    /**
     * 联系电话
     */
    public String recruitPhone;
    /**
     * 工作地点
     */
    public String recruitPosition;

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
}
