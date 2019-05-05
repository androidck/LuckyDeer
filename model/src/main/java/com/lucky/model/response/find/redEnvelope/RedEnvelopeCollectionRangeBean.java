package com.lucky.model.response.find.redEnvelope;

import java.io.Serializable;

/**
 * 精准定位和范围限制实体
 *
 * @author wangxiangyi
 * @date 2019/03/28
 */
public class RedEnvelopeCollectionRangeBean implements Serializable {
    /**
     * createDate : 2019-03-27 17:35:00
     * updateDate : 2019-03-27 17:35:00
     * id : 0d272e5099df4b198478c47a0219173c
     * dictType : precise_condition
     * dictKey : -1
     * dictValue : 不限
     * dictDescribe : 精确要求
     * addedValue :
     * dictRemarks :
     * isEffective : 1
     * companyId : 66b163de67ea43aba793ab6c5ad4b3a0
     */
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 更新时间
     */
    private String updateDate;
    /**
     * id
     */
    private String id;
    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 字典键
     */
    private String dictKey;
    /**
     * 字典值
     */
    private String dictValue;
    /**
     * 字典描述
     */
    private String dictDescribe;
    /**
     * 附加值
     */
    private String addedValue;
    /**
     * 备注
     */
    private String dictRemarks;
    /**
     * 是否有效
     */
    private String isEffective;
    /**
     * 机构id
     */
    private String companyId;

    private boolean isSelect;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDictDescribe() {
        return dictDescribe;
    }

    public void setDictDescribe(String dictDescribe) {
        this.dictDescribe = dictDescribe;
    }

    public String getAddedValue() {
        return addedValue;
    }

    public void setAddedValue(String addedValue) {
        this.addedValue = addedValue;
    }

    public String getDictRemarks() {
        return dictRemarks;
    }

    public void setDictRemarks(String dictRemarks) {
        this.dictRemarks = dictRemarks;
    }

    public String getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(String isEffective) {
        this.isEffective = isEffective;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
