package com.lucky.deer.home.pepayment.date.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseFragment;
import com.lucky.deer.home.pepayment.PlanDetailsActivity;
import com.lucky.deer.home.pepayment.date.adapter.HistoryPlanListAdapter;
import com.lucky.model.request.BaseReq;
import com.lucky.model.response.home.pepayment.HistoryPlanList;
import com.lucky.model.util.HintUtil;

import java.util.List;

import butterknife.BindView;
import xyz.yhsj.loadstatusview.LoadStatusView;

/**
 * 历史计划
 *
 * @author wangxiangyi
 * @date 2018/12/12
 */
public class HistoryPlanFragment extends BaseFragment {
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
    private HistoryPlanListAdapter mAdapter;

    public static HistoryPlanFragment newInstance(Bundle args) {
        HistoryPlanFragment fragment = new HistoryPlanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
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
        mAdapter = new HistoryPlanListAdapter();
        rvList.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
//        loadData();
    }

    @Override
    protected void initListener() {
        /*下拉刷新*/
        srlRefresh.setOnRefreshListener(this::loadData);
        /*上啦加载*/
        mAdapter.setOnLoadMoreListener(this::loadData, rvList);
        /*item点击监听*/
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(mActivity, PlanDetailsActivity.class).putExtra(mEntity, mAdapter.getData().get(position)));
        });
    }

    @Override
    protected void onlazyLoadData() {
        showLoadingDialog();
//        srlRefresh.setRefreshing(true);
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
        /*获取历史计划信息*/
        mNetworkRequestInstance.listMyCompletedPlan(baseReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        List<HistoryPlanList> list = returnData.getData().getList();
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
