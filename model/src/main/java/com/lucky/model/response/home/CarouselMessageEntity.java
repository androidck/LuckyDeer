package com.lucky.model.response.home;

import java.io.Serializable;

/**
 * 轮播消息
 *
 * @author wangxiangyi
 * @date 2018/11/29
 */
public class CarouselMessageEntity implements Serializable {

    /**
     * id : 1
     * message : 恭喜尾号为7763的用户升级成功
     * createBy : 1
     * createDate : 1543493932000
     * updateBy : 1
     * updateDate : 1543493936000
     * delFlag : 0
     * remark : 1
     */

    /**
     * id
     */
    private String id;
    /**
     * 消息内容
     */
    private String message;
    /**
     *
     */
    private String createBy;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     *
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private String updateDate;
    /**
     *
     */
    private String delFlag;
    /**
     * 备注
     */
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
