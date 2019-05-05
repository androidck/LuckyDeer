package com.lucky.deer.weight.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;

/**
 * 公共列表
 *
 * @author wangxiangyi
 * @date 2018/11/2
 */
public class PublicListViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public PublicListViewAdapter() {
        super(R.layout.item_public_list_view);

    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_text, item);
    }
}
