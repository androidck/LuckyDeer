package com.lucky.deer.home.business.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseFragment;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.find.BusinessDetailsActivity;
import com.lucky.deer.home.MainActivity;
import com.lucky.deer.home.business.adapter.MyBusinessAdapter;
import com.lucky.model.response.home.cardLife.MyBusinessEntity;
import com.lucky.model.util.HintUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的商户页面
 *
 * @author wangxiangyi
 * @date 2019/03/28
 */
public class MyBusinessFragment extends BaseFragment {
    /**
     * 下拉刷新监听
     */
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    /**
     * 列表
     */
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    /**
     * 团队总人数
     */
    @BindView(R.id.tv_total_number_teams_human_head)
    TextView tvTotalNumberTeamsHumanHead;
    /**
     * VIP总人数
     */
    @BindView(R.id.tv_number_vip_team_human_head)
    TextView tvNumberVipTeamHumanHead;
    /**
     * 商户上1
     */
    @BindView(R.id.rb_business_top1)
    RadioButton rbBusinessTop1;
    /**
     * 商户下布局1
     */
    @BindView(R.id.ll_business_bottom1)
    LinearLayout llBusinessBottom1;
    /**
     * 商户下1
     */
    @BindView(R.id.rb_business_bottom1)
    RadioButton rbBusinessBottom1;
    /**
     * 商户上2
     */
    @BindView(R.id.rb_business_top2)
    RadioButton rbBusinessTop2;
    /**
     * 商户下布局2
     */
    @BindView(R.id.rb_business_bottom2)
    RadioButton rbBusinessBottom2;
    /**
     * 商户下2
     */
    @BindView(R.id.ll_business_bottom2)
    LinearLayout llBusinessBottom2;
//    /**
//     * 商户布局
//     */
//    @BindView(R.id.rg_business_layout)
//    RadioGroup rgBusinessLayout;
//    /**
//     * 商户1
//     */
//    @BindView(R.id.rb_business1)
//    RadioButton rbBusiness1;
//    /**
//     * 商户2
//     */
//    @BindView(R.id.rb_business2)
//    RadioButton rbBusiness2;

    private MyBusinessAdapter mAdapter;
    private MyBusinessEntity mData;

    public static MyBusinessFragment newInstance(Bundle args) {
        MyBusinessFragment fragment = new MyBusinessFragment();
        fragment.setArguments(args);
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
        return R.layout.fragment_my_business;
    }

    @Override
    protected void initView() {
        rvList.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new MyBusinessAdapter();
        rvList.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        /*下拉刷新*/
        srlRefresh.setOnRefreshListener(this::initData);
//        rgBusinessLayout.setOnCheckedChangeListener((group, checkedId) -> {
//            switch (checkedId) {
//                case R.id.rb_business1:
//                    /*设置商户信息*/
//                    if (mData != null && mData.getDirectPushLevelGroupList() != null && mData.getDirectPushLevelGroupList().size() > 0) {
//                        List<MyBusinessEntity> directPushLevelGroupList = mData.getDirectPushLevelGroupList();
//                        for (MyBusinessEntity myBusinessEntity : directPushLevelGroupList) {
//                            myBusinessEntity.setType("1");
//                        }
//                        mAdapter.setNewData(directPushLevelGroupList);
//                    } else {
//                        mAdapter.setNewData(new ArrayList<>());
//                    }
//                    break;
//                case R.id.rb_business2:
//                    /*设置商户信息*/
//                    if (mData != null && mData.getIndirectPushLevelGroupList() != null && mData.getIndirectPushLevelGroupList().size() > 0) {
//                        List<MyBusinessEntity> directPushLevelGroupList = mData.getIndirectPushLevelGroupList();
//                        for (MyBusinessEntity myBusinessEntity : directPushLevelGroupList) {
//                            myBusinessEntity.setType("2");
//                        }
//                        mAdapter.setNewData(directPushLevelGroupList);
//                    } else {
//                        mAdapter.setNewData(new ArrayList<>());
//                    }
//                    break;
//                default:
//            }
//        });
        /*商户点击监听*/
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(mActivity, BusinessDetailsActivity.class).putExtra(mEntity, mAdapter.getData().get(position)));
        });
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        if (!srlRefresh.isRefreshing()) {
            showLoadingDialog();
        }
        mNetworkRequestInstance.merchantInfo()
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        mData = returnData.getData();
                        /*团队总人数*/
                        tvTotalNumberTeamsHumanHead.setText(String.format(getString(R.string.item_adapter_business_level_number_people), TextUtils.isEmpty(mData.getCountByNum()) ? "0" : mData.getCountByNum()));
                        /*VIP总人数*/
                        tvNumberVipTeamHumanHead.setText(String.format(getString(R.string.item_adapter_business_level_number_people), TextUtils.isEmpty(mData.getVipByNum()) ? "0" : mData.getVipByNum()));
                        /*商户人数1*/
//                        rbBusiness1.setText(String.format(getString(R.string.item_adapter_business_name), mData.getDirectPushTitle(), TextUtils.isEmpty(mData.getDirectPushByNum()) ? "0" : mData.getDirectPushByNum()));
                        /*商户人数2*/
//                        rbBusiness2.setText(String.format(getString(R.string.item_adapter_business_name), mData.getIndirectPushTitle(), TextUtils.isEmpty(mData.getIndirectPushByNum()) ? "0" : mData.getIndirectPushByNum()));
                        /*商户人数1*/
                        rbBusinessTop1.setText(mData.getDirectPushTitle());
                        rbBusinessBottom1.setText(String.format(getString(R.string.item_adapter_business_level_number_people), TextUtils.isEmpty(mData.getDirectPushByNum()) ? "0" : mData.getDirectPushByNum()));
                        /*商户人数2*/
                        rbBusinessTop2.setText(mData.getIndirectPushTitle());
                        rbBusinessBottom2.setText(String.format(getString(R.string.item_adapter_business_level_number_people), TextUtils.isEmpty(mData.getIndirectPushByNum()) ? "0" : mData.getIndirectPushByNum()));
                        /*设置商户信息*/
                        if (mData.getDirectPushLevelGroupList() != null && mData.getDirectPushLevelGroupList().size() > 0) {
                            List<MyBusinessEntity> directPushLevelGroupList = mData.getDirectPushLevelGroupList();
                            for (MyBusinessEntity myBusinessEntity : directPushLevelGroupList) {
                                myBusinessEntity.setType("1");
                            }
                            mAdapter.setNewData(directPushLevelGroupList);
                        }
                    } else {
                        /*团队总人数*/
                        tvTotalNumberTeamsHumanHead.setText(String.format(getString(R.string.item_adapter_business_level_number_people), "0"));
                        /*VIP总人数*/
                        tvNumberVipTeamHumanHead.setText(String.format(getString(R.string.item_adapter_business_level_number_people), "0"));
                        /*商户人数1*/
//                        rbBusiness1.setText(String.format(getString(R.string.item_adapter_business_name), getString(R.string.default_my_business_direct_push_merchants), "0"));
                        /*商户人数2*/
//                        rbBusiness2.setText(String.format(getString(R.string.item_adapter_business_name), getString(R.string.default_my_business_push_merchant), "0"));
                        /*商户人数1*/
                        rbBusinessTop1.setText(R.string.default_my_business_direct_push_merchants);
                        rbBusinessBottom1.setText(String.format(getString(R.string.item_adapter_business_level_number_people), "0"));
                        /*商户人数2*/
                        rbBusinessTop2.setText(R.string.default_my_business_push_merchant);
                        rbBusinessBottom2.setText(String.format(getString(R.string.item_adapter_business_level_number_people), "0"));
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                    }
                });
    }

    @OnClick({R.id.iv_invite_friends, R.id.btn_business1, R.id.btn_business2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_invite_friends:
                startActivity(new Intent(mActivity, MainActivity.class).putExtra(mEntity, KeyConstant.MEMBER_CENTRE));
                break;
            case R.id.btn_business1:
                rbBusinessTop1.setChecked(true);
                llBusinessBottom1.setEnabled(true);
                rbBusinessBottom1.setChecked(true);

                rbBusinessTop2.setChecked(false);
                llBusinessBottom2.setEnabled(false);
                rbBusinessBottom2.setChecked(false);


                /*设置商户信息*/
                if (mData != null && mData.getDirectPushLevelGroupList() != null && mData.getDirectPushLevelGroupList().size() > 0) {
                    List<MyBusinessEntity> directPushLevelGroupList = mData.getDirectPushLevelGroupList();
                    for (MyBusinessEntity myBusinessEntity : directPushLevelGroupList) {
                        myBusinessEntity.setType("1");
                    }
                    mAdapter.setNewData(directPushLevelGroupList);
                } else {
                    mAdapter.setNewData(new ArrayList<>());
                }
                break;
            case R.id.btn_business2:
                rbBusinessTop1.setChecked(false);
                llBusinessBottom1.setEnabled(false);
                rbBusinessBottom1.setChecked(false);

                rbBusinessTop2.setChecked(true);
                llBusinessBottom2.setEnabled(true);
                rbBusinessBottom2.setChecked(true);
                /*设置商户信息*/
                if (mData != null && mData.getIndirectPushLevelGroupList() != null && mData.getIndirectPushLevelGroupList().size() > 0) {
                    List<MyBusinessEntity> directPushLevelGroupList = mData.getIndirectPushLevelGroupList();
                    for (MyBusinessEntity myBusinessEntity : directPushLevelGroupList) {
                        myBusinessEntity.setType("2");
                    }
                    mAdapter.setNewData(directPushLevelGroupList);
                } else {
                    mAdapter.setNewData(new ArrayList<>());
                }
                break;
            default:
        }
    }
}
