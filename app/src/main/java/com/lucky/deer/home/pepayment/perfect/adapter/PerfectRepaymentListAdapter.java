package com.lucky.deer.home.pepayment.perfect.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.enums.ExecutionStatusEnum;
import com.lucky.model.response.home.pepayment.perfect.MakePerfectBillPlanEntity;
import com.lucky.model.util.GlideUtils;

/**
 * 完美养卡适配器
 *
 * @author wangxiangyi
 * @date 2019/02/18
 */
public class PerfectRepaymentListAdapter extends BaseQuickAdapter<MakePerfectBillPlanEntity, BaseViewHolder> {
    public PerfectRepaymentListAdapter() {
        super(R.layout.item_perfect_repayment_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, MakePerfectBillPlanEntity item) {
        /*设置卡logo*/
        GlideUtils.loadImage(mContext, helper.getView(R.id.iv_bank_logo), item.getBankLogo());
        /*计划编号*/
        helper.setText(R.id.tv_plan_number, String.format(mContext.getString(R.string.plan_number), item.getPlanNo()))
                /*养卡状态*/
                .setImageResource(R.id.iv_execution_status, ExecutionStatusEnum.getSearchStatusStyle(ExecutionStatusEnum.implementation_plan, item.getStatus()))
                /*卡号*/
                .setText(R.id.tv_bank_card_number, StringUtil.replaceCardNumber(item.getCarNumber()))
                /*银行名称*/
                .setText(R.id.tv_bank_name, item.getBankName())
                /*预留金额*/
                .setText(R.id.tv_reserve_amount_required, StringUtil.setNumberFormatting(item.getActualRepaymentMoney()))
                /*计划养卡金额*/
                .setText(R.id.tv_prepayment_amount, StringUtil.setNumberFormatting(item.getPlanRepaymentMoney()));
    }
}
