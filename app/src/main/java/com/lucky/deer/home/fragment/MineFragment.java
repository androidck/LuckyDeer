package com.lucky.deer.home.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseFragment;
import com.lucky.deer.common.WebViewActivity;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.home.onlineApplication.OnlineApplicationActivity;
import com.lucky.deer.login.LoginActivity;
import com.lucky.deer.mine.HelpActivity;
import com.lucky.deer.mine.MoreSettingActivity;
import com.lucky.deer.mine.SelectOrViewAvatarActivity;
import com.lucky.deer.util.PhoneUtils;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.enums.ExecutionStatusEnum;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.model.common.WebViewBean;
import com.lucky.model.request.userinfo.MineInfoReq;
import com.lucky.model.response.userinfo.PersonalCenterInfo;
import com.lucky.model.util.GlideUtils;
import com.lucky.model.util.HawkUtil;
import com.lucky.model.util.HintUtil;
import com.lucky.model.util.RxBus;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 我的模块
 *
 * @author wangxiangyi
 * @date 2018/09/20
 */
public class MineFragment extends BaseFragment {

    /**
     * 下拉刷新
     */
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    /**
     * 昵称布局
     */
    @BindView(R.id.rl_user_base_info)
    RelativeLayout rlUserBaseInfo;
    /**
     * 头像
     */
    @BindView(R.id.btn_avatar)
    QMUIRadiusImageView btnAvatar;
    /**
     * 点击登录
     */
    @BindView(R.id.btn_click_login)
    TextView btnClickLogin;
    /**
     * 昵称
     */
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    /**
     * 用户账号
     */
    @BindView(R.id.tv_account_number)
    TextView tvAccountNumber;

    PublicDialog inistanceView;
    @BindView(R.id.btn_help)
    RelativeLayout btnHelp;

    private QMUIDialog qmuiDialog;

    private PersonalCenterInfo mData;

    /**
     * 我是业务员弹窗
     */
    private QMUIBottomSheet mQmuibottomsheetAlesman;

    public static MineFragment newInstance(Bundle args) {
        MineFragment fragment = new MineFragment();
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
        return R.layout.fragment_mine;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        srlRefresh.setEnableLoadMore(false);
        /* 判断用户是否登录*/
        if (getLoginStatus()) {
            btnClickLogin.setVisibility(View.GONE);
        } else {
            btnClickLogin.setVisibility(View.VISIBLE);
            tvAccountNumber.setText(String.format(getString(R.string.text_fragment_mine_account_number), ""));
            srlRefresh.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    refreshLayout.getLayout().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initData();
                            refreshLayout.finishRefresh();
                        }
                    },150);
                }
            });
//          /*  int state = ((UserInfo) HawkUtil.getInstance().getSaveData(HawkUtil.USER_INFO)).getRegisterState();
//            if (state == 1 || state == 2 || state == 3 || state == 4 || state == 6) {
//                tvAccountAuthent.setText("未认证");
//            } else {
//                tvAccountAuthent.setText("已认证");
//            }*/
        }
        /*获取单利*/
        inistanceView = PublicDialog.getInstance();
        /*通知*/
        RxBus.getInstance()
                .toObservable()
                .subscribe(t -> initData());
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        if (!getLoginStatus()) {
            return;
        }
        getRegistrationStatus();
        mNetworkRequestInstance.getUserPersonalCenter()
                .subscribe(personalCenterInfoResponseData -> {
                    dismissLoadingDialog();
                    //判断是否请求成功
                    if (RequestUtils.isRequestSuccess(personalCenterInfoResponseData)) {
                        mData = personalCenterInfoResponseData.getData();
                        if (mData == null || "".equals(mData)) {
                            HawkUtil.getInstance().remove(HawkUtil.USER_INFO);
                            HintUtil.showErrorWithToast(mActivity, "用户已强制下线，请重新登录");
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            btnClickLogin.setVisibility(View.GONE);
                            if (TextUtils.isEmpty(mData.getUserHead())) {
                                GlideUtils.loadImage(mActivity, btnAvatar, ExecutionStatusEnum.getSearchStatusDefaultAvatar(ExecutionStatusEnum.defaultAvatar, getUserInfo().getSex()));
                            } else {
                                GlideUtils.loadImage(mActivity, btnAvatar, mData.getUserHead());
                            }
                            tvNickname.setText(mData.getNickName());
                            tvAccountNumber.setText(String.format(getString(R.string.text_fragment_mine_account_number), mData.getUserNo()));
                        }
                    } else {
                        HintUtil.showErrorWithToast(mActivity, personalCenterInfoResponseData.getMsg());
                    }
                });
//        /*获取个人中心视频*/
//        boolean isLogin = HawkUtil.getInstance().isContains(HawkUtil.USER_INFO);
//        if (isLogin == false) {
//            return;
//        } else {
//            mNetworkRequestInstance.listVideos(new BaseReq())
//                    .subscribe(returnData -> {
//                        List<VideoListEntity> list = new ArrayList<>();
//                        if (RequestUtils.isRequestSuccess(returnData)) {
//                            if (returnData.getData().getList().size() == 1) {
//                                list.add(returnData.getData().getList().get(0));
//                            } else if (returnData.getData().getList().size() >= 2) {
//                                list.add(returnData.getData().getList().get(0));
//                                list.add(returnData.getData().getList().get(1));
//                            }
//                        }
//                        VideoListEntity videoListEntity = new VideoListEntity();
//                        videoListEntity.setAddCover(R.mipmap.video_add);
//                        list.add(videoListEntity);
//                        mAdapter.setNewData(list);
//                    });
//        }
    }

    @Override
    protected void initListener() {
        /*下拉刷新监听*/
        /*获取修改昵称监听*/
        inistanceView.setOnClickListener((OnClickListener<String>) (status, useType, isPhoneNumber, text) -> {
            switch (status) {
                case CANCEL:
                    qmuiDialog.dismiss();
                    break;
                case OK:
                    if (isPhoneNumber) {
                        PhoneUtils.callPhoneDirectly(mActivity, text);
                    } else {
                        updateUserNiceName(text);
                    }
                    break;
                default:
            }
        });
//        /*初始化我是业务员点击监听*/
//        inistanceView.setOnItemClickListener((status, position) -> {
//            if (status != null) {
//                if (OnItemClickListener.ClickStatus.OK.equals(status)) {
//                    switch (position) {
//                        /*完善个人信息*/
//                        case 0:
//                            startActivity(new Intent(mActivity, PerfectInfoActivity.class));
//                            break;
//                        /*发布招聘信息*/
//                        case 1:
//                            startActivity(new Intent(mActivity, RecruitmentInfoActivity.class));
//                            break;
//                        /*查看招聘信息*/
//                        case 2:
//                            startActivity(new Intent(mActivity, RecruitmentInfoListActivity.class));
//                            break;
//                        default:
//                    }
//                }
//                if (mQmuibottomsheetAlesman != null && mQmuibottomsheetAlesman.isShowing()) {
//                    mQmuibottomsheetAlesman.dismiss();
//                }
//            }
//        });
//        /*视频点击监听*/
//        mAdapter.setOnItemClickListener((adapter, view, position) -> {
//            if (mAdapter.getData().get(position).getAddCover() > 0) {
//                /*获取视频权限*/
//                PermissionsUtils
//                        .getInstance(mActivity)
//                        .setPermissionsRequest(Manifest.permission.CAMERA,
//                                Manifest.permission.RECORD_AUDIO,
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        .subscribe(aBoolean -> {
//                            if (!aBoolean) {
//                                PermissionsUtils
//                                        .getInstance(mActivity)
//                                        .openSettingDetail();
//                            } else {
//                                /*上传视频*/
//                                startActivity(new Intent(mActivity, VideoActivity.class));
//                            }
//                        });
//            } else {
//                WebViewBean webViewBean = new WebViewBean();
//                webViewBean.setPageType(KeyConstant.MY_VIDEO);
//                webViewBean.setWebTitle(mAdapter.getData().get(position).getTitle());
//                webViewBean.setContent(mAdapter.getData().get(position).getDesc());
//                webViewBean.setWebUrl(HttpConstant.H5_WATCH_VIDEO + mAdapter.getData().get(position).getId());
//                startActivity(new Intent(mActivity, WebViewActivity.class).putExtra(mEntity, webViewBean));
//            }
//        });
//        sStartOrderingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            /*判断是否是手动按下监听*/
//            if (sStartOrderingSwitch.isPressed()) {
//                BaseActivity.isOpenIntervalPositioning(mActivity, isChecked);
//            }
//        });
//    }

//    @OnClick({R.id.iv_avatar, R.id.tv_vip, R.id.iv_edit, R.id.tv_integral, R.id.tv_credit_card, R.id.tv_debit_card,
//            R.id.tv_upcoming, R.id.rl_my_video, R.id.rl_referrer, R.id.rl_info_supplement, R.id.rl_message_board,
//            R.id.rl_transaction_record, R.id.rl_my_salesman, R.id.rl_share_download_link, R.id.rl_me_customer_service,
//            R.id.rl_upgrade_code, R.id.rl_more_setting, R.id.rl_online_service, R.id.rl_help, R.id.btn})
//    public void onViewClicked(View view) {
//        //判断是否登录
//        boolean isLogin = HawkUtil.getInstance().isContains(HawkUtil.USER_INFO);
//        //已登录可以点击的
//        if (isLogin == true) {
//            int state = ((UserInfo) HawkUtil.getInstance().getSaveData(HawkUtil.USER_INFO)).getRegisterState();
//            Intent intent;
//            switch (view.getId()) {
//                //头像
//                case R.id.iv_avatar:
//                    intent = new Intent(mActivity, SelectOrViewAvatarActivity.class);
//                    if (mData != null && !TextUtils.isEmpty(mData.getUserHead())) {
//                        intent.putExtra(mEntity, mData.getUserHead());
//                    }
//                    startActivityForResult(intent, KeyConstant.VIEW_AVATAR);
//                    break;
//                /*会员等级*/
//                case R.id.tv_vip:
//                    if (startActivitys(state) == true) {
//                        if (getRegisterState(getUserInfo().getRegisterState())) {
//                            intent = new Intent(mActivity, MemberCentreActivity.class);
//                            if (mData != null && !TextUtils.isEmpty(mData.getUserHead())) {
//                                intent.putExtra(mEntity, mData.getUserHead());
//                            }
//                            startActivityForResult(intent, KeyConstant.VIEW_AVATAR);
//                        }
//                    }
//                    break;
//                /*编辑 昵称*/
//                case R.id.iv_edit:
//                    qmuiDialog = inistanceView.setCustomizeView(
//                            inistanceView.initEtOrTvView(mActivity,
//                                    tvNickname.getText().toString().trim(),
//                                    false),
//                            true,
//                            KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_5);
//                    break;
//                /*积分*/
//                case R.id.tv_integral:
//
//                    break;
//                /*信用卡*/
//                case R.id.tv_credit_card:
//                    if (startActivitys(state) == true) {
//                        if (getRegisterState(getUserInfo().getRegisterState())) {
//                            SelectBankCardList selectBankCardList = new SelectBankCardList();
//                            selectBankCardList.setFlag(KeyConstant.SELECT_CREDIT_CARD);
//                            startActivity(new Intent(mActivity, SelectBankCardActivity.class).putExtra(mEntity, selectBankCardList));
//                        }
//                    }
//                    break;
//                /*储蓄卡*/
//                case R.id.tv_debit_card:
//                    if (startActivitys(state) == true) {
//                        if (getRegisterState(getUserInfo().getRegisterState())) {
//                            SelectBankCardList selectBankCardList = new SelectBankCardList();
//                            selectBankCardList.setFlag(KeyConstant.SELECT_DEBIT_CARD);
//                            startActivity(new Intent(mActivity, SelectBankCardActivity.class).putExtra(mEntity, selectBankCardList));
//                        }
//                    }
//                    break;
//                /*代办*/
//                case R.id.tv_upcoming:
//                    break;
//                /*我的视频*/
//                case R.id.rl_my_video:
//                    ActivityUtils.startActivity(mActivity, MyVideoActivity.class);
//                    break;
//                /*推荐人*/
//                case R.id.rl_referrer:
//                    getRefereeUserInfo();
//                    break;
//                /*信息补充*/
//                case R.id.rl_info_supplement:
//                    ActivityUtils.startActivity(mActivity, InfoSupplementActivity.class);
//                    break;
//                /*留言板*/
//                case R.id.rl_message_board:
//                    ActivityUtils.startActivity(mActivity, MessageBoardActivity.class);
//                    break;
//                /*交易记录*/
//                case R.id.rl_transaction_record:
//                    ActivityUtils.startActivity(mActivity, TransactionRecordActivity.class);
//                    break;
//                /*我是业务员*/
//                case R.id.rl_my_salesman:
//                    /*初始化视图*/
//                    mQmuibottomsheetAlesman = inistanceView.addHeaderView(inistanceView.publicListView(mActivity, getResources().getStringArray(R.array.item_my_salesman)));
//                    mQmuibottomsheetAlesman.show();
//                    break;
//                /*分享下载链接*/
//                case R.id.rl_share_download_link:
//                    shareDownloadLink();
//                    break;
//                /*我的客服*/
//                case R.id.rl_me_customer_service:
//                    /*初始化拨打客服电话弹出框*/
//                    qmuiDialog = inistanceView.setCustomizeView(
//                            inistanceView.initEtOrTvView(mActivity,
//                                    tvFixedLine.getText().toString().trim(),
//                                    "呼叫",
//                                    true),
//                            true,
//                            KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_4);
//                    break;
//                /*在线客服*/
//                case R.id.rl_online_service:
//                    WebViewBean webViewBean = new WebViewBean();
//                    webViewBean.setWebTitle(getString(R.string.online_service));
//                    webViewBean.setWebUrl(HttpConstant.H5_ONLINE_SERVICE + getUserInfo().getId());
//                    webViewBean.setCallJsMethod("YTX.destroy()");
//                    startActivity(new Intent(mActivity, WebViewActivity.class).putExtra(mEntity, webViewBean));
//                    break;
//                /*升级码*/
//                case R.id.rl_upgrade_code:
//                    /*输入验证码弹出框*/
//                    qmuiDialog = inistanceView.setCustomizeView(
//                            inistanceView.initVerificationCodeView(mActivity,
//                                    getString(R.string.text_upgrade_code),
//                                    getString(R.string.hint_upgrade_code),
//                                    getString(R.string.toast_upgrade_code_can_not_empty),
//                                    InputType.TYPE_CLASS_TEXT,
//                                    KeyConstant.TYPE_UPGRADE_CODE),
//                            true,
//                            KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_4);
//                    break;
//                /*更多设置*/
//                case R.id.rl_more_setting:
//                    startActivity(new Intent(mActivity, MoreSettingActivity.class).putExtra(mEntity, mData.getExtendOne()));
//                    break;
//                /*帮助*/
//                case R.id.rl_help:
//                    startActivity(new Intent(mActivity, HelpActivity.class));
////                    HintUtil.showErrorWithToast(mActivity, getString(R.string.stay_tuned));
//                    break;
//                default:
//
//            }
//        } else {
//            ActivityUtils.startActivity(mActivity, LoginActivity.class);
//        }
    }

    /**
     * 修改用户昵称
     *
     * @param text 修改昵称
     */
    @SuppressLint("CheckResult")
    private void updateUserNiceName(String text) {
        MineInfoReq mineInfoReq = new MineInfoReq();
        mineInfoReq.setNickName(text);
        showLoadingDialog();
        mNetworkRequestInstance.updateUserNiceName(mineInfoReq)
                .subscribe(stringResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        tvNickname.setText(text);
                        qmuiDialog.dismiss();
                    } else {
                        HintUtil.showErrorWithToast(mActivity, stringResponseData.getMsg());
                    }
                });
    }


    @OnClick({R.id.btn_avatar, R.id.btn_click_login, R.id.btn_edit, R.id.btn_product_collection, R.id.btn_browsing_history,
            R.id.btn_comment, R.id.btn_view_all_orders, R.id.btn_shopping_cart, R.id.btn_pending_payment,
            R.id.btn_to_delivered, R.id.btn_pending_receipt, R.id.btn_refund_or_after_sale, R.id.btn_online,
            R.id.btn_nearby_red_envelope, R.id.btn_harvest_address, R.id.btn_online_service, R.id.btn_more_setting,R.id.btn_help})
    public void onViewClicked(View view) {
        //获取当前的用户状态
        if (HawkUtil.getInstance().isContains(HawkUtil.USER_INFO)) {
            switch (view.getId()) {
                /*头像*/
                case R.id.btn_avatar:
                    Intent intent = new Intent(mActivity, SelectOrViewAvatarActivity.class);
                    if (mData != null && !TextUtils.isEmpty(mData.getUserHead())) {
                        intent.putExtra(mEntity, mData.getUserHead());
                    }
                    startActivityForResult(intent, KeyConstant.VIEW_AVATAR);
                    break;
                /*点击登录*/
                case R.id.btn_click_login:
                    break;
                /*编辑昵称*/
                case R.id.btn_edit:
                    qmuiDialog = inistanceView.setCustomizeView(
                            inistanceView.initEtOrTvView(mActivity,
                                    tvNickname.getText().toString().trim(),
                                    false),
                            true,
                            KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_5);
                    break;
                /*商品收藏*/
                case R.id.btn_product_collection:
                    /*判断是否实名制或开卡成功*/
                    if (getRegisterState(getUserInfo().getRegisterState())) {
                        jumpWebView(getString(R.string.text_fragment_mine_product_collection), String.format(HttpConstant.H5_MALL_PRODUCT_COLLECTION, (getUserInfo() != null ? getUserInfo().getId() : "")));
                    } else {
                        obtainLoginStatus();
                    }
                    break;
                /*浏览历史*/
                case R.id.btn_browsing_history:
                    /*判断是否实名制或开卡成功*/
                    if (getRegisterState(getUserInfo().getRegisterState())) {
                        jumpWebView(getString(R.string.text_fragment_mine_browsing_history), String.format(HttpConstant.H5_MALL_BROWSING_HISTORY, (getUserInfo() != null ? getUserInfo().getId() : "")));
                    } else {
                        obtainLoginStatus();
                    }
                    break;
                /*待评价*/
                case R.id.btn_comment:
                    /*判断是否实名制或开卡成功*/
                    if (getRegisterState(getUserInfo().getRegisterState())) {
                        jumpWebView(getString(R.string.text_fragment_mine_comment), HttpConstant.H5_MALL_ORDER_INFORMATION + getUserInfo().getId());
                    } else {
                        obtainLoginStatus();
                    }
                    break;
                /*查看全部订单*/
                case R.id.btn_view_all_orders:
                    /*判断是否实名制或开卡成功*/
                    if (getRegisterState(getUserInfo().getRegisterState())) {
                        startBrowserActivity(getActivity(), 1, HttpConstant.H5_MALL_ORDER_INFORMATION + getUserInfo().getId(), "全部订单");
                    } else {
                        obtainLoginStatus();
                    }
                    break;
                /*购物车*/
                case R.id.btn_shopping_cart:
                    /*判断是否实名制或开卡成功*/
                    if (getRegisterState(getUserInfo().getRegisterState())) {
                       // startBrowserActivity(getActivity(), 1, HttpConstant.H5_MALL_SHOPPING_CART + "c263ac9d674e46558f89d1cb76cb9951", getString(R.string.text_fragment_mine_shopping_cart));
                          startBrowserActivity(getActivity(),1,HttpConstant.H5_MALL_SHOPPING_CART+getUserInfo().getId(),getString(R.string.text_fragment_mine_shopping_cart));
                    } else {
                        obtainLoginStatus();
                    }
                    break;
                /*待收款*/
                case R.id.btn_pending_payment:
                    /*判断是否实名制或开卡成功*/
                    if (getRegisterState(getUserInfo().getRegisterState())) {
                        startBrowserActivity(getActivity(), 1, HttpConstant.H5_MALL_ORDER_INFORMATION + getUserInfo().getId(), getString(R.string.text_fragment_mine_pending_payment));

                    } else {
                        obtainLoginStatus();
                    }
                    break;
                /*待发货*/
                case R.id.btn_to_delivered:
                    /*判断是否实名制或开卡成功*/
                    if (getRegisterState(getUserInfo().getRegisterState())) {
                        startBrowserActivity(getActivity(), 1, HttpConstant.H5_MALL_ORDER_INFORMATION + getUserInfo().getId(), getString(R.string.text_fragment_mine_to_delivered));

                    } else {
                        obtainLoginStatus();
                    }
                    break;
                /*待收货*/
                case R.id.btn_pending_receipt:
                    /*判断是否实名制或开卡成功*/
                    if (getRegisterState(getUserInfo().getRegisterState())) {
                        startBrowserActivity(getActivity(), 1, HttpConstant.H5_MALL_ORDER_INFORMATION + getUserInfo().getId(), getString(R.string.text_fragment_mine_pending_receipt));

                    } else {
                        obtainLoginStatus();
                    }
                    break;
                /*退款/售后*/
                case R.id.btn_refund_or_after_sale:
                    /*判断是否实名制或开卡成功*/
                    if (getRegisterState(getUserInfo().getRegisterState())) {
                        startBrowserActivity(getActivity(), 1, HttpConstant.H5_MALL_REFUND_OR_AFTER_SALE + getUserInfo().getId(), getString(R.string.text_fragment_mine_refund_or_after_sale));

                    } else {
                        obtainLoginStatus();
                    }
                    break;
                /*在线申请信用卡*/
                case R.id.btn_online:
                    startActivity(new Intent(mActivity, OnlineApplicationActivity.class).putExtra(mEntity, OnlineApplicationActivity.ALL_DATA));
                    break;
                /*附近红包*/
                case R.id.btn_nearby_red_envelope:
                    HintUtil.showErrorWithToast(mActivity, getString(R.string.stay_tuned));
                    break;
                /*收获地址*/
                case R.id.btn_harvest_address:
                    /*判断是否实名制或开卡成功*/
                    if (getRegisterState(getUserInfo().getRegisterState())) {

                        startBrowserActivity(getActivity(), 1, String.format(HttpConstant.H5_MALL_HARVEST_ADDRESS, (getUserInfo() != null ? getUserInfo().getId() : "")), getString(R.string.text_fragment_mine_harvest_address));

                    } else {
                        obtainLoginStatus();
                    }
                    break;
                /*在线客服*/
                case R.id.btn_online_service:
                    WebViewBean webViewBean = new WebViewBean();
                    webViewBean.setWebTitle(getString(R.string.online_service));
                    webViewBean.setWebUrl(HttpConstant.H5_ONLINE_SERVICE + getUserInfo().getId());
                    webViewBean.setCallJsMethod("YTX.destroy()");
                    startActivity(new Intent(mActivity, WebViewActivity.class).putExtra(mEntity, webViewBean));
                    break;
                /*更多设置*/
                case R.id.btn_more_setting:
                    startActivity(new Intent(mActivity, MoreSettingActivity.class).putExtra(mEntity, mData.getExtendOne()));
                    break;
                case R.id.btn_help:
                    startActivity(new Intent(mActivity, HelpActivity.class));
                    break;
                default:
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == KeyConstant.VIEW_AVATAR) {
            mData.setUserHead(data.getExtras().getString(mEntity));
            GlideUtils.loadImage(mActivity, btnAvatar, mData.getUserHead());
        }
    }


//        /**
//         * 分享下来链接方法
//         */
//        public void shareDownloadLink () {
//            new SharingFunctionDialog
//                    .WebSharingBuilder(mActivity)
//                    .setAppName(R.string.app_name)
//                    .setTitle("分享标题")
//                    .setDescription("分享内容")
//                    .setUrl("https://www.baidu.com/")
//                    .create()
//                    .show();
//        }
}



