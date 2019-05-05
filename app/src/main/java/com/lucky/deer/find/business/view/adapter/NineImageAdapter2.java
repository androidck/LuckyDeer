package com.lucky.deer.find.business.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.model.util.GlideUtils;

import java.util.List;

/**
 * @author KCrason
 * @date 2018/4/27
 */
public class NineImageAdapter2 extends BaseQuickAdapter<String, BaseViewHolder> {

    public NineImageAdapter2(@Nullable List<String> data) {
        super(R.layout.item_public_image_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        GlideUtils.loadImage(mContext, helper.getView(R.id.iv_image), item);
    }
}
