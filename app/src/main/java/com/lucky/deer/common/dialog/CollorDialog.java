package com.lucky.deer.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.lucky.deer.R;
import com.lucky.deer.common.adapter.CollorDialogAdapter;
import com.lucky.model.response.find.redEnvelope.RedEnvelopeCollectionRangeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 红包dialog
 *
 * @date 2019/03/27
 */
public class CollorDialog extends Dialog {

//    @BindView(R.id.tv_unlimited)
//    TextView tvUnlimited;
//    @BindView(R.id.tv_male)
//    TextView tvMale;
//    @BindView(R.id.tv_female)
//    TextView tvFemale;
    /**
     * 内容列表
     */
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    /**
     * 控制点击dialog外部是否dismiss
     */
    private boolean iscancelable;
    /**
     * 精准定位信息
     */
    private List<RedEnvelopeCollectionRangeBean> mData;
    private Context context;
    private OnItemClickListener onItemClickListener;
    /**
     * 适配器
     */
    private CollorDialogAdapter mAdapter;


    public CollorDialog(@NonNull Context context, boolean iscancelable, List<RedEnvelopeCollectionRangeBean> data, OnItemClickListener onItemClickListener) {
        super(context);
        this.context = context;
        this.iscancelable = iscancelable;
        this.mData = data;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_collar);
        ButterKnife.bind(this);
        /*点击外部不可dismiss*/
        setCancelable(iscancelable);
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        initView();
    }


    private void initView() {
        rvList.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new CollorDialogAdapter(mData);
        rvList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(mAdapter.getData().get(position));
                dismiss();
            }
        });
    }


    public interface OnItemClickListener {
        void onClick(RedEnvelopeCollectionRangeBean data);
    }
}
