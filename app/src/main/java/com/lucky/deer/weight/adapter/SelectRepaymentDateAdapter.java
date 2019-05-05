package com.lucky.deer.weight.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.deer.util.DateUtil;
import com.lucky.model.common.dialog.SelectRepaymentDateEntity;

import java.util.List;

/**
 * 选择日期适配器
 *
 * @author wangxiangyi
 * @date 2019/1/14
 */
public class SelectRepaymentDateAdapter extends BaseSectionQuickAdapter<SelectRepaymentDateEntity, BaseViewHolder> {

    /**
     * @param data
     */
    public SelectRepaymentDateAdapter(List<SelectRepaymentDateEntity> data) {
        super(R.layout.item_day_number, R.layout.item_month, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SelectRepaymentDateEntity item) {
        helper.setText(R.id.item_month, DateUtil.dateConversionFormat(item.header, DateUtil.ymd, DateUtil.MM) + "月")
                .setGone(R.id.item_select_all_one, item.isHideSelectAll())
                .setText(R.id.item_select_all_one, item.isAllSelect() ? "全部取消" : "全选")
                .addOnClickListener(R.id.item_select_all_one);
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectRepaymentDateEntity item) {
        helper.setText(R.id.cb_day, DateUtil.dateConversionFormat(item.getDate(), DateUtil.ymd, DateUtil.dd))
                .setChecked(R.id.cb_day, item.isSelect());
    }
}
