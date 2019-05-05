package com.lucky.deer.mine.salesman;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.util.StringUtil;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我是业务员：公司简介,
 *
 * @author wangxiangyi
 * @date 2018/12/18
 */
public class CompanySynopsisActivity extends BaseActivity {
    /**
     * 标志
     */
    public static final String TYPE = "type";
    /**
     * 内容
     */
    public static final String CONTENT = "content";

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.et_company_synopsis)
    EditText etCompanySynopsis;
    private int mHintID;

    @Override
    protected int initLayout() {
        return R.layout.activity_company_synopsis;
    }

    @Override
    protected void initView() {
        if (getBundleData() != null) {
            int mTitleID = R.string.title_activity_company_synopsis;
            switch (getBundleData().getInt(TYPE)) {
                /*公司名称*/
                case KeyConstant.COMPANY_NAME:
                    mTitleID = R.string.title_activity_company_name;
                    mHintID = R.string.toast_company_name_not_empty;
                    break;
                /*公司介绍*/
                case KeyConstant.COMPANY_PROFILE:
                    mTitleID = R.string.title_activity_company_synopsis;
                    mHintID = R.string.toast_company_synopsis_not_empty;
                    break;
                /*业务介绍*/
                case KeyConstant.BUSINESS_PROFILE:
                    mTitleID = R.string.title_activity_business_introduction;
                    mHintID = R.string.toast_business_introduction_not_empty;
                    break;
                /*招聘要求*/
                case KeyConstant.RECRUITMENT_REQUIREMENTS:
                    mTitleID = R.string.title_activity_recruitment_requirements;
                    mHintID = R.string.toast_recruitment_requirements_not_empty;
                    break;
                default:
            }
            initTopBar(topBar, mTitleID,
                    R.string.save,
                    R.color.color_hint,
                    v -> {
                        examineRequiredVerification();
                    });
            etCompanySynopsis.setText(TextUtils.isEmpty(getBundleData().getString(CONTENT)) ? "" : getBundleData().getString(CONTENT));
        }
        /*设置软键盘弹出*/
        HintUtil.showSoftInputFromInputMethod(mActivity, etCompanySynopsis);
    }

    @OnClick(R.id.iv_remove)
    public void onViewClicked() {
        StringUtil.clearData(etCompanySynopsis);
    }

    @Override
    protected boolean examineRequiredVerification() {
        if (TextUtils.isEmpty(etCompanySynopsis.getText().toString().trim()) && mHintID > 0) {
            HintUtil.showErrorWithToast(mContext, mHintID);
            return false;
        }
        return super.examineRequiredVerification();
    }

    @Override
    protected void startRequestInterface() {
        Intent intent = new Intent();
        intent.putExtra(mEntity, etCompanySynopsis.getText().toString().trim());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
