package com.lucky.deer.home.pepayment.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.deer.util.DateUtil;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.enums.ExecutionStatusEnum;
import com.lucky.model.response.home.pepayment.PlanDetailsEntity;

import java.util.List;

/**
 * 计划详情适配器
 *
 * @author wangxiangyi
 * @date 2018/12/14
 */
public class PlanDetailsAdapter extends BaseSectionQuickAdapter<PlanDetailsEntity, BaseViewHolder> {

    public PlanDetailsAdapter(List<PlanDetailsEntity> data) {
        super(R.layout.item_consumption_list, R.layout.item_plan_bill, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, PlanDetailsEntity item) {
        helper.setText(R.id.tv_blue_repayment_content, DateUtil.dateConversionFormat(item.getBillRepaymentTime(), "yyyy.MM.dd HH:mm", "yyyy.MM.dd"))
                .setGone(R.id.rrl_blue, true)
                .setGone(R.id.rrl_green, false)
                .setGone(R.id.rrl_red, false);
        TextView tvBlueRepaymentContent = helper.getView(R.id.tv_blue_repayment_content);
        tvBlueRepaymentContent.setSelected(true);
//        if (!TextUtils.isEmpty(ExecutionStatusEnum.getSearchStatusColor(ExecutionStatusEnum.repayment_status, item.getStatus()))) {
//            switch (ExecutionStatusEnum.getSearchStatusColor(ExecutionStatusEnum.repayment_status, item.getStatus())) {
//                case "blue":
//                    helper.setText(R.id.tv_blue_repayment_content, DateUtil.dateConversionFormat(item.getBillRepaymentTime(), "yyyy.MM.dd HH:mm", "yyyy.MM.dd"))
//                            .setGone(R.id.rrl_blue, true)
//                            .setGone(R.id.rrl_green, false)
//                            .setGone(R.id.rrl_red, false);
//                    TextView tvBlueRepaymentContent = helper.getView(R.id.tv_blue_repayment_content);
//                    tvBlueRepaymentContent.setSelected(true);
//                    break;
//                case "green":
//                    helper.setText(R.id.tv_green_repayment_content, DateUtil.dateConversionFormat(item.getBillRepaymentTime(), "yyyy.MM.dd HH:mm", "yyyy.MM.dd"))
//                            .setGone(R.id.rrl_blue, false)
//                            .setGone(R.id.rrl_green, true)
//                            .setGone(R.id.rrl_red, false);
//                    TextView tvRreenRepaymentContent = helper.getView(R.id.tv_green_repayment_content);
//                    tvRreenRepaymentContent.setSelected(true);
//                    break;
//                case "red":
//                    helper.setText(R.id.tv_red_repayment_content, DateUtil.dateConversionFormat(item.getBillRepaymentTime(), "yyyy.MM.dd HH:mm", "yyyy.MM.dd"))
//                            .setGone(R.id.rrl_blue, false)
//                            .setGone(R.id.rrl_green, false)
//                            .setGone(R.id.rrl_red, true);
//                    TextView tvRedRepaymentContent = helper.getView(R.id.tv_red_repayment_content);
//                    tvRedRepaymentContent.setSelected(true);
//                    break;
//                default:
//                    helper.setGone(R.id.rrl_blue, true)
//                            .setGone(R.id.rrl_green, false)
//                            .setGone(R.id.rrl_red, false);
//                    break;
//            }
//        } else {
//            helper.setGone(R.id.rrl_blue, true)
//                    .setGone(R.id.rrl_green, false)
//                    .setGone(R.id.rrl_red, false);
//        }
    }

    @Override
    protected void convert(BaseViewHolder helper, PlanDetailsEntity item) {
        /*不带圆角布局*/
        helper.setGone(R.id.ll_not_fillet_white, 0 != helper.getAdapterPosition() % item.getLastOne())
                /*圆角布局*/
                .setGone(R.id.rrl_fillet_white, 0 == helper.getAdapterPosition() % item.getLastOne())
                .setText(R.id.tv_consumption_date,
                        0 == helper.getAdapterPosition() % item.getLastOne() ?
                                ("1".equals(item.getStatus()) ?
                                        DateUtil.dateConversionFormat(item.getRepaymentTime(), "yyyy.MM.dd HH:mm", DateUtil.h_m) :
                                        DateUtil.dateConversionFormat(item.getBillRepaymentTime(), "yyyy.MM.dd HH:mm", DateUtil.h_m)) :
                                DateUtil.dateConversionFormat(item.getConsumptionTime(), "yyyy.MM.dd HH:mm", DateUtil.h_m))
//                .setText(R.id.tv_amount_consumption, mContext.getString(R.string.amount_consumption) + "：" + StringUtil.setNumberFormatting(item.getConsumptionMoney()))
                .setText(R.id.tv_amount_consumption,
                        0 == helper.getAdapterPosition() % item.getLastOne() ?
                                (mContext.getString(R.string.repayment) + "：¥" + StringUtil.setNumberFormatting(item.getRepaymentMoney())) :
                                (mContext.getString(R.string.consumption) + "：¥" + StringUtil.setNumberFormatting(item.getConsumptionMoney())))
//                .setText(R.id.tv_status, ExecutionStatusEnum.getSearchStatusName(ExecutionStatusEnum.consumption_status, item.getConsumptionStatus()))
                .setText(R.id.tv_status,
                        0 == helper.getAdapterPosition() % item.getLastOne() ?
                                ExecutionStatusEnum.getSearchStatusName(ExecutionStatusEnum.consumption_status1, item.getStatus()) :
                                (("2".equals(item.getConsumptionStatus()) || "3".equals(item.getConsumptionStatus()) || "5".equals(item.getConsumptionStatus())) ?
                                        item.getExceptionDescribe() :
                                        ExecutionStatusEnum.getSearchStatusName(ExecutionStatusEnum.consumption_status, item.getConsumptionStatus())))
//                .setTextColor(R.id.tv_status, mContext.getResources().getColor(ExecutionStatusEnum.getSearchStatusTextColor(ExecutionStatusEnum.consumption_status, item.getConsumptionStatus())));
                .setTextColor(R.id.tv_status,
                        0 == helper.getAdapterPosition() % item.getLastOne() ?
                                mContext.getResources().getColor(ExecutionStatusEnum.getSearchStatusTextColor(ExecutionStatusEnum.consumption_status1, item.getStatus())) :
                                mContext.getResources().getColor(ExecutionStatusEnum.getSearchStatusTextColor(ExecutionStatusEnum.consumption_status, item.getConsumptionStatus())))
                /*圆角布局*/
                .setText(R.id.tv_fillet_white_consumption_date,
                        0 == helper.getAdapterPosition() % item.getLastOne() ?
                                ("1".equals(item.getStatus()) ?
                                        DateUtil.dateConversionFormat(item.getRepaymentTime(), "yyyy.MM.dd HH:mm", DateUtil.h_m) :
                                        DateUtil.dateConversionFormat(item.getBillRepaymentTime(), "yyyy.MM.dd HH:mm", DateUtil.h_m)) :
                                DateUtil.dateConversionFormat(item.getConsumptionTime(), "yyyy.MM.dd HH:mm", DateUtil.h_m))
                .setText(R.id.tv_fillet_white_amount_consumption,
                        0 == helper.getAdapterPosition() % item.getLastOne() ?
                                (mContext.getString(R.string.repayment) + "：¥" + StringUtil.setNumberFormatting(item.getRepaymentMoney())) :
                                (mContext.getString(R.string.consumption) + "：¥" + StringUtil.setNumberFormatting(item.getConsumptionMoney())))
                .setText(R.id.tv_fillet_white_status,
                        0 == helper.getAdapterPosition() % item.getLastOne() ?
                                ExecutionStatusEnum.getSearchStatusName(ExecutionStatusEnum.consumption_status1, item.getStatus()) :
                                (("2".equals(item.getConsumptionStatus()) || "3".equals(item.getConsumptionStatus()) || "5".equals(item.getConsumptionStatus())) ?
                                        item.getExceptionDescribe() :
                                        ExecutionStatusEnum.getSearchStatusName(ExecutionStatusEnum.consumption_status, item.getConsumptionStatus())))
                .setTextColor(R.id.tv_fillet_white_status,
                        0 == helper.getAdapterPosition() % item.getLastOne() ?
                                mContext.getResources().getColor(ExecutionStatusEnum.getSearchStatusTextColor(ExecutionStatusEnum.consumption_status1, item.getStatus())) :
                                mContext.getResources().getColor(ExecutionStatusEnum.getSearchStatusTextColor(ExecutionStatusEnum.consumption_status, item.getConsumptionStatus())));

    }

//    public PlanDetailsAdapter() {
//        super(R.layout.item_plan_bill);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, PlanDetailsEntity item) {
//        if (!TextUtils.isEmpty(ExecutionStatusEnum.getSearchStatusColor(ExecutionStatusEnum.repayment_status, item.getStatus()))) {
//            switch (ExecutionStatusEnum.getSearchStatusColor(ExecutionStatusEnum.repayment_status, item.getStatus())) {
//                case "blue":
////                    helper.setText(R.id.tv_blue_repayment_content, item.getRepaymentTime() + "\u3000" +
////                            mContext.getString(R.string.plan_repayment) + "：" + StringUtil.setNumberFormatting(item.getBillRepaymentMoney()) + "\u3000" +
////                            mContext.getString(R.string.actual_repayment) + "：" + StringUtil.setNumberFormatting(item.getRepaymentMoney()))
////                    helper.setText(R.id.tv_blue_repayment_content, item.getBillRepaymentTime() + "\u3000" +
////                            mContext.getString(R.string.today_actual_consumption_amount) + "：¥" + StringUtil.setNumberFormatting(item.getConsumptionMoney()))
//                    helper.setText(R.id.tv_blue_repayment_content, DateUtil.dateConversionFormat(item.getBillRepaymentTime(),"yyyy.MM.dd HH:mm","yyyy.MM.dd"))
//                            .setGone(R.id.rrl_blue, true)
//                            .setGone(R.id.rrl_green, false)
//                            .setGone(R.id.rrl_red, false);
//                    TextView tvBlueRepaymentContent = helper.getView(R.id.tv_blue_repayment_content);
//                    tvBlueRepaymentContent.setSelected(true);
//                    break;
//                case "green":
////                    helper.setText(R.id.tv_green_repayment_content, item.getRepaymentTime() + "\u3000" +
////                            mContext.getString(R.string.plan_repayment) + "：" + StringUtil.setNumberFormatting(item.getBillRepaymentMoney()) + "\u3000" +
////                            mContext.getString(R.string.actual_repayment) + "：" + StringUtil.setNumberFormatting(item.getRepaymentMoney()))
////                    helper.setText(R.id.tv_green_repayment_content, item.getBillRepaymentTime() + "\u3000" +
////                            mContext.getString(R.string.today_actual_consumption_amount) + "：¥" + StringUtil.setNumberFormatting(item.getConsumptionMoney()))
//                    helper.setText(R.id.tv_green_repayment_content, DateUtil.dateConversionFormat(item.getBillRepaymentTime(),"yyyy.MM.dd HH:mm","yyyy.MM.dd"))
//                            .setGone(R.id.rrl_blue, false)
//                            .setGone(R.id.rrl_green, true)
//                            .setGone(R.id.rrl_red, false);
//                    TextView tvRreenRepaymentContent = helper.getView(R.id.tv_green_repayment_content);
//                    tvRreenRepaymentContent.setSelected(true);
//                    break;
//                case "red":
////                    helper.setText(R.id.tv_red_repayment_content, item.getRepaymentTime() + "\u3000" +
////                            mContext.getString(R.string.plan_repayment) + "：" + StringUtil.setNumberFormatting(item.getBillRepaymentMoney()) + "\u3000" +
////                            mContext.getString(R.string.actual_repayment) + "：" + StringUtil.setNumberFormatting(item.getRepaymentMoney()))
////                    helper.setText(R.id.tv_red_repayment_content, item.getBillRepaymentTime() + "\u3000" +
////                            mContext.getString(R.string.today_actual_consumption_amount) + "：¥" + StringUtil.setNumberFormatting(item.getConsumptionMoney()))
//                    helper.setText(R.id.tv_red_repayment_content, DateUtil.dateConversionFormat(item.getBillRepaymentTime(),"yyyy.MM.dd HH:mm","yyyy.MM.dd"))
//                            .setGone(R.id.rrl_blue, false)
//                            .setGone(R.id.rrl_green, false)
//                            .setGone(R.id.rrl_red, true);
//                    TextView tvRedRepaymentContent = helper.getView(R.id.tv_red_repayment_content);
//                    tvRedRepaymentContent.setSelected(true);
//                    break;
//                default:
//                    helper.setGone(R.id.rrl_blue, false)
//                            .setGone(R.id.rrl_green, false)
//                            .setGone(R.id.rrl_red, false);
//                    break;
//            }
//            if (item.getConsume() != null) {
//                initConsumptionList(helper, item);
//            }
//        } else {
//            helper.setGone(R.id.rrl_blue, false)
//                    .setGone(R.id.rrl_green, false)
//                    .setGone(R.id.rrl_red, false);
//        }
//    }
//
//    /**
//     * 初始化消费列表
//     */
//    private void initConsumptionList(BaseViewHolder helper, PlanDetailsEntity planDetailsEntity) {
//        RecyclerView rvList = helper.getView(R.id.rv_list);
//        rvList.setLayoutManager(new LinearLayoutManager(mContext));
//        List<ConsumeList> dataConsume = planDetailsEntity.getConsume();
//        ConsumptionListAdapter consumptionListAdapter = new ConsumptionListAdapter(dataConsume);
//        consumptionListAdapter.setLastOne(dataConsume.size() - 1);
//        rvList.setAdapter(consumptionListAdapter);
//    }
}
