package com.lucky.deer.find.business.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseFragment;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.find.business.adapter.RedEnvelopesDetailAdapter;
import com.lucky.deer.find.business.redEnvelope.RedEnvelopeInfoActivity;
import com.lucky.deer.find.withdraw.FindWithdrawalActivity;
import com.lucky.model.request.BaseReq;
import com.lucky.model.response.find.redEnvelope.PageBean;
import com.lucky.model.response.find.redEnvelope.RedEnvelopesDetailEntity;
import com.lucky.model.util.HintUtil;
import com.lucky.model.util.RxBus;

import java.util.List;

import butterknife.BindView;
import xyz.yhsj.loadstatusview.LoadStatusView;

/**
 * 红包明细列表
 *
 * @author wangxiangyi
 * @date 2019/03/26
 */
public class RedEnvelopesDetailFragment extends BaseFragment {
    /**
     * 收到的红包
     */
    public static final String RECEIVED_RED_ENVELOPE = "receivedRedEnvelope";
    /**
     * 我发的红包
     */
    public static final String SENT_RED_ENVELOPE = "sentRedEnvelope";
    /**
     * 共收到/共发出
     */
    TextView tvIssueOrReceive;
    /**
     * 当前资金
     */
    TextView tvCurrentFunds;
    /**
     * 去提现
     */
    TextView btnWithdrawCash;
    /**
     * 下拉刷新控件
     */
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
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
    /**
     * 页面标记
     */
    private String mPageMark;
    /**
     * 列表页面
     */
    private RedEnvelopesDetailAdapter mAdapter;
    private RedEnvelopesDetailEntity mData;


    public static RedEnvelopesDetailFragment newInstance(Bundle args) {
        RedEnvelopesDetailFragment fragment = new RedEnvelopesDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPageMark = getArguments().getString(mEntity);
        }
    }

    @Override
    protected int initlayout() {
        return R.layout.fragment_red_envelopes_detail;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        rvList.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new RedEnvelopesDetailAdapter();
        if (!TextUtils.isEmpty(mPageMark)) {
            switch (mPageMark) {
                case RECEIVED_RED_ENVELOPE:
                    mAdapter.isReceiveRedEnvelope(true);
                    break;
                case SENT_RED_ENVELOPE:
                    mAdapter.isReceiveRedEnvelope(false);
                    break;
                default:
            }
        }
        rvList.setAdapter(mAdapter);
        rvList.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        initHeaderLayout();
        /*红包明细更新*/
        RxBus.getInstance()
                .toObservable()
                .subscribe(retrenData -> {
                    if (!TextUtils.isEmpty(retrenData) && KeyConstant.UPDATE_RED_ENVELOPE_DETAILS.equals(retrenData)) {
                        initData();
                    }
                });
    }

    @Override
    protected void initListener() {
        /*下拉刷新*/
        srlRefresh.setOnRefreshListener(this::initData);
        /*上拉加载*/
        mAdapter.setOnLoadMoreListener(this::initData, rvList);
        /*点击红包明细列表监听*/
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(mActivity, RedEnvelopeInfoActivity.class).putExtra(mEntity, mAdapter.getData().get(position).getAdvertId()));
        });
    }

    @Override
    protected void initData() {
        BaseReq baseReq = new BaseReq();
        if (srlRefresh != null && srlRefresh.isRefreshing()) {
            pageCurrent = 1;
            baseReq.setPageCurrent(pageCurrent);
        } else if (pageCurrent > 1) {
            baseReq.setPageCurrent(pageCurrent);
        } else {
            showLoadingDialog();
        }
        if (mPageMark != null) {
            switch (mPageMark) {
                /*收到的红包*/
                case RECEIVED_RED_ENVELOPE:
                    /*查询用户领取红包明细*/
                    mNetworkRequestInstance.userReceiveRedEnvelope(baseReq)
                            .subscribe(returnData -> {
                                dismissLoadingDialog();
                                if (srlRefresh != null) {
                                    srlRefresh.setRefreshing(false);
                                }
                                if (lsvLoadStatus != null) {
                                    lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                                }
                                if (RequestUtils.isRequestSuccess(returnData)) {
                                    mData = returnData.getData();
                                    setData();
                                    List<PageBean> list = mData.getPage();
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
                                    } else {
                                        lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                                        mAdapter.notifyDataSetChanged();
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
                    break;
                /*我发的红包*/
                case SENT_RED_ENVELOPE:
                    /*查询用户发放红包明细*/
                    mNetworkRequestInstance.userIssueRedEnvelope(baseReq)
                            .subscribe(returnData -> {
                                dismissLoadingDialog();
                                if (srlRefresh != null) {
                                    srlRefresh.setRefreshing(false);
                                }
                                if (lsvLoadStatus != null) {
                                    lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                                }
                                if (RequestUtils.isRequestSuccess(returnData)) {
                                    mData = returnData.getData();
                                    setData();
                                    List<PageBean> list = mData.getPage();
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
                                    } else {
                                        lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                                        mAdapter.notifyDataSetChanged();
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
                    break;
                default:
            }
        }
    }

    /**
     * 初始化头部布局
     */
    private void initHeaderLayout() {
        /*获取计划详情信息头部*/
        View headerView = LinearLayout.inflate(mActivity, R.layout.fragment_header_red_envelopes_detail, null);
        getHeaderViewId(headerView);
        initHeaderListener();
        setData();
        if (!mAdapter.isHeaderViewAsFlow()) {
            mAdapter.removeAllHeaderView();
        }
        mAdapter.addHeaderView(headerView);
    }

    /**
     * 获取头部控件
     */
    private void getHeaderViewId(View headerView) {
        /* 共收到/共发出*/
        tvIssueOrReceive = headerView.findViewById(R.id.tv_issue_or_receive);
        /*当前资金*/
        tvCurrentFunds = headerView.findViewById(R.id.tv_current_funds);
        /*提现按钮*/
        btnWithdrawCash = headerView.findViewById(R.id.btn_withdraw_cash);
    }

    /**
     * 头部监听
     */
    private void initHeaderListener() {
        /*点击提现*/
        btnWithdrawCash.setOnClickListener(v -> {
            if (mData != null) {
                startActivity(new Intent(mActivity, FindWithdrawalActivity.class).putExtra(mEntity, mData));
            }
        });
    }

    /**
     * 设置头部数据
     */
    private void setData() {
        if (TextUtils.isEmpty(mPageMark)) {
            tvIssueOrReceive.setText(Html.fromHtml(String.format(getString(R.string.text_red_envelopes_detail_received_total), "0.00")));
            tvCurrentFunds.setText(Html.fromHtml(String.format(getString(R.string.text_red_envelopes_detail_current_funds), "0.00")));
        } else {
            if (mData != null) {
                switch (mPageMark) {
                    /*收到的红包*/
                    case RECEIVED_RED_ENVELOPE:
                        tvIssueOrReceive.setText(Html.fromHtml(String.format(getString(R.string.text_red_envelopes_detail_received_total), TextUtils.isEmpty(mData.getReceiveNum()) ? "0" : mData.getReceiveNum())));
                        break;
                    /*我发的红包*/
                    case SENT_RED_ENVELOPE:
                        tvIssueOrReceive.setText(Html.fromHtml(String.format(getString(R.string.text_red_envelopes_detail_issued), TextUtils.isEmpty(mData.getIssueNum()) ? "0" : mData.getIssueNum())));
                        break;
                    default:
                }
                tvCurrentFunds.setText(Html.fromHtml(String.format(getString(R.string.text_red_envelopes_detail_current_funds), TextUtils.isEmpty(mData.getWalletMoney()) ? "0" : mData.getWalletMoney())));
            } else {
                switch (mPageMark) {
                    /*收到的红包*/
                    case RECEIVED_RED_ENVELOPE:
                        tvIssueOrReceive.setText(Html.fromHtml(String.format(getString(R.string.text_red_envelopes_detail_received_total), "0")));
                        break;
                    /*我发的红包*/
                    case SENT_RED_ENVELOPE:
                        tvIssueOrReceive.setText(Html.fromHtml(String.format(getString(R.string.text_red_envelopes_detail_issued), "0")));
                        break;
                    default:
                }
                tvCurrentFunds.setText(Html.fromHtml(String.format(getString(R.string.text_red_envelopes_detail_current_funds), "0.00")));
            }
        }
    }

}
