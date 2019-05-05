package com.lucky.deer.home.onlineApplication.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.model.response.home.OnlineLoanEntity;

/**
 * 网贷适配器
 *
 * @author wangxiangyi
 * @date 2019/03/16
 */
public class OnlineLoanAdapter extends BaseQuickAdapter<OnlineLoanEntity, BaseViewHolder> {

    public OnlineLoanAdapter() {
        super(R.layout.item_online_loan);
    }

    @Override
    protected void convert(BaseViewHolder helper, OnlineLoanEntity item) {
        helper.setText(R.id.tv_help_function_title, item.getName());
    }
}
