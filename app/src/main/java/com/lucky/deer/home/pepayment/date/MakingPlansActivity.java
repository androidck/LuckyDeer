package com.lucky.deer.home.pepayment.date;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.dykj.requestcore.util.RequestUtils;
import com.lucky.mapfeatures.positioning.MapLocationListener;
import com.lucky.mapfeatures.positioning.PositioningFeatures;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.common.WebViewActivity;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.home.pepayment.PlanDetailsActivity;
import com.lucky.deer.util.DateUtil;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.SelectRepaymentDateDialog;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.model.common.WebViewBean;
import com.lucky.model.common.dialog.SelectRepaymentDateEntity;
import com.lucky.model.request.home.repayment.MakingPlansReq;
import com.lucky.model.response.home.pepayment.PepaymentPlanList;
import com.lucky.model.response.selectbankcard.SelectBankCardList;
import com.lucky.model.util.GlideUtils;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import cn.addapp.pickers.picker.DatePicker;

/**
 * 制定计划
 *
 * @author wangxinagyi
 * @date 2018/12/12
 */
public class MakingPlansActivity extends BaseActivity {
    /**
     * 获取当前(制定计划)Activity
     */
    @SuppressLint("StaticFieldLeak")
    public static MakingPlansActivity mMakingPlansActivity;

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
     * 账单日
     */
    @BindView(R.id.tv_billing_day)
    TextView tvBillingDay;
    /**
     * 还款日
     */
    @BindView(R.id.tv_repayment_day)
    TextView tvRepaymentDay;
    /**
     * 还款金额
     */
    @BindView(R.id.et_money)
    EditText etMoney;
    /**
     * 某日还款布局
     */
    @BindView(R.id.rl_repayment_date)
    LinearLayout rlRepaymentDate;
    /**
     * 某日还款
     */
    @BindView(R.id.tv_repayment_number)
    TextView tvRepaymentNumber;
    /**
     * 选择还款日期状态加载框
     */
    @BindView(R.id.pb_progress_bar)
    ProgressBar pbProgressBar;
    /**
     * 选择还款日期状态
     */
    @BindView(R.id.tv_repayment_date)
    TextView tvRepaymentDate;
    /**
     * 增加还款笔数
     */
    @BindView(R.id.btn_add)
    Button btnAdd;
    /**
     * 还款笔数
     */
    @BindView(R.id.tv_number)
    TextView tvNumber;
    /**
     * 减少还款笔数
     */
    @BindView(R.id.btn_reduce)
    Button btnReduce;
    /**
     * 每个月最高还款笔数
     */
    @BindView(R.id.tv_repayments_number_pens)
    TextView tvRepaymentsNumberPens;
    /**
     * 预留额度不低于
     */
    @BindView(R.id.tv_amount_not_lower_than)
    TextView tvAmountNotLowerThan;
    /**
     * 弹出框实例
     */
    private PublicDialog inistanceView;
    /**
     * 弹出dialog
     */
    private QMUIDialog qmuiDialog;
    /**
     * 信用卡信息
     */
    private SelectBankCardList selectBankCardList;
    /**
     * 还款笔数最小值
     */
    private final int min = 1;
    /**
     * 还款笔数最大值
     */
    private final int max = 2;
    /**
     * 设置还款笔数
     */
    private int value;
    /**
     * 账单、还款日<p>
     * billingDay：账单日<p>
     * repaymentDay：还款日
     */
    private String billingDay, repaymentDay;
    /**
     * 选择还款日期
     */
    private String mRepaymentDate;
    /**
     * 提交位置
     */
    private String mSubmitLocation;
    /**
     * 提交计划返回数据
     */
    private PepaymentPlanList data;

    private int isShowLoginDialog;

    private ProgressDialog progressDialog;
    private List<SelectRepaymentDateEntity> mDataAll;

    @Override
    protected int initLayout() {
        return R.layout.activity_making_plans;
    }

    @Override
    protected void initView() {
        mMakingPlansActivity = this;
        topBar.setTitle(R.string.title_activity_making_plans)
                .setTextColor(ContextCompat.getColor(mContext, R.color.white));
        topBar.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
        llTopBarBackground.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
        topBar.addLeftImageButton(R.mipmap.photo_back, 0)
                .setOnClickListener(v ->
                        overridePendingTransition(false, true)
                );
        /*初始化还款金额输入格式*/
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
            if (!TextUtils.isEmpty(selectBankCardList.getBillDate())) {
                /*设置账单日*/
                tvBillingDay.setText(DateUtil.dateConversionFormat(selectBankCardList.getBillDate(), DateUtil.ymd, DateUtil.md));
                billingDay = DateUtil.dateConversionFormat(selectBankCardList.getBillDate(), DateUtil.ymd, DateUtil.ymd);
            }
            if (!TextUtils.isEmpty(selectBankCardList.getRepaymentDate())) {
                /*设置还款日*/
                tvRepaymentDay.setText(DateUtil.dateConversionFormat(selectBankCardList.getRepaymentDate(), DateUtil.ymd, DateUtil.md));
                repaymentDay = DateUtil.dateConversionFormat(selectBankCardList.getRepaymentDate(), DateUtil.ymd, DateUtil.ymd);
            }
            monthlyRepayments(0);
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
                calculateMinimumRepayment(s + "");
            }
        });
        /*弹出框监听*/
        inistanceView.setOnClickListener((OnClickListener<String>) (status, useType, isPhoneNumber, text) -> {
            switch (status) {
                case OK:
                    if (data != null) {
//                        data.setPageMark(KeyConstant.MAKING_PLANS);
//                        data.setCreditId(selectBankCardList.getCreditId());
//                        jumpActivity(mContext, PlanDetailsActivity.class, data);
                        data.setPageMark(KeyConstant.MAKING_PLANS);
                        data.setCreditId(selectBankCardList.getCreditId());
                        data.setChooseRepaymentDate(mRepaymentDate);
                        jumpActivity(mContext, PlanDetailsActivity.class, data);
                    }
                    break;
                default:
            }
            if (qmuiDialog != null && qmuiDialog.isShowing()) {
                qmuiDialog.dismiss();
            }
        });
    }

    @OnClick({R.id.ll_billing_day, R.id.ll_repayment_day, R.id.rl_repayment_date,
            R.id.btn_add, R.id.btn_reduce, R.id.tv_instructions, R.id.tv_billing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*账单日*/
            case R.id.ll_billing_day:
                onOptionPicker(1, billingDay);
                break;
            /*还款日*/
            case R.id.ll_repayment_day:
                onOptionPicker(2, repaymentDay);
                break;
            /*还款日期*/
            case R.id.rl_repayment_date:
//                if (progressDialog == null) {
//                    progressDialog = new ProgressDialog(mContext);
//                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                }
//                progressDialog.setMessage("加载中...");
//                progressDialog.setCancelable(true);
//                progressDialog.show();
                selectRepaymentNumber();
                break;
            /*添加还款笔数*/
            case R.id.btn_add:
                value = Integer.parseInt(tvNumber.getText().toString().trim());
                if (value == max) {
                    btnAdd.setEnabled(false);
                } else {
                    value++;
                    if (value == max) {
                        btnAdd.setEnabled(false);
                    }
                }
                btnReduce.setEnabled(true);
                tvNumber.setText(String.valueOf(value));
                break;
            /*减少还款笔数*/
            case R.id.btn_reduce:
                value = Integer.parseInt(tvNumber.getText().toString().trim());
                if (value == min) {
                    btnReduce.setEnabled(false);
                } else {
                    value--;
                    if (value == min) {
                        btnReduce.setEnabled(false);
                    }
                }
                btnAdd.setEnabled(true);
                tvNumber.setText(String.valueOf(value));
                break;
            /*操作说明*/
            case R.id.tv_instructions:
                WebViewBean webViewBean = new WebViewBean();
                webViewBean.setWebTitle(getString(R.string.instructions));
                webViewBean.setWebUrl(HttpConstant.H5_DATE_REPAYMENT_MAKING_PLANS_INSTRUCTIONS);
                jumpActivity(mActivity, WebViewActivity.class, webViewBean);
                break;
            /*制定账单*/
            case R.id.tv_billing:
                examineRequiredVerification();
                break;
            default:
        }
    }

    @Override
    protected boolean examineRequiredVerification() {
        /*判断是否选择账单日*/
        if (TextUtils.isEmpty(tvBillingDay.getText().toString().trim()) || billingDay.length() == 0) {
            PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), R.string.toast_please_choose_billing_day, getString(R.string.cancel));
//            HintUtil.showErrorWithToast(mContext, R.string.toast_please_choose_billing_day);
            return false;
        }
        /*判断是否选择还款日*/
        else if (TextUtils.isEmpty(tvRepaymentDay.getText().toString().trim()) ||
                repaymentDay.length() == 0) {
            PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), R.string.toast_please_choose_repayment_day, getString(R.string.cancel));
//            HintUtil.showErrorWithToast(mContext, R.string.toast_please_choose_repayment_day);
            return false;
        }
        /*判断是否输入还款金额*/
        else if (TextUtils.isEmpty(etMoney.getText().toString().trim()) || Float.parseFloat(etMoney.getText().toString().trim()) <= 0) {
            PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), R.string.toast_please_enter_repayment_amount, getString(R.string.cancel));
//            HintUtil.showErrorWithToast(mContext, R.string.toast_please_enter_repayment_amount);
            return false;
        }
        /*判断是否选择还款日期*/
        else if (TextUtils.isEmpty(tvRepaymentNumber.getText().toString().trim()) ||
                TextUtils.isEmpty(mRepaymentDate)) {
            PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), R.string.toast_please_choose_repayment_date, getString(R.string.cancel));
//            HintUtil.showErrorWithToast(mContext, R.string.toast_please_choose_repayment_date);
            return false;
        }
        /*计算每天消费金额不能大于2900元*/
        else if (StringUtil.divideCalculation(Float.parseFloat(etMoney.getText().toString().trim()), StringUtil.getSplitNumber(mRepaymentDate, ",")) > 2900) {
//            PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), R.string.toast_daily_repayment_amount_not_greater_than_2900, getString(R.string.cancel));
            PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt),
                    StringUtil.getSplitNumber(mRepaymentDate, ",") +
                            getString(R.string.toast_repayment_amount_not_greater_than) +
                            /*钱数乘以天数在乘以笔数*/
                            (int) StringUtil.multiplicationCalculation(
                                    StringUtil.multiplicationCalculation(2900, StringUtil.getSplitNumber(mRepaymentDate, ",")),
                                    TextUtils.isEmpty(tvNumber.getText().toString().trim()) ? 1 : Integer.parseInt(tvNumber.getText().toString().trim()))
                            + "元",
                    getString(R.string.cancel));
//            HintUtil.showErrorWithToast(mContext, R.string.toast_daily_repayment_amount_not_greater_than_2900);
            return false;
        }
        /*计算每日还款金额不能小于300元*/
        else if (StringUtil.divideCalculation(Float.parseFloat(etMoney.getText().toString().trim()), StringUtil.getSplitNumber(mRepaymentDate, ",")) < 300) {
            PublicDialog.getInstance().setMessageDialog(mContext,
                    getString(R.string.dialog_prompt),
                    StringUtil.getSplitNumber(mRepaymentDate, ",") + getString(R.string.toast_repayment_amount_not_less_than) +
                            /*钱数乘以天数在乘以笔数*/
                            (int) StringUtil.multiplicationCalculation(
                                    /*钱数乘以天数*/
                                    StringUtil.multiplicationCalculation(300, StringUtil.getSplitNumber(mRepaymentDate, ",")),
                                    TextUtils.isEmpty(tvNumber.getText().toString().trim()) ? 1 : Integer.parseInt(tvNumber.getText().toString().trim()))
                            + "元",
                    getString(R.string.cancel));
//            HintUtil.showErrorWithToast(mContext, R.string.toast_daily_repayment_amount_not_less_than_500);
            return false;
        }
        /*判断是否选择笔数*/
        else if (TextUtils.isEmpty(tvNumber.getText().toString().trim()) && Integer.parseInt(tvNumber.getText().toString().trim()) <= 0) {
            PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), R.string.toast_please_choose_daily_repayments, getString(R.string.cancel));
//            HintUtil.showErrorWithToast(mContext, R.string.toast_please_choose_daily_repayments);
            return false;
        }
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        MakingPlansReq makingPlansReq = new MakingPlansReq();
        /*信用卡主键*/
        makingPlansReq.setCreditCardId(selectBankCardList.getCreditId());
        /*账单日*/
        makingPlansReq.setStrBillDate(DateUtil.dateConversionFormat(billingDay, DateUtil.ymd, DateUtil.y_m_d));
        /*还款日*/
        makingPlansReq.setStrRepaymentDate(DateUtil.dateConversionFormat(repaymentDay, DateUtil.ymd, DateUtil.y_m_d));
        /*计划还款金额*/
        makingPlansReq.setPlanRepaymentMoney(etMoney.getText().toString().trim());
        /*还款日期*/
        makingPlansReq.setChooseRepaymentDate(mRepaymentDate);
        /*计划还款笔数*/
        makingPlansReq.setRepaymentNum(((int) StringUtil.multiplicationCalculation(StringUtil.getSplitNumber(mRepaymentDate, ","),
                TextUtils.isEmpty(tvNumber.getText().toString().trim()) ? 1 : Integer.parseInt(tvNumber.getText().toString().trim()))) + "");
        /*用户所在城市，选填*/
        makingPlansReq.setCity(TextUtils.isEmpty(mSubmitLocation) ? "" : mSubmitLocation);
        /*还款类型*/
        makingPlansReq.setRepaymentType("1");
        showLoadingDialog();
        mNetworkRequestInstance.makeBillPlan(makingPlansReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        data = returnData.getData();
                        if ("1".equals(data.getIsWarning())) {
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
                                                    (TextUtils.isEmpty(data.getKeepMoney()) ? "0.00" : data.getKeepMoney()) +
                                                    "元，请仔细核对并保证信用卡中余额充足，否则将造成计划执行失败！！",
                                            R.color.colorRed,
                                            "继续",
                                            true),
                                    KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_3);
//                            data.setPageMark(KeyConstant.MAKING_PLANS);
//                            data.setCreditId(selectBankCardList.getCreditId());
//                            data.setChooseRepaymentDate(makingPlansReq.getChooseRepaymentDate());
//                            jumpActivity(mContext, PlanDetailsActivity.class, data);
                        }
                    } else {
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }


    /**
     * 时间选择器
     *
     * @param type       1：账单日
     *                   2：还款日
     * @param selectDate 选择日期
     */
    public void onOptionPicker(final int type, String selectDate) {
        try {
            String times = DateUtil.getCurrentDate(DateUtil.ymd);
            DatePicker picker = new DatePicker(mActivity);
            picker.setCanLoop(true);
            picker.setWheelModeEnable(true);
            picker.setTopPadding(15);
            /*开始日期*/
//            String rangeStart = DateUtil.dateCalculation(times, 24 * DateUtil.oneDayNumberMilliseconds, DateUtil.ymd, DateUtil.less);
            String rangeStart = DateUtil.dateCalculation(times, 33 * DateUtil.oneDayNumberMilliseconds, DateUtil.ymd, DateUtil.less);
            /*设置列表开始时间*/
            picker.setRangeStart(
                    /*年*/
                    DateUtil.getsingleDate(rangeStart, DateUtil.ymd, DateUtil.yyyy),
                    /*月*/
                    DateUtil.getsingleDate(rangeStart, DateUtil.ymd, DateUtil.MM),
                    /*日*/
                    DateUtil.getsingleDate(rangeStart, DateUtil.ymd, DateUtil.dd));
            /*设置列表结束时间*/
            picker.setRangeEnd(
                    /*年*/
                    DateUtil.getsingleDate(rangeStart, DateUtil.ymd, DateUtil.yyyy) + 5,
                    /*月*/
                    DateUtil.getsingleDate(rangeStart, DateUtil.ymd, DateUtil.MM),
                    /*日*/
                    DateUtil.getsingleDate(rangeStart, DateUtil.ymd, DateUtil.dd));
            if (TextUtils.isEmpty(selectDate)) {
                /*设置当前时间*/
                picker.setSelectedItem(
                        DateUtil.getsingleDate(times, DateUtil.ymd, DateUtil.yyyy),
                        DateUtil.getsingleDate(times, DateUtil.ymd, DateUtil.MM),
                        DateUtil.getsingleDate(times, DateUtil.ymd, DateUtil.dd));
            } else {
                /*设置选择选中的时间*/
                picker.setSelectedItem(
                        DateUtil.getsingleDate(selectDate, DateUtil.ymd, DateUtil.yyyy) <= 0 ? DateUtil.getsingleDate(times, DateUtil.ymd, DateUtil.yyyy) : DateUtil.getsingleDate(selectDate, DateUtil.ymd, DateUtil.yyyy),
                        DateUtil.getsingleDate(selectDate, DateUtil.ymd, DateUtil.MM) <= 0 ? DateUtil.getsingleDate(times, DateUtil.ymd, DateUtil.MM) : DateUtil.getsingleDate(selectDate, DateUtil.ymd, DateUtil.MM),
                        DateUtil.getsingleDate(selectDate, DateUtil.ymd, DateUtil.dd) <= 0 ? DateUtil.getsingleDate(times, DateUtil.ymd, DateUtil.dd) : DateUtil.getsingleDate(selectDate, DateUtil.ymd, DateUtil.dd));
            }
            picker.setWeightEnable(true);
            picker.setLineColor(Color.BLACK);
            picker.setOnDatePickListener((DatePicker.OnYearMonthDayPickListener) (year, month, day) -> {
                if (type == 1) {
                    billingDay = picker.getSelectedYear() + "年" + picker.getSelectedMonth() + "月" + picker.getSelectedDay() + "日";
                    /*判断选择的账单日*/
                    /*判断选择的日是否是今天或当前*/
                    if (DateUtil.compareDate(DateUtil.getCurrentDate(DateUtil.ymd), billingDay, DateUtil.ymd) == -1) {
                        PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), "账单日未到，不可制定计划", getString(R.string.cancel));
//                        HintUtil.showErrorWithToast(mContext, "账单日未到，不可制定计划");
                        StringUtil.clearData(tvBillingDay, tvRepaymentDay, tvRepaymentNumber, tvRepaymentDate);
                        billingDay = "";
                    } else {
                        tvBillingDay.setText(picker.getSelectedMonth() + "月" + picker.getSelectedDay() + "日");
                        /*清空还款日和选择的还款日期*/
                        StringUtil.clearData(tvRepaymentDay, tvRepaymentNumber, tvRepaymentDate);
                    }
                    /*还款日*/
                    repaymentDay = "";
                    /*选择还款日期*/
                    mRepaymentDate = "";
                    mDataAll = null;
                    /*计算每月还款笔数*/
                    monthlyRepayments(0);
                    /*计算预留额度大概多少元*/
                    calculateMinimumRepayment(etMoney.getText().toString().trim());
                } else if (type == 2) {
                    if (TextUtils.isEmpty(tvBillingDay.getText().toString().trim())) {
                        PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), "请选择账单日", getString(R.string.cancel));
//                        HintUtil.showErrorWithToast(mContext, "请选择账单日");
                    } else {
                        repaymentDay = picker.getSelectedYear() + "年" + picker.getSelectedMonth() + "月" + picker.getSelectedDay() + "日";
                        tvRepaymentDay.setText(picker.getSelectedMonth() + "月" + picker.getSelectedDay() + "日");
                        if (!TextUtils.isEmpty(tvRepaymentNumber.getText().toString().trim())) {
                            /*清空还款日期*/
                            StringUtil.clearData(tvRepaymentNumber, tvRepaymentDate);
                            mRepaymentDate = "";
                            mDataAll = null;
                        }
                        selectDateCalculation();
                        /*计算预留额度大概多少元*/
                        calculateMinimumRepayment(etMoney.getText().toString().trim());
                        /*计算每月还款笔数*/
                        monthlyRepayments(0);
                    }
                }
            });
            if (!picker.isShowing()) {
                picker.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 计算时间
     */
    private void selectDateCalculation() {
        if (billingDay == null || billingDay.length() == 0) {
            PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), "请选择您信用卡的养卡开始日期", getString(R.string.cancel));
//            HintUtil.showErrorWithToast(mContext, "请选择您信用卡的账单日");
            return;
        } else if (repaymentDay == null || repaymentDay.length() == 0) {
            PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), "请选择您信用卡的养卡结束日期", getString(R.string.cancel));
//            HintUtil.showErrorWithToast(mContext, "请选择您信用卡的还款日");
            return;
        } else if (DateUtil.compareDate(billingDay, repaymentDay, DateUtil.ymd) != -1) {
            PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), "您选择结束日期应该大于开始日期，请核实~", getString(R.string.cancel));
//            HintUtil.showErrorWithToast(mContext, "您选择还款日应该大于账单日，请核实~");
            StringUtil.clearData(tvRepaymentDay);
            repaymentDay = "";
            return;
        } else if (DateUtil.dateDiff(DateUtil.getCurrentDate(DateUtil.ymd), repaymentDay, DateUtil.ymd) <= 1) {
            PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), "当前日期与结束日期大于等于两天~", getString(R.string.cancel));
//            HintUtil.showErrorWithToast(mContext, "还款日与还款日大于等于两天~");
            StringUtil.clearData(tvRepaymentDay);
            repaymentDay = "";
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat(DateUtil.ymd, Locale.CHINA);
        try {
            Date billdate = format.parse(billingDay);
            Date returndate = format.parse(repaymentDay);
//
//            if ((returndate.getTime() - billdate.getTime()) / (60 * 60 * 1000 * 24) > 25) {
//                PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), "请选择最近账单日/还款日", getString(R.string.cancel));
////                HintUtil.showErrorWithToast(mContext, "请选择最近账单日/还款日");
//                /*清楚日期*/
//                repaymentDay = "";
//                StringUtil.clearData(tvRepaymentDay);
//            } else if ((returndate.getTime() - billdate.getTime()) / (60 * 60 * 1000 * 24) < 15) {
//                PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), "还款日必须大于账单日不少于16天才能进行还款", getString(R.string.cancel));
////                HintUtil.showErrorWithToast(mContext, "还款日必须大于账单日不少于16天才能进行还款");
//                /*清楚日期*/
//                repaymentDay = "";
//                StringUtil.clearData(tvRepaymentDay);
//            }
            if ((returndate.getTime() - billdate.getTime()) / (60 * 60 * 1000 * 24) > 35) {
                PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), "请选择最近开始日期/还款日期", getString(R.string.cancel));
//                HintUtil.showErrorWithToast(mContext, "请选择最近账单日/还款日");
                /*清楚日期*/
                repaymentDay = "";
                StringUtil.clearData(tvRepaymentDay);
            } else if ((returndate.getTime() - billdate.getTime()) / (60 * 60 * 1000 * 24) < 2) {
                PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), "结束日期必须大于开始日期不少于2天才能进行养卡", getString(R.string.cancel));
//                HintUtil.showErrorWithToast(mContext, "还款日必须大于账单日不少于16天才能进行还款");
                /*清楚日期*/
                repaymentDay = "";
                StringUtil.clearData(tvRepaymentDay);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 选择还款号
     */
    public void selectRepaymentNumber() {
        if (TextUtils.isEmpty(etMoney.getText().toString().trim()) ||
                Integer.parseInt(etMoney.getText().toString().trim()) <= 0) {
            PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), "请输入养卡金额", getString(R.string.cancel));
//            HintUtil.showErrorWithToast(mContext, "请输入还款金额");
            return;
        } else if (TextUtils.isEmpty(billingDay) || TextUtils.isEmpty(repaymentDay)) {
            PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), "请选择开始日期或结束日期", getString(R.string.cancel));
//            HintUtil.showErrorWithToast(mContext, "请选择账单日或还款日");
            return;
        } else if (DateUtil.compareDate(billingDay, repaymentDay, DateUtil.ymd) != -1) {
            PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), "结束日期必须大于开始日期", getString(R.string.cancel));
//            HintUtil.showErrorWithToast(mContext, "还款日必须大于账单日");
            return;
        } else if (DateUtil.dateDiff(DateUtil.getCurrentDate(DateUtil.ymd), repaymentDay, DateUtil.ymd) <= 1) {
            PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), "结束日期已到或结束日期已过", getString(R.string.cancel));
//            HintUtil.showErrorWithToast(mContext, "还款日已到或还款日已过");
            return;
        }
        /*获取当前日期*/
        String date = DateUtil.getCurrentDate(DateUtil.ymd);
        /*判断还款日和账单日是否大于25天*/
        if (DateUtil.dateDiff(billingDay, repaymentDay, DateUtil.ymd) > 35) {
            PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), "开始日期和结束日期间隔太长", getString(R.string.cancel));
//            HintUtil.showErrorWithToast(mContext, "账单日和还款日间隔太长");
            return;
        }
        /**/
        if (DateUtil.dateDiff(date, repaymentDay, DateUtil.ymd) > 35) {
            PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), "结束日期和当前时间间隔太长", getString(R.string.cancel));
//            HintUtil.showErrorWithToast(mContext, "还款日和当前时间间隔太长");
            return;
        }
        if (DateUtil.dateDiff(billingDay, date, DateUtil.ymd) > 35) {
            PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), "开始日期和当前时间间隔太长", getString(R.string.cancel));
//            HintUtil.showErrorWithToast(mContext, "账单日和当前时间间隔太长");
            return;
        }
        // showLoadingDialog();

//        rlRepaymentDate.setEnabled(false);
//        SelectplanDialog selectplanDialog = new SelectplanDialog(
//                mActivity,
//                DateUtil.getCurrentDate(DateUtil.dd),
//                DateUtil.dateConversionFormat(repaymentDay, DateUtil.ymd, DateUtil.dd),
//                mRepaymentDate,
//                R.style.ActionSheetDialogStyle,
//                position -> {
//                    if (position != null && position.length() > 0) {
//                        mRepaymentDate = position;
//                        tvRepaymentDate.setText("已选择");
//                        String temp[] = position.split(",");
//                        StringBuffer stringBuffer = new StringBuffer();
//                        for (int i = 0; i < temp.length; i++) {
//                            if (i != 0) {
//                                stringBuffer.append(",");
//                            }
//                            stringBuffer.append(temp[i].substring(8, 10));
//                        }
//                        tvRepaymentNumber.setText(stringBuffer.toString());
//                    } else {
//                        tvRepaymentDate.setText("未选择");
//                        StringUtil.clearData(tvRepaymentNumber);
//                        mRepaymentDate = "";
//                    }
//                    /*计算预留额度大概多少元*/
//                    calculateMinimumRepayment(etMoney.getText().toString().trim());
//                });

        rlRepaymentDate.setEnabled(false);
        SelectRepaymentDateDialog selectplanDialog = new SelectRepaymentDateDialog(
                mActivity,
                repaymentDay,
                mDataAll,
                R.style.ActionSheetDialogStyle,
                (dataAll, selectedData, selectedDay, selectedNumber) -> {
                    mRepaymentDate = selectedData;
                    mDataAll = dataAll;
                    tvRepaymentDate.setText("已选择");
                    tvRepaymentNumber.setText(selectedDay);
                    /*计算预留额度大概多少元*/
                    calculateMinimumRepayment(etMoney.getText().toString().trim());
                    /*计算每月还款笔数*/
                    monthlyRepayments(selectedNumber);
                });
        if (!selectplanDialog.isShowing()) {
            selectplanDialog.show();
        }
//        selectplanDialog.setOnDataLoadingCompletedListener(new SelectplanDialog.OnDataLoadingCompletedListener() {
//            @Override
//            public void onDataLoading(int type) {
//                isShowLoginDialog = type;
//            }
//        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus == false) {
//            cancelProgressDialog();
            rlRepaymentDate.setEnabled(true);
        }
    }

    public void cancelProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    /**
     * 计算备注1：每月还款笔数
     *
     * @param selectedNumber
     */
    @SuppressLint("SetTextI18n")
    public void monthlyRepayments(int selectedNumber) {
        if (!TextUtils.isEmpty(repaymentDay)) {
            /*备注信息1*/
            tvRepaymentsNumberPens.setText(getString(R.string.remarks_repayments_number_pens) + (selectedNumber <= 0 ?
                    (DateUtil.dateDiff(DateUtil.getCurrentDate(DateUtil.ymd), repaymentDay, DateUtil.ymd) <= 0 ?
                            1 : DateUtil.dateDiff(DateUtil.getCurrentDate(DateUtil.ymd), repaymentDay, DateUtil.ymd))
                    : selectedNumber) + "笔");
        } else {
            /*备注信息1*/
            tvRepaymentsNumberPens.setText(getString(R.string.default_remarks_repayments_number_pens));
        }
    }

    /**
     * 计算备注2：预留额度大概
     *
     * @param s
     */
    @SuppressLint("SetTextI18n")
    public void calculateMinimumRepayment(String s) {
        if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(mRepaymentDate)) {
            /*计算每笔金额*/
            float perAmount = StringUtil.divideCalculation(Float.valueOf(s), StringUtil.getSplitNumber(mRepaymentDate, ","));
            /*计算消费金额大概手续费*/
            float probablyHandlingFee = StringUtil.multiplicationCalculation(Float.valueOf(s), Float.valueOf(String.valueOf(0.008)));
            /*备注信息2*/
            tvAmountNotLowerThan.setText(getString(R.string.remarks_amount_not_lower_than) +
                    StringUtil.setNumberFormatting(String.valueOf(StringUtil.additionCalculation(String.valueOf(perAmount), String.valueOf(probablyHandlingFee)))) + "元");
        } else {
            tvAmountNotLowerThan.setText(getString(R.string.remarks_amount_not_lower_than) + "0.00元");
        }
    }
}