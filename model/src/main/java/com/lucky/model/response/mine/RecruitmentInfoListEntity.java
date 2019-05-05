package com.lucky.model.response.mine;

import java.io.Serializable;
import java.util.List;

/**
 * 招聘信息
 *
 * @author wangxiangyi
 * @date 2018/12/19
 */
public class RecruitmentInfoListEntity implements Serializable {

    private int pageNo;
    private int pageSize;
    private int count;
    private int totalCount;
    private int currentPage;
    private int numPerPage;
    private int firstResult;
    private int maxResults;
    private String html;
    private List<RecruitmentInfoList> list;

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

    public List<RecruitmentInfoList> getList() {
        return list;
    }

    public void setList(List<RecruitmentInfoList> list) {
        this.list = list;
    }
}
