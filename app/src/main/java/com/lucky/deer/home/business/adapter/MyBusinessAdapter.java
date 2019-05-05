package com.lucky.deer.home.business.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.model.response.home.cardLife.MyBusinessEntity;
import com.lucky.deer.R;
import com.lucky.model.util.GlideUtils;

/**
 * 我的商户
 *
 * @author wangxiangyi
 * @date 2019/03/19
 */
//public class MyBusinessAdapter extends BaseSectionQuickAdapter<MyBusinessEntity, BaseViewHolder> {
public class MyBusinessAdapter extends BaseQuickAdapter<MyBusinessEntity, BaseViewHolder> {
    public MyBusinessAdapter() {
        super(R.layout.item_res_my_business);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyBusinessEntity item) {
        GlideUtils.loadImage(mContext, helper.getView(R.id.iv_business_img), item.getLevelIco());
        /*商户级别级别名称*/
        helper.setText(R.id.tv_business_level_name, item.getLevelName())
                /*商户人数*/
                .setText(R.id.tv_business_human_head, String.format(mContext.getString(R.string.item_adapter_business_level_number_people), TextUtils.isEmpty(item.getLevelCount()) ? "0" : item.getLevelCount()));
    }

//    public MyBusinessAdapter(List<MyBusinessEntity> data) {
//        super(R.layout.item_res_my_business, R.layout.item_head_my_business, data);
//    }
//
//    @Override
//    protected void convertHead(BaseViewHolder helper, MyBusinessEntity item) {
//        /*商户推送名称*/
//        helper.setText(R.id.tv_business_name, String.format(mContext.getString(R.string.item_adapter_business_name), item.getTitle(), TextUtils.isEmpty(item.getNumberMerchants()) ? "0" : item.getNumberMerchants()));
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, MyBusinessEntity item) {
//        GlideUtils.loadImage(mContext, helper.getView(R.id.iv_business_img), item.getLevelIco());
//        /*商户级别级别名称*/
//        helper.setText(R.id.tv_business_level_name, item.getLevelName())
//                /*商户人数*/
//                .setText(R.id.tv_business_human_head, String.format(mContext.getString(R.string.item_adapter_business_level_number_people), TextUtils.isEmpty(item.getLevelCount()) ? "0" : item.getLevelCount()));
//    }
}
