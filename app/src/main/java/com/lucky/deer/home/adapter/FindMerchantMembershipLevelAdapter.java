package com.lucky.deer.home.adapter;

import android.content.Intent;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lucky.deer.R;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.find.BusinessDetailsActivity;
import com.lucky.model.response.find.LevelUserGroupCountList;
import com.lucky.model.response.find.MerchantUserGroupCountList;
import com.lucky.model.util.GlideUtils;

import java.util.List;

/**
 * 发现页会员等级适配器
 *
 * @author wangxiangyi
 * @date 2018/11/16
 */
public class FindMerchantMembershipLevelAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    public FindMerchantMembershipLevelAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_find_merchant);
        addItemType(TYPE_LEVEL_1, R.layout.item_find_membership_level);

    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                MerchantUserGroupCountList item0 = (MerchantUserGroupCountList) item;
                helper.setText(R.id.tv_merchant_name, item0.getBusinessName())
                        .setText(R.id.tv_total_people, String.format("%s" + mContext.getString(R.string.people), item0.getBusinessNumberPeople()))
                        .setChecked(R.id.cb_is_collapse, item0.isExpanded())
                        .setVisible(R.id.cb_is_collapse, !item0.isLastOne());
                if (!item0.isLastOne()) {
                    if (!TextUtils.isEmpty(item0.getBusinessNumberPeople()) && Double.parseDouble(item0.getBusinessNumberPeople()) > 0) {
                        helper.itemView.setOnClickListener(v -> {
                            int pos = helper.getAdapterPosition();
                            helper.setChecked(R.id.cb_is_collapse, item0.isExpanded());
                            /*判断是否展开*/
                            if (item0.isExpanded()) {
                                /*收起*/
                                collapse(pos);
                            } else {
                                /*展开*/
                                expand(pos);
                            }
                        });
                    }
                }
                break;
            case TYPE_LEVEL_1:
                LevelUserGroupCountList item1 = (LevelUserGroupCountList) item;
                GlideUtils.loadImage(mContext, helper.getView(R.id.iv_grade_logo), item1.getLevelIco());
                helper.setText(R.id.tv_grade_name, item1.getLevelName())
                        .setText(R.id.tv_grade_people, item1.getLevelCount() + mContext.getString(R.string.people));
                helper.itemView.setOnClickListener(v -> {
                    mContext.startActivity(new Intent(mContext, BusinessDetailsActivity.class).putExtra(HttpConstant.ENTITY, item1));
                });
                break;
            default:
        }
    }
}
