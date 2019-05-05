package com.lucky.deer.login;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.dykj.requestcore.util.cipher.MD5Utils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.util.RegexUtil;
import com.lucky.deer.util.StringUtil;
import com.lucky.model.request.login.LoginReq;
import com.lucky.model.response.userinfo.UserInfo;
import com.lucky.model.util.EnumCodeUse;
import com.lucky.model.util.HawkUtil;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 找回密码
 *
 * @author wangxiangyi
 * @date 2018/10/09
 */
public class RetrievePasswordActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.et_account_number)
    EditText etAccountNumber;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_determine)
    TextView tvDetermine;
    String mCodeId;

    @Override
    protected int initLayout() {
        return R.layout.activity_retrieve_password;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, true, R.string.title_activity_retrieve_password,
                R.mipmap.login_close, v -> overridePendingTransition(false, true));
        /*初始化倒计时控件*/
        initCountDownTimer(tvCode);
        /*自动添加空格*/
        StringUtil.setPhoneWatcher(etAccountNumber);
        etAccountNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 13) {
                    if (!RegexUtil.isMobileExact(StringUtil.removeAllSpace(etAccountNumber.getText().toString().trim()))) {
                        HintUtil.showErrorWithToast(mContext, R.string.toast_please_ok_enter_phone_number);
                        return;
                    }
                    yanzengPhone(StringUtil.removeAllSpace(etAccountNumber.getText().toString().trim()));
                }
            }
        });
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
                        tvDetermine.setEnabled(true);
                    } else {
                        tvCode.setEnabled(false);
                        tvDetermine.setEnabled(false);
                        HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                    }
                });
    }

    @OnClick({R.id.tv_code, R.id.tv_determine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_code:
                if (!RegexUtil.isMobileExact(StringUtil.removeAllSpace(etAccountNumber.getText().toString().trim()))) {
                    HintUtil.showErrorWithToast(mContext, R.string.toast_please_ok_enter_phone_number);
                    return;
                }
                LoginReq loginReq = new LoginReq();
                loginReq.setCodeUse(EnumCodeUse.getEnumCodeUse(R.string.modify_login_pwd));
                loginReq.setMobile(StringUtil.removeAllSpace(etAccountNumber.getText().toString().trim()));
                showLoadingDialog();
                mNetworkRequestInstance.getPublicMobile(loginReq)
                        .subscribe(stringResponseData -> {
                            dismissLoadingDialog();
                            if (RequestUtils.isRequestSuccess(stringResponseData)) {
                                mCountDownTimer.start();
                                tvCode.setEnabled(false);
                                mCodeId = stringResponseData.getData();
                                HintUtil.showErrorWithToast(mContext, getString(R.string.been_sent));
                            } else {
                                HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                            }
                        });
                break;
            case R.id.tv_determine:
                examineRequiredVerification();
                break;
            default:
        }
    }

    @Override
    protected boolean examineRequiredVerification() {
        if (TextUtils.isEmpty(etCode.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, "请输入验证码");
            return false;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, "请输入密码");
            return false;
        } else if (etPassword.getText().toString().trim().length() < 6 ||
                etPassword.getText().toString().trim().length() > 12) {
            HintUtil.showErrorWithToast(mContext, "请输入6-12位的密码");
            return false;
        }
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        LoginReq loginReq = new LoginReq();
        loginReq.setPhone(StringUtil.removeAllSpace(etAccountNumber.getText().toString().trim()));
        loginReq.setNewPwd(MD5Utils.stringToMD5(etPassword.getText().toString().trim()));
        loginReq.setCodeId(mCodeId);
        loginReq.setCode(etCode.getText().toString().trim());
        showLoadingDialog();
        mNetworkRequestInstance.forgetUserPwwd(loginReq)
                .subscribe(stringResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        showSuccessDialog(R.string.more_success);
                    } else {
                        HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                    }
                });
    }

    /**
     * 修改成功要执行的信息
     */
    @Override
    public void carriedOutMethod() {
        /*保存用户信息*/
        UserInfo saveData = HawkUtil.getInstance().getSaveData(HawkUtil.USER_INFO);
        if (saveData == null) {
            saveData = new UserInfo();
        }
        /*用户名*/
        saveData.setLoginAccount(StringUtil.removeAllSpace(etAccountNumber.getText().toString().trim()));
        /*明文密码*/
        saveData.setLoginPassword(etPassword.getText().toString().trim());
        /*密文密码*/
        saveData.setLoginPasswordSalt(MD5Utils.stringToMD5(etPassword.getText().toString().trim()));
        /*保存用户名、密码*/
        HawkUtil.getInstance().saveData(HawkUtil.USER_INFO, saveData);
        jumpActivity(mActivity, LoginActivity.class, true);
    }
}
