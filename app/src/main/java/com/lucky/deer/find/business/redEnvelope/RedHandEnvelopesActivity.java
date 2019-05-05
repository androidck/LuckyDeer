package com.lucky.deer.find.business.redEnvelope;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.demo.cloudstorage.executor.StartUpload;
import com.dykj.requestcore.util.RequestUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.common.dialog.CollorDialog;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.find.business.adapter.ItemTouchHelperCallback;
import com.lucky.deer.find.business.adapter.RedAddPhotoAdapter;
import com.lucky.deer.home.cardLife.PayResult;
import com.lucky.deer.recognition.FileUtil;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.model.common.SearchForListInfoEntity;
import com.lucky.model.request.find.business.redEnvelope.RedHandEnvelopesReq;
import com.lucky.model.request.home.alipay.AlipayResponse;
import com.lucky.model.request.home.cardLife.PurchaseMembershipLevelReq;
import com.lucky.model.response.find.redEnvelope.RedEnvelopeCollectionRangeBean;
import com.lucky.model.response.find.redEnvelope.RedEnvelopeCollectionRangeEntity;
import com.lucky.model.util.HintUtil;
import com.lucky.model.util.ImgUtils;
import com.lucky.model.util.RxBus;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.lucky.deer.weight.interfaces.OnItemClickListener.ClickStatus.OK;

/**
 * 发红包页面
 *
 * @author wangxiangyi
 * @date 2019/03/26
 */

public class RedHandEnvelopesActivity extends BaseActivity {
    /**
     * 标题
     */
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    /**
     * 文章内容
     */
    @BindView(R.id.ed_content)
    EditText edContent;
    /**
     * 列表信息
     */
    @BindView(R.id.rv_list)
    SwipeRecyclerView rvList;
    /**
     * 红包个数
     */
    @BindView(R.id.ed_count)
    EditText edCount;
    /**
     * 红包金额
     */
    @BindView(R.id.ed_money)
    EditText edMoney;
    /**
     * 位置信息
     */
    @BindView(R.id.tv_current_position)
    TextView tvCurrentPosition;
    /**
     * 位置/范围
     */
    @BindView(R.id.tv_range)
    TextView tvRange;
    /**
     * 精准推送
     */
    @BindView(R.id.tv_push)
    TextView tvPush;
    /**
     * 平台收取手续信息
     */
    @BindView(R.id.tv_tip)
    TextView tvTip;

    List<Integer> list = new ArrayList<>();

    private RedAddPhotoAdapter adapter;
    ImageView itemImg;

    private QMUIBottomSheet qmuiBottomSheet;
    private PublicDialog instance;
    private String absolutePath;
    private String imgPath;

    List<String> imgUrl = new ArrayList<>();
    /**
     * 字典值
     */
    private RedEnvelopeCollectionRangeEntity mData;
    /**
     * 选择范围信息
     */
    private SearchForListInfoEntity mRangeInfo;
    /**
     * 选择范围
     */
    private RedEnvelopeCollectionRangeBean mItemData;


    //拍照
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;
    @Override
    protected int initLayout() {
        return R.layout.activity_hand_envelopes;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_red_envelopes);
        /*初始化输入两位小数*/
        StringUtil.restrictionLength(2, edMoney);
        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        View footerView = getLayoutInflater().inflate(R.layout.item_red_add_head, rvList, false);
        itemImg = footerView.findViewById(R.id.item_img);
        itemImg.setOnClickListener(v -> platePhoto());
        rvList.addFooterView(footerView);

        adapter = new RedAddPhotoAdapter(mContext, imgUrl);
        ItemTouchHelperCallback helperCallback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(helperCallback);
        itemTouchHelper.attachToRecyclerView(rvList);
        rvList.setAdapter(adapter);
        adapter.setOnItemDelListener(position -> {
            imgUrl.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyDataSetChanged();
            if (imgUrl.size() < 4) {
                itemImg.setVisibility(View.VISIBLE);
            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        /*获取单利*/
        instance = PublicDialog.getInstance();
        showLoadingDialog();
        /*获取限制领取红包广告的字典 */
        mNetworkRequestInstance.getRedEnvelopeAvertisingConfigureDict()
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        mData = returnData.getData();
                    } else {
                        finish();
                    }
                });
        addPhoto();
    }

    @OnClick({/*R.id.btn_current_position,*/ R.id.btn_range, R.id.btn_precise_push, R.id.btn_plug_money})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            /*当前位置*/
//            case R.id.btn_current_position:
//                break;
            /*位置/范围*/
            case R.id.btn_range:
                if (mData == null) {
                    jumpActivityForResult(mActivity, LocationManagementActivity.class, KeyConstant.POSITIONING_SEARCH_KEY);
                } else {
                    jumpActivityForResult(mActivity, LocationManagementActivity.class, KeyConstant.POSITIONING_SEARCH_KEY, mData);
                }
                break;
            /*精准推送*/
            case R.id.btn_precise_push:
                if (mData != null) {
                    new CollorDialog(mContext, true, mData.getPreciseConditions(), data -> {
                        mItemData = data;
                        tvPush.setText(data.getDictValue());
                    }).show();
                }
                break;
            /*提交按钮*/
            case R.id.btn_plug_money:
                examineRequiredVerification();
                break;
            default:
        }
    }


    //添加照片
    public void addPhoto() {
        adapter.notifyDataSetChanged();
    }

    public void platePhoto() {
        String[] getdata = getdata(R.array.list_select_or_view_avatar);
        /*初始化视图*/
        qmuiBottomSheet = instance.addHeaderView(instance.publicListView(mContext, getdata));
        qmuiBottomSheet.show();
    }

    @Override
    protected void initListener() {
        /*初始化选择监听*/
        instance.setOnItemClickListener((status, position) -> {
            qmuiBottomSheet.dismiss();
            if (status == OK) {
                switch (position) {
                    /*拍照*/
                    case 0:
                        absolutePath = FileUtil.getSaveFile(getApplication()).getAbsolutePath();
                        Intent intent = new Intent(mContext, CameraActivity.class);
                        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, absolutePath);
                        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                                CameraActivity.CONTENT_TYPE_GENERAL);
                        startActivityForResult(intent, KeyConstant.SHOOTING_AVATAR);
                        break;
                    /*相册选择*/
                    case 1:
                        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, CODE_GALLERY_REQUEST);
                        break;
                    default:
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*判断是否返回不成功*/
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                /*拍照图片*/
                case KeyConstant.SHOOTING_AVATAR:
                    uploadImage(absolutePath);
                    break;
                /*相册选择头像*/
                case CODE_GALLERY_REQUEST:
                    uploadImage(ImgUtils.getInstance().getImgPath(mContext, data));
                    break;
                /*定位页面参数*/
                case KeyConstant.POSITIONING_SEARCH_KEY:
                    if (data != null) {
                        mRangeInfo = (SearchForListInfoEntity) data.getSerializableExtra(mEntity);
                        if (mRangeInfo != null) {
                            tvCurrentPosition.setText(mRangeInfo.getTitle());
                            if (mRangeInfo.getSelectRangeInfo() != null) {
                                tvRange.setText(mRangeInfo.getSelectRangeInfo().getDictValue());
                            }
                        }
                    }
                    break;
                default:
            }
        }
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
                    if (TextUtils.isEmpty(baseReq.getKey())) {
                        dismissLoadingDialog();
                        HintUtil.showErrorWithToast(mContext, "图片上传失败！");
                    } else {
                        dismissLoadingDialog();
                        if (list.size() != 4) {
                            addPhoto();
                        }
                        imgPath = HttpConstant.QINIU_URL + baseReq.getKey();
                        if (imgPath != null) {
                            imgUrl.add(imgPath);
                        }
                        Log.d("addImgUrl", imgUrl.size() + "");
                        if (imgUrl.size() >= 4) {
                            itemImg.setVisibility(View.GONE);
                        }
                        adapter.setImgUrl(imgUrl);
                    }
                });
    }

    @Override
    protected boolean examineRequiredVerification() {
        if (TextUtils.isEmpty(edContent.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_please_enter_article_content);
            return false;
        } else if (TextUtils.isEmpty(edCount.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_please_enter_number_red_packets);
            return false;
        } else if (TextUtils.isEmpty(edMoney.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_please_enter_red_envelope_amount);
            return false;
        } else if (mRangeInfo != null && mRangeInfo.getSelectRangeInfo() != null &&
                StringUtil.setComparison1(edMoney.getText().toString().trim(), mRangeInfo.getSelectRangeInfo().getAddedValue()) == -1) {
            HintUtil.showErrorWithToast(mContext, String.format(getString(R.string.toast_please_enter_red_envelope_amount_notLess_than), mRangeInfo.getSelectRangeInfo().getAddedValue()));
            return false;
        } else if (TextUtils.isEmpty(tvRange.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_please_choose_red_envelopes_range);
            return false;
        } else if (TextUtils.isEmpty(tvPush.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_please_choose_precise_push);
            return false;
        }
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        RedHandEnvelopesReq requestParameter = new RedHandEnvelopesReq();
        /*文章内容*/
        requestParameter.setContext(edContent.getText().toString().trim());
        /*发送类型*/
        requestParameter.setType("1");
        if (imgUrl != null && imgUrl.size() > 0) {
            List list = new ArrayList();
            for (String url : imgUrl) {
                Map<String, String> map = new HashMap<>();
                map.put("enclosure", url);
                map.put("type", "1");
                list.add(map);
            }
            /*图片*/
            requestParameter.setEnclosures(new Gson().toJson(list));
        }
        /*红包个数*/
        requestParameter.setRedEnvelopesNumber(edCount.getText().toString().trim());
        /*红包钱数*/
        requestParameter.setRechargeAmount(edMoney.getText().toString().trim());
        /*当前位置名称*/
        requestParameter.setLocationName(tvCurrentPosition.getText().toString().trim());
        /*范围*/
        requestParameter.setScopeRequirement((mRangeInfo != null && mRangeInfo.getSelectRangeInfo() != null ? mRangeInfo.getSelectRangeInfo().getDictKey() : ""));
        /*精准推送*/
        requestParameter.setPreciseCondition(mItemData != null ? mItemData.getDictKey() : "");
        /*设置经度*/
        requestParameter.setLongitude(mRangeInfo.getLongitude());
        /*设置纬度*/
        requestParameter.setLatitude(mRangeInfo.getLatitude());
        /*设置城市编码*/
        requestParameter.setCityCode(mRangeInfo.getAdCode());
        showLoadingDialog();
        mNetworkRequestInstance.sendRedEnvelope(requestParameter)
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        getPaymentFunction(returnData.getData());
                    } else {
                        dismissLoadingDialog();
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }

    /**
     * 获取支付信息
     *
     * @param id 账单id
     */
    @SuppressLint("CheckResult")
    private void getPaymentFunction(String id) {
        PurchaseMembershipLevelReq data = new PurchaseMembershipLevelReq();
        /*交易描述*/
        data.setBody("购买会员等级");
        /*商品的标题*/
        data.setSubject("会员升级");
        /*绝对超时时间*/
        data.setTimeExpire("30");
        /*商品类型*/
        data.setGoodsType("0");
        /*订单总金额*/
        data.setTotalAmount(edMoney.getText().toString().trim());
        /*订单类型*/
        data.setType("2");
        /*购买等级id*/
        data.setProductId(id);
        showLoadingDialog();
        mNetworkRequestInstance.callOrderPay(data)
                .compose(bindToLifecycle())
                .subscribe(purchaseMembershipLevelEntityResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(purchaseMembershipLevelEntityResponseData)) {
                        Runnable payRunnable = () -> {
                            Map<String, String> result = new PayTask(mActivity).payV2(purchaseMembershipLevelEntityResponseData.getData(), true);
                            Message msg = new Message();
                            msg.what = sdkPayFlag;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        };
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    } else {
                        HintUtil.showErrorWithToast(mContext, purchaseMembershipLevelEntityResponseData.getMsg());
                    }
                });
    }

    /**
     * 支付标志
     */
    public int sdkPayFlag = 1;

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @SuppressLint("CheckResult")
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /*对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。*/
                    /*同步返回需要验证的信息*/
                    String resultInfo = payResult.getResult();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(payResult.getResultStatus(), "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        initData();
                        AlipayResponse response = new AlipayResponse();
                        Type type = new TypeToken<Map<String, String>>() {
                        }.getType();
                        JsonObject jsonObject = new JsonParser().parse(resultInfo).getAsJsonObject();
                        Map<String, String> map = new Gson().fromJson(jsonObject.getAsJsonObject("alipay_trade_app_pay_response"), type);
                        response.setTradeNo(map.get("trade_no"));
                        response.setOutTradeNo(map.get("out_trade_no"));
                        response.setOrderType("2");
                        mNetworkRequestInstance.synchronousCallback(response)
                                .compose(bindToLifecycle())
                                .subscribe(returnData -> {

                                });
                        showSuccessDialog(R.string.dialog_red_envelope_released_successfully);
                    } else if (TextUtils.equals(payResult.getResultStatus(), "6001")) {
                        HintUtil.showErrorWithToast(mContext, "取消支付");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void carriedOutMethod() {
        RxBus.getInstance().post();
        finish();
    }
}
