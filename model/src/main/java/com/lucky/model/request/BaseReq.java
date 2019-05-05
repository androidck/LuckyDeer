package com.lucky.model.request;


import com.lucky.model.response.userinfo.UserInfo;
import com.lucky.model.util.HawkUtil;

import java.io.Serializable;

/**
 * 基础请求类
 *
 * @author wangxiangyi
 * @date 2018/9/18.
 */
public class BaseReq implements Serializable {
    /**
     * appid
     */
    private final String appId = "e18911500bd243e390fb32d86adf1076";
    /**
     * userId
     */
    private String userId = HawkUtil.getInstance().getSaveData(HawkUtil.USER_INFO) == null ? "" : ((UserInfo) HawkUtil.getInstance().getSaveData(HawkUtil.USER_INFO)).getId();
    /**
     * 页数
     */
    private int pageCurrent=1;
    /**
     * 条数
     */
    private int pageSize = 10;

    public String getAppId() {
        return appId;
    }

    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(int pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
