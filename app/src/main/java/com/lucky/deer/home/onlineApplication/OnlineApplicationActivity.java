package com.lucky.deer.home.onlineApplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我要网申
 *
 * @author wangxiangyi
 * @date 2019/03/16
 */
@SuppressLint("Registered")
public class OnlineApplicationActivity extends BaseActivity {


    public static final String ALL_DATA = "2";
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.ll_top_bar_background)
    LinearLayout llTopBarBackground;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    List<OnlineApplicationEntity> onlineApplicationList = new ArrayList<>();
    OnlineNewApplicationAdapter adapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_online_application_list;
    }

    @Override
    protected void initView() {
        initTopBar(topBar,"极速办卡");
        adapter=new OnlineNewApplicationAdapter(mContext,onlineApplicationList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageCurrent++;
                        getNetLoanBankCard();
                        refreshLayout.finishLoadMore();
                    }
                },300);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onlineApplicationList.clear();
                        pageCurrent=1;
                        getNetLoanBankCard();
                        refreshLayout.finishRefresh();
                        refreshLayout.setEnableLoadMore(true);
                    }
                },300);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        getNetLoanBankCard();
    }

    //网申数据
    public void getNetLoanBankCard(){
        HelpReq helpReq = new HelpReq();
        helpReq.setType(getStringData());
        helpReq.setPageCurrent(pageCurrent);
        showLoadingDialog();
        mNetworkRequestInstance.queryOnlineApplicationList(helpReq)
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        onlineApplicationList.addAll(returnData.getData().getOnlineApplicationList());
                        adapter.notifyDataSetChanged();
                    }else{
                        if (returnData.getMsg().equals("暂无数据")){
                            toast("数据已加载完毕");
                            refreshLayout.setEnableLoadMore(false);
                        }else {
                            HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                        }
                    }
                });

    }
}
