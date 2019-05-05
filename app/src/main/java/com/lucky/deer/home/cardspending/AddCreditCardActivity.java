package com.lucky.deer.home.cardspending;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.BankCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.demo.cloudstorage.executor.StartUpload;
import com.dykj.requestcore.util.RequestUtils;
import com.google.gson.Gson;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.recognition.FileUtil;
import com.lucky.deer.recognition.Package.executor.StartObtainInfo;
import com.lucky.deer.util.RegexUtil;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.WheelMenuDialog;
import com.lucky.deer.weight.enums.PublicEnum;
import com.lucky.model.common.fourelements.FourElementsEntity;
import com.lucky.model.request.certification.FourFactorAuthenticationReq;
import com.lucky.model.request.certification.SaveVerifyData;
import com.lucky.model.request.quickpay.AddCardReq;
import com.lucky.model.response.ResponseData;
import com.lucky.model.response.mine.QueryIdentityAuthEntity;
import com.lucky.model.response.perfectinformation.BankInfoVoEntity;
import com.lucky.model.util.GlideUtils;
import com.lucky.model.util.HawkUtil;
import com.lucky.model.util.HintUtil;
import com.lucky.model.util.LayoutStyleUtil;
import com.lucky.model.util.RxBus;
import com.qmuiteam.qmui.widget.QMUITopBar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加信用卡
 *
 * @author wangxiangyi
 * @date 2018/10/23
 */
public class AddCreditCardActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.rl_card_back_color)
    RelativeLayout rlCardBackColor;
    @BindView(R.id.iv_blurring_logo)
    ImageView ivBlurringLogo;
    @BindView(R.id.iv_bank_logo)
    ImageView ivBankLogo;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_bank_type)
    TextView tvBankType;
    @BindView(R.id.et_bank_nickname)
    TextView etBankNickname;
    @BindView(R.id.et_bank_numaer)
    EditText etBankNumaer;
    @BindView(R.id.et_date_month)
    EditText etDateMonth;
    @BindView(R.id.et_date_year)
    EditText etDateYear;
    @BindView(R.id.et_cvn)
    EditText etCvn;
    @BindView(R.id.et_reserve_phone_number)
    EditText etReservePhoneNumber;
    /**
     * 账单日
     */
    @BindView(R.id.tv_billing_day)
    TextView tvBillingDay;
    /**
     * 还款日
     */
    @BindView(R.id.tv_repayment_day)
    TextView tvRepaymentDay;
    /**
     * 提交按钮
     */
    @BindView(R.id.tv_add_card)
    TextView tvAddCard;
    private String mAbsolutePath;
    private String debitCardUrl;
    /**
     * 查询用户使命认证信息
     */
    private QueryIdentityAuthEntity queryIdentityAuth;

    @Override
    protected int initLayout() {
        return R.layout.activity_add_credit_card;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_add_credit_card);
        StringUtil.setHintSize(etBankNumaer, "请输入卡号");
        /*格式化银行卡号*/
        StringUtil.setBankCardNumAddSpace(etBankNumaer);
        /*格式化手机号*/
        StringUtil.setPhoneWatcher(etReservePhoneNumber);
        initAccessToken();
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
    }

    @Override
    protected void initListener() {
        /*失去焦点监听*/
        etBankNumaer.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (mAbsolutePath == null) {
                    getBankAbbreviation(null);
                }
            }
        });
        /*信用卡有效期(月份)输入监听*/
        etDateMonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && s.length() >= 2) {
                    if (Integer.parseInt(s.toString()) > 12) {
                        HintUtil.showErrorWithToast(mContext, R.string.toast_correct_effective_date);
                        StringUtil.clearData(etDateMonth);
                        return;
                    }
                    etDateYear.requestFocus();
                }
            }
        });
        /*信用卡有效期(年份)输入监听*/
        etDateYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && s.length() >= 2) {
                    etCvn.requestFocus();
                } else if (TextUtils.isEmpty(s)) {
                    etDateMonth.requestFocus();
                }
            }
        });
        /*信用卡cvn输入监听*/
        etCvn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s) && !TextUtils.isEmpty(etDateMonth.getText().toString().trim()) && etDateMonth.getText().toString().length() >= 2) {
                    etDateYear.requestFocus();
                }
            }
        });
    }

    @OnClick({R.id.iv_take_photo, R.id.rl_billing_day, R.id.rl_repayment_day, R.id.tv_add_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*银行卡拍照*/
            case R.id.iv_take_photo:
                mAbsolutePath = FileUtil.getSaveFile(mActivity).getAbsolutePath();
                Intent intent = new Intent(mActivity, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, mAbsolutePath);
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_BANK_CARD);
                startActivityForResult(intent, KeyConstant.CREDIT_CARD_POSITIVE);
                break;
            /*账单日*/
            case R.id.rl_billing_day:
                selectBillingDateOrRepaymentDate(1, tvBillingDay.getText().toString().trim());
                break;
            /*还款日*/
            case R.id.rl_repayment_day:
                selectBillingDateOrRepaymentDate(2, tvRepaymentDay.getText().toString().trim());
                break;
            /*添加按钮*/
            case R.id.tv_add_card:
                examineRequiredVerification();
                break;
            default:
        }
    }

    /**
     * 选择账单日或还款日
     *
     * @param mark 1:账单日
     *             2：还款日
     */
    public void selectBillingDateOrRepaymentDate(int mark, String selectedNumber) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            String s;
            if (i < 10) {
                s = "0" + i;
            } else {
                s = String.valueOf(i);
            }
            list.add(s);
        }
        WheelMenuDialog.getInstance()
                .level1WheelMenu(mActivity,
                        list,
                        selectedNumber,
                        "日",
                        (i, s) -> {
                            switch (mark) {
                                /*账单日*/
                                case 1:
                                    tvBillingDay.setText(s);
                                    StringUtil.clearData(tvRepaymentDay);
                                    break;
                                /*还款日*/
                                case 2:
                                    if (TextUtils.isEmpty(tvBillingDay.getText().toString().trim())) {
                                        HintUtil.showErrorWithToast(mContext, R.string.toast_please_choose_credit_card_billing_day);
                                    } else if (TextUtils.isEmpty(s)) {
                                        HintUtil.showErrorWithToast(mContext, R.string.toast_please_choose_credit_card_repayment_day);
                                        return;
                                    }
                                    /*账单日小于还款日时*/
                                    else if (StringUtil.setComparison(tvBillingDay.getText().toString().trim(), s) &&
                                            /*还款日减去账单日不能小于16天*/
                                            Integer.parseInt(StringUtil.getSubtract(s, tvBillingDay.getText().toString().trim())) < 15) {
                                        HintUtil.showErrorWithToast(mContext, "账单日与还款日间隔不能小于十六天");
                                        return;
                                    }
                                    tvRepaymentDay.setText(s);
                                    break;
                                default:
                            }
                        });
    }

    @Override
    protected boolean examineRequiredVerification() {
        if (TextUtils.isEmpty(tvBankName.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mActivity, R.string.toast_not_supported_this_bank);
            return false;
        } else if (TextUtils.isEmpty(StringUtil.removeAllSpace(etBankNumaer.getText().toString().trim()))) {
            HintUtil.showErrorWithToast(mActivity, R.string.toast_enter_credit_card_number);
            return false;
        } else if (TextUtils.isEmpty(etDateMonth.getText().toString().trim()) &&
                TextUtils.isEmpty(etDateYear.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mActivity, R.string.toast_correct_effective_date);
            return false;
        } else if (etDateMonth.getText().toString().trim().length() != 2 ||
                Integer.parseInt(etDateMonth.getText().toString().trim()) > 12) {
            HintUtil.showErrorWithToast(mActivity, R.string.toast_correct_effective_date);
            return false;
        } else if (etDateYear.getText().toString().trim().length() != 2) {
            HintUtil.showErrorWithToast(mActivity, R.string.toast_correct_effective_date);
            return false;
        } else if (TextUtils.isEmpty(etCvn.getText().toString().trim()) &&
                etCvn.getText().toString().trim().length() != 3) {
            HintUtil.showErrorWithToast(mActivity, R.string.toast_correct_effective_cvn_code);
            return false;
        } else if (!RegexUtil.isMobileExact(StringUtil.removeAllSpace(etReservePhoneNumber.getText().toString().trim()))) {
            HintUtil.showErrorWithToast(mActivity, R.string.toast_please_phone_number);
            return false;
        } else if (TextUtils.isEmpty(tvBillingDay.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_please_choose_credit_card_billing_day);
            return false;
        } else if (TextUtils.isEmpty(tvRepaymentDay.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_please_choose_credit_card_repayment_day);
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
//            fourFactorAuthenticationReq.setCardNo(StringUtil.removeAllSpace(etBankNumaer.getText().toString().trim()));
//            showLoadingDialog();
//            mNetworkRequestInstance.bankAuthenticate(fourFactorAuthenticationReq)
//                    .subscribe(returnData -> {
//                        if (RequestUtils.isFourElementsSuccess(returnData)) {
//                            setAddCard();
//                        } else {
//                            tvAddCard.setEnabled(true);
//                            dismissLoadingDialog();
//                            showFailedDialog(R.string.data_loading_massage1);
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
//            fourFactorAuthenticationReq.setBankcard(StringUtil.removeAllSpace(etBankNumaer.getText().toString().trim()));
//            showLoadingDialog();
//            mNetworkRequestInstance.jingdongBankAuthenticate(fourFactorAuthenticationReq)
//                    .subscribe(returnData -> {
//                        if (RequestUtils.isRequestSuccess(returnData)) {
//                            setAddCard();
//                        } else {
//                            tvAddCard.setEnabled(true);
//                            dismissLoadingDialog();
//                            showFailedDialog(R.string.data_loading_massage1);
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
            fourFactorAuthenticationReq.setBankcard(StringUtil.removeAllSpace(etBankNumaer.getText().toString().trim()));
            showLoadingDialog();
            mNetworkRequestInstance.elementsValidate(fourFactorAuthenticationReq)
                    .subscribe(returnData -> {
                        if (RequestUtils.isRequestSuccess(returnData)) {
                            setAddCard();
                        } else {
                            tvAddCard.setEnabled(true);
                            dismissLoadingDialog();
                            showFailedDialog(R.string.data_loading_massage1);
                        }
                    });
        }
    }

    /**
     * 添加信用卡接口
     */
    @SuppressLint("CheckResult")
    private void setAddCard() {
        AddCardReq addCardReq = new AddCardReq();
        /*银行名称*/
        addCardReq.setBranch_bank(tvBankName.getText().toString().trim());
        /*昵称*/
        addCardReq.setNickName(etBankNickname.getText().toString().trim());
        /*银行卡号*/
        addCardReq.setCarNumber(StringUtil.removeAllSpace(etBankNumaer.getText().toString().trim()));
        /*预留手机号*/
        addCardReq.setPhone(StringUtil.removeAllSpace(etReservePhoneNumber.getText().toString().trim()));
        /*cvn安全码*/
        addCardReq.setCvv(etCvn.getText().toString().trim());
        /*有效期 */
        addCardReq.setEffectiveDate(etDateMonth.getText().toString().trim() + etDateYear.getText().toString().trim());
        /*银行卡照片*/
        addCardReq.setPhoto(HttpConstant.QINIU_URL + debitCardUrl);
        /*账单日*/
        addCardReq.setStatementDate(tvBillingDay.getText().toString().trim());
        /*还款日*/
        addCardReq.setRepaymentDate(tvRepaymentDay.getText().toString().trim());
        showLoadingDialog();
        mNetworkRequestInstance.addCreditCard(addCardReq)
                .subscribe(stringResponseData -> {
                    dismissLoadingDialog();
                    tvAddCard.setEnabled(true);
                    if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        showSuccessDialog(R.string.dialog_add_credit_card_success);
                        RxBus.getInstance().post(KeyConstant.NOTICE_ADD_BANK_CARD);
                    } else {
                        HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                    }
                });
    }

    @Override
    public void carriedOutMethod() {
        finish();
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
        saveVerifyData.setCardNo(StringUtil.removeAllSpace(etBankNumaer.getText().toString().trim()));
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
        if (resultCode == Activity.RESULT_OK && requestCode == KeyConstant.CREDIT_CARD_POSITIVE) {
            bankCardIdentification(mAbsolutePath);
            //收到回掉后
            showSoftInputFromWindow(AddCreditCardActivity.this, etCvn);
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


    /**
     * 识别银行卡并获取信息
     *
     * @param absolutePath 银行卡图片路径
     */
    @SuppressLint("CheckResult")
    private void bankCardIdentification(String absolutePath) {
        showLoadingDialog();
        StartObtainInfo.getInstance()
                .getBankCardInfo(absolutePath)
                .subscribe(bankCardResult -> {
                    /*判断识别银行卡名称是否为空*/
                    if (RequestUtils.isRequestSuccess(bankCardResult)) {
                        etBankNumaer.setText(bankCardResult.getData().getBankCardNumber());
                        getBankAbbreviation(bankCardResult.getData());
                    } else {
                        mAbsolutePath = null;
                        debitCardUrl = "";
                        dismissLoadingDialog();
                        HintUtil.showErrorWithToast(mActivity, bankCardResult.getMsg());
                    }
                });
    }

    /**
     * 获取银行缩写名称
     */
    @SuppressLint("CheckResult")
    public void getBankAbbreviation(BankCardResult bankCardResult) {
        String bankNo = StringUtil.removeAllSpace(etBankNumaer.getText().toString().trim());
        if (TextUtils.isEmpty(bankNo)) {
            dismissLoadingDialog();
            HintUtil.showErrorWithToast(mActivity, getString(R.string.toast_enter_credit_card_number));
            return;
        }
        mNetworkRequestInstance.getBankAbbreviation(bankNo)
                .subscribe(stringResponseData -> {
                    /*验证银行卡号是否正确*/
                    if (stringResponseData.isValidated()) {
                        /*判断是否是信用卡*/
                        if (2 == PublicEnum.getEnumCardType(stringResponseData.getCardType())) {
                            tvAddCard.setEnabled(true);
                            /*获取总行信息*/
                            List<BankInfoVoEntity> bankInfo = HawkUtil.getInstance().getSaveData(KeyConstant.BANK_INFO);
                            if (bankInfo != null) {
                                for (BankInfoVoEntity bankInfoVoEntity : bankInfo) {
                                    if (stringResponseData.getBank().equals(bankInfoVoEntity.getBankShortName())) {
                                        /*根据卡号获取背景颜色*/
                                        getBankBackgroundVo();
                                        if (!TextUtils.isEmpty(mAbsolutePath)) {
                                            /*把拍照图片上传到七牛云*/
                                            uploadImage(mAbsolutePath);
                                        }
                                        tvBankType.setText(PublicEnum.getEnumTitleName(stringResponseData.getCardType()));
                                        /*判断返回json*/
                                        if (bankCardResult != null && !TextUtils.isEmpty(bankCardResult.getJsonRes())) {
                                            JSONObject jsonObject = new JSONObject(bankCardResult.getJsonRes());
                                            if (jsonObject.getJSONObject("result") != null &&
                                                    !TextUtils.isEmpty(jsonObject.getJSONObject("result").getString("valid_date")) &&
                                                    jsonObject.getJSONObject("result").getString("valid_date").contains("/")) {
                                                JSONObject result = jsonObject.getJSONObject("result");
                                                String validDate = String.valueOf(result.getString("valid_date"));
                                                etDateMonth.setText(validDate.split("/")[0]);
                                                etDateYear.setText(validDate.split("/")[1]);
                                            }
                                        }
                                        if (!TextUtils.isEmpty(StringUtil.removeAllSpace(etBankNumaer.getText().toString().trim()))) {
                                            HintUtil.showSoftInputFromInputMethod(mActivity, etDateMonth);
                                        }
                                        if (!TextUtils.isEmpty(etDateMonth.getText().toString().trim())) {
                                            HintUtil.showSoftInputFromInputMethod(mActivity, etDateYear);
                                        }
                                        if (!TextUtils.isEmpty(etDateYear.getText().toString().trim())) {
                                            HintUtil.showSoftInputFromInputMethod(mActivity, etCvn);
                                        }
                                        break;
                                    }
                                }
                                dismissLoadingDialog();
                            } else {
                                dismissLoadingDialog();
                                mAbsolutePath = null;
                            }
                        } else {
                            tvAddCard.setEnabled(false);
                            dismissLoadingDialog();
                            StringUtil.clearData(tvBankName, tvBankType, etDateMonth, etDateYear, etCvn);
                            rlCardBackColor.setBackground(LayoutStyleUtil.setShapeDrawable(getResources().getColor(R.color.color_default_bank)));
                            ivBankLogo.setImageDrawable(null);
                            ivBlurringLogo.setImageDrawable(null);
                            HintUtil.showErrorWithToast(mActivity, R.string.toast_current_not_credit_card_number);
                            mAbsolutePath = null;
                        }
                    } else {
                        tvAddCard.setEnabled(false);
                        dismissLoadingDialog();
                        StringUtil.clearData(tvBankName, tvBankType, etDateMonth, etDateYear, etCvn);
                        rlCardBackColor.setBackground(LayoutStyleUtil.setShapeDrawable(getResources().getColor(R.color.color_default_bank)));
                        ivBankLogo.setImageDrawable(null);
                        ivBlurringLogo.setImageDrawable(null);
                        HintUtil.showErrorWithToast(mActivity, R.string.toast_please_enter_correct_bank_card_number);
                        mAbsolutePath = null;
                    }
                });
    }

    /**
     * 根据银行卡号获取背景样式
     */
    @SuppressLint("CheckResult")
    public void getBankBackgroundVo() {
        AddCardReq addCardReq = new AddCardReq();
        addCardReq.setBankCarNum(StringUtil.removeAllSpace(etBankNumaer.getText().toString().trim()));
        mNetworkRequestInstance.getBankBackgroundVo(addCardReq)
                .subscribe(stringResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        if (!TextUtils.isEmpty(stringResponseData.getData().getBackgroundColor()) &&
                                stringResponseData.getData().getBackgroundColor().startsWith("#")) {
                            rlCardBackColor.setBackground(LayoutStyleUtil.setShapeDrawable(stringResponseData.getData().getBackgroundColor()));
                        }
                        GlideUtils.loadImage(mContext, ivBlurringLogo, stringResponseData.getData().getBankBackground());
                        GlideUtils.loadImage(mContext, ivBankLogo, stringResponseData.getData().getLogo());
                        if (TextUtils.isEmpty(tvBankName.getText().toString())) {
                            tvBankName.setText(stringResponseData.getData().getBankName());
                        }
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
