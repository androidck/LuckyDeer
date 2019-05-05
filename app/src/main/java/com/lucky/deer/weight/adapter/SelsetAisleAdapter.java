package com.lucky.deer.weight.adapter;


import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.deer.util.StringUtil;
import com.lucky.model.response.cardspending.SwipeChannelEntity;

/**
 * 通道弹出框
 *
 * @author wangxiangyi
 * @date 20018/10/20
 */
public class SelsetAisleAdapter extends BaseQuickAdapter<SwipeChannelEntity, BaseViewHolder> {
    public SelsetAisleAdapter() {
        super(R.layout.dialog_item_selset_aisle_details);
    }

    @Override
    protected void convert(BaseViewHolder helper, SwipeChannelEntity item) {
        helper.setGone(R.id.rv_aisle_info, false)
                .setGone(R.id.rv_aisle_list_info, true)
                .setText(R.id.list_online_amount, item.getChannelName())
                .setText(R.id.tv_list_rate, mContext.getString(R.string.rate) + (TextUtils.isEmpty(item.getRate()) ? "" : "：" + item.getRate() + "%"))
                .setText(R.id.tv_list_single_pen_handling_fee, mContext.getString(R.string.single_pen_handling_fee) + (TextUtils.isEmpty(item.getFee()) ? "" : StringUtil.setMoneyConverter(item.getFee())))
//                .setText(R.id.tv_business_type, mContext.getString(R.string.business_type) + PublicEnum.getEnumTitleName(item.getChannelType(), "通道接口"))
                .setText(R.id.tv_list_business_type, mContext.getString(R.string.business_type) + item.getExtendedField1())
                .setGone(R.id.tv_list_swipe_has_points, "1".equals(item.getScoreFlag()))
                .setText(R.id.tv_list_swipe_has_points, "1".equals(item.getScoreFlag()) ? mContext.getString(R.string.swipe_has_points) : mContext.getString(R.string.swipe_not_points))
                .setChecked(R.id.cb_list_selected, item.isChecked())
                .setText(R.id.tv_list_support_bank_name, mContext.getString(R.string.support_bank_name) + (TextUtils.isEmpty(item.getSupportBanks()) ? "" : item.getSupportBanks()))
                .addOnClickListener(R.id.cb_list_selected);
    }
}
