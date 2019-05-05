package com.lucky.deer.mine.accountManagement;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.util.RegexUtil;
import com.lucky.model.request.login.LoginReq;
import com.lucky.model.request.userinfo.MineInfoReq;
import com.lucky.model.response.userinfo.UserInfo;
import com.lucky.model.util.EnumCodeUse;
import com.lucky.model.util.HawkUtil;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 更换手机号
 *
 * @author wangxiangyi
 * @date 2018/10/29
 */
public class ReplacePhoneNumberActivity extends BaseActivity {


    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.et_account_number)
    EditText etAccountNumber;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.tv_code)
    TextView tvCode;
    /**
     * 验证码
     */
    private String code;

    @Override
    protected int initLayout() {
        return R.layout.activity_replace_phone_number;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_replace_phone_number);
        initCountDownTimer(tvCode);
    }


    @OnClick({R.id.tv_code, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_code:
                getVerificationCode();
                break;
            case R.id.tv_confirm:
                examineRequiredVerification();
                break;
            default:
        }
    }

    /**
     * 获取验证码
     */
    @SuppressLint("CheckResult")
    private void getVerificationCode() {
        if (!RegexUtil.isMobileExact(etAccountNumber.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_please_ok_enter_phone_number);
            return;
        }
        showLoadingDialog();
        LoginReq loginReq = new LoginReq();
        loginReq.setCodeUse(EnumCodeUse.getEnumCodeUse(R.string.replace_phone_number));
        loginReq.setMobile(etAccountNumber.getText().toString().trim());
        mNetworkRequestInstance.getMobile(loginReq)
                .subscribe(userInfoResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(userInfoResponseData)) {
                        HintUtil.showErrorWithToast(mContext, R.string.toast_code_sent_success);
                        mCountDownTimer.start();
                        tvCode.setClickable(false);
                        code = userInfoResponseData.getData();
                    } else {
                        HintUtil.showErrorWithToast(mContext, userInfoResponseData.getMsg());
                    }
                });
    }

    /**
     * 必填验证
     *
     * @return
     */
    @Override
    protected boolean examineRequiredVerification() {
        if (!RegexUtil.isMobileExact(etAccountNumber.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_please_ok_enter_phone_number);
            return false;
        } else if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_please_enter_code);
            return false;
        }
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        MineInfoReq mineInfoReq = new MineInfoReq();
        /*手机号*/
        mineInfoReq.setPhone(etAccountNumber.getText().toString().trim());
        /*验证码Id*/
        mineInfoReq.setCodeId(code);
        /*验证码*/
        mineInfoReq.setCode(etPassword.getText().toString().trim());
        mNetworkRequestInstance.updateUserPhone(mineInfoReq)
                .subscribe(stringResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        showSuccessDialog(R.string.dialog_replace_phone_number_success);
                    } else {
                        HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                    }
                });
    }

    @Override
    public void carriedOutMethod() {
        UserInfo userInfo = getUserInfo();
        if (userInfo != null) {
            /*保存修改后的手机号*/
            userInfo.setLoginAccount(etAccountNumber.getText().toString().trim());
            userInfo.setPhone(etAccountNumber.getText().toString().trim());
        }
        HawkUtil.getInstance().saveData(HawkUtil.USER_INFO, userInfo);
        overridePendingTransition(false, true);
    }
}
