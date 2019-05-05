package com.lucky.model.response.mine;

import java.io.Serializable;

/**
 * 我是业务员：完善信息
 *
 * @author wangxiangyi
 * @date 2018/12/19
 */
public class PerfectInfoEntity implements Serializable {

    /**
     * 业务员主键
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
     * 公司名称
     */
    private String financialCompanyId;
    /**
     * 公司介绍
     */
    private String financialCompanyDesc;
    /**
     * 公司地址
     */
    private String financialCompanyAddress;
    /**
     * 业务简介
     */
    private String businessIntroduction;
    /**
     * 用户id
     */
    private String userId;
    /**
     *
     */
    private String receiptFlag;
    /**
     * 业务员类型<p>
     * 1: 银行贷款专员<p>
     * 2: 信用卡专员<p>
     * 3: 金融机构专员<p>
     * 4: 小小抵押贷专员
     */
    private String salesmanType;
    /**
     * 审核状态 更新时传参
     */
    private String status;
    /**
     * 备注信息
     */
    private String remarks;

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

    public String getFinancialCompanyId() {
        return financialCompanyId;
    }

    public void setFinancialCompanyId(String financialCompanyId) {
        this.financialCompanyId = financialCompanyId;
    }

    public String getFinancialCompanyDesc() {
        return financialCompanyDesc;
    }

    public void setFinancialCompanyDesc(String financialCompanyDesc) {
        this.financialCompanyDesc = financialCompanyDesc;
    }

    public String getFinancialCompanyAddress() {
        return financialCompanyAddress;
    }

    public void setFinancialCompanyAddress(String financialCompanyAddress) {
        this.financialCompanyAddress = financialCompanyAddress;
    }

    public String getBusinessIntroduction() {
        return businessIntroduction;
    }

    public void setBusinessIntroduction(String businessIntroduction) {
        this.businessIntroduction = businessIntroduction;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReceiptFlag() {
        return receiptFlag;
    }

    public void setReceiptFlag(String receiptFlag) {
        this.receiptFlag = receiptFlag;
    }

    public String getSalesmanType() {
        return salesmanType;
    }

    public void setSalesmanType(String salesmanType) {
        this.salesmanType = salesmanType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
