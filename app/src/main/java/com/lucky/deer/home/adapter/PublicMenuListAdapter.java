package com.lucky.deer.home.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.model.response.financial.FinancialServicesEntity;
import com.lucky.model.util.GlideUtils;


/**
 * 公共列表
 *
 * @author wangxiangyi
 * @date 2018/11/13
 */
public class PublicMenuListAdapter extends BaseQuickAdapter<FinancialServicesEntity, BaseViewHolder> {
    private boolean mIsShow = false;

    public PublicMenuListAdapter() {
        super(R.layout.item_public_menu_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, FinancialServicesEntity item) {
        GlideUtils.loadImage(mContext, helper.getView(R.id.iv_logo), item.getIcon());
        helper.setText(R.id.tv_name, item.getMenuName())
                .setGone(R.id.tv_phone, mIsShow)
                .setText(R.id.tv_phone, item.getLinkHref());
    }

    /**
     * 是否显示电话
     */
    public void isShowPhone(boolean isShow) {
        mIsShow = isShow;
    }
}
