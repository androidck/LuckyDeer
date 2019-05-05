package com.lucky.model.response.perfectinformation;

import java.io.Serializable;
import java.util.List;

/**
 * 获取区域数据
 *
 * @author wangxiangyi
 * @date 2018/10/18
 */
public class AccountOpeningAreaEntity implements Serializable {
    /**
     * 省编号
     */
    private String code;
    /**
     * 省名称
     */
    private String name;
    /**
     * 城市/地区信息
     */
    private List<AreaVoListBean> areaVoList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AreaVoListBean> getAreaVoList() {
        return areaVoList;
    }

    public void setAreaVoList(List<AreaVoListBean> areaVoList) {
        this.areaVoList = areaVoList;
    }
}
