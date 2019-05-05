package com.lucky.model.request.home.repayment.perfect;


import com.lucky.model.request.BaseReq;

/**
 * 完美养卡列表请求参数
 *
 * @author wangxiangyi
 * @date 2019/02/26
 */
public class PerfectRepaymentListReq extends BaseReq {
    /**
     * 需要查询列表的：<p>
     * 1：正在养卡<p>
     * 2：养卡历史<p>
     * 3：养卡失败<p>
     * 4：全部计划
     */
    private String status;
    /************************完美养卡详情请求参数*****************************/
    /**
     * 完美养卡计划ID
     */
    private String billPlanId;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBillPlanId() {
        return billPlanId;
    }

    public void setBillPlanId(String billPlanId) {
        this.billPlanId = billPlanId;
    }
}
