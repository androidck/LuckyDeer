package com.lucky.deer.find.adapter;

import android.text.TextUtils;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.deer.util.StringUtil;
import com.lucky.model.response.find.PurchaseMemberEntity;
import com.lucky.model.util.LayoutStyleUtil;

/**
 * 购买会员列表适配器
 *
 * @author wangxiangyi
 * @date 2018/11/16
 */
public class PurchaseMemberAdapter extends BaseQuickAdapter<PurchaseMemberEntity, BaseViewHolder> {
    public PurchaseMemberAdapter() {
        super(R.layout.item_purchase_member);
    }

    @Override
    protected void convert(BaseViewHolder helper, PurchaseMemberEntity item) {
        LinearLayout llBackground = helper.getView(R.id.ll_background);
        if (!TextUtils.isEmpty(item.getBackgroundColor())){
            llBackground.setBackground(LayoutStyleUtil.setShapeDrawable(item.getBackgroundColor()));
        }
        helper.setText(R.id.tv_member_title, item.getLevelName())
                .setGone(R.id.tv_apply, "1".equals(item.getApplicationButton()))
                .setText(R.id.tv_buy_member, "222".equals(item.getLevelId()) ? "立即推广" : "购买")
                .setGone(R.id.tv_buy_member, (!TextUtils.isEmpty(item.getPrice()) && Float.valueOf(item.getPrice()) > 0) || "222".equals(item.getLevelId()))
                .addOnClickListener(R.id.tv_apply)
                .addOnClickListener(R.id.tv_buy_member);


        String money=StringUtil.setNumberFormatting(item.getPrice(), 2);
        if (money.equals("0.00")){
            helper.setGone(R.id.tv_apply,false);
            helper.setText(R.id.tv_member_info, ("222".equals(item.getLevelId()) ? item.getIntroduce() : ("111".equals(item.getLevelId()) ? item.getIntroduce() : item.getIntroduce())));
        }else {
            helper.setGone(R.id.tv_apply,true);
            helper.setText(R.id.tv_member_info, ("222".equals(item.getLevelId()) ? item.getIntroduce() : ("111".equals(item.getLevelId()) ? item.getIntroduce() : "支付" + StringUtil.setNumberFormatting(item.getPrice(), 2) + "元，" + item.getIntroduce())));
        }

    }
}
