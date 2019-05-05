package com.lucky.sharingfunction;

import android.content.Context;
import android.util.TypedValue;

/**
 * dp,sp和px之间的转换
 *
 * @author wangxiangyi
 * @date 2018/10/25
 */
public class DisplayUtils {
    /**
     * 将px转换为其等效的dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 将dp转换为等效的px
     * <p>
     * final float scale = Resources.getSystem().getDisplayMetrics().density;
     * return (int) (dp * scale + 0.5f);
     */
    public static int dp2px(Context context, float dipValue) {
        /*同时系统也提供了TypedValue类帮助我们转换*/
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, context.getResources().getDisplayMetrics());
        if (px > 0) {
            return px;
        } else {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dipValue * scale + 0.5f);
        }
    }


    /**
     * 将px转换为等效的sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * 将sp转换为等效的px
     */
    public static int sp2px(Context context, float spValue) {
        /*同时系统也提供了TypedValue类帮助我们转换*/
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
        if (px > 0) {
            return px;
        } else {
            final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (spValue * fontScale + 0.5f);
        }
    }
}
