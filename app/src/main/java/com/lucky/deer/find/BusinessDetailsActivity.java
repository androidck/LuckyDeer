package com.lucky.deer.find;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.find.adapter.BusinessDetailsAdapter;
import com.lucky.deer.util.PhoneUtils;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.model.request.find.BusinessDetailsReq;
import com.lucky.model.response.home.cardLife.MyBusinessEntity;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import butterknife.BindView;
import xyz.yhsj.loadstatusview.LoadStatusView;

/**
 * 商户详情
 *
 * @author admin
 * @date 2018/12/3
 */
public class BusinessDetailsActivity extends BaseActivity {
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
    private BusinessDetailsAdapter mAdapter;
    private MyBusinessEntity data;
    private PublicDialog inistanceView;
    private QMUIDialog qmuiDialog;

    @Override
    protected int initLayout() {
        return R.layout.activity_business_details;
    }

    @Override
    protected void initView() {
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new BusinessDetailsAdapter();
        if (getSerializableData() != null) {
            data = (MyBusinessEntity) getSerializableData();
            initTopBar(topBar, data.getLevelName());
            mAdapter.setBusinessType(data.getType());
        }
        rvList.setAdapter(mAdapter);
        rvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        /*获取单利*/
        inistanceView = PublicDialog.getInstance();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        BusinessDetailsReq req = new BusinessDetailsReq();
        if (data != null) {
            req.setLevelId(data.getLevelId());
            req.setType(data.getType());
        }
//        if (srlRefresh.isRefreshing()) {
//            pageCurrent = 1;
//            req.setPageCurrent(pageCurrent);
//        } else if (pageCurrent > 1) {
//            req.setPageCurrent(pageCurrent);
//        } else {
//            showLoadingDialog();
//        }
        if (!srlRefresh.isRefreshing()) {
            showLoadingDialog();
        }
        mNetworkRequestInstance.getRecommendUserInfoList(req)
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        mAdapter.setNewData(returnData.getData());
//                        PerfectRepaymentListReq<BusinessDetailsEntity> list = returnData.getData();
//                        if (list != null) {
//                            if (req.getPageCurrent() == 1) {
//                                mAdapter.setNewData(list);
//                                if (list.size() >= req.getPageSize()) {
//                                    pageCurrent++;
//                                } else {
//                                    /*加载结束*/
//                                    mAdapter.loadMoreEnd(true);
//                                }
//                            } else if (list.size() >= req.getPageSize()) {
//                                mAdapter.addData(list);
//                                /*加载完成*/
//                                mAdapter.loadMoreComplete();
//                                pageCurrent++;
//                            } else {
//                                mAdapter.addData(list);
//                                /*加载结束*/
//                                mAdapter.loadMoreEnd(true);
//                            }
//                        }
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
//        /*上啦加载监听*/
//        mAdapter.setOnLoadMoreListener(this::initData, rvList);
        /*点击商户详情拨打监听*/
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if ("1".equals(mAdapter.getData().get(position).isShowPhone())) {
                /*初始化拨打客服电话弹出框*/
                qmuiDialog = inistanceView.setCustomizeView(
                        inistanceView.initEtOrTvView(mActivity,
                                mAdapter.getData().get(position).getPhone(),
                                "呼叫",
                                true),
                        true,
                        KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_5);
            }
        });
        /*电话拨打监听*/
        inistanceView.setOnClickListener((OnClickListener<String>) (status, useType, isPhoneNumber, text) -> {
            switch (status) {
                case CANCEL:
                    qmuiDialog.dismiss();
                    break;
                case OK:
                    qmuiDialog.dismiss();
                    PhoneUtils.callPhoneDirectly(mActivity, text);
                    break;
                default:
            }
        });
    }
}
