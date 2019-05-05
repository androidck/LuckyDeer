package com.lucky.deer.home.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.demo.cloudstorage.executor.StartUpload;
import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseFragment;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.home.adapter.OnShowPromotionPageListener;
import com.lucky.deer.home.adapter.PromotionAdapter;
import com.lucky.deer.util.StringUtil;
import com.lucky.model.response.promotion.PromotionEntity;
import com.lucky.model.util.GlideUtils;
import com.lucky.model.util.HintUtil;
import com.lucky.sharingfunction.dialog.SharingFunctionDialog;
import com.lucky.sharingfunction.weixinshare.WeiXinListener;
import com.lucky.sharingfunction.weixinshare.entity.WeiXinEntity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 推广页面
 *
 * @author wangxiangyi
 * @date 2019/03/25
 */
public class PromotionFragment extends BaseFragment implements WeiXinListener {
    /**
     * 下拉刷新
     */
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    /**
     * 推广大图
     */
    @BindView(R.id.iv_promotion_img)
    ImageView ivPromotionImg;
    /**
     * 二维码布局
     */
    @BindView(R.id.rl_code)
    RelativeLayout rlCode;
    /**
     * 二维码
     */
    @BindView(R.id.iv_code)
    ImageView ivCode;
    /**
     * 选择推广
     */
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private PromotionAdapter promotionAdapter;
    private String absolutePath;
    private String promotionUrl;

    /**
     * 分享功能
     */
//    private SharingFunctionDialog.ImgSharingBuilder mSharingFunction;
    /**
     * 当前展示的图片信息
     */
    private PromotionEntity promotionEntity1;
    /**
     * 是否显示推广按钮
     */
    private OnShowPromotionPageListener mListener;

    public static PromotionFragment newInstance(Bundle args) {
        PromotionFragment fragment = new PromotionFragment();
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mListener != null) {
            mListener.onisShow(isVisibleToUser);
        }
    }

    public void setOnIsShowShareListener(OnShowPromotionPageListener listener) {
        mListener = listener;
    }

    @Override
    protected int initlayout() {
        return R.layout.fragment_child_promotion;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        rvList.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false));
        promotionAdapter = new PromotionAdapter();
        rvList.setAdapter(promotionAdapter);

    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        if (!getLoginStatus()) {
            return;
        }
        if (!srlRefresh.isRefreshing()) {
            showLoadingDialog();
        }
        /*获取推广信息*/
        mNetworkRequestInstance.getSearch()
                .subscribe(listResponseData -> {
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    if (RequestUtils.isRequestSuccess(listResponseData)) {
                        List<PromotionEntity> data = listResponseData.getData();
                        promotionEntity1 = data.get(0);
                        promotionEntity1.setSelect(true);
                        setImg(promotionEntity1);
                        promotionAdapter.setNewData(data);
                    } else {
                        HintUtil.showErrorWithToast(mActivity, listResponseData.getMsg());
                    }
                });
    }

    @Override
    protected void initListener() {
        /*下拉刷新*/
        srlRefresh.setOnRefreshListener(this::initData);
        promotionAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            promotionEntity1 = promotionAdapter.getData().get(position);
            setImg(promotionEntity1);
            for (PromotionEntity promotionEntity : promotionAdapter.getData()) {
                promotionEntity.setSelect(false);
            }
            promotionEntity1.setSelect(true);
            promotionAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == KeyConstant.PROMOTION_PICTURE) {
            uploadImage(absolutePath);
        }
    }

    /**
     * 设置网路图片
     */
    private void setImg(PromotionEntity promotionEntity) {
        switch (promotionEntity.getQrcodeType()) {
            /*二维码图片*/
            case "1":
                rlCode.setVisibility(View.VISIBLE);
                /*设置推广二维码图*/
                GlideUtils.loadImage(mActivity, ivCode, promotionEntity.getQrcode());
                break;
            /*链接由前台生成*/
            case "2":
                rlCode.setVisibility(View.VISIBLE);
                /*生成二维码并添加到组建里面*/
                ivCode.setImageBitmap(CodeUtils.createImage(promotionEntity.getQrcode(), 250, 250, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)));
                break;
            /*大图中自带二维码*/
            case "3":
                rlCode.setVisibility(View.GONE);
                break;
            default:
        }
        /*设置推广大图*/
        GlideUtils.loadImageSize(mActivity, ivPromotionImg, promotionEntity.getBigPicture(), 500, 800);
    }

    /**
     * 获取组建上图片
     */
    public void getPictureProcessing() {
        /*分享功能初始化*/
        SharingFunctionDialog.ImgSharingBuilder mSharingFunction = new SharingFunctionDialog
                .ImgSharingBuilder(mActivity)
                .setAppName(R.string.app_name)
                .setTitle("测试标题")
                .setDescription("测试内容")
                .setText("测试文本内容");
        if (promotionEntity1 != null && !TextUtils.isEmpty(promotionEntity1.getQrcodeType())) {
            switch (promotionEntity1.getQrcodeType()) {
                /*二维码图片*/
                case "1":
                    /*链接由前台生成*/
                case "2":
                    /*获取控件上图片转换成BitMap并设置到分享控件里面*/
                    mSharingFunction.setPictureCombiner(ivPromotionImg, ivCode);
                    break;
                /*大图中自带二维码*/
                case "3":
                    /*获取控件上图片转换成BitMap并设置到分享控件里面*/
                    mSharingFunction.setBitMapImg(mSharingFunction.getControlImage(ivPromotionImg));
                    break;
                default:
            }
            mSharingFunction.create();
            mSharingFunction.show();
        }
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
                    promotionUrl = baseReq.getKey();
                    ivPromotionImg.setImageBitmap(StringUtil.decodeStream(absolutePath));
                });
    }

    @Override
    public void onWeiXinData(WeiXinEntity data) {
        Log.d("WeiXinEntity", data.toString());
    }
}
