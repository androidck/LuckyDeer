package com.lucky.model.util;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

/**
 * 屏幕适配
 *
 * @author wangxiangyi
 * @date 2018/10/8
 */
public class DensityUtils {
    private static float appDensity;
    private static float appScaledDensity;
    private static DisplayMetrics appDisplayMetrics;
    private static int barHeight;
    public final static String WIDTH = "width";
    public final static String HEIGHT = "height";

    /**
     * 在Application里初始化一下
     *
     * @param application
     */
    public static void setDensity(@NonNull final Application application) {
        //获取application的DisplayMetrics
        appDisplayMetrics = application.getResources().getDisplayMetrics();
        //获取状态栏高度
        barHeight = getStatusBarHeight(application);

        if (appDensity == 0) {
            //初始化的时候赋值
            appDensity = appDisplayMetrics.density;
            appScaledDensity = appDisplayMetrics.scaledDensity;

            //添加字体变化的监听
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //字体改变后,将appScaledDensity重新赋值
                    if (newConfig != null && newConfig.fontScale > 0) {
                        appScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {
                }
            });
        }
    }

    /**
     * 此方法在BaseActivity中做初始化<br>
     * 在setContentView()之前设置
     *
     * @param activity 当前页面
     */
    public static void setDefault(Activity activity) {
        setAppOrientation(activity, WIDTH);
    }

    public static void setConfiguration(Activity activity) {
        /*获取设置的配置信息*/
        Configuration mConfiguration = activity.getResources().getConfiguration();
        /*获取屏幕方向*/
        int ori = mConfiguration.orientation;
        /*横屏*/
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            setOrientation(activity, HEIGHT);
        }
        /*竖屏*/
        else if (ori == Configuration.ORIENTATION_PORTRAIT) {
            setOrientation(activity, WIDTH);
        }
    }

    /**
     * 此方法用于在某一个Activity里面更改适配的方向
     * 在setContentView()之前设置
     *
     * @param activity
     * @param orientation
     */
    public static void setOrientation(Activity activity, String orientation) {
        setAppOrientation(activity, orientation);
    }

    /**
     * targetDensity
     * targetScaledDensity
     * targetDensityDpi
     * 这三个参数是统一修改过后的值
     * orientation:方向值,传入width或height
     */
    private static void setAppOrientation(@Nullable Activity activity, String orientation) {

        float targetDensity;

        if (orientation.equals("height")) {
            /*设计图的高度 单位:dp*/
            targetDensity = (appDisplayMetrics.heightPixels - barHeight) / 640f;
        } else {
            /*设计图的宽度 单位:dp*/
            targetDensity = appDisplayMetrics.widthPixels / 360f;
        }

        float targetScaledDensity = targetDensity * (appScaledDensity / appDensity);
        int targetDensityDpi = (int) (160 * targetDensity);

        /**
         *
         * 最后在这里将修改过后的值赋给系统参数
         * 只修改Activity的density值
         */
        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
