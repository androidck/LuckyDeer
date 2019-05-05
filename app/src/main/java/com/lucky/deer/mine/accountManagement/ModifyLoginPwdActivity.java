package com.lucky.deer.mine.accountManagement;

import android.annotation.SuppressLint;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.dykj.requestcore.util.RequestUtils;
import com.dykj.requestcore.util.cipher.MD5Utils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.login.RegisteredTwoActivity;
import com.lucky.deer.util.StringUtil;
import com.lucky.model.common.PassingEntity;
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
 * 修改登录/提现密码
 *
 * @author wangxiangyi
 * @date 2018/10/29
 */
public class ModifyLoginPwdActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    private int flag;

    @Override
    protected int initLayout() {
        return R.layout.activity_modify_login_pwd;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_setting_login_pwd);
    }

    @Override
    protected void initData() {
        if (getSerializableData() != null) {
            flag = ((PassingEntity) getSerializableData()).getFlag();
            if (flag == 2) {
                /*修改账号密码*/
                /*设置旧密码输入限制*/
                etOldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                StringUtil.setStringLength(12, etOldPassword);
                /*设置输入新密码限制*/
                etNewPassword.setHint(R.string.hint_password);
                etNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                StringUtil.setStringLength(12, etNewPassword);
                /*设置重复密码输入限制*/
                etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                StringUtil.setStringLength(12, etConfirmPassword);
            } else {
                /*修改支付密码*/
                /*设置旧密码输入限制*/
                etOldPassword.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                StringUtil.setStringLength(6, etOldPassword);
                /*设置输入新密码限制*/
                etNewPassword.setHint(R.string.hint_please_enter_password_6);
                etNewPassword.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                StringUtil.setStringLength(6, etNewPassword);
                /*设置重复密码输入限制*/
                etConfirmPassword.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                StringUtil.setStringLength(6, etConfirmPassword);
            }
        }
    }

    @OnClick({R.id.tv_forget_password, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*忘记密码*/
            case R.id.tv_forget_password:
                forgetPassword();
                break;
            /*确认*/
            case R.id.tv_confirm:
                examineRequiredVerification();
                break;
            default:
        }
    }

    @Override
    protected boolean examineRequiredVerification() {
        if (TextUtils.isEmpty(etOldPassword.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, getString(R.string.toast_old_password));
            return false;
        }
        if (flag == 2) {
            /*判断输入登录密码*/
            if (etNewPassword.getText().toString().trim().length() < 6 || etNewPassword.getText().toString().trim().length() > 12) {
                HintUtil.showErrorWithToast(mContext, R.string.toast_new_password);
                return false;
            }
        } else {
            /*判断输入支付密码*/
            if (etNewPassword.getText().toString().trim().length() < 6 || etNewPassword.getText().toString().trim().length() > 6) {
                HintUtil.showErrorWithToast(mContext, R.string.toast_please_enter_password_6);
                return false;
            }
        }
        if (!etNewPassword.getText().toString().trim().equals(etConfirmPassword.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_password_inconsistent);
            return false;
        }
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        MineInfoReq mineInfoReq = new MineInfoReq();
        showLoadingDialog();
        /*修改登录密码*/
        if (flag == 2) {
            /*旧密码*/
            mineInfoReq.setOldPwd(MD5Utils.stringToMD5(etOldPassword.getText().toString().trim()));
            /*新密码*/
            mineInfoReq.setNewPwd(MD5Utils.stringToMD5(etNewPassword.getText().toString().trim()));
            mNetworkRequestInstance.modifyUserPwwd(mineInfoReq)
                    .subscribe(stringResponseData -> {
                        dismissLoadingDialog();
                        if (RequestUtils.isRequestSuccess(stringResponseData)) {
                            showSuccessDialog(R.string.more_success);
                        } else {
                            HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                        }
                    });
        }
        /*修改支付密码*/
        else {
            /*旧密码*/
            mineInfoReq.setOldPaymentPassword(MD5Utils.stringToMD5(etOldPassword.getText().toString().trim()));
            /*新密码*/
            mineInfoReq.setNewPaymentPassword(MD5Utils.stringToMD5(etNewPassword.getText().toString().trim()));
            mNetworkRequestInstance.modifyPaymentPassword(mineInfoReq)
                    .subscribe(stringResponseData -> {
                        dismissLoadingDialog();
                        if (RequestUtils.isRequestSuccess(stringResponseData)) {
                            showSuccessDialog(R.string.more_success);
                        } else {
                            HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                        }
                    });
        }
    }

    @Override
    public void carriedOutMethod() {
        UserInfo userInfo = getUserInfo();
        if (userInfo != null) {
            userInfo.setLoginPassword(etNewPassword.getText().toString().trim());
            userInfo.setLoginPasswordSalt(MD5Utils.stringToMD5(etNewPassword.getText().toString().trim()));
            HawkUtil.getInstance().saveData(HawkUtil.USER_INFO, userInfo);
        }
        overridePendingTransition(false, true);
    }

    @SuppressLint("CheckResult")
    private void forgetPassword() {
        LoginReq loginReq = new LoginReq();
        /*登录密码*/
        if (flag == 2) {
            loginReq.setCodeUse(EnumCodeUse.getEnumCodeUse(R.string.modify_login_pwd));
        }
        /*支付密码*/
        else {
            loginReq.setCodeUse(EnumCodeUse.getEnumCodeUse(R.string.modify_Pay_pwd));
        }
        UserInfo userInfo = getUserInfo();
        if (userInfo != null) {
            showLoadingDialog();
            loginReq.setMobile(userInfo.getPhone());
            mNetworkRequestInstance.getPublicMobile(loginReq)
                    .subscribe(userInfoResponseData -> {
                        dismissLoadingDialog();
                        if (RequestUtils.isRequestSuccess(userInfoResponseData)) {
                            loginReq.setCodeId(userInfoResponseData.getData());
                            loginReq.setPhone(userInfo.getPhone());
                            /*登录密码*/
                            if (flag == 2) {
                                loginReq.setFlag(3);
                            } else {
                                loginReq.setFlag(6);
                            }
                            jumpActivity(mActivity, RegisteredTwoActivity.class, loginReq, true);
                        } else {
                            HintUtil.showErrorWithToast(mContext, userInfoResponseData.getMsg());
                        }
                    });
        }
    }
}
