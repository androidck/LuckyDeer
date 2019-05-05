package com.lucky.model.response.find;

import java.io.Serializable;

public class EnclosuresBean implements Serializable {
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 附件类型 1图片
     */
    private String type;
    /**
     * 附件
     */
    private String enclosure;
    /**
     * 附件排序
     */
    private String enclosureSort;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    public String getEnclosureSort() {
        return enclosureSort;
    }

    public void setEnclosureSort(String enclosureSort) {
        this.enclosureSort = enclosureSort;
    }
}
