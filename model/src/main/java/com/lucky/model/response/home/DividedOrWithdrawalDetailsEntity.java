package com.lucky.model.response.home;

import java.io.Serializable;
import java.util.List;

/**
 * 选择银行卡实体
 *
 * @author wangxiangyi
 * @date 2018/10/10
 */
public class DividedOrWithdrawalDetailsEntity implements Serializable {

    /**
     * 页数
     */
    private int pageNo;
    /**
     * 页面大小
     */
    private int pageSize;
    /**
     * 总条数
     */
    private int count;
    /**
     * 提现金额
     */
    private String withdrawMoney;
    /**
     * 提现时间
     */
    private String transferDate;
    private String html;
    private int currentPage;
    private int totalCount;
    private int numPerPage;
    private int firstResult;
    private int maxResults;
    /**
     * 分润或提现明细详情
     */
    private List<DividedOrWithdrawalDetailsList> list;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(String withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public List<DividedOrWithdrawalDetailsList> getList() {
        return list;
    }

    public void setList(List<DividedOrWithdrawalDetailsList> list) {
        this.list = list;
    }
}
