package com.lucky.sharingfunction.dialog;

import android.content.Context;

import com.lucky.sharingfunction.R;
import com.lucky.sharingfunction.constant.KeyConstant;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

/**
 * 分享View
 *
 * @author wangxiangyi
 * @date 2018/11/30
 */
public class SharingFunctionView {
    private static SharingFunctionView instance;
    private static Context mContext;
    private QMUIBottomSheet build;
    private OnItemClickListener mListener;

    public static SharingFunctionView getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            synchronized (SharingFunctionView.class) {
                if (instance == null) {
                    instance = new SharingFunctionView();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化分享view
     *
     * @return
     */
    public SharingFunctionView initView() {
        build = new QMUIBottomSheet.BottomGridSheetBuilder(mContext)
                .addItem(R.mipmap.icon_friend, mContext.getString(R.string.we_chat), KeyConstant.TAG_SHARE_WE_CHAT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.icon_friendship, mContext.getString(R.string.friends_circle), KeyConstant.TAG_SHARE_FRIENDS_CIRCLE, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.icon_qq, mContext.getString(R.string.qq_space), KeyConstant.TAG_SHARE_QQ_SPACE, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.icon_qzone, mContext.getString(R.string.qq_zone), KeyConstant.TAG_SHARE_QQ_ZONE, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
//                .addItem(R.mipmap.icon_weibo, mContext.getString(R.string.wei_bo), KeyConstant.TAG_SHARE_WEI_BO, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
//                .addItem(R.mipmap.icon_ding, mContext.getString(R.string.nail), KeyConstant.TAG_SHARE_NAIL, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
//                .addItem(R.mipmap.icon_pay, mContext.getString(R.string.alipay), KeyConstant.TAG_SHARE_ALIPAY, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                .setOnSheetItemClickListener((dialog, itemView) -> {
                    dialog.dismiss();
                    mListener.onItemClick(Integer.parseInt(itemView.getTag().toString()));
                }).build();
        return this;
    }


    /**
     * 点击监听
     *
     * @param listener
     * @return
     */
    public SharingFunctionView setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
        return this;
    }

    /**
     * 显示分享
     */
    public void show() {
        if (build != null) {
            if (build.isShowing()) {
                build.dismiss();
            }
            build.show();
        }
    }
}
