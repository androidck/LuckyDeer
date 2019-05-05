package com.lucky.deer.weight.interfaces;

/**
 * 列表点击监听
 *
 * @author wangxiangyi
 * @date 2018/10/29
 */
public interface OnItemClickListener {
    enum ClickStatus {
        /**
         * 确定
         */
        OK,
        /**
         * 取消
         */
        CANCEL
    }

    void onItemClick(ClickStatus status,Integer position);
}
