package com.lucky.deer.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.dykj.requestcore.util.RequestUtils;
import com.dykj.requestcore.util.cipher.MD5Utils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.common.WebViewActivity;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.mine.perfectuserInfo.AuthenticationActivity;
import com.lucky.model.common.WebViewBean;
import com.lucky.model.request.login.LoginReq;
import com.lucky.model.response.userinfo.UserInfo;
import com.lucky.model.util.DeviceInfoUtils;
import com.lucky.model.util.HawkUtil;
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
public class RegisteredActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.et_recommendation_code)
    EditText etRecommendationCode;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.cb_is_accept_terms)
    CheckBox cbIsAcceptTerms;
    LoginReq data;

    @Override
    protected int initLayout() {
        return R.layout.activity_submit_registered;
    }

    @Override
    protected void initView() {
        /*设置标题系系信息*/
        initTopBar(topBar, true, R.string.title_activity_registered,
                R.mipmap.login_close, v -> overridePendingTransition(false, true));
    }

    @Override
    protected void initData() {
        data = (LoginReq) getSerializableData();
    }

    @OnClick({R.id.iv_address_book, R.id.tv_application_terms, R.id.tv_submit_registration})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*通讯录*/
            case R.id.iv_address_book:
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), KeyConstant.SELECT_ADDRESS_BOOK_INFO);
                break;
            /*查看申请条款*/
            case R.id.tv_application_terms:
                WebViewBean webViewBean = new WebViewBean();
                webViewBean.setWebTitle(getString(R.string.application_terms1));
                webViewBean.setWebUrl(HttpConstant.H5_REGISTRATION_APPLICATION_TERMS);
                jumpActivity(mActivity, WebViewActivity.class, webViewBean);
                break;
            /*提交注册*/
            case R.id.tv_submit_registration:
                examineRequiredVerification();
                break;
            default:
        }
    }

    @Override
    protected boolean examineRequiredVerification() {
        if (TextUtils.isEmpty(etRecommendationCode.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_please_enter_referral_code);
            return false;
        } /*else if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, getString(R.string.hint_email));
            return false;
        } */ else if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_password);
            return false;
        } else if (etPassword.getText().toString().trim().length() < 6 ||
                etPassword.getText().toString().trim().length() > 12) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_password);
            return false;
        } else if (!cbIsAcceptTerms.isChecked()) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_is_accept_terms_application_terms);
            return false;
        }
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        if (data != null) {
            LoginReq loginReq = new LoginReq();
            loginReq.setLoginName(data.getPhone());
            loginReq.setCodeId(data.getCodeId());
            loginReq.setCode(data.getCode());
            loginReq.setRecommendCode(etRecommendationCode.getText().toString().trim());
            loginReq.setEmail(etEmail.getText().toString().trim());
            loginReq.setPwd(MD5Utils.stringToMD5(etPassword.getText().toString().trim()));
            showLoadingDialog();
            mNetworkRequestInstance.registered(loginReq)
                    .subscribe(userInfoResponseData -> {
                        dismissLoadingDialog();
                        if (RequestUtils.isRequestSuccess(userInfoResponseData)) {
                            showSuccessDialog(R.string.dialog_registration_success, userInfoResponseData.getData());
                        } else {
                            HintUtil.showErrorWithToast(mContext, userInfoResponseData.getMsg());
                        }
                    });
        }
    }

    /**
     * 设置要执行的信息
     */
    @Override
    public <T> void carriedOutMethod(T executionParam) {
        UserInfo userInfo = (UserInfo) executionParam;
        /*账户名*/
        userInfo.setLoginAccount(data.getPhone());
        /*明文密码*/
        userInfo.setLoginPassword(etPassword.getText().toString().trim());
        /*密文密码*/
        userInfo.setLoginPasswordSalt(MD5Utils.stringToMD5(etPassword.getText().toString().trim()));
        /*登录状态*/
        userInfo.setLoginStatus("1");
        /*保存用户名、密码*/
        HawkUtil.getInstance().saveData(HawkUtil.USER_INFO, userInfo);
        jumpActivity(mActivity, AuthenticationActivity.class, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == KeyConstant.SELECT_ADDRESS_BOOK_INFO) {
                if (!TextUtils.isEmpty(DeviceInfoUtils.get().getPhoneContacts(data.getData())[1])) {
                    etRecommendationCode.setText(
                            DeviceInfoUtils.get().getPhoneContacts(data.getData())[1]
                                    .replaceAll("_", "")
                                    .replaceAll("-", "")
                                    .replaceAll(" ", "")
                    );
                }
            }
        }
    }
}