package com.lucky.model.response.selectbankcard;

import java.io.Serializable;
import java.util.List;

/**
 * 选择银行卡实体
 *
 * @author wangxiangyi
 * @date 2018/10/10
 */
public class SelectBankCardBean implements Serializable {


    private int pageNo;
    private int pageSize;
    private int count;
    private String html;
    private int currentPage;
    private int totalCount;
    private int numPerPage;
    private int firstResult;
    private int maxResults;
    /**
     * 卡片信息
     */
    private List<SelectBankCardList> list;
    /***********************************设置默认储蓄卡返回参数*******************************************/
    /**
     * 判断是否设置成默认
     */
    private boolean result;

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

    public List<SelectBankCardList> getList() {
        return list;
    }

    public void setList(List<SelectBankCardList> list) {
        this.list = list;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
