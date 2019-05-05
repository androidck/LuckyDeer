package com.lucky.deer;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.multidex.BuildConfig;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.demo.cloudstorage.CloudstorageKeyConstant;
import com.demo.cloudstorage.qiniuyun.InitFileExecutor;
import com.dykj.requestcore.retrofit.RetrofitExecutor;
import com.hjq.toast.ToastUtils;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.recognition.Package.identify.DocumentIdentify;
import com.lucky.model.util.DensityUtils;
import com.lucky.model.util.DeviceInfoUtils;
import com.lucky.model.util.HawkUtil;
import com.lucky.umengfunction.UMengConfigure;
import com.lucky.umengfunction.constant.UMengConstant;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.NoEncryption;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import cn.jpush.android.api.JPushInterface;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * @author wangxiangyi
 * @date 2018/09/17
 */
@SuppressLint("Registered")
public class MyApplication extends Application {

    public static Context context;

    public boolean isDebug=true;

    @Override
    public void onCreate() {
        super.onCreate();
        // 调试时，将第三个参数改为true
        if (isDebug){
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(MyApplication.this);
        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true;
        // 设置是否自动下载补丁
        Beta.canAutoDownloadPatch = true;
        // 设置是否提示用户重启
        Beta.canNotifyUserRestart = true;
        // 设置是否自动合成补丁
        Beta.canAutoPatch = true;

        Bugly.init(this, "c1d741ba69", false);

        context = getApplicationContext();
        DensityUtils.setDensity(this);

        ToastUtils.init(this);


        /*初始化热更新*/

        /*初始化log*/
        Logger.init(getString(R.string.app_name))
                .hideThreadInfo()
                .methodCount(2)
                .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);
        /*初始化hawk*/
        Hawk.init(context)
                .setEncryption(new NoEncryption())
                .build();
        /*网络框架初始化*/
        RetrofitExecutor.onCreate(context, HttpConstant.BASE_URL, BuildConfig.DEBUG);
        /*获取系统信息初始化*/
        DeviceInfoUtils.onCreate(context);
        /*文字识别初始化*/
        DocumentIdentify.onCreate(context, KeyConstant.API_KEY, KeyConstant.SECRET_KEY, BuildConfig.DEBUG);
        /*初始化七牛存储*/
        InitFileExecutor.onCreate(getApplicationContext(), CloudstorageKeyConstant.ACCESSKEY, CloudstorageKeyConstant.SECRETKEY, BuildConfig.DEBUG);
        /*初始化二维码工具类*/
        ZXingLibrary.initDisplayOpinion(context);
        /*极光推送初始化*/
        JPushInterface.setDebugMode(true);
        JPushInterface.init(context);
        /*保存推送id*/
        if (!TextUtils.isEmpty(JPushInterface.getRegistrationID(context))) {
            HawkUtil.getInstance().saveData(HawkUtil.REGISTRATION_ID, JPushInterface.getRegistrationID(context));
        }
        if (!BuildConfig.DEBUG) {
            /*友盟初始化*/
            UMengConfigure.init(context, UMengConstant.uMengAppKey, getString(R.string.app_name), UMengConstant.pushKey, BuildConfig.DEBUG);
        }

    }


    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorBg, R.color.colorDeep);//全局设置主题颜色
                return new ClassicsHeader(context);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1) {
            /*非默认值*/
            getResources();
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        /*非默认值*/
        if (res.getConfiguration().fontScale != 1) {
            Configuration newConfig = new Configuration();
            /*设置默认*/
            newConfig.setToDefaults();
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    /**
     * 分割 Dex 支持
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

        // 安装tinker
        Beta.installTinker();
    }


}
