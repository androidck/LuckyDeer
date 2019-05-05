package com.lucky.deer.recognition;

import android.annotation.SuppressLint;
import android.content.Intent;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.recognition.weight.Dialog;
import com.lucky.model.util.HintUtil;

/**
 * 文字识别全部功能
 *
 * @author wangxinagyi
 * @date 2018/10/16
 *//*
@SuppressLint("Registered")
public class TextRecognitionActivity extends BaseActivity {

    private Dialog alertDialog;

    @Override
    protected int initLayout() {
        return R.layout.activity_text_recognition;
    }

    @Override
    protected void initView() {
        alertDialog = Dialog.getInstance(mContext);

        // 通用文字识别
        findViewById(R.id.general_basic_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_GENERAL_BASIC);
                });

        // 通用文字识别(高精度版)
        findViewById(R.id.accurate_basic_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_ACCURATE_BASIC);
                });

        // 通用文字识别（含位置信息版）
        findViewById(R.id.general_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_GENERAL);
                });

        // 通用文字识别（含位置信息高精度版）
        findViewById(R.id.accurate_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_ACCURATE);
                });

        // 通用文字识别（含生僻字版）
        findViewById(R.id.general_enhance_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_GENERAL_ENHANCED);
                });

        // 网络图片识别
        findViewById(R.id.general_webimage_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_GENERAL_WEBIMAGE);
                });

        // 身份证识别
        findViewById(R.id.idcard_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, IDCardActivity.class);
                    startActivity(intent);
                });

        // 银行卡识别
        findViewById(R.id.bankcard_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_BANK_CARD);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_BANKCARD);
                });

        // 行驶证识别
        findViewById(R.id.vehicle_license_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_VEHICLE_LICENSE);
                });

        // 驾驶证识别
        findViewById(R.id.driving_license_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_DRIVING_LICENSE);
                });

        // 车牌识别
        findViewById(R.id.license_plate_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_LICENSE_PLATE);
                });

        // 营业执照识别
        findViewById(R.id.business_license_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_BUSINESS_LICENSE);
                });

        // 通用票据识别
        findViewById(R.id.receipt_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_RECEIPT);
                });

        // 护照识别
        findViewById(R.id.passport_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_PASSPORT);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_PASSPORT);
                });

        // 二维码识别
        findViewById(R.id.qrcode_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_QRCODE);
                });

        // 数字识别
        findViewById(R.id.numbers_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_NUMBERS);
                });

        // 名片识别
        findViewById(R.id.business_card_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_BUSINESSCARD);
                });

        // 增值税发票识别
        findViewById(R.id.vat_invoice_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_VATINVOICE);
                });

        // 彩票识别
        findViewById(R.id.lottery_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_LOTTERY);
                });

        // 手写识别
        findViewById(R.id.handwritting_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_HANDWRITING);
                });

        // 自定义模板
        findViewById(R.id.custom_button)
                .setOnClickListener(v -> {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, KeyConstant.REQUEST_CODE_CUSTOM);
                });


        // 请选择您的初始化方式
        // initAccessToken();
        initAccessTokenWithAkSk();
    }


    private boolean checkTokenStatus() {
        if (!KeyConstant.isGotToken) {
            HintUtil.showErrorWithToast(mContext, "token还未成功获取");
        }
        return KeyConstant.isGotToken;
    }

    *//**
     * 以license文件方式初始化
     *//*
    private void initAccessToken() {
        OCR.getInstance()
                .initAccessToken(new OnResultListener<AccessToken>() {
                    @Override
                    public void onResult(AccessToken accessToken) {
                        String token = accessToken.getAccessToken();
                        KeyConstant.isGotToken = true;
                    }

                    @Override
                    public void onError(OCRError error) {
                        error.printStackTrace();
                        alertDialog.alertText("licence方式获取token失败", error.getMessage());
                    }
                }, getApplicationContext());
    }

    *//**
     * 用明文ak，sk初始化
     *//*
    private void initAccessTokenWithAkSk() {
        OCR.getInstance()
                .initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
                    @Override
                    public void onResult(AccessToken result) {
                        String token = result.getAccessToken();
                        KeyConstant.isGotToken = true;
                    }

                    @Override
                    public void onError(OCRError error) {
                        error.printStackTrace();
                        alertDialog.alertText("AK，SK方式获取token失败", error.getMessage());
                    }
                }, getApplicationContext(), KeyConstant.API_KEY, KeyConstant.SECRET_KEY);
    }

    *//**
     * 自定义license的文件路径和文件名称，以license文件方式初始化
     *//*
    private void initAccessTokenLicenseFile() {
      *//*  OCR.getInstance()
                .initAccessToken(new OnResultListener<AccessToken>() {
                    @Override
                    public void onResult(AccessToken accessToken) {
                        String token = accessToken.getAccessToken();
                        KeyConstant.isGotToken = true;
                    }

                    @Override
                    public void onError(OCRError error) {
                        error.printStackTrace();
                        alertDialog.alertText("自定义文件路径licence方式获取token失败", error.getMessage());
                    }
                }, "aip.license", mContext);*//*
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     *//*   if (resultCode == Activity.RESULT_OK) {
            String absolutePath = FileUtil.getSaveFile(mContext).getAbsolutePath();
            switch (requestCode) {
                *//**//*识别成功回调，通用文字识别（含位置信息）*//**//*
                case KeyConstant.REQUEST_CODE_GENERAL:
                    RecognizeService.recGeneral(mContext, absolutePath, new DataProcessingAdapter(mContext));
                    break;
                *//**//*识别成功回调，通用文字识别（含位置信息高精度版）*//**//*
                case KeyConstant.REQUEST_CODE_ACCURATE:
                    RecognizeService.recAccurate(mContext, absolutePath, new DataProcessingAdapter(mContext));
                    break;
                *//**//*识别成功回调，通用文字识别*//**//*
                case KeyConstant.REQUEST_CODE_GENERAL_BASIC:
                    RecognizeService.recGeneralBasic(mContext, absolutePath, new DataProcessingAdapter(mContext));
                    break;
                *//**//* 识别成功回调，通用文字识别（高精度版）*//**//*
                case KeyConstant.REQUEST_CODE_ACCURATE_BASIC:
                    RecognizeService.recAccurateBasic(mContext, absolutePath, new DataProcessingAdapter(mContext));
                    break;
                *//**//*识别成功回调，通用文字识别（含生僻字版）*//**//*
                case KeyConstant.REQUEST_CODE_GENERAL_ENHANCED:
                    RecognizeService.recGeneralEnhanced(mContext, absolutePath, new DataProcessingAdapter(mContext));
                    break;

                *//**//*识别成功回调，网络图片文字识别*//**//*
                case KeyConstant.REQUEST_CODE_GENERAL_WEBIMAGE:
                    RecognizeService.recWebimage(mContext, absolutePath, new DataProcessingAdapter(mContext));
                    break;
                *//**//* 识别成功回调，银行卡识别*//**//*
                case KeyConstant.REQUEST_CODE_BANKCARD:
                    RecognizeService.recBankCard(mContext, absolutePath, new DataProcessingAdapter(mContext));
                    break;
                *//**//*识别成功回调，行驶证识别*//**//*
                case KeyConstant.REQUEST_CODE_VEHICLE_LICENSE:
                    RecognizeService.recVehicleLicense(mContext, absolutePath, new DataProcessingAdapter(mContext));
                    break;
                *//**//* 识别成功回调，驾驶证识别*//**//*
                case KeyConstant.REQUEST_CODE_DRIVING_LICENSE:
                    RecognizeService.recDrivingLicense(mContext, absolutePath, new DataProcessingAdapter(mContext));
                    break;
                *//**//* 识别成功回调，车牌识别*//**//*
                case KeyConstant.REQUEST_CODE_LICENSE_PLATE:
                    RecognizeService.recLicensePlate(mContext, absolutePath, new DataProcessingAdapter(mContext));
                    break;
                *//**//*识别成功回调，营业执照识别*//**//*
                case KeyConstant.REQUEST_CODE_BUSINESS_LICENSE:
                    RecognizeService.recBusinessLicense(mContext, absolutePath, new DataProcessingAdapter(mContext));
                    break;
                *//**//*识别成功回调，通用票据识别*//**//*
                case KeyConstant.REQUEST_CODE_RECEIPT:
                    RecognizeService.recReceipt(mContext, absolutePath, new DataProcessingAdapter(mContext));
                    break;
                *//**//*识别成功回调，护照*//**//*
                case KeyConstant.REQUEST_CODE_PASSPORT:
                    RecognizeService.recPassport(mContext, absolutePath, new DataProcessingAdapter(mContext));
                    break;
                *//**//*识别成功回调，二维码*//**//*
                case KeyConstant.REQUEST_CODE_QRCODE:
                    RecognizeService.recQrcode(mContext, absolutePath, new DataProcessingAdapter(mContext));
                    break;
                *//**//*识别成功回调，彩票*//**//*
                case KeyConstant.REQUEST_CODE_LOTTERY:
                    RecognizeService.recLottery(mContext, absolutePath, new DataProcessingAdapter(mContext));
                    break;
                *//**//*识别成功回调，增值税发票*//**//*
                case KeyConstant.REQUEST_CODE_VATINVOICE:
                    RecognizeService.recVatInvoice(mContext, absolutePath, new DataProcessingAdapter(mContext));
                    break;
                *//**//*识别成功回调，数字*//**//*
                case KeyConstant.REQUEST_CODE_NUMBERS:
                    RecognizeService.recNumbers(mContext, absolutePath, new DataProcessingAdapter(mContext));
                    break;
                *//**//* 识别成功回调，手写*//**//*
                case KeyConstant.REQUEST_CODE_HANDWRITING:
                    RecognizeService.recHandwriting(mContext, absolutePath, new DataProcessingAdapter(mContext));
                    break;
                *//**//*识别成功回调，名片*//**//*
                case KeyConstant.REQUEST_CODE_BUSINESSCARD:
                    RecognizeService.recBusinessCard(mContext, absolutePath, new DataProcessingAdapter(mContext));
                    break;
                *//**//*识别成功回调，自定义模板*//**//*
                case KeyConstant.REQUEST_CODE_CUSTOM:
                    RecognizeService.recCustom(mContext, absolutePath, new DataProcessingAdapter(mContext));
                    break;
                default:
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放内存资源
        OCR.getInstance(mContext).release();
    }*//*
    }
}*/
