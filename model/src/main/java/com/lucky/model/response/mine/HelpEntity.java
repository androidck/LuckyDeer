package com.lucky.model.response.mine;

import java.io.Serializable;
import java.util.List;

/**
 * 帮助请求接口参数
 *
 * @author wangxiangyi
 * @date 2019/03/15
 */
public class HelpEntity implements Serializable {
    /**
     * 总条数
     */
    private String rowCount;
    /**
     * 帮助信息列表
     */
    private List<HelpEntity> cmsList;
    /***************************************帮助信息**************************************************/
    /**
     * 标题
     */
    private String titile;
    /**
     * id
     */
    private String id;
    /**
     * 摘要
     */
    private String summary;


    public String getRowCount() {
        return rowCount;
    }

    public void setRowCount(String rowCount) {
        this.rowCount = rowCount;
    }

    public List<HelpEntity> getCmsList() {
        return cmsList;
    }

    public void setCmsList(List<HelpEntity> cmsList) {
        this.cmsList = cmsList;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


}
