package com.lucky.deer.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucky.deer.R;
import com.lucky.model.util.GlideUtils;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;

/**
 * 红包dialog
 */
public class RedEnvelopesDialog extends Dialog {

    private boolean isCancelable;//控制点击dialog外部是否dismiss
    private View view;
    private Context mContext;
    private QMUIRadiusImageView imgHeadImage;
    private TextView tvNickName;
    private ImageView btnOpen;
    /**
     *
     */
    private View.OnClickListener mListener;
    /**
     * 头像
     */
    private String mUrl;
    /**
     * 昵称
     */
    private String mNickName;

    public RedEnvelopesDialog(@NonNull Context context, boolean isCancelable) {
        super(context);
        this.mContext = context;
        this.isCancelable = isCancelable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        /*这行一定要写在前面*/
        setContentView(R.layout.dialog_red_envelopes);
        /*点击外部不可dismiss*/
        setCancelable(isCancelable);
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        initView();
        initListener();
        initData();
    }

    /**
     * 初始化监听
     */
    private void initView() {
        imgHeadImage = findViewById(R.id.img_head_image);
        tvNickName = findViewById(R.id.tv_nick_name);
        btnOpen = findViewById(R.id.btn_open);
    }

    /**
     * 监听
     */
    private void initListener() {
        btnOpen.setOnClickListener(v -> {
            if (mListener != null) {
                dismiss();
                mListener.onClick(v);
            }
        });
    }

    /**
     * 设置数据
     */
    private void initData() {
        if (TextUtils.isEmpty(mUrl)) {
            GlideUtils.loadImage(mContext, imgHeadImage, R.mipmap.user_photo_no);
        } else {
            GlideUtils.loadImage(mContext, imgHeadImage, mUrl);
        }
        if (!TextUtils.isEmpty(mNickName)) {
            tvNickName.setText(mNickName);
        }
    }

    /**
     * 设置头像
     *
     * @param url 头像地址
     * @return
     */
    public RedEnvelopesDialog setHeadImage(String url) {
        mUrl = url;
        return this;
    }

    /**
     * 设置头像
     *
     * @param nickName 昵称
     * @return
     */
    public RedEnvelopesDialog setNickName(String nickName) {
        mNickName = nickName;
        return this;
    }

    /**
     * 开红包的点击监听
     *
     * @param listener
     * @return
     */
    public RedEnvelopesDialog setOnClickListener(View.OnClickListener listener) {
        mListener = listener;
        return this;
    }
}
