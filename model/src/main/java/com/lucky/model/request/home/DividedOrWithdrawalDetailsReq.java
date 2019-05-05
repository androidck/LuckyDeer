package com.lucky.model.request.home;

import com.lucky.model.request.BaseReq;

/**
 * 我的分润或提现明细页面实体
 *
 * @author wangxiangyi
 * @date 2018/11/15
 */
public class DividedOrWithdrawalDetailsReq extends BaseReq {
    /**
     * 提现状态<p>
     * 1:未审核<p>
     * 2:已审核<p>
     * 3:审核失败<p>
     * 4:已转账<p>
     * 5:转账失败<p>
     * 默认查询状态为4的
     */
    private String withdrawStatus;

    public String getWithdrawStatus() {
        return withdrawStatus;
    }

    public void setWithdrawStatus(String withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }
}
