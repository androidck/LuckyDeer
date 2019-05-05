package com.lucky.deer.mine;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.login.LoginActivity;
import com.lucky.deer.mine.aboutUs.AboutUsActivity;
import com.lucky.deer.mine.accountManagement.AccountManagementActivity;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.model.request.mine.MoreSettingReq;
import com.lucky.model.util.CleanMessageUtil;
import com.lucky.model.util.HawkUtil;
import com.lucky.model.util.HintUtil;
import com.lucky.model.util.RxBus;
import com.lucky.umengfunction.features.StatisticalFunction;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 更多设置
 *
 * @author wangxiangyi
 * @date 2018/10/29
 */
public class MoreSettingActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    /**
     * 允许下级和上级查看手机号(布局)
     */
    @BindView(R.id.rl_subordinate_show_mobile_phone_number)
    RelativeLayout rlSubordinateShowMobilePhoneNumber;
    /**
     * 是否向下级展示手机号
     */
    @BindView(R.id.s_start_ordering_switch)
    Switch sStartOrderingSwitch;
    @BindView(R.id.tv_fixed_line)
    TextView tvFixedLine;
    private PublicDialog inistanceView;
    private QMUIDialog qmuiDialog;

    @Override
    protected int initLayout() {
        return R.layout.activity_more_setting;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_more_setting);
        /*获取单利*/
        inistanceView = PublicDialog.getInstance();
        inistanceView.setOnClickListener((OnClickListener<String>) (status, useType, isPhoneNumber, text) -> {
            if (qmuiDialog != null) {
                switch (status) {
                    case OK:
                        CleanMessageUtil.clearAllCache(mContext);
                        tvFixedLine.setText(CleanMessageUtil.getTotalCacheSize(mContext));
                        showSuccessDialog(R.string.dialog_clear_cache_success);
                        qmuiDialog.dismiss();
                        break;
                    case CANCEL:
                        qmuiDialog.dismiss();
                        break;
                    default:
                }
            }
        });
        if (!TextUtils.isEmpty(getStringData())) {
            if (getStringData().equals("3")) {
                rlSubordinateShowMobilePhoneNumber.setVisibility(View.GONE);
            } else {
                rlSubordinateShowMobilePhoneNumber.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty(getStringData())) {
            sStartOrderingSwitch.setChecked(getStringData().equals("1"));
        }
        tvFixedLine.setText(CleanMessageUtil.getTotalCacheSize(mContext));
        sStartOrderingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                isShowMobilePhoneNumber("1");
            } else {
                isShowMobilePhoneNumber("2");
            }
        });

    }

    /**
     * 是否向下属展示手机号
     *
     * @param isChecked
     */
    @SuppressLint("CheckResult")
    private void isShowMobilePhoneNumber(String isChecked) {
        MoreSettingReq moreSettingReq = new MoreSettingReq();
        moreSettingReq.setExtendOne(isChecked);
        //showLoadingDialog();
        mNetworkRequestInstance.updateExtendOne(moreSettingReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    RxBus.getInstance().post();
                });

    }

    @OnClick({R.id.rl_account_and_security, R.id.rl_about_us, R.id.rl_clean_cache, R.id.tv_retreat_safely})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*账号与安全*/
            case R.id.rl_account_and_security:
                jumpActivity(mContext, AccountManagementActivity.class);
                break;
            /*关于我们*/
            case R.id.rl_about_us:
                jumpActivity(mContext, AboutUsActivity.class);
                break;
            /*清理缓存*/
            case R.id.rl_clean_cache:
                qmuiDialog = inistanceView.setCustomizeView(
                        inistanceView.initTitleEtOrTvView(
                                mContext, getString(R.string.dialog_prompt), getString(R.string.dialog_is_clear_cache),
                                true),
                        KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_4);
                break;
            /*退出登录*/
            case R.id.tv_retreat_safely:
                mNetworkRequestInstance.userSignOut()
                        .subscribe(stringResponseData -> {
                            if (RequestUtils.isRequestSuccess(stringResponseData)) {
                                HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                                //登出
                                StatisticalFunction.getInstance(mContext).setProfileSignOff();
                            } else {
                                HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                            }
                        });
                HawkUtil.getInstance().remove(HawkUtil.USER_INFO);
                jumpActivity(mContext, LoginActivity.class, true);
                break;
            default:
        }
    }
}
