package com.lucky.deer.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dykj.requestcore.util.RequestUtils;
import com.hjq.toast.ToastUtils;
import com.lucky.deer.R;
import com.lucky.deer.common.WebViewActivity;
import com.lucky.deer.common.dialog.DataperfectiDialog;
import com.lucky.deer.common.web.BrowserActivity;
import com.lucky.deer.common.web.SonicJavaScriptInterface;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.execute.RequestInternet;
import com.lucky.deer.login.LoginActivity;
import com.lucky.deer.mine.perfectuserInfo.AuthenticationActivity;
import com.lucky.deer.mine.perfectuserInfo.BankCardActivity;
import com.lucky.deer.mine.perfectuserInfo.HeldIdentityActivity;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.model.common.WebViewBean;
import com.lucky.model.response.userinfo.UserInfo;
import com.lucky.model.util.HawkUtil;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;

/**
 * 基础Fragment
 *
 * @author wangxiangyi
 * @date 2018/09/19
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 分页，页数
     */
    protected int pageCurrent = 1;
    /**
     * 传递参数key
     */
    protected String mEntity = HttpConstant.ENTITY;
    private Unbinder unbinder;
    protected FragmentActivity mActivity;
    /**
     * 网络请求
     */
    protected RequestInternet mNetworkRequestInstance;
    private QMUITipDialog mDialo;
    /**
     * Fragment的View加载完毕的标记
     */
    protected boolean isViewCreated = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(initlayout(), container, false);
        unbinder = ButterKnife.bind(this, inflate);
        if (isImmersive()) {
            // 沉浸式状态栏
            QMUIStatusBarHelper.translucent(getActivity());
        }
        mActivity = getActivity();
        mNetworkRequestInstance = RequestInternet.getInstance();
       if (isStatusBarFontWhite()) {
            //*设置状态栏字体颜色为黑色*//*
            QMUIStatusBarHelper.setStatusBarLightMode(mActivity);
        } else {
            //*设置状态栏字体颜色为白色*//*
            QMUIStatusBarHelper.setStatusBarDarkMode(mActivity);
        }
        initView();
        initData();
        /*视图创建完成，将变量置为true*/
        isViewCreated = true;
        /*如果Fragment可见进行数据加载*/
        if (getUserVisibleHint()) {
            onlazyLoadData();
        }
        return inflate;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        /*视图变为可见并且是第一次加载*/
        if (isViewCreated && isVisibleToUser) {
            onlazyLoadData();
        }
    }

    /**
     * 页面可见,加载数据
     */
    protected void onlazyLoadData() {
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity = getActivity();
        mNetworkRequestInstance = RequestInternet.getInstance();
        initListener();
    }


    /**
     * 初始化布局
     */
    protected abstract int initlayout();

    /**
     * 初始化控件
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
     * 设置状态栏颜色
     */
    public boolean isStatusBarFontWhite() {
        return true;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        /*视图销毁将变量置为false*/
        isViewCreated = false;
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
        initTopBar(topBarLayout, title, 0, null);
    }

    /**
     * 设置标题功能
     *
     * @param topBarLayout 标题组建
     * @param titleId      标题名称
     */
    protected void initTopBar(QMUITopBar topBarLayout, int titleId, int drawableResId, View.
            OnClickListener listener) {
        initTopBar(topBarLayout, getString(titleId), drawableResId, listener);
    }

    /**
     * 设置标题功能
     *
     * @param topBarLayout 标题组建
     * @param title        标题名称
     */
    protected void initTopBar(QMUITopBar topBarLayout, String title, int drawableResId, View.
            OnClickListener listener) {
        if (topBarLayout != null) {
            //设置标题，默认是显示在中间
            topBarLayout
                    .setTitle(title)
                    .setTextColor(ContextCompat.getColor(mActivity, android.R.color.black));
            //设置背景颜色
            topBarLayout.setBackgroundColor(ContextCompat.getColor(mActivity, android.R.color.white));
            if (drawableResId > 0) {
                topBarLayout.addRightImageButton(drawableResId, 0)
                        .setOnClickListener(listener);
            }
        }
    }

    public boolean isImmersive() {
        return true;
    }

    public void jumpFragment(int containerViewId, Fragment fragment) {
        //压栈式跳转
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, null)
                .addToBackStack(null)
                .commit();

    }

    public void jumpFragment1(int containerViewId, Fragment fragment) {
        /*非压栈式跳转*/
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, null)
                .commit();
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
     * 加载弹出框
     */
    public void showLoadingDialog() {
        showLoadingDialog(R.string.data_loading_massage);
    }

    /**
     * 加载弹出框
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
        showSuccessDialog(getString(messageId));
    }

    /**
     * 数据成功弹出框
     *
     * @param message 显示内容
     */
    public <T> void showSuccessDialog(String message) {
        showLoadingDialog(message, QMUITipDialog.Builder.ICON_TYPE_SUCCESS);
        countdownCarriedOut(1);
    }

    /**
     * 数据失败弹出框
     *
     * @param messageId 显示内容
     */
    public void showFailedDialog(int messageId) {
        showFailedDialog(getString(messageId));
    }

    /**
     * 数据失败弹出框
     *
     * @param message 显示内容
     */
    public void showFailedDialog(String message) {
        showLoadingDialog(message, QMUITipDialog.Builder.ICON_TYPE_FAIL);
        countdownCarriedOut(1);
    }

    /**
     * 加载弹出框
     *
     * @param message
     */
    public void showLoadingDialog(String message, int type) {
        if (mDialo != null && mDialo.isShowing()) {
            mDialo.dismiss();
        }
        mDialo = new QMUITipDialog.Builder(getActivity())
                .setIconType(type)
                .setTipWord(message)
                .create(false);
        mDialo.show();
    }

    /**
     * 倒计时执行功能倒计时功能
     *
     * @param seconds 设置时间（单位为毫秒）
     */
    public Observable countdownCarriedOut1(long seconds) {
        return Observable.timer(seconds, TimeUnit.MILLISECONDS);
    }

    /**
     * 倒计时执行功能关闭弹窗
     *
     * @param seconds 设置时间（单位为秒）
     */
    public <T> void countdownCarriedOut(int seconds) {
        Observable.timer(seconds, TimeUnit.SECONDS)
                .subscribe(aLong -> {
                    dismissLoadingDialog();
                });
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
     * 设置状态栏高度
     */
    public void setStatusBarHeight(View view) {
        if (view != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            /*4个参数按顺序分别是左上右下*/
            layoutParams.setMargins(0, QMUIStatusBarHelper.getStatusbarHeight(mActivity), 0, 0);
            view.setLayoutParams(layoutParams);
        }
    }

    /**
     * 设置状态栏高度
     */
    public void setStatusBarHeight(View view,int height) {
        if (view != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            /*4个参数按顺序分别是左上右下*/
            layoutParams.setMargins(0, height, 0, 0);
            view.setLayoutParams(layoutParams);
        }
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
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
                            mActivity, getString(R.string.dialog_prompt), getString(R.string.please_login),
                            true),
                    KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_4);
            inistanceView.setOnClickListener((OnClickListener<String>) (status, useType, isPhoneNumber, text) -> {
                if (listener == null) {
                    switch (status) {
                        case OK:
                            startLoginActivity(mActivity);
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
     * 跳转到登录页面
     *
     * @param context
     */
    public void startLoginActivity(Activity context) {
        startActivity(new Intent(context, LoginActivity.class));
    }

    /**
     * 跳转到资料完善页面
     *
     * @param context
     */
    public void startAuthenticationActivity(Activity context, Bundle bundle) {
        Intent intent = new Intent(context, AuthenticationActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 跳转到资料完善页面
     *
     * @param context
     */
    public void startHeldIdentityActivity(Activity context, Bundle bundle) {
        Intent intent = new Intent(context, HeldIdentityActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void toast(String s){
        ToastUtils.show(s);
    }

    /**
     * 跳转到资料完善页面
     *
     * @param context
     */
    public void startBankCardActivity(Activity context, Bundle bundle) {
        Intent intent = new Intent(context, BankCardActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 获取注册进件状态
     */
    @SuppressLint("CheckResult")
    public void getRegistrationStatus() {

        mNetworkRequestInstance.queryRegisterState()
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        if (HawkUtil.getInstance().isContains(HawkUtil.USER_INFO)) {
                            UserInfo userInfo = getUserInfo();
                            if (returnData.getData() != null) {
                                userInfo.setRegisterState(Integer.parseInt(returnData.getData().getRegisterState()));
                                userInfo.setIsOpenDateRepayment(returnData.getData().getIsOpenDateRepayment());
                                HawkUtil.getInstance().saveData(HawkUtil.USER_INFO, userInfo);
                            }else {

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
                new DataperfectiDialog(mActivity, 1, "身份信息完善", R.style.UpdateAppDialog).show();
                return false;
            case 2:
                new DataperfectiDialog(mActivity, 2, "储蓄卡信息完善", R.style.UpdateAppDialog).show();
                return false;
            case 3:
                new DataperfectiDialog(mActivity, 3, "手持信息完善", R.style.UpdateAppDialog).show();
                return false;
            case 4:
                HintUtil.showErrorWithToast(mActivity, R.string.hint_unfilled_customer_service);
                return false;
            case 5:
                return true;
            case 6:
                HintUtil.showErrorWithToast(mActivity, R.string.hint_incoming_failure_customer_service);
                return false;
            default:
                return false;
        }
    }

    /**
     * 跳转网页
     *
     * @param title 标题
     * @param url   网页地址
     */
    public void jumpWebView(String title, String url) {
        WebViewBean webViewBean = new WebViewBean();
        webViewBean.setWebTitle(title);
        webViewBean.setWebUrl(url);
        startActivity(new Intent(mActivity, WebViewActivity.class).putExtra(mEntity, webViewBean));
    }

    public int getStatusBar() {
        /**
         * 获取状态栏高度
         * */
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    //跳转到浏览器
    public void startBrowserActivity(Context context, int mode, String url, String title) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra(BrowserActivity.PARAM_URL, url);
        intent.putExtra(BrowserActivity.PARAM_MODE, mode);
        intent.putExtra(KeyConstant.DETAILS_TITLE,title);
        intent.putExtra(SonicJavaScriptInterface.PARAM_CLICK_TIME, System.currentTimeMillis());
        context.startActivity(intent);
    }
}
