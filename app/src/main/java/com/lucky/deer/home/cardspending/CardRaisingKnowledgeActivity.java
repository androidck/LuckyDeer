package com.lucky.deer.home.cardspending;

import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;

/**
 * 养卡小知识
 *
 * @author wangxiangyi
 * @date 2019/01/17
 */
public class CardRaisingKnowledgeActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBar topBar;

    @Override
    protected int initLayout() {
        return R.layout.activity_card_raising_knowledge;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_card_raising_knowledge);
    }
}
