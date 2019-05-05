package com.lucky.deer.weight.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lucky.deer.R;
import com.lucky.model.common.dialog.Day;

import java.util.List;

/**
 * 日期适配器
 * Created by Administrator on 2018/4/14.
 */

public class StampAdapter extends RecyclerView.Adapter<StampViewHolder> {

    private LayoutInflater inflater;

    private Context mContext;
    private List<Day> mDatas;

    private boolean mSelect;


    private OnResfreshListener onResfreshListener;


    public StampAdapter(Context context, List<Day> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public StampViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_day, parent, false);
        StampViewHolder holder = new StampViewHolder(view);
        return holder;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(final StampViewHolder holder, final int position) {
        holder.item_day.setText(mDatas.get(position).getDays());
        if (mDatas.get(position).getIsSelect()) {
            holder.item_day.setBackground(mContext.getResources().getDrawable(R.drawable.round_blue_full));
            holder.item_day.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.item_day.setBackgroundColor(Color.parseColor("#00ffffff"));
            holder.item_day.setTextColor(Color.parseColor("#222222"));
        }

        //权限监听
//        if (onResfreshListener != null) {
//            boolean isSelect = false;
//            for (int i = 0; i < mDatas.size(); i++) {
//                if (!mDatas.get(i).getIsSelect()) {
//                    isSelect = false;
//                    holder.item_day.setBackgroundColor(Color.parseColor("#00ffffff"));
//                    mDatas.get(position).setSelect(false);
//                    holder.item_day.setTextColor(Color.parseColor("#222222"));
//                    break;
//                } else {
//                    isSelect = true;
//                    holder.item_day.setBackground(mContext.getResources().getDrawable(R.drawable.round_blue_full));
//                    mDatas.get(position).setSelect(true);
//                    holder.item_day.setTextColor(Color.parseColor("#ffffff"));
//                    break;
//                }
//            }
//            onResfreshListener.onResfresh(isSelect);
//        }

        //全选当前日期
        holder.item_day.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                if (mDatas.get(position).getIsSelect() == false) {
                    holder.item_day.setBackground(mContext.getResources().getDrawable(R.drawable.round_blue_full));
                    mDatas.get(position).setSelect(true);
                    holder.item_day.setTextColor(Color.parseColor("#ffffff"));
                } else {
                    holder.item_day.setBackgroundColor(Color.parseColor("#00ffffff"));
                    mDatas.get(position).setSelect(false);
                    holder.item_day.setTextColor(Color.parseColor("#222222"));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //实时刷新监听
    public interface OnResfreshListener {
        void onResfresh(boolean isSelect);
    }


    public void setRefreshListener(OnResfreshListener onResfreshListener) {
        this.onResfreshListener = onResfreshListener;
    }
}

class StampViewHolder extends RecyclerView.ViewHolder {

    TextView item_day;

    public StampViewHolder(View itemView) {
        super(itemView);
        item_day = itemView.findViewById(R.id.item_day);
    }
}
