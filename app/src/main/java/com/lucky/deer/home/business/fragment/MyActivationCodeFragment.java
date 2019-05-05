package com.lucky.deer.home.business.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.lucky.deer.R;
import com.lucky.deer.base.BaseFragment;
import com.lucky.deer.base.BaseFragmentPagerAdapter;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的激活码页面
 *
 * @author wangxiangyi
 * @date 2019/03/28
 */
public class MyActivationCodeFragment extends BaseFragment {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    QMUIViewPager viewPager;

    public static MyActivationCodeFragment newInstance(Bundle args) {
        MyActivationCodeFragment fragment = new MyActivationCodeFragment();
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
        return R.layout.fragment_my_activation_code;
    }

    @Override
    protected void initView() {
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager());
        List<Fragment> fragmentList = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putString(mEntity, MyActivationCodeChildFragment.ALL);
        fragmentList.add(MyActivationCodeChildFragment.newInstance(bundle));
        Bundle bundle2 = new Bundle();
        bundle2.putString(mEntity, MyActivationCodeChildFragment.NOT_USE);
        fragmentList.add(MyActivationCodeChildFragment.newInstance(bundle2));
        Bundle bundle3 = new Bundle();
        bundle3.putString(mEntity, MyActivationCodeChildFragment.ALREADY_USED);
        fragmentList.add(MyActivationCodeChildFragment.newInstance(bundle3));
        baseFragmentPagerAdapter.addFragmentList(fragmentList);
        viewPager.setAdapter(baseFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(baseFragmentPagerAdapter.getCount());
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData() {
        String[] textArray = getResources().getStringArray(R.array.tab_my_activation_code);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setText(textArray[i]);
        }
    }
}
