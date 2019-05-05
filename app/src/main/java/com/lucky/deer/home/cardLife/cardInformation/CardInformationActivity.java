package com.lucky.deer.home.cardLife.cardInformation;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.base.BaseFragmentPagerAdapter;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.home.cardLife.cardInformation.fragment.BankCardFragment;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import butterknife.BindView;

/**
 * 卡信息
 *
 * @author wangxiangyi
 * @date 2019/03/20
 */
public class CardInformationActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    QMUIViewPager viewPager;

    @Override
    protected int initLayout() {
        return R.layout.activity_card_information;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, "");
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putInt(mEntity, KeyConstant.SELECT_CREDIT_CARD );
        baseFragmentPagerAdapter.addFragment(BankCardFragment.newInstance(bundle));
        Bundle bundle2 = new Bundle();
        bundle2.putInt(mEntity, KeyConstant.SELECT_DEBIT_CARD);
        baseFragmentPagerAdapter.addFragment(BankCardFragment.newInstance(bundle2));
        viewPager.setAdapter(baseFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(baseFragmentPagerAdapter.getCount());
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData() {
        String[] textArray = getdata(R.array.list_bank_card);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setText(textArray[i]);
        }
    }
}
