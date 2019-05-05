package com.lucky.model.request.home.repayment;

import com.lucky.model.request.BaseReq;

/**
 * 终止计划参数
 *
 * @author wangxiangyi
 * @date 2019/01/07
 */
public class TerminationPlanReq extends BaseReq {
    /**
     * 计划id（账单主键）
     */
    public String billPlanId;
    /**************************************明日还款计划是否正常执行***************************************************/
    /**
     * 账单异常确认主键
     */
    public String id;
    /**
     * 是否同意<p>
     * 2：是<p>
     * 3：否
     */
    public String isAgree;
    /**
     * 点击取消传4，点击确定传1
     */
    public String status;

    public String getBillPlanId() {
        return billPlanId;
    }

    public void setBillPlanId(String billPlanId) {
        this.billPlanId = billPlanId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsAgree() {
        return isAgree;
    }

    public void setIsAgree(String isAgree) {
        this.isAgree = isAgree;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
