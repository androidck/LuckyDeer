package com.lucky.model.response.home.pepayment.perfect;

import java.util.List;

/**
 * 完美养卡列表信息
 *
 * @author wangxiangyi
 * @date 2019/02/26
 */
public class PerfectRepaymentEntity {

    private String pageNo;
    private String pageSize;
    private String count;
    private List<MakePerfectBillPlanEntity> list;
    private String firstResult;
    private String maxResults;
    private String html;
    private String totalCount;
    private String currentPage;
    private String numPerPage;

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<MakePerfectBillPlanEntity>  getList() {
        return list;
    }

    public void setList(List<MakePerfectBillPlanEntity> list) {
        this.list = list;
    }

    public String getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(String firstResult) {
        this.firstResult = firstResult;
    }

    public String getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(String maxResults) {
        this.maxResults = maxResults;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(String numPerPage) {
        this.numPerPage = numPerPage;
    }
}
