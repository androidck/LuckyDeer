package com.lucky.deer.mine.salesman;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.common.WebViewActivity;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.mine.salesman.adapter.RecruitmentInfoAdapter;
import com.lucky.deer.view.RecycleViewDivider;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.model.common.WebViewBean;
import com.lucky.model.request.BaseReq;
import com.lucky.model.request.mine.PerfectInfoReq;
import com.lucky.model.response.mine.RecruitmentInfoList;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import xyz.yhsj.loadstatusview.LoadStatusView;


/**
 * 招聘信息
 *
 * @author wangxiangyi
 * @date 2018/12/19
 */
public class RecruitmentInfoListActivity extends BaseActivity {
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
     * 空白布局
     */
    @BindView(R.id.lsv_load_status)
    LoadStatusView lsvLoadStatus;
    /**
     * 列表
     */
    @BindView(R.id.rv_list)
    SwipeRecyclerView rvList;
    /**
     * 发布信息按钮
     */
    @BindView(R.id.tv_release_info)
    TextView tvReleaseInfo;
    private PublicDialog inistanceView;
    private RecruitmentInfoAdapter mAdapter;
    /**
     * 删除监听
     */
    private SwipeMenuBridge mMenuBridge;
    /**
     * 删除对话框
     */
    private QMUIDialog deleteDialog;
    /**
     * 删除的位置
     */
    private int mPosition;


    @Override
    protected int initLayout() {
        return R.layout.activity_recruitment_info_list;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_recruitment_info);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        /*获取单利*/
        inistanceView = PublicDialog.getInstance();
        mAdapter = new RecruitmentInfoAdapter();
        /*添加侧滑按钮*/
        rvList.setSwipeMenuCreator((swipeLeftMenu, swipeRightMenu, viewType) -> {
            /*添加菜单到右侧。*/
            swipeRightMenu.addMenuItem(new SwipeMenuItem(mContext)
                    .setBackgroundColor(getResources().getColor(R.color.colorRed))
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                    .setText(R.string.delete)
                    .setTextColorResource(R.color.white)
                    .setWidth(getResources().getDimensionPixelSize(R.dimen.dp_70)));
        });
       /* *//*点击监听*//*
        rvList.setSwipeMenuItemClickListener(menuBridge -> {
            mMenuBridge = menuBridge;
            deleteDialog = inistanceView.setCustomizeView(
                    inistanceView.initTitleEtOrTvView(
                            mContext, getString(R.string.dialog_prompt),
                            getString(R.string.dialog_is_delete_job_postings),
                            true),
                    KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_4);
            mPosition = menuBridge.getAdapterPosition();
        });*/
        rvList.setAdapter(mAdapter);
        /*设置列表分割线*/
        rvList.addItemDecoration(new RecycleViewDivider(mContext, DividerItemDecoration.VERTICAL, 10, R.color.color_background));
        View view = LayoutInflater.from(mContext).inflate(R.layout.public_text_view, null);
        rvList.addFooterView(view);
        view.setOnClickListener(v -> {
            jumpActivity(mContext, RecruitmentInfoActivity.class);
        });
        tvReleaseInfo.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListUserForRecruits();
    }

    @Override
    protected void initListener() {
        /*下拉刷新监听*/
        srlRefresh.setOnRefreshListener(this::getListUserForRecruits);
        /*上啦加载监听*/
        mAdapter.setOnLoadMoreListener(this::getListUserForRecruits, rvList);
        /*删除提示框点击监听*/
        inistanceView.setOnClickListener((status,useType, isPhoneNumber, text) -> {
            switch (status) {
                case OK:
                    removeItem(mPosition);
                    if (mMenuBridge != null) {
                        mMenuBridge.closeMenu();
                    }
                    break;
                default:
            }
            if (deleteDialog != null && deleteDialog.isShowing()) {
                deleteDialog.dismiss();
            }
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            String recruitmentTitle = "";
            String[] stringArray = getdata(R.array.recruitment_position_list);
            if (stringArray.length > 0 &&
                    !TextUtils.isEmpty(mAdapter.getData().get(position).getRecruitJob()) &&
                    Integer.parseInt(mAdapter.getData().get(position).getRecruitJob()) > 0 &&
                    (Integer.parseInt(mAdapter.getData().get(position).getRecruitJob()) - 1) <= stringArray.length) {
                recruitmentTitle = stringArray[Integer.parseInt(mAdapter.getData().get(position).getRecruitJob()) - 1];
            }
            WebViewBean webViewBean = new WebViewBean();
            webViewBean.setWebTitle(recruitmentTitle);
            webViewBean.setWebUrl(HttpConstant.H5_RECRUITMENT_DETAILS + mAdapter.getData().get(position).getId());
            jumpActivity(mContext, WebViewActivity.class, webViewBean);
        });
    }

    /**
     * 获取招聘信息列表
     */
    @SuppressLint("CheckResult")
    private void getListUserForRecruits() {
        BaseReq baseReq = new BaseReq();
        if (srlRefresh.isRefreshing()) {
            pageCurrent = 1;
            baseReq.setPageCurrent(pageCurrent);
        } else if (pageCurrent > 1) {
            baseReq.setPageCurrent(pageCurrent);
        } else {
            showLoadingDialog();
        }
        mNetworkRequestInstance.listUserForRecruits(baseReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        List<RecruitmentInfoList> list = returnData.getData().getList();
                        if (list != null) {
                            tvReleaseInfo.setVisibility(View.GONE);
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
                            tvReleaseInfo.setVisibility(View.VISIBLE);
                        }
                    } else if (RequestUtils.isEmpty(returnData)) {
                        tvReleaseInfo.setVisibility(View.VISIBLE);
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

    @OnClick(R.id.tv_release_info)
    public void onViewClicked(View view) {
        jumpActivity(mContext, RecruitmentInfoActivity.class);
    }

    /**
     * 删除招聘信息
     *
     * @param position 要删除的位置
     */
    @SuppressLint("CheckResult")
    private void removeItem(int position) {
        PerfectInfoReq perfectInfoReq = new PerfectInfoReq();
        perfectInfoReq.setId(mAdapter.getData().get(position).getId());
        showLoadingDialog();
        mNetworkRequestInstance.deleteRecruit(perfectInfoReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        showSuccessDialog(R.string.dialog_delete_success);
                        mAdapter.remove(position);
                        if (mAdapter.getData().size() <= 0) {
                            lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                            tvReleaseInfo.setVisibility(View.VISIBLE);
                        }
                    } else {
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }
}
