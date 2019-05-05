package com.lucky.model.response.find;

import java.io.Serializable;
import java.util.List;

/**
 * 我的视频实体类
 * @author wangxiangyi
 * @date 2018/11/20
 */
public class MyVideoEntity implements Serializable {

    private int pageNo;
    private int pageSize;
    private int count;
    private int totalCount;
    private int currentPage;
    private int numPerPage;
    private int maxResults;
    private int firstResult;
    private String html;
    private List<VideoListEntity> list;

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

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public List<VideoListEntity> getList() {
        return list;
    }

    public void setList(List<VideoListEntity> list) {
        this.list = list;
    }
}
