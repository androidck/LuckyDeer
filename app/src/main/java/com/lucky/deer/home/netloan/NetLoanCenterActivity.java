package com.lucky.deer.home.netloan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.home.onlineApplication.adapter.OnlineNewApplicationAdapter;
import com.lucky.model.request.mine.HelpReq;
import com.lucky.model.response.home.OnlineApplicationEntity;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 网申中心
 */
@Route(path = "/netloan/NetLoanCenterActivity")
public class NetLoanCenterActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.ll_top_bar_background)
    LinearLayout llTopBarBackground;
    @BindView(R.id.recyclerView)
    SwipeRecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private OnlineNewApplicationAdapter mAdapter;
    List<OnlineApplicationEntity> onlineApplicationList=new ArrayList<>();

    @Autowired
    String type;

    @Override
    protected int initLayout() {
        return R.layout.activity_loan_center;
    }

    @Override
    protected void initView() {
        initTopBar(topBar,"网申中心");

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        View headView=getLayoutInflater().inflate(R.layout.activity_loan_center_head,recyclerView,false);
        recyclerView.addHeaderView(headView);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageCurrent++;
                        getNetApply();
                        refreshLayout.finishLoadMore();
                    }
                },150);
            }
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onlineApplicationList.clear();
                        pageCurrent=1;
                        getNetApply();
                        refreshLayout.finishRefresh();
                        refreshLayout.setEnableLoadMore(true);
                    }
                },150);
            }
        });
        mAdapter=new OnlineNewApplicationAdapter(mContext,onlineApplicationList);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        getNetApply();
    }

    //获取信用卡网申
    public void getNetApply() {
        HelpReq helpReq = new HelpReq();
        helpReq.setType(type);
        helpReq.setPageCurrent(pageCurrent);
        showLoadingDialog();
        mNetworkRequestInstance.queryOnlineApplicationList(helpReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        onlineApplicationList.addAll(returnData.getData().getOnlineApplicationList());
                        mAdapter.notifyDataSetChanged();
                    } else {
                        if (returnData.getMsg().equals("暂无数据")){
                            HintUtil.showErrorWithToast(mActivity,"数据已加载完成");
                            refreshLayout.setEnableLoadMore(false);
                        }else {
                            HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                        }
                    }
                });

    }




}
