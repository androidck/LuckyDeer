package com.lucky.deer.mine.perfectuserInfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.demo.cloudstorage.executor.StartUpload;
import com.dykj.requestcore.util.RequestUtils;
import com.google.gson.Gson;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.home.MainActivity;
import com.lucky.deer.mine.OptionsWindowHelper;
import com.lucky.deer.recognition.FileUtil;
import com.lucky.deer.recognition.Package.executor.StartObtainInfo;
import com.lucky.deer.util.RegexUtil;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.util.SupportBankEnum;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.enums.PublicEnum;
import com.lucky.model.common.fourelements.FourElementsEntity;
import com.lucky.model.request.certification.FourFactorAuthenticationReq;
import com.lucky.model.request.certification.SaveVerifyData;
import com.lucky.model.request.userinfo.PerfectUserInfoReq;
import com.lucky.model.response.ResponseData;
import com.lucky.model.response.mine.QueryIdentityAuthEntity;
import com.lucky.model.response.perfectinformation.BankInfoVoEntity;
import com.lucky.model.response.userinfo.UserInfo;
import com.lucky.model.util.DisplayUtils;
import com.lucky.model.util.HawkUtil;
import com.lucky.model.util.HintUtil;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jeesoft.widget.pickerview.CharacterPickerView;
import io.reactivex.functions.Consumer;

/**
 * 储蓄卡资料完善
 */
public class BankCardActivity extends BaseActivity {

    @BindView(R.id.iv_id_card)
    ImageView ivIdCard;
    @BindView(R.id.tv_bank_card_name)
    TextView tvBankCardName;
    @BindView(R.id.et_bank_card_number)
    EditText etBankCardNumber;
    @BindView(R.id.tv_select_account_opening_area)
    TextView tvSelectAccountOpeningArea;
    @BindView(R.id.tv_select_bank)
    TextView tvSelectBank;
    @BindView(R.id.et_reserve_phone_number)
    EditText etReservePhoneNumber;
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.ll_select_account_opening_area)
    LinearLayout llSelectAccountOpeningArea;
    @BindView(R.id.ll_select_bank)
    LinearLayout llSelectBank;
    @BindView(R.id.tv_submit_review)
    TextView tvSubmitReview;
    /**
     * 扫描照面路径
     */
    private String absolutePath;
    /**
     * 图片链接
     */
    private String debitCardUrl;
    /**
     * accountOpeningArea：开户地区
     * selectBank：选择开户行
     */
    private View accountOpeningArea, selectBank;
    /**
     * 列表弹出框
     */
    private QMUIBottomSheet bottomAccountOpeningArea, bottomSelectBank;
    /**
     * 总行id
     */
    private String bankInfoId;

    private CharacterPickerView pickerView, selectBankpickerView;
    /**
     * 查询用户使命认证信息
     */
    private QueryIdentityAuthEntity queryIdentityAuth;

    @Override
    protected int initLayout() {
        return R.layout.activity_bank_card;
    }


    @Override
    protected void initView() {
        accountOpeningArea = LinearLayout.inflate(mActivity, R.layout.dialog_account_opening_area, null);
        selectBank = LinearLayout.inflate(mActivity, R.layout.dialog_account_opening_area, null);
        accountOpeningArea.findViewById(R.id.tv_cancel)
                .setOnClickListener(v -> {
                    bottomAccountOpeningArea.dismiss();
                });
        accountOpeningArea.findViewById(R.id.tv_ok)
                .setOnClickListener(v -> {
                    tvSelectAccountOpeningArea.setText(OptionsWindowHelper.getNameAll());
                    tvSelectAccountOpeningArea.setTag(OptionsWindowHelper.getCode());
                    bottomAccountOpeningArea.dismiss();
                });
        LinearLayout llView = accountOpeningArea.findViewById(R.id.ll_view);


        selectBank.findViewById(R.id.tv_cancel)
                .setOnClickListener(v -> {
                    bottomSelectBank.dismiss();
                });
        selectBank.findViewById(R.id.tv_ok)
                .setOnClickListener(v -> {
                    tvSelectBank.setText(OptionsWindowHelper.getSelectBankpickerName());
                    tvSelectBank.setTag(OptionsWindowHelper.getSelectBankpickerCode());
                    bottomSelectBank.dismiss();
                });
        LinearLayout llSelectBankView = selectBank.findViewById(R.id.ll_view);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dp2px(mActivity, 250));
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        pickerView = new CharacterPickerView(mActivity);
        pickerView.setMaxTextSize(13);
        llView.addView(pickerView, layoutParams);
        selectBankpickerView = new CharacterPickerView(mActivity);
        selectBankpickerView.setMaxTextSize(13);
        llSelectBankView.addView(selectBankpickerView, layoutParams);
        initTopBar();
        initAccessToken();
        /*格式化银行卡号*/
        StringUtil.setBankCardNumAddSpace(etBankCardNumber);
        /*格式化手机号*/
        StringUtil.setPhoneWatcher(etReservePhoneNumber);
    }

    private void initTopBar() {
        topBar.setTitle("完善信息");
        topBar.addRightTextButton("稍后完善", R.id.btn_right)
                .setOnClickListener(v -> {
                    Intent intent = new Intent(BankCardActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });
        topBar.addLeftImageButton(R.mipmap.back, R.id.btn_left)
                .setOnClickListener(v -> {
                    Intent intent = new Intent(BankCardActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        areaList();
        List<BankInfoVoEntity> bankInfo = HawkUtil.getInstance().getSaveData(KeyConstant.BANK_INFO);
        if (bankInfo == null) {
            /*获取总行信息*/
            mNetworkRequestInstance.getBankInfoVo()
                    .subscribe(listResponseData -> {
                        if (RequestUtils.isRequestSuccess(listResponseData)) {
                            HawkUtil.getInstance().saveData(KeyConstant.BANK_INFO, listResponseData.getData());
                        } else {
                            HintUtil.showErrorWithToast(mContext, listResponseData.getMsg());
                        }
                    });
        }
        queryIdentityAuth();
    }

    /**
     * 查询用户使命认证信息
     */
    @SuppressLint("CheckResult")
    private void queryIdentityAuth() {
        /*查询用户使命认证信息*/
        showLoadingDialog();
        mNetworkRequestInstance.queryIdentityAuth()
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        queryIdentityAuth = returnData.getData();
                    } else {
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }

    @OnClick({R.id.tv_authentication, R.id.tv_held_identity_card, R.id.iv_id_card,
            R.id.ll_select_account_opening_area, R.id.ll_select_bank, R.id.tv_submit_review})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*身份证认证*/
            case R.id.tv_authentication:
                jumpActivity(mContext, AuthenticationActivity.class);
                break;
            /*手持身份证*/
            case R.id.tv_held_identity_card:
                jumpActivity(mContext, HeldIdentityActivity.class);
                break;
            /*储蓄卡拍照按钮*/
            case R.id.iv_id_card:
                absolutePath = FileUtil.getBankFile(mActivity).getAbsolutePath();
                Intent intent = new Intent(mActivity, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, absolutePath);
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_BANK_CARD);
                startActivityForResult(intent, KeyConstant.DEBIT_CARD_POSITIVE);
                break;
            /*银行卡号*/
            case R.id.et_bank_card_number:
                showSoftInputFromWindow(BankCardActivity.this, etBankCardNumber);
                break;
            /*开户地区*/
            case R.id.ll_select_account_opening_area:
                if (TextUtils.isEmpty(StringUtil.removeAllSpace(etBankCardNumber.getText().toString().trim()))) {
                    HintUtil.showErrorWithToast(mActivity, getString(R.string.hint_bank_card_number));
                    StringUtil.clearData(etBankCardNumber);
                    return;
                }
                getBankAbbreviation();
                bottomAccountOpeningArea = PublicDialog.getInstance().addHeaderView(mActivity, accountOpeningArea);
//                bottomAccountOpeningArea.setOnDismissListener(dialog -> {
//                    tvSelectAccountOpeningArea.setText(OptionsWindowHelper.getNameAll());
//                    tvSelectAccountOpeningArea.setTag(OptionsWindowHelper.getCode());
//                });
                break;
            /*选择开户行*/
            case R.id.ll_select_bank:
                if (TextUtils.isEmpty(tvSelectAccountOpeningArea.getText().toString().trim())) {
                    HintUtil.showErrorWithToast(mActivity, getString(R.string.hint_account_opening_area));
                    return;
                } else if (TextUtils.isEmpty(bankInfoId)) {
                    HintUtil.showErrorWithToast(mActivity, getString(R.string.toast_bank_not_supported));
                    return;
                }else {
                    bankInfo(bankInfoId);
                }
                break;
            case R.id.tv_submit_review:
                examineRequiredVerification();
                break;
            default:
        }
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    protected boolean examineRequiredVerification() {
        if (TextUtils.isEmpty(StringUtil.removeAllSpace(etBankCardNumber.getText().toString().trim()))) {
            HintUtil.showErrorWithToast(mActivity, getString(R.string.hint_bank_card_number));
            return false;
        } else if (TextUtils.isEmpty(tvSelectAccountOpeningArea.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mActivity, getString(R.string.hint_account_opening_area));
            return false;
        } else if (TextUtils.isEmpty(tvSelectBank.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mActivity, getString(R.string.hint_please_select_bank));
            return false;
        } else if (!RegexUtil.isMobileExact(StringUtil.removeAllSpace(etReservePhoneNumber.getText().toString().trim()))) {
            HintUtil.showErrorWithToast(mActivity, R.string.toast_please_phone_number);
            return false;
        }
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        if (queryIdentityAuth == null) {
            queryIdentityAuth();
        } else {
//            FourFactorAuthenticationReq fourFactorAuthenticationReq = new FourFactorAuthenticationReq();
//            /*开户行名称*/
//            fourFactorAuthenticationReq.setName(queryIdentityAuth.getRealName());
//            /*身份证号*/
//            fourFactorAuthenticationReq.setIdNo(queryIdentityAuth.getIdCard());
//            /*预留手机号*/
//            fourFactorAuthenticationReq.setPhoneNo(StringUtil.removeAllSpace(etReservePhoneNumber.getText().toString().trim()));
//            /*银行卡号*/
//            fourFactorAuthenticationReq.setCardNo(StringUtil.removeAllSpace(etBankCardNumber.getText().toString().trim()));
//            showLoadingDialog();
//            mNetworkRequestInstance.bankAuthenticate(fourFactorAuthenticationReq)
//                    .subscribe(returnData -> {
//                        if (RequestUtils.isFourElementsSuccess(returnData)) {
//                            setAddCard();
//                        } else {
//                            dismissLoadingDialog();
//                            showFailedDialog(R.string.data_loading_massage2);
//                        }
//                        setStatisticsFourElement(returnData);
//                    });
//            /*京东万象四要素*/
//            FourFactorAuthenticationReq fourFactorAuthenticationReq = new FourFactorAuthenticationReq();
//            /*开户行名称*/
//            fourFactorAuthenticationReq.setRealname(queryIdentityAuth.getRealName());
//            /*身份证号*/
//            fourFactorAuthenticationReq.setIdcard(queryIdentityAuth.getIdCard());
//            /*预留手机号*/
//            fourFactorAuthenticationReq.setPhone(StringUtil.removeAllSpace(etReservePhoneNumber.getText().toString().trim()));
//            /*银行卡号*/
//            fourFactorAuthenticationReq.setBankcard(StringUtil.removeAllSpace(etBankCardNumber.getText().toString().trim()));
//            showLoadingDialog();
//            mNetworkRequestInstance.jingdongBankAuthenticate(fourFactorAuthenticationReq)
//                    .subscribe(returnData -> {
//                        if (RequestUtils.isRequestSuccess(returnData)) {
//                            setAddCard();
//                        } else {
//                            dismissLoadingDialog();
//                            showFailedDialog(R.string.data_loading_massage2);
//                        }
//                        setStatisticsFourElement(returnData);
//                    });
//        }
            /*四要素认证*/
            FourFactorAuthenticationReq fourFactorAuthenticationReq = new FourFactorAuthenticationReq();
            /*公司id*/
            fourFactorAuthenticationReq.setCompanyId(getUserInfo() == null ? "" : getUserInfo().getCompanyId());
            /*预留手机号*/
            fourFactorAuthenticationReq.setPhone(StringUtil.removeAllSpace(etReservePhoneNumber.getText().toString().trim()));
            /*银行卡号*/
            fourFactorAuthenticationReq.setBankcard(StringUtil.removeAllSpace(etBankCardNumber.getText().toString().trim()));
            showLoadingDialog();
            mNetworkRequestInstance.elementsValidate(fourFactorAuthenticationReq)
                    .subscribe(returnData -> {
                        if (RequestUtils.isRequestSuccess(returnData)) {
                            setAddCard();
                        } else {
                            dismissLoadingDialog();
                            showFailedDialog(R.string.data_loading_massage2);
                        }
                    });


        }
    }

    /**
     * 添加储蓄卡接口
     */
    @SuppressLint("CheckResult")
    private void setAddCard() {
        PerfectUserInfoReq perfectUserInfoReq = new PerfectUserInfoReq();
        /*银行卡号*/
        perfectUserInfoReq.setCarNumber(StringUtil.removeAllSpace(etBankCardNumber.getText().toString().trim()));
        /*开户银行名称*/
        perfectUserInfoReq.setOpenBank(tvBankCardName.getText().toString().trim());
        /*预留手机号*/
        perfectUserInfoReq.setPhone(StringUtil.removeAllSpace(etReservePhoneNumber.getText().toString().trim()));
        /*区域id*/
        perfectUserInfoReq.setAreaCode(tvSelectAccountOpeningArea.getTag().toString());
        /*银行卡照片*/
        perfectUserInfoReq.setPhoto(HttpConstant.QINIU_URL + debitCardUrl);
        /*支银行卡id不能为空*/
        perfectUserInfoReq.setBankId(tvSelectBank.getTag() + "");
        /*是否默认使用*/
        perfectUserInfoReq.setIsDefault("1");
        showLoadingDialog();
        mNetworkRequestInstance.userBankCardBinding(perfectUserInfoReq)
                .subscribe(stringBeanResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(stringBeanResponseData)) {
                        showSuccessDialog(R.string.dialog_improve_info_success);
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
    //    private void setStatisticsFourElement(ResponseData<String> returnData) {
    private void setStatisticsFourElement(ResponseData<FourElementsEntity> returnData) {
//        SaveVerifyData saveVerifyData = new SaveVerifyData();
//        saveVerifyData.setName(returnData.getName());
//        saveVerifyData.setIdNo(returnData.getIdNo());
//        saveVerifyData.setPhoneNo(returnData.getPhoneNo());
//        saveVerifyData.setCardNo(returnData.getCardNo());
//        saveVerifyData.setResultJson(new Gson().toJson(returnData));
//        saveVerifyData.setCompanyId(getUserInfo() == null ? "" : getUserInfo().getCompanyId());
        SaveVerifyData saveVerifyData = new SaveVerifyData();
        /*开户行名称*/
        saveVerifyData.setName(queryIdentityAuth.getRealName());
        /*身份证号*/
        saveVerifyData.setIdNo(queryIdentityAuth.getIdCard());
        /*预留手机号*/
        saveVerifyData.setPhoneNo(StringUtil.removeAllSpace(etReservePhoneNumber.getText().toString().trim()));
        /*银行卡号*/
        saveVerifyData.setCardNo(StringUtil.removeAllSpace(etBankCardNumber.getText().toString().trim()));
        /*返回参数*/
        saveVerifyData.setResultJson(new Gson().toJson(returnData));
        /*公司id*/
        saveVerifyData.setCompanyId(getUserInfo() == null ? "" : getUserInfo().getCompanyId());
        mNetworkRequestInstance.saveVerifyData(saveVerifyData).subscribe(t -> {
        });
    }

    @Override
    public void carriedOutMethod() {
        UserInfo userInfo = HawkUtil.getInstance().getSaveData(HawkUtil.USER_INFO);
        if (userInfo != null) {
            /*设置完善信息状态*/
            userInfo.setRegisterState(4);
            HawkUtil.getInstance().saveData(HawkUtil.USER_INFO, userInfo);
            jumpActivity(mContext, MainActivity.class, true);
//            Intent intent = new Intent(BankCardActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == KeyConstant.DEBIT_CARD_POSITIVE) {
            bankCardIdentification(absolutePath);
            bankInfoId = "";
        }
    }

    /**
     * 获取银行卡信息
     *
     * @param absolutePath
     */
    @SuppressLint("CheckResult")
    private void bankCardIdentification(String absolutePath) {
        showLoadingDialog();
        StartObtainInfo.getInstance()
                .getBankCardInfo(absolutePath)
                .subscribe(bankCardResult -> {
                    /*判断识别银行卡名称是否为空*/
                    if (RequestUtils.isRequestSuccess(bankCardResult)) {
                        /*判断是否是储蓄卡*/
                        if (1 == PublicEnum.getEnumCardType(bankCardResult.getData().getBankCardType().name())) {
                            ivIdCard.setImageBitmap(StringUtil.decodeStream(absolutePath));
                            etBankCardNumber.setText(bankCardResult.getData().getBankCardNumber());
                            /*通过高德接口识别银行卡号是否正确*/
                            getBankAbbreviation();
                        } else {
                            dismissLoadingDialog();
                            HintUtil.showErrorWithToast(mActivity, getString(R.string.toast_please_take_savings_card));
                        }
                    } else {
                        dismissLoadingDialog();
                        HintUtil.showErrorWithToast(mActivity, bankCardResult.getMsg());
                    }
                });
    }


    /**
     * 获取银行缩写名称
     */
    @SuppressLint("CheckResult")
    public void getBankAbbreviation() {
        String bankNo = StringUtil.removeAllSpace(etBankCardNumber.getText().toString().trim());
        if (TextUtils.isEmpty(bankNo)) {
            dismissLoadingDialog();
            StringUtil.clearData(tvSelectAccountOpeningArea, tvSelectBank);
            HintUtil.showErrorWithToast(mActivity, getString(R.string.hint_bank_card_number));
            return;
        }
        if (bottomAccountOpeningArea != null) {
            showLoadingDialog();
        }
        Log.d("asdasd", bankNo);
        mNetworkRequestInstance.getBankAbbreviation(bankNo)
                .subscribe(stringResponseData -> {
                    /*验证银行卡号是否正确*/
                    if (stringResponseData.isValidated() == true) {
                        /*判断时候是储蓄卡*/
                        if (1 == PublicEnum.getEnumCardType(stringResponseData.getCardType())) {
                            /*判断是否支持此银行*/
                            if (SupportBankEnum.isSupportTheBank(stringResponseData.getBank())) {
                                /*获取总行信息*/
                                List<BankInfoVoEntity> bankInfo = HawkUtil.getInstance().getSaveData(KeyConstant.BANK_INFO);
                                if (bankInfo != null) {
                                    for (BankInfoVoEntity bankInfoVoEntity : bankInfo) {
                                        if (stringResponseData.getBank().equals(bankInfoVoEntity.getBankShortName())) {
                                            /*上传到七牛云*/
                                            if (!TextUtils.isEmpty(absolutePath)) {
                                                uploadImg2QiNiu(absolutePath);
                                            }
                                            bankInfoId = bankInfoVoEntity.getId();
                                            tvBankCardName.setText(bankInfoVoEntity.getBankName());
                                            if (bottomAccountOpeningArea != null) {
                                                bottomAccountOpeningArea.show();
                                            } else {
                                                bottomAccountOpeningArea = null;
                                            }
                                            break;
                                        } else {
                                            bankInfoId = "";
                                        }
                                    }
                                    if (TextUtils.isEmpty(bankInfoId)) {
                                        dismissLoadingDialog();
                                        HintUtil.showErrorWithToast(mActivity, getString(R.string.toast_bank_not_supported));
                                    } else {
                                        dismissLoadingDialog();
                                    }
                                }
                            } else {
                                PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), R.string.supported_savings_card, getString(R.string.cancel));
                                dismissLoadingDialog();
                                ivIdCard.setImageResource(R.mipmap.bank_card);
                                bankInfoId = "";
                            }
                        } else {
                            bankInfoId = "";
                            dismissLoadingDialog();
                            HintUtil.showErrorWithToast(mActivity, getString(R.string.toast_please_take_savings_card));
                        }
                    } else {
                        bankInfoId = "";
                        dismissLoadingDialog();
                        HintUtil.showErrorWithToast(mActivity, getString(R.string.hint_bank_card_number));
                        StringUtil.clearData(tvSelectAccountOpeningArea, tvSelectBank);
                    }
                });
    }


    /**
     * 获取区域id
     */
    @SuppressLint("CheckResult")
    private void areaList() {
        showLoadingDialog();
        mNetworkRequestInstance.getAllAreaList()
                .subscribe(listResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(listResponseData)) {
                        //初始化选项数据
                        OptionsWindowHelper.setThreeLevelLinkagePickerData(pickerView, listResponseData.getData());
                    } else {
                        HintUtil.showErrorWithToast(mActivity, listResponseData.getMsg());
                    }
                });
    }

    /**
     * 获取支行信息列表
     *
     * @param bankInfoId 总行id
     */
    @SuppressLint("CheckResult")
    private void bankInfo(String bankInfoId) {
        if (TextUtils.isEmpty(tvSelectAccountOpeningArea.getTag() + "")) {
            HintUtil.showErrorWithToast(mActivity, getString(R.string.hint_account_opening_area));
            return;
        }
        PerfectUserInfoReq perfectUserInfoReq = new PerfectUserInfoReq();
        /*总行id */
        perfectUserInfoReq.setParentId(bankInfoId);
        /*城市id*/
        perfectUserInfoReq.setCityId(tvSelectAccountOpeningArea.getTag() + "");
        showLoadingDialog();
        mNetworkRequestInstance.bankBranch(perfectUserInfoReq)
                .subscribe(listResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(listResponseData)) {
                        if (listResponseData.getData() != null) {
                            OptionsWindowHelper.setOneLevelLinkagePickerData(selectBankpickerView, listResponseData.getData());
                            bottomSelectBank = PublicDialog.getInstance().addHeaderView2(mActivity, selectBank);
//                            bottomSelectBank.setOnDismissListener(dialog -> {
//                                tvSelectBank.setText(OptionsWindowHelper.getSelectBankpickerName());
//                                tvSelectBank.setTag(OptionsWindowHelper.getSelectBankpickerCode());
//                            });
                            bottomSelectBank.show();
                        } else {
                            StringUtil.clearData(tvSelectBank);
                            HintUtil.showErrorWithToast(mActivity, R.string.toast_not_account_info_yet);
                        }
                    } else {
                        StringUtil.clearData(tvSelectBank);
                        HintUtil.showErrorWithToast(mActivity, listResponseData.getMsg());
                    }
                });
    }


    /**
     * 上传图片
     *
     * @param imagePath
     */
    @SuppressLint("CheckResult")
    private void uploadImage(String imagePath) {
        StartUpload
                .getInstance()
                .upload(null, imagePath)
                .subscribe(baseReq -> {
                    dismissLoadingDialog();
                    debitCardUrl = baseReq.getKey();

                });
    }

    /**
     * 七牛云上传工具类
     */
    private void uploadImg2QiNiu(String filePath) {
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认 256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认 512K
                .connectTimeout(10) // 链接超时。默认 10秒
                .responseTimeout(60) // 服务器响应超时。默认 60秒
                .zone(FixedZone.zone0) // 设置区域，指默认 Zone.zone0 <span style="font-size:14px;"><strong><span style="color:#FF0000;">注：这步是最关键的 当初错的主要原因也是他 根据自己的地方选</span></strong></span>
                .build();

        UploadManager uploadManager = new UploadManager();
        // 设置图片名字
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String key = "icon_" + sdf.format(new Date());
        String picPath = filePath;

        uploadManager.put(picPath, key, Auth.create(HttpConstant.QINIU_AK, HttpConstant.QIUNIU_SK).uploadToken("minmai"), new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                // info.error中包含了错误信息，可打印调试
                // 上传成功后将key值上传到自己的服务器
                if (info.isOK()) {
                    dismissLoadingDialog();
                    debitCardUrl = key;
                    Log.d("asdasdsss", debitCardUrl);
                }
            }
        }, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
        }, getApplicationContext());
    }
}
