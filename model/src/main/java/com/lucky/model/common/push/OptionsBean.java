package com.lucky.model.common.push;

import java.io.Serializable;

/**
 * 选项
 *
 * @author wangxiangyi
 * @date 2018/11/21
 */
public class OptionsBean implements Serializable {
    /**
     * sendno : 1
     * time_to_live : 86400
     * apns_production : false
     */
    private String sendno;
    private String time_to_live;
    private boolean apns_production;

    public String getSendno() {
        return sendno;
    }

    public void setSendno(String sendno) {
        this.sendno = sendno;
    }

    public String getTime_to_live() {
        return time_to_live;
    }

    public void setTime_to_live(String time_to_live) {
        this.time_to_live = time_to_live;
    }

    public boolean isApns_production() {
        return apns_production;
    }

    public void setApns_production(boolean apns_production) {
        this.apns_production = apns_production;
    }
}
