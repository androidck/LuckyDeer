package com.lucky.deer.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * ViewPage 适配器
 * Created by Administrator on 2015/6/24.
 */
public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> list;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == ((Fragment) obj).getView();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = ((Fragment) object);
    }
}
