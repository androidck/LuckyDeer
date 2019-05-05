/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.lucky.deer.recognition;

import com.lucky.deer.base.BaseActivity;

public class IDCardActivity extends BaseActivity {
    @Override
    protected int initLayout() {
        return 0;
    }

    @Override
    protected void initView() {

    }

   /* private TextView infoTextView;
    private Dialog alertDialog;

    private boolean hasGotToken=false;


    private boolean checkGalleryPermission() {
        AtomicBoolean mReturn = new AtomicBoolean(false);
        PermissionsUtils.getInstance(mContext)
                .setPermissionsRequest(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        mReturn.set(true);
                    } else {
                        PermissionsUtils.getInstance(mContext).openSettingDetail();
                    }
                });
        return mReturn.get();
    }


    @Override
    protected int initLayout() {
        return R.layout.activity_idcard;
    }

    @Override
    protected void initView() {
        alertDialog = Dialog.getInstance(mContext);
        infoTextView = (TextView) findViewById(R.id.info_text_view);
        checkGalleryPermission();
        initAccessToken();
        //  初始化本地质量控制模型,释放代码在onDestory中
        //  调用身份证扫描必须加上 intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true); 关闭自动初始化和释放本地模型
        CameraNativeHelper.init(mContext, OCR.getInstance(mContext)
                        .getLicense(),
                new CameraNativeHelper.CameraNativeInitCallback() {
                    @Override
                    public void onError(int errorCode, Throwable e) {
                        String msg;
                        switch (errorCode) {
                            case CameraView.NATIVE_SOLOAD_FAIL:
                                msg = "加载so失败，请确保apk中存在ui部分的so";
                                break;
                            case CameraView.NATIVE_AUTH_FAIL:
                                msg = "授权本地质量控制token获取失败";
                                break;
                            case CameraView.NATIVE_INIT_FAIL:
                                msg = "本地质量控制";
                                break;
                            default:
                                msg = String.valueOf(errorCode);
                        }
                        infoTextView.setText("本地质量控制初始化错误，错误原因： " + msg);
                    }
                });

        findViewById(R.id.gallery_button_front)
                .setOnClickListener(v -> {
                    if (checkGalleryPermission()) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, KeyConstant.REQUEST_CODE_PICK_IMAGE_FRONT);
                    }
                });

        findViewById(R.id.gallery_button_back)
                .setOnClickListener(v -> {
                    if (checkGalleryPermission()) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, KeyConstant.REQUEST_CODE_PICK_IMAGE_BACK);
                    }
                });

        // 身份证正面拍照
        findViewById(R.id.id_card_front_button)
                .setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_CAMERA);
                });

        // 身份证正面扫描


        // 身份证反面拍照
        findViewById(R.id.id_card_back_button)
                .setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_CAMERA);
                });

        // 身份证反面扫描
        findViewById(R.id.id_card_back_button_native)
                .setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                            true);
                    // KEY_NATIVE_MANUAL设置了之后CameraActivity中不再自动初始化和释放模型
                    // 请手动使用CameraNativeHelper初始化和释放模型
                    // 推荐这样做，可以避免一些activity切换导致的不必要的异常
                    intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
                            true);
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_CAMERA);
                });
    }

    public void idCardFont(){
        findViewById(R.id.id_card_front_button_native)
                .setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                            true);
                    // KEY_NATIVE_MANUAL设置了之后CameraActivity中不再自动初始化和释放模型
                    // 请手动使用CameraNativeHelper初始化和释放模型
                    // 推荐这样做，可以避免一些activity切换导致的不必要的异常
                    intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
                            true);
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_CAMERA);
                });
    }

    private void recIDCard(String idCardSide, String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);

        OCR.getInstance().recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    alertDialog.alertText("", result.toString());
                }
            }

            @Override
            public void onError(OCRError error) {
                alertDialog.alertText("", error.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            switch (requestCode) {
                case KeyConstant.REQUEST_CODE_PICK_IMAGE_FRONT:
                    recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, getRealPathFromURI(uri));
                    break;
                case KeyConstant.REQUEST_CODE_PICK_IMAGE_BACK:
                    recIDCard(IDCardParams.ID_CARD_SIDE_BACK, getRealPathFromURI(uri));
                    break;
                case KeyConstant.REQUEST_CODE_CAMERA:
                    if (data != null) {
                        String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                        String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
                        if (!TextUtils.isEmpty(contentType)) {
                            if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                                recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                            } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                                recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                            }
                        }
                    }
                    break;
                default:
            }
        }
    }


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        // 释放本地质量控制模型
        CameraNativeHelper.release();
        super.onDestroy();
    }

    //授权文件（安全模式）
    //此种身份验证方案使用授权文件获得AccessToken，缓存在本地。建议有安全考虑的开发者使用此种身份验证方式。
    private void initAccessToken() {
        OCR.getInstance(this).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                // 调用成功，返回AccessToken对象
                String token = accessToken.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
            }
        }, getApplicationContext());
    }*/
}
