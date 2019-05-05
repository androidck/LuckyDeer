package com.lucky.deer.find.adapter;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.model.response.find.SubmitFindWithdrawalEntity;

/**
 * 提现进度适配器
 *
 * @author wangxiangyi
 * @date 2018/11/16
 */
public class SubmitFindWithdrawalAdapter extends BaseQuickAdapter<SubmitFindWithdrawalEntity, BaseViewHolder> {
    public SubmitFindWithdrawalAdapter() {
        super(R.layout.item_trace);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void convert(BaseViewHolder helper, SubmitFindWithdrawalEntity item) {
        if (item.isLineTopViewGreen()) {
            helper.setImageResource(R.id.iv_line, R.mipmap.line01);
        } else {
            helper.setImageResource(R.id.iv_line, R.mipmap.line02);
        }
        if (item.isLineButtomViewGreen()) {
            helper.setImageResource(R.id.iv_line2, R.mipmap.line01);
        } else {
            helper.setImageResource(R.id.iv_line2, R.mipmap.line02);
        }
        helper.setVisible(R.id.iv_line, item.isLineTopView())
                .setImageResource(R.id.iv_dot, item.getScheduleDot())
                .setVisible(R.id.iv_line2, item.isLineButtomView())
                .setText(R.id.tv_deal_with_status, item.getDealWithStatus())
                .setText(R.id.tv_time_arrival, item.getDealWithReason())
                .setGone(R.id.tv_time_arrival, !TextUtils.isEmpty(item.getDealWithReason()));
    }
}
