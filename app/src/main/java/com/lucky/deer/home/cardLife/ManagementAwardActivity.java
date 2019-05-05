package com.lucky.deer.home.cardLife;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 管理奖
 *
 * @author wangxiangyi
 * @date 2019/03/27
 */
public class ManagementAwardActivity extends BaseActivity {
    /**
     * 标题
     */
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    /**
     * 商户名称
     */
    @BindView(R.id.tv_business_name)
    TextView tvBusinessName;
    /**
     * 人数
     */
    @BindView(R.id.tv_number_people)
    TextView tvNumberPeople;
    /**
     * 简介
     */
    @BindView(R.id.tv_management_award_introduction)
    TextView tvManagementAwardIntroduction;
    /**
     * 申请按钮
     */
    @BindView(R.id.btn_apply_immediately)
    TextView btnApplyImmediately;


    @Override
    protected int initLayout() {
        return R.layout.activity_management_award;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_management_award);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        /*商户名称*/
        /*人数*/
        tvNumberPeople.setText(String.format(getString(R.string.text_management_award_number_people), "0"));
        showLoadingDialog();
        mNetworkRequestInstance.getUserDirectPushVipByNum()
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        btnApplyImmediately.setEnabled(!(TextUtils.isEmpty(returnData.getData().getByNum()) || Integer.parseInt(returnData.getData().getByNum()) < 3));
                        /*人数*/
                        tvNumberPeople.setText(String.format(getString(R.string.text_management_award_number_people), returnData.getData().getByNum()));

                        if (returnData.getData().getAuditStatus().equals("3")){
                            btnApplyImmediately.setText("重新申请");
                        }
                    } else {
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }

    @OnClick(R.id.btn_apply_immediately)
    public void onViewClicked() {
        startRequestInterface();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        showLoadingDialog();
        mNetworkRequestInstance.userManagerBonusApply()
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                        finish();
                    } else {
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }

}
