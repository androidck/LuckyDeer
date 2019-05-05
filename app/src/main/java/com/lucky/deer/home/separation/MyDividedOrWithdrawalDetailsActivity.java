package com.lucky.deer.home.separation;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.find.withdraw.SubmitFindWithdrawalActivity;
import com.lucky.deer.home.adapter.MySeparationAdapter;
import com.lucky.model.request.home.DividedOrWithdrawalDetailsReq;
import com.lucky.model.response.home.DividedOrWithdrawalDetailsList;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.List;

import butterknife.BindView;
import xyz.yhsj.loadstatusview.LoadStatusView;

/**
 * 我的分润或提现明细页面
 *
 * @author wangxiangyi
 * @date 2018/11/05
 */
public class MyDividedOrWithdrawalDetailsActivity extends BaseActivity {
    /**
     * 分润
     */
    public static String SEPARATION = "separation";
    /**
     * 提现明细
     */
    public static String REFLECTING_DETAILS = "reflectingDetails";

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    @BindView(R.id.lsv_load_status)
    LoadStatusView lsvLoadStatus;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private MySeparationAdapter mAdapter;
    /**
     * 页面展示类型
     * separation：分润
     * reflectingDetails：提现明细
     */
    private String viewType;

    @Override
    protected int initLayout() {
        return R.layout.activity_my_separation;
    }

    @Override
    protected void initView() {
        if (getStringData() != null) {
            viewType = getStringData();
            if (SEPARATION.equals(viewType)) {
                initTopBar(topBar, R.string.title_activity_my_separation);
            } else {
                initTopBar(topBar, R.string.title_activity_reflecting_details);
            }
        }
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new MySeparationAdapter();
        mAdapter.setType(SEPARATION.equals(viewType));
        rvList.setAdapter(mAdapter);
        rvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        DividedOrWithdrawalDetailsReq data = new DividedOrWithdrawalDetailsReq();
        if (srlRefresh.isRefreshing()) {
            pageCurrent = 1;
            data.setPageCurrent(pageCurrent);
        } else if (pageCurrent > 1) {
            data.setPageCurrent(pageCurrent);
        } else {
            showLoadingDialog();
        }
        if (SEPARATION.equals(viewType)) {
            /*我的分润*/
            mNetworkRequestInstance.listForRecords(data)
                    .compose(bindToLifecycle())
                    .subscribe(listResponseData -> {
                        lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                        dismissLoadingDialog();
                        srlRefresh.setRefreshing(false);
                        if (RequestUtils.isRequestSuccess(listResponseData)) {
                            List<DividedOrWithdrawalDetailsList> list = listResponseData.getData().getList();
                            if (list != null) {
                                if (data.getPageCurrent() == 1) {
                                    mAdapter.setNewData(list);
                                    if (list.size() >= data.getPageSize()) {
                                        pageCurrent++;
                                    } else {
                                        /*加载结束*/
                                        mAdapter.loadMoreEnd(true);
                                    }
                                } else if (list.size() >= data.getPageSize()) {
                                    mAdapter.addData(list);
                                    /*加载完成*/
                                    mAdapter.loadMoreComplete();
                                    pageCurrent++;
                                } else {
                                    mAdapter.addData(list);
                                    /*加载结束*/
                                    mAdapter.loadMoreEnd();
                                }
                            } else {
                                if (pageCurrent > 1) {
                                    /*加载结束*/
                                    mAdapter.loadMoreEnd();
                                }else {
                                    mAdapter.notifyDataSetChanged();
                                    lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                                }
                            }
                        } else if (RequestUtils.isEmpty(listResponseData)) {
                            mAdapter.notifyDataSetChanged();
                            lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                        } else {
                            if (pageCurrent > 1) {
                                /*加载失败*/
                                mAdapter.loadMoreFail();
                            }
                            HintUtil.showErrorWithToast(mContext, listResponseData.getMsg());
                        }
                    });
        } else {
            /*我的提现*/
            data.setWithdrawStatus("4");
            mNetworkRequestInstance.getWithdrawRecord(data)
                    .compose(bindToLifecycle())
                    .subscribe(listResponseData -> {
                        lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                        dismissLoadingDialog();
                        srlRefresh.setRefreshing(false);
                        if (RequestUtils.isRequestSuccess(listResponseData)) {
                            List<DividedOrWithdrawalDetailsList> list = listResponseData.getData().getList();
                            if (list != null) {
                                if (data.getPageCurrent() == 1) {
                                    mAdapter.setNewData(list);
                                    if (list.size() >= data.getPageSize()) {
                                        pageCurrent++;
                                    } else {
                                        /*加载结束*/
                                        mAdapter.loadMoreEnd(true);
                                    }
                                } else if (list.size() >= data.getPageSize()) {
                                    mAdapter.addData(list);
                                    /*加载完成*/
                                    mAdapter.loadMoreComplete();
                                    pageCurrent++;
                                } else {
                                    mAdapter.addData(list);
                                    /*加载结束*/
                                    mAdapter.loadMoreEnd();
                                }
                            } else {
                                mAdapter.notifyDataSetChanged();
                                lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                            }
                        } else {
                            if (pageCurrent > 1) {
                                /*加载失败*/
                                mAdapter.loadMoreFail();
                            }
                            HintUtil.showErrorWithToast(mContext, listResponseData.getMsg());
                        }
                    });
        }
    }

    @Override
    protected void initListener() {
        /*下拉刷新监听*/
        srlRefresh.setOnRefreshListener(this::initData);
        /*上啦加载监听*/
        mAdapter.setOnLoadMoreListener(this::initData, rvList);
        /*判断是否是提现明细*/
        if (!SEPARATION.equals(viewType)) {
            /*点击监听*/
            mAdapter.setOnItemClickListener((adapter, view, position) -> {
                DividedOrWithdrawalDetailsList data = mAdapter.getData().get(position);
                switch (data.getWithdrawStatus()) {
                    /*未审核*/
                    case "1":
                        /*已转账*/
                    case "4":
                        /*已审核*/
                    case "2":
                        jumpActivity(mContext, SubmitFindWithdrawalActivity.class, data.getId());
                        break;
                    /*审核失败*/
                    case "3":
                        /*转账失败*/
                    case "5":
                        jumpActivity(mContext, SubmitFindWithdrawalActivity.class, data.getId(), true);
                        break;
                    default:
                }
            });
        }

    }
}
