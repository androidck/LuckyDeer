package com.lucky.model.request.mine;

import com.lucky.model.request.BaseReq;

/**
 * 完善业务员信息
 *
 * @author wangxiangyi
 * @date 2018/12/19
 */
public class PerfectInfoReq extends BaseReq {
    /**
     * 业务员主键 更新时传参
     */
    private String id;
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
     * 业务员类型<p>
     * 1：银行贷款专员<p>
     * 2：信用卡专员<p>
     * 3：金融机构专员<p>
     * 4：小小抵押贷专员
     */
    private String salesmanType;
    /**
     * 备注信息
     */
    private String remarks;
    /**
     * 审核状态 更新时传参
     */
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSalesmanType() {
        return salesmanType;
    }

    public void setSalesmanType(String salesmanType) {
        this.salesmanType = salesmanType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
