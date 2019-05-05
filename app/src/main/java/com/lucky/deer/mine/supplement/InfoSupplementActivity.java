package com.lucky.deer.mine.supplement;

import android.support.design.widget.TabLayout;

import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.base.BaseFragmentPagerAdapter;
import com.lucky.deer.mine.supplement.fragment.AuthenticationFragment;
import com.lucky.deer.mine.supplement.fragment.HeldIdentityCardFragment;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import butterknife.BindView;

/**
 * 补录信息
 *
 * @author wangxiangyi
 * @date 2018/11/05
 */
public class InfoSupplementActivity extends BaseActivity {


    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    QMUIViewPager viewPager;

    @Override
    protected int initLayout() {
        return R.layout.activity_info_supplement;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_info_supplement);
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager());
        baseFragmentPagerAdapter.addFragment(AuthenticationFragment.newInstance(null));
        baseFragmentPagerAdapter.addFragment(HeldIdentityCardFragment.newInstance(null));
        viewPager.setAdapter(baseFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData() {
        String[] textArray = getdata(R.array.list_info_supplement);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setText(textArray[i]);
        }
    }
}
