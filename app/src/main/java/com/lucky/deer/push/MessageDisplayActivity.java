package com.lucky.deer.push;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;

/**
 * 推送消息展示页
 *
 * @author wangxiangyi
 * @date 2018/11/22
 */
public class MessageDisplayActivity extends BaseActivity {


    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.tv_push_info)
    TextView tvPushInfo;

    @Override
    protected int initLayout() {
        return R.layout.activity_message_display;
    }

    @Override
    protected void initView() {
        if (getBundleData() != null) {
            Bundle bundleData = getBundleData();
            initTopBar(topBar, TextUtils.isEmpty(bundleData.get("title") + "") ? getString(R.string.title_activity_message_display) : bundleData.get("title").toString());
            tvPushInfo.setText(TextUtils.isEmpty(bundleData.get("content") + "") ? "" : bundleData.get("content").toString());
        }
    }
}
