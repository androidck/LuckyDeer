package com.lucky.model.response.find.redEnvelope;

import java.io.Serializable;
import java.util.List;

/**
 * 红包明细列表
 *
 * @author wangxiangyi
 * @date 2019/03/29
 */
public class RedEnvelopesDetailEntity implements Serializable {
    /**********************************************领取红包明细*****************************************/
    /**
     * 领取红包数量
     */
    private String receiveNum;
    /**
     * 当前资金(元)
     */
    private String walletMoney;
    /**
     * 领取明细
     */
    private List<PageBean> page;
    /**********************************************发放红包明细*****************************************/

    /**
     * 领取红包数量
     */
    private String issueNum;


    public String getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(String receiveNum) {
        this.receiveNum = receiveNum;
    }

    public String getIssueNum() {
        return issueNum;
    }

    public void setIssueNum(String issueNum) {
        this.issueNum = issueNum;
    }

    public String getWalletMoney() {
        return walletMoney;
    }

    public void setWalletMoney(String walletMoney) {
        this.walletMoney = walletMoney;
    }

    public List<PageBean> getPage() {
        return page;
    }

    public void setPage(List<PageBean> page) {
        this.page = page;
    }

}
