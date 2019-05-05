package com.lucky.deer.find.business.redEnvelope;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.base.BaseFragmentPagerAdapter;
import com.lucky.deer.find.business.fragment.RedEnvelopesDetailFragment;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import butterknife.BindView;

/**
 * 红包明细
 *
 * @author wangxiangyi
 * @date 2019/03/26
 */
public class RedEnvelopesDetailActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    QMUIViewPager viewPager;

    @Override
    protected int initLayout() {
        return R.layout.activity_red_envelopes_detail;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_red_envelopes_detail);
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putString(mEntity, RedEnvelopesDetailFragment.RECEIVED_RED_ENVELOPE);
        baseFragmentPagerAdapter.addFragment(RedEnvelopesDetailFragment.newInstance(bundle));
        Bundle bundle2 = new Bundle();
        bundle2.putString(mEntity, RedEnvelopesDetailFragment.SENT_RED_ENVELOPE);
        baseFragmentPagerAdapter.addFragment(RedEnvelopesDetailFragment.newInstance(bundle2));
        viewPager.setAdapter(baseFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(baseFragmentPagerAdapter.getCount());
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData() {
        String[] textArray = getdata(R.array.red_envelopes_detail);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setText(textArray[i]);
        }
    }
}
