package com.lucky.model.response.selectbankcard;

import java.io.Serializable;

/**
 * 用户余额信息包含提现手续费率
 *
 * @author wangxiangyi
 * @date 2018/11/27
 */
public class CashWithdrawalRate implements Serializable {

    /**
     * 总余额
     */
    private String totalBalance;
    /**
     * 可用余额
     */
    private String useBalance;
    /**
     * 冻结余额
     */
    private String frozenBalance;
    /**
     * 手续费率
     */
    private String transferPoundage;

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getUseBalance() {
        return useBalance;
    }

    public void setUseBalance(String useBalance) {
        this.useBalance = useBalance;
    }

    public String getFrozenBalance() {
        return frozenBalance;
    }

    public void setFrozenBalance(String frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    public String getTransferPoundage() {
        return transferPoundage;
    }

    public void setTransferPoundage(String transferPoundage) {
        this.transferPoundage = transferPoundage;
    }
}
