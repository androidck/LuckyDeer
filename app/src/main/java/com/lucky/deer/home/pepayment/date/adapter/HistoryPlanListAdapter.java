package com.lucky.deer.home.pepayment.date.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.deer.util.ProgressBarUtil;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.enums.ExecutionStatusEnum;
import com.lucky.model.response.home.pepayment.HistoryPlanList;
import com.lucky.model.util.GlideUtils;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundRelativeLayout;

import java.util.Arrays;

/**
 * 历史养卡列表适配器
 *
 * @author wangxingyi
 * @date 2018/12/12
 */
public class HistoryPlanListAdapter extends BaseQuickAdapter<HistoryPlanList, BaseViewHolder> {
    public HistoryPlanListAdapter() {
//        super(R.layout.item_history_plan_list);
        super(R.layout.item_pepayment_plan_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryPlanList item) {
        GlideUtils.loadImage(mContext, helper.getView(R.id.iv_bank_logo), item.getExtendOne());
        /*异常提示*/
        QMUIRoundRelativeLayout qrrlPlanAbnormal = helper.getView(R.id.qrrl_plan_abnormal);
        /*获取进度控件*/
        ProgressBar pbProgressBar = helper.getView(R.id.pb_progress_bar);
        /*设置养卡进度*/
        ProgressBarUtil instance = ProgressBarUtil.getInstance(mContext, pbProgressBar);
        /*判断执行状态*/
        switch (item.getStatus()) {
            /*执行中*/
            case "1":
                /*已完成*/
            case "2":
                /*用户撤销*/
            case "3":
                /*制定中*/
            case "5":
                /*未完全成功*/
            case "7":
                qrrlPlanAbnormal.setVisibility(View.GONE);
                /*进度条样式*/
                instance.setProgressDrawable(ProgressBarUtil.layerProgressDrawableColorBlue);
                break;
            /*异常关闭*/
            case "4":
                /*执行异常*/
            case "6":
                /*终止*/
            case "8":
//                qrrlPlanAbnormal.setVisibility(View.VISIBLE);
                /*进度条样式*/
                instance.setProgressDrawable(ProgressBarUtil.layerProgressDrawableColorRed);
                break;
            default:
                qrrlPlanAbnormal.setVisibility(View.GONE);
                instance.setProgressDrawable(ProgressBarUtil.layerProgressDrawableColorBlue);
                break;
        }
        /*已成功消费金额*/
        helper.setText(R.id.tv_successfully_consumed, mContext.getString(R.string.text_successfully_consumed) + item.getAlreadyConsumptionMoney() + "元")
                /*已成功养卡金额*/
                .setText(R.id.tv_successfully_repaid, mContext.getString(R.string.text_successfully_repaid) + item.getAlreadyRepaymentMoney() + "元")
                /*创建日期*/
                .setText(R.id.tv_create_date, String.format(mContext.getString(R.string.text_create_date), item.getCreateDate()));
        if (!TextUtils.isEmpty(item.getChooseRepaymentDate()) && item.getChooseRepaymentDate().contains(",")) {
            String[] split = StringUtil.getSplit(item.getChooseRepaymentDate(), ",");
            StringUtil.dataSorting(Arrays.asList(split));
            /*设置提交日期*/
            helper.setText(R.id.tv_planned_date, mContext.getString(R.string.text_planned_date) + split[0] + "至" + split[split.length - 1]);
        } else {
            /*设置提交日期*/
            helper.setText(R.id.tv_planned_date, mContext.getString(R.string.text_planned_date) + item.getChooseRepaymentDate());
        }
        /*执行状态*/
        helper.setImageResource(R.id.iv_execution_status, ExecutionStatusEnum.getSearchStatusStyle(ExecutionStatusEnum.implementation_plan, item.getStatus()))
                /*设置银行卡号*/
                .setText(R.id.tv_bank_card_number, StringUtil.replaceCardNumber(item.getCarNumber()))
                /*设置银行名称*/
                .setText(R.id.tv_bank_name, item.getBankName())
                /*养卡笔数*/
                .setText(R.id.tv_repayments, item.getRepaymentNum())
                /*需预留金额标题字体颜色*/
                .setTextColor(R.id.tv_title_reserve_amount_required, "2".equals(item.getStatus()) ? mContext.getResources().getColor(R.color.color_hint) : mContext.getResources().getColor(R.color.color_80_blue))
                /*需预留金额*/
                .setText(R.id.tv_reserve_amount_required, "￥" + StringUtil.setNumberFormatting(item.getKeepMoney()))
                /*需预留金额字体颜色*/
                .setTextColor(R.id.tv_reserve_amount_required, "2".equals(item.getStatus()) ? mContext.getResources().getColor(R.color.color_hint) : mContext.getResources().getColor(R.color.color_80_blue))
                /*预留养卡金额*/
                .setText(R.id.tv_prepayment_amount, "￥" + item.getPlanRepaymentMoney())
                /*剩余养卡笔数（判断计算的笔数是否是空）*/
//                .setText(R.id.tv_number_remaining, "剩余" + StringUtil.getSubtract(item.getRepaymentNum(), item.getAlreadyRepaymentNum()) + "笔");
                .setText(R.id.tv_number_remaining, "剩余" + (TextUtils.isEmpty(StringUtil.getSubtract(item.getRepaymentNum(), item.getAlreadyRepaymentNum())) ? 0 :
                        /*判断养卡笔数减去已养卡笔数是否小于或等于0，如果小于等于0就把值设置为0，如果大于零则设置为计算的值*/
                        (Integer.parseInt(StringUtil.getSubtract(item.getRepaymentNum(), item.getAlreadyRepaymentNum())) <= 0 ? "0" :
                                StringUtil.getSubtract(item.getRepaymentNum(), item.getAlreadyRepaymentNum()))) + "笔");
//        /*设置总进度（正常进度）*/
//        apbSchedule.setMaxValue(TextUtils.isEmpty(item.getRepaymentNum()) ? 0 : Integer.valueOf(item.getRepaymentNum()));
//        /*设置总进度（异常进度）*/
//        apbAbnormalSchedule.setMaxValue(TextUtils.isEmpty(item.getRepaymentNum()) ? 0 : Integer.valueOf(item.getRepaymentNum()));
//        /*设置进度*/
//        apbSchedule.setProgress(TextUtils.isEmpty(item.getAlreadyRepaymentNum()) ? 0 : Integer.valueOf(item.getAlreadyRepaymentNum()));
//        apbAbnormalSchedule.setProgress(TextUtils.isEmpty(item.getAlreadyRepaymentNum()) ? 0 : Integer.valueOf(item.getAlreadyRepaymentNum()));

        /*最大进度*/
        instance.setMaxValue(TextUtils.isEmpty(item.getRepaymentNum()) ? 0 : Integer.valueOf(item.getRepaymentNum()))
                /*实际进度*/
                .setProgress(TextUtils.isEmpty(item.getAlreadyRepaymentNum()) ? 0 : Integer.valueOf(item.getAlreadyRepaymentNum()));
    }
}
