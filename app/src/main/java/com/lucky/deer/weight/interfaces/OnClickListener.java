package com.lucky.deer.weight.interfaces;

/**
 * 点击监听
 *
 * @author wangxiangyi
 * @date 2018/10/29
 */
public interface OnClickListener<T> {
    enum ClickStatus {
        /**
         * 确定
         */
        OK,
        /**
         * 取消
         */
        CANCEL,
        /**
         * 留言
         */
        LEAVE_COMMENTS,
        /**
         * 一个按钮的功能
         */
        ONE_BUTTON
    }

    /**
     * 点击监听回掉
     *
     * @param status        点击状态
     * @param useType       使用类型
     * @param isPhoneNumber 是否是手机号
     * @param text          获取信息
     */
    void onClick(ClickStatus status, String useType, boolean isPhoneNumber, T text);
}
