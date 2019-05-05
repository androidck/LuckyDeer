package com.lucky.deer.home.pepayment.date;

import android.annotation.SuppressLint;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.google.gson.Gson;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.common.WebViewActivity;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.home.cardspending.SelectBankCardActivity;
import com.lucky.deer.home.pepayment.adapter.PlanDetailsAdapter;
import com.lucky.deer.util.ProgressBarUtil;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.enums.ExecutionStatusEnum;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.model.common.WebViewBean;
import com.lucky.model.request.home.repayment.OpenCardConfirmReq;
import com.lucky.model.request.home.repayment.OpenCardReq;
import com.lucky.model.request.home.repayment.TerminationPlanReq;
import com.lucky.model.response.home.pepayment.HistoryPlanList;
import com.lucky.model.response.home.pepayment.PepaymentPlanList;
import com.lucky.model.response.home.pepayment.PlanDetailsEntity;
import com.lucky.model.util.GlideUtils;
import com.lucky.model.util.HintUtil;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import xyz.yhsj.loadstatusview.LoadStatusView;

/**
 * 计划详情
 *
 * @author wangxiangyi
 * @date 2018/12/14
 */
public class PlanDetailsActivity extends BaseActivity {
    /**
     * 标题背景
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
     * 执行列表空白页
     */
    @BindView(R.id.lsv_load_status)
    LoadStatusView lsvLoadStatus;
    /**
     * 执行列表
     */
    @BindView(R.id.rv_list)
    SwipeRecyclerView rvList;
//    /**
//     * 按钮布局
//     */
//    @BindView(R.id.ll_btn_submit_bill)
//    LinearLayout llBtnSubmitBill;
//    /**
//     * 重新制定
//     */
//    @BindView(R.id.tv_enactment)
//    TextView tvEnactment;
//    /**
//     * 提交账单
//     */
//    @BindView(R.id.tv_submit_bill)
//    TextView tvSubmitBill;
    /**
     * 创建时间
     */
    TextView tvCreateDate;
    /**
     * 计划日期
     */
    TextView tvPlannedDate;
    /**
     * 执行状态
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
     * 银行卡logo
     */
    ImageView ivBankLogo;
    /**
     * 开始日
     */
    TextView tvBillingDay;
    /**
     * 结束日
     */
    TextView tvRepaymentDay;
    /**
     * 终止计划
     */
    TextView tvStopPlanning;
    /**
     * 养卡笔数
     */
    TextView tvRepayments;
    /**
     * 需预留金额（标题）
     */
    TextView tvTitleReserveAmountRequired;
    /**
     * 需预留金额
     */
    TextView tvReserveAmountRequired;
    /**
     * 预养卡金额
     */
    TextView tvPrepaymentAmount;
//    /**
//     * 正常进度条
//     */
//    QMUIProgressBar apbSchedule;
//    /**
//     * 异常进度条
//     */
//    QMUIProgressBar apbAbnormalSchedule;
    /**
     * 进度
     */
    ProgressBar pbProgressBar;
    /**
     * 养卡笔数
     */
    TextView tvNumberRemaining;

//    /**
//     * 银行logo
//     */
//    ImageView ivCardLogo;
//    /**
//     * 银行卡名称
//     */
//    TextView tvCardName;
//    /**
//     * 银行卡号
//     */
//    TextView tvCardNumber;
//    /**
//     * 银行卡名称
//     */
//    TextView tvBankName;
//    /**
//     * 银行卡尾号
//     */
//    TextView tvTailNumber;
//    /**
//     * 执行状态
//     */
//    TextView tvExecutionStatus;
//    /**
//     * 养卡类型
//     */
//    TextView tvRepaymentType;
//    /**
//     * 开始时间
//     */
//    TextView tvStartDate;
//    /**
//     * 结束时间
//     */
//    TextView tvEndDate;
//    /**
//     * 消费金额
//     */
//    TextView tvActualAmount;
//    /**
//     * 手续费
//     */
//    TextView tvHandlingFee;

    private PlanDetailsAdapter mAdapter;
    /**
     * 执行计划数据
     */
    private PepaymentPlanList mPepaymentPlanData;
    /**
     * 历史计划数据
     */
    private HistoryPlanList mHistoryPlanData;
    /**
     * creditId：银行卡id<p>
     * channelId ：通道id<p>
     * planId ：计划id
     */
    private String creditId, channelId, planId;
    private PublicDialog inistanceView;
    private QMUIDialog qmuiDialog;
    /**
     * 开始时间
     */
    private String startDate;
    /**
     * 短信流水号
     */
    private String tradeNo;


    @Override
    protected int initLayout() {
        return R.layout.activity_plan_bill;
    }

    @Override
    protected void initView() {
        topBar.setTitle(R.string.title_activity_plan_details)
                .setTextColor(ContextCompat.getColor(mContext, R.color.white));
        topBar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_80_blue));
        llTopBarBackground.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_80_blue));
        topBar.addLeftImageButton(R.mipmap.photo_back, 0)
                .setOnClickListener(v ->
                        overridePendingTransition(false, true)
                );
        if (getSerializableData() != null) {
            /*执行计划*/
            if (getSerializableData() instanceof PepaymentPlanList) {
                mPepaymentPlanData = (PepaymentPlanList) getSerializableData();
                Logger.i("执行计划====" + new Gson().toJson(mPepaymentPlanData));
                /*计划id*/
                planId = mPepaymentPlanData.getId();
            }
            /*历史计划*/
            else if (getSerializableData() instanceof HistoryPlanList) {
                mHistoryPlanData = (HistoryPlanList) getSerializableData();
                /*计划id*/
                planId = mHistoryPlanData.getId();
                Logger.i("历史计划====" + new Gson().toJson(mHistoryPlanData));
            }
        }

        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new PlanDetailsAdapter(new ArrayList<>());
        rvList.setAdapter(mAdapter);
        /*设置列表分割线*/
//        rvList.addItemDecoration(new RecycleViewDivider(mContext, DividerItemDecoration.VERTICAL, 10, R.color.color_50_Red));
        /*获取单利*/
        inistanceView = PublicDialog.getInstance();
    }


    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        /*判断是否是日期养卡：制定计划页面*/
        if (!srlRefresh.isRefreshing() && mPepaymentPlanData != null && KeyConstant.MAKING_PLANS.equals(mPepaymentPlanData.getPageMark())) {
            setAdapterData(mPepaymentPlanData.getList());
        } else {
            if (!srlRefresh.isRefreshing()) {
                showLoadingDialog();
            }
            OpenCardReq openCardReq = new OpenCardReq();
            openCardReq.setPlanId(planId);
            /*获取计划列表*/
            mNetworkRequestInstance.billDetails(openCardReq)
                    .compose(bindToLifecycle())
                    .subscribe(returnData -> {
                        dismissLoadingDialog();
                        srlRefresh.setRefreshing(false);
                        if (RequestUtils.isRequestSuccess(returnData)) {
                            if (returnData.getData() != null && returnData.getData().size() > 0) {
                                setAdapterData(returnData.getData());
                            }
                        } else if (RequestUtils.isEmpty(returnData)) {
                        } else {
                            HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                        }
                    });
        }
    }

    /**
     * 设置适配器数据
     *
     * @param data
     */
    public void setAdapterData(List<PlanDetailsEntity> data) {
        initHeaderLayout();
        if (data != null && data.size() > 0) {
            List<PlanDetailsEntity> myData = new ArrayList<>();
            for (PlanDetailsEntity planDetailsDate : data) {
                myData.add(new PlanDetailsEntity(true, null, planDetailsDate));
                if (planDetailsDate.getConsume() != null && planDetailsDate.getConsume().size() > 0) {
                    for (PlanDetailsEntity consumeDate : planDetailsDate.getConsume()) {
                        PlanDetailsEntity consumeEntity = new PlanDetailsEntity(false, null, consumeDate);
                        /*总条数*/
                        consumeEntity.setLastOne(planDetailsDate.getConsume().size() + 1);
                        myData.add(consumeEntity);
                    }
                }
            }
            mAdapter.setNewData(myData);
        }
    }

    @Override
    protected void initListener() {
        /*下拉刷新*/
        srlRefresh.setOnRefreshListener(this::initData);
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

//        /*滚动监听*/
//        rvList.addOnScrollListener(new OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                /*获取LayoutManager*/
//                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//                //经过测试LinearLayoutManager和GridLayoutManager有以下的方法,这里只针对LinearLayoutManager
//                if (manager instanceof LinearLayoutManager) {
//                    //经测试第一个完整的可见的item位置，若为0则是最上方那个;在item超过屏幕高度的时候只有第一个item出现的时候为0 ，其他时候会是一个负的值
//                    //此方法常用作判断是否能下拉刷新，来解决滑动冲突
//                    int findFirstCompletelyVisibleItemPosition = ((LinearLayoutManager) manager).findFirstCompletelyVisibleItemPosition();
//                    //最后一个完整的可见的item位置
//                    int findLastCompletelyVisibleItemPosition = ((LinearLayoutManager) manager).findLastCompletelyVisibleItemPosition();
//                    //第一个可见的位置
//                    int findFirstVisibleItemPosition = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
//                    //最后一个可见的位置
//                    int findLastVisibleItemPosition = ((LinearLayoutManager) manager).findLastVisibleItemPosition();
//                    //如果有滑动冲突--可以用以下方法解决(如果可见位置是position==0的话才能有下拉刷新否则禁掉)
//                    srlRefresh.setEnabled(findFirstCompletelyVisibleItemPosition == 0);
//                }
//            }
//        });
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
     * 初始化头部布局
     */
    private void initHeaderLayout() {
        /*获取计划详情信息头部*/
        View headerView = LinearLayout.inflate(mContext, R.layout.activity_header_plan_bill, null);
        getHeaderViewId(headerView);
        initHeaderListener();
        setData();
        if (!mAdapter.isHeaderViewAsFlow()) {
            mAdapter.removeAllHeaderView();
        }
        mAdapter.addHeaderView(headerView);
    }

    /**
     * 添加按钮
     */
    private void initFooterLayout() {
        View footerView = LinearLayout.inflate(mContext, R.layout.activity_footer_plan_bill, null);
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

    /**
     * 获取标题布局id
     *
     * @param headerView
     */
    private void getHeaderViewId(View headerView) {
        /*创建时间*/
        tvCreateDate = headerView.findViewById(R.id.tv_create_date);
        /*计划日期*/
        tvPlannedDate = headerView.findViewById(R.id.tv_planned_date);
        /*执行状态*/
        ivExecutionStatus = headerView.findViewById(R.id.iv_execution_status);
        /*银行卡号*/
        tvBankCardNumber = headerView.findViewById(R.id.tv_bank_card_number);
        /*银行卡名称*/
        tvBankName = headerView.findViewById(R.id.tv_bank_name);
        /*银行卡logo*/
        ivBankLogo = headerView.findViewById(R.id.iv_bank_logo);
        /*开始日*/
        tvBillingDay = headerView.findViewById(R.id.tv_billing_day);
        /*结束日*/
        tvRepaymentDay = headerView.findViewById(R.id.tv_repayment_day);
        /*终止计划*/
        tvStopPlanning = headerView.findViewById(R.id.tv_stop_planning);
        /*养卡笔数*/
        tvRepayments = headerView.findViewById(R.id.tv_repayments);
        /*需预留金额（标题）*/
        tvTitleReserveAmountRequired = headerView.findViewById(R.id.tv_title_reserve_amount_required);
        /*需预留金额*/
        tvReserveAmountRequired = headerView.findViewById(R.id.tv_reserve_amount_required);
        /*预养卡金额*/
        tvPrepaymentAmount = headerView.findViewById(R.id.tv_prepayment_amount);
//        /*正常进度条*/
//        apbSchedule = headerView.findViewById(R.id.apb_schedule);
//        /*异常进度条*/
//        apbAbnormalSchedule = headerView.findViewById(R.id.apb_abnormal_schedule);
        pbProgressBar = headerView.findViewById(R.id.pb_progress_bar);
        /*养卡笔数*/
        tvNumberRemaining = headerView.findViewById(R.id.tv_number_remaining);
    }

    /**
     * 初始化headr监听
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
     * 设置页面传值
     */
    @SuppressLint("SetTextI18n")
    public void setData() {
        /*执行计划*/
        if (mPepaymentPlanData != null) {
            /*判断是否是日期养卡：制定计划页面*/
            if (KeyConstant.MAKING_PLANS.equals(mPepaymentPlanData.getPageMark())) {
                /*获取信用卡id*/
                creditId = mPepaymentPlanData.getCreditId();
                channelId = mPepaymentPlanData.getChannelId();
                initFooterLayout();
                tvStopPlanning.setVisibility(View.GONE);
            } else {
                ivExecutionStatus.setVisibility(View.VISIBLE);
            }
            startDate = mPepaymentPlanData.getPlanRunTime();
            /*创建时间*/
            tvCreateDate.setText(String.format(mContext.getString(R.string.text_create_date), mPepaymentPlanData.getCreateDate()));
            if (!TextUtils.isEmpty(mPepaymentPlanData.getChooseRepaymentDate()) && mPepaymentPlanData.getChooseRepaymentDate().contains(",")) {
                String[] split = StringUtil.getSplit(mPepaymentPlanData.getChooseRepaymentDate(), ",");
                StringUtil.dataSorting(Arrays.asList(split));
                /*计划日期*/
                tvPlannedDate.setText(mContext.getString(R.string.text_planned_date) + split[0] + "至" + split[split.length - 1]);
            } else {
                /*计划日期*/
                tvPlannedDate.setText(mContext.getString(R.string.text_planned_date) + mPepaymentPlanData.getChooseRepaymentDate());
            }
            /*执行状态*/
            if (ExecutionStatusEnum.getSearchStatusStyle(ExecutionStatusEnum.implementation_plan, mPepaymentPlanData.getStatus()) == 0) {
                ivExecutionStatus.setVisibility(View.GONE);
            } else {
                ivExecutionStatus.setVisibility(View.VISIBLE);

                ivExecutionStatus.setImageResource(ExecutionStatusEnum.getSearchStatusStyle(ExecutionStatusEnum.implementation_plan, mPepaymentPlanData.getStatus()));
                if ("gray".equals(ExecutionStatusEnum.getSearchStatusColor(ExecutionStatusEnum.implementation_plan, mPepaymentPlanData.getStatus())) ||
                        "green".equals(ExecutionStatusEnum.getSearchStatusColor(ExecutionStatusEnum.implementation_plan, mPepaymentPlanData.getStatus())) ||
                        "red".equals(ExecutionStatusEnum.getSearchStatusColor(ExecutionStatusEnum.implementation_plan, mPepaymentPlanData.getStatus()))) {
                    tvStopPlanning.setVisibility(View.GONE);
                } else {
                    if (!KeyConstant.MAKING_PLANS.equals(mPepaymentPlanData.getPageMark())) {
                        tvStopPlanning.setVisibility(View.VISIBLE);
                    }
                }
            }
            /*设置银行卡号*/
            tvBankCardNumber.setText(StringUtil.replaceCardNumber(mPepaymentPlanData.getCarNumber()));
            /*真是姓名+银行卡名*/
//            tvBankName.setText(mPepaymentPlanData.getRealName() + "  " + mPepaymentPlanData.getBankName());
            tvBankName.setText(mPepaymentPlanData.getBankName());
            /*设置银行logo*/
            GlideUtils.loadImage(mContext, ivBankLogo, mPepaymentPlanData.getExtendOne());
            /*开始日*/
            tvBillingDay.setText(mPepaymentPlanData.getBillDate());
            /*结束日*/
            tvRepaymentDay.setText(mPepaymentPlanData.getRepaymentDate());
            /*养卡笔数*/
            tvRepayments.setText(mPepaymentPlanData.getRepaymentNum());
            /*需预留金额*/
            tvReserveAmountRequired.setText("￥" + StringUtil.setNumberFormatting(mPepaymentPlanData.getKeepMoney()));
            /*预计养卡金额*/
            tvPrepaymentAmount.setText("￥" + StringUtil.setNumberFormatting(mPepaymentPlanData.getPlanRepaymentMoney()));
            /*设置养卡进度*/
            ProgressBarUtil.getInstance(mContext, pbProgressBar)
                    /*进度条样式*/
                    .setProgressDrawable("red".equals(ExecutionStatusEnum.getSearchStatusColor(ExecutionStatusEnum.implementation_plan, mPepaymentPlanData.getStatus())) ?
                            ProgressBarUtil.layerProgressDrawableColorRed :
                            ProgressBarUtil.layerProgressDrawableColorBlue)
                    /*最大进度*/
                    .setMaxValue(TextUtils.isEmpty(mPepaymentPlanData.getRepaymentNum()) ? 0 : Integer.parseInt(mPepaymentPlanData.getRepaymentNum()))
                    /*实际进度*/
                    .setProgress(TextUtils.isEmpty(mPepaymentPlanData.getAlreadyRepaymentNum()) ? 0 : Integer.parseInt(mPepaymentPlanData.getAlreadyRepaymentNum()));
            /*养卡笔数（判断计算的笔数是否是空）*/
            tvNumberRemaining.setText("剩余" + (TextUtils.isEmpty(StringUtil.getSubtract(mPepaymentPlanData.getRepaymentNum(), mPepaymentPlanData.getAlreadyRepaymentNum())) ? 0 :
                    /*判断养卡笔数减去已养卡笔数是否小于或等于0，如果小于等于0就把值设置为0，如果大于零则设置为计算的值*/
                    (Integer.parseInt(StringUtil.getSubtract(mPepaymentPlanData.getRepaymentNum(), mPepaymentPlanData.getAlreadyRepaymentNum())) <= 0 ? "0" :
                            StringUtil.getSubtract(mPepaymentPlanData.getRepaymentNum(), mPepaymentPlanData.getAlreadyRepaymentNum()))) + "笔");
        }
        /*历史计划*/
        else if (mHistoryPlanData != null) {
            if (!TextUtils.isEmpty(mHistoryPlanData.getChooseRepaymentDate()) && mHistoryPlanData.getChooseRepaymentDate().contains(",")) {
                String[] split = StringUtil.getSplit(mHistoryPlanData.getChooseRepaymentDate(), ",");
                StringUtil.dataSorting(Arrays.asList(split));
                /*计划日期*/
                tvPlannedDate.setText(mContext.getString(R.string.text_planned_date) + split[0] + "至" + split[split.length - 1]);
            } else {
                /*计划日期*/
                tvPlannedDate.setText(mContext.getString(R.string.text_planned_date) + mHistoryPlanData.getChooseRepaymentDate());
            }
            /*执行状态*/
            if (ExecutionStatusEnum.getSearchStatusStyle(ExecutionStatusEnum.implementation_plan, mHistoryPlanData.getStatus()) == 0) {
                ivExecutionStatus.setVisibility(View.GONE);
            } else {
                ivExecutionStatus.setVisibility(View.VISIBLE);
                ivExecutionStatus.setImageResource(ExecutionStatusEnum.getSearchStatusStyle(ExecutionStatusEnum.implementation_plan, mHistoryPlanData.getStatus()));
                if ("gray".equals(ExecutionStatusEnum.getSearchStatusColor(ExecutionStatusEnum.implementation_plan, mHistoryPlanData.getStatus())) ||
                        "green".equals(ExecutionStatusEnum.getSearchStatusColor(ExecutionStatusEnum.implementation_plan, mHistoryPlanData.getStatus())) ||
                        "red".equals(ExecutionStatusEnum.getSearchStatusColor(ExecutionStatusEnum.implementation_plan, mPepaymentPlanData.getStatus()))) {
                    tvStopPlanning.setVisibility(View.GONE);
                } else {
                    if (!KeyConstant.MAKING_PLANS.equals(mPepaymentPlanData.getPageMark())) {
                        tvStopPlanning.setVisibility(View.VISIBLE);
                    }
                }
            }
            /*银行卡号*/
            tvBankCardNumber.setText(StringUtil.replaceCardNumber(mHistoryPlanData.getCarNumber()));
            /*真是姓名+银行卡名*/
//            tvBankName.setText(mHistoryPlanData.getRealName() + "  " + mHistoryPlanData.getBankName());
            tvBankName.setText(mHistoryPlanData.getBankName());
            /*银行卡logo*/
            GlideUtils.loadImage(mContext, ivBankLogo, mHistoryPlanData.getExtendOne());
            /*开始日*/
            tvBillingDay.setText(mHistoryPlanData.getBillDate());
            /*结束日*/
            tvRepaymentDay.setText(mHistoryPlanData.getRepaymentDate());
            /*养卡笔数*/
            tvRepayments.setText(mHistoryPlanData.getRepaymentNum());
            /*需预留金额*/
            tvReserveAmountRequired.setText("￥" + StringUtil.setNumberFormatting(mHistoryPlanData.getKeepMoney()));
            /*预计养卡金额*/
            tvPrepaymentAmount.setText("￥" + StringUtil.setNumberFormatting(mHistoryPlanData.getPlanRepaymentMoney()));
            /*设置养卡进度*/
            ProgressBarUtil.getInstance(mContext, pbProgressBar)
                    /*进度条样式*/
                    .setProgressDrawable("red".equals(ExecutionStatusEnum.getSearchStatusColor(ExecutionStatusEnum.implementation_plan, mHistoryPlanData.getStatus())) ?
                            ProgressBarUtil.layerProgressDrawableColorRed :
                            ProgressBarUtil.layerProgressDrawableColorBlue)
                    /*最大进度*/
                    .setMaxValue(TextUtils.isEmpty(mHistoryPlanData.getRepaymentNum()) ? 0 : Integer.parseInt(mHistoryPlanData.getRepaymentNum()))
                    /*实际进度*/
                    .setProgress(TextUtils.isEmpty(mHistoryPlanData.getAlreadyRepaymentNum()) ? 0 : Integer.parseInt(mHistoryPlanData.getAlreadyRepaymentNum()));
            /*养卡笔数（判断计算的笔数是否是空）*/
            tvNumberRemaining.setText("剩余" + (TextUtils.isEmpty(StringUtil.getSubtract(mHistoryPlanData.getRepaymentNum(), mHistoryPlanData.getAlreadyRepaymentNum())) ? 0 :
                    /*判断养卡笔数减去已养卡笔数是否小于或等于0，如果小于等于0就把值设置为0，如果大于零则设置为计算的值*/
                    (Integer.parseInt(StringUtil.getSubtract(mHistoryPlanData.getRepaymentNum(), mHistoryPlanData.getAlreadyRepaymentNum())) <= 0 ? "0" :
                            StringUtil.getSubtract(mHistoryPlanData.getRepaymentNum(), mHistoryPlanData.getAlreadyRepaymentNum()))) + "笔");
        }
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
                                                mContext, "提示",
                                                "该帐单将在" + startDate + "执行是否确认？",
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

    @Override
    /**
     * 设置要执行的信息
     *
     * @param executionParam 参数
     */
    public <T> void carriedOutMethod(T executionParam) {
        if (KeyConstant.TYPE_TERMINATION_PLAN.equals(executionParam)) {
            /*日期养卡*/
            DateRepaymentActivity.mDateRepaymentActivity.finish();
            jumpActivity(mContext, DateRepaymentActivity.class, true);
        } else {
            /*日期养卡*/
            DateRepaymentActivity.mDateRepaymentActivity.finish();
            /*选择银行卡*/
            SelectBankCardActivity.mSelectBankCardActivity.finish();
            /*制定计划*/
            MakingPlansActivity.mMakingPlansActivity.finish();
            jumpActivity(mContext, DateRepaymentActivity.class, true);
        }
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
}
