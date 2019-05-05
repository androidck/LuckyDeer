package com.lucky.deer.startup.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseFragment;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.home.MainActivity;
import com.lucky.model.util.DeviceInfoUtils;
import com.lucky.model.util.HawkUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 第三个引导页fragment
 *
 * @author wangxiangyi
 * @date 2018/09/19
 */
public class GuidePageFragmentThree extends BaseFragment {
    @BindView(R.id.iv_guide_image)
    ImageView ivGuideImage;
    @BindView(R.id.btn_enter_project)
    Button btnEnterProject;

    public static GuidePageFragmentThree newInstance(Bundle args) {
        GuidePageFragmentThree fragment = new GuidePageFragmentThree();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initlayout() {
        return R.layout.fragment_guide_page;
    }

    @Override
    protected void initView() {
        ivGuideImage.setBackgroundResource(R.mipmap.guide_3);
    }

    @OnClick(R.id.btn_enter_project)
    public void onViewClicked() {
        ActivityUtils.startActivity(mActivity, MainActivity.class);
        mActivity.finish();
        HawkUtil.getInstance().saveData(KeyConstant.VERSION_CODE, DeviceInfoUtils.get().getVersionCode());
    }


}
