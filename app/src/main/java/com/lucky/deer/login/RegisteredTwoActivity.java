package com.lucky.deer.login;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.jyn.vcview.VerificationCodeView;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.mine.accountManagement.SettingWthdrawPwdActivity;
import com.lucky.model.request.login.LoginReq;
import com.lucky.model.util.EnumCodeUse;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册页面
 *
 * @author wangxiangyi
 * @date 2018/09/20
 */
@SuppressLint("Registered")
public class RegisteredTwoActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_show_phone)
    TextView tvShowPhone;
    @BindView(R.id.ll_account_number)
    LinearLayout llAccountNumber;
    @BindView(R.id.verificationcodeview)
    VerificationCodeView verificationcodeview;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_submit_registration)
    TextView tvSubmitRegistration;
    /**
     * 标志位<p>
     * 3：账号管理：忘记登录密码<p>
     * 6：账号管理：忘记支付密码
     */
    private int mFlag;
    /**
     * mCodeId：手机号
     * <br>
     * mCodeId：返回验证
     */
    String mCodeId, mPhone;
    /**
     * 输入的验证码
     */
    String mCode;
    /**
     * 实体
     */
    LoginReq loginReqEntity;

    @Override
    protected int initLayout() {
        return R.layout.activity_registered;
    }

    @Override
    protected void initView() {
        ivLogo.setVisibility(View.GONE);
        tvShowPhone.setVisibility(View.VISIBLE);
        llAccountNumber.setVisibility(View.GONE);
        tvSubmitRegistration.setVisibility(View.GONE);
        /*初始化倒计时控件*/
        initCountDownTimer(tvCode);
        verificationcodeview.setOnCodeFinishListener(code -> {
            mCode = code;
            examineRequiredVerification();
        });
        HintUtil.showErrorWithToast(mContext,R.string.toast_code_sent_success);
    }

    @Override
    protected void initData() {
        loginReqEntity = (LoginReq) getSerializableData();
        if (loginReqEntity != null) {
            mFlag = loginReqEntity.getFlag();
            mCodeId = loginReqEntity.getCodeId();
            mPhone = loginReqEntity.getPhone();
            if (mFlag > 0) {
                /*设置标题系系信息*/
                initTopBar(topBar, true, R.string.title_get_verification_code,
                        R.mipmap.login_close, v -> overridePendingTransition(false, true));
            } else {
                /*设置标题系系信息*/
                initTopBar(topBar, true, R.string.title_activity_registered,
                        R.mipmap.login_close, v -> overridePendingTransition(false, true));
            }
        }
        mCountDownTimer.start();
        tvCode.setEnabled(false);
        tvShowPhone.setText(getString(R.string.get_verification_code1) + mPhone + getString(R.string.get_verification_code2));
    }

    @OnClick({R.id.tv_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*获取验证码*/
            case R.id.tv_code:
                getCode();
                break;
            default:
        }
    }

    @Override
    protected boolean examineRequiredVerification() {
        if (TextUtils.isEmpty(mCode)) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_please_enter_code);
            return false;
        }
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        LoginReq loginReq = new LoginReq();
        loginReq.setPhone(mPhone);
        loginReq.setCodeId(mCodeId);
        loginReq.setCode(mCode);
        showLoadingDialog();
        mNetworkRequestInstance.getAjaxValidateCode(loginReq)
                .subscribe(userInfoResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(userInfoResponseData)) {
                        switch (mFlag) {
                            case 3:
                                loginReq.setFlag(2);
                                jumpActivity(mContext, SettingWthdrawPwdActivity.class, loginReq, true);
                                break;
                            case 6:
                                loginReq.setFlag(5);
                                jumpActivity(mContext, SettingWthdrawPwdActivity.class, loginReq, true);
                                break;
                            default:
                                jumpActivity(mContext, RegisteredActivity.class, loginReq, true);
                                break;
                        }
                    } else {
                        HintUtil.showErrorWithToast(mContext, userInfoResponseData.getMsg());
                    }
                });
    }

    /**
     * 获取验证码
     */
    @SuppressLint("CheckResult")
    private void getCode() {
        LoginReq loginReq = new LoginReq();
        switch (mFlag) {
            case 3:
                loginReq.setCodeUse(EnumCodeUse.getEnumCodeUse(R.string.modify_login_pwd));
                break;
            case 6:
                loginReq.setCodeUse(EnumCodeUse.getEnumCodeUse(R.string.modify_Pay_pwd));
                break;
            default:
                loginReq.setCodeUse(EnumCodeUse.getEnumCodeUse(R.string.registered));
                break;
        }
        loginReq.setMobile(mPhone);
        mNetworkRequestInstance.getMobile(loginReq)
                .subscribe(userInfoResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(userInfoResponseData)) {
                        HintUtil.showErrorWithToast(mContext,R.string.toast_code_sent_success);
                        mCountDownTimer.start();
                        tvCode.setEnabled(false);
                    } else {
                        HintUtil.showErrorWithToast(mContext, userInfoResponseData.getMsg());
                    }
                });
    }
}
