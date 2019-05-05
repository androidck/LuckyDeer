package com.lucky.deer.mine;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.mine.adapter.TransactionRecordAdapter;
import com.lucky.model.request.BaseReq;
import com.lucky.model.response.mine.TransactionRecordList;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.List;

import butterknife.BindView;
import xyz.yhsj.loadstatusview.LoadStatusView;

/**
 * 交易记录
 *
 * @author wangxiangyi
 * @date 2018/11/29
 */
public class TransactionRecordActivity extends BaseActivity {
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
     * 空白页布局
     */
    @BindView(R.id.lsv_load_status)
    LoadStatusView lsvLoadStatus;
    /**
     * 列表
     */
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    /**
     * 适配器
     */
    private TransactionRecordAdapter mAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_transaction_record;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_transaction_record);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new TransactionRecordAdapter();
        rvList.setAdapter(mAdapter);
        rvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        BaseReq baseReq = new BaseReq();
        if (srlRefresh.isRefreshing()) {
            pageCurrent = 1;
            baseReq.setPageCurrent(pageCurrent);
        } else if (pageCurrent > 1) {
            baseReq.setPageCurrent(pageCurrent);
        } else {
            showLoadingDialog();
        }
        mNetworkRequestInstance.queryTradingRecord(baseReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        List<TransactionRecordList> list = returnData.getData().getList();
                        if (list != null) {
                            if (baseReq.getPageCurrent() == 1) {
                                mAdapter.setNewData(list);
                                if (list.size() >= baseReq.getPageSize()) {
                                    pageCurrent++;
                                }else {
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
                        }else {
                            if (pageCurrent > 1) {
                                /*加载结束*/
                                mAdapter.loadMoreEnd();
                            }else {
                                mAdapter.notifyDataSetChanged();
                                lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
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
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }

    @Override
    protected void initListener() {
        /*下拉刷新监听*/
        srlRefresh.setOnRefreshListener(this::initData);
        /*上啦加载监听*/
        mAdapter.setOnLoadMoreListener(this::initData, rvList);
    }
}
