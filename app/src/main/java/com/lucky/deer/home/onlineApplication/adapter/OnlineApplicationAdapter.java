package com.lucky.deer.home.onlineApplication.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.model.response.home.OnlineApplicationEntity;
import com.lucky.model.util.GlideUtils;

import java.util.List;

/**
 * 网申网贷适配器
 *
 * @author wangxiangyi
 * @date 2019/03/16
 */
public class OnlineApplicationAdapter extends BaseMultiItemQuickAdapter<OnlineApplicationEntity, BaseViewHolder> {
    /**
     * 横向
     */
    public static int horizontal = 0;
    /**
     * 纵向
     */
    public static int vertical = 1;

    public OnlineApplicationAdapter(List<OnlineApplicationEntity> data) {
        super(data);
        addItemType(horizontal, R.layout.item_online_application_horizontal);
        addItemType(vertical, R.layout.item_online_application_vertical);
    }


    @Override
    protected void convert(BaseViewHolder helper, OnlineApplicationEntity item) {
        if (horizontal == item.getItemType()) {
            GlideUtils.loadImage(mContext,helper.getView(R.id.iv_credit_card_img),item.getCardImage());
            helper.setText(R.id.tv_credit_card_name, item.getBankName())
            .setText(R.id.tv_credit_card_info,item.getCardName());
        } else {
            helper.setText(R.id.tv_help_function_title, item.getBankName());
        }
    }
}
