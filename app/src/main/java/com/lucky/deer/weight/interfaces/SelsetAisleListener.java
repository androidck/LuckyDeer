package com.lucky.deer.weight.interfaces;

import com.lucky.model.response.cardspending.SwipeChannelEntity;

/**
 * 点击监听
 *
 * @author wangxiangyi
 * @date 2018/10/20
 */
public interface SelsetAisleListener {
    enum ClickType {
        /*确定*/
        ok,
        /*取消*/
        cancel
    }
    /*点击监听*/
    void onItemClick( SwipeChannelEntity swipeChannelEntity);
}
