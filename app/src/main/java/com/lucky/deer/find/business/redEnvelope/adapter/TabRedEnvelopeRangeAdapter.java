package com.lucky.deer.find.business.redEnvelope.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.model.response.find.redEnvelope.RedEnvelopeCollectionRangeBean;

/**
 * 红包范围适配器
 *
 * @author wangxiangyi
 * @date 2019/03/28
 */
public class TabRedEnvelopeRangeAdapter extends BaseQuickAdapter<RedEnvelopeCollectionRangeBean, BaseViewHolder> {

    public TabRedEnvelopeRangeAdapter() {
        super(R.layout.item_tab_red_envelope_range);
    }

    @Override
    protected void convert(BaseViewHolder helper, RedEnvelopeCollectionRangeBean item) {
        helper.setText(R.id.rb_range, item.getDictValue())
                .setChecked(R.id.rb_range, item.isSelect());
    }
}
