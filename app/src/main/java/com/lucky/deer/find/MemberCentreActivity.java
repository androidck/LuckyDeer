package com.lucky.deer.find;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.find.adapter.PurchaseMemberAdapter;
import com.lucky.deer.util.ProgressBarUtil;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.view.RecycleViewDivider;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.enums.ExecutionStatusEnum;
import com.lucky.model.request.find.PurchaseMembershipLevelReq;
import com.lucky.model.response.find.MemberCentreEntity;
import com.lucky.model.util.GlideUtils;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.util.Map;

import butterknife.BindView;

/**
 * 会员中心
 *
 * @author wangxiangyi
 * @date 2018/11/15
 */
public class MemberCentreActivity extends BaseActivity {

    @BindView(R.id.ll_top_bar_background)
    LinearLayout llTopBarBackground;
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    /**
     * 头像
     */
    @BindView(R.id.iv_avatar)
    QMUIRadiusImageView ivAvatar;
    /**
     * 昵称
     */
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    /**
     * 会员
     */
    @BindView(R.id.tv_new_vip)
    TextView tvNewVip;
    /**
     * 会员账号
     */
    @BindView(R.id.tv_account_number)
    TextView tvAccountNumber;

    @BindView(R.id.endLevel)
    TextView endLevel;

    @BindView(R.id.beginLevel)
    TextView beginLevel;
//    /**
//     * 设置进度
//     */
//    @BindView(R.id.apb_schedule)
//    QMUIProgressBar apbSchedule;
    /**
     * 设置进度
     */
    @BindView(R.id.pb_progress_bar)
    ProgressBar pbProgressBar;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    /**
     * 购买会员适配器
     */
    private PurchaseMemberAdapter mAdapter;
    private PublicDialog instance;
    private QMUIBottomSheet qmuiBottomSheet;

    private int mPosition;
    /**
     * 支付标志
     */
    public int sdkPayFlag = 1;

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
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
    protected int initLayout() {
        return R.layout.activity_member_centre;
    }

    @Override
    public boolean isStatusBarFontWhite() {
        return false;
    }

    @Override
    protected void initView() {
        llTopBarBackground.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorBlack_2e2e2e));
        topBar.setTitle(R.string.title_activity_member_centre)
                .setTextColor(ContextCompat.getColor(mContext, R.color.white));
        topBar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorBlack_2e2e2e));
        //左边添加返回按钮
        topBar.addLeftImageButton(R.mipmap.photo_back, 0)
                .setOnClickListener(v ->
                        overridePendingTransition(false, true)
                );
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new PurchaseMemberAdapter();
        rvList.setAdapter(mAdapter);
        /*设置列表分割线*/
        rvList.addItemDecoration(new RecycleViewDivider(mContext, DividerItemDecoration.VERTICAL, 10, R.color.color_background));
        /*获取单利*/
        instance = PublicDialog.getInstance();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        if (!srlRefresh.isRefreshing()) {
            showLoadingDialog();
        }
        /*设置总进度大小*/
        ProgressBarUtil progressBarUtil = ProgressBarUtil.getInstance(mContext, pbProgressBar)
                .setMaxValue(100);
//        /*设置总进度大小*/
//        apbSchedule.setMaxValue(100);
        mNetworkRequestInstance.search()
                .subscribe(listResponseData -> {
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    if (RequestUtils.isReqSuccAndHasData(listResponseData)) {
                        MemberCentreEntity data = listResponseData.getData();
                        /*判断是否上传头像*/
                        if (TextUtils.isEmpty(data.getUserHead())) {
                            /*获取默认头像并设置*/
                            GlideUtils.loadImage(mActivity, ivAvatar, ExecutionStatusEnum.getSearchStatusDefaultAvatar(ExecutionStatusEnum.defaultAvatar, getUserInfo().getSex()));
                        } else {
                            /*设置上传头像*/
                            GlideUtils.loadImage(mActivity, ivAvatar, data.getUserHead());
                        }
                        /*昵称*/
                        tvNickname.setText(data.getNickName());
                        /*会员等级*/
                        tvNewVip.setText(data.getLevelName());
                        /*账号*/
                        tvAccountNumber.setText(getString(R.string.text_fragment_mine_account_number) + data.getUserNo());

                        endLevel.setText(data.getEndLevel());

                        beginLevel.setText(data.getBeginLevel());
//                        /*进度*/
//                        apbSchedule.setProgress((int) (data.getLevelRatio() * 100));
                        progressBarUtil.setProgress((int) (data.getLevelRatio() * 100));
                        /*设置购买会员信息*/
                        mAdapter.setNewData(data.getList());
                    } else {
                        HintUtil.showErrorWithToast(mContext, listResponseData.getMsg());
                    }
                });
    }

    @Override
    protected void initListener() {
        /*下拉刷新监听*/
        srlRefresh.setOnRefreshListener(this::initData);
        /*会员等级购买监听*/
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mPosition = position;
            switch (view.getId()) {
                /*申请*/
                case R.id.tv_apply:
                    showLoadingDialog();
                    PurchaseMembershipLevelReq data = new PurchaseMembershipLevelReq();
                    data.setApplyMemberLevelId(mAdapter.getData().get(position).getLevelId());
                    mNetworkRequestInstance.upgradeApply(data)
                            .compose(bindToLifecycle())
                            .subscribe(returnData -> {
                                dismissLoadingDialog();
                                if (RequestUtils.isRequestSuccess(returnData)) {
                                    showSuccessDialog(R.string.dialog_successful_application);
                                } else {
                                    HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                                }
                            });
                    break;
                /*购买/立即推广*/
                case R.id.tv_buy_member:
                    /*判断是否时立即推广*/
                    if ("222".equals(mAdapter.getData().get(position).getLevelId())) {
                        /*立即推广*/
                        //jumpActivity(mContext, MainActivity.class, KeyConstant.MEMBER_CENTRE, true);

                        HintUtil.showErrorWithToast(mContext, "敬请期待");
                    } else {
                        /*购买*/
                        String[] getdata = getdata(R.array.payment_method);
                        //*初始化视图
                        qmuiBottomSheet = instance.addHeaderView(instance.publicListView(mContext, getdata));
                        qmuiBottomSheet.show();


                    }
                    break;
                default:
            }
        });
        instance.setOnItemClickListener((status, position) -> {
            switch (status) {
                case OK:
                    qmuiBottomSheet.dismiss();
                    /*判断选择支付方式*/
                    switch (position) {
                        /*支付宝*/
                        case 0:
                            purchaseMembershipLevel();
                            break;
                        /*微信*/
                        case 1:
                            break;
                        /*余额支付*/
                        case 2:
                            break;
                        default:
                    }
                    break;
                case CANCEL:
                    qmuiBottomSheet.dismiss();
                    break;
                default:
            }
        });
    }

    /**
     * 购买会员等级
     */
    @SuppressLint("CheckResult")
    private void purchaseMembershipLevel() {
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
        data.setTotalAmount(StringUtil.setNumberFormatting(mAdapter.getData().get(mPosition).getPrice(), 2));
        /*订单类型*/
        data.setType("1");
        /*购买等级id*/
        data.setMemberLevelId(mAdapter.getData().get(mPosition).getLevelId());
        showLoadingDialog();
        mNetworkRequestInstance.callOrderPay(data)
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


}
