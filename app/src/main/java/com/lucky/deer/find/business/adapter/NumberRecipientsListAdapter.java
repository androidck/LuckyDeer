package com.lucky.deer.find.business.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.deer.util.StringUtil;
import com.lucky.model.response.find.ReceiveRedEnvelopesUserVOListBean;
import com.lucky.model.util.GlideUtils;

/**
 * 领取人数列表
 *
 * @author wangxiangyi
 * @date 2019/03/26
 */
public class NumberRecipientsListAdapter extends BaseQuickAdapter<ReceiveRedEnvelopesUserVOListBean, BaseViewHolder> {
    public NumberRecipientsListAdapter() {
        super(R.layout.item_child_number_recipients);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReceiveRedEnvelopesUserVOListBean item) {
        /*设置头像*/
        GlideUtils.loadImage(mContext, helper.getView(R.id.qriv_avatar), item.getUserHead(),R.mipmap.user_photo_no);
        /*设置领取金额*/
        helper.setText(R.id.tv_nickname, StringUtil.setNumberFormatting(item.getMoney()));
    }
}
