package com.lucky.deer.home.onlineApplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucky.deer.R;
import com.lucky.deer.base.BaseRecyclerViewAdapter;
import com.lucky.deer.common.web.BrowserActivity;
import com.lucky.deer.common.web.SonicJavaScriptInterface;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.view.AdapterView;
import com.lucky.model.response.home.OnlineApplicationEntity;
import com.lucky.model.util.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 网申网贷适配器
 *
 * @author wangxiangyi
 * @date 2019/03/16
 */
public class OnlineNewApplicationAdapter extends BaseRecyclerViewAdapter<OnlineNewApplicationAdapter.ViewHolder> {

    public Context mContext;
    public List<OnlineApplicationEntity> mData;


    public OnlineNewApplicationAdapter(Context context,List<OnlineApplicationEntity> mData) {
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
        return new ViewHolder(parent,R.layout.item_recommend_bank);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cardName.setText(mData.get(position).getBankName());
        GlideUtils.loadImage(mContext,holder.imgBankLogo,mData.get(position).getCardImage());
        holder.tvDesc.setText(mData.get(position).getCardRemarks());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdapterView.itemClickEffect(holder);
            }
        });
        //我要办卡
        holder.btnBankCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBrowserActivity(mContext,1,mData.get(position).getAddress(),mData.get(position).getBankName());
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder{

        ImageView imgBankLogo;
        TextView cardName,tvDesc,tvNumber;
        Button btnBankCard;
        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
            imgBankLogo= (ImageView) findViewById(R.id.img_bank);
            cardName= (TextView) findViewById(R.id.card_name);
            tvDesc= (TextView) findViewById(R.id.tv_desc);
            tvNumber= (TextView) findViewById(R.id.tv_number);
            btnBankCard= (Button) findViewById(R.id.btn_bank_card);
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
