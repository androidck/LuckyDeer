package com.lucky.deer.find.business.redEnvelope;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.find.business.adapter.NumberRecipientsListAdapter;
import com.lucky.deer.find.business.view.NineGridView;
import com.lucky.deer.find.business.view.adapter.NineImageAdapter;
import com.lucky.deer.find.business.view.others.GlideSimpleTarget;
import com.lucky.deer.find.business.view.util.Utils;
import com.lucky.deer.home.MainActivity;
import com.lucky.deer.util.MyCountDownTimer;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.util.onCountDownTimerListener;
import com.lucky.model.request.find.business.BusinessReq;
import com.lucky.model.response.find.BusinessList;
import com.lucky.model.response.find.EnclosuresBean;
import com.lucky.model.util.GlideUtils;
import com.lucky.model.util.HintUtil;
import com.lucky.sharingfunction.dialog.SharingFunctionDialog;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ch.ielse.view.imagewatcher.ImageWatcher;

/**
 * 红包信息
 *
 * @author wangxiangyi
 * @date 2019/03/26
 */
public class RedEnvelopeInfoActivity extends BaseActivity {
    public static String id = "id";
    public static String pageMarkKey = "pageMarkKey";
    /**
     * 标题
     */
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    /**
     * 下拉刷新
     */
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    /**
     * 头像
     */
    @BindView(R.id.qriv_avatar)
    QMUIRadiusImageView qrivAvatar;
    /**
     * 昵称
     */
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    /**
     * 浏览量
     */
    @BindView(R.id.tv_amount_visits)
    TextView tvAmountVisits;
    /**
     * 派送状态
     */
    @BindView(R.id.tv_delivery_status)
    TextView tvDeliveryStatus;
    /**
     * 已领人数
     */
    @BindView(R.id.tv_number_recipients)
    TextView tvNumberRecipients;
    /**
     * 共有多少钱
     */
    @BindView(R.id.tv_how_much_is)
    TextView tvHowMuchIs;
    /**
     * 领取人列表
     */
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    /**
     * 文章内容
     */
    @BindView(R.id.tv_content)
    TextView tvContent;
    /**
     * 图片列表
     */
    @BindView(R.id.nine_grid_view)
    NineGridView nineGridView;
    /**
     * 查看大图
     */
    @BindView(R.id.image_watcher)
    ImageWatcher imageWatcher;
    /**
     * 文章id
     */
    private String articleId;
    /**
     * 页面key
     */
    private String pageKey;


    private boolean isCanReturn;

    private NumberRecipientsListAdapter nAdapter;
    private BusinessList redEnvelopeAdvertisingVO;

    @Override
    protected int initLayout() {
        return R.layout.activity_red_envelope_info;
    }

    @Override
    protected void initView() {
        /*添加标题*/
        topBar.setTitle(R.string.title_activity_red_envelope_info);
        /*查看计时器*/
        MyCountDownTimer.getInstance()
                .initCountDownTimer(5000, new onCountDownTimerListener() {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        isCanReturn = false;
                        topBar.removeAllLeftViews();
                        topBar.addLeftTextButton(String.format("%d s", millisUntilFinished), 0)
                                .setTextColor(getResources().getColor(R.color.color_blue));
                    }

                    @Override
                    public void onFinish() {
                        isCanReturn = true;
                        topBar.removeAllLeftViews();
                        topBar.addLeftImageButton(R.mipmap.back, 0)
                                .setOnClickListener(v -> {
                                    if (!TextUtils.isEmpty(pageKey) && "1".equals(pageKey)) {
                                        jumpActivity(mContext, MainActivity.class, true);
                                    } else {
                                        overridePendingTransition(false, true);
                                    }
                                });
                    }
                }).start();
        rvList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        nAdapter = new NumberRecipientsListAdapter();
        rvList.setAdapter(nAdapter);
        /*设置半透明*/
        imageWatcher.setTranslucentStatus(Utils.calcStatusBarHeight(mActivity));
        /*设置空布局*/
        imageWatcher.setErrorImageRes(R.mipmap.error_picture);
        /*获取文章id*/
        if (!TextUtils.isEmpty(getStringData())) {
            articleId = getStringData();
        }
        /*获取网页跳转链接地址*/
        else if (getBundleData() != null) {
            /*跳转页面key*/
            pageKey = getBundleData().getString(pageMarkKey);
            articleId = getBundleData().getString(id);
        }
    }

    @Override
    protected void initListener() {
        /*下拉刷新*/
        srlRefresh.setOnRefreshListener(this::initData);
        /*初始化加载图片文件*/
        imageWatcher.setLoader((context, url, lc) -> {
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .apply(new RequestOptions()
                            .centerCrop()
                            /*占位图*/
                            .placeholder(R.color.white)
                            /*错误图*/
                            .error(R.mipmap.error_picture)
                            .centerInside()
                            // .priority(Priority.HIGH)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(new GlideSimpleTarget(lc));
        });
        /*点击图片监听*/
        nineGridView.setOnImageClickListener((position, view) -> {
            if (imageWatcher != null && nineGridView.getImageLists().size() > 0) {
                imageWatcher.show((ImageView) view, nineGridView.getImageViews(), nineGridView.getImageLists());
//                QMUIStatusBarHelper.setStatusBarLightMode(this);
//                    QMUIStatusBarHelper.setStatusBarDarkMode(this);
            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        BusinessReq businessReq = new BusinessReq();
        if (!TextUtils.isEmpty(articleId)) {
            businessReq.setRedEnvelopeAdvertisingId(articleId);
        }
        if (!srlRefresh.isRefreshing()) {
            showLoadingDialog();
        }
        /*文章详情接口*/
        mNetworkRequestInstance.getArticleDetails(businessReq)
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (srlRefresh != null) {
                        srlRefresh.setRefreshing(false);
                    }
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        /*领取状态*/
                        tvDeliveryStatus.setText(TextUtils.isEmpty(returnData.getData().getReceivingRemarks()) ? "" : returnData.getData().getReceivingRemarks());
                        if (returnData.getData() != null && returnData.getData().getRedEnvelopeAdvertisingVO() != null) {
                            redEnvelopeAdvertisingVO = returnData.getData().getRedEnvelopeAdvertisingVO();
                            /*设置头像*/
                            GlideUtils.loadImage(mContext, qrivAvatar, redEnvelopeAdvertisingVO.getUserHead(), R.mipmap.user_photo_no);
                            /*设置昵称*/
                            tvNickname.setText(redEnvelopeAdvertisingVO.getNickName());
                            /*浏览次数*/
                            tvAmountVisits.setText(String.format(getString(R.string.text_red_envelope_info_amount_visits), TextUtils.isEmpty(redEnvelopeAdvertisingVO.getBrowseVolume()) ? "0" : redEnvelopeAdvertisingVO.getBrowseVolume()));
//                            /*共有多少钱*/
//                            tvHowMuchIs.setText(String.format(getString(R.string.text_red_envelope_info_how_much_is), TextUtils.isEmpty(redEnvelopeAdvertisingVO.getTotalMoney()) ? "0.00" : redEnvelopeAdvertisingVO.getTotalMoney()));
                            if ("3".equals(redEnvelopeAdvertisingVO.getRedEnvelopeState())) {
                                /*已领取完*/
                                tvNumberRecipients.setText(String.format(getString(R.string.text_red_envelope_info_several_red_envelope),
                                        TextUtils.isEmpty(redEnvelopeAdvertisingVO.getRedEnvelopesNumber()) ? "0" : redEnvelopeAdvertisingVO.getRedEnvelopesNumber()));
                            } else {
                                /*未领取完*/
                                tvNumberRecipients.setText(String.format(getString(R.string.text_red_envelope_info_receive_few_red_envelope),
                                        /*领取红包个数*/
                                        (TextUtils.isEmpty(redEnvelopeAdvertisingVO.getSurplusRedEnvelopesNumber()) ? "0" :
                                                (TextUtils.isEmpty(redEnvelopeAdvertisingVO.getRedEnvelopesNumber()) ? "0" :
                                                        StringUtil.getSubtract(redEnvelopeAdvertisingVO.getRedEnvelopesNumber(), redEnvelopeAdvertisingVO.getSurplusRedEnvelopesNumber()))),
                                        /*红包个数*/
                                        (TextUtils.isEmpty(redEnvelopeAdvertisingVO.getRedEnvelopesNumber()) ? "0" :
                                                redEnvelopeAdvertisingVO.getRedEnvelopesNumber())));
                            }
                            if (returnData.getData().getReceiveRedEnvelopesUserVOList() != null &&
                                    returnData.getData().getReceiveRedEnvelopesUserVOList().size() > 0) {
                                /*这是领取人列表*/
                                nAdapter.setNewData(returnData.getData().getReceiveRedEnvelopesUserVOList());
                            } else {
                                nAdapter.setNewData(new ArrayList<>());
                            }

                            /*文章内容*/
                            tvContent.setText(redEnvelopeAdvertisingVO.getContext());
                            /*文章图片*/
                            if (redEnvelopeAdvertisingVO.getEnclosures() != null && redEnvelopeAdvertisingVO.getEnclosures().size() > 0) {
                                List<String> listimg = new ArrayList<>();
                                for (EnclosuresBean enclosure : redEnvelopeAdvertisingVO.getEnclosures()) {
                                    listimg.add(enclosure.getEnclosure());
                                }
                                nineGridView.setAdapter(new NineImageAdapter(mContext, new RequestOptions().centerCrop(), DrawableTransitionOptions.withCrossFade(), listimg));
                            }
                            topBar.removeAllRightViews();
                            topBar.addRightImageButton(R.mipmap.share_btn, 0)
                                    .setOnClickListener(v -> {
                                        new SharingFunctionDialog
                                                .WebSharingBuilder(mContext)
                                                .setAppName(R.string.app_name)
                                                .setTitle("这里可以领红包了")
                                                .setDescription((redEnvelopeAdvertisingVO != null && !TextUtils.isEmpty(redEnvelopeAdvertisingVO.getContext())) ? redEnvelopeAdvertisingVO.getContext() : "")
                                                .setUrl(String.format(HttpConstant.H5_SHARE_RED_ENVELOPE_ARTICLES, TextUtils.isEmpty(articleId) ? "" : articleId,
                                                        getUserInfo() != null ? getUserInfo().getUserNo() : ""))
                                                .create()
                                                .show();
                                    });
                        }
                    } else {
                        topBar.removeAllRightViews();
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SharingFunctionDialog.getInstance(mContext).onNewIntent(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!TextUtils.isEmpty(pageKey) && "1".equals(pageKey)) {
            jumpActivity(mContext, MainActivity.class, true);
            return true;
        } else if (isCanReturn) {
            return super.onKeyDown(keyCode, event);
        } else {
            return false;
        }
    }
}
