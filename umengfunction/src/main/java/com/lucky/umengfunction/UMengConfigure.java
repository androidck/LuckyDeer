package com.lucky.umengfunction;

import android.content.Context;

import com.umeng.commonsdk.UMConfigure;

/**
 * 友盟的配置
 *
 * @author wangxiangyi
 * @date 2018/12/7
 */
public class UMengConfigure {
    /**
     * 友盟的初始化
     *
     * @param context          上下文
     * @param uMengAppKey      友盟的key
     * @param channel          渠道名称
     * @param pushKey          推送的key
     * @param isLogEnabled     设置组件化的Log开关 默认为false，如需查看LOG设置为true
     */
    public static void init(Context context, String uMengAppKey, String channel, String pushKey, boolean isLogEnabled) {
        /**
         * 注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，
         * 也需要在App代码中调用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，
         * UMConfigure.init调用中appkey和channel参数请置为null）
         */
        UMConfigure.init(context, uMengAppKey, channel, UMConfigure.DEVICE_TYPE_PHONE, pushKey);
        /*设置组件化的Log开关*/
        UMConfigure.setLogEnabled(isLogEnabled);
        /*设置日志加密 默认为false（不加密）*/
        UMConfigure.setEncryptEnabled(!isLogEnabled);

    }
}
