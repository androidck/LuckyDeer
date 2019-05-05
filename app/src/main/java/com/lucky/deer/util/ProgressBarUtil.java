package com.lucky.deer.util;

import android.content.Context;
import android.widget.ProgressBar;

import com.lucky.deer.R;

/**
 * 进度条工具类
 *
 * @author wangxiangyi
 * @date 2018/01/24
 */
public class ProgressBarUtil {

    private static ProgressBarUtil instances;
    private static Context mContext;
    private static ProgressBar mProgressBar;
    /**
     * 蓝色圆角样式
     */
    public static int layerProgressDrawableColorBlue = R.drawable.layer_progress_drawable_color_blue;
    /**
     * 红色圆角样式
     */
    public static int layerProgressDrawableColorRed = R.drawable.layer_progress_drawable_color_red;

    public static ProgressBarUtil getInstance(Context context, ProgressBar progressBar) {
        mContext = context;
        mProgressBar = progressBar;
        if (instances == null) {
            synchronized (ProgressBarUtil.class) {
                if (instances == null) {
                    instances = new ProgressBarUtil();
                }
            }
        }
        return instances;
    }

    /**
     * 设置进度样式
     *
     * @return
     */
    public ProgressBarUtil setProgressDrawable(int drawable) {
        if (mContext != null && mProgressBar != null && drawable > 0) {
            /*设置样式*/
            mProgressBar.setProgressDrawable(mContext.getResources().getDrawable(drawable));
//            /*设置样式*/
//            mProgressBar.setProgressDrawable(
//                    "red".equals(ExecutionStatusEnum.getSearchStatusColor(ExecutionStatusEnum.implementation_plan, mPepaymentPlanData.getStatus())) ?
//                            mContext.getResources().getDrawable(R.drawable.layer_progress_drawable_color_red) :
//                            mContext.getResources().getDrawable(R.drawable.layer_progress_drawable_color_blue)
//            );
        }
        return this;
    }

    /**
     * 设置总进度
     *
     * @return
     */
    public ProgressBarUtil setMaxValue(int maximumProgress) {
        if (mProgressBar != null) {
            mProgressBar.setMax(maximumProgress);
        }
        return this;
    }

    /**
     * 设置进度
     *
     * @return
     */
    public ProgressBarUtil setProgress(int progress) {
        if (mProgressBar != null) {
            mProgressBar.setProgress(progress);
        }
        return this;
    }

}
