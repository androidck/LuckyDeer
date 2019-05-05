package com.lucky.model.response.cardspending;

public class ManageAward {

    private String byNum;//人数

    private String auditStatus;//状态 0 未申请 1:审核中 2：审核成功 3：审核失败

    public String getByNum() {
        return byNum;
    }

    public void setByNum(String byNum) {
        this.byNum = byNum;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }
}
