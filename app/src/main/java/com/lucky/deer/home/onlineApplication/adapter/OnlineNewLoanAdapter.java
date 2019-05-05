package com.lucky.deer.home.onlineApplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucky.deer.R;
import com.lucky.deer.base.BaseRecyclerViewAdapter;
import com.lucky.deer.common.web.BrowserActivity;
import com.lucky.deer.common.web.SonicJavaScriptInterface;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.view.AdapterView;
import com.lucky.model.response.home.OnlineLoanEntity;
import com.lucky.model.util.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 网申网贷适配器
 *
 * @author wangxiangyi
 * @date 2019/03/16
 */
public class OnlineNewLoanAdapter extends BaseRecyclerViewAdapter<OnlineNewLoanAdapter.ViewHolder> {

    public Context mContext;
    public List<OnlineLoanEntity> mData;


    public OnlineNewLoanAdapter(Context context,List<OnlineLoanEntity> mData) {
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
        return new ViewHolder(parent,R.layout.item_net_loan);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideUtils.loadImage(mContext,holder.imgLoan,mData.get(position).getIco());
        holder.loanName.setText(mData.get(position).getName());
        holder.tvLoanDesc.setText(mData.get(position).getLendingMethod());
        holder.tvQuota.setText(mData.get(position).getCreditQuota()+"元");
        String html= "日利率\t<font color='#F5A623'>"+mData.get(position).getDayInterestRate()+"%</font>";
        holder.tvDateRate.setText(Html.fromHtml(html));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdapterView.itemClickEffect(holder);
                startBrowserActivity(mContext,1,mData.get(position).getAddress(),mData.get(position).getName());
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder{

        ImageView imgLoan;
        TextView loanName,tvLoanDesc,tvNumber,tvQuota,tvDateRate;

        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
            imgLoan= (ImageView) findViewById(R.id.img_loan);
            loanName= (TextView) findViewById(R.id.loan_name);
            tvLoanDesc= (TextView) findViewById(R.id.tv_loan_desc);
            tvNumber= (TextView) findViewById(R.id.tv_number);
            tvQuota= (TextView) findViewById(R.id.tv_quota);
            tvDateRate= (TextView) findViewById(R.id.tv_date_rate);
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
