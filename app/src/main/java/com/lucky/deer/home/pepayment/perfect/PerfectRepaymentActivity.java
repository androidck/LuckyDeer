package com.lucky.deer.home.pepayment.perfect;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.base.BaseFragmentPagerAdapter;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.home.cardspending.SelectBankCardActivity;
import com.lucky.deer.home.pepayment.perfect.fragment.PerfectRepaymentFagment;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.model.response.selectbankcard.SelectBankCardList;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import butterknife.BindView;

/**
 * 完美养卡列表
 *
 * @author wangxiangyi
 * @date 2018/12/11
 */
@SuppressLint("Registered")
public class PerfectRepaymentActivity extends BaseActivity {
    /**
     * 获取当前(日期养卡)Activity
     */
    @SuppressLint("StaticFieldLeak")
    public static PerfectRepaymentActivity mPerfectRepaymentActivity;

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    QMUIViewPager viewPager;

    @Override
    protected int initLayout() {
        return R.layout.activity_perfect_repayment;
    }

    @Override
    protected void initView() {
        mPerfectRepaymentActivity = this;
        initTopBar(topBar,
                R.string.title_activity_perfect_repayment,
                R.mipmap.add_video,
                v -> {
                    /*判断是否开启制定计划*/
                    if ("1".equals(getUserInfo().getIsOpenDateRepayment())) {
                        /*开启*/
                        SelectBankCardList selectBankCardList = new SelectBankCardList();
                        selectBankCardList.setFlag(KeyConstant.SELECT_CREDIT_CARD);
                        selectBankCardList.setEnterAisleFlag(KeyConstant.PERFECT_REPAYMENT);
                        jumpActivity(mActivity, SelectBankCardActivity.class, selectBankCardList);
                    } else {
                        PublicDialog.getInstance().setMessageDialog(mActivity, "提示", "功能修改中，已制订计划不会定制正常执行", getString(R.string.cancel));
                    }
                });
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager());
        /*执行中*/
        Bundle executePlanBundle = new Bundle();
        executePlanBundle.putString(mEntity, PerfectRepaymentFagment.executePlan);
        baseFragmentPagerAdapter.addFragment(PerfectRepaymentFagment.newInstance(executePlanBundle));
        /*异常*/
        Bundle abnormalPlanBundle = new Bundle();
        abnormalPlanBundle.putString(mEntity, PerfectRepaymentFagment.abnormalPlan);
        baseFragmentPagerAdapter.addFragment(PerfectRepaymentFagment.newInstance(abnormalPlanBundle));
        /*历史*/
        Bundle repaymentHistoryBundle = new Bundle();
        repaymentHistoryBundle.putString(mEntity, PerfectRepaymentFagment.repaymentHistory);
        baseFragmentPagerAdapter.addFragment(PerfectRepaymentFagment.newInstance(repaymentHistoryBundle));
        /*全部计划*/
        Bundle allPlanBundle = new Bundle();
        allPlanBundle.putString(mEntity, PerfectRepaymentFagment.AllPlan);
        baseFragmentPagerAdapter.addFragment(PerfectRepaymentFagment.newInstance(allPlanBundle));
        String[] textArray = getdata(R.array.list_date_repayment);
        for (int i = 0; i < baseFragmentPagerAdapter.getCount(); i++) {
            baseFragmentPagerAdapter.addTitle(textArray[i]);
        }
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(baseFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        /*预加载页面*/
        viewPager.setOffscreenPageLimit(baseFragmentPagerAdapter.getCount());
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        /*获取注册进件状态*/
        getRegistrationStatus();
    }
}
