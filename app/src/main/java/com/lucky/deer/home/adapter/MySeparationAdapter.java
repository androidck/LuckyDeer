package com.lucky.deer.home.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.deer.util.DateUtil;
import com.lucky.deer.util.StringUtil;
import com.lucky.model.response.home.DividedOrWithdrawalDetailsList;

/**
 * 我的分润或体现明细适配器
 *
 * @author wangxiangyi
 * @date 2018/11/05
 */
public class MySeparationAdapter extends BaseQuickAdapter<DividedOrWithdrawalDetailsList, BaseViewHolder> {
    private boolean mIsDivided;

    public MySeparationAdapter() {
        super(R.layout.item_my_separation);
    }

    @Override
    protected void convert(BaseViewHolder helper, DividedOrWithdrawalDetailsList item) {
        if (mIsDivided) {
            /*分润*/
            helper.setGone(R.id.ll_divided_layout, true)
                    .setGone(R.id.ll_withdraw_layout, false)
                    /*分润图片*/
                    .setImageResource(R.id.iv_image, ("刷卡".equals(item.getTransactionType()) ? R.mipmap.credit_card_icon : R.mipmap.repayment_icon))
                    /*分润账号*/
                    .setText(R.id.tv_divided_account_number, String.format("来自于：%s", StringUtil.replacePhoneNumber(item.getPhone())))
                    /*消费金额*/
                    .setText(R.id.tv_amount_consumption, "消费：" + (TextUtils.isEmpty(item.getTradingAmount()) ? "0.00" : StringUtil.setNumberFormatting("" + StringUtil.divideCalculation(Integer.parseInt(item.getTradingAmount()), 100))))
                    /*分润金额*/
                    .setText(R.id.tv_divided_amount, "分润：" + StringUtil.setNumberFormatting(item.getOperationBalance()))
                    .setText(R.id.tv_divided_time, DateUtil.stampToDate(item.getCreateDate(), DateUtil.y_m_d));
        } else {
            String type = "";
            switch (item.getWithdrawStatus()) {
                /*未审核*/
                case "1":
                    type = "审核中";
                    break;
                /*已审核*/
                case "2":
                    type = "已审核";
                    break;
                /*审核失败*/
                case "3":
                    type = "审核失败";
                    break;
                /*已转账*/
                case "4":
                    type = "提现成功";
                    break;
                /*转账失败 已转账为转账成功*/
                case "5":
                    type = "提现失败";
                    break;
                /*提现关闭*/
                case "6":
                    type = "提现关闭";
                    break;
                default:
            }
            /*提现*/
            helper.setGone(R.id.ll_divided_layout, false)
                    .setGone(R.id.ll_withdraw_layout, true)
                    .setImageResource(R.id.iv_image, R.mipmap.withdrawal)
                    .setText(R.id.tv_withdrawal_status, type)
                    .setText(R.id.tv_time, DateUtil.stampToDate(item.getCreateDate(), DateUtil.ymdhms))
                    .setText(R.id.tv_amount, item.getWithdrawMoneyY());
        }
    }

    /**
     * 是否是分润
     */
    public void setType(boolean isDivided) {
        mIsDivided = isDivided;
    }
}
