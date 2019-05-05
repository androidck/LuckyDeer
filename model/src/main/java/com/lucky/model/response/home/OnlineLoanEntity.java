package com.lucky.model.response.home;

import java.io.Serializable;

/**
 * 我要网申返回实体
 *
 * @author wangxiangyi
 * @date 2019/03/16
 */
public class OnlineLoanEntity implements Serializable {
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
     * 编号
     */
    private String seqNumber;
    /**
     * 图标
     */
    private String ico;
    /**
     * 网贷名称
     */
    private String name;
    /**
     * 信用额度
     */
    private String creditQuota;
    /**
     * 放款方式
     */
    private String lendingMethod;
    /**
     * 月利率%
     */
    private String dayInterestRate;
    /**
     * 地址
     */
    private String address;
    /**
     * 是否置顶<br>
     * 1：是<br>
     * 0：否
     */
    private String isTop;
    /**
     * 是否推荐<br>
     * 1：是<br>
     * 0：否
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

    public String getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(String seqNumber) {
        this.seqNumber = seqNumber;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreditQuota() {
        return creditQuota;
    }

    public void setCreditQuota(String creditQuota) {
        this.creditQuota = creditQuota;
    }

    public String getLendingMethod() {
        return lendingMethod;
    }

    public void setLendingMethod(String lendingMethod) {
        this.lendingMethod = lendingMethod;
    }

    public String getDayInterestRate() {
        return dayInterestRate;
    }

    public void setDayInterestRate(String dayInterestRate) {
        this.dayInterestRate = dayInterestRate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    @Override
    public String toString() {
        return "OnlineLoanEntity{" +
                "remarks='" + remarks + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", id='" + id + '\'' +
                ", seqNumber='" + seqNumber + '\'' +
                ", ico='" + ico + '\'' +
                ", name='" + name + '\'' +
                ", creditQuota='" + creditQuota + '\'' +
                ", lendingMethod='" + lendingMethod + '\'' +
                ", dayInterestRate='" + dayInterestRate + '\'' +
                ", address='" + address + '\'' +
                ", isTop='" + isTop + '\'' +
                ", isRecommend='" + isRecommend + '\'' +
                '}';
    }
}
