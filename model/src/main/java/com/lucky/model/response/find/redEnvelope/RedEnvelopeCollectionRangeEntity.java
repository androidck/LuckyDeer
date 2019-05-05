package com.lucky.model.response.find.redEnvelope;

import java.io.Serializable;
import java.util.List;

/**
 * 限制红包领取范围
 *
 * @author wangxiangyi
 * @date 2019/03/28
 */
public class RedEnvelopeCollectionRangeEntity implements Serializable {

    /**
     * 精准定位列表
     */
    private List<RedEnvelopeCollectionRangeBean> preciseConditions;
    /**
     * 范围限制
     */
    private List<RedEnvelopeCollectionRangeBean> scopeRestrictions;

    public List<RedEnvelopeCollectionRangeBean> getPreciseConditions() {
        return preciseConditions;
    }

    public void setPreciseConditions(List<RedEnvelopeCollectionRangeBean> preciseConditions) {
        this.preciseConditions = preciseConditions;
    }

    public List<RedEnvelopeCollectionRangeBean> getScopeRestrictions() {
        return scopeRestrictions;
    }

    public void setScopeRestrictions(List<RedEnvelopeCollectionRangeBean> scopeRestrictions) {
        this.scopeRestrictions = scopeRestrictions;
    }
}
