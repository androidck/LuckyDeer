package com.lucky.deer.mine.video;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.model.request.find.PostVideoReq;
import com.lucky.model.util.GlideUtils;
import com.lucky.model.util.HintUtil;
import com.lucky.model.util.RxBus;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 视频发布
 *
 * @author wangxiangyi
 * @date 2018/11/20
 */
public class PostVideoActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.et_video_title)
    EditText etVideoTitle;
    @BindView(R.id.iv_video_img)
    QMUIRadiusImageView ivVideoImg;
    @BindView(R.id.et_video_content)
    EditText etVideoContent;

    @Override
    protected int initLayout() {
        return R.layout.activity_post_video;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_post_video);
    }

    @Override
    protected void initData() {
        if (getStringData() != null) {
            GlideUtils.loadImage(mContext, ivVideoImg, getStringData() + "?vframe/jpg/offset/0");
        }
    }

    @OnClick({R.id.iv_video_img, R.id.tv_post_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*视频按钮*/
            case R.id.iv_video_img:
                break;
            /*发布按钮*/
            case R.id.tv_post_video:
                examineRequiredVerification();
                break;
            default:
        }
    }

    @Override
    protected boolean examineRequiredVerification() {
        if (TextUtils.isEmpty(etVideoTitle.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_please_enter_title);
            return false;
        }
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        PostVideoReq postVideoReq = new PostVideoReq();
        /*标题*/
        postVideoReq.setTitle(etVideoTitle.getText().toString().trim());
        /*视频描述*/
        postVideoReq.setDesc(etVideoContent.getText().toString().trim());
        if (getStringData() != null) {
            /*视频封面*/
            postVideoReq.setCover(getStringData() + "?vframe/jpg/offset/0");
            /*视频地址*/
            postVideoReq.setVideoUrl(getStringData());
        }
        showLoadingDialog();
        mNetworkRequestInstance.insertVideo(postVideoReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        showSuccessDialog(R.string.dialog_successful_video_release);
                        RxBus.getInstance().post(KeyConstant.POST_VIDEO);
                    } else {
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }

    @Override
    public void carriedOutMethod() {
        jumpActivity(mContext, MyVideoActivity.class, true);
    }
}
