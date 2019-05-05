package com.lucky.deer.home.business;

import android.support.design.widget.TabLayout;

import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.base.BaseFragmentPagerAdapter;
import com.lucky.deer.home.business.fragment.MyActivationCodeFragment;
import com.lucky.deer.home.business.fragment.MyBusinessFragment;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import butterknife.BindView;

/**
 * 我的商户
 *
 * @author wangxiangyi
 * @date 2019/03/19
 */
public class MyBusinessActivity extends BaseActivity {


    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    QMUIViewPager viewPager;


    @Override
    protected int initLayout() {
        return R.layout.activity_my_business;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, "");
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager());
        baseFragmentPagerAdapter.addFragment(MyBusinessFragment.newInstance(null));
     //   baseFragmentPagerAdapter.addFragment(MyActivationCodeFragment.newInstance(null));
        viewPager.setAdapter(baseFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(baseFragmentPagerAdapter.getCount());
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData() {
        String[] textArray = getdata(R.array.tab_my_business_or_my_activation_code);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setText(textArray[i]);
        }
    }
}
