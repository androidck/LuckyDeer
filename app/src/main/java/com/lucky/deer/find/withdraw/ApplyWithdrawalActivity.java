package com.lucky.deer.find.withdraw;

import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 提现申请
 *
 * @author wangxiangyi
 * @date 2019/02/25
 */
public class ApplyWithdrawalActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBar topBar;

    @Override
    protected int initLayout() {
        return R.layout.activity_apply_withdrawal;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, true, R.string.title_activity_apply_withdrawal);
    }


    @OnClick(R.id.btn_carry_out)
    public void onViewClicked() {
        finish();
    }
}
