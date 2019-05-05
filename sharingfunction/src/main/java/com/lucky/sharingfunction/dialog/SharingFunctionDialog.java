package com.lucky.sharingfunction.dialog;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.lucky.sharingfunction.DisplayUtils;
import com.lucky.sharingfunction.alipayshare.AlipayShare;
import com.lucky.sharingfunction.constant.KeyConstant;
import com.lucky.sharingfunction.ddshare.DingDingShare;
import com.lucky.sharingfunction.qq.QqFeatures;
import com.lucky.sharingfunction.util.ImageUtils;
import com.lucky.sharingfunction.util.PermissionsUtils;
import com.lucky.sharingfunction.util.ToastUtil;
import com.lucky.sharingfunction.weiboshare.WeiBoShare;
import com.lucky.sharingfunction.weixinshare.WeiXinFeatures;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * 分享功能对话框
 */
public class SharingFunctionDialog {


    private static Context mContext;
    private static SharingFunctionDialog instance;


    public static SharingFunctionDialog getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            synchronized (SharingFunctionDialog.class) {
                if (instance == null) {
                    instance = new SharingFunctionDialog();
                }
            }
        }
        return instance;
    }

    public void onNewIntent(Intent data) {
        WeiXinFeatures.getInstance((Activity) mContext).onNewIntent(data);
        WeiBoShare.getInstance((Activity) mContext).onNewIntent(data);
        DingDingShare.getInstance(mContext).onNewIntent(data);
        AlipayShare.getInstance(mContext).onNewIntent(data);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 官方文档没这句代码, 但是很很很重要, 不然不会回调!
        Tencent.onActivityResultData(requestCode, resultCode, data, new ShareUiListener());

        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_QQ_SHARE ||
                    resultCode == Constants.REQUEST_QZONE_SHARE ||
                    resultCode == Constants.REQUEST_OLD_SHARE) {
                QqFeatures.getInstance((Activity) mContext).onActivityResult(requestCode, resultCode, data);
                Tencent.handleResultData(data, new ShareUiListener());
            } else {
                WeiXinFeatures.getInstance((Activity) mContext).onNewIntent(data);
                WeiBoShare.getInstance((Activity) mContext).onNewIntent(data);
                DingDingShare.getInstance(mContext).onNewIntent(data);
                AlipayShare.getInstance(mContext).onNewIntent(data);
            }
        }



    }

    /**
     * 分享app名称
     */
    private String sharingAppName;
    /**
     * 分享标题
     */
    private String sharingTitle;
    /**
     * 分享内容描述
     */
    private String sharingDescription;
    /**
     * 分享文本
     */
    private String sharingText;
    /**
     * 分享的图片url
     */
    private String sharingImageUrl;
    /**
     * 分享的网页url
     */
    private String sharingUrl;
    /**
     * 分享图片
     */
    private Bitmap bitMapImage;
    /**
     * 分享本地图片地址
     */
    private String pathImage;


    private String getAppName() {
        return sharingAppName;
    }

    private void setAppName(String sharingAppName) {
        this.sharingAppName = sharingAppName;
    }

    private String getTitle() {
        return sharingTitle;
    }

    private void setTitle(String sharingTitle) {
        this.sharingTitle = sharingTitle;
    }

    private String getDescription() {
        return sharingDescription;
    }

    private void setDescription(String sharingDescription) {
        this.sharingDescription = sharingDescription;
    }

    private String getText() {
        return sharingText;
    }

    private void setText(String sharingText) {
        this.sharingText = sharingText;
    }

    private String getImageUrl() {
        return sharingImageUrl;
    }

    private void setImageUrl(String sharingImageUrl) {
        this.sharingImageUrl = sharingImageUrl;
    }

    private String getUrl() {
        return sharingUrl;
    }

    private void setUrl(String sharingUrl) {
        this.sharingUrl = sharingUrl;
    }

    public Bitmap getBitMapImage() {
        return bitMapImage;
    }

    public void setBitMapImage(Bitmap bitMapImage) {
        this.bitMapImage = bitMapImage;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    /**
     * 图片分型
     */
    public static class ImgSharingBuilder {
        /**
         * 分享app名称
         */
        private String sharingAppName;
        /**
         * 分享标题
         */
        private String sharingTitle;
        /**
         * 分享内容描述
         */
        private String sharingDescription;
        /**
         * 分享文本
         */
        private String sharingText;

        /**
         * 分享图片
         */
        private Bitmap bitMapImage;
        /**
         * 分享本地图片地址
         */
        private String pathImage;

//        private String qqImgPath;

        private SharingFunctionView sharingFunctionView;

        public ImgSharingBuilder(Context context) {
            mContext = context;
        }

        public ImgSharingBuilder setAppName(int sharingAppName) {
            return setAppName(mContext.getString(sharingAppName));
        }

        public ImgSharingBuilder setAppName(String sharingAppName) {
            this.sharingAppName = sharingAppName;
            return this;
        }

        public ImgSharingBuilder setTitle(int sharingTitle) {
            return setTitle(mContext.getString(sharingTitle));
        }

        public ImgSharingBuilder setTitle(String sharingTitle) {
            this.sharingTitle = sharingTitle;
            return this;
        }

        public ImgSharingBuilder setDescription(int sharingDescription) {
            return setDescription(mContext.getString(sharingDescription));
        }

        public ImgSharingBuilder setDescription(String sharingDescription) {
            this.sharingDescription = sharingDescription;
            return this;
        }

        public ImgSharingBuilder setText(int sharingText) {
            return setText(mContext.getString(sharingText));
        }

        public ImgSharingBuilder setText(String sharingText) {
            this.sharingText = sharingText;
            return this;
        }

        public ImgSharingBuilder setBitMapImg(Bitmap bitMapImage) {
            this.bitMapImage = bitMapImage;
            return this;
        }

        public ImgSharingBuilder setPathImg(String pathImage) {
            this.pathImage = pathImage;
            return this;
        }

        public String getAppName() {
            return sharingAppName;
        }

        public String getTitle() {
            return sharingTitle;
        }

        public String getDescription() {
            return sharingDescription;
        }

        public String getText() {
            return sharingText;
        }


        public Bitmap getBitMapImage() {
            return bitMapImage;
        }

        public String getPathImage() {
            return pathImage;
        }

        /**
         * 获取控件上面的图片
         *
         * @param view 控件
         * @return
         */
        public Bitmap getControlImage(ImageView view) {
            if (view != null && view.getDrawable() != null) {
                return ((BitmapDrawable) view.getDrawable()).getBitmap();
            }
            return null;
        }

        /**
         * 图片合成器
         *
         * @param bitMapImage1 图片一
         * @param bitMapImage2 图片二
         */
        @SuppressLint("CheckResult")
        public void setPictureCombiner(ImageView bitMapImage1, ImageView bitMapImage2) {
            /*获取单利*/
            PermissionsUtils.getInstance(mContext)
                    .setPermissionsRequest(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(aBoolean -> {
                        if (aBoolean) {
                            Bitmap bitmap1 = getControlImage(bitMapImage1);
                            Bitmap bitmap2 = getControlImage(bitMapImage2);
                            if (bitmap1 != null && bitmap2 != null) {
                                /*生成图片底板*/
                                Bitmap bitmap3 = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(), bitmap1.getConfig());
                                Canvas canvas = new Canvas(bitmap3);
                                canvas.drawBitmap(bitmap1, new Matrix(), null);
                                /*bitmap2写入点的x、y坐标*/
                                canvas.drawBitmap(bitmap2, (bitmap1.getWidth() / 2) - (bitmap2.getWidth() / 2),
                                        (bitmap1.getHeight() - DisplayUtils.dp2px(mContext, 120)) - (bitmap2.getHeight() / 2),
                                        null);
                                if (ImageUtils.saveImageToGallery(mContext, bitmap3, "image3")) {
                                    pathImage = ImageUtils.getImagePath();
                                }
                                bitMapImage = bitmap3;
                            }
                        } else {
                            PermissionsUtils.getInstance(mContext).openSettingDetail();
                        }
                    });
        }

        /**
         * 组装产品
         */
        private void construct(SharingFunctionDialog computer) {
            computer.setAppName(sharingAppName);
            computer.setTitle(sharingTitle);
            computer.setDescription(sharingDescription);
            computer.setText(sharingText);
            computer.setBitMapImage(bitMapImage);
            computer.setPathImage(pathImage);
        }

        /**
         * 创建数据应用
         */
        public ImgSharingBuilder create() {
            SharingFunctionDialog computer = new SharingFunctionDialog();
            construct(computer);
            initView();
            return this;
        }

        private void initView() {
            sharingFunctionView = SharingFunctionView
                    .getInstance(mContext)
                    .initView()
                    .setOnItemClickListener(position -> {
                        switch (position) {
                            /*微信*/
                            case KeyConstant.TAG_SHARE_WE_CHAT:
                                WeiXinFeatures.getInstance((Activity) mContext)
                                        .shareImgToWechat(getTitle(), getDescription(), getBitMapImage(), getPathImage(), 0);
                                break;
                            /*朋友圈*/
                            case KeyConstant.TAG_SHARE_FRIENDS_CIRCLE:
                                WeiXinFeatures.getInstance((Activity) mContext)
                                        .shareImgToWechat(getTitle(), getDescription(), getBitMapImage(), getPathImage(), 1);
                                break;
                            /*QQ*/
                            case KeyConstant.TAG_SHARE_QQ_SPACE:
                                QqFeatures.getInstance((Activity) mContext)
                                        .sharePicToQQ(getTitle(), getPathImage(), 0);
                                break;
                            /*QQ空间*/
                            case KeyConstant.TAG_SHARE_QQ_ZONE:
                                QqFeatures.getInstance((Activity) mContext)
                                        .sharePicToQQ(getTitle(), getPathImage(), 1);
                                break;
                            /*微博*/
                            case KeyConstant.TAG_SHARE_WEI_BO:
                                WeiBoShare.getInstance((Activity) mContext)
                                        .wbShare(WeiBoShare.getInstance((Activity) mContext).getImageObj(getPathImage(), getBitMapImage()));
                                break;
                            /*钉钉*/
                            case KeyConstant.TAG_SHARE_NAIL:
//                                DingDingShare.getInstance(mContext)
//                                        .sendWebPageMessage(getTitle(), getDescription(), getImageUrl(), getPathImage(), false);
                                break;
                            /*支付宝*/
                            case KeyConstant.TAG_SHARE_ALIPAY:
//                                AlipayShare.getInstance(mContext)
//                                        .shareWebPageMessage(getTitle(), getDescription(), getImageUrl(), getPathImage());
                                break;
                            default:
                        }
                    });
        }

        public void show() {
            sharingFunctionView.show();
        }
    }


    /**
     * 网页分享
     */
    public static class WebSharingBuilder {
        /**
         * 分享app名称
         */
        private String sharingAppName;
        /**
         * 分享标题
         */
        private String sharingTitle;
        /**
         * 分享内容描述
         */
        private String sharingDescription;
        /**
         * 分享文本
         */
        private String sharingText;
        /**
         * 分享的图片url
         */
        private String sharingImageUrl;
        /**
         * 分享的网页url
         */
        private String sharingUrl;

        private SharingFunctionView sharingFunctionView;


        public WebSharingBuilder(Context context) {
            mContext = context;
        }

        /**
         * 分享app名称
         *
         * @param sharingAppName app名称
         * @return
         */
        public WebSharingBuilder setAppName(int sharingAppName) {
            return setAppName(mContext.getString(sharingAppName));
        }

        /**
         * 分享app名称
         *
         * @param sharingAppName app名称
         * @return
         */
        public WebSharingBuilder setAppName(String sharingAppName) {
            this.sharingAppName = sharingAppName;
            return this;
        }

        /**
         * 分享标题
         *
         * @param sharingTitle 标题
         * @return
         */
        public WebSharingBuilder setTitle(int sharingTitle) {
            return setTitle(mContext.getString(sharingTitle));
        }

        /**
         * 分享标题
         *
         * @param sharingTitle 标题
         * @return
         */
        public WebSharingBuilder setTitle(String sharingTitle) {
            this.sharingTitle = sharingTitle;
            return this;
        }

        /**
         * 分享内容描述
         *
         * @param sharingDescription 描述内容
         * @return
         */
        public WebSharingBuilder setDescription(int sharingDescription) {
            return setDescription(mContext.getString(sharingDescription));
        }

        /**
         * 分享内容描述
         *
         * @param sharingDescription 描述内容
         * @return
         */
        public WebSharingBuilder setDescription(String sharingDescription) {
            this.sharingDescription = sharingDescription;
            return this;
        }

        public WebSharingBuilder setText(int sharingText) {
            return setText(mContext.getString(sharingText));
        }

        public WebSharingBuilder setText(String sharingText) {
            this.sharingText = sharingText;
            return this;
        }

        public WebSharingBuilder setImageUrl(String sharingImageUrl) {
            this.sharingImageUrl = sharingImageUrl;
            return this;
        }

        /**
         * 内容链接
         *
         * @param sharingUrl 内容链接
         * @return
         */
        public WebSharingBuilder setUrl(String sharingUrl) {
            this.sharingUrl = sharingUrl;
            return this;
        }

        public String getAppName() {
            return sharingAppName;
        }

        public String getTitle() {
            return sharingTitle;
        }

        public String getDescription() {
            return sharingDescription;
        }

        public String getText() {
            return sharingText;
        }

        public String getImageUrl() {
            return sharingImageUrl;
        }

        public String getUrl() {
            return sharingUrl;
        }

        /**
         * 组装产品
         */
        private void construct(SharingFunctionDialog computer) {
            computer.setAppName(sharingAppName);
            computer.setTitle(sharingTitle);
            computer.setDescription(sharingDescription);
            computer.setText(sharingText);
            computer.setImageUrl(sharingImageUrl);
            computer.setUrl(sharingUrl);
        }

        /**
         * 创建数据应用
         */
        public WebSharingBuilder create() {
            SharingFunctionDialog computer = new SharingFunctionDialog();
            construct(computer);
            initView();
            return this;
        }

        private void initView() {
            sharingFunctionView = SharingFunctionView
                    .getInstance(mContext)
                    .initView()
                    .setOnItemClickListener(position -> {
                        switch (position) {
                            /*微信*/
                            case KeyConstant.TAG_SHARE_WE_CHAT:
                                WeiXinFeatures.getInstance((Activity) mContext)
                                        .shareToWechat(getTitle(), getDescription(), getUrl(), 0);
                                break;
                            /*朋友圈*/
                            case KeyConstant.TAG_SHARE_FRIENDS_CIRCLE:
                                WeiXinFeatures.getInstance((Activity) mContext)
                                        .shareToWechat(getTitle(), getDescription(), getUrl(), 1);
                                break;
                            /*QQ*/
                            case KeyConstant.TAG_SHARE_QQ_SPACE:
                                QqFeatures.getInstance((Activity) mContext)
                                        .shareToQQ(getAppName(), getTitle(), getDescription(), getImageUrl(), getUrl(), 0);
                                break;
                            /*QQ空间*/
                            case KeyConstant.TAG_SHARE_QQ_ZONE:
                                QqFeatures.getInstance((Activity) mContext)
                                        .shareToQQ(getAppName(), getTitle(), getDescription(), getImageUrl(), getUrl(), 1);
                                break;
                            /*微博*/
                            case KeyConstant.TAG_SHARE_WEI_BO:
                                WeiBoShare.getInstance((Activity) mContext)
                                        .wbShare(WeiBoShare.getInstance((Activity) mContext).getTextObj(getTitle(), getDescription(), getText(), getUrl()));
                                break;
                            /*钉钉*/
                            case KeyConstant.TAG_SHARE_NAIL:
                                DingDingShare.getInstance(mContext)
                                        .sendWebPageMessage(getTitle(), getDescription(), getImageUrl(), getUrl(), false);
                                break;
                            /*支付宝*/
                            case KeyConstant.TAG_SHARE_ALIPAY:
                                AlipayShare.getInstance(mContext)
                                        .shareWebPageMessage(getTitle(), getDescription(), getImageUrl(), getUrl());
                                break;
                            default:
                        }
                    });
        }

        public void show() {
            sharingFunctionView.show();
        }
    }


    class ShareUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            ToastUtil.toastShow(mContext,"分享成功");
        }

        @Override
        public void onError(UiError uiError) {
            // 分享异常
            ToastUtil.toastShow(mContext,"分享失败");
        }

        @Override
        public void onCancel() {
            //分享取消
            ToastUtil.toastShow(mContext,"取消分享");
        }

    }

}
