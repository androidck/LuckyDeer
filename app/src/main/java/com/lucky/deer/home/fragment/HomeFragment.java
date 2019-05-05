package com.lucky.deer.home.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseFragment;
import com.lucky.deer.common.WebViewActivity;
import com.lucky.deer.home.MainActivity;
import com.lucky.deer.home.adapter.FindArticleAdapter;
import com.lucky.deer.home.onlineApplication.OnlineApplicationActivity;
import com.lucky.deer.home.onlineApplication.OnlineLoanActivity;
import com.lucky.deer.home.onlineApplication.adapter.OnlineApplicationAdapter;
import com.lucky.model.common.WebViewBean;
import com.lucky.model.request.mine.HelpReq;
import com.lucky.model.response.home.OnlineApplicationEntity;
import com.lucky.model.response.home.OnlineLoanEntity;
import com.lucky.model.response.home.QueryBannerEntity;
import com.lucky.model.util.GlideUtils;
import com.lucky.model.util.HawkUtil;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页
 *
 * @author wangxiangyi
 * @date 2018/09/20
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.ly_bar)
    LinearLayout lyBar;
    /**
     * 下拉刷新布局
     */
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    /**
     * 列表
     */
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    MainActivity mainActivity;

    /**
     * 发现文章适配器
     */
    private FindArticleAdapter mFindArticleAdapter;
    /**
     * 轮播控件
     */
    XBanner xbBanner;
    /**
     * 在线办卡
     */
    private TextView tvOnlineCard;
    /**
     * 快速借款
     */
    private TextView tvFastLoan;
    /**
     * 卡生活
     */
    private TextView tvCardLife;
    /**
     * 了解一路金服产品
     */
    private TextView tvUnderstandingAllWayProduct;
    /**
     * 好卡推荐（查看更多）
     */
    private TextView tvGoodCardSeeMore;
    /**
     * 好卡推荐列表
     */
    private RecyclerView rvGoodCardList;
    /**
     * 全部贷款
     */
    private TextView tvLoanSupermarketSeeMore;
    /**
     * 超市贷款第一项
     */
    private LinearLayout llLoanSupermarket1;
    /**
     * 超市贷款第一项：最高金额
     */
    private TextView tvMaximumAmount1;
    /**
     * 超市贷款第一项：贷款名称
     */
    private TextView tvLoanName1;
    /**
     * 超市贷款第一项：月利率
     */
    private TextView tvMonthlyInterestRate1;
    /**
     * 超市贷款第二项
     */
    private LinearLayout llLoanSupermarket2;
    /**
     * 超市贷款第一项：最高金额
     */
    private TextView tvMaximumAmount2;
    /**
     * 超市贷款第一项：贷款名称
     */
    private TextView tvLoanName2;
    /**
     * 超市贷款第一项：月利率
     */
    private TextView tvMonthlyInterestRate2;
    /**
     * 网贷信息
     */
    private List<OnlineLoanEntity> data;
    private OnlineApplicationAdapter mAdapter;


    public static HomeFragment newInstance(Bundle bundle) {
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
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
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mainActivity = (MainActivity) getActivity();
        setStatusBarHeight(lyBar, getStatusBar());
        /*设置状态栏字体颜色为黑色*/
        QMUIStatusBarHelper.setStatusBarLightMode(mActivity);
        rvList.setLayoutManager(new LinearLayoutManager(mActivity));
        mFindArticleAdapter = new FindArticleAdapter();
        rvList.setAdapter(mFindArticleAdapter);
        /*解决数据加载完成后, 没有停留在顶部的问题*/
        rvList.setFocusable(false);
        /*添加分割线*/
        rvList.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        initHeaderLayout();
    }

    @SuppressLint("ResourceType")
    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        srlRefresh.setOnRefreshListener(this::queryAppRollMessage);
        /*文章监听*/
        mFindArticleAdapter.setOnItemClickListener((adapter, view, position) -> {
            HintUtil.showErrorWithToast(mActivity, getString(R.string.stay_tuned));
        });
        /*文章查看更多监听*/
        mFindArticleAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            HintUtil.showErrorWithToast(mActivity, getString(R.string.stay_tuned));
        });
    }

    /**
     * 初始化头部布局
     */
    private void initHeaderLayout() {
        /*获取计划详情信息头部*/
        View headerView = LinearLayout.inflate(mActivity, R.layout.fragment_header_home, null);
        getHeaderViewId(headerView);
        initHeaderListener();
        setHeaderData();
        if (!mFindArticleAdapter.isHeaderViewAsFlow()) {
            mFindArticleAdapter.removeAllHeaderView();
        }
        mFindArticleAdapter.addHeaderView(headerView);
    }

    /**
     * 初始化Header控件
     */
    private void getHeaderViewId(View headerView) {
        /*轮播控件*/
        xbBanner = headerView.findViewById(R.id.xb_banner);
        /*在线办卡*/
        tvOnlineCard = headerView.findViewById(R.id.tv_online_card);
        /*快速借款*/
        tvFastLoan = headerView.findViewById(R.id.tv_fast_loan);
        /*卡生活*/
        tvCardLife = headerView.findViewById(R.id.tv_card_life);
        /*了解一路金服产品*/
        tvUnderstandingAllWayProduct = headerView.findViewById(R.id.tv_understanding_all_way_product);
        /*好卡推荐（查看更多）*/
        tvGoodCardSeeMore = headerView.findViewById(R.id.tv_good_card_see_more);
        /*好卡推荐列表*/
        rvGoodCardList = headerView.findViewById(R.id.rv_good_card_list);
        /*全部贷款*/
        tvLoanSupermarketSeeMore = headerView.findViewById(R.id.tv_loan_supermarket_see_more);
        /*超市贷款第一项*/
        llLoanSupermarket1 = headerView.findViewById(R.id.ll_loan_supermarket1);
        /*超市贷款第一项：最高金额*/
        tvMaximumAmount1 = headerView.findViewById(R.id.tv_maximum_amount1);
        /*超市贷款第一项：贷款名称*/
        tvLoanName1 = headerView.findViewById(R.id.tv_loan_name1);
        /*超市贷款第一项：月利率*/
        tvMonthlyInterestRate1 = headerView.findViewById(R.id.tv_monthly_interest_rate1);
        /*超市贷款第二项*/
        llLoanSupermarket2 = headerView.findViewById(R.id.ll_loan_supermarket2);
        /*超市贷款第二项：最高金额*/
        tvMaximumAmount2 = headerView.findViewById(R.id.tv_maximum_amount2);
        /*超市贷款第二项：贷款名称*/
        tvLoanName2 = headerView.findViewById(R.id.tv_loan_name2);
        /*超市贷款第二项：月利率*/
        tvMonthlyInterestRate2 = headerView.findViewById(R.id.tv_monthly_interest_rate2);
        /*设置显示卡的方向为横向*/
        rvGoodCardList.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false));
        mAdapter = new OnlineApplicationAdapter(new ArrayList<>());
        rvGoodCardList.setAdapter(mAdapter);

    }

    /**
     * 初始化Header监听
     */
    private void initHeaderListener() {
        //加载广告图片
        xbBanner.loadImage((banner, model, view, position) -> {
            GlideUtils.loadImage(mActivity, (ImageView) view, ((QueryBannerEntity) model).getImageUrl());
        });
        xbBanner.setOnItemClickListener((banner, model, view, position) -> {
            if (model != null) {
                QueryBannerEntity queryBannerEntity = (QueryBannerEntity) model;
                if ("1".equals(queryBannerEntity.getType())) {
                    if (!TextUtils.isEmpty(queryBannerEntity.getLink())) {
                        Intent intent = new Intent(mActivity, WebViewActivity.class);
                        WebViewBean webViewBean = new WebViewBean();
                        webViewBean.setWebTitle(((QueryBannerEntity) model).getTitle());
                        webViewBean.setWebUrl(((QueryBannerEntity) model).getLink());
                        intent.putExtra(mEntity, webViewBean);
                        startActivity(intent);
                    }
                } else {
//                    HintUtil.showErrorWithToast(mActivity, getString(R.string.stay_tuned));
//                    Intent intent = new Intent(mActivity, WebViewActivity.class);
//                    WebViewBean webViewBean = new WebViewBean();
//                    webViewBean.setWebTitle(((QueryBannerEntity) model).getTitle());
//                    webViewBean.setWebUrl(((QueryBannerEntity) model).getLink());
//                    intent.putExtra(mEntity, webViewBean);
//                    startActivity(intent);
                }
            }
        });
        xbBanner.startAutoPlay();
        /*在线办卡*/
        tvOnlineCard.setOnClickListener(this);
        /*快速借款*/
        tvFastLoan.setOnClickListener(this);
        /*卡生活*/
        tvCardLife.setOnClickListener(this);
        /*了解一路金服产品*/
        tvUnderstandingAllWayProduct.setOnClickListener(this);
        /*好卡推荐（查看更多）*/
        tvGoodCardSeeMore.setOnClickListener(this);
        /*全部贷款*/
        tvLoanSupermarketSeeMore.setOnClickListener(this);
        /*超市贷款第一项*/
        llLoanSupermarket1.setOnClickListener(this);
        /*超市贷款第二项*/
        llLoanSupermarket2.setOnClickListener(this);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            jumpWebView(mAdapter.getData().get(position).getBankName(), mAdapter.getData().get(position).getAddress());
        });
    }

    @Override
    public void onClick(View v) {
        //获取当前的用户状态
        if (HawkUtil.getInstance().isContains(HawkUtil.USER_INFO)) {
            /*判断是否实名制或开卡成功*/
            if (getRegisterState(getUserInfo().getRegisterState())) {
                Class mClass = null;
                String mPassValue = null;
                switch (v.getId()) {
                    /*在线办卡*/
                    case R.id.tv_online_card:
                        mClass = OnlineApplicationActivity.class;
                        mPassValue = OnlineApplicationActivity.ALL_DATA;
                        break;
                    /*快速借款*/
                    case R.id.tv_fast_loan:
                        mClass = OnlineLoanActivity.class;
                        mPassValue = OnlineLoanActivity.ALL_DATA;
                        break;
                    /*卡生活*/
                    case R.id.tv_card_life:
                        HintUtil.showErrorWithToast(mActivity, getString(R.string.stay_tuned));
                        break;
                    /*了解一路金服产品*/
                    case R.id.tv_understanding_all_way_product:
                        HintUtil.showErrorWithToast(mActivity, getString(R.string.stay_tuned));
                        break;
                    /*好卡推荐（查看更多）*/
                    case R.id.tv_good_card_see_more:
                        mClass = OnlineApplicationActivity.class;
                        mPassValue = OnlineApplicationActivity.ALL_DATA;
                        break;
                    /*全部贷款*/
                    case R.id.tv_loan_supermarket_see_more:
                        mClass = OnlineLoanActivity.class;
                        mPassValue = OnlineLoanActivity.ALL_DATA;
                        break;
                    /*超市贷款第一项*/
                    case R.id.ll_loan_supermarket1:
                        if (data != null && data.size() >= 1) {
                            jumpWebView(data.get(0).getName(), data.get(0).getAddress());
                        }
                        break;
                    /*超市贷款第二项*/
                    case R.id.ll_loan_supermarket2:
                        if (data != null && data.size() >= 2) {
                            jumpWebView(data.get(1).getName(), data.get(1).getAddress());
                        }
                        break;
                    default:
                }
                /*跳转页面*/
                if (mClass != null) {
                    Intent intent = new Intent(mActivity, mClass);
                    if (!TextUtils.isEmpty(mPassValue)) {
                        intent.putExtra(mEntity, mPassValue);
                    }
                    startActivity(intent);
                }
            }
        } else {
            obtainLoginStatus();
        }
    }

    /**
     * 设置Header数据
     */
    private void setHeaderData() {
        queryAppRollMessage();
    }

    /**
     * 获取轮播信息
     */
    @SuppressLint("CheckResult")
    private void queryAppRollMessage() {
        if (!srlRefresh.isRefreshing()) {
            showLoadingDialog();
        }
        /*获取注册进件状态(并保存到用户信息里面)*/
        getRegistrationStatus();
        /*获取轮播广告*/
        mNetworkRequestInstance.queryBanner()
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        xbBanner.setData(returnData.getData(), null);
                    } else {
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                    }
                });
        HelpReq helpReq = new HelpReq();
        helpReq.setType("1");
        if (srlRefresh.isRefreshing()) {
            pageCurrent = 1;
            helpReq.setPageCurrent(pageCurrent);
        } else if (pageCurrent > 1) {
            helpReq.setPageCurrent(pageCurrent);
        } else {
            showLoadingDialog();
        }
        /*网申接口*/
        mNetworkRequestInstance.queryOnlineApplicationList(helpReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        if ("1".equals(returnData.getData().getHomeConfig())) {
                            tvCardLife.setVisibility(View.VISIBLE);
                            upDataDrawableTop(tvOnlineCard, R.mipmap.online_card);
                            upDataDrawableTop(tvFastLoan, R.mipmap.rapid_loan);
                        } else {
                            upDataDrawableTop(tvOnlineCard, R.mipmap.online_card_a);
                            upDataDrawableTop(tvFastLoan, R.mipmap.rapid_loan_a);
                            tvCardLife.setVisibility(View.GONE);
                        }
                        List<OnlineApplicationEntity> onlineApplicationList = returnData.getData().getOnlineApplicationList();
                        for (OnlineApplicationEntity item : onlineApplicationList) {
                            item.setItemType(OnlineApplicationAdapter.horizontal);
                        }
                        mAdapter.setNewData(onlineApplicationList);
                    } else {
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                    }
                });
        /*网贷接口*/
        mNetworkRequestInstance.queryOnlineLoanList(helpReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        if (returnData.getData() != null) {
                            data = returnData.getData();
                            if (returnData.getData().size() == 1) {
                                tvMaximumAmount1.setText(returnData.getData().get(0).getCreditQuota());
                                tvLoanName1.setText(returnData.getData().get(0).getName());
                                tvMonthlyInterestRate1.setText(Html.fromHtml(String.format(getString(R.string.home_text_monthly_interest_rate), returnData.getData().get(0).getDayInterestRate())));
                            } else if (returnData.getData().size() >= 2) {
                                tvMaximumAmount1.setText(returnData.getData().get(0).getCreditQuota());
                                tvLoanName1.setText(returnData.getData().get(0).getName());
                                tvMonthlyInterestRate1.setText(Html.fromHtml(String.format(getString(R.string.home_text_monthly_interest_rate), returnData.getData().get(0).getDayInterestRate())));
                                tvMaximumAmount2.setText(returnData.getData().get(1).getCreditQuota());
                                tvLoanName2.setText(returnData.getData().get(1).getName());
                                tvMonthlyInterestRate2.setText(Html.fromHtml(String.format(getString(R.string.home_text_monthly_interest_rate), returnData.getData().get(1).getDayInterestRate())));
                            }
                        }
                    } else {
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                    }
                });
        /*获取全部文章列表 */
        mNetworkRequestInstance.queryAppIntroduceCmsList(helpReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        mFindArticleAdapter.setNewData(returnData.getData());
//                        if (returnData.getData() != null) {
//                            if (helpReq.getPageCurrent() == 1) {
//                                mFindArticleAdapter.setNewData(returnData.getData());
//                                if (returnData.getData().size() >= helpReq.getPageSize()) {
//                                    pageCurrent++;
//                                } else {
//                                    /*加载结束*/
//                                    mFindArticleAdapter.loadMoreEnd(true);
//                                }
//                            } else if (returnData.getData().size() >= helpReq.getPageSize()) {
//                                mFindArticleAdapter.addData(returnData.getData());
//                                /*加载完成*/
//                                mFindArticleAdapter.loadMoreComplete();
//                                pageCurrent++;
//                            } else {
//                                mFindArticleAdapter.addData(returnData.getData());
//                                /*加载结束*/
//                                mFindArticleAdapter.loadMoreEnd();
//                            }
//                        }

                    } else {
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                    }
                });
//        /*获取轮播通知*/
//        mNetworkRequestInstance.queryAppRollMessage()
//                .subscribe(returnData -> {
//                    dismissLoadingDialog();
//                    srlRefresh.setRefreshing(false);
//                    if (RequestUtils.isRequestSuccess(returnData)) {
//                        StringBuffer stringBuffer = new StringBuffer();
//                        for (CarouselMessageEntity carouselMessageEntity : returnData.getData()) {
//                            stringBuffer.append(carouselMessageEntity.getMessage());
//                            stringBuffer.append("，");
//                        }
//                        tvAnnouncement.setText(stringBuffer.toString());
//                    } else {
//                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
//                    }
//                });
    }

    /**
     * 跳转网页
     *
     * @param title 标题
     * @param url   网页地址
     */
    public void jumpWebView(String title, String url) {
        WebViewBean webViewBean = new WebViewBean();
        webViewBean.setWebTitle(title);
        webViewBean.setWebUrl(url);
        startActivity(new Intent(mActivity, WebViewActivity.class).putExtra(mEntity, webViewBean));
    }



    /**
     * 动态设置DrawableTop图片
     *
     * @param view   控件
     * @param mipmap 更换的图片
     */
    private void upDataDrawableTop(TextView view, int mipmap) {
        Drawable drawable = getResources().getDrawable(mipmap);
        /*这一步必须要做,否则不会显示.*/
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        view.setCompoundDrawables(null, drawable, null, null);

    }
//    @OnClick({R.id.tv_d
// ivided_detail, R.id.tv_want_upgrade, R.id.tv_announcement,
//            R.id.tv_smart_collection, R.id.tv_date_repayment,
//            R.id.ll_financial_services, R.id.ll_domestic_services, R.id.ll_credit_card_knowledge,
//            R.id.ll_my_need_net_application, R.id.ll_want_borrow, R.id.ll_invite_friends})
//    public void onViewClicked(View view) {
//        Class mClass = null;
//        String mPassValue = null;
//        //获取当前的用户状态
//        boolean islogin = HawkUtil.getInstance().isContains(HawkUtil.USER_INFO);
//        if (islogin == false) {
//            obtainLoginStatus();
//        } else {
//            int state = ((UserInfo) HawkUtil.getInstance().getSaveData(HawkUtil.USER_INFO)).getRegisterState();
//            if (state == 1) {
//                //资料完善页面
//                new DataperfectiDialog(mActivity, state, "身份信息完善", R.style.UpdateAppDialog).show();
//            } else if (state == 2) {
//                new DataperfectiDialog(mActivity, state, "储蓄卡信息完善", R.style.UpdateAppDialog).show();
//            } else if (state == 3) {
//                new DataperfectiDialog(mActivity, state, "手持信息完善", R.style.UpdateAppDialog).show();
//            } else {
//                switch (view.getId()) {
//                    /*公告*/
//                    case R.id.tv_announcement:
//                        if (getRegisterState(getUserInfo().getRegisterState())) {
//                            mClass = AnnouncementActivity.class;
//                        } else {
//                            mClass = LoginActivity.class;
//                        }
//                        break;
//                    /*刷卡*/
//                    case R.id.tv_smart_collection:
//                        if (getLoginStatus()) {
//                            if (getRegisterState(getUserInfo().getRegisterState())) {
//                                mClass = CardSpendingActivity.class;
//                            }
//                        } else {
//                            mClass = LoginActivity.class;
//                        }
//                        break;
//                    /*日期养卡*/
//                    case R.id.tv_date_repayment:
//                        if (getLoginStatus()) {
//                            if (getRegisterState(getUserInfo().getRegisterState())) {
////                                    mClass = PepaymentPlanActivity.class;
//                                mClass = DateRepaymentActivity.class;
//                            }
//                        } else {
//                            mClass = LoginActivity.class;
//                        }
//                        break;
//                    /*我的分润改为完美养卡*/
//                    case R.id.tv_divided_detail:
//                        if (getLoginStatus()) {
////                            mClass = MyDividedOrWithdrawalDetailsActivity.class;
////                            mPassValue = MyDividedOrWithdrawalDetailsActivity.SEPARATION;
//                            if (getRegisterState(getUserInfo().getRegisterState())) {
//                                /*完美养卡*/
//                                mClass = PerfectRepaymentActivity.class;
//                            }
//                        } else {
//                            mClass = LoginActivity.class;
//                        }
//                        break;
//                    /*我的升级*/
//                    case R.id.tv_want_upgrade:
//                        if (getLoginStatus()) {
//                            if (getRegisterState(getUserInfo().getRegisterState())) {
//                                mClass = MemberCentreActivity.class;
//                            }
//                        } else {
//                            mClass = LoginActivity.class;
//                        }
//                        break;
//                    /*金融服务*/
//                    case R.id.ll_financial_services:
//                        mClass = FinancialServicesActivity.class;
//                        mPassValue = FinancialServicesActivity.FINANCIAL_SERVICES;
//                        break;
//                    /*生活服务*/
//                    case R.id.ll_domestic_services:
//                        mClass = FinancialServicesActivity.class;
//                        mPassValue = FinancialServicesActivity.DOMESTIC_SERVICES;
//                        break;
//                    /*信用卡知识*/
//                    case R.id.ll_credit_card_knowledge:
//                        mClass = FinancialServicesActivity.class;
//                        mPassValue = FinancialServicesActivity.CREDIT_CARD_KNOWLEDGE;
//                        break;
//                    /*我要网申*/
//                    case R.id.ll_my_need_net_application:
//                        if (getLoginStatus()) {
////                            Intent intent = new Intent(mActivity, WebViewActivity.class);
////                            WebViewBean webViewBean = new WebViewBean();
////                            webViewBean.setPageType(KeyConstant.MY_NEED_NET_APPLICATION);
////                            webViewBean.setWebTitle(getString(R.string.my_need_net_application));
////                            webViewBean.setWebUrl(HttpConstant.H5_MY_NEED_NET_APPLICATION);
////                            intent.putExtra(mEntity, webViewBean);
////                            startActivity(intent);
//                            mClass = OnlineApplicationActivity.class;
//                            mPassValue = OnlineApplicationActivity.ALL_DATA;
//                        } else {
//                            mClass = LoginActivity.class;
//                        }
//                        break;
//                    /*我要借贷*/
//                    case R.id.ll_want_borrow:
//                        if (getLoginStatus()) {
//                            mClass = OnlineLoanActivity.class;
////                            mPassValue = OnlineLoanActivity.RECOMMEND_DATA;
//                            mPassValue = OnlineLoanActivity.ALL_DATA;
//                        } else {
//                            mClass = LoginActivity.class;
//                        }
//                        break;
//                    /*邀请好友*/
//                    case R.id.ll_invite_friends:
//                        if (getLoginStatus()) {
//                            /*立即推广*/
//                            mainActivity.jumpFragment(KeyConstant.MEMBER_CENTRE);
//                        } else {
//                            mClass = LoginActivity.class;
//                        }
//                        break;
//                    default:
//                }
//            }
//        }
//        if (mClass != null) {
//            Intent intent = new Intent(mActivity, mClass);
//            if (!TextUtils.isEmpty(mPassValue)) {
//                intent.putExtra(mEntity, mPassValue);
//            }
//            startActivity(intent);
//        }
//    }




}
