package com.lucky.deer.base;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 公共Fragment适配器
 *
 * @author wangxiangyi
 * @date 2018/5/14.
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitle = new ArrayList<>();

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * 添加fragment
     *
     * @param fragment
     */
    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
    }

    /**
     * 添加fragment
     *
     * @param fragment
     */
    public void addFragmentList(List<Fragment> fragment) {
        mFragments.addAll(fragment);
    }

    /**
     * 添加标题
     *
     * @param title 标题
     */
    public void addTitle(String title) {
        mTitle.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }


    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitle.size() > 0) {
            return mTitle.get(position);
        } else {
            return "";
        }
    }

}
