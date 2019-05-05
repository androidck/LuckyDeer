package com.lucky.deer.home.pepayment.adapter;

import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.deer.util.StringUtil;
import com.lucky.model.common.pepayment.PepaymentPlanMenuEntity;
import com.lucky.model.util.GlideUtils;
import com.lucky.model.util.LayoutStyleUtil;

/**
 * 计划还款菜单适配器
 *
 * @author wangxingyi
 * @date 2018/12/11
 */
public class PepaymentPlanMenuAdapter extends BaseQuickAdapter<PepaymentPlanMenuEntity, BaseViewHolder> {
    public PepaymentPlanMenuAdapter() {
        super(R.layout.item_pepayment_plan_menu);
    }

    @Override
    protected void convert(BaseViewHolder helper, PepaymentPlanMenuEntity item) {
        GlideUtils.loadImage(mContext, helper.getView(R.id.iv_menu_image), item.getMenuImage());
        RelativeLayout llBackground = helper.getView(R.id.ll_background);
        llBackground.setBackground(LayoutStyleUtil.setShapeDrawable(mContext.getResources().getColor(item.getMenuBackground())));
        helper.setText(R.id.tv_menu_title, item.getMenuTitle())
                .setText(R.id.tv_menu_content, StringUtil.setTextIndentation(item.getMenuContent()));
    }
}
