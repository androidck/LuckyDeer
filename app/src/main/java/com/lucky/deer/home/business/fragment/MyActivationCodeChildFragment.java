package com.lucky.deer.home.business.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseFragment;
import com.lucky.deer.home.business.adapter.MyActivationCodeChildAdapter;
import com.lucky.deer.util.PhoneUtils;
import com.lucky.deer.util.StringUtil;
import com.lucky.model.request.find.ActivationCodeChildReq;
import com.lucky.model.response.find.redEnvelope.ActivationCodeChildEntiviyt;
import com.lucky.model.util.HintUtil;

import java.util.ArrayList;

import butterknife.BindView;
import xyz.yhsj.loadstatusview.LoadStatusView;

/**
 * 我的激活码子页面功能（全部、未使用、已使用）等页面
 *
 * @author wangxiangyi
 * @date 2019/03/28
 */
public class MyActivationCodeChildFragment extends BaseFragment {
    /***
     * 全部
     */
    public static final String ALL = "all";
    /**
     * 未使用
     */
    public static final String NOT_USE = "notUse";
    /**
     * 已使用
     */
    public static final String ALREADY_USED = "alreadyUsed";
    /**
     * 下拉刷新
     */
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    /**
     * 空白
     */
    @BindView(R.id.lsv_load_status)
    LoadStatusView lsvLoadStatus;
    /**
     * 列表
     */
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private MyActivationCodeChildAdapter mAdapter;
    /**
     * 页面key
     */
    private String pageKey;

    public static MyActivationCodeChildFragment newInstance(Bundle args) {
        MyActivationCodeChildFragment fragment = new MyActivationCodeChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageKey = getArguments().getString(mEntity);
        }
    }

    @Override
    protected int initlayout() {
        return R.layout.fragment_my_activation_code_child;
    }

    @Override
    protected void initView() {
        rvList.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new MyActivationCodeChildAdapter(new ArrayList<>());
        rvList.setAdapter(mAdapter);
        /*添加分割线*/
        rvList.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initListener() {
        /*下拉刷新监听*/
        srlRefresh.setOnRefreshListener(this::initData);
        /*上啦加载监听*/
//        mAdapter.setOnLoadMoreListener(this::initData, rvList);

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                /*复制功能*/
                case R.id.btn_copy_upgrade_code:
                    if (StringUtil.copy(mActivity, mAdapter.getData().get(position).getCode())) {
                        HintUtil.showErrorWithToast(mActivity, "已复制到粘贴板");
                    } else {
                        HintUtil.showErrorWithToast(mActivity, "复制失败");
                    }
                    break;
                /*拨打电话*/
                case R.id.btn_phone_number:
                    PhoneUtils.callPhoneDirectly(mActivity, mAdapter.getData().get(position).getUseCodeUserPhone());
                    break;
                default:
            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        ActivationCodeChildReq baseReq = new ActivationCodeChildReq();
        if (!TextUtils.isEmpty(pageKey)) {
            baseReq.setQueryType(pageKey);
        }
//        if (srlRefresh.isRefreshing()) {
//            pageCurrent = 1;
//            baseReq.setPageCurrent(pageCurrent);
//        } else if (pageCurrent > 1) {
//            baseReq.setPageCurrent(pageCurrent);
//        } else {
//            showLoadingDialog();
//        }
        showLoadingDialog();
        mNetworkRequestInstance.queryUserUpgradeCode(baseReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        if (returnData.getData() == null || returnData.getData().size() <= 0) {
                            lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                        } else {
                            for (ActivationCodeChildEntiviyt datum : returnData.getData()) {
                                switch (pageKey) {
                                    case ALL:
                                    case NOT_USE:
                                        datum.setItemType(MyActivationCodeChildAdapter.ALL);
                                        break;
                                    case ALREADY_USED:
                                        datum.setItemType(MyActivationCodeChildAdapter.USED);
                                        break;
                                    default:
                                }
                            }
                            mAdapter.setNewData(returnData.getData());
                        }


//                        List<TransactionRecordList> list = returnData.getData().getList();
//                        if (list != null) {
//                            if (baseReq.getPageCurrent() == 1) {
//                                mAdapter.setNewData(list);
//                                if (list.size() >= baseReq.getPageSize()) {
//                                    pageCurrent++;
//                                } else {
//                                    /*加载结束*/
//                                    mAdapter.loadMoreEnd(true);
//                                }
//                            } else if (list.size() >= baseReq.getPageSize()) {
//                                mAdapter.addData(list);
//                                /*加载完成*/
//                                mAdapter.loadMoreComplete();
//                                pageCurrent++;
//                            } else {
//                                mAdapter.addData(list);
//                                /*加载结束*/
//                                mAdapter.loadMoreEnd();
//                            }
//                        } else {
//                            if (pageCurrent > 1) {
//                                /*加载结束*/
//                                mAdapter.loadMoreEnd();
//                            } else {
//                                mAdapter.notifyDataSetChanged();
//                                lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
//                            }
//                        }

                    } else if (RequestUtils.isEmpty(returnData)) {
                        lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                        mAdapter.notifyDataSetChanged();
                    } else {
//                        if (pageCurrent > 1) {
//                            /*加载失败*/
//                            mAdapter.loadMoreFail();
//                        }
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                    }

                });

    }
}
