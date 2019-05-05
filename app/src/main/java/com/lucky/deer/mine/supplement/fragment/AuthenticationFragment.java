package com.lucky.deer.mine.supplement.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.demo.cloudstorage.executor.StartUpload;
import com.dykj.requestcore.util.RequestUtils;
import com.google.gson.Gson;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseFragment;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.recognition.FileUtil;
import com.lucky.deer.recognition.Package.executor.StartObtainInfo;
import com.lucky.deer.util.StringUtil;
import com.lucky.model.request.certification.SaveVerifyData;
import com.lucky.model.request.userinfo.PerfectUserInfoReq;
import com.lucky.model.response.ResponseData;
import com.lucky.model.response.selectbankcard.SelectBankCardList;
import com.lucky.model.util.HintUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 完善信息：身份证认证
 *
 * @author wangxiangyi
 * @date 2018/10/12
 */
public class AuthenticationFragment extends BaseFragment {
    /**
     * 身份证前面
     */
    @BindView(R.id.iv_id_card)
    ImageView ivIdCard;
    /**
     * 姓名
     */
    @BindView(R.id.et_name)
    TextView etName;
    /**
     * 身份证号
     */
    @BindView(R.id.tv_identity_number)
    TextView tvIdentityNumber;
    /**
     * 身份证后面
     */
    @BindView(R.id.iv_reverse_photo)
    ImageView ivReversePhoto;
    /**
     * 身份证有效日期
     */
    @BindView(R.id.tv_valid_until)
    TextView tvValidUntil;
    /**
     * 下一步按钮
     */
    @BindView(R.id.tv_authentication_next)
    TextView tvAuthenticationNext;
    /**
     * 文件路径
     */
    private String absolutePath;
    /**
     * 身份证正面
     */
    private String POSITIVE;
    /**
     * 身份证反面
     */
    private String NEGATIVE;
    /**
     * 民族，地址
     */
    private String ethnic, address;
    /**
     * 查询用户默认储蓄卡信息
     */
    private SelectBankCardList mData;

    public static AuthenticationFragment newInstance(Bundle args) {
        AuthenticationFragment fragment = new AuthenticationFragment();
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
        return R.layout.fragment_authentication;
    }

    @Override
    protected void initView() {
        tvAuthenticationNext.setText(R.string.ok);
        initAccessToken();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
//        /*查询默认储蓄卡信息*/
//        showLoadingDialog();
//        mNetworkRequestInstance.getDefaultDebitCardVo()
//                .subscribe(returnData -> {
//                    dismissLoadingDialog();
//                    if (RequestUtils.isRequestSuccess(returnData)) {
//                        mData = returnData.getData();
//                    } else {
//                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
//                    }
//                });
    }

    @OnClick({R.id.iv_id_card, R.id.iv_reverse_photo, R.id.tv_authentication_next})
    public void onViewClicked(View view) {
        absolutePath = FileUtil.getSaveFile(mActivity).getAbsolutePath();
        Intent intent = new Intent(mActivity, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, absolutePath);
        //使用本地质量控制能力需要授权
        intent.putExtra(CameraActivity.KEY_NATIVE_TOKEN, OCR.getInstance().getLicense());
        //设置本地质量使用开启
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE, true);
        switch (view.getId()) {
            case R.id.iv_id_card:
                //设置扫描的身份证的类型（正面front）
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, KeyConstant.REQUEST_CODE_CAMERA_POSITIVE);
                break;
            case R.id.iv_reverse_photo:
                //设置扫描的身份证的类型（反面back）
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                startActivityForResult(intent, KeyConstant.REQUEST_CODE_CAMERA_NEGATIVE);
                break;
            case R.id.tv_authentication_next:
                examineRequiredVerification();
                break;
            default:
        }
    }

    @Override
    protected boolean examineRequiredVerification() {
        if (TextUtils.isEmpty(POSITIVE) || TextUtils.isEmpty(etName.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mActivity, getString(R.string.toast_please_upload_id_card_face));
            return false;
        } else if (TextUtils.isEmpty(tvIdentityNumber.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mActivity, getString(R.string.toast_please_upload_identification_number));
            return false;
        } else if (TextUtils.isEmpty(NEGATIVE) || TextUtils.isEmpty(tvValidUntil.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mActivity, getString(R.string.toast_please_upload_id_card_card_reverse));
            return false;
        }
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        submitAuthentication();
//        if (mData == null) {
//            initData();
//        } else {
//            FourFactorAuthenticationReq fourFactorAuthenticationReq = new FourFactorAuthenticationReq();
//            /*开户行名称*/
//            fourFactorAuthenticationReq.setName(etName.getText().toString().trim());
//            /*身份证号*/
//            fourFactorAuthenticationReq.setIdNo(tvIdentityNumber.getText().toString().trim());
//            /*预留手机号*/
//            fourFactorAuthenticationReq.setPhoneNo(mData.getPhone());
//            /*银行卡号*/
//            fourFactorAuthenticationReq.setCardNo(mData.getCarNumber());
//            showLoadingDialog();
//            mNetworkRequestInstance.bankAuthenticate(fourFactorAuthenticationReq)
//                    .subscribe(returnData -> {
//                        if (RequestUtils.isFourElementsSuccess(returnData)) {
//                            submitAuthentication();
//                        } else {
//                            dismissLoadingDialog();
//                            HintUtil.showErrorWithToast(mActivity, returnData.getRespMessage());
//                        }
//                        setStatisticsFourElement(returnData);
//                    });
//        }
    }

    /**
     * 提交补录信息
     */
    @SuppressLint("CheckResult")
    public void submitAuthentication() {
        PerfectUserInfoReq perfectUserInfoReq = new PerfectUserInfoReq();
        /*名称*/
        perfectUserInfoReq.setRealName(etName.getText().toString().trim());
        /*民族*/
        perfectUserInfoReq.setNation(ethnic);
        /*身份证号*/
        perfectUserInfoReq.setIdCard(tvIdentityNumber.getText().toString().trim());
        /*地址*/
        perfectUserInfoReq.setDetailedAddress(address);
        /*身份证有效期*/
        perfectUserInfoReq.setEffectiveDate(tvValidUntil.getText().toString().trim());
        /*身份证正面*/
        perfectUserInfoReq.setCardBackPic(HttpConstant.QINIU_URL + NEGATIVE);
        /*身份证反面*/
        perfectUserInfoReq.setCardFrontPic(HttpConstant.QINIU_URL + POSITIVE);
        showLoadingDialog();
        mNetworkRequestInstance.userRealNameAuthenticationOne(perfectUserInfoReq)
                .subscribe(stringBeanResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(stringBeanResponseData)) {
                        showSuccessDialog("信息补录成功");
                    } else {
                        HintUtil.showErrorWithToast(mActivity, stringBeanResponseData.getMsg());
                    }
                });
    }

    /**
     * 统计四要素
     *
     * @param returnData
     */
    @SuppressLint("CheckResult")
    private void setStatisticsFourElement(ResponseData<String> returnData) {
        SaveVerifyData saveVerifyData = new SaveVerifyData();
        saveVerifyData.setName(returnData.getName());
        saveVerifyData.setIdNo(returnData.getIdNo());
        saveVerifyData.setPhoneNo(returnData.getPhoneNo());
        saveVerifyData.setCardNo(returnData.getCardNo());
        saveVerifyData.setResultJson(new Gson().toJson(returnData));
        saveVerifyData.setCompanyId(getUserInfo() == null ? "" : getUserInfo().getCompanyId());
        mNetworkRequestInstance.saveVerifyData(saveVerifyData)
                .subscribe(t -> {
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            recIDCard(requestCode, absolutePath);
        }
    }

    /**
     * 解析身份证图片
     *
     * @param idCardSide 身份证正反面
     * @param filePath   图片路径
     */
    @SuppressLint({"CheckResult", "SetTextI18n"})
    private void recIDCard(int idCardSide, String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            showLoadingDialog();
            StartObtainInfo.getInstance()
                    .getIDCardInfo(idCardSide, filePath)
                    .subscribe(idCardResult -> {
                        if (RequestUtils.isRequestSuccess(idCardResult)) {
                            if (idCardResult.getData() != null) {
                                IDCardResult result = idCardResult.getData();
                                /*正面*/
                                if (KeyConstant.REQUEST_CODE_CAMERA_POSITIVE == idCardSide) {
                                    /*正面*/
                                    if (result.getAddress() == null ||
                                            result.getName() == null ||
                                            result.getEthnic() == null ||
                                            result.getIdNumber() == null) {
                                        HintUtil.showErrorWithToast(mActivity, "请重新进行身份证识别");
                                    } else {
                                        uploadImage(idCardSide, filePath);
                                        /*设置前面图片*/
                                        ivIdCard.setImageBitmap(StringUtil.decodeStream(filePath));
                                        /*用户名称*/
                                        etName.setText((TextUtils.isEmpty(result.getName().getWords()) ? "" : result.getName().getWords()));
                                        ethnic = result.getEthnic().getWords();
                                        address = result.getAddress().getWords();
                                        /*用户身份证号*/
                                        tvIdentityNumber.setText((result.getIdNumber() == null &&
                                                TextUtils.isEmpty(result.getIdNumber().getWords()) ? "" :
                                                result.getIdNumber().getWords()));
                                    }
                                } else {/*反面*/
                                    if (result.getSignDate() == null && result.getExpiryDate() == null) {
                                        HintUtil.showErrorWithToast(mActivity, "请重新拍摄身份证背面");
                                    } else {
                                        /*判断身份反面是否识别出证件有效期*/
                                        if (TextUtils.isEmpty(result.getSignDate().getWords()) || TextUtils.isEmpty(result.getExpiryDate().getWords())) {
                                            HintUtil.showErrorWithToast(mActivity, "请重新拍摄身份证背面");
                                        } else {
                                            uploadImage(idCardSide, filePath);
                                            /*设置背面图片*/
                                            ivReversePhoto.setImageBitmap(StringUtil.decodeStream(filePath));
                                            /*失效日期*/
                                            tvValidUntil.setText((TextUtils.isEmpty(result.getSignDate().getWords()) ? "" : result.getSignDate().getWords()) + "-" +
                                                    (TextUtils.isEmpty(result.getExpiryDate().getWords()) ? "" : result.getExpiryDate().getWords()));
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
    }

    /**
     * 上传图片
     *
     * @param idCardSide 正面或反面
     * @param imagePath  图片地址
     */
    @SuppressLint("CheckResult")
    private void uploadImage(int idCardSide, String imagePath) {
        StartUpload
                .getInstance()
                .upload(null, imagePath)
                .subscribe(baseReq -> {
                    dismissLoadingDialog();
                    /*正面*/
                    if (KeyConstant.REQUEST_CODE_CAMERA_POSITIVE == idCardSide) {
                        POSITIVE = baseReq.getKey();
                    } else {
                        NEGATIVE = baseReq.getKey();
                    }
                });
    }

    //授权文件（安全模式）
    //此种身份验证方案使用授权文件获得AccessToken，缓存在本地。建议有安全考虑的开发者使用此种身份验证方式。
    private void initAccessToken() {
        OCR.getInstance().initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                // 调用成功，返回AccessToken对象
                String token = accessToken.getAccessToken();
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
            }
        }, getActivity());
    }
}
