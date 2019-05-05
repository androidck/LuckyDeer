package com.lucky.model.response.perfectinformation;

import java.util.List;

/**
 * 城市/地区信息
 *
 * @author wangxiangyi
 * @date 2018/10/22
 */
public class AreaVoListBean {
    /**
     * 城市/地区编号
     */
    private String code;
    /**
     * 城市/地区名字
     */
    private String name;
    /**
     * 地区信息
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
