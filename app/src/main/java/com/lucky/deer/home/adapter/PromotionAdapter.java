package com.lucky.deer.home.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.model.response.promotion.PromotionEntity;
import com.lucky.model.util.GlideUtils;


/**
 * 推广适配器
 *
 * @author wangxiangyi
 * @date 2008/11/13
 */
public class PromotionAdapter extends BaseQuickAdapter<PromotionEntity, BaseViewHolder> {
    public PromotionAdapter() {
        super(R.layout.item_promotion);
    }

    @Override
    protected void convert(BaseViewHolder helper, PromotionEntity item) {
        GlideUtils.loadImage(mContext, helper.getView(R.id.iv_promotion_img), item.getSmallPicture());
        helper.setChecked(R.id.cb_selected_promotion, item.isSelect())
                .addOnClickListener(R.id.cb_selected_promotion);
    }
}
