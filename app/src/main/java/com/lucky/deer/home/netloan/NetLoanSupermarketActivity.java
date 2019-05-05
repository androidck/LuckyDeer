package com.lucky.deer.home.netloan;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.home.onlineApplication.adapter.OnlineNewLoanAdapter;
import com.lucky.model.request.mine.HelpReq;
import com.lucky.model.response.home.OnlineLoanEntity;
import com.lucky.model.util.GlideUtils;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *  网贷超市
 */
@Route(path = "/netloan/NetLoanSupermarketActivity")
public class NetLoanSupermarketActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.ll_top_bar_background)
    LinearLayout llTopBarBackground;
    @BindView(R.id.recyclerView)
    SwipeRecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    List<OnlineLoanEntity> onlineLoanEntities=new ArrayList<>();
    private OnlineNewLoanAdapter adapter;

    ImageView imgBannerHead;

    @Autowired
    String type;

    @Override
    protected int initLayout() {
        return R.layout.activity_loan_center;
    }

    @Override
    protected void initView() {

        initTopBar(topBar,"网贷超市");

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        View view=getLayoutInflater().inflate(R.layout.activity_loan_center_head,recyclerView,false);
        imgBannerHead=view.findViewById(R.id.img_banner_head);
        GlideUtils.loadImage(mContext,imgBannerHead,R.mipmap.netloan_banner);
        recyclerView.addHeaderView(view);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageCurrent++;
                        getNetRecommend();
                        refreshLayout.finishLoadMore();
                    }
                },150);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onlineLoanEntities.clear();
                        pageCurrent=1;
                        getNetRecommend();
                        refreshLayout.finishRefresh();
                        refreshLayout.setEnableLoadMore(true);
                    }
                },150);
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        adapter=new OnlineNewLoanAdapter(mContext,onlineLoanEntities);
        recyclerView.setAdapter(adapter);
        getNetRecommend();
    }


    /**
     * 网贷
     */
    public void getNetRecommend(){
        HelpReq helpReq = new HelpReq();
        helpReq.setType(type);
        helpReq.setPageCurrent(pageCurrent);
        /*网贷接口*/
        mNetworkRequestInstance.queryOnlineLoanList(helpReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        onlineLoanEntities.addAll(returnData.getData());
                        adapter.notifyDataSetChanged();
                    } else {
                        if (returnData.getMsg().equals("暂无数据")){
                            HintUtil.showErrorWithToast(mActivity,"数据已加载完成");
                            refreshLayout.setEnableLoadMore(false);
                        }else {
                            HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                        }

                    }
                });
    }
}
