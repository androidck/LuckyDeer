package com.lucky.model.common;

import java.io.Serializable;

/**
 * 公共传参实体
 *
 * @author wangxiangyi
 * @date 2018/11/05
 */
public class PassingEntity implements Serializable {
    /**
     * 传参标志<p>
     * 1：账号管理：设置登录密码<p>
     * 2：账号管理：修改登录密码<p>
     * 3：账号管理：忘记登录密码<p>
     * 4：账号管理：设置支付密码<p>
     * 5：账号管理：修改支付密码<p>
     * 6：账号管理：忘记支付密码
     */
    private int flag = 0;

    public PassingEntity() {
    }

    public PassingEntity(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
