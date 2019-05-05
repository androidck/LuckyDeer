package com.lucky.deer.mine.aboutUs;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.EditText;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.model.request.userinfo.MineInfoReq;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 意见反馈
 *
 * @author wangxiangyi
 * @date 2018/11/1
 */
public class FeedbackOpinionActivity extends BaseActivity {


    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.et_feedback_content)
    EditText etFeedbackContent;
    @BindView(R.id.et_phone_or_qq_number)
    EditText etPhoneOrQqNumber;

    @Override
    protected int initLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_feedback);
    }


    @OnClick(R.id.tv_submit)
    public void onViewClicked() {
        examineRequiredVerification();
    }

    @Override
    protected boolean examineRequiredVerification() {
        if (TextUtils.isEmpty(etFeedbackContent.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, getString(R.string.prompt_intput_feedback_opinion));
            return false;
        } else if (TextUtils.isEmpty(etPhoneOrQqNumber.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, getString(R.string.prompt_intput_phone_or_qq_number));
            return false;
        }
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        MineInfoReq mineInfoReq = new MineInfoReq();
        mineInfoReq.setFeedbackContent(etFeedbackContent.getText().toString().trim());
        showLoadingDialog();
        mNetworkRequestInstance.userFeedback(mineInfoReq)
                .subscribe(stringResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        showSuccessDialog(R.string.dialog_feedback_success);
                    } else {
                        HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                    }
                });
    }

    @Override
    public void carriedOutMethod() {
        overridePendingTransition(false, true);
    }
}
