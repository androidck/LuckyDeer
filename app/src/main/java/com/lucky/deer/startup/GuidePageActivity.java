package com.lucky.deer.startup;

import android.content.res.TypedArray;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.base.BaseFragmentPagerAdapter;
import com.lucky.deer.startup.fragment.GuidePageFragmentOne;
import com.lucky.deer.startup.fragment.GuidePageFragmentThree;
import com.lucky.deer.startup.fragment.GuidePageFragmentTwo;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import butterknife.BindView;

/**
 * 引导页
 *
 * @author wangxiangyi
 * @date 2018/09/19
 */
public class GuidePageActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    QMUIViewPager viewPager;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    ImageView[] dotViews;

    @Override
    protected int initLayout() {
        return R.layout.activity_guide_page_adapter;
    }

    @Override
    protected void initView() {
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager());
//        TypedArray imgGuidePage = getResources().obtainTypedArray(R.array.img_guide_page);
        baseFragmentPagerAdapter.addFragment(GuidePageFragmentOne.newInstance(null));
        baseFragmentPagerAdapter.addFragment(GuidePageFragmentTwo.newInstance(null));
        baseFragmentPagerAdapter.addFragment(GuidePageFragmentThree.newInstance(null));

        /*释放资源*/
//        imgGuidePage.recycle();
        viewPager.setAdapter(baseFragmentPagerAdapter);
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                for (int i = 0; i < dotViews.length; i++) {
//                    if (position == i) {
//                        dotViews[i].setSelected(true);
//                    } else {
//                        dotViews[i].setSelected(false);
//                    }
//                }
//            }
//            @Override
//            public void onPageScrollStateChanged(int i) {
//            }
//        });

    }

    /**
     * 初始化进度控件
     *
     * @param imgGuidePage 图片
     */
    private void initDots(TypedArray imgGuidePage) {
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(20, 20);
        /*设置小圆点左右之间的间隔*/
        mParams.setMargins(10, 0, 10, 0);
        dotViews = new ImageView[imgGuidePage.length()];
        for (int i = 0; i < imgGuidePage.length(); i++) {
            /*判断小圆点的数量，从0开始，0表示第一个*/
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(mParams);
            imageView.setImageResource(R.drawable.dotselector);
            if (i == 0) {
                /*默认启动时，选中第一个小圆点*/
                imageView.setSelected(true);
            } else {
                imageView.setSelected(false);
            }
            /*得到每个小圆点的引用*/
            dotViews[i] = imageView;
            /*添加到布局里面显示*/
            llLayout.addView(imageView);
        }
    }
}




