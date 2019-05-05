package com.lucky.deer.mine.accountManagement;

import android.annotation.SuppressLint;
import android.text.InputType;
import android.widget.EditText;

import com.dykj.requestcore.util.RequestUtils;
import com.dykj.requestcore.util.cipher.MD5Utils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.util.StringUtil;
import com.lucky.model.common.PassingEntity;
import com.lucky.model.request.login.LoginReq;
import com.lucky.model.request.userinfo.MineInfoReq;
import com.lucky.model.response.userinfo.UserInfo;
import com.lucky.model.util.HawkUtil;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置支付密码
 *
 * @author wangxiangyi
 * @date 2018/11/05
 */
public class SettingWthdrawPwdActivity extends BaseActivity {


    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    private LoginReq loginReq;
    private PassingEntity pwdReq;


    @Override
    protected int initLayout() {
        return R.layout.activity_setting_wthdraw_pwd;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_setting_wthdraw_pwd);
        if (getSerializableData() != null) {
            if (getSerializableData() instanceof PassingEntity) {
                pwdReq = (PassingEntity) getSerializableData();
                if (pwdReq.getFlag() == 4) {
                    /*设置支付密码限制*/
                    etPassword.setHint(R.string.hint_please_enter_password_6);
                    etPassword.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    StringUtil.setStringLength( 6,etPassword);
                    /*设置重复密码限制*/
                    etConfirmPassword.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    StringUtil.setStringLength( 6,etConfirmPassword);
                }
            } else if (getSerializableData() instanceof LoginReq) {
                loginReq = (LoginReq) getSerializableData();
                if (loginReq.getFlag() == 5) {
                    /*设置支付密码限制*/
                    etPassword.setHint(R.string.hint_please_enter_password_6);
                    etPassword.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    StringUtil.setStringLength( 6,etPassword);
                    /*设置重复密码限制*/
                    etConfirmPassword.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    StringUtil.setStringLength( 6,etConfirmPassword);
                }
            }
        }
    }

    @OnClick(R.id.tv_confirm)
    public void onViewClicked() {
        examineRequiredVerification();
    }

    @Override
    protected boolean examineRequiredVerification() {
        if ((pwdReq != null && pwdReq.getFlag() == 4) || (loginReq != null && loginReq.getFlag() == 5)) {
            /*判断支付设置支付密码*/
            if (etPassword.getText().toString().trim().length() < 6 || etPassword.getText().toString().trim().length() > 6) {
                HintUtil.showErrorWithToast(mContext, R.string.toast_please_enter_password_6);
                return false;
            }
        } else {
            if (etPassword.getText().toString().trim().length() < 6 || etPassword.getText().toString().trim().length() > 12) {
                HintUtil.showErrorWithToast(mContext, R.string.toast_password);
                return false;
            }
        }
        if (!etPassword.getText().toString().trim().equals(etConfirmPassword.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, getString(R.string.toast_password_inconsistent));
            return false;
        }
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        MineInfoReq mineInfoReq = new MineInfoReq();
        if (getSerializableData() != null) {
            if (getSerializableData() instanceof LoginReq) {
                loginReq = (LoginReq) getSerializableData();
                if (loginReq.getFlag() == 5) {
                    mineInfoReq.setPhone(loginReq.getPhone());
                    mineInfoReq.setCodeId(loginReq.getCodeId());
                    mineInfoReq.setCode(loginReq.getCode());
                    mineInfoReq.setPaymentPassword(MD5Utils.stringToMD5(etPassword.getText().toString().trim()));
                    mNetworkRequestInstance.resetPaymentPassword(mineInfoReq)
                            .subscribe(stringResponseData -> {
                                if (RequestUtils.isRequestSuccess(stringResponseData)) {
                                    showSuccessDialog(R.string.setting_success);
                                } else {
                                    HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                                }
                            });
                } else if (loginReq.getFlag() == 2) {
                    mineInfoReq.setPhone(loginReq.getPhone());
                    mineInfoReq.setCodeId(loginReq.getCodeId());
                    mineInfoReq.setCode(loginReq.getCode());
                    mineInfoReq.setNewPwd(MD5Utils.stringToMD5(etPassword.getText().toString().trim()));
                    mNetworkRequestInstance.forgetUserPwwd(mineInfoReq)
                            .subscribe(stringResponseData -> {
                                if (RequestUtils.isRequestSuccess(stringResponseData)) {
                                    showSuccessDialog(R.string.setting_success);
                                } else {
                                    HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                                }
                            });
                }
            } else {
                PassingEntity pwdReq = (PassingEntity) getSerializableData();
                /*设置支付密码*/
                if (pwdReq.getFlag() == 4) {
                    /*新密码*/
                    mineInfoReq.setPaymentPassword(MD5Utils.stringToMD5(etPassword.getText().toString().trim()));
                    showLoadingDialog();
                    mNetworkRequestInstance.settingPaymentPassword(mineInfoReq)
                            .subscribe(stringResponseData -> {
                                dismissLoadingDialog();
                                if (RequestUtils.isRequestSuccess(stringResponseData)) {
                                    showSuccessDialog(R.string.setting_success);
                                } else {
                                    HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                                }
                            });
                }
            }
        }
    }

    @Override
    public void carriedOutMethod() {
        UserInfo userInfo = getUserInfo();
        if (userInfo != null) {
            /*保存登录密码*/
            if (loginReq != null && loginReq.getFlag() == 2) {
                userInfo.setLoginPassword(etPassword.getText().toString().trim());
                userInfo.setLoginPasswordSalt(MD5Utils.stringToMD5(etPassword.getText().toString().trim()));
            }
            /*保存支付密码*/
            else {
                userInfo.setPaymentPasswordSalt(MD5Utils.stringToMD5(etPassword.getText().toString().trim()));
            }
            HawkUtil.getInstance().saveData(HawkUtil.USER_INFO, userInfo);
        }
        overridePendingTransition(false, true);
    }
}
