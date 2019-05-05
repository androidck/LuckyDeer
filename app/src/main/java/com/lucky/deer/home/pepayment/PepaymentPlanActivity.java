package com.lucky.deer.home.pepayment;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.home.pepayment.adapter.PepaymentPlanListAdapter;
import com.lucky.deer.home.pepayment.adapter.PepaymentPlanMenuAdapter;
import com.lucky.deer.home.pepayment.date.DateRepaymentActivity;
import com.lucky.model.common.pepayment.PepaymentPlanMenuEntity;
import com.lucky.model.request.BaseReq;
import com.lucky.model.response.home.pepayment.PepaymentPlanList;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import xyz.yhsj.loadstatusview.LoadStatusView;

/**
 * 还款计划
 *
 * @author wangxiangyi
 * @date 2018/12/11
 */
public class PepaymentPlanActivity extends BaseActivity {
    /**
     * 获取当前（还款计划）Activity
     */
    @SuppressLint("StaticFieldLeak")
    public static PepaymentPlanActivity mPepaymentPlanActivity;
    /**
     * 标题
     */
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    /**
     * 刷新功能
     */
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    /**
     * 菜单列表
     */
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    /**
     * 空白页布局
     */
    @BindView(R.id.lsv_load_status)
    LoadStatusView lsvLoadStatus;
    /**
     * 计划列表
     */
    @BindView(R.id.rv_list_vertical)
    RecyclerView rvListVertical;
    /**
     * 菜单列表适配器
     */
    private PepaymentPlanMenuAdapter mAdapterMenu;
    /**
     * 计划列表适配器
     */
    private PepaymentPlanListAdapter mAdapterList;


    @Override
    protected int initLayout() {
        return R.layout.activity_pepayment_plan;
    }

    @Override
    protected void initView() {
        mPepaymentPlanActivity = this;
        initTopBar(topBar, R.string.title_activity_pepayment_plan);
        /*菜单列表初始化*/
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvList.setLayoutManager(linearLayoutManager);
        mAdapterMenu = new PepaymentPlanMenuAdapter();
        rvList.setAdapter(mAdapterMenu);
        /*计划列表初始化*/
        rvListVertical.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapterList = new PepaymentPlanListAdapter();
        rvListVertical.setAdapter(mAdapterList);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void initData() {
        /*日期还款*/
        List<PepaymentPlanMenuEntity> menuList = new ArrayList<>();
        TypedArray menuBackground = getResources().obtainTypedArray(R.array.menu_background);
        TypedArray menuImage = getResources().obtainTypedArray(R.array.menu_image);
        menuList.add(new PepaymentPlanMenuEntity(
                menuBackground.getResourceId(1, 0),
                menuImage.getResourceId(1, 0),
                getdata(R.array.menu_title)[1],
                "用户输入账单日、还款日，以及金额，我们会根据账单日与还款日计算出可选择还款时间帮助用户完成还款,还款日与账单日不超过25天且不少于16天才能进行还款。"));
        menuBackground.recycle();
        menuImage.recycle();
        mAdapterMenu.addData(menuList);
        /*多个还款方式*/
//        List<PepaymentPlanMenuEntity> menuList = new ArrayList<>();
//        TypedArray menuBackground = getResources().obtainTypedArray(R.array.menu_background);
//        TypedArray menuImage = getResources().obtainTypedArray(R.array.menu_image);
//        for (int i = 0; i < getdata(R.array.menu_title).length; i++) {
//            menuList.add(new PepaymentPlanMenuEntity(
//                    menuBackground.getResourceId(i, 0),
//                    menuImage.getResourceId(i, 0),
//                    getdata(R.array.menu_title)[i],
//                    getdata(R.array.menu_content)[i])
//            );
//        }
//        menuBackground.recycle();
//        menuImage.recycle();
//        mAdapterMenu.addData(menuList);

        getPlanList();
    }

    @Override
    protected void initListener() {
        srlRefresh.setOnRefreshListener(this::getPlanList);
        mAdapterList.setOnLoadMoreListener(this::getPlanList, rvListVertical);
        /*还款菜单点击监听*/
        mAdapterMenu.setOnItemClickListener((adapter, view, position) -> {
            Class mClass = null;
//            switch (position) {
//                /*自定义还款*/
//                case 0:
//                    break;
//                /*日期还款*/
//                case 1:
//                    mClass = DateRepaymentActivity.class;
//                    break;
//                /*余额还款*/
//                case 2:
//                    break;
//                /*完美还款*/
//                case 3:
//                    break;
//                default:
//            }
            mClass = DateRepaymentActivity.class;
            if (mClass != null) {
                jumpActivity(mContext, mClass);
            } else {
                HintUtil.showErrorWithToast(mContext, R.string.stay_tuned);
            }
        });
        /*计划列表点击监听*/
        mAdapterList.setOnItemClickListener((adapter, view, position) -> {
            jumpActivity(mContext, PlanDetailsActivity.class, mAdapterList.getData().get(position));
        });
    }

    /**
     * 获取我的计划列表
     */
    @SuppressLint("CheckResult")
    public void getPlanList() {
        BaseReq baseReq = new BaseReq();
        if (srlRefresh.isRefreshing()) {
            pageCurrent = 1;
            baseReq.setPageCurrent(pageCurrent);
        } else if (pageCurrent > 1) {
            baseReq.setPageCurrent(pageCurrent);
        } else {
            showLoadingDialog();
        }


        mNetworkRequestInstance.listMyPlan(baseReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        List<PepaymentPlanList> list = returnData.getData().getList();
                        if (list != null) {
                            if (baseReq.getPageCurrent() == 1) {
                                mAdapterList.setNewData(list);
                                if (list.size() >= baseReq.getPageSize()) {
                                    pageCurrent++;
                                } else {
                                    /*加载结束*/
                                    mAdapterList.loadMoreEnd(true);
                                }
                            } else if (list.size() >= baseReq.getPageSize()) {
                                mAdapterList.addData(list);
                                /*加载完成*/
                                mAdapterList.loadMoreComplete();
                                pageCurrent++;
                            } else {
                                mAdapterList.addData(list);
                                /*加载结束*/
                                mAdapterList.loadMoreEnd();
                            }
                        }
                    } else if (RequestUtils.isEmpty(returnData)) {
                        lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                        mAdapterList.notifyDataSetChanged();
                    } else {
                        if (pageCurrent > 1) {
                            /*加载失败*/
                            mAdapterList.loadMoreFail();
                        }
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }
}
