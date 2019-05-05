package com.lucky.deer.weight;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lucky.deer.R;
import com.lucky.deer.weight.adapter.SelsetAisleAdapter;
import com.lucky.deer.weight.interfaces.SelsetAisleListener;
import com.lucky.model.response.cardspending.SwipeChannelEntity;

import java.util.List;

/**
 * 通道弹出框
 *
 * @author wangxiangyi
 * @date 20018/10/20
 */
public class SelsetAisleDialog extends Dialog implements BaseQuickAdapter.OnItemClickListener {

    private Context mContext;
    private SelsetAisleAdapter selsetAisleAdapter;
    private SelsetAisleListener mListener;

    public SelsetAisleDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public SelsetAisleDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        initView();
    }

    protected SelsetAisleDialog(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_select_bank, null);
        RecyclerView rvList = view.findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        selsetAisleAdapter = new SelsetAisleAdapter();
        rvList.setAdapter(selsetAisleAdapter);
        setContentView(view);
        selsetAisleAdapter.setOnItemClickListener(this);
        rvList.addItemDecoration(new DividerItemDecoration(rvList.getContext(), new LinearLayoutManager(mContext).getOrientation()));
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        /*获取屏幕宽、高用*/
        DisplayMetrics d = mContext.getResources().getDisplayMetrics();
        /* 高度设置为屏幕的0.8*/
        lp.width = (int) (d.widthPixels * 0.8);
        /*高度设置为屏幕的0.6*/
        lp.height = (int) (d.heightPixels * 0.5);
        dialogWindow.setAttributes(lp);
    }

    public void setData(List<SwipeChannelEntity> data) {
        selsetAisleAdapter.setNewData(data);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mListener.onItemClick(selsetAisleAdapter.getData().get(position));
        dismiss();
    }

    /**
     * 确定监听
     *
     * @param listener
     */
    public void setSelsetAisleListener(SelsetAisleListener listener) {
        mListener = listener;
    }


}
