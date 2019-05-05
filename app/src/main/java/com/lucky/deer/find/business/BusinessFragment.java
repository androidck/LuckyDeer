package com.lucky.deer.find.business;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseFragment;
import com.lucky.deer.common.dialog.RedEnvelopesDialog;
import com.lucky.deer.find.business.adapter.BusinessAdapter;
import com.lucky.deer.find.business.adapter.BusinessAdapters;
import com.lucky.deer.find.business.redEnvelope.RedEnvelopeInfoActivity;
import com.lucky.deer.find.business.redEnvelope.RedEnvelopesDetailActivity;
import com.lucky.deer.find.business.redEnvelope.RedHandEnvelopesActivity;
import com.lucky.deer.home.MainActivity;
import com.lucky.model.request.find.business.BusinessReq;
import com.lucky.model.response.find.BusinessList;
import com.lucky.model.util.HintUtil;
import com.lucky.model.util.RxBus;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import xyz.yhsj.loadstatusview.LoadStatusView;

/**
 * 商机页面
 *
 * @author wangxiangyi
 * @date 2019/03/25
 */
public class BusinessFragment extends BaseFragment {
    /**
     * 下拉刷新
     */
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    /**
     * 空白页面
     */
    @BindView(R.id.lsv_load_status)
    LoadStatusView lsvLoadStatus;
    /**
     * 列表
     */
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    List<BusinessList> lists=new ArrayList<>();
    /**
     * 商机列表适配器
     */
    private BusinessAdapters adapters;
    //总数据
    private int countNum;

    public static BusinessFragment newInstance(Bundle args) {
        BusinessFragment fragment = new BusinessFragment();
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
        return R.layout.fragment_child_business;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        rvList.setLayoutManager(new LinearLayoutManager(mActivity));
        adapters=new BusinessAdapters(getActivity(),lists);
        rvList.setAdapter(adapters);
        /*添加分割线*/
        rvList.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        /*更新文章列表*/
        RxBus.getInstance().toObservable()
                .subscribe(returnData -> {
                    initData();
                });
        srlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageCurrent++;
                        initData();
                        refreshLayout.finishLoadMore();
                    }
                },150);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lists.clear();
                        srlRefresh.setEnableLoadMore(true);
                        pageCurrent=1;
                        initData();
                        refreshLayout.finishRefresh();
                    }
                },150);
            }
        });
        adapters.setOnItemClickListener(new BusinessAdapters.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                isCanReceive(position);
            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        BusinessReq businessReq = new BusinessReq();
        if (MainActivity.mAMapLocation != null) {
            /*经度*/
            businessReq.setLongitude(MainActivity.mAMapLocation.getLongitude());
            /*纬度*/
            businessReq.setLatitude(MainActivity.mAMapLocation.getLatitude());
        }
        businessReq.setPageCurrent(pageCurrent);
        showLoadingDialog();
        /*获取商机列表*/
        mNetworkRequestInstance.getBusinessOpportunityList(businessReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        if (returnData.getData().getList()==null){
                            lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                        }else {
                            lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                            if (lists.size()==returnData.getData().getTotalCount()){
                                srlRefresh.setEnableLoadMore(false);
                            }else {
                                //当前list 总条数与返回的总条数相等就禁止上拉加载
                                lists.addAll(returnData.getData().getList());
                                adapters.notifyDataSetChanged();
                            }
                        }
                    }else {
                        lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                    }
                });
    }



    @OnClick({R.id.btn_receive_red_envelope, R.id.btn_red_envelopes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*收红包*/
            case R.id.btn_receive_red_envelope:
                startActivity(new Intent(mActivity, RedEnvelopesDetailActivity.class));
                break;
            /*发红包*/
            case R.id.btn_red_envelopes:
                startActivity(new Intent(getActivity(), RedHandEnvelopesActivity.class));
                break;
            default:
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    /**
     * 获取是否可以领取红包
     *
     * @param position
     */
    @SuppressLint("CheckResult")
    private void isCanReceive(int position) {
        BusinessReq businessReq = new BusinessReq();
        businessReq.setRedEnvelopeAdvertisingId(lists.get(position).getId());
        if (MainActivity.mAMapLocation != null) {
            /*经度*/
            businessReq.setLongitude(MainActivity.mAMapLocation.getLongitude());
            /*纬度*/
            businessReq.setLatitude(MainActivity.mAMapLocation.getLatitude());
            /*城市编码*/
            businessReq.setCityCode(MainActivity.mAMapLocation.getAdCode());
        }
        showLoadingDialog();
        mNetworkRequestInstance.isReceiptConditions(businessReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        if (returnData.getData() != null && "1".equals(returnData.getData().isReceiptConditions())) {
                            new RedEnvelopesDialog(mActivity, true)
                                    .setHeadImage(lists.get(position).getUserHead())
                                    .setNickName(lists.get(position).getNickName())
                                    .setOnClickListener(v -> {
                                        startGrabbingRedPackets(lists.get(position).getId());
                                    }).show();
                        } else {
                            startActivity(new Intent(mActivity, RedEnvelopeInfoActivity.class).putExtra(mEntity, businessReq.getRedEnvelopeAdvertisingId()));
                        }
                    } else {
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                    }
                });
    }

    /**
     * 开始抢红包
     *
     * @param id
     */
    @SuppressLint("CheckResult")
    private void startGrabbingRedPackets(String id) {
        BusinessReq businessReq = new BusinessReq();
        businessReq.setRedEnvelopeAdvertisingId(id);
        if (MainActivity.mAMapLocation != null) {
            /*经度*/
            businessReq.setLongitude(MainActivity.mAMapLocation.getLongitude());
            /*纬度*/
            businessReq.setLatitude(MainActivity.mAMapLocation.getLatitude());
            /*城市编码*/
            businessReq.setCityCode(MainActivity.mAMapLocation.getAdCode());
        }
        showLoadingDialog();
        mNetworkRequestInstance.startGrabbingRedPackets(businessReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        startActivity(new Intent(mActivity, RedEnvelopeInfoActivity.class).putExtra(mEntity, businessReq.getRedEnvelopeAdvertisingId()));
                    } else {
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                    }
                });
    }

}
