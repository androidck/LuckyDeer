package com.lucky.deer.find.business.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.model.response.find.redEnvelope.PageBean;
import com.lucky.model.util.GlideUtils;


/**
 * 红包明细适配器
 *
 * @author wangxiangyi
 * @date 2019/03/26
 */
public class RedEnvelopesDetailAdapter extends BaseQuickAdapter<PageBean, BaseViewHolder> {
    /**
     * true：收红包 false：发红包
     */
    private boolean mIsReceiveRedEnvelope = true;

    public RedEnvelopesDetailAdapter() {
        super(R.layout.item_red_envelopes_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, PageBean item) {
        GlideUtils.loadImage(mContext, helper.getView(R.id.iv_image), (mIsReceiveRedEnvelope ? R.mipmap.money_record_icon : R.mipmap.money_send));
        /*收发时间*/
        helper.setText(R.id.tv_sending_time, mIsReceiveRedEnvelope ?
                TextUtils.isEmpty(item.getReceiveMoneyY()) ? "0.00" : item.getReceiveTime()
                :
                TextUtils.isEmpty(item.getIssueMoneyY()) ? "0.00" : item.getIssueTime())
                /*红包来源 */
                .setText(R.id.tv_red_envelope_source,
                        mIsReceiveRedEnvelope ?
                                String.format(mContext.getString(R.string.text_red_envelopes_detail_from), item.getEmitPeopleNickName())
                                :
                                item.getContext())
                /*红包金额*/
                .setText(R.id.tv_amount, mIsReceiveRedEnvelope ?
                        String.format(mContext.getString(R.string.text_red_envelopes_add), TextUtils.isEmpty(item.getReceiveMoneyY()) ? "0.00" : item.getReceiveMoneyY())
                        :
                        String.format(mContext.getString(R.string.text_red_envelopes_less), TextUtils.isEmpty(item.getIssueMoneyY()) ? "0.00" : item.getIssueMoneyY()))
                /*红包金额*/
                .setTextColor(R.id.tv_amount, mIsReceiveRedEnvelope ?
                        mContext.getResources().getColor(R.color.color_golden_ffc000)
                        :
                        mContext.getResources().getColor(R.color.colorBlack))

                .setText(R.id.tv_context, TextUtils.isEmpty(item.getContext()) ? "" : item.getContext())
                .setGone(R.id.tv_context, mIsReceiveRedEnvelope);
    }

    /**
     * 是否是收红包
     *
     * @param isReceiveRedEnvelope true：收红包 false：发红包
     */
    public void isReceiveRedEnvelope(boolean isReceiveRedEnvelope) {
        mIsReceiveRedEnvelope = isReceiveRedEnvelope;
    }
}
