package com.lucky.deer.home.netloan;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.home.onlineApplication.adapter.OnlineArticleAdapter;
import com.lucky.model.request.mine.HelpReq;
import com.lucky.model.response.home.FindArticleEntity;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Route(path = "/netloan/NetLoanArticleActivity")
public class NetLoanArticleActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.ll_top_bar_background)
    LinearLayout llTopBarBackground;
    @BindView(R.id.recyclerView)
    SwipeRecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private OnlineArticleAdapter articleAdapter;


    @Autowired
    String type;
    List<FindArticleEntity> findArticleEntities=new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_loan_center;
    }

    @Override
    protected void initView() {
        initTopBar(topBar,"用卡攻略");
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        View headView=getLayoutInflater().inflate(R.layout.activity_loan_center_head,recyclerView,false);
        recyclerView.addHeaderView(headView);

        articleAdapter = new OnlineArticleAdapter(mContext,findArticleEntities);
        recyclerView.setAdapter(articleAdapter);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageCurrent++;
                        getArticle();
                        refreshLayout.finishLoadMore();
                    }
                },150);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        findArticleEntities.clear();
                        pageCurrent=1;
                        getArticle();
                        refreshLayout.finishRefresh();
                    }
                },150);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        getArticle();
    }

    //获取文章
    public void getArticle() {
        HelpReq helpReq = new HelpReq();
        helpReq.setType(type);
        helpReq.setPageCurrent(pageCurrent);
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
}
