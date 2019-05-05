package com.lucky.deer.home.pepayment.perfect;

import android.annotation.SuppressLint;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.google.gson.Gson;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.common.WebViewActivity;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.home.cardspending.SelectBankCardActivity;
import com.lucky.deer.home.pepayment.perfect.adapter.PerfectPlanDetailsAdapter;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.enums.ExecutionStatusEnum;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.model.common.WebViewBean;
import com.lucky.model.request.home.repayment.OpenCardConfirmReq;
import com.lucky.model.request.home.repayment.OpenCardReq;
import com.lucky.model.request.home.repayment.TerminationPlanReq;
import com.lucky.model.request.home.repayment.perfect.PerfectRepaymentListReq;
import com.lucky.model.response.home.pepayment.perfect.MakePerfectBillPlanEntity;
import com.lucky.model.response.home.pepayment.perfect.PerfectBillPlanListEntity;
import com.lucky.model.response.home.pepayment.perfect.PrefectpayInfo;
import com.lucky.model.util.GlideUtils;
import com.lucky.model.util.HintUtil;
import com.lucky.model.util.ListUtils;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import xyz.yhsj.loadstatusview.LoadStatusView;

import static java.lang.String.format;

/**
 * 完美计划详情
 *
 * @author wangxiangyi
 * @date 2019/02/21
 */
public class PerfectPlanDetailsActivity extends BaseActivity {
    /**
     * 标题布局
     */
    @BindView(R.id.ll_top_bar_background)
    LinearLayout llTopBarBackground;
    /**
     * 标题
     */
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    /**
     * 下拉刷新
     */
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    /**
     * 空白控件
     */
    @BindView(R.id.lsv_load_status)
    LoadStatusView lsvLoadStatus;
    /**
     * 列表
     */
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private PerfectPlanDetailsAdapter mAdapter;

    /**
     * 计划编号
     */
    TextView tvPlanNumber;
    /**
     * 计划状态
     */
    ImageView ivExecutionStatus;
    /**
     * 银行卡号
     */
    TextView tvBankCardNumber;
    /**
     * 银行卡名称
     */
    TextView tvBankName;
    /**
     * 银行卡图标
     */
    ImageView ivBankLogo;
    /**
     * 需预留金额
     */
    TextView tvReserveAmountRequired;
    /**
     * 预养卡金额
     */
    TextView tvPrepaymentAmount;
    /**
     * 终止计划按钮
     */
    TextView tvStopPlanning;

    private MakePerfectBillPlanEntity mPepaymentPlanData;
    /**
     * 获取对话框单实例
     */
    private PublicDialog inistanceView;
    /**
     * creditId：信用卡id<P>
     * channelId：通道id<P>
     * planId：执行提交计划id
     */
    private String creditId, channelId, planId;
    /**
     * 自定义dialog对话框
     */
    private QMUIDialog qmuiDialog;
    /**
     * 短信流水号
     */
    private String tradeNo;

    @Override
    protected int initLayout() {
        return R.layout.activity_perfect_plan_details;
    }

    @Override
    protected void initView() {
        topBar.setTitle(R.string.title_activity_perfect_plan_details)
                .setTextColor(ContextCompat.getColor(mContext, R.color.white));
        topBar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_80_blue));
        llTopBarBackground.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_80_blue));
        topBar.addLeftImageButton(R.mipmap.photo_back, 0)
                .setOnClickListener(v ->
                        overridePendingTransition(false, true)
                );
        /*初始化适配器*/
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new PerfectPlanDetailsAdapter();
        rvList.setAdapter(mAdapter);
        if (getSerializableData() != null && getSerializableData() instanceof MakePerfectBillPlanEntity) {
            mPepaymentPlanData = (MakePerfectBillPlanEntity) getSerializableData();
            /*计划id*/
            planId = mPepaymentPlanData.getId();
            Logger.i("执行计划====" + new Gson().toJson(mPepaymentPlanData));
        }
        /*获取单利*/
        inistanceView = PublicDialog.getInstance();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        /*判断是否是日期养卡：制定计划页面*/
        if (!srlRefresh.isRefreshing() && mPepaymentPlanData != null && KeyConstant.MAKING_PLANS.equals(mPepaymentPlanData.getPageMark())) {
            setAdapterData(mPepaymentPlanData.getConsumptionList(), mPepaymentPlanData.getRepaymentList());
            srlRefresh.setEnabled(false);
            tvStopPlanning.setVisibility(View.GONE);
        } else {
            if (!srlRefresh.isRefreshing()) {
                showLoadingDialog();
                srlRefresh.setEnabled(true);
            }
            PerfectRepaymentListReq perfectRepaymentListReq = new PerfectRepaymentListReq();
            perfectRepaymentListReq.setBillPlanId(mPepaymentPlanData.getId());
            mNetworkRequestInstance.queryPerfectPlanDetail(perfectRepaymentListReq)
                    .compose(bindToLifecycle())
                    .subscribe(returnData -> {
                        dismissLoadingDialog();
                        srlRefresh.setRefreshing(false);
                        if (RequestUtils.isRequestSuccess(returnData)) {
                            /*计划id*/
                            planId = returnData.getData().getId();
                            setAdapterData(returnData.getData().getConsumptionList(), returnData.getData().getRepaymentList());
                        } else {
                            HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                        }
                    });
        }
    }

    @Override
    protected void initListener() {
        /*下拉刷新*/
        srlRefresh.setOnRefreshListener(this::initData);
        /*滚动监听*/
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);
                StringUtil.setViewTransparency(
                        rv.computeVerticalScrollOffset(),
                        topBar,
                        topBar,
                        llTopBarBackground);
            }
        });
        inistanceView.setOnClickListener((OnClickListener<String>) (status, useType, isPhoneNumber, text) -> {
            if (qmuiDialog != null) {
                switch (status) {
                    case OK:
                        if (KeyConstant.TYPE_VERIFICATION_CODE.equals(useType)) {
                            /*提交验证码*/
                            submitVerificationCode(text);
                        } else if (KeyConstant.TYPE_TERMINATION_PLAN.equals(useType)) {
                            /*终止计划*/
                            terminationPlan(planId);
                        } else if (KeyConstant.TYPE_SUBMIT_PLAN.equals(useType)) {
                            /*提交计划*/
                            submitPlan();
                        }
                        break;
                    default:
                }
                qmuiDialog.dismiss();
                qmuiDialog = null;
            }
        });
    }

    /**
     * 重组数据
     *
     * @param consumptionList 消费清单
     * @param repaymentList   养卡清单
     */
    private void setAdapterData(List<PerfectBillPlanListEntity> consumptionList, List<PerfectBillPlanListEntity> repaymentList) {
        initHeaderLayout();
        /*拆分消费清单：六个为一组*/
        List<List<PerfectBillPlanListEntity>> averageScoreData = ListUtils.averageAssign(consumptionList, 6);
        /*十六组信息列表*/
        List<List<PrefectpayInfo>> restructuringDataList = new ArrayList<>();
        for (int a = 0; a < averageScoreData.size(); a++) {
            /*每一组消费、养卡信息*/
            List<PrefectpayInfo> restructuringData = new ArrayList<>();
            PrefectpayInfo prefectpayInfo = null;
            for (int i = 1; i <= averageScoreData.get(a).size(); i++) {
                if (prefectpayInfo == null) {
                    prefectpayInfo = new PrefectpayInfo(0);
                    /*设置上午消费信息*/
                    prefectpayInfo.setAmTime(averageScoreData.get(a).get(i - 1).getExecutionTime());
                    prefectpayInfo.setAmPaymoney(averageScoreData.get(a).get(i - 1).getMoney());
                    prefectpayInfo.setAmPaymoneyStatus(averageScoreData.get(a).get(i - 1).getExecutionStatus());
                    prefectpayInfo.setExceptionDescribe(averageScoreData.get(a).get(i - 1).getExceptionDescribe());
                } else {
                    /*设置下午消费信息*/
                    prefectpayInfo.setPmTime(averageScoreData.get(a).get(i - 1).getExecutionTime());
                    prefectpayInfo.setPmPaymoney(averageScoreData.get(a).get(i - 1).getMoney());
                    prefectpayInfo.setPmPaymoneyStatus(averageScoreData.get(a).get(i - 1).getExecutionStatus());
                    prefectpayInfo.setExceptionDescribe(averageScoreData.get(a).get(i - 1).getExceptionDescribe());
                    /*添加一天消费信息*/
                    restructuringData.add(prefectpayInfo);
                    prefectpayInfo = null;
                }
            }
            /*添加养卡信息*/
            restructuringData.add(new PrefectpayInfo(1,
                    repaymentList.get(a).getExecutionTime(),
                    repaymentList.get(a).getMoney(),
                    repaymentList.get(a).getExecutionStatus(),
                    repaymentList.get(a).getExceptionDescribe())
            );
            restructuringDataList.add(restructuringData);
        }
        mAdapter.setNewData(restructuringDataList);
    }


    /**
     * 初始化头部布局
     */
    private void initHeaderLayout() {
        /*获取计划详情信息头部*/
        View headerView = LinearLayout.inflate(mContext, R.layout.activity_header_perfect_plan_bill, null);
        getHeaderViewId(headerView);
        initHeaderListener();
        setHeaderData();
        if (!mAdapter.isHeaderViewAsFlow()) {
            mAdapter.removeAllHeaderView();
        }
        mAdapter.addHeaderView(headerView);
    }

    /**
     * 初始化header控件
     */
    private void getHeaderViewId(View view) {
        /*计划编号*/
        tvPlanNumber = view.findViewById(R.id.tv_plan_number);
        /*计划状态*/
        ivExecutionStatus = view.findViewById(R.id.iv_execution_status);
        /*银行卡号*/
        tvBankCardNumber = view.findViewById(R.id.tv_bank_card_number);
        /*银行卡名称*/
        tvBankName = view.findViewById(R.id.tv_bank_name);
        /*银行卡图标*/
        ivBankLogo = view.findViewById(R.id.iv_bank_logo);
        /*需预留金额*/
        tvReserveAmountRequired = view.findViewById(R.id.tv_reserve_amount_required);
        /*预养卡金额*/
        tvPrepaymentAmount = view.findViewById(R.id.tv_prepayment_amount);
        /*终止计划*/
        tvStopPlanning = view.findViewById(R.id.tv_stop_planning);
    }

    /**
     * 初始化header监听
     */
    private void initHeaderListener() {
        tvStopPlanning.setOnClickListener(v -> {
            qmuiDialog = inistanceView.setCustomizeView(
                    inistanceView.initTitleEtOrTvView(
                            mActivity, getString(R.string.dialog_prompt), "是否终止计划",
                            KeyConstant.TYPE_TERMINATION_PLAN,
                            true),
                    KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_4);
        });
    }

    /**
     * 设置Header数据
     */
    @SuppressLint("StringFormatInvalid")
    private void setHeaderData() {
        if (mPepaymentPlanData != null) {
            if (mPepaymentPlanData != null && KeyConstant.MAKING_PLANS.equals(mPepaymentPlanData.getPageMark())) {
                /*获取信用卡id*/
                creditId = mPepaymentPlanData.getCreditId();
                channelId = mPepaymentPlanData.getChannelId();
                initFooterLayout();
            }
            /*计划编号*/
            tvPlanNumber.setText(format(getString(R.string.plan_number), mPepaymentPlanData.getPlanNo()));
            /*执行状态*/
            ivExecutionStatus.setImageResource(ExecutionStatusEnum.getSearchStatusStyle(ExecutionStatusEnum.implementation_plan, mPepaymentPlanData.getStatus()));
            /*银行卡号*/
            tvBankCardNumber.setText(StringUtil.replaceCardNumber(mPepaymentPlanData.getCarNumber()));
            /*银行卡名称*/
            tvBankName.setText(mPepaymentPlanData.getBankName());
            /*设置银行logo*/
            GlideUtils.loadImage(mContext, ivBankLogo, mPepaymentPlanData.getBankLogo());
            /*需预留金额*/
            tvReserveAmountRequired.setText(StringUtil.setNumberFormatting(mPepaymentPlanData.getActualRepaymentMoney()));
            /*预养卡金额*/
            tvPrepaymentAmount.setText(StringUtil.setNumberFormatting(mPepaymentPlanData.getPlanRepaymentMoney()));
            /*判断是否显示终止计划按钮*/
            if ("gray".equals(ExecutionStatusEnum.getSearchStatusColor(ExecutionStatusEnum.implementation_plan, mPepaymentPlanData.getStatus())) ||
                    "green".equals(ExecutionStatusEnum.getSearchStatusColor(ExecutionStatusEnum.implementation_plan, mPepaymentPlanData.getStatus())) ||
                    "red".equals(ExecutionStatusEnum.getSearchStatusColor(ExecutionStatusEnum.implementation_plan, mPepaymentPlanData.getStatus()))) {
                /*隐藏终止按钮*/
                tvStopPlanning.setVisibility(View.GONE);
            } else {
                /*判断不等于制定计划时*/
                if (!KeyConstant.MAKING_PLANS.equals(mPepaymentPlanData.getPageMark())) {
                    /*显示终止按钮*/
                    tvStopPlanning.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 添加按钮
     */
    private void initFooterLayout() {
        View footerView = LinearLayout.inflate(mContext, R.layout.activity_footer_plan_bill, null);
        footerView.findViewById(R.id.ll_background).setBackgroundColor(getResources().getColor(R.color.white));
        /*重置制定*/
        footerView.findViewById(R.id.tv_enactment)
                .setOnClickListener(v -> {
                    overridePendingTransition(false, true);
                });
        /*提交账单*/
        footerView.findViewById(R.id.tv_submit_bill)
                .setOnClickListener(v -> {
                    examineRequiredVerification();
                });
        if (!mAdapter.isFooterViewAsFlow()) {
            mAdapter.removeAllFooterView();
        }
        mAdapter.addFooterView(footerView);
    }

    @Override
    protected boolean examineRequiredVerification() {
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        OpenCardReq openCardReq = new OpenCardReq();
        /*信用卡ID*/
        openCardReq.setCreditId(creditId);
        /*通道ID*/
        openCardReq.setChannelId(channelId);
        showLoadingDialog();
        /*开卡接口*/
        mNetworkRequestInstance.openCard(openCardReq)
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        if (returnData.getData() != null && !"".equals(returnData.getData())) {
                            /*判断是否开卡*/
                            if ("0000".equals(returnData.getData().getTrxstatus())) {
                                /*开卡成功*/
                                /*确认信息弹窗*/
                                qmuiDialog = inistanceView.setCustomizeView(
                                        inistanceView.initTitleEtOrTvView(
                                                mContext,
                                                "提示",
                                                "该帐单将在明天执行是否确认？",
                                                KeyConstant.TYPE_SUBMIT_PLAN,
                                                true),
                                        KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_4);
                            } else if ("T".equals(returnData.getData().getStatus())) {
                                /*判断是否是跳转网页开卡*/
                                if (!TextUtils.isEmpty(returnData.getData().getHtml())) {
                                    /*跳转网页开发*/
                                    WebViewBean webViewBean = new WebViewBean();
                                    webViewBean.setWebHtml(returnData.getData().getHtml());
                                    jumpActivity(mContext, WebViewActivity.class, webViewBean);
                                } else if (!TextUtils.isEmpty(returnData.getData().getTradeNo())) {
                                    /*短信流水号*/
                                    tradeNo = returnData.getData().getTradeNo();
                                    /*输入验证码弹出框*/
                                    qmuiDialog = inistanceView.setCustomizeView(
                                            inistanceView.initVerificationCodeView(mContext,
                                                    "正在绑定银行卡",
                                                    "验证码",
                                                    KeyConstant.TYPE_VERIFICATION_CODE),
                                            KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_4);
                                }
                            } else {
                                HintUtil.showErrorWithToast(mContext, returnData.getData().getMessage());
                            }
                        }
                    } else {
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }

    /**
     * 提交验证码
     *
     * @param text 输入的验证码
     */
    @SuppressLint("CheckResult")
    private void submitVerificationCode(String text) {
        OpenCardConfirmReq openCardConfirmReq = new OpenCardConfirmReq();
        /*信用卡ID*/
        openCardConfirmReq.setCreditId(creditId);
        /*通道ID*/
        openCardConfirmReq.setChannelId(channelId);
        /*短信流水号*/
        openCardConfirmReq.setTradeNo(tradeNo);
        /*短信验证码*/
        openCardConfirmReq.setSmscode(text);
        showLoadingDialog();
        /*提交验证码接口*/
        mNetworkRequestInstance.openCardConfirm(openCardConfirmReq)
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        /*判断*/
                        if ("0000".equals(returnData.getData().getResCode())) {
                            submitPlan();
                        } else {
                            HintUtil.showErrorWithToast(mContext, returnData.getData().getResMsg());
                        }
                    } else {
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }

    /**
     * 提交计划
     */
    @SuppressLint("CheckResult")
    private void submitPlan() {
        OpenCardReq openCardReq = new OpenCardReq();
        openCardReq.setPlanId(planId);
        showLoadingDialog();
        /*提交制定账单*/
        mNetworkRequestInstance.submitPlan(openCardReq)
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        showSuccessDialog(R.string.dialog_successful_planning, KeyConstant.TYPE_SUBMIT_PLAN);
                    } else {
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }

    /**
     * 终止计划账单接口
     *
     * @param id
     */
    @SuppressLint("CheckResult")
    public void terminationPlan(String id) {
        TerminationPlanReq terminationPlanReq = new TerminationPlanReq();
        terminationPlanReq.setBillPlanId(id);
        showLoadingDialog();
        /*终止计划账单*/
        mNetworkRequestInstance.closeBillPlan(terminationPlanReq)
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        showSuccessDialog(R.string.dialog_termination_plan, KeyConstant.TYPE_TERMINATION_PLAN);
                    } else {
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                    }
                });
    }

    /**
     * 设置要执行的信息
     *
     * @param executionParam 参数
     */
    @Override
    public <T> void carriedOutMethod(T executionParam) {
        if (KeyConstant.TYPE_TERMINATION_PLAN.equals(executionParam)) {
            /*完美计划列表*/
            PerfectRepaymentActivity.mPerfectRepaymentActivity.finish();
            jumpActivity(mContext, PerfectRepaymentActivity.class, true);
        } else {
            /*完美计划列表*/
            PerfectRepaymentActivity.mPerfectRepaymentActivity.finish();
            /*选择银行卡*/
            SelectBankCardActivity.mSelectBankCardActivity.finish();
            /*制定计划*/
            PerfectMakingPlansActivity.mPerfectMakingPlansActivity.finish();
            jumpActivity(mContext, PerfectRepaymentActivity.class, true);
        }
    }
}
