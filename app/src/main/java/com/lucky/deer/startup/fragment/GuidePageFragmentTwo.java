package com.lucky.deer.startup.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lucky.deer.R;
import com.lucky.deer.base.BaseFragment;

import butterknife.BindView;

/**
 * 第二个引导页fragment
 *
 * @author wangxiangyi
 * @date 2018/09/19
 */
public class GuidePageFragmentTwo extends BaseFragment {
    @BindView(R.id.iv_guide_image)
    ImageView ivGuideImage;
    @BindView(R.id.btn_enter_project)
    Button btnEnterProject;

    public static GuidePageFragmentTwo newInstance(Bundle args) {
        GuidePageFragmentTwo fragment = new GuidePageFragmentTwo();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initlayout() {
        return R.layout.fragment_guide_page;
    }

    @Override
    protected void initView() {
        btnEnterProject.setVisibility(View.GONE);
        ivGuideImage.setBackgroundResource(R.mipmap.guide_2);
    }
}
