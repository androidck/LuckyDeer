package com.lucky.deer.home.pepayment.perfect.fragment;

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
import com.lucky.deer.home.pepayment.perfect.PerfectPlanDetailsActivity;
import com.lucky.deer.home.pepayment.perfect.adapter.PerfectRepaymentListAdapter;
import com.lucky.model.request.home.repayment.perfect.PerfectRepaymentListReq;
import com.lucky.model.response.home.pepayment.perfect.MakePerfectBillPlanEntity;
import com.lucky.model.util.HintUtil;

import java.util.List;

import butterknife.BindView;
import xyz.yhsj.loadstatusview.LoadStatusView;

/**
 * 完美养卡列表
 *
 * @author wangxiangyi
 * @date 2019/02/20
 */
public class PerfectRepaymentFagment extends BaseFragment {
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
     * 养卡历史
     */
    public static String repaymentHistory = "repaymentHistory";
    /**
     * 下拉刷新
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
     * 页面区分标志
     */
    private String mPlanSign;
    /**
     * 适配器
     */
    private PerfectRepaymentListAdapter mAdapter;

    public static PerfectRepaymentFagment newInstance(Bundle args) {
        PerfectRepaymentFagment fragment = new PerfectRepaymentFagment();
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
        return R.layout.fragment_perfect_repayment_list;
    }

    @Override
    protected void initView() {
        /*计划列表初始化*/
        rvList.setLayoutManager(new LinearLayoutManager(mActivity));
        /*初始化适配器*/
        mAdapter = new PerfectRepaymentListAdapter();
        rvList.setAdapter(mAdapter);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        PerfectRepaymentListReq baseReq = new PerfectRepaymentListReq();
        if (srlRefresh != null && srlRefresh.isRefreshing()) {
            pageCurrent = 1;
            baseReq.setPageCurrent(pageCurrent);
        } else if (pageCurrent > 1) {
            baseReq.setPageCurrent(pageCurrent);
        } else {
            showLoadingDialog();
        }
        if (!TextUtils.isEmpty(mPlanSign)) {
            /*全部计划*/
            if (AllPlan.equals(mPlanSign)) {
                baseReq.setStatus("4");
            }
            /*执行计划*/
            else if (executePlan.equals(mPlanSign)) {
                baseReq.setStatus("1");
            }
            /*异常计划*/
            else if (abnormalPlan.equals(mPlanSign)) {
                baseReq.setStatus("3");
            }
            /*养卡历史*/
            else if (repaymentHistory.equals(mPlanSign)) {
                baseReq.setStatus("2");
            }
            mNetworkRequestInstance.queryPerfectPlanByStatus(baseReq)
                    .subscribe(returnData -> {
                        dismissLoadingDialog();
                        if (srlRefresh != null) {
                            srlRefresh.setRefreshing(false);
                        }
                        if (lsvLoadStatus != null) {
                            lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                        }
                        if (RequestUtils.isRequestSuccess(returnData)) {
                            List<MakePerfectBillPlanEntity> list = returnData.getData().getList();
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
    }

    @Override
    protected void initListener() {
        /*下拉刷新*/
        srlRefresh.setOnRefreshListener(this::initData);
        /*上啦加载*/
        mAdapter.setOnLoadMoreListener(this::initData, rvList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(mActivity, PerfectPlanDetailsActivity.class)
                    .putExtra(mEntity, mAdapter.getData().get(position)));
        });
    }
}