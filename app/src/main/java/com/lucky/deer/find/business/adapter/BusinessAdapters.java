package com.lucky.deer.find.business.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseRecyclerViewAdapter;
import com.lucky.deer.find.business.view.NineGridView;
import com.lucky.deer.find.business.view.adapter.NineImageAdapter;
import com.lucky.model.response.find.BusinessList;
import com.lucky.model.response.find.EnclosuresBean;
import com.lucky.model.util.GlideUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 上级列表适配器
 */
public class BusinessAdapters extends BaseRecyclerViewAdapter<BusinessAdapters.ViewHolder> {

    private Context mContext;
    private List<BusinessList> mData;
    private OnItemClickListener mListener;

    public BusinessAdapters(Context context,List<BusinessList> mData) {
        super(context);
        this.mContext=context;
        this.mData=mData;
        if (this.mData==null){
            this.mData=new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_find_child_business);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideUtils.loadImage(mContext, holder.qriv_avatar, mData.get(position).getUserHead(), R.mipmap.user_photo_no);
        holder.tvArticleTitle.setText(mData.get(position).getNickName());
        holder.tvContent.setText(mData.get(position).getContext());
        holder.tvReleaseTime.setText(String.format(mContext.getString(R.string.text_business_release_time), TextUtils.isEmpty(mData.get(position).getCreateDate()) ? "" : mData.get(position).getCreateDate()));
        initListener(holder);

        //九宫格图片
        List<String> list = new ArrayList<>();
        if (mData.get(position).getEnclosures() != null && mData.get(position).getEnclosures().size() > 0) {
            for (EnclosuresBean enclosure : mData.get(position).getEnclosures()) {
                list.add(enclosure.getEnclosure());
            }
        }
        holder.nine_grid_view.setAdapter(new NineImageAdapter(mContext, new RequestOptions().centerCrop(), DrawableTransitionOptions.withCrossFade(), list));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(position);
            }
        });
        holder.nine_grid_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(position);
            }
        });
        //拦截事件
        holder.nine_grid_view.OnClickListener(true);
    }


    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder{

        ImageView qriv_avatar;
        TextView tvArticleTitle,tvContent,tvReleaseTime;
        NineGridView nine_grid_view;
        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
            qriv_avatar= (ImageView) findViewById(R.id.qriv_avatar);
            tvArticleTitle= (TextView) findViewById(R.id.tv_article_title);
            tvContent= (TextView) findViewById(R.id.tv_content);
            tvReleaseTime= (TextView) findViewById(R.id.tv_release_time);
            nine_grid_view= (NineGridView) findViewById(R.id.nine_grid_view);
        }
    }

    private void initListener(ViewHolder holder) {
        TextView btnStart = (TextView) holder.findViewById(R.id.btn_state);
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
