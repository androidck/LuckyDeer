package com.lucky.deer.home.pepayment.perfect;

import android.annotation.SuppressLint;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.base.BaseActivity;
import com.lucky.mapfeatures.positioning.MapLocationListener;
import com.lucky.mapfeatures.positioning.PositioningFeatures;
import com.lucky.deer.R;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.util.DateUtil;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.model.request.home.repayment.PerfectMakingPlansReq;
import com.lucky.model.response.home.pepayment.perfect.MakePerfectBillPlanEntity;
import com.lucky.model.response.selectbankcard.SelectBankCardList;
import com.lucky.model.util.GlideUtils;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.lucky.deer.util.DateUtil.getCurrentDate;

/**
 * 完美制定计划页面
 *
 * @author wangxiangyi
 * @date 2019/02/20
 */
public class PerfectMakingPlansActivity extends BaseActivity {
    /**
     *
     */
    @SuppressLint("StaticFieldLeak")
    public static PerfectMakingPlansActivity mPerfectMakingPlansActivity;

    @BindView(R.id.ll_top_bar_background)
    LinearLayout llTopBarBackground;
    /**
     * 标题
     */
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    /**
     * 银行logo
     */
    @BindView(R.id.iv_card_logo)
    ImageView ivCardLogo;
    /**
     * 银行名称和人名
     */
    @BindView(R.id.tv_card_name)
    TextView tvCardName;
    /**
     * 银行卡号
     */
    @BindView(R.id.tv_card_number)
    TextView tvCardNumber;
    /**
     * 开始日
     */
    @BindView(R.id.tv_billing_day)
    TextView tvBillingDay;
    /**
     * 结束日
     */
    @BindView(R.id.tv_repayment_day)
    TextView tvRepaymentDay;
    /**
     * 养卡金额
     */
    @BindView(R.id.et_money)
    EditText etMoney;
    /**
     * 预留金额
     */
    @BindView(R.id.tv_reserved_amount)
    TextView tvReservedAmount;
    @BindView(R.id.ll_repayment_day)
    LinearLayout llRepaymentDay;
    @BindView(R.id.tv_billing)
    TextView tvBilling;
    /**
     * 银行信息
     */
    private SelectBankCardList selectBankCardList;
    /**
     * billingDay：开始日
     * repaymentDay：结束日
     */
    private String billingDay, repaymentDay;
    /**
     * 当前位置
     */
    private String mSubmitLocation;
    /**
     * 在制定计划返回数据
     */
    private MakePerfectBillPlanEntity data;
    /**
     * 自定义dialog初始化
     */
    private PublicDialog inistanceView;
    /**
     * 提示dialog
     */
    private QMUIDialog qmuiDialog;


    @Override
    protected int initLayout() {
        return R.layout.activity_perfect_making_plans;
    }

    @Override
    protected void initView() {
        mPerfectMakingPlansActivity = this;
        topBar.setTitle(R.string.title_activity_perfect_making_plans)
                .setTextColor(ContextCompat.getColor(mContext, R.color.white));
        topBar.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
        llTopBarBackground.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
        topBar.addLeftImageButton(R.mipmap.photo_back, 0)
                .setOnClickListener(v ->
                        overridePendingTransition(false, true)
                );
        /*初始化养卡金额输入格式*/
        StringUtil.restrictionLength(2, etMoney);
        /*获取单利*/
        inistanceView = PublicDialog.getInstance();
    }

    @Override
    protected void initData() {
        if (getSerializableData() != null) {
            selectBankCardList = (SelectBankCardList) getSerializableData();
            GlideUtils.loadImage(mContext, ivCardLogo, selectBankCardList.getBankColorLogo());
            tvCardName.setText(selectBankCardList.getBankName());
            tvCardNumber.setText(StringUtil.replaceCardNumber(selectBankCardList.getCarNumber()));
//            if (!TextUtils.isEmpty(selectBankCardList.getBillDate())) {
//                /*设置开始日*/
//                tvBillingDay.setText(DateUtil.dateConversionFormat(selectBankCardList.getBillDate(), DateUtil.ymd, DateUtil.md));
//                billingDay = DateUtil.dateConversionFormat(selectBankCardList.getBillDate(), DateUtil.ymd, DateUtil.ymd);
//            }
            /*当前日期作为开始日*/
            tvBillingDay.setText(getCurrentDate(DateUtil.md));
            billingDay = getCurrentDate(DateUtil.ymd);
            if (!TextUtils.isEmpty(selectBankCardList.getRepaymentDate())) {
                /*设置结束日*/
                tvRepaymentDay.setText(DateUtil.dateConversionFormat(selectBankCardList.getRepaymentDate(), DateUtil.ymd, DateUtil.md));
                repaymentDay = DateUtil.dateConversionFormat(selectBankCardList.getRepaymentDate(), DateUtil.ymd, DateUtil.ymd);
//                llRepaymentDay.setEnabled(false);
            } else {
                llRepaymentDay.setEnabled(true);
            }
        }
        showLoadingDialog(R.string.dialog_getting_targeting);
        /*提交数据时开启定位*/
        isOpenIntervalPositioning(mContext,
                false,
                2, new MapLocationListener() {
                    @Override
                    public void onLocationSuccess(AMapLocation amapLocation, int scenesUsed) {
                        dismissLoadingDialog();
                        if (!TextUtils.isEmpty(amapLocation.getCountry()) && "中国".equals(amapLocation.getCountry())) {
                            mSubmitLocation = amapLocation.getProvince() +
                                    "," +
                                    amapLocation.getCity() +
                                    "," +
                                    amapLocation.getAdCode();
                        }
                    }

                    @Override
                    public void onLocationFailure() {
                        PositioningFeatures.getInstance(mContext).stopLocation();
                    }
                });

    }

    @Override
    protected void initListener() {
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                /*计算预留额度大概多少元*/
                calculateReservedAmount(s + "");
            }
        });
        /*弹出框监听*/
        inistanceView.setOnClickListener((OnClickListener<String>) (status, useType, isPhoneNumber, text) -> {
            switch (status) {
                case OK:
                    if (data != null) {
                        data.setPageMark(KeyConstant.MAKING_PLANS);
                        data.setCreditId(selectBankCardList.getCreditId());
                        data.setBankLogo(selectBankCardList.getBankColorLogo());
                        jumpActivity(mContext, PerfectPlanDetailsActivity.class, data);
                    }
                    break;
                default:
            }
            if (qmuiDialog != null && qmuiDialog.isShowing()) {
                qmuiDialog.dismiss();
            }
        });
    }

    /**
     * 计算计算预留金额
     */
    private void calculateReservedAmount(String s) {
        if (!TextUtils.isEmpty(s) && Double.parseDouble(s) > 0) {
            /*计算预留金额*/
            float probablyHandlingFee1 = StringUtil.multiplicationCalculation(Float.valueOf(s), Float.valueOf(String.valueOf(0.25)));
            tvReservedAmount.setText(StringUtil.setNumberFormatting(String.valueOf(probablyHandlingFee1)));
        } else {
            tvReservedAmount.setText("");
        }
    }

    @OnClick({R.id.ll_repayment_day, R.id.tv_billing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*结束日*/
            case R.id.ll_repayment_day:
                DateUtil.onOptionPicker(mActivity,
                        DateUtil.dateCalculation(DateUtil.getCurrentDate(DateUtil.ymd), 15 * DateUtil.oneDayNumberMilliseconds, DateUtil.ymd, DateUtil.add),
                        "",
                        (year, month, day) -> {
                            repaymentDay = year + "年" + month + "月" + day + "日";
                            /*设置结束日*/
                            tvRepaymentDay.setText(DateUtil.dateConversionFormat(repaymentDay, DateUtil.ymd, DateUtil.md));
                            repaymentDay = DateUtil.dateConversionFormat(repaymentDay, DateUtil.ymd, DateUtil.ymd);
                        });
                break;
            /*制定按钮*/
            case R.id.tv_billing:
                examineRequiredVerification();
                break;
            default:
        }
    }

    @Override
    protected boolean examineRequiredVerification() {
        if (TextUtils.isEmpty(tvBillingDay.getText().toString().trim()) || TextUtils.isEmpty(billingDay)) {
            HintUtil.showErrorWithToast(mContext, "请选择开始日");
            return false;
        } else if (TextUtils.isEmpty(tvRepaymentDay.getText().toString().trim()) || TextUtils.isEmpty(repaymentDay)) {
            HintUtil.showErrorWithToast(mContext, "请选择结束日");
            return false;
        } else if (DateUtil.dateDiff(billingDay, repaymentDay, DateUtil.ymd) < 15) {
            HintUtil.showErrorWithToast(mContext, "结束日必须大于当前日期不少于16天才能进行完美养卡");
            return false;
        } else if (TextUtils.isEmpty(etMoney.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, "请输入养卡金额");
            return false;
        } else if (Double.valueOf(etMoney.getText().toString().trim()) > 23000) {
            HintUtil.showErrorWithToast(mContext, "养卡金额不能大于23000元");
            return false;
        } else if (Double.valueOf(etMoney.getText().toString().trim()) < 2400) {
            HintUtil.showErrorWithToast(mContext, "养卡金额不能低于2400元");
            return false;
        }
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        PerfectMakingPlansReq perfectMakingPlansReq = new PerfectMakingPlansReq();
        /*信用卡id*/
        perfectMakingPlansReq.setCreditCardId(selectBankCardList.getCreditId());
        /*开始日期年月日*/
        perfectMakingPlansReq.setStrBillDate(DateUtil.dateConversionFormat(billingDay, DateUtil.ymd, DateUtil.y_m_d));
        /*结束日期年月日*/
        perfectMakingPlansReq.setStrRepaymentDate(DateUtil.dateConversionFormat(repaymentDay, DateUtil.ymd, DateUtil.y_m_d));
        List<String> intervalDate = null;
        if (DateUtil.dateDiff(billingDay, repaymentDay, DateUtil.ymd) == 15) {
            /*获取区间时间*/
            intervalDate = DateUtil.getPerDaysByStartAndEndDate(
                    DateUtil.dateCalculation(billingDay, DateUtil.oneDayNumberMilliseconds, DateUtil.ymd, DateUtil.add),
                    repaymentDay, DateUtil.ymd);
        } else if (DateUtil.dateDiff(billingDay, repaymentDay, DateUtil.ymd) >= 16) {
            /*获取区间时间*/
            intervalDate = DateUtil.getPerDaysByStartAndEndDate(
                    DateUtil.dateCalculation(billingDay, DateUtil.oneDayNumberMilliseconds, DateUtil.ymd, DateUtil.add),
                    DateUtil.dateCalculation(billingDay, 16 * DateUtil.oneDayNumberMilliseconds, DateUtil.ymd, DateUtil.add)
                    , DateUtil.ymd);
        }
        StringUtil.dataSorting(intervalDate);
        if (intervalDate != null && intervalDate.size() == 15) {
            intervalDate.add(intervalDate.get(intervalDate.size() - 1));
        }
        StringBuilder stringBuffer = new StringBuilder();
        if (intervalDate != null) {
            for (String s : intervalDate) {
                if (stringBuffer.length() <= 0) {
                    stringBuffer.append(DateUtil.dateConversionFormat(s, DateUtil.ymd, DateUtil.y_m_d));
                } else {
                    stringBuffer.append("," + DateUtil.dateConversionFormat(s, DateUtil.ymd, DateUtil.y_m_d));
                }
            }
        }
        /*结束日期年月日*/
        perfectMakingPlansReq.setChooseRepaymentDate(stringBuffer.toString());
        /*计划养卡金额*/
        perfectMakingPlansReq.setPlanRepaymentMoney(etMoney.getText().toString().trim());
        /*计划养卡笔数*/
        perfectMakingPlansReq.setRepaymentNum("4");
        /*养卡类型*/
        perfectMakingPlansReq.setRepaymentType("2");
        /*用户所在城市，选填*/
        perfectMakingPlansReq.setCity(TextUtils.isEmpty(mSubmitLocation) ? "" : mSubmitLocation);
        showLoadingDialog();
        mNetworkRequestInstance.makePerfectBillPlan(perfectMakingPlansReq)
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        data = returnData.getData();
                        if ("1".equals(data.isWarning())) {
                            qmuiDialog = inistanceView.setCustomizeView(
                                    inistanceView.initTitleEtOrTvView(
                                            mActivity,
                                            "制定计划",
                                            R.color.colorBlack,
                                            StringUtil.setTextIndentation(data.getWarningContent()),
                                            R.color.colorRed,
                                            true,
                                            true),
                                    KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_3);
                        } else {
                            qmuiDialog = inistanceView.setCustomizeView(
                                    inistanceView.initTitleEtOrTvView(
                                            mActivity,
                                            "是否继续制定计划",
                                            R.color.colorBlack,
                                            "本计划所需您信用卡的预留金额为" +
                                                    (TextUtils.isEmpty(data.getActualRepaymentMoney()) ? "0.00" : data.getActualRepaymentMoney()) +
                                                    "元，请仔细核对并保证信用卡中余额充足，否则将造成计划执行失败！！",
                                            R.color.colorRed,
                                            "继续",
                                            true),
                                    KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_3);
                        }
                    } else {
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }
}