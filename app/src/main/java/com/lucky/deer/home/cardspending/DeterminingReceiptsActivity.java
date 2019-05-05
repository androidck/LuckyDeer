package com.lucky.deer.home.cardspending;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.common.WebViewActivity;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.home.MainActivity;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.enums.PublicEnum;
import com.lucky.model.common.WebViewBean;
import com.lucky.model.request.quickpay.CreateQuickpayReq;
import com.lucky.model.response.selectbankcard.SelectBankCardList;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 确定收款
 *
 * @author wangxingyi
 * @date 2018/10/11
 */
public class DeterminingReceiptsActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.tv_aisle_name)
    TextView tvAisleName;
    @BindView(R.id.tv_actual_name)
    TextView tvActualName;
    @BindView(R.id.tv_peserve_phone)
    TextView tvPeservePhone;
    @BindView(R.id.tv_amount_consumption)
    TextView tvAmountConsumption;
    @BindView(R.id.tv_handling_fee)
    TextView tvHandlingFee;
    /**
     * 储蓄卡信息
     */
    private SelectBankCardList selectBankCardList;
    /**
     * 所在位置
     */
    private String mSubmitLocation;

    @Override
    protected int initLayout() {
        return R.layout.activity_determining_receipts;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_determining_receipts);

    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        showLoadingDialog();
        mNetworkRequestInstance.getDefaultDebitCardVo()
                .subscribe(selectBankCardBeanResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(selectBankCardBeanResponseData)) {
                        selectBankCardList = selectBankCardBeanResponseData.getData();
                        setDefaultDebitCardVo();
                    } else {
                        HintUtil.showErrorWithToast(mContext, selectBankCardBeanResponseData.getMsg());
                    }
                });
        if (getBundleData() != null) {
            getBundleData().getString(KeyConstant.CHANNEL_ID);
            getBundleData().getString(KeyConstant.CREDIT_CARD_ID);
            tvAmountConsumption.setText(getBundleData().getString(KeyConstant.AMOUNT_CONSUMPTION));
            tvHandlingFee.setText(getBundleData().getString(KeyConstant.HANDLING_FEE));
            /*定位信息*/
            mSubmitLocation = getBundleData().getString(KeyConstant.LOCATION_INFORMATION);
        }
    }

    @OnClick({R.id.ll_selset_aisle, R.id.tv_determining_receipts})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*收款账号*/
            case R.id.ll_selset_aisle:
                SelectBankCardList selectBankCardList = new SelectBankCardList();
                selectBankCardList.setFlag(KeyConstant.SELECT_DEBIT_CARD);
                selectBankCardList.setEnterAisleFlag(KeyConstant.CARD_SPENDING);
                jumpActivityForResult(mActivity, SelectBankCardActivity.class, KeyConstant.SELECT_DEBIT_CARD, selectBankCardList);
                break;
            /*确定收款*/
            case R.id.tv_determining_receipts:
                examineRequiredVerification();
                break;
            default:
        }
    }

    @Override
    protected boolean examineRequiredVerification() {
        if (selectBankCardList == null) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_please_select_payment_account);
            return false;
        }
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        CreateQuickpayReq createQuickpayReq = new CreateQuickpayReq();
        createQuickpayReq.setChannelId(getBundleData().getString(KeyConstant.CHANNEL_ID));
        createQuickpayReq.setDebitCardId(selectBankCardList.getDebitCardId());
        createQuickpayReq.setCreditCardId(getBundleData().getString(KeyConstant.CREDIT_CARD_ID));
        createQuickpayReq.setMoney(tvAmountConsumption.getText().toString().trim());
        /*城市名称*/
        createQuickpayReq.setCity(mSubmitLocation);
        /*前台跳转地址 */
        createQuickpayReq.setReturnUrl(KeyConstant.WEB_VIEW_HTTP_BAIDU_COM);
        showLoadingDialog();
        mNetworkRequestInstance.createQuickpay(createQuickpayReq)
                .subscribe(stringResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        switch (stringResponseData.getData().getPayState()) {
                            /*交易成功 */
                            case "PAID":
                                showSuccessDialog(R.string.dialog_receipt_success);
                                break;
                            /*需要开卡*/
                            case "WAITING":
                                WebViewBean webViewBean = new WebViewBean();
                                webViewBean.setWebTitle(PublicEnum.getEnumTitleName(stringResponseData.getData().getCreatePayType(), "支付类型"));
                                webViewBean.setWebUrl(stringResponseData.getData().getCodeUrl());
                                jumpActivity(mContext, WebViewActivity.class, webViewBean, true);
                                break;
                            /*交易取消或超时 其他代表未交易*/
                            case "CANCELED_OR_TIMEOUT":
                            default:
                                showFailedDialog(R.string.dialog_receipt_failure);
                        }
                    } else {
                        HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                    }
                });
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
            tvActualName.setText(selectBankCardList.getRealName());
            tvAisleName.setText(StringUtil.replaceCardNumber(selectBankCardList.getCarNumber()));
            tvPeservePhone.setText(selectBankCardList.getPhone());
        }
    }

    @Override
    public void carriedOutMethod() {
        jumpActivity(mContext, MainActivity.class, true);
    }

}
