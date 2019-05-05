package com.lucky.deer.mine.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.model.response.mine.HelpEntity;

/**
 * 帮助列表适配器
 *
 * @author wangxiangyi
 * @date 2019/03/15
 */
public class HelpAdapter extends BaseQuickAdapter<HelpEntity, BaseViewHolder> {
    public HelpAdapter() {
        super(R.layout.item_help);
    }

    @Override
    protected void convert(BaseViewHolder helper, HelpEntity item) {
        helper.setText(R.id.tv_help_function_title, item.getTitile());
    }
}
