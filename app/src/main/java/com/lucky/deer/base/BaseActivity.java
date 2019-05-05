package com.lucky.deer.base;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocation;
import com.dykj.requestcore.util.PermissionsUtils;
import com.dykj.requestcore.util.RequestUtils;
import com.hjq.toast.ToastUtils;
import com.lucky.deer.find.business.view.util.AppUtil;
import com.lucky.mapfeatures.positioning.MapLocationListener;
import com.lucky.mapfeatures.positioning.PositioningFeatures;
import com.lucky.deer.MyApplication;
import com.lucky.deer.R;
import com.lucky.deer.common.WebViewActivity;
import com.lucky.deer.common.dialog.DataperfectiDialog;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.execute.RequestInternet;
import com.lucky.deer.find.withdraw.SubmitFindWithdrawalActivity;
import com.lucky.deer.home.MainActivity;
import com.lucky.deer.login.LoginActivity;
import com.lucky.deer.util.DateUtil;
import com.lucky.deer.util.MyCountDownTimer;
import com.lucky.deer.util.onCountDownTimerListener;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.model.request.userinfo.MineInfoReq;
import com.lucky.model.response.userinfo.UserInfo;
import com.lucky.model.util.DensityUtils;
import com.lucky.model.util.HawkUtil;
import com.lucky.model.util.HintUtil;
import com.lucky.umengfunction.features.StatisticalFunction;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import rx.Observable;

/**
 * 基础
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    /**
     * 分页，页数
     */
    protected int pageCurrent = 1;
    /**
     * 全局上下文
     */
    protected Context mContext;
    protected Activity mActivity;
    /**
     * 传递参数key
     */
    protected String mEntity = HttpConstant.ENTITY;
    /**
     * 网络请求
     */
    protected RequestInternet mNetworkRequestInstance;
    /**
     * 倒计时控件
     */
    protected MyCountDownTimer mCountDownTimer;
    /**
     * 获取权限
     */
    protected PermissionsUtils mPermissions;
    private QMUITipDialog mDialo;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = MyApplication.context;
        if (initLayout() > 0) {
            /*初始化屏幕适配*/
            ARouter.getInstance().inject(this);
            DensityUtils.setConfiguration(this);
            setContentView(initLayout());
            ButterKnife.bind(this);
            getSavedInstanceState(savedInstanceState);
            mContext = this;
            mActivity = isChild() ? getParent() : this;
            mNetworkRequestInstance = RequestInternet.getInstance();
            mPermissions = PermissionsUtils.getInstance(this);
            if (isImmersive()) {
                // 沉浸式状态栏
                QMUIStatusBarHelper.translucent(this);
            }
            if (isStatusBarFontWhite()) {
                /*设置状态栏字体颜色为黑色*/
                QMUIStatusBarHelper.setStatusBarLightMode(this);
            } else {
                /*设置状态栏字体颜色为白色*/
                QMUIStatusBarHelper.setStatusBarDarkMode(this);
            }
            initView();
            initData();
        }
    }

    /**
     * 获取保存的实例状态
     *
     * @param savedInstanceState
     */
    protected void getSavedInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mContext = this;
        mActivity = isChild() ? getParent() : this;
        StatisticalFunction.getInstance(mContext).onResume();
        initListener();
    }


    @Override
    protected void onPause() {
        super.onPause();
        StatisticalFunction.getInstance(mContext).onPause();
    }

    /**
     * 设置标题功能(默认启用返回)
     *
     * @param topBarLayout 标题组建
     * @param titleID      标题名称
     */
    protected void initTopBar(QMUITopBar topBarLayout, int titleID) {
        initTopBar(topBarLayout, getString(titleID));
    }

    /**
     * 设置标题功能
     *
     * @param topBarLayout 标题组建
     * @param title        标题名称
     */
    protected void initTopBar(QMUITopBar topBarLayout, String title) {
        initTopBar(topBarLayout, false, title, 0, 0, 0, null);
    }

    /**
     * 设置标题功能
     *
     * @param topBarLayout    标题组建
     * @param isUpEnabledBack 是否显示
     * @param titleID         标题名称
     */
    protected void initTopBar(QMUITopBar topBarLayout, boolean isUpEnabledBack, int titleID) {
        initTopBar(topBarLayout, isUpEnabledBack, getString(titleID), 0, 0, 0, null);
    }

    /**
     * 设置标题功能
     *
     * @param topBarLayout    标题组建
     * @param isUpEnabledBack 是否显示
     * @param title           标题名称
     */
    protected void initTopBar(QMUITopBar topBarLayout, boolean isUpEnabledBack, String title) {
        initTopBar(topBarLayout, isUpEnabledBack, title, 0, 0, 0, null);
    }


    /**
     * 设置标题功能
     *
     * @param topBarLayout 标题组建
     * @param titleID      标题名称
     */
    protected void initTopBar(QMUITopBar topBarLayout, int titleID, int drawableResId, View.OnClickListener listener) {
        initTopBar(topBarLayout, false, getString(titleID), drawableResId, 0, 0, listener);
    }

    /**
     * 设置标题功能
     *
     * @param topBarLayout 标题组建
     * @param titleID      标题名称
     */
    protected void initTopBar(QMUITopBar topBarLayout, int titleID, int textId, int colorId, View.OnClickListener listener) {
        initTopBar(topBarLayout, false, getString(titleID), 0, textId, colorId, listener);
    }

    /**
     * 设置标题功能
     *
     * @param topBarLayout 标题组建
     * @param title        标题名称
     */
    protected void initTopBar(QMUITopBar topBarLayout, String title, int drawableResId, View.OnClickListener listener) {
        initTopBar(topBarLayout, false, title, drawableResId, 0, 0, listener);
    }

    /**
     * 设置标题功能
     *
     * @param topBarLayout    标题组建
     * @param isUpEnabledBack 是否显示
     * @param titleId         标题名称
     */
    protected void initTopBar(QMUITopBar topBarLayout, boolean isUpEnabledBack, int titleId, int drawableResId, View.OnClickListener listener) {
        initTopBar(topBarLayout, isUpEnabledBack, getString(titleId), drawableResId, 0, 0, listener);
    }

    /**
     * 设置标题功能
     *
     * @param topBarLayout    标题组建
     * @param isUpEnabledBack 是否显示
     * @param title           标题名称
     */
    protected void initTopBar(QMUITopBar topBarLayout, boolean isUpEnabledBack, String title, int drawableResId, int textId, int colorId, View.OnClickListener listener) {
        if (topBarLayout != null) {
            //设置标题，默认是显示在中间
            topBarLayout
                    .setTitle(title)
                    .setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
            //设置背景颜色
            topBarLayout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
            if (drawableResId > 0) {
                topBarLayout.addRightImageButton(drawableResId, 0)
                        .setOnClickListener(listener);
            }
            if (textId > 0 && colorId > 0) {
                topBarLayout.addRightTextButton(textId, 0)
                        .setOnClickListener(listener);
            }
            if (isUpEnabledBack) {
                return;
            }
            //左边添加返回按钮
            topBarLayout
                    .addLeftImageButton(R.mipmap.back, 0)
                    .setOnClickListener(v ->
                            overridePendingTransition(false, true)
                    );
        }
    }


    /**
     * activity跳转
     *
     * @param activity
     * @param clazz
     * @param entity
     * @param finishSelf
     */
    protected <T extends Serializable> void jumpActivity(Context activity, Class<?> clazz, T entity, boolean finishSelf, int... flags) {
        if (isRepeatedClicks()) {
            Intent intent = new Intent(activity, clazz);
            if (null != entity) {
                intent.putExtra(mEntity, entity);
            }
            if (null != flags && flags.length > 0) {
                for (int flag : flags) {
                    intent.addFlags(flag);
                }
            }
            activity.startActivity(intent);

            overridePendingTransition(true, finishSelf);
        }
    }

    /**
     * activity跳转
     *
     * @param activity
     * @param clazz
     * @param finishSelf
     * @param flags
     */
    protected void jumpActivity(Context activity, Class<?> clazz, boolean finishSelf, int... flags) {
        jumpActivity(activity, clazz, null, finishSelf, flags);
    }

    /**
     * activity跳转
     *
     * @param activity
     * @param clazz
     * @param finishSelf
     */
    protected void jumpActivity(Context activity, Class<?> clazz, boolean finishSelf) {
        jumpActivity(activity, clazz, null, finishSelf);
    }

    /**
     * 默认不关闭当前的activity
     *
     * @param activity
     * @param clazz
     */
    protected void jumpActivity(Context activity, Class<?> clazz) {
        jumpActivity(activity, clazz, false);
    }

    /**
     * 默认不关闭当前的activity
     *
     * @param activity
     * @param clazz
     * @param entity
     * @param <T>
     */
    protected <T extends Serializable> void jumpActivity(Context activity, Class<?> clazz, T entity) {
        jumpActivity(activity, clazz, entity, false);
    }

    /**
     * 默认不关闭当前的activity
     *
     * @param activity
     * @param clazz
     * @param bundle
     * @param <T>
     */
    protected <T extends Bundle> void jumpBundleActivity(Context activity, Class<?> clazz, T bundle) {
        jumpBundleActivity(activity, clazz, bundle, false);
    }

    /**
     * 默认不关闭当前的activity
     *
     * @param activity
     * @param clazz
     * @param finishSelf
     * @param <T>
     */
    protected <T extends Bundle> void jumpBundleActivity(Context activity, Class<?> clazz, boolean finishSelf) {
        jumpBundleActivity(activity, clazz, new Bundle(), finishSelf);
    }

    /**
     * activity跳转
     *
     * @param activity
     * @param clazz
     * @param entity
     * @param finishSelf
     */
    protected <T extends Bundle> void jumpBundleActivity(Context activity, Class<?> clazz, T entity, boolean finishSelf) {
        if (isRepeatedClicks()) {
            Intent intent = new Intent(activity, clazz);
            if (null != entity) {
                intent.putExtra(mEntity, entity);
            }
            activity.startActivity(intent);
//        if (finishSelf) {
//            activity.finish();
//        }
            overridePendingTransition(true, finishSelf);
        }
    }

    /**
     * jumpActivityForResult
     *
     * @param activity
     * @param clazz
     * @param requestCode
     * @param entity
     */
    protected void jumpActivityForResult2(Activity activity, Class<?> clazz, int requestCode, Bundle entity) {
        if (isRepeatedClicks()) {
            Intent intent = new Intent(activity, clazz);
            if (null != entity) {
                intent.putExtra(mEntity, entity);
            }
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * jumpActivityForResult
     *
     * @param activity
     * @param clazz
     * @param requestCode
     * @param entity
     * @param <T>
     */
    protected <T extends Serializable> void jumpActivityForResult(Activity activity, Class<?> clazz, int requestCode, T entity) {
        if (isRepeatedClicks()) {
            Intent intent = new Intent(activity, clazz);
            if (null != entity) {
                intent.putExtra(mEntity, entity);
            }
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * jumpActivityForResult
     *
     * @param activity
     * @param clazz
     * @param requestCode
     */
    protected void jumpActivityForResult(Activity activity, Class<?> clazz, int requestCode) {
        jumpActivityForResult(activity, clazz, requestCode, null);
    }

    /**
     * 获取页面传值
     *
     * @return
     */
    protected int getIntData() {
        return getIntent() == null ? null : getIntent().getIntExtra(mEntity, 0);
    }

    /**
     * 获取页面传值
     *
     * @return
     */
    protected String getStringData() {
        return getIntent() == null ? null : getIntent().getStringExtra(mEntity);
    }

    /**
     * 获取页面传值
     *
     * @return 数据
     */
    protected Bundle getBundleData() {
        return getIntent() == null ? null : getIntent().getBundleExtra(mEntity);
    }

    /**
     * 获取页面传值
     *
     * @return
     */
    protected Serializable getSerializableData() {
        return getIntent() == null ? null : getIntent().getSerializableExtra(mEntity);
    }

    /**
     * 设置是否沉浸状态栏
     *
     * @return
     */
    @Override
    public boolean isImmersive() {
        return true;
    }

    /**
     * 设置状态栏颜色
     */
    public boolean isStatusBarFontWhite() {
        return true;
    }

    /**
     * 获取页面
     */
    protected abstract int initLayout();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 初始化监听
     */
    protected void initListener() {

    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 必填验证
     */
    protected boolean examineRequiredVerification() {
        startRequestInterface();
        return true;
    }

    /**
     * 请求接口
     */
    protected void startRequestInterface() {

    }


    /**
     * 数据加载弹出框
     */
    public void showLoadingDialog() {
        showLoadingDialog(getString(R.string.data_loading_massage), QMUITipDialog.Builder.ICON_TYPE_LOADING);
    }


    /**
     * 数据加载弹出框
     *
     * @param messageId 设置自定义文字
     */
    public void showLoadingDialog(int messageId) {
        showLoadingDialog(getString(messageId), QMUITipDialog.Builder.ICON_TYPE_LOADING);
    }

    /**
     * 数据成功弹出框
     *
     * @param messageId 显示内容
     */
    public <T> void showSuccessDialog(int messageId) {
        showSuccessDialog(messageId, null);
    }

    /**
     * 数据成功弹出框
     *
     * @param messageId 显示内容
     */
    public <T> void showSuccessDialog(int messageId, T executionParam) {
        showSuccessDialog(3, messageId, executionParam);
    }

    /**
     * 数据成功弹出框
     *
     * @param seconds   设置时间
     * @param messageId 显示内容
     */
    public void showSuccessDialog(int seconds, int messageId) {
        showSuccessDialog(seconds, messageId, null);
    }

    /**
     * 数据成功弹出框并执行方法
     *
     * @param seconds        设置时间
     * @param messageId      显示内容
     * @param executionParam 执行的信息
     */
    public <T> void showSuccessDialog(int seconds, int messageId, T executionParam) {
        showLoadingDialog(getString(messageId), QMUITipDialog.Builder.ICON_TYPE_SUCCESS);
        countdownCarriedOut(seconds, true, executionParam);
    }

    /**
     * 数据成功弹出框
     *
     * @param messageId 显示内容
     */
    public <T> void showFailedDialog(int messageId) {
        showFailedDialog(messageId, null);
    }

    /**
     * 数据成功弹出框
     *
     * @param messageId 显示内容
     */
    public <T> void showFailedDialog(int messageId, T executionParam) {
        showFailedDialog(3, messageId, executionParam);
    }

    /**
     * 数据失败弹出框
     *
     * @param seconds   设置时间
     * @param messageId 显示内容
     */
    public void showFailedDialog(int seconds, int messageId) {
        showFailedDialog(seconds, messageId, null);
    }

    /**
     * 数据失败弹出框并执行方法
     *
     * @param seconds        设置时间
     * @param messageId      显示内容
     * @param executionParam 执行的信息
     */
    public <T> void showFailedDialog(int seconds, int messageId, T executionParam) {
        showLoadingDialog(getString(messageId), QMUITipDialog.Builder.ICON_TYPE_FAIL);
        countdownCarriedOut(seconds, executionParam);
    }

    /**
     * 加载弹出框
     *
     * @param message
     */
    public void showLoadingDialog(String message, int iconType) {
        if (mDialo != null && mDialo.isShowing()) {
            mDialo.dismiss();
        }
        mDialo = new QMUITipDialog.Builder(mActivity)
                .setIconType(iconType)
                .setTipWord(message)
                .create(false);
        mDialo.show();
    }

    /**
     * 关闭加载框
     */
    public void dismissLoadingDialog() {
        if (mDialo != null && mDialo.isShowing()) {
            mDialo.dismiss();
        }
    }

    /**
     * 倒计时执行功能(默认不做人任何操作)
     *
     * @param seconds 设置时间
     */
    public void countdownCarriedOut(int seconds) {
        countdownCarriedOut(seconds, null);
    }

    /**
     * 倒计时执行功能(默认不做人任何操作)
     *
     * @param seconds 设置时间
     */
    public <T> void countdownCarriedOut(int seconds, T executionParam) {
        countdownCarriedOut(seconds, false, executionParam);
    }

    /**
     * 倒计时执行功能
     *
     * @param seconds     设置时间
     * @param isOperating 是否做操作
     */
    public <T> void countdownCarriedOut(int seconds, boolean isOperating, T executionParam) {
        Observable.timer(seconds, TimeUnit.SECONDS)
                .subscribe(aLong -> {
                    if (isOperating) {
                        if (executionParam == null) {
                            carriedOutMethod();
                        } else {
                            carriedOutMethod(executionParam);
                        }
                    }
                    dismissLoadingDialog();
                });
    }

    /**
     * 设置要执行的信息
     *
     * @param executionParam 参数
     */
    public <T> void carriedOutMethod(T executionParam) {
    }

    /**
     * 设置要执行的信息
     */
    public void carriedOutMethod() {
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isBack()) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (mActivity instanceof MainActivity && event.getRepeatCount() == 0) {
                    // 判断间隔时间 小于2秒就退出应用
                    if ((System.currentTimeMillis() - exitTime) > 2000) {
                        HintUtil.showErrorWithToast(mContext, "再按一次返回键退出");
                        /*计算两次返回键按下的时间差*/
                        exitTime = System.currentTimeMillis();
                    } else {
                        Intent home = new Intent(Intent.ACTION_MAIN);
                        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        home.addCategory(Intent.CATEGORY_HOME);
                        startActivity(home);
                        // 关闭应用程序
                        System.exit(0);
                    }
                } else if (mActivity instanceof LoginActivity) {
                    MainActivity.messageCallback.onMessage(1);
                    finish();
                } else if (mActivity instanceof WebViewActivity && 1 == HawkUtil.getInstance().getSaveData(KeyConstant.IS_ADVERTISING, 0)) {
                    jumpActivity(mActivity, MainActivity.class, true);
                } else {
                    overridePendingTransition(false, true);
                }
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }
        return false;
    }

    /**
     * 是否可以返回
     *
     * @return
     */
    private boolean isBack() {
        if (mActivity instanceof SubmitFindWithdrawalActivity || mContext instanceof SubmitFindWithdrawalActivity) {
            return false;
        }
        return true;
    }

    /**
     * 获取数据本地数据
     *
     * @param id
     * @return
     */
    public String[] getdata(int id) {
        return getResources().getStringArray(id);
    }

    /**
     * 获取用户细信息
     *
     * @return
     */
    public UserInfo getUserInfo() {
        return HawkUtil.getInstance().getSaveData(HawkUtil.USER_INFO);
    }

    /**
     * 获取登录状态
     *
     * @return 登录状态值
     */
    public boolean getLoginStatus() {
        return getUserInfo() != null && "1".equals(getUserInfo().getLoginStatus());
    }

    /**
     * 未登录跳转弹窗
     */
    public void obtainLoginStatus() {
        obtainLoginStatus(null);
    }

    /**
     * 未登录跳转弹窗
     */
    public void obtainLoginStatus(OnClickListener listener) {
        if (!getLoginStatus()) {
            /*获取单利*/
            PublicDialog inistanceView = PublicDialog.getInstance();
            QMUIDialog qmuiDialog = inistanceView.setCustomizeView(
                    inistanceView.initTitleEtOrTvView(
                            mContext, getString(R.string.dialog_prompt), getString(R.string.please_login),
                            true),
                    KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_4);
            inistanceView.setOnClickListener((OnClickListener<String>) (status, useType, isPhoneNumber, text) -> {
                if (listener == null) {
                    switch (status) {
                        case OK:
                            jumpActivity(mActivity, LoginActivity.class);
                            break;
                        default:
                    }
                    qmuiDialog.dismiss();
                } else {
                    listener.onClick(status, KeyConstant.NO_TYPE, isPhoneNumber, text);
                    qmuiDialog.dismiss();
                }
            });
        }
    }

    /**
     * 初始化倒计时
     *
     * @param code 显示秒数控件
     */
    public void initCountDownTimer(TextView code) {
        /*初始化倒计时控件*/
        mCountDownTimer = MyCountDownTimer
                .getInstance()
                .initCountDownTimer(60000,
                        new onCountDownTimerListener() {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                code.setText(millisUntilFinished + "s 可以重新获取");
                            }

                            @Override
                            public void onFinish() {
                                mCountDownTimer.cancel();
                                code.setText(R.string.resend_code);
                                code.setEnabled(true);
                            }
                        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    /**
     * 获取注册进件状态
     */
    @SuppressLint("CheckResult")
    public void getRegistrationStatus() {
        mNetworkRequestInstance.queryRegisterState()
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        if (HawkUtil.getInstance().isContains(HawkUtil.USER_INFO)) {
                            UserInfo userInfo = getUserInfo();
                            if (returnData.getData() != null) {
                                userInfo.setRegisterState(Integer.parseInt(returnData.getData().getRegisterState()));
                                userInfo.setIsOpenDateRepayment(returnData.getData().getIsOpenDateRepayment());
                                HawkUtil.getInstance().saveData(HawkUtil.USER_INFO, userInfo);
                            }
                        }
                    }/* else {
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                    }*/
                });
    }

    /**
     * 根据状态跳转页面
     *
     * @return
     */
    public boolean getRegisterState(int state) {
        switch (state) {
            case 1:
                //资料完善页面
                new DataperfectiDialog(mContext, 1, "身份信息完善", R.style.UpdateAppDialog).show();
                return false;
            case 2:
                new DataperfectiDialog(mContext, 2, "储蓄卡信息完善", R.style.UpdateAppDialog).show();
                return false;
            case 3:
                new DataperfectiDialog(mContext, 3, "手持信息完善", R.style.UpdateAppDialog).show();
                return false;
            case 4:
                HintUtil.showErrorWithToast(mContext, R.string.hint_unfilled_customer_service);
                return false;
            case 5:
                return true;
            case 6:
                HintUtil.showErrorWithToast(mContext, R.string.hint_incoming_failure_customer_service);
                return false;
            default:
                return false;
        }
    }

    /**
     * 是否开启循环点位
     *
     * @param context
     * @param isStartStatus 是否开启间隔定位
     *                      true：开启
     *                      false：不开启
     */
    public static void isOpenIntervalPositioning(Context context, boolean isStartStatus) {
        isOpenIntervalPositioning(context, isStartStatus, 1, null);
    }

    /**
     * 是否开启循环点位
     *
     * @param context
     * @param isStartStatus 是否开启间隔定位
     *                      true：开启
     *                      false：不开启
     * @param scenesUsed    定位场景
     *                      1：业务员定位
     *                      2：定位回掉
     */
    public static void isOpenIntervalPositioning(Context context, boolean isStartStatus, int scenesUsed, final MapLocationListener listener) {
        PositioningFeatures instance = PositioningFeatures.getInstance(context);
        instance.setScenesUsed(scenesUsed);
        instance.setOnceLocation(!isStartStatus);
        if (isStartStatus) {
            /*设置定位间隔（6*60*60=6小时）*/
            instance.setInterval(6 * 60 * 60);
        } else {
            PositioningFeatures.getInstance(context).stopLocation();
        }
//        if (listener == null && !isStartStatus) {
//            PositioningFeatures.getInstance(context).stopLocation();
//        }
        instance.create()
                .setLocationListener(new MapLocationListener() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void onLocationSuccess(AMapLocation amapLocation, int scenesUsed) {
                        if (scenesUsed == 1) {
                            MineInfoReq mineInfoReq = new MineInfoReq();
                            if (isStartStatus) {
                                mineInfoReq.setReceiptFlag("1");
                            } else {
                                mineInfoReq.setReceiptFlag("2");
                            }
                            if (amapLocation != null) {
                                mineInfoReq.setRealTimePosition(amapLocation.getLongitude() + "," + amapLocation.getLatitude());
                                RequestInternet.getInstance().openOrOffReceipt(mineInfoReq)
                                        .subscribe(stringResponseData -> {
                                            if (RequestUtils.isRequestSuccess(stringResponseData)) {
                                                if (HawkUtil.getInstance().isContains(HawkUtil.USER_INFO)) {
                                                    /*判断是否开始接单*/
                                                    UserInfo userInfo = HawkUtil.getInstance().getSaveData(HawkUtil.USER_INFO);
                                                    /*设置接单状态并保存*/
                                                    userInfo.setStartOrdering(isStartStatus);
                                                    HawkUtil.getInstance().saveData(HawkUtil.USER_INFO, userInfo);
                                                }
                                            }
                                        });
                            }
                        } else if (listener != null) {
                            listener.onLocationSuccess(amapLocation, scenesUsed);
                        }
                    }

                    @Override
                    public void onLocationFailure() {
                        if (scenesUsed >= 2 && listener != null) {
                            listener.onLocationFailure();
                        } else {
                            PositioningFeatures.getInstance(context).stopLocation();
                        }
                    }
                }).startLocation();
    }

    /**
     * 防止按钮重复点击
     */
    public boolean isRepeatedClicks() {
        return DateUtil.isRepeatedClicks();
    }


    /**
     * 退出页面
     *
     * @param isOutOrEnter 是否是跳转页面或推出页面
     *                     true：跳转页面；false：退出页面
     * @param isFinish     是否删除页面
     */
    protected void overridePendingTransition(boolean isOutOrEnter, boolean isFinish) {
        if (isFinish) {
            finish();
        }
//        if (isOutOrEnter) {
//            /*跳转页面*/
//            overridePendingTransition(R.anim.activity_top_to_bottom, R.anim.activity_bottom_to_top);
//        } else {
//            /*退出页面*/
//            overridePendingTransition(R.anim.activity_bottom_to_top, R.anim.activity_top_to_bottom);
//
//        }
//
    }

    public void toast(String s){
        ToastUtils.show(s);
    }

    /**
     * 点击空白处或滑动时候隐藏软键盘
     * parent里面下如果有scrollview占据整个画面的话，必须resLayout=scrollview_resid，不然监听不到
     * */
    protected void hideParentSoftKeyborad(int resLayout){
        //以前的：点空白处隐藏软键盘

        findViewById(resLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        AppUtil.hideSoftKeyboard(mActivity);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        AppUtil.hideSoftKeyboard(mActivity);
                        break;
                }
                return false;
            }
        });
    }
}
