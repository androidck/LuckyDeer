package com.lucky.deer.home.onlineApplication;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.common.WebViewActivity;
import com.lucky.deer.home.onlineApplication.adapter.OnlineLoanAdapter;
import com.lucky.model.common.WebViewBean;
import com.lucky.model.request.mine.HelpReq;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import xyz.yhsj.loadstatusview.LoadStatusView;

/**
 * 我要网贷
 *
 * @author wangxiangyi
 * @date 2019/03/16
 */
public class OnlineLoanActivity extends BaseActivity {
    /**
     * 首页推荐置顶的网贷数据
     */
    public static final String RECOMMEND_DATA = "1";
    /**
     * 所有网贷列表
     */
    public static final String ALL_DATA = "2";
    /**
     * 标题控件
     */
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    /**
     * 下拉刷新控件
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
    private OnlineLoanAdapter mAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_online_loan;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_online_loan);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new OnlineLoanAdapter();
        rvList.setAdapter(mAdapter);
        /*添加分割线*/
        rvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
    }


    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        HelpReq helpReq = new HelpReq();
        helpReq.setType(getStringData());
        if (srlRefresh.isRefreshing()) {
            pageCurrent = 1;
            helpReq.setPageCurrent(pageCurrent);
        } else if (pageCurrent > 1) {
            helpReq.setPageCurrent(pageCurrent);
        } else {
            showLoadingDialog();
        }
        mNetworkRequestInstance.queryOnlineLoanList(helpReq)
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        if (returnData.getData() != null) {
                            if (helpReq.getPageCurrent() == 1) {
                                mAdapter.setNewData(returnData.getData());
                                if (returnData.getData().size() >= helpReq.getPageSize()) {
                                    pageCurrent++;
                                } else {
                                    /*加载结束*/
                                    mAdapter.loadMoreEnd(true);
                                }
                            } else if (returnData.getData().size() >= helpReq.getPageSize()) {
                                mAdapter.addData(returnData.getData());
                                /*加载完成*/
                                mAdapter.loadMoreComplete();
                                pageCurrent++;
                            } else {
                                mAdapter.addData(returnData.getData());
                                /*加载结束*/
                                mAdapter.loadMoreEnd();
                            }
                        }
                    } else if (RequestUtils.isEmpty(returnData)) {
                        if (helpReq.getPageCurrent() > 1) {
                            /*加载结束*/
                            mAdapter.loadMoreEnd();
                        } else {
                            lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                            mAdapter.notifyDataSetChanged();
                        }
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
        /*下拉刷新*/
        srlRefresh.setOnRefreshListener(this::initData);
        /*上拉加载*/
        mAdapter.setOnLoadMoreListener(this::initData, rvList);
        /*点击监听*/
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            WebViewBean webViewBean = new WebViewBean();
            webViewBean.setWebTitle(mAdapter.getData().get(position).getName());
            webViewBean.setWebUrl(mAdapter.getData().get(position).getAddress());
            jumpActivity(mContext, WebViewActivity.class, webViewBean);
        });
    }
}
