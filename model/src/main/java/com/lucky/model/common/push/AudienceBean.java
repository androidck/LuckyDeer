package com.lucky.model.common.push;

import java.util.List;

/**
 * 听众信息
 *
 * @author wangxiangyi
 * @date 2018/11/21
 */
public class AudienceBean {
    /**
     * 听众id
     */
    private List<String> registration_id;

    public List<String> getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(List<String> registration_id) {
        this.registration_id = registration_id;
    }
}
