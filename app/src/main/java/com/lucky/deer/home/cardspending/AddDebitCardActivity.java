package com.lucky.deer.home.cardspending;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
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
import com.lucky.model.response.perfectinformation.AccountOpeningAreaEntity;
import com.lucky.model.response.perfectinformation.BankInfoVoEntity;
import com.lucky.model.response.selectbankcard.SelectBankCardList;
import com.lucky.model.util.DisplayUtils;
import com.lucky.model.util.HawkUtil;
import com.lucky.model.util.HintUtil;
import com.lucky.model.util.RxBus;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jeesoft.widget.pickerview.CharacterPickerView;

/**
 * 添加储蓄卡
 *
 * @author wangxiangyi
 * @date 2018/10/23
 */
public class AddDebitCardActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.iv_cash_card)
    ImageView ivCashCard;
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
    /**
     * 提交按钮
     */
    @BindView(R.id.tv_add_card)
    TextView tvAddCard;
    /**
     * 扫描照面路径
     */
    private String absolutePath;
    /**
     * 图片链接
     */
    private String debitCardUrl;

    /**
     * 总行id
     */
    private String bankInfoId;

    private View accountOpeningArea, selectBank;
    private CharacterPickerView pickerView, selectBankpickerView;
    private QMUIBottomSheet bottomSelectBank, bottomAccountOpeningArea;
    /**
     * 查询用户使命认证信息
     */
    private QueryIdentityAuthEntity queryIdentityAuth;
    /**
     * 借记卡信息
     */
    private SelectBankCardList mDebitCardInfo;
    private List<AccountOpeningAreaEntity> regionalData;

    @Override
    protected int initLayout() {
        return R.layout.activity_add_debit_card;
    }

    @Override
    protected void initView() {
        if (getSerializableData() != null) {
            mDebitCardInfo = (SelectBankCardList) getSerializableData();
            initTopBar(topBar, KeyConstant.MODIFY_DEBIT_CARD.equals(mDebitCardInfo.getDebitCardType()) ? R.string.title_activity_modify_debit_card : R.string.title_activity_add_debit_card);
            if (KeyConstant.MODIFY_DEBIT_CARD.equals(mDebitCardInfo.getDebitCardType())) {
                tvAddCard.setText(R.string.determine_modify);
            } else {
                tvAddCard.setText(R.string.add);
            }
        } else {
            initTopBar(topBar, R.string.title_activity_add_debit_card);
            tvAddCard.setText(R.string.add);
        }
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
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dp2px(mContext, 250));
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        pickerView = new CharacterPickerView(mActivity);
        pickerView.setMaxTextSize(13);
        llView.addView(pickerView, layoutParams);
        selectBankpickerView = new CharacterPickerView(mActivity);
        selectBankpickerView.setMaxTextSize(13);
        llSelectBankView.addView(selectBankpickerView, layoutParams);
        initAccessToken();
        /*格式化银行卡号*/
        StringUtil.setBankCardNumAddSpace(etBankCardNumber);
        /*格式化手机号*/
        StringUtil.setPhoneWatcher(etReservePhoneNumber);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
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
        areaList();
    }

    @OnClick({R.id.iv_cash_card, R.id.ll_select_account_opening_area, R.id.ll_select_bank, R.id.tv_add_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_cash_card:
                absolutePath = FileUtil.getBankFile(mActivity).getAbsolutePath();
                Intent intent = new Intent(mActivity, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, absolutePath);
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_BANK_CARD);
                startActivityForResult(intent, KeyConstant.DEBIT_CARD_POSITIVE);
                break;
            /*开户地区*/
            case R.id.ll_select_account_opening_area:
                if (TextUtils.isEmpty(StringUtil.removeAllSpace(etBankCardNumber.getText().toString().trim()))) {
                    HintUtil.showErrorWithToast(mActivity, getString(R.string.hint_bank_card_number));
                    StringUtil.clearData(etBankCardNumber);
                    return;
                }
                showLoadingDialog();
                getBankAbbreviation();
                bottomAccountOpeningArea = PublicDialog.getInstance().addHeaderView(mContext, accountOpeningArea);
//                bottomAccountOpeningArea.setOnDismissListener(dialog -> {
//                    tvSelectAccountOpeningArea.setText(OptionsWindowHelper.getNameAll());
//                    tvSelectAccountOpeningArea.setTag(OptionsWindowHelper.getCode());
//                });
                break;
            case R.id.ll_select_bank:
                if (TextUtils.isEmpty(bankInfoId)) {
                    HintUtil.showErrorWithToast(mActivity, getString(R.string.toast_bank_not_supported));
                    return;
                }
                bankInfo(bankInfoId);
                break;
            case R.id.tv_add_card:
                examineRequiredVerification();
                break;
            default:
        }
    }

    @Override
    protected boolean examineRequiredVerification() {
        /*if (TextUtils.isEmpty(tvBankCardName.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mActivity, R.string.toast_not_supported_this_bank);
            return false;
        } else*/
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
        tvAddCard.setEnabled(false);
        if (queryIdentityAuth == null) {
            initData();
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
//                            if (mDebitCardInfo != null) {
//                                setModifyCard();
//                            } else {
//                                setAddCard();
//                            }
//                        } else {
//                            tvAddCard.setEnabled(true);
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
//                            if (mDebitCardInfo != null) {
//                                setModifyCard();
//                            } else {
//                                setAddCard();
//                            }
//                        } else {
//                            tvAddCard.setEnabled(true);
//                            dismissLoadingDialog();
//                            showFailedDialog(R.string.data_loading_massage2);
//                        }
//                        setStatisticsFourElement(returnData);
//                    });
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
                            if (mDebitCardInfo != null) {
                                setModifyCard();
                            } else {
                                setAddCard();
                            }
                        } else {
                            tvAddCard.setEnabled(true);
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
        perfectUserInfoReq.setIsDefault("0");
        showLoadingDialog();
        mNetworkRequestInstance.addUserBankCard(perfectUserInfoReq)
                .subscribe(stringResponseData -> {
                    dismissLoadingDialog();
                    tvAddCard.setEnabled(true);
                    if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        RxBus.getInstance().post(KeyConstant.NOTICE_ADD_BANK_CARD);
                        finish();
                    } else {
                        HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                    }
                });
    }

    /**
     * 修改储蓄卡接口
     */
    @SuppressLint("CheckResult")
    private void setModifyCard() {
        PerfectUserInfoReq perfectUserInfoReq = new PerfectUserInfoReq();
        /*修改的储蓄卡ID*/
        perfectUserInfoReq.setOldDebitCardId(mDebitCardInfo.getDebitCardId());
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
        mNetworkRequestInstance.updateUserBankCard(perfectUserInfoReq)
                .subscribe(stringResponseData -> {
                    dismissLoadingDialog();
                    tvAddCard.setEnabled(true);
                    if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        RxBus.getInstance().post(KeyConstant.NOTICE_ADD_BANK_CARD);
                        finish();
                    } else {
                        HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
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
        mNetworkRequestInstance.saveVerifyData(saveVerifyData)
                .subscribe(t -> {
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == KeyConstant.DEBIT_CARD_POSITIVE) {
            bankCardIdentification(absolutePath);
            /*清空银行信息id*/
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
                        etBankCardNumber.setText(bankCardResult.getData().getBankCardNumber());
                        getBankAbbreviation();
                    } else {
                        dismissLoadingDialog();
                        HintUtil.showErrorWithToast(mActivity, bankCardResult.getMsg());
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
                        regionalData = listResponseData.getData();
                        //初始化选项数据
                        OptionsWindowHelper.setThreeLevelLinkagePickerData(pickerView, regionalData);
                    } else {
                        HintUtil.showErrorWithToast(mActivity, listResponseData.getMsg());
                    }
                });
    }

    /**
     * 获取银行缩写名称
     */
    @SuppressLint("CheckResult")
    public void getBankAbbreviation() {
        if (TextUtils.isEmpty(StringUtil.removeAllSpace(etBankCardNumber.getText().toString().trim()))) {
            dismissLoadingDialog();
            HintUtil.showErrorWithToast(mActivity, getString(R.string.hint_bank_card_number));
            StringUtil.clearData(tvSelectAccountOpeningArea);
            return;
        }
        mNetworkRequestInstance.getBankAbbreviation(StringUtil.removeAllSpace(etBankCardNumber.getText().toString().trim()))
                .subscribe(stringResponseData -> {
                    /*验证银行卡号是否正确*/
                    if (stringResponseData.isValidated()) {
                        /*判断时候是储蓄卡*/
                        if (1 == PublicEnum.getEnumCardType(stringResponseData.getCardType())) {
                            /*判断是否支持此银行*/
                            if (SupportBankEnum.isSupportTheBank(stringResponseData.getBank())) {
                                /*获取总行信息*/
                                List<BankInfoVoEntity> bankInfo = HawkUtil.getInstance().getSaveData(KeyConstant.BANK_INFO);
                                if (bankInfo != null) {
                                    for (BankInfoVoEntity bankInfoVoEntity : bankInfo) {
                                        if (stringResponseData.getBank().equals(bankInfoVoEntity.getBankShortName())) {
                                            if (!TextUtils.isEmpty(absolutePath)) {
                                                /*上传到七牛云*/
                                                uploadImage(absolutePath);
                                                ivCashCard.setImageBitmap(StringUtil.decodeStream(absolutePath));
                                            } else {
                                                dismissLoadingDialog();
                                            }
                                            bankInfoId = bankInfoVoEntity.getId();
                                            tvBankCardName.setText(bankInfoVoEntity.getBankName());
                                            if (bottomAccountOpeningArea != null && regionalData != null) {
                                                bottomAccountOpeningArea.show();
                                            } else {
                                                areaList();
                                            }
                                            break;
                                        } else {
                                            bankInfoId = "";
                                        }
                                    }
                                    if (TextUtils.isEmpty(bankInfoId)) {
                                        dismissLoadingDialog();
                                        HintUtil.showErrorWithToast(mActivity, getString(R.string.toast_bank_not_supported));
                                    }
                                } else {
                                    /*获取总行信息*/
                                    mNetworkRequestInstance.getBankInfoVo()
                                            .subscribe(listResponseData -> {
                                                dismissLoadingDialog();
                                                if (RequestUtils.isRequestSuccess(listResponseData)) {
                                                    HawkUtil.getInstance().saveData(KeyConstant.BANK_INFO, listResponseData.getData());
                                                } else {
                                                    HintUtil.showErrorWithToast(mContext, listResponseData.getMsg());
                                                }
                                            });
                                }
                            } else {
                                PublicDialog.getInstance().setMessageDialog(mContext, getString(R.string.dialog_prompt), R.string.supported_savings_card, getString(R.string.cancel));
                                dismissLoadingDialog();
                                ivCashCard.setImageResource(R.mipmap.bank_card);
                                bankInfoId = "";
                            }
                        } else {
                            dismissLoadingDialog();
                            StringUtil.clearData(tvBankCardName, etBankCardNumber, tvSelectAccountOpeningArea, tvSelectBank, etReservePhoneNumber);
                            HintUtil.showErrorWithToast(mActivity, R.string.toast_please_take_savings_card);
                        }
                    } else {
                        /*清空银行信息id*/
                        bankInfoId = "";
                        dismissLoadingDialog();
                        HintUtil.showErrorWithToast(mActivity, getString(R.string.hint_bank_card_number));
                        StringUtil.clearData(tvSelectAccountOpeningArea);
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
                            bottomSelectBank = PublicDialog.getInstance().addHeaderView2(mContext, selectBank);
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
