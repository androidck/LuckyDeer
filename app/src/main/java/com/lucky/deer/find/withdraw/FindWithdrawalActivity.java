package com.lucky.deer.find.withdraw;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.AuthTask;
import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.home.cardspending.SelectBankCardActivity;
import com.lucky.deer.home.separation.MyDividedOrWithdrawalDetailsActivity;
import com.lucky.deer.login.LoginActivity;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.util.bean.AuthResult;
import com.lucky.model.request.home.cardLife.FindWithdrawalReq;
import com.lucky.model.response.find.redEnvelope.RedEnvelopesDetailEntity;
import com.lucky.model.response.selectbankcard.CashWithdrawalRate;
import com.lucky.model.response.selectbankcard.SelectBankCardList;
import com.lucky.model.util.GlideUtils;
import com.lucky.model.util.HintUtil;
import com.lucky.model.util.RxBus;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的提现
 *
 * @author wangxiangyi
 * @date 2018/11/15
 */
@SuppressLint("Registered")
public class FindWithdrawalActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    /**
     * 银行logo
     */
    @BindView(R.id.rl_select_card)
    RelativeLayout rlSelectCard;
    /**
     * 银行logo
     */
    @BindView(R.id.tv_card_logo)
    ImageView tvCardLogo;
    /**
     * 银行名称
     */
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    /**
     * 卡尾号
     */
    @BindView(R.id.tv_bank_tail_number)
    TextView tvBankTailNumber;
    /**
     * 银行卡类型
     */
    @BindView(R.id.tv_bank_type)
    TextView tvBankType;
    /**
     * 提现金额
     */
    @BindView(R.id.et_withdrawal_amount)
    EditText etWithdrawalAmount;
    /**
     * 可提现提现
     */
    @BindView(R.id.tv_cash_withdrawal_amount)
    TextView tvCashWithdrawalAmount;
    /**
     * 全部提现
     */
    @BindView(R.id.tv_full_withdrawal)
    TextView tvFullWithdrawal;
    /**
     * 卡片信息
     */
    private SelectBankCardList selectBankCardList;
    /**
     * 手续费率信息
     */
    private CashWithdrawalRate cashWithdrawalRate;
    /**
     * 是否授权
     * false：未授权
     * true：已授权
     */
    private boolean isAuthorization;
    /**
     * 获取支付宝加密串
     */
    private String authorizationEncrypted;


    private static final int SDK_AUTH_FLAG = 2;
    /**
     * 红包明细信息
     */
    private RedEnvelopesDetailEntity mRedEnvelopeDetails;

    @Override
    protected int initLayout() {
        return R.layout.activity_find_withdrawal;
    }

    @Override
    protected void initView() {
        if (getStringData() != null) {
            tvCashWithdrawalAmount.setText(getString(R.string.cash_withdrawal_amount) + getStringData() + "元");
        } else if (getSerializableData() != null) {
            if (getSerializableData() instanceof RedEnvelopesDetailEntity) {
                /*获取*/
                mRedEnvelopeDetails = (RedEnvelopesDetailEntity) getSerializableData();
                if (mRedEnvelopeDetails != null) {
                    tvCashWithdrawalAmount.setText(getString(R.string.cash_withdrawal_amount) + mRedEnvelopeDetails.getWalletMoney() + "元");
                }
            }
        }
        if (mRedEnvelopeDetails != null) {
            initTopBar(topBar, R.string.title_activity_find_withdrawal);
        } else {
            initTopBar(topBar,
                    R.string.title_activity_find_withdrawal,
                    R.mipmap.find_more,
                    v -> {
                        if (getLoginStatus()) {
                            jumpActivity(mContext, MyDividedOrWithdrawalDetailsActivity.class, MyDividedOrWithdrawalDetailsActivity.REFLECTING_DETAILS);
                        } else {
                            jumpActivity(mContext, LoginActivity.class);
                        }
                    });
        }
        /*初始化输入两位小数*/
        StringUtil.restrictionLength(2, etWithdrawalAmount);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        showLoadingDialog();
//        mNetworkRequestInstance.getDefaultDebitCardVo()
//                .compose(bindToLifecycle())
//                .subscribe(selectBankCardBeanResponseData -> {
//                    dismissLoadingDialog();
//                    if (RequestUtils.isRequestSuccess(selectBankCardBeanResponseData)) {
//                        isAuthorization = "2".equals(selectBankCardBeanResponseData.getData().getType());
//                        selectBankCardList = selectBankCardBeanResponseData.getData().getDebitCardVo();
//                        setDefaultDebitCardVo();
//
//                    } else {
//                        HintUtil.showErrorWithToast(mContext, selectBankCardBeanResponseData.getMsg());
//                    }
//                });
        /*获取授权状态*/
        mNetworkRequestInstance.getAuthorizationStatus()
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        isAuthorization = "2".equals(returnData.getData());
                    }
                });
        /*获取授权码*/
        mNetworkRequestInstance.authorizationEncrypted()
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        authorizationEncrypted = returnData.getData();
                    }
                });
        mNetworkRequestInstance.userWallet()
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        cashWithdrawalRate = returnData.getData();
                    }
                });
    }

    @Override
    protected void initListener() {
        etWithdrawalAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("StringFormatMatches")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(s) && !".".startsWith(s.toString())) {
                    if (
                        /*红包明细提现*/
                            (mRedEnvelopeDetails != null && Double.parseDouble(mRedEnvelopeDetails.getWalletMoney()) < Double.parseDouble(s.toString())) ||
                                    /*卡生活提现*/
                                    (getStringData() != null && Double.parseDouble(getStringData()) < Double.parseDouble(s.toString()))) {
                        tvCashWithdrawalAmount.setText(R.string.more_amount_raised);
                        tvCashWithdrawalAmount.setTextColor(mContext.getResources().getColor(R.color.colorRed));
                        tvFullWithdrawal.setVisibility(View.GONE);
                    } else {
                        /*计算提现手续费 参数1：要提现金额，参数2：提现手续费*/
                        float v = StringUtil.multiplicationCalculation(Float.parseFloat(s.toString()), Float.parseFloat((cashWithdrawalRate == null || TextUtils.isEmpty(cashWithdrawalRate.getTransferPoundage())) ? "0" : cashWithdrawalRate.getTransferPoundage()));
                        /*设置信息*/
                        tvCashWithdrawalAmount.setText(
                                String.format(getString(R.string.hint_handling_fee), StringUtil.setNumberFormatting(v + ""), String.format("%.2f", StringUtil.multiplicationCalculation(Float.parseFloat((cashWithdrawalRate == null || TextUtils.isEmpty(cashWithdrawalRate.getTransferPoundage())) ? "0" : cashWithdrawalRate.getTransferPoundage()), Float.parseFloat("100")))) + "\n" +
                                        getString(R.string.cash_withdrawal_amount) + (mRedEnvelopeDetails != null ? mRedEnvelopeDetails.getWalletMoney() : getStringData()) + "元");
                        /*设置显示费率和提现金额*/
                        tvCashWithdrawalAmount.setTextColor(mContext.getResources().getColor(R.color.color_hint));
                        tvFullWithdrawal.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (mRedEnvelopeDetails != null) {
                        tvCashWithdrawalAmount.setText(getString(R.string.cash_withdrawal_amount) + mRedEnvelopeDetails.getWalletMoney() + "元");
                    } else {
                        tvCashWithdrawalAmount.setText(getString(R.string.cash_withdrawal_amount) + getStringData() + "元");
                    }
                    /*设置显示费率和提现金额*/
                    tvCashWithdrawalAmount.setTextColor(mContext.getResources().getColor(R.color.color_hint));
                    tvFullWithdrawal.setVisibility(View.VISIBLE);
                }
//                if (getStringData() != null && !TextUtils.isEmpty(s) &&
//                        !s.toString().startsWith(".") &&
//                        (mRedEnvelopeDetails != null ? Double.parseDouble(mRedEnvelopeDetails.getWalletMoney()) < Double.parseDouble(s.toString()) : Double.parseDouble(getStringData()) < Double.parseDouble(s.toString()))) {
//                    tvCashWithdrawalAmount.setText(R.string.more_amount_raised);
//                    tvCashWithdrawalAmount.setTextColor(mContext.getResources().getColor(R.color.colorRed));
//                    tvFullWithdrawal.setVisibility(View.GONE);
//                } else {
//                    if (!TextUtils.isEmpty(s)) {
//                        /*计算提现手续费 参数1：要提现金额，参数2：提现手续费*/
//                        float v = StringUtil.multiplicationCalculation(Float.parseFloat(s.toString()), Float.parseFloat((cashWithdrawalRate == null || TextUtils.isEmpty(cashWithdrawalRate.getTransferPoundage())) ? "0" : cashWithdrawalRate.getTransferPoundage()));
//                        /*设置信息*/
//                        tvCashWithdrawalAmount.setText(
//                                String.format(getString(R.string.hint_handling_fee), StringUtil.setNumberFormatting(v + ""), String.format("%.2f", StringUtil.multiplicationCalculation(Float.parseFloat((cashWithdrawalRate == null || TextUtils.isEmpty(cashWithdrawalRate.getTransferPoundage())) ? "0" : cashWithdrawalRate.getTransferPoundage()), Float.parseFloat("100")))) + "\n" +
//                                        getString(R.string.cash_withdrawal_amount) + (mRedEnvelopeDetails != null ? mRedEnvelopeDetails.getWalletMoney() : getStringData()) + "元");
//                    } else {
//                        if (mRedEnvelopeDetails != null) {
//                            tvCashWithdrawalAmount.setText(getString(R.string.cash_withdrawal_amount) + mRedEnvelopeDetails.getWalletMoney() + "元");
//                        } else {
//                            tvCashWithdrawalAmount.setText(getString(R.string.cash_withdrawal_amount) + getStringData() + "元");
//                        }
//                    }
//                    tvCashWithdrawalAmount.setTextColor(mContext.getResources().getColor(R.color.color_hint));
//                    tvFullWithdrawal.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.rl_select_card, R.id.tv_full_withdrawal, R.id.tv_determine_withdrawal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*选择储蓄卡*/
            case R.id.rl_select_card:
                SelectBankCardList selectBankCardList = new SelectBankCardList();
                selectBankCardList.setFlag(KeyConstant.SELECT_DEBIT_CARD);
                selectBankCardList.setEnterAisleFlag(KeyConstant.CARD_SPENDING);
                jumpActivityForResult(mActivity, SelectBankCardActivity.class, KeyConstant.SELECT_DEBIT_CARD, selectBankCardList);
                break;
            /*全部提现*/
            case R.id.tv_full_withdrawal:
                if (getStringData() != null) {
                    if (Float.parseFloat(getStringData()) <= 0) {
                        HintUtil.showErrorWithToast(mContext, "您的金服距离满仓还差那么一点点，努力，胜利就在前方\uD83C\uDF39\uD83D\uDC4F");
                        return;
                    }
                    etWithdrawalAmount.setText(getStringData());
                }
                /*红包明细*/
                else if (mRedEnvelopeDetails != null) {
                    if (Float.parseFloat(mRedEnvelopeDetails.getWalletMoney()) <= 0) {
                        HintUtil.showErrorWithToast(mContext, "您的金服距离满仓还差那么一点点，努力，胜利就在前方\uD83C\uDF39\uD83D\uDC4F");
                        return;
                    }
                    etWithdrawalAmount.setText(mRedEnvelopeDetails.getWalletMoney());
                }
                break;
            /*确定提现*/
            case R.id.tv_determine_withdrawal:
                examineRequiredVerification();
                break;
            default:
        }
    }

    @Override
    protected boolean examineRequiredVerification() {
       /* if (selectBankCardList == null) {
            HintUtil.showErrorWithToast(mContext, "请选择提现储蓄卡");
            return false;
        } else*/
        if (TextUtils.isEmpty(etWithdrawalAmount.getText().toString().trim()) || etWithdrawalAmount.getText().toString().equals("0.00")) {
            HintUtil.showErrorWithToast(mContext, "请输入提现金额");
            return false;
        } else if (tvFullWithdrawal.getVisibility() == View.GONE) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_cashable_balance_been_exceeded);
            return false;
        } else if (TextUtils.isEmpty(etWithdrawalAmount.getText().toString().trim()) || (StringUtil.setComparison(etWithdrawalAmount.getText().toString().trim(), (mRedEnvelopeDetails != null ? "9" : "99")))) {
            HintUtil.showErrorWithToast(mContext, String.format("您提现的金额不能低于%s元", (mRedEnvelopeDetails != null ? 10 : 100)));
            return false;
        }
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        if (isAuthorization) {
            requestWithdrawal();
        } else {
            if (!TextUtils.isEmpty(authorizationEncrypted)) {
                new Thread(() -> {
                    // 调用授权接口，获取授权结果
                    Map<String, String> result = new AuthTask(mActivity).authV2(authorizationEncrypted, true);
                    Message msg = new Message();
                    msg.what = SDK_AUTH_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);

                }).start();
            } else {
                /*获取授权码*/
                mNetworkRequestInstance.authorizationEncrypted()
                        .compose(bindToLifecycle())
                        .subscribe(returnData -> {
                            if (RequestUtils.isRequestSuccess(returnData)) {
                                authorizationEncrypted = returnData.getData();
                                startRequestInterface();
                            }
                        });
            }
        }
    }

    @SuppressLint("CheckResult")
    private void requestWithdrawal() {
        if (mRedEnvelopeDetails != null) {
            FindWithdrawalReq findWithdrawalReq = new FindWithdrawalReq();
            findWithdrawalReq.setMoney(etWithdrawalAmount.getText().toString().trim());
            showLoadingDialog();
            mNetworkRequestInstance.redWithdrawUserWithdraw(findWithdrawalReq)
                    .compose(bindToLifecycle())
                    .subscribe(returnData -> {
                        dismissLoadingDialog();
                        if (RequestUtils.isRequestSuccess(returnData)) {
                            jumpActivity(mContext, ApplyWithdrawalActivity.class, true);
                            RxBus.getInstance().post(KeyConstant.UPDATE_RED_ENVELOPE_DETAILS);
                        } else {
                            HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                        }
                    });

        } else {
            FindWithdrawalReq findWithdrawalReq = new FindWithdrawalReq();
//        findWithdrawalReq.setDebitCardId(selectBankCardList.getDebitCardId());
            findWithdrawalReq.setMoney(etWithdrawalAmount.getText().toString().trim());
            showLoadingDialog();
            mNetworkRequestInstance.userWithdraw(findWithdrawalReq)
                    .compose(bindToLifecycle())
                    .subscribe(stringResponseData -> {
                        dismissLoadingDialog();
                        if (RequestUtils.isRequestSuccess(stringResponseData)) {
//                        jumpActivity(mContext, SubmitFindWithdrawalActivity.class, stringResponseData.getData());
                            jumpActivity(mContext, ApplyWithdrawalActivity.class, true);
                            RxBus.getInstance().post(KeyConstant.UPDATE_CARD_LIFE);
                        } else {
                            HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && KeyConstant.SELECT_DEBIT_CARD == requestCode) {
            selectBankCardList = (SelectBankCardList) data.getSerializableExtra(mEntity);
            setDefaultDebitCardVo();
        }
    }

    public void setDefaultDebitCardVo() {
        if (selectBankCardList != null) {
//            rlSelectCard.setBackgroundColor(Color.parseColor(selectBankCardList.getBackgroundColor()));
            GlideUtils.loadImage(mContext, tvCardLogo, selectBankCardList.getBankColorLogo());
            tvBankName.setText(selectBankCardList.getBankName());
//            tvBankTailNumber.setText(StringUtil.replaceCardNumber(selectBankCardList.getCarNumber()));
            tvBankTailNumber.setText(getString(R.string.bank_tail_number) + StringUtil.substringData(selectBankCardList.getCarNumber(), selectBankCardList.getCarNumber().length() - 4, selectBankCardList.getCarNumber().length()));
            tvBankType.setText(selectBankCardList.getType());
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_AUTH_FLAG:
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(authResult.getResultStatus(), "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        isAuthorization = true;
                        FindWithdrawalReq findWithdrawalReq = new FindWithdrawalReq();
                        for (String s : StringUtil.getSplit(authResult.getResult(), "&")) {
                            if (s.startsWith("user_id")) {
                                findWithdrawalReq.setCode(StringUtil.getSplit(s, "=")[1]);
                                break;
                            }
                        }
                        showLoadingDialog();
                        mNetworkRequestInstance.getAlipayUserInfo(findWithdrawalReq)
                                .compose(bindToLifecycle())
                                .subscribe(returnData -> {
                                    if (RequestUtils.isRequestSuccess(returnData)) {
                                        requestWithdrawal();
                                    } else {
                                        dismissLoadingDialog();
                                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                                    }
                                });
                    } else {
                        // 其他状态值则为授权失败
                        HintUtil.showErrorWithToast(mContext, "授权失败");
                    }
                    break;
                default:
            }
        }
    };
}
