package com.lucky.deer.common.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.model.response.find.redEnvelope.RedEnvelopeCollectionRangeBean;

import java.util.List;

/**
 * 精准定位对话框适配器
 *
 * @author wangxiangyi
 * @date 2019/03/29
 */
public class CollorDialogAdapter extends BaseQuickAdapter<RedEnvelopeCollectionRangeBean, BaseViewHolder> {
    public CollorDialogAdapter(@Nullable List<RedEnvelopeCollectionRangeBean> data) {
        super(R.layout.item_collor_dialog, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RedEnvelopeCollectionRangeBean item) {
        helper.setText(R.id.tv_text, item.getDictValue());
    }
}
