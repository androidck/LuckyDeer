package com.lucky.deer.home.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;


import com.lucky.deer.R;
import com.lucky.deer.base.BaseFragment;
import com.lucky.deer.base.BaseFragmentPagerAdapter;
import com.lucky.deer.find.business.BusinessFragment;
import com.lucky.deer.home.cardLife.CardLifeActivity;
import com.lucky.model.response.EventMsg;
import com.lucky.model.util.RxBus;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 发现模块
 *
 * @author wangxiangyi
 * @date 2018/09/20
 */
public class FindFragment extends BaseFragment {
    /**
     * 标题
     */
    @BindView(R.id.top_bar1)
    QMUITopBar topBar1;
    /**
     * 标题
     */
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    /**
     * 导航栏标题
     */
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    /**
     * 内容布局
     */
    @BindView(R.id.view_pager)
    QMUIViewPager viewPager;
    private List<Fragment> fragmentList;

    public static FindFragment newInstance(Bundle args) {
        FindFragment fragment = new FindFragment();
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
        return R.layout.fragment_find;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        topBar1.setMinimumHeight(getStatusBar());
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager());
        fragmentList = new ArrayList<>();
        fragmentList.add(BusinessFragment.newInstance(null));
        fragmentList.add(PromotionFragment.newInstance(null));
        baseFragmentPagerAdapter.addFragmentList(fragmentList);
        viewPager.setAdapter(baseFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(baseFragmentPagerAdapter.getCount());
        tabLayout.setupWithViewPager(viewPager);
        //接受通知
        //默认选中第二个
        SharedPreferences sPreferences=getContext().getSharedPreferences("SELECT_INFO",  Context.MODE_PRIVATE);
        String username=sPreferences.getString("model", "");
        if (username!=null&&username.equals("1")){
            tabLayout.getTabAt(Integer.parseInt(username)).select();
            sPreferences.edit().clear().commit();
        }

    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        String[] textArray = getResources().getStringArray(R.array.find_navigation_bar_info);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setText(textArray[i]);
        }
    }

    @Override
    protected void initListener() {
        if (fragmentList != null && fragmentList.size() > 0) {
            for (Fragment fragment : fragmentList) {
                if (fragment instanceof PromotionFragment) {
                    ((PromotionFragment) fragment).setOnIsShowShareListener(isShow -> {
                        if (isShow) {
                            topBar.addRightImageButton(R.mipmap.share_btn, 0)
                                    .setOnClickListener(v -> {
                                        if (fragmentList != null && fragmentList.size() > 0) {
                                            for (Fragment item : fragmentList) {
                                                if (item instanceof PromotionFragment) {
                                                    ((PromotionFragment) item).getPictureProcessing();
                                                }
                                            }
                                        }
                                    });
                        } else {
                            topBar.removeAllRightViews();
                        }
                    });
                }
            }
        }
    }

}
