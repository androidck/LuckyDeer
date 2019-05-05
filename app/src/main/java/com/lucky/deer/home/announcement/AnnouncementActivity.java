package com.lucky.deer.home.announcement;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.home.announcement.adapter.AnnouncementAdapter;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import xyz.yhsj.loadstatusview.LoadStatusView;

/**
 * 公告
 *
 * @author wangxiangyi
 * @date 2019/01/16
 */
@Route(path = "/announcement/AnnouncementActivity")
public class AnnouncementActivity extends BaseActivity {
    /**
     * 标题
     */
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    /**
     * 下拉控件
     */
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    /**
     * 空白的面
     */
    @BindView(R.id.lsv_load_status)
    LoadStatusView lsvLoadStatus;
    /**
     * 列表
     */
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    /**
     * 列表适配器
     */
    private AnnouncementAdapter mAdapter;


    @Override
    protected int initLayout() {
        return R.layout.activity_announcement;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_announcement);
        /*设置列表为纵向*/
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new AnnouncementAdapter();
        rvList.setAdapter(mAdapter);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
//        BaseReq baseReq = new BaseReq();
//        if (srlRefresh.isRefreshing()) {
//            pageCurrent = 1;
//            baseReq.setPageCurrent(pageCurrent);
//        } else if (pageCurrent > 1) {
//            baseReq.setPageCurrent(pageCurrent);
//        } else {
//            showLoadingDialog();
//        }
        if (!srlRefresh.isRefreshing()) {
            showLoadingDialog();
        }
        /*获取轮播通知*/
        mNetworkRequestInstance.queryAppRollMessage()
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        mAdapter.setNewData(returnData.getData());
                    } else if (RequestUtils.isEmpty(returnData)) {
                        lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                    } else {
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                    }
                });
    }

    @Override
    protected void initListener() {
        /*下拉刷新监听*/
        srlRefresh.setOnRefreshListener(this::initData);
//        /*上啦加载监听*/
//        mAdapter.setOnLoadMoreListener(this::initData, rvList);
    }
}
