package com.lucky.deer.login;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.util.RegexUtil;
import com.lucky.deer.util.StringUtil;
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
public class RegisteredOneActivity extends BaseActivity {


    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.et_account_number)
    EditText etAccountNumber;
    @BindView(R.id.ll_code)
    LinearLayout llCode;
    @BindView(R.id.tv_submit_registration)
    TextView tvSubmitRegistration;

    @Override
    protected int initLayout() {
        return R.layout.activity_registered;
    }

    @Override
    protected void initView() {
        /*设置标题系系信息*/
        initTopBar(topBar, true, R.string.title_activity_registered,
                R.mipmap.login_close, v -> overridePendingTransition(false, true));
        /*自动添加空格*/
        StringUtil.setPhoneWatcher(etAccountNumber);
        llCode.setVisibility(View.GONE);
        tvSubmitRegistration.setText(R.string.next);
    }

    @OnClick({R.id.tv_submit_registration})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*提交注册*/
            case R.id.tv_submit_registration:
                examineRequiredVerification();
                break;
            default:
        }
    }

    @Override
    protected boolean examineRequiredVerification() {
        if (!RegexUtil.isMobileExact(StringUtil.removeAllSpace(etAccountNumber.getText().toString().trim()))) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_please_ok_enter_phone_number);
            return false;
        }
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        showLoadingDialog();
        LoginReq loginReq = new LoginReq();
        loginReq.setCodeUse(EnumCodeUse.getEnumCodeUse(R.string.registered));
        loginReq.setMobile(StringUtil.removeAllSpace(etAccountNumber.getText().toString().trim()));
        mNetworkRequestInstance.getMobile(loginReq)
                .subscribe(userInfoResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(userInfoResponseData)) {
                        loginReq.setCodeId(userInfoResponseData.getData());
                        loginReq.setPhone(StringUtil.removeAllSpace(etAccountNumber.getText().toString().trim()));
                        jumpActivity(mActivity, RegisteredTwoActivity.class, loginReq);
                    } else {
                        HintUtil.showErrorWithToast(mContext, userInfoResponseData.getMsg());
                    }
                });

    }
}
