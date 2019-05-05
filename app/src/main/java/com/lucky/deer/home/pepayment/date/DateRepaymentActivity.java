package com.lucky.deer.home.pepayment.date;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.widget.TextView;

import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.base.BaseFragmentPagerAdapter;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.home.cardspending.SelectBankCardActivity;
import com.lucky.deer.home.pepayment.date.fragment.PublicPlanFragment;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.model.response.selectbankcard.SelectBankCardList;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import butterknife.BindView;

/**
 * 日期还款
 *
 * @author wangxiangyi
 * @date 2018/12/11
 */
public class DateRepaymentActivity extends BaseActivity {
    /**
     * 获取当前(日期还款)Activity
     */
    @SuppressLint("StaticFieldLeak")
    public static DateRepaymentActivity mDateRepaymentActivity;

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    /**
     * 还款介绍
     */
    @BindView(R.id.tv_introduction)
    TextView tvIntroduction;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    QMUIViewPager viewPager;

    @Override
    protected int initLayout() {
        return R.layout.activity_date_repayment;
    }

    @Override
    protected void initView() {
        mDateRepaymentActivity = this;
        initTopBar(topBar,
                R.string.title_activity_date_repayment,
                R.mipmap.add_video,
                v -> {
                    /*判断是否开启制定计划*/
                    if ("1".equals(getUserInfo().getIsOpenDateRepayment())) {
                        /*开启*/
                        SelectBankCardList selectBankCardList = new SelectBankCardList();
                        selectBankCardList.setFlag(KeyConstant.SELECT_CREDIT_CARD);
                        selectBankCardList.setEnterAisleFlag(KeyConstant.DATE_REPAYMENT);
                        jumpActivity(mActivity, SelectBankCardActivity.class, selectBankCardList);
                    } else {
                        PublicDialog.getInstance().setMessageDialog(mActivity, "提示", "功能修改中，已制订计划不会定制正常执行", getString(R.string.cancel));
                    }
                });
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager());
//        baseFragmentPagerAdapter.addFragment(AllPlanFragment.newInstance(null));
//        baseFragmentPagerAdapter.addFragment(ExecutePlanFragment.newInstance(null));
//        baseFragmentPagerAdapter.addFragment(HistoryPlanFragment.newInstance(null));

        Bundle executePlanBundle = new Bundle();
        executePlanBundle.putString(mEntity, PublicPlanFragment.executePlan);
        baseFragmentPagerAdapter.addFragment(PublicPlanFragment.newInstance(executePlanBundle));
        Bundle abnormalPlanBundle = new Bundle();
        abnormalPlanBundle.putString(mEntity, PublicPlanFragment.abnormalPlan);
        baseFragmentPagerAdapter.addFragment(PublicPlanFragment.newInstance(abnormalPlanBundle));
        Bundle repaymentHistoryBundle = new Bundle();
        repaymentHistoryBundle.putString(mEntity, PublicPlanFragment.repaymentHistory);
        baseFragmentPagerAdapter.addFragment(PublicPlanFragment.newInstance(repaymentHistoryBundle));
        /*全部计划*/
        Bundle allPlanBundle = new Bundle();
        allPlanBundle.putString(mEntity, PublicPlanFragment.AllPlan);
        baseFragmentPagerAdapter.addFragment(PublicPlanFragment.newInstance(allPlanBundle));
        String[] textArray = getdata(R.array.list_date_repayment);
        for (int i = 0; i < baseFragmentPagerAdapter.getCount(); i++) {
            baseFragmentPagerAdapter.addTitle(textArray[i]);
        }
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(baseFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        /*预加载页面*/
        viewPager.setOffscreenPageLimit(baseFragmentPagerAdapter.getCount());
        tvIntroduction.setText(
                StringUtil.setTextIndentation(
                        "请正确输入开始日期、截止日期与养卡金额，系统会每天以消三还一的方式进行，每次交易相隔1小时以上，交易均有推送消息通知，或关注公众号查看还款消息！"
                ));
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        /*获取注册进件状态*/
        getRegistrationStatus();
    }
}
