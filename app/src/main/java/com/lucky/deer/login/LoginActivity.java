package com.lucky.deer.login;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.dykj.requestcore.util.cipher.MD5Utils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.home.MainActivity;
import com.lucky.deer.mine.perfectuserInfo.AuthenticationActivity;
import com.lucky.deer.mine.perfectuserInfo.BankCardActivity;
import com.lucky.deer.mine.perfectuserInfo.HeldIdentityActivity;
import com.lucky.deer.util.MyCountDownTimer;
import com.lucky.deer.util.RegexUtil;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.util.onCountDownTimerListener;
import com.lucky.model.request.login.LoginReq;
import com.lucky.model.response.userinfo.UserInfo;
import com.lucky.model.util.EnumCodeUse;
import com.lucky.model.util.HawkUtil;
import com.lucky.model.util.HintUtil;
import com.lucky.sharingfunction.qq.QqFeatures;
import com.lucky.sharingfunction.weixinshare.WeiXinFeatures;
import com.lucky.umengfunction.features.StatisticalFunction;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登陆页面
 *
 * @author wangxiangyi
 * @date 2018/09/19
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.et_account_number)
    EditText etAccountNumber;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_code_login)
    TextView tvCodeLogin;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register_now)
    TextView tvRegisterNow;
    private String codeId;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, true, R.string.title_activity_login,
                R.mipmap.login_close, v -> {
                    /*判断是否是登录状态*/
                    if (getLoginStatus()) {
                        /*登录状态*/
                        overridePendingTransition(false, true);
                    } else {
                        /*没有登录*/
                        //jumpActivity(mContext, MainActivity.class, true);
                        MainActivity.messageCallback.onMessage(1);
                        finish();
                    }
                });
        /*初始化倒计时控件*/
        initCountDownTimer(tvCode);
        /*自动添加空格*/
        StringUtil.setPhoneWatcher(etAccountNumber);
        /*光标监听*/
        etAccountNumber.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                /*获取焦点*/
            } else {
                /*失去焦点*/
                getUserIdByPhone(StringUtil.removeAllSpace(etAccountNumber.getText().toString().trim()));
            }
        });
        if (tvCodeLogin.getText().toString().trim().equals(getString(R.string.login_code))) {
            tvCode.setText(R.string.forget_password);
            tvCode.setEnabled(true);
        } else {
            tvCode.setText(R.string.get_verification_code);
            initCountDownTimer(tvCode);
            etAccountNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 11) {
                        if (!RegexUtil.isMobileExact(StringUtil.removeAllSpace(etAccountNumber.getText().toString().trim()))) {
                            HintUtil.showErrorWithToast(mContext, R.string.toast_please_ok_enter_phone_number);
                            return;
                        }
                        yanzengPhone(StringUtil.removeAllSpace(etAccountNumber.getText().toString().trim()));
                    }
                }
            });
        }
    }

    /**
     * 验证手机号是否注册
     *
     * @param phone 手机号
     */
    @SuppressLint("CheckResult")
    private void yanzengPhone(String phone) {
        LoginReq loginReq = new LoginReq();
        loginReq.setPhone(phone);
        mNetworkRequestInstance.getUserIdPhone(loginReq)
                .subscribe(stringResponseData -> {
                    if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        tvCode.setEnabled(true);
                    } else {
                        tvCode.setEnabled(false);
                        HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                    }
                });
    }


    @Override
    protected void initData() {
        UserInfo saveData = HawkUtil.getInstance().getSaveData(HawkUtil.USER_INFO);
        if (saveData != null) {
            etAccountNumber.setText(saveData.getLoginAccount());
            getUserIdByPhone(saveData.getLoginAccount().trim());
        }
    }

    @OnClick({R.id.tv_code, R.id.tv_code_login, R.id.tv_register_now,
            R.id.tv_login, R.id.tv_we_chat, R.id.tv_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*获取验证码或忘记密码*/
            case R.id.tv_code:
                if (tvCodeLogin.getText().toString().trim().equals(getString(R.string.login_code))) {
                    /*忘记密码*/
                    jumpActivity(mActivity, RetrievePasswordActivity.class);
                } else {
                    getVerificationCode();
                }
                break;
            /*注册*/
            case R.id.tv_register_now:
                jumpActivity(mActivity, RegisteredOneActivity.class);
                break;
            /*验证码登录/密码登录*/
            case R.id.tv_code_login:
                if (tvCodeLogin.getText().toString().trim().equals(getString(R.string.login_code))) {
                    /*忘记密码改为获取验证码*/
                    tvCode.setText(R.string.get_verification_code);
                    /*设置为不可点击*/
                    //tvCode.setEnabled(false);
                    /*切换到验证码登录页面*/
                    tvCodeLogin.setText(R.string.password_login);
                    tvCode.setVisibility(View.VISIBLE);
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    StringUtil.clearData(etPassword);
                    etPassword.setHint(R.string.hint_code);
                    /*限制输入数字*/
                    etPassword.setInputType(InputType.TYPE_CLASS_NUMBER);
                    /*限制输入6位*/
                    etPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                } else {
                    /*获取验证码改为忘记密码*/
                    tvCode.setText(R.string.forget_password);
                    /*更改为可以点击*/
                    tvCode.setEnabled(true);
                    /*切换到密码登录页面*/
                    tvCodeLogin.setText(R.string.login_code);
                    tvCodeLogin.setVisibility(View.VISIBLE);
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    StringUtil.clearData(etPassword);
                    etPassword.setHint(R.string.hint_password);
                    /*限制输入密码*/
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    /*限制输入6位*/
                    etPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
                }
                break;
            /*登录*/
            case R.id.tv_login:
                examineRequiredVerification();
                break;
            case R.id.tv_we_chat:
                HintUtil.showErrorWithToast(mActivity, getString(R.string.stay_tuned));
//                WeiXinFeatures.getInstance(mActivity).doLogin();
                break;
            case R.id.tv_qq:
                QqFeatures.getInstance(mActivity).doLogin();
                break;
            default:
        }
    }

    /**
     * 获取验证码
     */
    @SuppressLint("CheckResult")
    private void getVerificationCode() {
        if (!RegexUtil.isMobileExact(StringUtil.removeAllSpace(etAccountNumber.getText().toString().trim()))) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_please_ok_enter_phone_number);
            return;
        }
        LoginReq loginReq = new LoginReq();
        loginReq.setCodeUse(EnumCodeUse.getEnumCodeUse(R.string.login));
        loginReq.setMobile(StringUtil.removeAllSpace(etAccountNumber.getText().toString().trim()));
        showLoadingDialog();
        mNetworkRequestInstance.getPublicMobile(loginReq)
                .subscribe(stringResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        mCountDownTimer.start();
                        codeId = stringResponseData.getData();
                        tvCode.setEnabled(false);
                        HintUtil.showErrorWithToast(mContext, getString(R.string.been_sent));
                    } else {
                        HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                    }
                });
    }

    @Override
    protected boolean examineRequiredVerification() {
        if (!RegexUtil.isMobileExact(StringUtil.removeAllSpace(etAccountNumber.getText().toString().trim()))) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_please_ok_enter_phone_number);
            return false;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
            /*判断切换按钮是否显示*/
            if (tvCodeLogin.getText().toString().trim().equals(getString(R.string.password_login))) {
                HintUtil.showErrorWithToast(mContext, R.string.toast_please_enter_code);
            } else {
                HintUtil.showErrorWithToast(mContext, R.string.toast_password);
            }
            return false;
        }
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        LoginReq loginReq = new LoginReq();
        if (tvCodeLogin.getText().toString().trim().equals(getString(R.string.password_login))) {
            loginReq.setPhone(StringUtil.removeAllSpace(etAccountNumber.getText().toString().trim()));
            loginReq.setCodeId(codeId);
            loginReq.setCode(etPassword.getText().toString().trim());
        } else {
            loginReq.setLoginName(StringUtil.removeAllSpace(etAccountNumber.getText().toString().trim()));
            loginReq.setPwd(MD5Utils.stringToMD5(etPassword.getText().toString().trim()));
        }
        showLoadingDialog();
        mNetworkRequestInstance.userLogin(loginReq, tvCodeLogin.getText().toString().trim().equals(getString(R.string.password_login)))
                .subscribe(stringResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        UserInfo userInfo = stringResponseData.getData();
                        /*用户名*/
                        userInfo.setLoginAccount(StringUtil.removeAllSpace(etAccountNumber.getText().toString().trim()));
                        userInfo.setLoginStatus("1");
                        /*判断是否是快速登录*/
                        if (tvCodeLogin.getText().toString().trim().equals(getString(R.string.login_code))) {
                            /*明文密码*/
                            userInfo.setLoginPassword(etPassword.getText().toString().trim());
                            /*密文密码*/
                            userInfo.setLoginPasswordSalt(loginReq.getPwd());
                        }
                        /*统计登录情况*/
                        StatisticalFunction.getInstance(mContext).setProfileSignIn(userInfo.getId());
                        /*保存用户信息*/
                        HawkUtil.getInstance().saveData(HawkUtil.USER_INFO, userInfo);
                        switch (userInfo.getRegisterState()) {
                            /*注册成功*/
                            case 1:
                                jumpActivity(mActivity, AuthenticationActivity.class, true);
                                break;
                            case 2:
                                jumpActivity(mActivity, HeldIdentityActivity.class, true);
                                break;
                            case 3:
                                jumpActivity(mActivity, BankCardActivity.class, true);
                                break;
                            /*跳转首页*/
                            case 4:
                            case 5:
                            case 6:
                                jumpActivity(mActivity, MainActivity.class, true);
                                break;
                            default:
                        }
                    } else {
                        HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                    }
                });
    }

    @Override
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        WeiXinFeatures
                .getInstance(mActivity)
                .onNewIntent(data, data1 -> {
                    switch (data1.getCode()) {
                        /*成功*/
                        case 1:
                            /*第三方账号统计登录情况*/
//                    StatisticalFunction.getInstance(mContext).setProfileSignIn("微信".userInfo.getId());
                            break;
                        default:

                    }
                });
    }

    /**
     * 验证手机号是否存在
     *
     * @param phone 手机号
     */
    @SuppressLint("CheckResult")
    private void getUserIdByPhone(String phone) {
        if (RegexUtil.isMobileExact(StringUtil.removeAllSpace(etAccountNumber.getText().toString().trim()))) {
            LoginReq loginReq = new LoginReq();
            loginReq.setPhone(phone);
            mNetworkRequestInstance.getUserIdPhone(loginReq)
                    .subscribe(stringResponseData -> {
                        if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        } else {
                            HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                        }
                    });
        } else {
            if (StringUtil.removeAllSpace(etAccountNumber.getText().toString().trim()).length() <=11) {
                HintUtil.showErrorWithToast(mContext, R.string.toast_please_ok_enter_phone_number);
            }
        }
    }
}
