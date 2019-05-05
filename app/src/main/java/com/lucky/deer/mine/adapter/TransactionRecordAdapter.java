package com.lucky.deer.mine.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.deer.util.StringUtil;
import com.lucky.model.response.mine.TransactionRecordList;

/**
 * 交易记录适配器
 *
 * @author wangxiangyi
 * @date 2018/11/29
 */
public class TransactionRecordAdapter extends BaseQuickAdapter<TransactionRecordList, BaseViewHolder> {
    public TransactionRecordAdapter() {
        super(R.layout.item_transaction_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, TransactionRecordList item) {
        String mTypeText = "";
        int mTypeColor;
        switch (item.getTradingStatus()) {
            /*未审核*/
            case "1":
                mTypeText = "交易成功";
                mTypeColor = R.color.colorGreen;
                break;
            /*已审核*/
            case "2":
                mTypeText = "交易失败";
                mTypeColor = R.color.color_hint;
                break;
            /*审核失败*/
            case "3":
                mTypeText = "交易申请中";
                mTypeColor = R.color.color_golden;
                break;
            case "4":
                mTypeText = item.getExtendedField1();
                mTypeColor = R.color.color_hint;
                break;
            default:
                mTypeColor = R.color.color_hint;
        }
        helper.setText(R.id.tv_trading_bank, item.getBankName() + "（" +
                (TextUtils.isEmpty(item.getCreditCard()) ? "" :
                        StringUtil.substringData(item.getCreditCard(),
                                (item.getCreditCard().length() - 4),
                                item.getCreditCard().length()) + "）"))
                .setText(R.id.tv_channel_name, item.getChannelName())
                .setText(R.id.tv_time, item.getTradingDate())
                .setText(R.id.tv_amount, StringUtil.setNumberFormatting(item.getTradingAmount()))
                .setText(R.id.tv_trading_status, mTypeText)
                .setTextColor(R.id.tv_trading_status, mContext.getResources().getColor(mTypeColor));
    }
}
