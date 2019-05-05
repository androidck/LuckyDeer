package com.lucky.deer.mine.video;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ErrorListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.demo.cloudstorage.executor.StartUpload;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.HttpConstant;

import java.io.File;

import butterknife.BindView;

/**
 * 视频录制
 *
 * @author wangxiangyi
 * @date 2018/11/20
 */
public class VideoActivity extends BaseActivity {


    @BindView(R.id.jv_video)
    JCameraView jvVideo;

    @Override
    protected int initLayout() {
        return R.layout.activity_video;
    }

    @Override
    protected void initView() {
        /*设置视频保存路径*/
        jvVideo.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "JCamera");
        /*设置只能录像或只能拍照或两种都可以（默认两种都可以）*/
        jvVideo.setFeatures(JCameraView.BUTTON_STATE_ONLY_RECORDER);
        /*设置视频质量*/
        jvVideo.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);
    }

    @Override
    protected void initListener() {
        /*JCameraView监听*/
        jvVideo.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                /*打开Camera失败回调*/
                Log.i("CJT", "open camera error");
            }

            @Override
            public void AudioPermissionError() {
            }
        });
        /*获取封面监听*/
        jvVideo.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                /*获取图片bitmap*/
                Log.i("jvVideo", "bitmap = " + bitmap.getWidth());
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                /*获取视频路径*/
                uploadImage(url);
            }
        });
        /*左边按钮点击事件*/
        jvVideo.setLeftClickListener(() -> finish());
        /*右边按钮点击事件*/
        jvVideo.setRightClickListener(() -> Toast.makeText(mContext, "Right", Toast.LENGTH_SHORT).show());
    }


    /**
     * 上传图片
     *
     * @param imagePath
     */
    @SuppressLint("CheckResult")
    private void uploadImage(String imagePath) {
        showLoadingDialog();
        StartUpload
                .getInstance()
                .upload(null, imagePath)
                .subscribe(baseReq -> {
                    dismissLoadingDialog();
                    if (TextUtils.isEmpty(baseReq.getKey())) {
                        finish();
                    } else {
                        jumpActivity(mContext, PostVideoActivity.class, HttpConstant.QINIU_URL + baseReq.getKey(), true);
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        jvVideo.onPause();
    }
}
