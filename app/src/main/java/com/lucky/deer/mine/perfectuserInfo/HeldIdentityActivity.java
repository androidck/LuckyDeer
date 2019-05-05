package com.lucky.deer.mine.perfectuserInfo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.cloudstorage.executor.StartUpload;
import com.dykj.requestcore.util.PermissionsUtils;
import com.dykj.requestcore.util.RequestUtils;
import com.dykj.requestcore.util.SystemUtil;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.home.MainActivity;
import com.lucky.deer.util.StringUtil;
import com.lucky.model.request.userinfo.PerfectUserInfoReq;
import com.lucky.model.response.userinfo.UserInfo;
import com.lucky.model.util.HawkUtil;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;

/**
 * 完成手持身份证
 */
public class HeldIdentityActivity extends BaseActivity {

    @BindView(R.id.iv_reverse_photo)
    ImageView ivReversePhoto;
    @BindView(R.id.tv_held_identity_card_next)
    TextView tvHeldIdentityCardNext;
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    private String absolutePath;
    private String imagePathUrl;

    @Override
    protected int initLayout() {
        return R.layout.activity_held_identity;
    }

    @Override
    protected void initView() {
        initTopBar();
    }


    private void initTopBar() {
        topBar.setTitle("完善信息");
        topBar.addRightTextButton("稍后完善", R.id.btn_right)
                .setOnClickListener(v -> {
                    Intent intent = new Intent(HeldIdentityActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });
        topBar.addLeftImageButton(R.mipmap.back, R.id.btn_left)
                .setOnClickListener(v -> {
//                    Intent intent = new Intent(HeldIdentityActivity.this, MainActivity.class);
//                    startActivity(intent);
                    finish();
                });
    }

    @OnClick({R.id.tv_authentication, R.id.iv_reverse_photo, R.id.tv_held_identity_card_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*身份证认证*/
            case R.id.tv_authentication:
                jumpActivity(mContext, AuthenticationActivity.class);
                break;
            case R.id.iv_reverse_photo:
                PermissionsUtils.getInstance(mContext)
                        .setPermissionsRequest(
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(aBoolean -> {
                            if (aBoolean) {
                                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), KeyConstant.HAND_HELD_IDENTITY_CARD);
                            } else {
                                PermissionsUtils.getInstance(mContext).openSettingDetail();
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
                        UserInfo userInfo = HawkUtil.getInstance().getSaveData(HawkUtil.USER_INFO);
                        if (userInfo != null) {
                            userInfo.setRegisterState(3);
                            HawkUtil.getInstance().saveData(HawkUtil.USER_INFO, userInfo);
                            jumpActivity(mContext, BankCardActivity.class, true);
//                            Intent intent = new Intent(HeldIdentityActivity.this, BankCardActivity.class);
//                            startActivity(intent);
//                            finish();
                        }
                    } else {
                        HintUtil.showErrorWithToast(mActivity, stringBeanResponseData.getMsg());
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == KeyConstant.HAND_HELD_IDENTITY_CARD) {
//            uploadImage(absolutePath);
            saveCameraImage(data);
        }
    }

    /**
     * 保存相机的图片
     **/
    private void saveCameraImage(Intent data) {
        // 检查sd card是否存在
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.i(TAG, "sd card is not avaiable/writeable right now.");
            return;
        }
        String name = "img_" + SystemUtil.getInstance().getCurrentTimeMillis() + ".jpg";
        /*解析返回的图片成bitmap*/
        Bitmap bmp = (Bitmap) data.getExtras().get("data");
        saveBitmap(bmp, name);
    }

    /*
     * 保存文件，文件名为当前日期
     */
    public void saveBitmap(Bitmap bitmap, String bitName) {
        String fileName;
        File file;
        if (Build.BRAND.equals("Xiaomi")) { // 小米手机
            fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + bitName;
        } else {  // Meizu 、Oppo
            fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/" + bitName;
        }
        file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            // 格式为 JPEG，照相机拍出的图片为JPEG格式的，PNG格式的不能显示在相册中
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
                out.flush();
                out.close();
                // 插入图库
                MediaStore.Images.Media.insertImage(this.getContentResolver(), file.getAbsolutePath(), bitName, null);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 发送广播，通知刷新图库的显示
        this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileName)));
        uploadImage(fileName);
    }

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
