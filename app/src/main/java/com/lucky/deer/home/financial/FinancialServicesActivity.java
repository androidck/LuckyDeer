package com.lucky.deer.home.financial;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.dykj.requestcore.util.RequestUtils;
import com.lucky.mapfeatures.positioning.MapLocationListener;
import com.lucky.mapfeatures.positioning.PositioningFeatures;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.common.WebViewActivity;
import com.lucky.deer.home.adapter.FinancialServicesAdapter;
import com.lucky.deer.home.map.CreditCounterActivity;
import com.lucky.model.common.WebViewBean;
import com.lucky.model.request.home.AllMenuTreeDateReq;
import com.lucky.model.response.financial.FinancialServicesEntity;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 金融服务
 *
 * @author wangxiangyi
 * @date 2018/11/07
 */
public class FinancialServicesActivity extends BaseActivity {
    /**
     * 金融服务
     */
    public static String FINANCIAL_SERVICES = "1000";
    /**
     * 生活服务
     */
    public static String DOMESTIC_SERVICES = "2000";
    /**
     * 信用卡基础知识
     */
    public static String CREDIT_CARD_KNOWLEDGE = "3000";

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    List<FinancialServicesEntity> dataLists = new ArrayList<>();
    private FinancialServicesAdapter adapter;
    /**
     * 展示页面标识<p>
     * FINANCIAL_SERVICES：金融服务<p>
     * DOMESTIC_SERVICES：生活服务<p>
     * DOMESTIC_SERVICES：信用卡基础知识
     */
    private String stringData;

    @Override
    protected int initLayout() {
        return R.layout.activity_financial_services;
    }

    @Override
    protected void initView() {
        if (getStringData() != null) {
            stringData = getStringData();
            if (FINANCIAL_SERVICES.equals(getStringData())) {
                initTopBar(topBar, R.string.title_activity_financial_services);
            } else if (DOMESTIC_SERVICES.equals(getStringData())) {
                initTopBar(topBar, R.string.title_activity_domestic_services);
            } else {
                initTopBar(topBar, R.string.title_activity_credit_card_knowledge);
            }
        }
        rvList.setLayoutManager(new GridLayoutManager(mContext, 3));
        adapter = new FinancialServicesAdapter(dataLists);
        rvList.setAdapter(adapter);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        AllMenuTreeDateReq allMenuTreeDateReq = new AllMenuTreeDateReq();
        allMenuTreeDateReq.setParentId(stringData);
        /*获取二级菜单*/
        allMenuTreeDateReq.setLevel("2");
        if (!srlRefresh.isRefreshing()) {
            showLoadingDialog();
        }
        mNetworkRequestInstance.menuTreeDate(allMenuTreeDateReq)
                .subscribe(listResponseData -> {
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    if (RequestUtils.isRequestSuccess(listResponseData)) {
                        dataLists.clear();
                        for (FinancialServicesEntity itemTitle : listResponseData.getData()) {
                            dataLists.add(new FinancialServicesEntity(true, itemTitle.getMenuName()));
                            if (itemTitle.getList() != null && itemTitle.getList().size() > 0) {
                                for (FinancialServicesEntity itemfeatures : itemTitle.getList()) {
                                    FinancialServicesEntity featuresArray = new FinancialServicesEntity(false, itemTitle.getMenuName(), itemfeatures.getIcon(), itemfeatures.getMenuName());
                                    featuresArray.setId(itemfeatures.getId());
                                    featuresArray.setParentId(itemfeatures.getParentId());
                                    featuresArray.setParentIds(itemfeatures.getParentIds());
                                    featuresArray.isValid(itemfeatures.isValid());
                                    featuresArray.setLinkHref(itemfeatures.getLinkHref());
                                    featuresArray.setJumpMark(itemfeatures.getJumpMark());
                                    featuresArray.setUserPlace(itemfeatures.getUserPlace());
                                    dataLists.add(featuresArray);
                                }
                            }
                        }
                        adapter.setNewData(dataLists);
                    } else {
                        HintUtil.showErrorWithToast(mContext, listResponseData.getMsg());
                    }
                });
    }

    @Override
    protected void initListener() {
        srlRefresh.setOnRefreshListener(this::initData);
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.ll_function) {
                setPageJump((FinancialServicesEntity) adapter.getData().get(position));
            }
        });
    }

    /**
     * 跳转页面
     *
     * @param entity 点击信息
     */
    private void setPageJump(FinancialServicesEntity entity) {
        /*遍历功能列表信息*/
        for (FinancialServicesEntity dataList : dataLists) {
            /*判断当前点击的位置*/
            if (entity.getId().equals(dataList.getId())) {
                /*判断是否是网页*/
                if (TextUtils.isEmpty(entity.getLinkHref())) {
                    if (TextUtils.isEmpty(entity.getJumpMark())) {
                        HintUtil.showErrorWithToast(mContext, getString(R.string.stay_tuned));
                    } else {
                        Class mClass = null;
                        switch (entity.getJumpMark()) {
                            /*金融服务：柜台征信*/
                            case "1":
                                mClass = CreditCounterActivity.class;
                                break;
                            /*  在线放款：银行贷款*/
                            case "2":
                                /*基本知识：客服电话*/
                            case "3":
                                /*基本知识：进度查询 */
                            case "4":
                                mClass = PublicMenuListActivity.class;
                                break;
                            default:
                        }
                        if (mClass != null) {
                            jumpActivity(mContext, mClass, entity);
                        }
                    }
                } else if ("银行贷款专员".equals(entity.getMenuName()) ||
                        "银行信用卡专员".equals(entity.getMenuName()) ||
                        "金融机构专员".equals(entity.getMenuName()) ||
                        "小小抵押贷".equals(entity.getMenuName())) {

                    showLoadingDialog(R.string.dialog_getting_targeting);
                    /*提交数据时开启定位*/
                    isOpenIntervalPositioning(mContext,
                            false,
                            3,
                            new MapLocationListener() {
                                @Override
                                public void onLocationSuccess(AMapLocation amapLocation, int scenesUsed) {
                                    dismissLoadingDialog();
                                    if (scenesUsed == 3) {
                                        if (amapLocation != null &&
                                                amapLocation.getLongitude() > 0 &&
                                                amapLocation.getLatitude() > 0) {
                                            /*跳转链接*/
                                            WebViewBean webViewBean = new WebViewBean();
                                            webViewBean.setWebTitle(entity.getMenuName());
                                            webViewBean.setWebUrl(entity.getLinkHref() + "&lng=" + amapLocation.getLongitude() + "&lat=" + amapLocation.getLatitude());
                                            jumpActivity(mContext, WebViewActivity.class, webViewBean);
                                        } else {
                                            showFailedDialog(R.string.dialog_positioning_failure);
                                            PositioningFeatures.getInstance(mContext).stopLocation();
                                        }
                                    }
                                }

                                @Override
                                public void onLocationFailure() {
                                    dismissLoadingDialog();
                                    PositioningFeatures.getInstance(mContext).stopLocation();
                                    showFailedDialog(R.string.dialog_positioning_failure);
                                }
                            });
                    break;
                } else {
                    /*跳转链接*/
                    WebViewBean webViewBean = new WebViewBean();
                    webViewBean.setWebTitle(dataList.getMenuName());
                    webViewBean.setWebUrl(dataList.getLinkHref());
                    jumpActivity(mContext, WebViewActivity.class, webViewBean);
                    break;
                }
            }
        }

    }

}
