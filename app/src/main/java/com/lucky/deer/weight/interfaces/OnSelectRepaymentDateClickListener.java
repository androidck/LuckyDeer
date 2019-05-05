package com.lucky.deer.weight.interfaces;

import com.lucky.model.common.dialog.SelectRepaymentDateEntity;

import java.util.List;

/**
 * 还款日期选择器点击监听
 *
 * @author wangxiangyi
 * @date 2019/01/15
 */
public interface OnSelectRepaymentDateClickListener {
    /**
     * 点击监听
     *
     * @param dataAll        全部数据
     * @param selectedData   选中的数据
     * @param selectedNumber 选中的个数
     */
    void onSelectRepaymentDatemClick(List<SelectRepaymentDateEntity> dataAll, String selectedData, String selectedDay, int selectedNumber);
}
