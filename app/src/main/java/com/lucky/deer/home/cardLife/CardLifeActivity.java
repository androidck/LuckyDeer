package com.lucky.deer.home.cardLife;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.common.WebViewActivity;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.find.MemberCentreActivity;
import com.lucky.deer.find.withdraw.FindWithdrawalActivity;
import com.lucky.deer.home.MainActivity;
import com.lucky.deer.home.business.MyBusinessActivity;
import com.lucky.deer.home.cardLife.cardInformation.CardInformationActivity;
import com.lucky.deer.home.cardspending.CardSpendingActivity;
import com.lucky.deer.home.pepayment.date.DateRepaymentActivity;
import com.lucky.deer.home.pepayment.perfect.PerfectRepaymentActivity;
import com.lucky.deer.home.separation.MyDividedOrWithdrawalDetailsActivity;
import com.lucky.deer.mine.HelpActivity;
import com.lucky.deer.mine.MoreSettingActivity;
import com.lucky.deer.mine.TransactionRecordActivity;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.model.common.WebViewBean;
import com.lucky.model.request.find.PurchaseMembershipLevelReq;
import com.lucky.model.request.userinfo.MineInfoReq;
import com.lucky.model.response.EventMsg;
import com.lucky.model.response.home.cardLife.CardLifeEntity;
import com.lucky.model.response.userinfo.RefereeUserInfo;
import com.lucky.model.util.HawkUtil;
import com.lucky.model.util.HintUtil;
import com.lucky.model.util.RxBus;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 卡生活页面
 *
 * @author wangxiangyi
 * @date 2019/03/18
 */
@Route(path = "/cardLife/CardLifeActivity")
public class CardLifeActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;

    private SharedPreferences putPreferences;
    private SharedPreferences.Editor putEditor;

    OnSelectListener onSelectListener;
    /**
     * 是否隐藏金额
     */
    @BindView(R.id.cb_is_show)
    CheckBox cbIsShow;
    /**
     * 总资产
     */
    @BindView(R.id.tv_total_assets)
    TextView tvTotalAssets;
    /**
     * 佣金
     */
    @BindView(R.id.tv_commission)
    TextView tvCommission;
    /**
     * 分润
     */
    @BindView(R.id.tv_divided)
    TextView tvDivided;
    /**
     * 积分
     */
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    /**
     * 推荐人布局
     */
    @BindView(R.id.rl_referrer)
    RelativeLayout rlReferrer;
    /**
     * 推荐人手机
     */
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    /**
     * 推荐人昵称
     */
    @BindView(R.id.tv_referees_name)
    TextView tvRefereesName;
    @BindView(R.id.btn_help)
    RelativeLayout btnHelp;

    String allAmount;
    /**
     * 获取公共dialog实体
     */
    private PublicDialog inistanceView;
    /**
     * 获取dialog
     */
    private QMUIDialog qmuiDialog;
    /**
     * 推荐人idcb_is_show
     */
    private String refereeId;
    /**
     * 请求数据
     */
    private CardLifeEntity mData;

    @Override
    protected int initLayout() {
        return R.layout.activity_card_life;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        initTopBar(topBar, "卡生活");
        /*获取单利*/
        inistanceView = PublicDialog.getInstance();
        /*通知（更新卡通知接口）*/
        RxBus.getInstance()
                .toObservable()
                .subscribe(s -> {
                    if (KeyConstant.UPDATE_CARD_LIFE.equals(s)) {
                        initData();
                    }
                });
        tvTotalAssets.setText("0.00");
        tvCommission.setText("0.00");
        tvDivided.setText("0.00");
        tvIntegral.setText("0");
        srlRefresh.setEnableLoadMore(false);
        srlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        refreshLayout.finishRefresh();
                    }
                },150);
            }
        });

        cbIsShow.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cbIsShow.setChecked(true);
                tvTotalAssets.setText(mData != null && !TextUtils.isEmpty(mData.getTotalBalance()) ? mData.getTotalBalance() : "0.00");
                tvCommission.setText(mData != null && !TextUtils.isEmpty(mData.getYjMoney()) ? StringUtil.setNumberFormatting(mData.getYjMoney()) : "0.00");
                tvDivided.setText(mData != null && !TextUtils.isEmpty(mData.getFeMoney()) ? StringUtil.setNumberFormatting(mData.getFeMoney()) : "0.00");
                tvIntegral.setText(mData != null && !TextUtils.isEmpty(mData.getIntegralCalculus()) ? mData.getIntegralCalculus() : "0");
            } else {
                cbIsShow.setChecked(false);
                tvTotalAssets.setText("******");
                tvCommission.setText("*****");
                tvDivided.setText("*****");
                tvIntegral.setText("*****");
            }
        });
    }

    @Override
    protected void initListener() {
        /*下拉刷新*/
//        srlRefresh.setOnRefreshListener(this::initData);
        /*查看金额监听*/

        /*获取修改昵称监听*/
        inistanceView.setOnClickListener((OnClickListener<String>) (status, useType, isPhoneNumber, text) -> {
            switch (status) {
                case CANCEL:
                    qmuiDialog.dismiss();
                    break;
                case OK:
                    if (KeyConstant.TYPE_UPGRADE_CODE.equals(useType)) {
                        setUpgradeCode(text);
                    }
                    break;
                case LEAVE_COMMENTS:
                    userLeaveMessage(text);
                    break;
                default:
            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        showLoadingDialog();
        mNetworkRequestInstance.getUserAssetsInfo()
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        mData = returnData.getData();
                        allAmount=mData.getTotalBalance();
                        cbIsShow.setChecked(true);
                        tvTotalAssets.setText(mData != null && !TextUtils.isEmpty(mData.getTotalBalance()) ? mData.getTotalBalance() : "0.00");
                        tvCommission.setText(mData != null && !TextUtils.isEmpty(mData.getYjMoney()) ? StringUtil.setNumberFormatting(mData.getYjMoney()) : "0.00");
                        tvDivided.setText(mData != null && !TextUtils.isEmpty(mData.getFeMoney()) ? StringUtil.setNumberFormatting(mData.getFeMoney()) : "0.00");
                        tvIntegral.setText(mData != null && !TextUtils.isEmpty(mData.getIntegralCalculus()) ? mData.getIntegralCalculus() : "0");
                        /*推荐人名称*/
                        if (!TextUtils.isEmpty(mData.getRefereeUser()) && mData.getRefereeUser().length() > 0) {
                            tvRefereesName.setText(StringUtil.substringData(mData.getRefereeUser(), 0, 1) + "**");
                        }
                        /*推荐人手机号*/
                        if (!TextUtils.isEmpty(mData.getRefereePhone())) {
                            rlReferrer.setVisibility(View.VISIBLE);
                            tvPhoneNumber.setText("2".equals(mData.getRefereeExtendOne()) ?
                                    StringUtil.replacePhoneNumber(mData.getRefereePhone()) : mData.getRefereePhone());
                        } else {
                            rlReferrer.setVisibility(View.GONE);
                        }
                    } else {
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }


    @OnClick({R.id.tv_withdraw_cash, R.id.tv_commission, R.id.tv_divided, R.id.tv_integral, R.id.tv_smart_collection, R.id.tv_date_repayment,
            R.id.tv_perfect_repayment, R.id.tv_custom_repayment, R.id.tv_my_business, R.id.tv_card_information, R.id.tv_want_upgrade,
            R.id.tv_transaction_record, R.id.sign_benefits, R.id.rl_sign_benefits, R.id.rl_invite_friends, R.id.rl_referrer,
            R.id.rl_management_award, R.id.rl_upgrade_code, R.id.rl_online_service, R.id.rl_settings,R.id.btn_help})
    public void onViewClicked(View view) {
        //获取当前的用户状态
        if (HawkUtil.getInstance().isContains(HawkUtil.USER_INFO)) {
            /*判断是否实名制或开卡成功*/
            if (getRegisterState(getUserInfo().getRegisterState())) {
                Class mClass = null;
                String mPassValue = null;
                switch (view.getId()) {
                    /*提现*/
                    case R.id.tv_withdraw_cash:
                        mClass = FindWithdrawalActivity.class;
                        if (mData == null || TextUtils.isEmpty(mData.getTotalBalance())) {
                            mPassValue = "0.00";
                        } else {
                            mPassValue = mData.getTotalBalance();
                        }
                        break;
                    /*佣金*/
                    case R.id.tv_commission:
                        toast(getString(R.string.stay_tuned));
                        break;
                    /*分润*/
                    case R.id.tv_divided:
                        mClass = MyDividedOrWithdrawalDetailsActivity.class;
                        mPassValue = MyDividedOrWithdrawalDetailsActivity.SEPARATION;
                        break;
                    /*积分*/
                    case R.id.tv_integral:
                        toast(getString(R.string.stay_tuned));
                        break;
                    /*刷卡*/
                    case R.id.tv_smart_collection:
                        mClass = CardSpendingActivity.class;
                        break;
                    /*日期养卡*/
                    case R.id.tv_date_repayment:
                        mClass = DateRepaymentActivity.class;
                        break;
                    /*完美养卡*/
                    case R.id.tv_perfect_repayment:
                        mClass = PerfectRepaymentActivity.class;
                        break;
                    /*自定义养卡*/
                    case R.id.tv_custom_repayment:
                        toast(getString(R.string.stay_tuned));
                        break;
                    /*我的商户*/
                    case R.id.tv_my_business:
                        mClass = MyBusinessActivity.class;
                        break;
                    /*卡信息*/
                    case R.id.tv_card_information:
                        mClass = CardInformationActivity.class;
                        break;
                    /*我要升级*/
                    case R.id.tv_want_upgrade:
                        mClass = MemberCentreActivity.class;
                        break;
                    /*交易记录*/
                    case R.id.tv_transaction_record:
                        mClass = TransactionRecordActivity.class;
                        break;
                    /*签到领福利*/
                    case R.id.rl_sign_benefits:
                        toast(getString(R.string.stay_tuned));
                        break;
                    /*邀请好友*/
                    case R.id.rl_invite_friends:
                        jumpActivity(mContext, MainActivity.class, KeyConstant.MEMBER_CENTRE, true);
                        putPreferences = getSharedPreferences("SELECT_INFO", Context.MODE_PRIVATE);
                        putEditor = putPreferences.edit();
                        putEditor.putString("model", "1");
                        putEditor.commit();
                        break;
                    /*推荐人*/
                    case R.id.rl_referrer:
                        getRefereeUserInfo();
                        break;
                    /*管理奖*/
                    case R.id.rl_management_award:
                        jumpActivity(mContext, ManagementAwardActivity.class);
                        break;
                    /*升级码*/
                    case R.id.rl_upgrade_code:
                        /*输入验证码弹出框*/
                        qmuiDialog = inistanceView.setCustomizeView(
                                inistanceView.initVerificationCodeView(mActivity,
                                        getString(R.string.text_upgrade_code),
                                        getString(R.string.hint_upgrade_code),
                                        getString(R.string.toast_upgrade_code_can_not_empty),
                                        InputType.TYPE_CLASS_TEXT,
                                        KeyConstant.TYPE_UPGRADE_CODE),
                                true,
                                KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_4);
                        break;
                    /*在线客服*/
                    case R.id.rl_online_service:
                        WebViewBean webViewBean = new WebViewBean();
                        webViewBean.setWebTitle(getString(R.string.online_service));
                        webViewBean.setWebUrl(HttpConstant.H5_ONLINE_SERVICE + getUserInfo().getId());
                        webViewBean.setCallJsMethod("YTX.destroy()");
                        jumpActivity(mActivity, WebViewActivity.class, webViewBean);
                        break;
                    /*设置*/
                    case R.id.rl_settings:
                        mClass = MoreSettingActivity.class;
                        mPassValue = mData.getIsSelectLowerPhone();
                        break;
                    case R.id.btn_help:
                        startActivity(new Intent(mActivity, HelpActivity.class));
                        break;
                    default:
                }
                /*跳转页面*/
                if (mClass != null) {
                    Intent intent = new Intent(mActivity, mClass);
                    if (!TextUtils.isEmpty(mPassValue)) {
                        intent.putExtra(mEntity, mPassValue);
                    }
                    startActivity(intent);
                }
            }
        } else {
            obtainLoginStatus();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    /**
     * 获取推荐人信息
     */
    @SuppressLint("CheckResult")
    private void getRefereeUserInfo() {
        showLoadingDialog();
        mNetworkRequestInstance.getRefereeUserInfo()
                .compose(bindToLifecycle())
                .subscribe(refereeUserInfoResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(refereeUserInfoResponseData)) {
                        if (refereeUserInfoResponseData.getData() != null) {
                            RefereeUserInfo data = refereeUserInfoResponseData.getData();
                            if (data != null) {
                                refereeId = data.getRefereeId();
                                if (mData != null) {
                                    data.setIsAllphone(mData.getRefereeExtendOne());
                                }
                                /*初始化推荐人信息弹出框*/
                                qmuiDialog = inistanceView.setCustomizeView(
                                        inistanceView.referrerInfoView(mActivity,
                                                data),
                                        true,
                                        0.6f);
                            }
                        }
                    } else {
                        HintUtil.showErrorWithToast(mActivity, refereeUserInfoResponseData.getMsg());
                    }
                });
    }

    /**
     * 用户给推荐人发送留言
     *
     * @param text 留言内容
     */
    @SuppressLint("CheckResult")
    private void userLeaveMessage(String text) {
        MineInfoReq mineInfoReq = new MineInfoReq();
        /*设置推荐人id*/
        mineInfoReq.setRefereeId(refereeId);
        /*设置留言内容*/
        mineInfoReq.setLeaveMessageContent(text);
        showLoadingDialog();
        mNetworkRequestInstance.userLeaveMessage(mineInfoReq)
                .compose(bindToLifecycle())
                .subscribe(refereeUserInfoResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(refereeUserInfoResponseData)) {
                        showSuccessDialog(R.string.dialog_message_success);
                        qmuiDialog.dismiss();
                    } else {
                        HintUtil.showErrorWithToast(mActivity, refereeUserInfoResponseData.getMsg());
                    }
                });
    }

    /**
     * 升级码上级会员请求
     *
     * @param upgradeCode 升级码
     */
    @SuppressLint("CheckResult")
    private void setUpgradeCode(String upgradeCode) {
        PurchaseMembershipLevelReq data = new PurchaseMembershipLevelReq();
        data.setUpgradeCode(upgradeCode);
        showLoadingDialog();
        mNetworkRequestInstance.useUpgradeCode(data)
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    qmuiDialog.dismiss();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        showSuccessDialog(R.string.dialog_update_successed);
                    } else {
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                    }
                });
    }

    public interface OnSelectListener{
        void isSelect(int i);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }
}
