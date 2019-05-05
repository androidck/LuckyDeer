package com.lucky.deer.home.cardspending;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.dykj.requestcore.util.RequestUtils;
import com.lucky.mapfeatures.positioning.MapLocationListener;
import com.lucky.mapfeatures.positioning.PositioningFeatures;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.common.WebViewActivity;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.adapter.SelsetAisleAdapter;
import com.lucky.deer.weight.enums.PublicEnum;
import com.lucky.model.common.WebViewBean;
import com.lucky.model.request.quickpay.CreateQuickpayReq;
import com.lucky.model.request.quickpay.QueryBankLimitReq;
import com.lucky.model.response.cardspending.QueryBankLimitEntity;
import com.lucky.model.response.cardspending.SwipeChannelEntity;
import com.lucky.model.response.selectbankcard.SelectBankCardList;
import com.lucky.model.util.GlideUtils;
import com.lucky.model.util.HawkUtil;
import com.lucky.model.util.HintUtil;
import com.lucky.model.util.LayoutStyleUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 刷卡消费
 *
 * @author wangxiangyi
 * @date 2018/10/10
 */
public class CardSpendingActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.ll_add_card)
    LinearLayout llAddCard;
    /**
     * 信用卡布局
     */
    @BindView(R.id.ll_layout)
    RelativeLayout llLayout;
    /**
     * 信用卡布局卡卡布局
     */
    @BindView(R.id.ll_card_background)
    RelativeLayout llCardBackground;
    @BindView(R.id.iv_card_blurring_logo)
    ImageView ivCardBlurringLogo;
    @BindView(R.id.iv_card_logo)
    ImageView ivCardLogo;
    @BindView(R.id.tv_card_name)
    TextView tvCardName;
    @BindView(R.id.tv_card_type)
    TextView tvCardType;
    @BindView(R.id.tv_card_number)
    TextView tvCardNumber;
    @BindView(R.id.tv_card_type2)
    TextView tvCardType2;
    /**
     * 信用卡解绑按钮
     */
    @BindView(R.id.tv_untied)
    TextView tvUntied;
    /**
     * 开始日、结束日布局
     */
    @BindView(R.id.ll_billing_date)
    LinearLayout llBillingDate;
    /**
     * 去养卡按钮
     */
    @BindView(R.id.tv_repayment)
    TextView tvRepayment;
    /**
     * 选择通道布局
     */
    @BindView(R.id.tv_select_aisle)
    TextView tvSelectAisle;
    /**
     * 通道展示布局
     */
    @BindView(R.id.ll_aisle_info_layout)
    LinearLayout llAisleInfoLayout;
    /**
     * 通道上线金额
     */
    @BindView(R.id.tv_online_amount)
    TextView tvOnlineAmount;
    /**
     * 费率
     */
    @BindView(R.id.tv_rate)
    TextView tvRate;
    /**
     * 单笔手续费
     */
    @BindView(R.id.tv_single_pen_handling_fee)
    TextView tvSinglePenHandlingFee;
    /**
     * 商户类型
     */
    @BindView(R.id.tv_business_type)
    TextView tvBusinessType;
    /**
     * 刷卡有积分
     */
    @BindView(R.id.tv_swipe_has_points)
    TextView tvSwipeHasPoints;
    /**
     * 支持银行
     */
    @BindView(R.id.tv_support_bank_name)
    TextView tvSupportBankName;
    /**
     * 展开箭头
     */
    @BindView(R.id.iv_zhankai)
    ImageView ivZhankai;
    @BindView(R.id.et_amount_consumption_number)
    EditText etAmountConsumptionNumber;
    @BindView(R.id.tv_handling_fee)
    TextView tvHandlingFee;
    /**
     * 最低或高限额
     */
    @BindView(R.id.tv_limit)
    TextView tvLimit;
    @BindView(R.id.tv_next)
    TextView tvNext;
    SelectBankCardList selectBankCardLists;
    String handlingFee;
    private QMUIBottomSheet dialog;
    private SelsetAisleAdapter selsetAisleAdapter;
    private View dialogSelectBankView;
    private String rate, fee;
    private SwipeChannelEntity swipeChannelEntity;
    /**
     * 限额信息
     */
    private QueryBankLimitEntity mChannelListenerimit;
    /**
     * 所在位置
     */
    private String mSubmitLocation;

    @Override
    protected int initLayout() {
        return R.layout.activity_card_spending;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_card_spending,
                R.mipmap.little_knoeledge,
                v -> {
                    jumpActivity(mActivity, CardRaisingKnowledgeActivity.class);
                });
        llAddCard.setVisibility(View.VISIBLE);
        llLayout.setVisibility(View.GONE);
        llAisleInfoLayout.setVisibility(View.GONE);
        /*初始化输入两位小数*/
        StringUtil.restrictionLength(2, etAmountConsumptionNumber);
        /*监听输入金额*/
        etAmountConsumptionNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    tvNext.setVisibility(View.GONE);
                    tvHandlingFee.setText(getString(R.string.handling_fee) + "0.00元");
                } else {
                    tvNext.setVisibility(View.VISIBLE);
                    handlingFee = StringUtil.getFeeCalculation(s.toString(), rate, fee);
                    tvHandlingFee.setText(getString(R.string.handling_fee) + handlingFee);
                }
            }
        });
        dialogSelectBankView = LayoutInflater.from(mContext).inflate(R.layout.dialog_select_bank, null);
        RecyclerView rvList = dialogSelectBankView.findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        selsetAisleAdapter = new SelsetAisleAdapter();
        rvList.setAdapter(selsetAisleAdapter);

        rvList.addItemDecoration(new DividerItemDecoration(rvList.getContext(), new LinearLayoutManager(mContext).getOrientation()));
        showLoadingDialog(R.string.dialog_getting_targeting);
        /*提交数据时开启定位*/
        isOpenIntervalPositioning(mContext,
                false,
                2, new MapLocationListener() {
                    @Override
                    public void onLocationSuccess(AMapLocation amapLocation, int scenesUsed) {
                        dismissLoadingDialog();
                        if (amapLocation != null && !TextUtils.isEmpty(amapLocation.getCountry()) && "中国".equals(amapLocation.getCountry())) {
                            StringBuffer string = new StringBuffer();
                            string.append(amapLocation.getProvince());
                            string.append(",");
                            string.append(amapLocation.getCity());
                            string.append(",");
                            string.append(amapLocation.getAdCode());
                            mSubmitLocation = string.toString();
                        }
                    }

                    @Override
                    public void onLocationFailure() {
                        PositioningFeatures.getInstance(mContext).stopLocation();
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        mNetworkRequestInstance
                .queryChannel()
                .subscribe(listResponseData -> {
                    if (RequestUtils.isRequestSuccess(listResponseData)) {
                        selsetAisleAdapter.setNewData(listResponseData.getData());
                    } else {
                        HintUtil.showErrorWithToast(mContext, listResponseData.getMsg());
                    }
                });
    }

    @Override
    protected void initListener() {
        /*item点击监听*/
        selsetAisleAdapter.setOnItemClickListener((adapter, view1, position) -> {
            for (SwipeChannelEntity datum : selsetAisleAdapter.getData()) {
                datum.setIsChecked(false);
            }
            selsetAisleAdapter.getData().get(position).setIsChecked(true);
            selsetAisleAdapter.notifyDataSetChanged();
        });
        /*item控件监听*/
        selsetAisleAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.cb_list_selected:
                    for (SwipeChannelEntity datum : selsetAisleAdapter.getData()) {
                        datum.setIsChecked(false);
                    }
                    selsetAisleAdapter.getData().get(position).setIsChecked(true);
                    selsetAisleAdapter.notifyDataSetChanged();
                    break;
                default:
            }
        });
        dialogSelectBankView
                .findViewById(R.id.tv_cancel)
                .setOnClickListener(v -> {
                    dialog.dismiss();
                });
        dialogSelectBankView
                .findViewById(R.id.tv_ok)
                .setOnClickListener(v -> {
                    for (SwipeChannelEntity datum : selsetAisleAdapter.getData()) {
                        if (datum.isChecked()) {
                            swipeChannelEntity = datum;
                            dialog.dismiss();
                        }
                    }
                    if (swipeChannelEntity == null) {
                        HintUtil.showErrorWithToast(mContext, "请选择消费类型");
                    } else {
//                                openPay(swipeChannelEntity.getChannelId());
                        queryBankLimit(swipeChannelEntity.getChannelId());
                    }
                });
    }

    /**
     * 开通快捷支付
     *
     * @param channelId
     */
    @SuppressLint("CheckResult")
    public void openPay(String channelId) {
        CreateQuickpayReq createQuickpayReq1 = new CreateQuickpayReq();
        /*通道ID*/
        createQuickpayReq1.setChannelId(channelId);
        /*信用卡ID*/
        createQuickpayReq1.setCreditCardId(selectBankCardLists.getCreditId());
        showLoadingDialog();
        mNetworkRequestInstance.openQuickPayQuery(createQuickpayReq1)
                .compose(bindToLifecycle())
                .subscribe(createQuickpayEntityResponseData -> {
                    if (RequestUtils.isRequestSuccess(createQuickpayEntityResponseData)) {
                        if (createQuickpayEntityResponseData.getData().getOpenQuickPayFlag()) {
                            dismissLoadingDialog();
                            HawkUtil.getInstance().saveData(KeyConstant.IS_OPEN_FAST_PAYMENT, createQuickpayEntityResponseData.getData().getOpenQuickPayFlag());
                        } else {
                            CreateQuickpayReq createQuickpayReq = new CreateQuickpayReq();
                            createQuickpayReq.setChannelId(channelId);
                            createQuickpayReq.setDebitCardId(createQuickpayEntityResponseData.getData().getDefaultDebitId());
                            createQuickpayReq.setCreditCardId(selectBankCardLists.getCreditId());
                            /*城市名称*/
                            createQuickpayReq.setCity(mSubmitLocation);
                            createQuickpayReq.setMoney("10");
                            /*前台跳转地址 */
                            createQuickpayReq.setReturnUrl(KeyConstant.WEB_VIEW_HTTP_BAIDU_COM);
                            mNetworkRequestInstance.createQuickpay(createQuickpayReq)
                                    .compose(bindToLifecycle())
                                    .subscribe(stringResponseData -> {
                                        dismissLoadingDialog();
                                        if (RequestUtils.isRequestSuccess(stringResponseData)) {
                                            WebViewBean webViewBean = new WebViewBean();
                                            webViewBean.setWebTitle(PublicEnum.getEnumTitleName(stringResponseData.getData().getCreatePayType(), "支付类型"));
                                            webViewBean.setWebUrl(stringResponseData.getData().getCodeUrl());
                                            jumpActivity(mContext, WebViewActivity.class, webViewBean);
                                        } else {
                                            HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                                        }
                                    });
                        }
                    } else {
                        dismissLoadingDialog();
                        HintUtil.showErrorWithToast(mContext, createQuickpayEntityResponseData.getMsg());
                    }
                });
    }

    /***
     * 获取通道限额
     * @param channelId
     */
    @SuppressLint("CheckResult")
    private void queryBankLimit(String channelId) {
        QueryBankLimitReq data = new QueryBankLimitReq();
        data.setCreditCardId(selectBankCardLists.getCreditId());
        data.setChannelId(channelId);
        showLoadingDialog();
        mNetworkRequestInstance.queryBankLimit(data)
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        tvNext.setEnabled(true);
                        mChannelListenerimit = returnData.getData();
                        if (swipeChannelEntity != null) {
                            tvSelectAisle.setVisibility(View.GONE);
                            llAisleInfoLayout.setVisibility(View.VISIBLE);
                            tvOnlineAmount.setText(TextUtils.isEmpty(swipeChannelEntity.getChannelSingleLimitUp()) ? "" : StringUtil.setMoneyConverter(swipeChannelEntity.getChannelSingleLimitUp()));
                            rate = swipeChannelEntity.getRate();
                            fee = swipeChannelEntity.getFee();
                            tvRate.setText(getString(R.string.rate) + (TextUtils.isEmpty(swipeChannelEntity.getRate()) ? "" : swipeChannelEntity.getRate() + "%"));
                            tvSinglePenHandlingFee.setText(mContext.getString(R.string.single_pen_handling_fee) + (TextUtils.isEmpty(swipeChannelEntity.getFee()) ? "" : StringUtil.setMoneyConverter(swipeChannelEntity.getFee())));
//            tvBusinessType.setText(getString(R.string.business_type) + PublicEnum.getEnumTitleName(swipeChannelEntity.getExtendedField1(), "通道接口"));
                            tvBusinessType.setText(getString(R.string.business_type) + swipeChannelEntity.getExtendedField1());
                            /*判断是否有积分*/
                            if ("1".equals(swipeChannelEntity.getScoreFlag())) {
                                /*有积分*/
                                tvSwipeHasPoints.setVisibility(View.VISIBLE);
                                tvSwipeHasPoints.setText(getString(R.string.swipe_has_points));
                            } else {
                                /*没有积分*/
//                tvSwipeHasPoints.setText(getString(R.string.swipe_not_points));
                                tvSwipeHasPoints.setVisibility(View.GONE);
                            }
                            tvSupportBankName.setText(getString(R.string.support_bank_name) + (TextUtils.isEmpty(swipeChannelEntity.getSupportBanks()) ? "" : swipeChannelEntity.getSupportBanks()));
                            if (!TextUtils.isEmpty(etAmountConsumptionNumber.getText().toString().trim())) {
                                handlingFee = StringUtil.getFeeCalculation(etAmountConsumptionNumber.getText().toString().trim(), rate, fee);
                                tvHandlingFee.setText(getString(R.string.handling_fee) + handlingFee);
                            }
                            tvLimit.setText(String.format(getString(R.string.hint_limit), StringUtil.setMoneyConverter(mChannelListenerimit.getTradingLimitLowY()), StringUtil.setMoneyConverter(mChannelListenerimit.getTradingLimitUpY())));
                        }
                    } else {
                        tvNext.setEnabled(false);
                        /*显示选择通道按钮*/
                        tvSelectAisle.setVisibility(View.VISIBLE);
                        /*隐藏信息通道控件*/
                        llAisleInfoLayout.setVisibility(View.GONE);
                        /*清楚提示控件信息*/
                        StringUtil.clearData(tvLimit);
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }

    @OnClick({R.id.ll_add_card, R.id.ll_card_background, R.id.tv_add_card,
            R.id.ll_select_aisle, R.id.iv_zhankai, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_add_card:
            case R.id.ll_card_background:
            case R.id.tv_add_card:
                SelectBankCardList selectBankCardList = new SelectBankCardList();
                selectBankCardList.setFlag(KeyConstant.SELECT_CREDIT_CARD);
                selectBankCardList.setEnterAisleFlag(KeyConstant.CARD_SPENDING);
                jumpActivityForResult(mActivity, SelectBankCardActivity.class, KeyConstant.SELECT_CREDIT_CARD, selectBankCardList);
                break;
            case R.id.ll_select_aisle:
                if (selectBankCardLists == null) {
                    HintUtil.showErrorWithToast(mContext, R.string.toast_select_credit_card);
                    return;
                }
                if (selsetAisleAdapter.getData().size() > 0) {
                    dialog = PublicDialog.getInstance().addHeaderView(mContext, dialogSelectBankView);
                    dialog.show();
                } else {
                    HintUtil.showErrorWithToast(mContext, R.string.toast_not_channel_available);
                    return;
                }
                break;
            case R.id.iv_zhankai:
                StringUtil.setImageViewRotation(ivZhankai, tvSupportBankName);
                break;
            case R.id.tv_next:
                examineRequiredVerification();
                break;
            default:
        }
    }

    @Override
    protected boolean examineRequiredVerification() {
        if (selectBankCardLists == null) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_select_credit_card);
            return false;
        } else if (tvSelectAisle.getVisibility() == View.VISIBLE) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_select_aisles);
            return false;
        } else if (TextUtils.isEmpty(etAmountConsumptionNumber.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_input_mount_consumption);
            return false;
        } else if (mChannelListenerimit != null &&
                TextUtils.isEmpty(mChannelListenerimit.getTradingLimitLowY()) ||
                TextUtils.isEmpty(mChannelListenerimit.getTradingLimitUpY()) ||
                StringUtil.setComparison1(etAmountConsumptionNumber.getText().toString().trim(), mChannelListenerimit.getTradingLimitLowY()) == -1) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_input_mount_consumption_below_lower_limit);
            return false;

        } else if (StringUtil.setComparison1(etAmountConsumptionNumber.getText().toString().trim(), mChannelListenerimit.getTradingLimitUpY()) == 1) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_input_mount_consumption_below_upper_limit);
            return false;
        }
        /*是否开通快捷支付*/
//        else if (!HawkUtil.getInstance().getSaveData(CloudstorageKeyConstant.IS_OPEN_FAST_PAYMENT,false)) {
//            HintUtil.showErrorWithToast(mContext, getString(R.string.hint_open_fast_payment));
//            return false;
//        }
        return super.examineRequiredVerification();
    }

    @Override
    protected void startRequestInterface() {
        Bundle bundle = new Bundle();
        bundle.putString(KeyConstant.CHANNEL_ID, swipeChannelEntity.getChannelId());
        bundle.putString(KeyConstant.CREDIT_CARD_ID, selectBankCardLists.getCreditId());
        bundle.putString(KeyConstant.AMOUNT_CONSUMPTION, etAmountConsumptionNumber.getText().toString().trim());
        bundle.putString(KeyConstant.HANDLING_FEE, handlingFee);
        bundle.putString(KeyConstant.LOCATION_INFORMATION, mSubmitLocation);
        jumpBundleActivity(mContext, DeterminingReceiptsActivity.class, bundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            /*选择银行卡*/
            if (requestCode == KeyConstant.SELECT_CREDIT_CARD) {
                llAddCard.setVisibility(View.GONE);
                llLayout.setVisibility(View.VISIBLE);
                selectBankCardLists = (SelectBankCardList) data.getSerializableExtra(mEntity);
                if (selectBankCardLists != null) {
                    if (!TextUtils.isEmpty(selectBankCardLists.getBackgroundColor()) &&
                            selectBankCardLists.getBackgroundColor().startsWith("#")) {
                        llCardBackground.setBackground(LayoutStyleUtil.setShapeDrawable(selectBankCardLists.getBackgroundColor()));
                    }
                    GlideUtils.loadImage(mContext, ivCardLogo, selectBankCardLists.getLogo());
                    GlideUtils.loadImage(mContext, ivCardBlurringLogo, selectBankCardLists.getBankBackground());
                    tvCardName.setText(selectBankCardLists.getBankName());
                    tvCardType.setText(selectBankCardLists.getType());
                    tvCardType2.setVisibility(View.GONE);
                    tvCardNumber.setText(StringUtil.replaceCardNumber(selectBankCardLists.getCarNumber()));
//                    tvCardType2.setText(selectBankCardLists.getCreditAlias());
                    tvUntied.setVisibility(View.GONE);
                    llBillingDate.setVisibility(View.GONE);
                    tvRepayment.setVisibility(View.GONE);
                    /*判断是否选择通道*/
                    if (swipeChannelEntity != null && !TextUtils.isEmpty(swipeChannelEntity.getChannelId())) {
                        if (llAisleInfoLayout.getVisibility() == View.VISIBLE) {
                            /*获取通道id并请求限制限额*/
                            queryBankLimit(swipeChannelEntity.getChannelId());
                        }
                    }
                }
            } else {
                llAddCard.setVisibility(View.VISIBLE);
                llLayout.setVisibility(View.GONE);
            }
        }
    }
}
