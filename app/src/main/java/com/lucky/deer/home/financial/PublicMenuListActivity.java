package com.lucky.deer.home.financial;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.common.WebViewActivity;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.home.adapter.PublicMenuListAdapter;
import com.lucky.deer.util.PhoneUtils;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.model.common.WebViewBean;
import com.lucky.model.request.home.AllMenuTreeDateReq;
import com.lucky.model.response.financial.FinancialServicesEntity;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import butterknife.BindView;

/**
 * 公共列表
 *
 * @author wangxiangyi
 * @date 2018/11/13
 */
public class PublicMenuListActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    private PublicMenuListAdapter menuListAdapter;
    private FinancialServicesEntity entity;
    private PublicDialog instance;
    private QMUIDialog qmuiDialog;

    @Override
    protected int initLayout() {
        return R.layout.activity_public_menu_list;
    }

    @Override
    protected void initView() {
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        menuListAdapter = new PublicMenuListAdapter();
        if (getSerializableData() != null) {
            entity = (FinancialServicesEntity) getSerializableData();
            initTopBar(topBar, entity.getMenuName());
            /*判断是否是电话客服*/
            switch (entity.getJumpMark()) {
                /*客服电话*/
                case "3":
                    menuListAdapter.isShowPhone(true);
                    break;
                default:
                    menuListAdapter.isShowPhone(false);
                    break;
            }
        }
        rvList.setAdapter(menuListAdapter);
        rvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        /*获取单利*/
        instance = PublicDialog.getInstance();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        AllMenuTreeDateReq allMenuTreeDateReq = new AllMenuTreeDateReq();
        /*设置查询id*/
        allMenuTreeDateReq.setParentId(entity == null ? "" : entity.getId());
        /*获取一级菜单*/
        allMenuTreeDateReq.setLevel("1");
        if (!srlRefresh.isRefreshing()) {
            showLoadingDialog();
        }
        mNetworkRequestInstance.menuTreeDate(allMenuTreeDateReq)
                .subscribe(listResponseData -> {
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    if (RequestUtils.isRequestSuccess(listResponseData)) {
                        menuListAdapter.setNewData(listResponseData.getData());
                    } else {
                        HintUtil.showErrorWithToast(mContext, listResponseData.getMsg());
                    }
                });
    }

    @Override
    protected void initListener() {
        /*下拉刷新监听*/
        srlRefresh.setOnRefreshListener(this::initData);
        menuListAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (entity != null) {
                /*判断是否是电话客服*/
                switch (entity.getJumpMark()) {
                    /*客服电话*/
                    case "3":
                        /*初始化拨打客服电话弹出框*/
                        qmuiDialog = instance.setCustomizeView(
                                instance.initEtOrTvView(mActivity,
                                        menuListAdapter.getData().get(position).getLinkHref(),
                                        "呼叫",
                                        true),
                                true,
                                KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_5);
                        qmuiDialog.show();
                        break;
                    default:
                        /*跳转链接*/
                        WebViewBean webViewBean = new WebViewBean();
                        webViewBean.setWebTitle(menuListAdapter.getData().get(position).getMenuName());
                        webViewBean.setWebUrl(menuListAdapter.getData().get(position).getLinkHref());
                        jumpActivity(mContext, WebViewActivity.class, webViewBean);
                        break;
                }
            }
        });
        /*拨打电话监听*/
        instance.setOnClickListener((OnClickListener<String>)(status, useType,isPhoneNumber, text) -> {
            switch (status) {
                case CANCEL:
                    qmuiDialog.dismiss();
                    break;
                case OK:
                    if (isPhoneNumber) {
                        PhoneUtils.callPhoneDirectly(mActivity, text);
                    }
                    break;
                default:
            }
        });
    }
}
