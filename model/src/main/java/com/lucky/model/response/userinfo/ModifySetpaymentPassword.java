package com.lucky.model.response.userinfo;

/**
 * 支付密码返回参数
 *
 * @author wangxiangyi
 * @date 2018/11/05
 */
public class ModifySetpaymentPassword {


    /**
     * 是否设置支付密码<p>
     * 0：未设置<p>
     * 1:已经设置
     */
    private int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
