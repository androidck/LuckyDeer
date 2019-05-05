package com.lucky.deer.home.pepayment.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.enums.ExecutionStatusEnum;
import com.lucky.model.response.home.pepayment.ConsumeList;

import java.util.List;

/**
 * 消费列表适配器
 *
 * @author wangxiangyi
 * @date 2018/12/14
 */
public class ConsumptionListAdapter extends BaseQuickAdapter<ConsumeList, BaseViewHolder> {


    private int mLastOne;

    public ConsumptionListAdapter(@Nullable List<ConsumeList> data) {
        super(R.layout.item_consumption_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ConsumeList item) {
        helper.setText(R.id.tv_consumption_date,
                helper.getAdapterPosition() == mLastOne ?
                        ("1".equals(item.getStatus()) ? item.getRepaymentTime() : item.getBillRepaymentTime()) :
                        item.getConsumptionTime())
//                .setText(R.id.tv_amount_consumption, mContext.getString(R.string.amount_consumption) + "：" + StringUtil.setNumberFormatting(item.getConsumptionMoney()))
                .setText(R.id.tv_amount_consumption,
                        helper.getAdapterPosition() == mLastOne ?
                                (mContext.getString(R.string.repayment_amount) + "：￥" + StringUtil.setNumberFormatting(item.getRepaymentMoney())) :
                                (mContext.getString(R.string.amount_consumption) + "：￥" + StringUtil.setNumberFormatting(item.getConsumptionMoney())))
//                .setText(R.id.tv_status, ExecutionStatusEnum.getSearchStatusName(ExecutionStatusEnum.consumption_status, item.getConsumptionStatus()))
                .setText(R.id.tv_status,
                        helper.getAdapterPosition() == mLastOne ?
                                ExecutionStatusEnum.getSearchStatusName(ExecutionStatusEnum.consumption_status1, item.getStatus()) :
                                ExecutionStatusEnum.getSearchStatusName(ExecutionStatusEnum.consumption_status, item.getConsumptionStatus()))
//                .setTextColor(R.id.tv_status, mContext.getResources().getColor(ExecutionStatusEnum.getSearchStatusTextColor(ExecutionStatusEnum.consumption_status, item.getConsumptionStatus())));
                .setTextColor(R.id.tv_status, helper.getAdapterPosition() == mLastOne ?
                        mContext.getResources().getColor(ExecutionStatusEnum.getSearchStatusTextColor(ExecutionStatusEnum.consumption_status1, item.getStatus())) :
                        mContext.getResources().getColor(ExecutionStatusEnum.getSearchStatusTextColor(ExecutionStatusEnum.consumption_status, item.getConsumptionStatus())));
    }

    /**
     * 最后一个位置
     *
     * @param lastOne
     */
    public void setLastOne(int lastOne) {
        mLastOne = lastOne;
    }
}
