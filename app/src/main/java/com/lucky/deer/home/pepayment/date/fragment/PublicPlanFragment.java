package com.lucky.deer.home.pepayment.date.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseFragment;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.home.pepayment.PlanDetailsActivity;
import com.lucky.deer.home.pepayment.adapter.PepaymentPlanListAdapter;
import com.lucky.deer.home.pepayment.date.adapter.HistoryPlanListAdapter;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.model.request.BaseReq;
import com.lucky.model.request.home.repayment.TerminationPlanReq;
import com.lucky.model.response.home.pepayment.HistoryPlanList;
import com.lucky.model.response.home.pepayment.PepaymentPlanList;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.util.List;

import butterknife.BindView;
import xyz.yhsj.loadstatusview.LoadStatusView;

/**
 * 全部计划
 *
 * @author wangxiangyi
 * @date 2019/01/6
 */
public class PublicPlanFragment extends BaseFragment {
    /**
     * 全部计划
     */
    public static String AllPlan = "allPlan";
    /**
     * 执行计划
     */
    public static String executePlan = "executePlan";
    /**
     * 异常计划
     */
    public static String abnormalPlan = "abnormalPlan";
    /**
     * 还款历史
     */
    public static String repaymentHistory = "repaymentHistory";

    /**
     * 下啦刷新
     */
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    /**
     * 空白页
     */
    @BindView(R.id.lsv_load_status)
    LoadStatusView lsvLoadStatus;
    /**
     * 列表
     */
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    /**
     * 还款计划适配器
     */
    private PepaymentPlanListAdapter mAdapter;
    /**
     * 还款历史适配器
     */
    private HistoryPlanListAdapter mHistoryAdapter;
    /**
     * 计划标志
     */
    private String mPlanSign;
    /**
     * 获取单利
     */
    private PublicDialog inistanceView;
    private QMUIDialog qmuiDialog;
    /**
     * mBillPlanId:终止计划id
     * mBillPromptLogId：账单异常确认id
     */
    private String mBillPlanId, mBillPromptLogId;

    public static PublicPlanFragment newInstance(Bundle args) {
        PublicPlanFragment fragment = new PublicPlanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPlanSign = getArguments().getString(mEntity);
        }
    }

    @Override
    protected int initlayout() {
        return R.layout.fragment_execute_plan;
    }

    @Override
    protected void initView() {
        /*计划列表初始化*/
        rvList.setLayoutManager(new LinearLayoutManager(mActivity));
        if (repaymentHistory.equals(mPlanSign)) {
            mHistoryAdapter = new HistoryPlanListAdapter();
            rvList.setAdapter(mHistoryAdapter);
        } else {
            mAdapter = new PepaymentPlanListAdapter();
            rvList.setAdapter(mAdapter);
        }
        /*获取单利*/
        inistanceView = PublicDialog.getInstance();
    }

    @Override
    protected void initData() {
//        /*通知监听*/
//        RxBus.getInstance().addSubscription(
//                RxBus.getInstance()
//                        .toObserverable()
//                        .subscribe(o -> {
//                            if (KeyConstant.TYPE_TERMINATION_PLAN.equals(o)) {
//                                loadData();
//                            }
//                        }));
    }

    @Override
    protected void initListener() {
        /*下拉刷新*/
        srlRefresh.setOnRefreshListener(() -> loadData());
        /*历史计划*/
        if (repaymentHistory.equals(mPlanSign)) {
            /*上啦加载*/
            mHistoryAdapter.setOnLoadMoreListener(() -> loadData(), rvList);
            /*item点击监听*/
            mHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
                startActivity(new Intent(mActivity, PlanDetailsActivity.class).putExtra(mEntity, mHistoryAdapter.getData().get(position)));
            });
        } else {
            /*上啦加载*/
            mAdapter.setOnLoadMoreListener(() -> loadData(), rvList);
            /*item点击监听*/
            mAdapter.setOnItemClickListener((adapter, view, position) -> {
                startActivity(new Intent(mActivity, PlanDetailsActivity.class).putExtra(mEntity, mAdapter.getData().get(position)));
            });
        }
//        /*item里面点击监听*/
//        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
//            switch (view.getId()) {
//                /*终止计划按钮*/
//                case R.id.tv_stop_planning:
//                    mBillPlanId = mAdapter.getData().get(position).getId();
////                    qmuiDialog = inistanceView.setCustomizeView(
////                            inistanceView.initTitleEtOrTvView(
////                                    mActivity, getString(R.string.dialog_prompt), "是否终止计划？",
////                                    true),
////                            KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_4);
//                    showDialog("是否终止计划");
//                    break;
//                /*处理按钮*/
//                case R.id.tv_deal_with:
//                    mBillPromptLogId = mAdapter.getData().get(position).getBillPromptLogId();
//                    showDialog("是否正常执行明日计划");
//                    break;
//                default:
//            }
//        });

    }

    /**
     * 提示框
     *
     * @param msg
     */
    public void showDialog(String msg) {
        qmuiDialog = inistanceView.setCustomizeView(
                inistanceView.initTitleEtOrTvView(
                        mActivity, getString(R.string.dialog_prompt), msg,
                        true),
                KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_4);
        inistanceView.setOnClickListener((OnClickListener<String>) (status, useType, isPhoneNumber, text) -> {
            if (qmuiDialog != null) {
                switch (status) {
                    case OK:
                        if (!TextUtils.isEmpty(mBillPlanId)) {
                            /*终止计划*/
                            terminationPlan(mBillPlanId);
                        } else if (!TextUtils.isEmpty(mBillPromptLogId)) {
                            /*账单异常确认*/
                            billExceptionYesNo(2, 1);
                        }

                        break;
                    case CANCEL:
                        if (!TextUtils.isEmpty(mBillPromptLogId)) {
                            /*账单异常确认*/
                            billExceptionYesNo(3, 4);
                        }
                        break;
                    default:
                }
                qmuiDialog.dismiss();
                qmuiDialog = null;
            }
        });
    }

    @Override
    protected void onlazyLoadData() {
        showLoadingDialog();
//        if (srlRefresh!=null){
//            srlRefresh.setRefreshing(true);
//        }
        countdownCarriedOut1(1000)
                .subscribe(itme -> {
                    loadData();
                });
    }

    /**
     * 加载页面数据
     */
    @SuppressLint("CheckResult")
    private void loadData() {
        BaseReq baseReq = new BaseReq();
        if (srlRefresh != null && srlRefresh.isRefreshing()) {
            pageCurrent = 1;
            baseReq.setPageCurrent(pageCurrent);
        } else if (pageCurrent > 1) {
            baseReq.setPageCurrent(pageCurrent);
        }/* else {
            showLoadingDialog();
        }*/
        if (!TextUtils.isEmpty(mPlanSign)) {
            /*全部计划*/
            if (AllPlan.equals(mPlanSign)) {
                /*全部计划*/
                mNetworkRequestInstance.listMyPlan(baseReq)
                        .subscribe(returnData -> {
                            dismissLoadingDialog();
                            if (srlRefresh != null) {
                                srlRefresh.setRefreshing(false);
                            }
                            if (lsvLoadStatus != null) {
                                lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                            }
                            if (RequestUtils.isRequestSuccess(returnData)) {
                                List<PepaymentPlanList> list = returnData.getData().getList();
                                if (list != null) {
                                    if (baseReq.getPageCurrent() == 1) {
                                        mAdapter.setNewData(list);
                                        if (list.size() >= baseReq.getPageSize()) {
                                            pageCurrent++;
                                        } else {
                                            /*加载结束*/
                                            mAdapter.loadMoreEnd(true);
                                        }
                                    } else if (list.size() >= baseReq.getPageSize()) {
                                        mAdapter.addData(list);
                                        /*加载完成*/
                                        mAdapter.loadMoreComplete();
                                        pageCurrent++;
                                    } else {
                                        mAdapter.addData(list);
                                        /*加载结束*/
                                        mAdapter.loadMoreEnd();
                                    }
                                }
                            } else if (RequestUtils.isEmpty(returnData)) {
                                lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                if (pageCurrent > 1) {
                                    /*加载失败*/
                                    mAdapter.loadMoreFail();
                                }
                                HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                            }
                        });
            }
            /*执行计划*/
            else if (executePlan.equals(mPlanSign)) {
                /*查询执行列表*/
                mNetworkRequestInstance.listExecutingPlan(baseReq)
                        .subscribe(returnData -> {
                            dismissLoadingDialog();
                            if (srlRefresh != null) {
                                srlRefresh.setRefreshing(false);
                            }
                            if (lsvLoadStatus != null) {
                                lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                            }
                            if (RequestUtils.isRequestSuccess(returnData)) {
                                List<PepaymentPlanList> list = returnData.getData().getList();
                                if (list != null) {
                                    if (baseReq.getPageCurrent() == 1) {
                                        mAdapter.setNewData(list);
                                        if (list.size() >= baseReq.getPageSize()) {
                                            pageCurrent++;
                                        } else {
                                            /*加载结束*/
                                            mAdapter.loadMoreEnd(true);
                                        }
                                    } else if (list.size() >= baseReq.getPageSize()) {
                                        mAdapter.addData(list);
                                        /*加载完成*/
                                        mAdapter.loadMoreComplete();
                                        pageCurrent++;
                                    } else {
                                        mAdapter.addData(list);
                                        /*加载结束*/
                                        mAdapter.loadMoreEnd();
                                    }
                                }
                            } else if (RequestUtils.isEmpty(returnData)) {
                                lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                if (pageCurrent > 1) {
                                    /*加载失败*/
                                    mAdapter.loadMoreFail();
                                }
                                HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                            }
                        });
            } else if (abnormalPlan.equals(mPlanSign)) {
                /*查询异常列表*/
                mNetworkRequestInstance.billExceptionList(baseReq)
                        .subscribe(returnData -> {
                            dismissLoadingDialog();
                            if (srlRefresh != null) {
                                srlRefresh.setRefreshing(false);
                            }
                            if (lsvLoadStatus != null) {
                                lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                            }
                            if (RequestUtils.isRequestSuccess(returnData)) {
                                List<PepaymentPlanList> list = returnData.getData().getList();
                                if (list != null) {
                                    if (baseReq.getPageCurrent() == 1) {
                                        mAdapter.setNewData(list);
                                        if (list.size() >= baseReq.getPageSize()) {
                                            pageCurrent++;
                                        } else {
                                            /*加载结束*/
                                            mAdapter.loadMoreEnd(true);
                                        }
                                    } else if (list.size() >= baseReq.getPageSize()) {
                                        mAdapter.addData(list);
                                        /*加载完成*/
                                        mAdapter.loadMoreComplete();
                                        pageCurrent++;
                                    } else {
                                        mAdapter.addData(list);
                                        /*加载结束*/
                                        mAdapter.loadMoreEnd();
                                    }
                                }
                            } else if (RequestUtils.isEmpty(returnData)) {
                                lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                if (pageCurrent > 1) {
                                    /*加载失败*/
                                    mAdapter.loadMoreFail();
                                }
                                HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                            }
                        });
            } else if (repaymentHistory.equals(mPlanSign)) {
                /*查询还款历史列表*/
                mNetworkRequestInstance.listMyCompletedPlan(baseReq)
                        .subscribe(returnData -> {
                            dismissLoadingDialog();
                            srlRefresh.setRefreshing(false);
                            lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                            if (RequestUtils.isRequestSuccess(returnData)) {
                                List<HistoryPlanList> list = returnData.getData().getList();
                                if (list != null) {
                                    if (baseReq.getPageCurrent() == 1) {
                                        mHistoryAdapter.setNewData(list);
                                        if (list.size() >= baseReq.getPageSize()) {
                                            pageCurrent++;
                                        } else {
                                            /*加载结束*/
                                            mHistoryAdapter.loadMoreEnd(true);
                                        }
                                    } else if (list.size() >= baseReq.getPageSize()) {
                                        mHistoryAdapter.addData(list);
                                        /*加载完成*/
                                        mHistoryAdapter.loadMoreComplete();
                                        pageCurrent++;
                                    } else {
                                        mHistoryAdapter.addData(list);
                                        /*加载结束*/
                                        mHistoryAdapter.loadMoreEnd();
                                    }
                                }
                            } else if (RequestUtils.isEmpty(returnData)) {
                                lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                                mHistoryAdapter.notifyDataSetChanged();
                            } else {
                                if (pageCurrent > 1) {
                                    /*加载失败*/
                                    mHistoryAdapter.loadMoreFail();
                                }
                                HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                            }
                        });
            }
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
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        for (int i = 0; i < mAdapter.getData().size(); i++) {
                            if (id.equals(mAdapter.getData().get(i).getId())) {
                                if (AllPlan.equals(mPlanSign)) {
                                    mAdapter.getData().get(i).setStatus("8");
                                } else {
                                    mAdapter.remove(i);
                                }
                                break;
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                        if (mAdapter.getItemCount() <= 0) {
                            lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                        }
                    } else {
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                    }
                });
    }

    /**
     * 明日还款计划是否正常执行
     *
     * @param isAgree 是否同意 2：是\3：否
     * @param status  点击取消传4，点击确定传1
     */
    @SuppressLint("CheckResult")
    public void billExceptionYesNo(int isAgree, int status) {
        TerminationPlanReq terminationPlanReq = new TerminationPlanReq();
        terminationPlanReq.setId(mBillPromptLogId);
        terminationPlanReq.setIsAgree(String.valueOf(isAgree));
        terminationPlanReq.setStatus(String.valueOf(status));
        showLoadingDialog();
        /*终止计划账单*/
        mNetworkRequestInstance.billExceptionYesNo(terminationPlanReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        for (int i = 0; i < mAdapter.getData().size(); i++) {
                            if (mBillPromptLogId.equals(mAdapter.getData().get(i).getBillPromptLogId())) {
                                mAdapter.remove(i);
                                break;
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                        if (mAdapter.getItemCount() <= 0) {
                            lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                        }
                    } else {
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                    }
                });
    }
}
