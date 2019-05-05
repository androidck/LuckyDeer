package com.lucky.deer.home.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.model.response.financial.FinancialServicesEntity;
import com.lucky.model.util.GlideUtils;

import java.util.List;

/**
 * 金融服务适配器
 *
 * @author wangxiangyi
 * @date 2018/11/07
 */
public class FinancialServicesAdapter extends BaseSectionQuickAdapter<FinancialServicesEntity, BaseViewHolder> {

    public FinancialServicesAdapter(List<FinancialServicesEntity> data) {
        super(R.layout.item_financial_services, R.layout.item_financial_services, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, FinancialServicesEntity item) {
        helper.setGone(R.id.v_line, true)
                .setText(R.id.tv_service_title, item.header)
                .setGone(R.id.ll_function, false);
    }

    @Override
    protected void convert(BaseViewHolder helper, FinancialServicesEntity item) {
        GlideUtils.loadImage(mContext, helper.getView(R.id.iv_function_one), item.getIcon());
        helper.setGone(R.id.v_line, false)
                .setGone(R.id.tv_service_title, false)
                .setGone(R.id.ll_function, true)
                .setText(R.id.tv_function_one, item.getMenuName())
                .addOnClickListener(R.id.ll_function);
    }

}
