package com.lucky.deer.mine.supplement.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.cloudstorage.executor.StartUpload;
import com.dykj.requestcore.util.PermissionsUtils;
import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseFragment;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.util.ImageUtils;
import com.lucky.deer.util.StringUtil;
import com.lucky.model.request.userinfo.PerfectUserInfoReq;
import com.lucky.model.util.HintUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 完善信息：手持身份证
 *
 * @author wangxiangyi
 * @date 2018/10/12
 */
public class HeldIdentityCardFragment extends BaseFragment {

    @BindView(R.id.iv_reverse_photo)
    ImageView ivReversePhoto;
    @BindView(R.id.tv_held_identity_card_next)
    TextView tvHeldIdentityCardNext;
    private String imagePathUrl;

    public static HeldIdentityCardFragment newInstance(Bundle args) {
        HeldIdentityCardFragment fragment = new HeldIdentityCardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    protected int initlayout() {
        return R.layout.fragment_held_identity_card;
    }

    @Override
    protected void initView() {
        tvHeldIdentityCardNext.setText(R.string.ok);
    }


    @OnClick({R.id.iv_reverse_photo, R.id.tv_held_identity_card_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_reverse_photo:
                PermissionsUtils.getInstance(mActivity)
                        .setPermissionsRequest(
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(aBoolean -> {
                            if (aBoolean) {
                                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), KeyConstant.HAND_HELD_IDENTITY_CARD);
                            } else {
                                PermissionsUtils.getInstance(mActivity).openSettingDetail();
                            }
                        });
                break;
            case R.id.tv_held_identity_card_next:
                examineRequiredVerification();
                break;
            default:
        }
    }

    @Override
    protected boolean examineRequiredVerification() {
        if (TextUtils.isEmpty(imagePathUrl)) {
            HintUtil.showErrorWithToast(mActivity, R.string.toast_qheld_identity_card);
            return false;
        }
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        PerfectUserInfoReq perfectUserInfoReq = new PerfectUserInfoReq();
        perfectUserInfoReq.setHandIdCard(HttpConstant.QINIU_URL + imagePathUrl);
        showLoadingDialog();
        mNetworkRequestInstance.userRealNameAuthenticationTwo(perfectUserInfoReq)
                .subscribe(stringBeanResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(stringBeanResponseData)) {
                        showSuccessDialog("信息补录成功");
                    } else {
                        HintUtil.showErrorWithToast(mActivity, stringBeanResponseData.getMsg());
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == KeyConstant.HAND_HELD_IDENTITY_CARD) {
            uploadImage(ImageUtils.saveCameraImage(mActivity, data));
        }
    }

    /**
     * 上传图片
     *
     * @param imagePath 图片绝对路径
     */
    @SuppressLint("CheckResult")
    private void uploadImage(String imagePath) {
        showLoadingDialog();
        StartUpload
                .getInstance()
                .upload(null, imagePath)
                .subscribe(baseReq -> {
                    dismissLoadingDialog();
                    ivReversePhoto.setImageBitmap(StringUtil.decodeStream(imagePath));
                    imagePathUrl = baseReq.getKey();
                });
    }
}
