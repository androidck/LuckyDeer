package com.lucky.deer.find.business.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.deer.find.business.view.NineGridView;
import com.lucky.deer.find.business.view.adapter.NineImageAdapter;
import com.lucky.model.response.find.BusinessList;
import com.lucky.model.response.find.EnclosuresBean;
import com.lucky.model.util.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 商机适配器
 *
 * @author wangxiangyu
 * @date 2019/03/25
 */
public class BusinessAdapter extends BaseQuickAdapter<BusinessList, BaseViewHolder> {
    private OnItemClickListener mListener;


    public BusinessAdapter() {
        super(R.layout.item_find_child_business);
    }

    @Override
    protected void convert(BaseViewHolder helper, BusinessList item) {
        /*展示头像*/
        GlideUtils.loadImage(mContext, helper.getView(R.id.qriv_avatar), item.getUserHead(), R.mipmap.user_photo_no);
        /*设置昵称*/
        helper.setText(R.id.tv_article_title, item.getNickName())
                /*设置文章内容*/
                .setText(R.id.tv_content, item.getContext())
                /*发布时间*/
                .setText(R.id.tv_release_time, String.format(mContext.getString(R.string.text_business_release_time), TextUtils.isEmpty(item.getCreateDate()) ? "" : item.getCreateDate()));
        /*初始化全看*/
        initListener(helper);
        /*获取展示图片控件*/
        NineGridView nineGridView = helper.getView(R.id.nine_grid_view);
        List<String> list = new ArrayList<>();
        if (item.getEnclosures() != null && item.getEnclosures().size() > 0) {
            for (EnclosuresBean enclosure : item.getEnclosures()) {
                list.add(enclosure.getEnclosure());
            }
        }
        nineGridView.setAdapter(new NineImageAdapter(mContext, new RequestOptions().centerCrop(), DrawableTransitionOptions.withCrossFade(), list));
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(helper.getPosition());
            }
        });
        nineGridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(helper.getPosition());
            }
        });
    }

    private void initListener(BaseViewHolder helper) {
        TextView btnStart = helper.getView(R.id.btn_state);
        btnStart.setOnClickListener(v -> {
            if (btnStart.getMaxLines() == Integer.MAX_VALUE) {
                btnStart.setMaxLines(3);
                btnStart.setText("收起");
            } else {
                btnStart.setMaxLines(Integer.MAX_VALUE);
                btnStart.setText("全文");
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
