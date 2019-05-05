package com.lucky.deer.weight;

import android.app.Activity;
import android.text.TextUtils;

import java.util.List;

import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.picker.SinglePicker;

/**
 * 滚轮菜单
 *
 * @author wangxiangyi
 * @date 2019/01/18
 */
public class WheelMenuDialog {
    private static WheelMenuDialog instance;

    public static WheelMenuDialog getInstance() {
        if (instance == null) {
            synchronized (WheelMenuDialog.class) {
                if (instance == null) {
                    instance = new WheelMenuDialog();
                }
            }
        }
        return instance;
    }

    /**
     * 一级滚轮菜单
     *
     * @param activity       当前activity
     * @param data           展示的数据
     * @param selectedNumber 选中索引
     * @param unit           单位
     * @param listener       点击监听
     */
    public void level1WheelMenu(Activity activity, List<String> data, String selectedNumber, String unit, OnItemPickListener<String> listener) {
        SinglePicker<String> picker = new SinglePicker<>(activity, data);
        /*不禁用循环*/
        picker.setCanLoop(false);
        picker.setLineVisible(false);
        picker.setCanLinkage(true);
        /*设置字体大小*/
        picker.setTextSize(18);
        picker.setSelectedIndex(TextUtils.isEmpty(selectedNumber) ? 0 : Integer.parseInt(selectedNumber) - 1);
        picker.setWheelModeEnable(false);
        //启用权重 setWeightWidth 才起作用
        if (!TextUtils.isEmpty(unit)) {
            picker.setLabel(unit);
        }
        picker.setWeightEnable(true);
        picker.setWeightWidth(1);
        picker.setOnItemPickListener(listener);
        picker.show();
    }
}
