package com.lucky.deer.mine.perfectuserInfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.home.MainActivity;
import com.lucky.deer.util.FileUtils;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.model.request.userinfo.PerfectUserInfoReq;
import com.lucky.model.response.userinfo.UserInfo;
import com.lucky.model.util.HawkUtil;
import com.lucky.model.util.HintUtil;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 身份证识别
 */
public class AuthenticationActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    /**
     * 正面照片
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
    EditText tvIdentityNumber;
    /**
     * 反面照片
     */
    @BindView(R.id.iv_reverse_photo)
    ImageView ivReversePhoto;
    /**
     * 有效期
     */
    @BindView(R.id.tv_valid_until)
    EditText tvValidUntil;
    /**
     * 下一步
     */
    @BindView(R.id.tv_authentication_next)
    TextView tvAuthenticationNext;

    /**
     * 身份证类型<p>
     * 0：正面<p>
     * 1：反面
     */
    private int idType;
    /**
     * 正面地址
     */
    private String POSITIVE;
    /**
     * 反面地址
     */
    private String NEGATIVE;
    /**
     * ethnic：民族<p>
     * address：地址
     */
    private String ethnic, address;


    private static final String TAG = "AuthenticationActivity";
    /**
     * 照相机扫描的请求码
     */
    private static final int REQUEST_CODE_CAMERA = 102;
    private PublicDialog inistanceView;
    private QMUIDialog qmuiDialog;

    @Override
    protected int initLayout() {
        return R.layout.activity_id_card;
    }

    @Override
    protected void initView() {
        initAccessToken();
        initTopBar();
        /*获取单利*/
        inistanceView = PublicDialog.getInstance();
    }

    private void initTopBar() {
        topBar.setTitle("完善信息");
        topBar.addRightTextButton("稍后完善", R.id.btn_right)
                .setOnClickListener(v -> {
                    Intent intent = new Intent(AuthenticationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });
        topBar.addLeftImageButton(R.mipmap.back, R.id.btn_left)
                .setOnClickListener(v -> {
                    //                Intent intent = new Intent(AuthenticationActivity.this, MainActivity.class);
                    //                startActivity(intent);
                    finish();
                });
    }

    @Override
    protected void initListener() {
        inistanceView.setOnClickListener((OnClickListener<String>) (status,useType, isPhoneNumber, text) -> {
            if (qmuiDialog != null) {
                switch (status) {
                    case OK:
                        startRequestInterface();
                        break;
                    default:
                }
                qmuiDialog.dismiss();
            }
        });
    }

    @OnClick({R.id.iv_id_card, R.id.iv_reverse_photo, R.id.tv_authentication_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_id_card:
                idType = 0;
                scanFrontWithNativeQuality();
                break;
            case R.id.iv_reverse_photo:
                idType = 1;
                scanBackWithNativeQuality();
                break;
            case R.id.tv_authentication_next:
                //点击提交资料
//                startRequestInterface();
                examineRequiredVerification();
                break;
            case R.id.tv_identity_number:
                showSoftInputFromWindow(AuthenticationActivity.this, tvIdentityNumber);
                break;
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


    // 调用拍摄身份证正面（带本地质量控制）activity
    private void scanFrontWithNativeQuality() {
        Intent intent = new Intent(AuthenticationActivity.this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtils.getFrontSaveFile(getApplication()).getAbsolutePath());
        //使用本地质量控制能力需要授权
       // intent.putExtra(CameraActivity.KEY_NATIVE_TOKEN, OCR.getInstance().getLicense());
        //设置本地质量使用开启
        //intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE, true);
        //设置扫描的身份证的类型（正面front还是反面back）
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    //调用拍摄身份证反面（带本地质量控制）activity
    private void scanBackWithNativeQuality() {
        Intent intent = new Intent(AuthenticationActivity.this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtils.getBackSaveFile(getApplication()).getAbsolutePath());

        /*intent.putExtra(CameraActivity.KEY_NATIVE_TOKEN,
                OCR.getInstance().getLicense());*/
       /* intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                true);*/
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * 识别身份证
     *
     * @param idCardSide 正面（front）还是反面（back）
     * @param filePath   文件路径
     */
    private void recIDCard(String idCardSide, String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(5);
        // 调用身份证识别服务
        OCR.getInstance().recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                // 调用成功，返回IDCardResult对象
                if (result != null) {
                    if (idType == 0) {
                        //正面
                        if (result.getAddress() == null || result.getName() == null || result.getEthnic() == null || result.getIdNumber() == null) {
                            Toast.makeText(mContext, "请重新进行身份证识别", Toast.LENGTH_LONG).show();
                        } else {
                            String addresses = result.getAddress().toString(); //居住地址
                            String name = result.getName().toString(); //姓名
                            //String gender = result.getGender().toString(); //性别
                            String ethnics = result.getEthnic().toString(); //民族
                            //String birthday = result.getBirthday().toString(); //出生日期
                            String idNumber = result.getIdNumber().toString(); //身份证号码
                            etName.setText(name);
                            tvIdentityNumber.setText(idNumber);
                            ethnic = ethnics;
                            address = addresses;
                        }
                    } else {
                        if (result.getSignDate() == null || result.getExpiryDate() == null) {
                            Toast.makeText(AuthenticationActivity.this, "请重新拍摄身份证背面", Toast.LENGTH_LONG).show();
                        } else {
                            //反面
                            String signDate = result.getSignDate().toString(); //签发日期
                            String expiryDate = result.getExpiryDate().toString(); //截止日期
                            //String issueAuthority = result.getIssueAuthority().toString();//签发机关
                            if ("".equals(signDate) || signDate == null || "".equals(expiryDate) || expiryDate == null || "-".equals(tvValidUntil.getText().toString().trim())) {
                                Toast.makeText(AuthenticationActivity.this, "请重新拍摄身份证背面", Toast.LENGTH_LONG).show();
                                tvValidUntil.setText("");
                            } else {
                                tvValidUntil.setText(signDate + "-" + expiryDate);
                            }
                        }


                    }
                }
            }

            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError对象
                Log.i(TAG, "onError: " + error.getMessage());
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果拍摄类型是身份证
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                if (!TextUtils.isEmpty(contentType)) {
                    //判断是身份证正面还是反面
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        String filePath = FileUtils.getFrontSaveFile(getApplication()).getAbsolutePath();
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                        Bitmap bitmap = StringUtil.decodeStream(filePath);
                        ivIdCard.setImageBitmap(bitmap);
                        uploadImg2QiNiu(filePath, "1");
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        String filePath = FileUtils.getBackSaveFile(getApplication()).getAbsolutePath();
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                        Bitmap bitmap = StringUtil.decodeStream(filePath);
                        ivReversePhoto.setImageBitmap(bitmap);
                        uploadImg2QiNiu(filePath, "2");
                    }
                }
            }
        }
    }

    //授权文件（安全模式）
    //此种身份验证方案使用授权文件获得AccessToken，缓存在本地。建议有安全考虑的开发者使用此种身份验证方式。
    private void initAccessToken() {
        OCR.getInstance().initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                // 调用成功，返回AccessToken对象
                String token = accessToken.getAccessToken();
                Log.i(TAG, "token:-------->" + token);
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                Log.i(TAG, "onError:licence方式获取token失败---->" + error.getMessage());
            }
        }, getApplicationContext());
    }


    /**
     * 七牛云上传工具类
     */
    private void uploadImg2QiNiu(String filePath, final String types) {
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

        uploadManager.put(picPath, key, Auth.create(HttpConstant.QINIU_AK, HttpConstant.QIUNIU_SK).uploadToken(HttpConstant.BUCKET), new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                // info.error中包含了错误信息，可打印调试
                // 上传成功后将key值上传到自己的服务器
                if (info.isOK()) {
                    if ("1".equals(types)) {
                        POSITIVE = HttpConstant.QINIU_URL + key;
                        //ImageLoadUtils.loadImage(AuthenticationActivity.this, ivIdCard, POSITIVE);
                    } else if ("2".equals(types)) {
                        NEGATIVE = HttpConstant.QINIU_URL + key;
                        //ImageLoadUtils.loadImage(AuthenticationActivity.this, ivReversePhoto, NEGATIVE);
                    }
                }
            }
        }, null);
    }


    //点击下一步的验证
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
        /*确认信息弹窗*/
        qmuiDialog = inistanceView.setCustomizeView(
                inistanceView.initTitleEtOrTvView(
                        mContext, getString(R.string.dialog_confirm_info_title),
                        getString(R.string.name) + etName.getText().toString().trim() + "\n\n" +
                                getString(R.string.identity_number) + tvIdentityNumber.getText().toString().trim(),
                        true),
                KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_4);
        return false;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
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
        perfectUserInfoReq.setCardBackPic(NEGATIVE);
        /*身份证反面*/
        perfectUserInfoReq.setCardFrontPic(POSITIVE);
        showLoadingDialog();
        mNetworkRequestInstance.userRealNameAuthenticationOne(perfectUserInfoReq)
                .subscribe(stringBeanResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(stringBeanResponseData)) {
                        UserInfo userInfo = HawkUtil.getInstance().getSaveData(HawkUtil.USER_INFO);
                        if (userInfo != null) {
                            userInfo.setRegisterState(2);
                            HawkUtil.getInstance().saveData(HawkUtil.USER_INFO, userInfo);
                            jumpActivity(mContext, HeldIdentityActivity.class, true);
//                            Intent intent = new Intent(AuthenticationActivity.this, HeldIdentityActivity.class);
//                            startActivity(intent);
//                            finish();
                        }
                    } else {
                        HintUtil.showErrorWithToast(mActivity, stringBeanResponseData.getMsg());
                    }
                });
    }
}
