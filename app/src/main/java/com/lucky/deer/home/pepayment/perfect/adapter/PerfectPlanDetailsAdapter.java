package com.lucky.deer.home.pepayment.perfect.adapter;

import android.support.v7.widget.CardView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.deer.util.DateUtil;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.enums.ExecutionStatusEnum;
import com.lucky.model.response.home.pepayment.perfect.PrefectpayInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 完美养卡详情适配器
 *
 * @author wangxiangyi
 * @date 2019/02/18
 */
public class PerfectPlanDetailsAdapter extends BaseQuickAdapter<List<PrefectpayInfo>, BaseViewHolder> {
    /**
     * 异常信息
     */
    private final String aberrantText = "aberrantText";
    /**
     * 异常信息字体颜色
     */
    private final String aberrantTextColor = "aberrantTextColor";

    public PerfectPlanDetailsAdapter() {
        super(R.layout.item_perfect_plan_details);
    }

    @Override
    protected void convert(BaseViewHolder helper, List<PrefectpayInfo> item) {
        ((CardView) helper.getView(R.id.card_view)).setCardBackgroundColor(mContext.getResources().getColor(R.color.color_golden_ffc000));
        /*遍历每一组计划*/
        for (int i = 1; i <= item.size(); i++) {
            PrefectpayInfo itemData = item.get(i - 1);
            switch (i) {
                /*第一天消费*/
                case 1:
                    /*消费日期*/
                    helper.setText(R.id.tv_first_day_date, String.format(mContext.getString(R.string.text_perfect_plan_details_date), DateUtil.stampToDate(itemData.getAmTime(), DateUtil.y_m_d)))
                            /*上午消费时间*/
                            .setText(R.id.tv_first_day_morning_time, DateUtil.stampToDate(itemData.getAmTime(), DateUtil.h_m))
                            /*上午消费金额*/
                            .setText(R.id.tv_first_day_morning_consumption, String.format(mContext.getString(R.string.text_perfect_plan_details_consumption), StringUtil.setNumberFormatting(itemData.getAmPaymoney())))
                            /*上午消费状态*/
//                            .setText(R.id.tv_first_day_morning_status, ExecutionStatusEnum.getSearchStatusName(ExecutionStatusEnum.consumption_status, itemData.getAmPaymoneyStatus()))
                            .setText(R.id.tv_first_day_morning_status, String.valueOf(getConsumptionStatusAndTextColor(true, itemData).get(aberrantText)))
                            /*上午消费状态字体颜色*/
                            .setTextColor(R.id.tv_first_day_morning_status, mContext.getResources().getColor((int) getConsumptionStatusAndTextColor(true, itemData).get(aberrantTextColor)))
                            /*下午消费时间*/
                            .setText(R.id.tv_first_day_afternoon_time, DateUtil.stampToDate(itemData.getPmTime(), DateUtil.h_m))
                            /*下午消费金额*/
                            .setText(R.id.tv_first_day_afternoon_consumption, String.format(mContext.getString(R.string.text_perfect_plan_details_consumption), StringUtil.setNumberFormatting(itemData.getPmPaymoney())))
                            /*下午消费状态*/
//                            .setText(R.id.tv_first_day_afternoon_status, ExecutionStatusEnum.getSearchStatusName(ExecutionStatusEnum.consumption_status, itemData.getPmPaymoneyStatus()));
                            .setText(R.id.tv_first_day_afternoon_status, String.valueOf(getConsumptionStatusAndTextColor(false, itemData).get(aberrantText)))
                            /*下午消费状态字体颜色*/
                            .setTextColor(R.id.tv_first_day_afternoon_status, mContext.getResources().getColor((int) getConsumptionStatusAndTextColor(false, itemData).get(aberrantTextColor)));
                    break;
                /*第二天消费*/
                case 2:
                    /*消费日期*/
                    helper.setText(R.id.tv_next_day_date, String.format(mContext.getString(R.string.text_perfect_plan_details_date), DateUtil.stampToDate(itemData.getAmTime(), DateUtil.y_m_d)))
                            /*上午消费时间*/
                            .setText(R.id.tv_next_day_morning_time, DateUtil.stampToDate(itemData.getAmTime(), DateUtil.h_m))
                            /*上午消费金额*/
                            .setText(R.id.tv_next_day_morning_consumption, String.format(mContext.getString(R.string.text_perfect_plan_details_consumption), StringUtil.setNumberFormatting(itemData.getAmPaymoney())))
                            /*上午消费状态*/
//                            .setText(R.id.tv_next_day_morning_status, ExecutionStatusEnum.getSearchStatusName(ExecutionStatusEnum.consumption_status, itemData.getAmPaymoneyStatus()))
                            .setText(R.id.tv_next_day_morning_status, String.valueOf(getConsumptionStatusAndTextColor(true, itemData).get(aberrantText)))
                            /*上午消费状态字体颜色*/
                            .setTextColor(R.id.tv_next_day_morning_status, mContext.getResources().getColor((int) getConsumptionStatusAndTextColor(true, itemData).get(aberrantTextColor)))
                            /*下午消费时间*/
                            .setText(R.id.tv_next_day_afternoon_time, DateUtil.stampToDate(itemData.getPmTime(), DateUtil.h_m))
                            /*下午消费金额*/
                            .setText(R.id.tv_next_day_afternoon_consumption, String.format(mContext.getString(R.string.text_perfect_plan_details_consumption), StringUtil.setNumberFormatting(itemData.getPmPaymoney())))
                            /*下午消费状态*/
//                            .setText(R.id.tv_next_day_afternoon_status, ExecutionStatusEnum.getSearchStatusName(ExecutionStatusEnum.consumption_status, itemData.getPmPaymoneyStatus()));
                            .setText(R.id.tv_next_day_afternoon_status, String.valueOf(getConsumptionStatusAndTextColor(false, itemData).get(aberrantText)))
                            /*下午消费状态字体颜色*/
                            .setTextColor(R.id.tv_next_day_afternoon_status, mContext.getResources().getColor((int) getConsumptionStatusAndTextColor(false, itemData).get(aberrantTextColor)));
                    break;
                /*第三天消费*/
                case 3:
                    /*消费日期*/
                    helper.setText(R.id.tv_third_day_date, String.format(mContext.getString(R.string.text_perfect_plan_details_date), DateUtil.stampToDate(itemData.getAmTime(), DateUtil.y_m_d)))
                            /*上午消费时间*/
                            .setText(R.id.tv_third_day_morning_time, DateUtil.stampToDate(itemData.getAmTime(), DateUtil.h_m))
                            /*上午消费金额*/
                            .setText(R.id.tv_third_day_morning_consumption, String.format(mContext.getString(R.string.text_perfect_plan_details_consumption), StringUtil.setNumberFormatting(itemData.getAmPaymoney())))
                            /*上午消费状态*/
//                            .setText(R.id.tv_third_day_morning_status, ExecutionStatusEnum.getSearchStatusName(ExecutionStatusEnum.consumption_status, itemData.getAmPaymoneyStatus()))
                            .setText(R.id.tv_third_day_morning_status, String.valueOf(getConsumptionStatusAndTextColor(true, itemData).get(aberrantText)))
                            /*上午消费状态字体颜色*/
                            .setTextColor(R.id.tv_third_day_morning_status, mContext.getResources().getColor((int) getConsumptionStatusAndTextColor(true, itemData).get(aberrantTextColor)))
                            /*下午消费时间*/
                            .setText(R.id.tv_third_day_afternoon_time, DateUtil.stampToDate(itemData.getPmTime(), DateUtil.h_m))
                            /*下午消费金额*/
                            .setText(R.id.tv_third_day_afternoon_consumption, String.format(mContext.getString(R.string.text_perfect_plan_details_consumption), StringUtil.setNumberFormatting(itemData.getPmPaymoney())))
                            /*下午消费状态*/
//                            .setText(R.id.tv_third_day_afternoon_status, ExecutionStatusEnum.getSearchStatusName(ExecutionStatusEnum.consumption_status, itemData.getPmPaymoneyStatus()));
                            .setText(R.id.tv_third_day_afternoon_status, String.valueOf(getConsumptionStatusAndTextColor(false, itemData).get(aberrantText)))
                            /*下午消费状态字体颜色*/
                            .setTextColor(R.id.tv_third_day_afternoon_status, mContext.getResources().getColor((int) getConsumptionStatusAndTextColor(false, itemData).get(aberrantTextColor)));
                    break;
                /*第四天养卡*/
                case 4:
                    /*结束日期*/
                    helper.setText(R.id.tv_fourth_day_date, String.format(mContext.getString(R.string.text_perfect_plan_details_date), DateUtil.stampToDate(itemData.getReturnTime(), DateUtil.y_m_d)))
                            /*养卡时间*/
                            .setText(R.id.tv_fourth_day_repayment_time, DateUtil.stampToDate(itemData.getReturnTime(), DateUtil.h_m))
                            /*养卡金额*/
                            .setText(R.id.tv_fourth_day_repayment, String.format(mContext.getString(R.string.text_perfect_plan_details_repayment), StringUtil.setNumberFormatting(itemData.getReturnMoney())))
                            /*养卡状态*/
                            .setText(R.id.tv_fourth_day_repayment_status, ExecutionStatusEnum.getSearchStatusName(ExecutionStatusEnum.consumption_status1, itemData.getReturnStatus()))
                            /*养卡状态字体颜色*/
                            .setTextColor(R.id.tv_fourth_day_repayment_status, mContext.getResources().getColor(ExecutionStatusEnum.getSearchStatusTextColor(ExecutionStatusEnum.consumption_status1, itemData.getReturnStatus())));
                    break;
                default:
            }
        }
    }

    /**
     * 消费状态
     *
     * @param isMorning true：上午，false：下午
     * @param itemData  信息
     * @return 返回错误信息
     */
    private Map<String, Object> getConsumptionStatusAndTextColor(boolean isMorning, PrefectpayInfo itemData) {
        Map<String, Object> map = new HashMap<>();
        switch (isMorning ? itemData.getAmPaymoneyStatus() : itemData.getPmPaymoneyStatus()) {
            case "2":
            case "3":
            case "5":
                /*接口返回错误信息*/
                map.put(aberrantText, itemData.getExceptionDescribe());
                break;
            default:
                /*本地错误信息*/
                map.put(aberrantText, ExecutionStatusEnum.getSearchStatusName(ExecutionStatusEnum.consumption_status, isMorning ? itemData.getAmPaymoneyStatus() : itemData.getPmPaymoneyStatus()));
                break;
        }
        map.put(aberrantTextColor, ExecutionStatusEnum.getSearchStatusTextColor(ExecutionStatusEnum.consumption_status, isMorning ? itemData.getAmPaymoneyStatus() : itemData.getPmPaymoneyStatus()));
        return map;
    }
}
