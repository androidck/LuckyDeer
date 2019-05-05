package com.lucky.deer.mine;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.common.WebViewActivity;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.mine.adapter.HelpAdapter;
import com.lucky.model.common.WebViewBean;
import com.lucky.model.request.mine.HelpReq;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import xyz.yhsj.loadstatusview.LoadStatusView;

/**
 * 帮助页面
 *
 * @author wangxiangyi
 * @date 2019/03/15
 */
@SuppressLint("Registered")
public class HelpActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    @BindView(R.id.lsv_load_status)
    LoadStatusView lsvLoadStatus;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    /**
     * 适配器
     */
    private HelpAdapter mAdapter;


    @Override
    protected int initLayout() {
        return R.layout.activiyt_help;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_help);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new HelpAdapter();
        rvList.setAdapter(mAdapter);
        //添加Android自带的分割线
        rvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
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
            webViewBean.setWebTitle(mAdapter.getData().get(position).getTitile());
            webViewBean.setWebUrl(String.format(HttpConstant.H5_HELP_DETAILS_CONNECT, mAdapter.getData().get(position).getId()));
            jumpActivity(mContext, WebViewActivity.class, webViewBean);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        HelpReq helpReq = new HelpReq();
        helpReq.setType("2");
        helpReq.setPageSize(15);
        if (srlRefresh.isRefreshing()) {
            pageCurrent = 1;
            helpReq.setPageCurrent(pageCurrent);
        } else if (pageCurrent > 1) {
            helpReq.setPageCurrent(pageCurrent);
        } else {
            showLoadingDialog();
        }
        mNetworkRequestInstance.cmsList(helpReq)
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        if (returnData.getData().getCmsList() != null) {
                            if (helpReq.getPageCurrent() == 1) {
                                mAdapter.setNewData(returnData.getData().getCmsList());
                                if (returnData.getData().getCmsList().size() >= helpReq.getPageSize()) {
                                    pageCurrent++;
                                } else {
                                    /*加载结束*/
                                    mAdapter.loadMoreEnd(true);
                                }
                            } else if (returnData.getData().getCmsList().size() >= helpReq.getPageSize()) {
                                mAdapter.addData(returnData.getData().getCmsList());
                                /*加载完成*/
                                mAdapter.loadMoreComplete();
                                pageCurrent++;
                            } else {
                                mAdapter.addData(returnData.getData().getCmsList());
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

}
