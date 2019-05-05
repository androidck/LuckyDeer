package com.lucky.deer.mine.message;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.mine.adapter.MessageBoardAdapter;
import com.lucky.deer.util.PhoneUtils;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.model.request.BaseReq;
import com.lucky.model.request.mine.MessageBoardReq;
import com.lucky.model.response.mine.MessageBoardList;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.util.List;

import butterknife.BindView;
import xyz.yhsj.loadstatusview.LoadStatusView;

/**
 * 留言板
 *
 * @author admin
 * @date 2018/11/30
 */
public class MessageBoardActivity extends BaseActivity {
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
    private MessageBoardAdapter mAdapter;
    private PublicDialog inistanceView;
    private QMUIDialog qmuiDialog;

    @Override
    protected int initLayout() {
        return R.layout.activity_message_board;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_message_board);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new MessageBoardAdapter();
        rvList.setAdapter(mAdapter);
        rvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        /*获取单利*/
        inistanceView = PublicDialog.getInstance();
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
        mNetworkRequestInstance.queryMessageBoard(baseReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        List<MessageBoardList> list = returnData.getData().getList();
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
        /*点击某一条监听*/
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            setViewStatus(position);
        });
        /*点击电话监听*/
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            /*初始化拨打客服电话弹出框*/
            qmuiDialog = inistanceView.setCustomizeView(
                    inistanceView.initEtOrTvView(mActivity,
                            mAdapter.getData().get(position).getPhone(),
                            "呼叫",
                            true),
                    true,
                    KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_5);
        });
        /*获取修改昵称监听*/
        inistanceView.setOnClickListener((OnClickListener<String>) (status, useType,isPhoneNumber, text) -> {
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

    /**
     * 修改状态
     *
     * @param position
     */
    @SuppressLint("CheckResult")
    private void setViewStatus(int position) {
        if ("1".equals(mAdapter.getData().get(position).getReadState())) {
//            jumpActivity(mContext, MessageDetailsActivity.class, mAdapter.getData().get(position));
        } else {
            MessageBoardReq data = new MessageBoardReq();
            data.setId(mAdapter.getData().get(position).getId());
            showLoadingDialog();
            mNetworkRequestInstance.updateReadState(data)
                    .subscribe(returnData -> {
                        dismissLoadingDialog();
                        if (RequestUtils.isRequestSuccess(returnData)) {
                            mAdapter.getData().get(position).setReadState("1");
                            mAdapter.notifyDataSetChanged();
//                            jumpActivity(mContext, MessageDetailsActivity.class, mAdapter.getData().get(position));
                        } else {
                            HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                        }
                    });
        }
    }
}
