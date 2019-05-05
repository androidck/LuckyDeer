package com.lucky.model.response.home.pepayment;

import java.util.List;

/**
 * 历史还款列表实体
 *
 * @author wangxingyi
 * @date 2018/12/11
 */
public class HistoryPlanEntity {
    private int pageNo;
    private int pageSize;
    private int count;
    private int totalCount;
    private int currentPage;
    private int numPerPage;
    private int firstResult;
    private int maxResults;
    private String html;
    private List<HistoryPlanList> list;

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

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
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

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public List<HistoryPlanList> getList() {
        return list;
    }

    public void setList(List<HistoryPlanList> list) {
        this.list = list;
    }
}
