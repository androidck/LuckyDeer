package com.lucky.deer.find.business.redEnvelope.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.model.common.SearchForListInfoEntity;

/**
 * 搜索类列表适配器
 *
 * @author wangxiangyi
 * @date 2019/03/27
 */
public class SearchForListInfoAdapter extends BaseQuickAdapter<SearchForListInfoEntity, BaseViewHolder> {
    public SearchForListInfoAdapter() {
        super(R.layout.item_search_for_list_info);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchForListInfoEntity item) {
        /*检索名称*/
        helper.setText(R.id.tv_search_for_name, item.getTitle())
                /*检索目标地址*/
                .setText(R.id.tv_search_for_address, item.getSnippet())
                /*选中*/
                .setChecked(R.id.rb_selected, item.isSelect())
                /*是否显示该组件*/
                .setGone(R.id.rb_selected, item.isShow());
    }
}
