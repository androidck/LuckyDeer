package com.lucky.deer.home.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseFragment;
import com.lucky.deer.home.onlineApplication.adapter.OnlineApplicationAdapter;
import com.lucky.deer.home.onlineApplication.adapter.OnlineArticleAdapter;
import com.lucky.deer.home.onlineApplication.adapter.OnlineNewApplicationAdapter;
import com.lucky.deer.home.onlineApplication.adapter.OnlineNewLoanAdapter;
import com.lucky.model.request.mine.HelpReq;
import com.lucky.model.response.home.CarouselMessageEntity;
import com.lucky.model.response.home.FindArticleEntity;
import com.lucky.model.response.home.OnlineApplicationEntity;
import com.lucky.model.response.home.OnlineLoanEntity;
import com.lucky.model.util.HawkUtil;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 新的fragment
 */
public class HomeNewFragment extends BaseFragment {
    @BindView(R.id.banner)
    ImageView banner;
    @BindView(R.id.btn_message)
    TextView tvAnnouncement;
    @BindView(R.id.tv_net_center)
    TextView tvNetCenter;
    @BindView(R.id.tv_net_supermarket)
    TextView tvNetSupermarket;
    @BindView(R.id.tv_life)
    TextView tvLife;
    @BindView(R.id.tv_credit_card_service)
    TextView tvCreditCardService;
    @BindView(R.id.tv_card_more)
    TextView tvCardMore;
    @BindView(R.id.good_card_recyclerView)
    RecyclerView goodCardRecyclerView;
    @BindView(R.id.tv_net_loan_more)
    TextView tvNetLoanMore;
    @BindView(R.id.net_loan_recyclerView)
    RecyclerView netLoanRecyclerView;
    @BindView(R.id.use_card_recyclerView)
    RecyclerView useCardRecyclerView;
    @BindView(R.id.tv_use_card)
    TextView tvUseCard;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;


    private OnlineNewApplicationAdapter mAdapter;
    private OnlineNewLoanAdapter adapter;
    private OnlineArticleAdapter articleAdapter;
    List<OnlineApplicationEntity> onlineApplicationList = new ArrayList<>();
    List<OnlineLoanEntity> onlineLoanEntities = new ArrayList<>();
    List<FindArticleEntity> findArticleEntities = new ArrayList<>();

    @Override
    protected int initlayout() {
        return R.layout.fragment_new_home;
    }

   /* @Override
    public boolean isStatusBarFontWhite() {
        return false;
    }*/

    @Override
    protected void initView() {
        QMUIStatusBarHelper.setStatusBarDarkMode(getActivity());

        netLoanRecyclerView.setNestedScrollingEnabled(false);
        useCardRecyclerView.setNestedScrollingEnabled(false);
        netLoanRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        useCardRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        netLoanRecyclerView.setNestedScrollingEnabled(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());  //LinearLayoutManager中定制了可扩展的布局排列接口，子类按照接口中的规范来实现就可以定制出不同排雷方式的布局了
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        goodCardRecyclerView.setLayoutManager(layoutManager);
        goodCardRecyclerView.setNestedScrollingEnabled(false);
        srlRefresh.setEnableLoadMore(false);
        tvAnnouncement.setMarqueeRepeatLimit(-1);
        srlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onlineApplicationList.clear();
                        onlineLoanEntities.clear();
                        findArticleEntities.clear();
                        getArticle();
                        getNetRecommend();
                        getNetApply();
                        queryAppRollMessage();
                        refreshLayout.finishRefresh();
                    }
                },300);
            }
        });

    }


    @Override
    protected void initData() {
        super.initData();
        mAdapter = new OnlineNewApplicationAdapter(getActivity(), onlineApplicationList);
        goodCardRecyclerView.setHasFixedSize(true);
        goodCardRecyclerView.setAdapter(mAdapter);
        goodCardRecyclerView.scrollToPosition(onlineApplicationList.size()*10);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(goodCardRecyclerView);



        adapter = new OnlineNewLoanAdapter(getActivity(), onlineLoanEntities);
        netLoanRecyclerView.setAdapter(adapter);

        articleAdapter = new OnlineArticleAdapter(getContext(), findArticleEntities);
        useCardRecyclerView.setAdapter(articleAdapter);

        getNetApply();
        getNetRecommend();
        getArticle();
        queryAppRollMessage();
    }

    @OnClick({R.id.tv_net_center, R.id.tv_net_supermarket, R.id.tv_life, R.id.tv_credit_card_service, R.id.tv_card_more, R.id.tv_net_loan_more, R.id.tv_use_card,R.id.btn_message})
    public void onViewClicked(View view) {
        if (HawkUtil.getInstance().isContains(HawkUtil.USER_INFO)) {
            if (getRegisterState(getUserInfo().getRegisterState())) {
                switch (view.getId()) {
                    case R.id.tv_net_center:
                        ARouter.getInstance()
                                .build("/netloan/NetLoanCenterActivity")
                                .withString("type", "2")
                                .navigation();
                        break;
                    case R.id.tv_net_supermarket:
                        ARouter.getInstance()
                                .build("/netloan/NetLoanSupermarketActivity")
                                .withString("type", "2")
                                .navigation();
                        break;
                    case R.id.tv_life:
                        ARouter.getInstance()
                                .build("/netloan/NearbyLifeActivity")
                                .navigation();
                        break;
                    case R.id.tv_credit_card_service:
                        ARouter.getInstance()
                                .build("/cardLife/CardLifeActivity")
                                .navigation();
                        break;
                    case R.id.tv_card_more:
                        ARouter.getInstance()
                                .build("/netloan/NetLoanCenterActivity")
                                .withString("type", "1")
                                .navigation();
                        break;
                    case R.id.tv_net_loan_more:
                        ARouter.getInstance()
                                .build("/netloan/NetLoanSupermarketActivity")
                                .withString("type", "1")
                                .navigation();
                        break;
                    case R.id.tv_use_card:
                        ARouter.getInstance()
                                .build("/netloan/NetLoanArticleActivity")
                                .withString("type", "2")
                                .navigation();
                        break;
                    case R.id.btn_message:
                        ARouter.getInstance()
                                .build("/announcement/AnnouncementActivity")
                                .navigation();
                        break;
                }
            }
        } else {
            obtainLoginStatus();
        }
    }


    //获取信用卡网申
    public void getNetApply() {
        HelpReq helpReq = new HelpReq();
        helpReq.setType("1");
        mNetworkRequestInstance.queryOnlineApplicationList(helpReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        if (returnData.getData().getHomeConfig().equals("1")){
                            tvCreditCardService.setVisibility(View.VISIBLE);
                        }else {
                            tvCreditCardService.setVisibility(View.GONE);
                        }
                        onlineApplicationList.addAll(returnData.getData().getOnlineApplicationList());
                        mAdapter.notifyDataSetChanged();
                    } else {
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());

                    }
                });


    }

    //获取网贷推荐
    public void getNetRecommend() {
        HelpReq helpReq = new HelpReq();
        helpReq.setType("1");
        /*网贷接口*/
        mNetworkRequestInstance.queryOnlineLoanList(helpReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        onlineLoanEntities.addAll(returnData.getData());
                        adapter.notifyDataSetChanged();
                    } else {
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                    }
                });
    }

    //获取文章
    public void getArticle() {
        HelpReq helpReq = new HelpReq();
        helpReq.setType("1");
        /*获取全部文章列表 */
        mNetworkRequestInstance.queryAppIntroduceCmsList(helpReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        findArticleEntities.addAll(returnData.getData());
                        articleAdapter.notifyDataSetChanged();
                    } else {
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                    }
                });
    }


    /**
     * 获取轮播信息
     */
    @SuppressLint("CheckResult")
    private void queryAppRollMessage() {
        /*获取轮播通知*/
        mNetworkRequestInstance.queryAppRollMessage()
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        StringBuffer stringBuffer = new StringBuffer();
                        for (CarouselMessageEntity carouselMessageEntity : returnData.getData()) {
                            stringBuffer.append(carouselMessageEntity.getMessage());
                            stringBuffer.append("，");
                        }
                        tvAnnouncement.setText(stringBuffer.toString());
                    } else {
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                    }
                });
    }

}
