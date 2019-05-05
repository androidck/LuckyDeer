package com.lucky.deer.home.onlineApplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucky.deer.R;
import com.lucky.deer.base.BaseRecyclerViewAdapter;
import com.lucky.deer.common.web.BrowserActivity;
import com.lucky.deer.common.web.SonicJavaScriptInterface;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.view.AdapterView;
import com.lucky.model.response.home.FindArticleEntity;
import com.lucky.model.util.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 网申网贷适配器
 *
 * @author wangxiangyi
 * @date 2019/03/16
 */
public class OnlineArticleAdapter extends BaseRecyclerViewAdapter<OnlineArticleAdapter.ViewHolder> {

    public Context mContext;
    public List<FindArticleEntity> mData;


    public OnlineArticleAdapter(Context context,List<FindArticleEntity> mData) {
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
        return new ViewHolder(parent,R.layout.item_article);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideUtils.loadImage(mContext,holder.imgArticle,mData.get(position).getCoverPhoto());
        holder.tvArticleTitle.setText(mData.get(position).getTitile());
        holder.tvIsRead.setText("阅读量\t"+mData.get(position).getReadCount());
        String [] str=mData.get(position).getCreateDate().split(" ");
        holder.tvDate.setText(str[0]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdapterView.itemClickEffect(holder);
                startBrowserActivity(mContext,1,String.format(HttpConstant.H5_HELP_DETAILS_CONNECT, mData.get(position).getId()),mData.get(position).getTitile());
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder{

        ImageView imgArticle;
        TextView tvArticleTitle,tvArticleDesc,tvIsRead,tvDate;

        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
            imgArticle= (ImageView) findViewById(R.id.img_article);
            tvArticleTitle= (TextView) findViewById(R.id.tv_article_title);
            tvIsRead= (TextView) findViewById(R.id.tv_is_read);
            tvDate= (TextView) findViewById(R.id.tv_date);

        }
    }

    //跳转到浏览器
    public void startBrowserActivity(Context context,int mode, String url,String title) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra(BrowserActivity.PARAM_URL, url);
        intent.putExtra(BrowserActivity.PARAM_MODE, mode);
        intent.putExtra(KeyConstant.DETAILS_TITLE,title);
        intent.putExtra(SonicJavaScriptInterface.PARAM_CLICK_TIME, System.currentTimeMillis());
        mContext.startActivity(intent);
    }

}
