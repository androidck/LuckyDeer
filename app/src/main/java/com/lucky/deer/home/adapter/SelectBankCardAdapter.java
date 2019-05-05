package com.lucky.deer.home.adapter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.util.StringUtil;
import com.lucky.model.response.selectbankcard.SelectBankCardList;
import com.lucky.model.util.GlideUtils;
import com.lucky.model.util.LayoutStyleUtil;


import java.util.List;

/**
 * 选择信用卡适配器
 *
 * @author wangxiangyi
 * @date 2018/10/10
 */
public class SelectBankCardAdapter extends BaseItemDraggableAdapter<SelectBankCardList, BaseViewHolder> {
    private int mType = KeyConstant.SELECT_CREDIT_CARD;
    private boolean mIsShowUntied = true;
    /**
     * 是否显示开始日和结束日日期（默认是显示：true）<p>
     * true：显示<p>
     * false：隐藏
     */
    private boolean mIsShowBillingDate = true;
    /**
     * *是否显示去养卡按钮（默认是显示：true）<p>
     * true：显示<p>
     * false：隐藏
     */
    private boolean mIsShowRepayment = true;

    public SelectBankCardAdapter(List<SelectBankCardList> data) {
        super(R.layout.adapter_select_bank_card, data);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(BaseViewHolder helper, SelectBankCardList item) {
        boolean isDefault = "1".equals(item.isDefault());
        GlideUtils.loadImage(mContext, helper.getView(R.id.iv_card_logo), item.getLogo());
        GlideUtils.loadImage(mContext, helper.getView(R.id.iv_card_blurring_logo), item.getBankBackground());
        RelativeLayout llCardBackground = helper.getView(R.id.ll_card_background);
        if (!TextUtils.isEmpty(item.getBackgroundColor()) &&
                item.getBackgroundColor().startsWith("#")) {
            llCardBackground.setBackground(LayoutStyleUtil.setShapeDrawable(item.getBackgroundColor()));
        } else {
            llCardBackground.setBackground(LayoutStyleUtil.setShapeDrawable(mContext.getResources().getColor(R.color.color_default_bank)));
        }
        helper.setGone(R.id.tv_default_receipt, isDefault)
                .setGone(R.id.v_placeholder, !isDefault)
                .setText(R.id.tv_card_name, item.getBankName())
                .setText(R.id.tv_card_type, item.getType())
                .setText(R.id.tv_card_number, StringUtil.replaceCardNumber(item.getCarNumber()))
                .setGone(R.id.tv_card_type2, KeyConstant.SELECT_CREDIT_CARD == mType)
                .setText(R.id.tv_card_type2, item.getCreditAlias())
//                .setGone(R.id.tv_untied, KeyConstant.SELECT_CREDIT_CARD == mType && mIsShowUntied)
                .setGone(R.id.tv_untied, mIsShowUntied)
                .setText(R.id.tv_untied, KeyConstant.SELECT_CREDIT_CARD == mType ? mContext.getString(R.string.untied) : mContext.getString(R.string.modify))
                /*日期布局*/
                .setGone(R.id.ll_billing_date, mIsShowBillingDate && !(TextUtils.isEmpty(item.getBillDate()) && TextUtils.isEmpty(item.getRepaymentDate())))
                /*开始日*/
                .setText(R.id.tv_billing_day_billing_day, item.getBillDate())
                /*结束日*/
                .setText(R.id.tv_repayment_day_billing_day, item.getRepaymentDate())
                /*是否显示养卡按钮*/
                .setGone(R.id.tv_repayment, mIsShowRepayment)
                /*编辑银行卡昵称按钮*/
                .addOnClickListener(R.id.tv_card_type2)
                /*解绑/修改按钮*/
                .addOnClickListener(R.id.tv_untied)
                /*开始日按钮*/
                .addOnClickListener(R.id.rl_billing_day)
                /*结束日按钮*/
                .addOnClickListener(R.id.rl_repayment_date)
                /*去养卡按钮*/
                .addOnClickListener(R.id.tv_repayment);
    }

    /**
     * 设置卡的类型
     */
    public void setCardType(int type) {
        mType = type;
    }

    /**
     * 获取卡的类型
     */
    public int getCardType() {
        return mType;
    }

    /**
     * 设置是否展示解绑卡按钮（默认是显示：true）
     *
     * @param isShowUntied true：显示<p>
     *                     false：隐藏
     */
    public void setShowUntiedCard(boolean isShowUntied) {
        mIsShowUntied = isShowUntied;
    }

    /**
     * 是否显示开始日和结束日日期（默认是显示：true）
     *
     * @param isShowBillingDate true：显示<p>
     *                          false：隐藏
     */
    public void setShowBillingDate(boolean isShowBillingDate) {
        mIsShowBillingDate = isShowBillingDate;
    }

    /**
     * 是否显示去养卡按钮（默认是显示：true）
     *
     * @param isShowRepayment true：显示<p>
     *                        false：隐藏
     */
    public void setShowRepayment(boolean isShowRepayment) {
        mIsShowRepayment = isShowRepayment;
    }
}
