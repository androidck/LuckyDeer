package com.lucky.deer.home.cardspending;

import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;

/**
 * 选择通道
 *
 * @author wangxingyi
 * @date 2018/10/11
 */
public class SelsetAisleActivity extends BaseActivity {


    @BindView(R.id.top_bar)
    QMUITopBar topBar;

    @Override
    protected int initLayout() {
        return R.layout.activity_selset_aisle;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_selset_aisle);
    }

}
