package com.lucky.deer.mine.accountManagement;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.model.common.PassingEntity;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 账号管理
 *
 * @author wangxiangyi
 * @date 2018/10/29
 */
public class AccountManagementActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.tv_is_withdraw_pwd)
    TextView tvIsWithdrawPwd;
    private QMUIBottomSheet qmuiBottomSheet;

    @Override
    protected int initLayout() {
        return R.layout.activity_account_management;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_account_management);
        /*获取单利*/
        PublicDialog instance = PublicDialog.getInstance();
        /*初始化视图*/
        qmuiBottomSheet = instance.addHeaderView(instance.publicListView(mContext, getdata(R.array.list_account_management)));
        /*初始化选择监听*/
        instance.setOnItemClickListener((status, position) -> {
            qmuiBottomSheet.dismiss();
            switch (status) {
                case OK:
                    jumpActivity(mContext, ReplacePhoneNumberActivity.class);
                    break;
                default:
            }
        });
    }


    @SuppressLint("CheckResult")
    @Override
    protected void onResume() {
        super.onResume();
        if (getUserInfo() != null) {
            /*设置截取之后的手机号*/
            tvPhoneNumber.setText(StringUtil.replacePhoneNumber(getUserInfo().getPhone()));
        }
        showLoadingDialog();
        mNetworkRequestInstance.isSetpaymentPassword()
                .subscribe(responseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(responseData) && responseData.getData().getFlag() == 1) {
                        tvIsWithdrawPwd.setText(R.string.modify_withdraw_pwd);
                    } else {
                        tvIsWithdrawPwd.setText(R.string.setting_withdraw_pwd);
                    }
                });
    }

    @OnClick({R.id.rl_phone_number, R.id.rl_setting_login_pwd, R.id.rl_setting_withdraw_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*更换手机号*/
            case R.id.rl_phone_number:
                qmuiBottomSheet.show();
                break;
            /*修改登录密码*/
            case R.id.rl_setting_login_pwd:
                PassingEntity loginEntity = new PassingEntity();
                loginEntity.setFlag(2);
                jumpActivity(mContext, ModifyLoginPwdActivity.class, loginEntity);
                break;
            /*设置或修改支付密码*/
            case R.id.rl_setting_withdraw_pwd:
                PassingEntity payEntity = new PassingEntity();
                /*设置*/
                if (tvIsWithdrawPwd.getText().toString().trim().equals(getString(R.string.setting_withdraw_pwd))) {
                    payEntity.setFlag(4);
                    jumpActivity(mContext, SettingWthdrawPwdActivity.class, payEntity);
                } else {
                    /*修改*/
                    payEntity.setFlag(5);
                    jumpActivity(mContext, ModifyLoginPwdActivity.class, payEntity);
                }
                break;
            default:
        }
    }
}
