package com.lucky.deer.mine.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.deer.util.StringUtil;
import com.lucky.model.response.mine.MessageBoardList;
import com.lucky.model.util.GlideUtils;

/**
 * 交易记录适配器
 *
 * @author wangxiangyi
 * @date 2018/11/29
 */
public class MessageBoardAdapter extends BaseQuickAdapter<MessageBoardList, BaseViewHolder> {
    public MessageBoardAdapter() {
        super(R.layout.item_message_board);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBoardList item) {
        String mTypeText = "";
        boolean mReadStatus;
        if (!TextUtils.isEmpty(item.getReadState())) {
            switch (item.getReadState()) {
                /*未查看*/
                case "0":
                    mTypeText = mContext.getString(R.string.not_viewed);
                    mReadStatus = false;
                    break;
                /*已查看*/
                case "1":
                    mTypeText = mContext.getString(R.string.viewed);
                    mReadStatus = true;
                    break;
                /*已查看，静等回复*/
                case "2":
                    mTypeText = mContext.getString(R.string.viewed_wait_reply);
                    mReadStatus = true;
                    break;
                default:
                    mTypeText = mContext.getString(R.string.not_viewed);
                    mReadStatus = false;
                    break;
            }
        }else {
            mTypeText = mContext.getString(R.string.not_viewed);
            mReadStatus = false;
        }
        GlideUtils.loadImage(mContext, helper.getView(R.id.iv_avatar), item.getUserHead());
        helper.setText(R.id.tv_nickname, item.getNickName())
                .setText(R.id.tv_date, StringUtil.stampToDate(item.getUpdateDate(), StringUtil.ymdhms))
                .setText(R.id.tv_phone, item.getPhone())
                .setText(R.id.tv_message_content, item.getLeaveMessageContent())
                .setChecked(R.id.cb_view_status, mReadStatus)
                .setText(R.id.cb_view_status, mTypeText)
                .addOnClickListener(R.id.tv_phone);
    }
}
