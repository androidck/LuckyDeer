package com.lucky.sharingfunction.qq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lucky.sharingfunction.R;
import com.lucky.sharingfunction.constant.KeyConstant;
import com.lucky.sharingfunction.util.ToastUtil;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * QQ功能
 *
 * @author wangxingyi
 * @date 2018/09/27
 */
public class QqFeatures {

    private static QqFeatures instance;
    private static Activity mActivity;

    private static Tencent mTencent;
    private Bundle params;

    private QqFeatures() {
        init();
    }

    public static QqFeatures getInstance(Activity activity) {
        mActivity = activity;
        if (instance == null) {
            synchronized (QqFeatures.class) {
                if (instance == null) {
                    instance = new QqFeatures();
                }
            }
        }
        init();
        return instance;
    }

    private static void init() {
        if (mTencent == null) {
            mTencent = Tencent.createInstance(KeyConstant.APP_ID_QQ, mActivity);
        }
    }

    /**
     * 分享图片到 QQ 、QQ 空间
     *
     * @param appName 分享app名称
     * @param picPath 分享的本地图片的url
     * @param flag    1是qq空间，0是好友
     */
    public void sharePicToQQ(int appName, String picPath, int flag) {
        sharePicToQQ(mActivity.getString(appName), picPath, flag);
    }

    /**
     * 分享图片到 QQ 、QQ 空间
     *
     * @param appName 分享app名称
     * @param picPath 分享的本地图片的url
     * @param flag    1是qq空间，0是好友
     */
    public void sharePicToQQ(String appName, String picPath, int flag) {
        if (isQQInstalled()) {
            params = new Bundle();
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, picPath);
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, flag);
            mTencent.shareToQQ(mActivity, params, new ShareUiListener());
//        if (1 == flag) {
//            mTencent.shareToQzone(mActivity, params, new BaseUiListener(mActivity, mTencent, 2));
//        } else {
//            mTencent.shareToQQ(mActivity, params, new BaseUiListener(mActivity, mTencent, 2));
//        }
        }
    }

    /**
     * 分享网址到QQ、QQ空间
     *
     * @param appName 分享app名称
     * @param title   分享标题
     * @param info    分享内容描述
     * @param imgurl  分享的图片url
     * @param url     分享的网页url
     * @param flag    1是qq空间，0是好友
     */
    public void shareToQQ(int appName, String title, String info, String imgurl, String url, int flag) {
        shareToQQ(mActivity.getString(appName), title, info, imgurl, url, flag);
    }

    /**
     * 分享网址到QQ、QQ空间
     *
     * @param appName 分享app名称
     * @param title   分享标题
     * @param info    分享内容描述
     * @param imgurl  分享的图片url
     * @param url     分享的网页url
     * @param flag    1是qq空间，0是好友
     */
    public void shareToQQ(String appName, String title, String info, String imgurl, String url, int flag) {
        if (isQQInstalled()) {
            params = new Bundle();
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, info);
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
//        /*图片链接ArrayList*/
//        params.putStringArrayList(QQShare.SHARE_TO_QQ_IMAGE_URL, strings);
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgurl);
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, flag);
            mTencent.shareToQQ(mActivity, params, new ShareUiListener());
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mTencent != null && !mTencent.isSessionValid()) {
            Tencent.onActivityResultData(requestCode, resultCode, data, new ShareUiListener());
//            mTencent.handleLoginData(data, new BaseUiListener(mActivity, mTencent, 1));
        }
    }

    /**
     * QQ登录
     */
    public void doLogin() {
        if (isQQInstalled()) {
            if (mTencent != null && !mTencent.isSessionValid()) {
                logOut();
                mTencent.login(mActivity, KeyConstant.SCOPE_QQ, new ShareUiListener());
            }
        }
    }

    /**
     * 退出登录
     */
    public void logOut() {
        if (isQQInstalled()) {
            if (mTencent != null && !mTencent.isSessionValid()) {
                mTencent.logout(mActivity);
            }
        }
    }

    /**
     * 判断手机是否安装了qq软件
     *
     * @return true：安装了，false：没有安装
     */
    public boolean isQQInstalled() {
        if (!mTencent.isQQInstalled(mActivity)) {
//            JumpStoreUtils.goToMarket(mActivity, "con.tencent.mobileqq");
            ToastUtil.toastShow(mActivity, R.string.no_qq);
            return false;
        }
        return true;
    }

    class ShareUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            ToastUtil.toastShow(mActivity,"分享成功");
        }

        @Override
        public void onError(UiError uiError) {
            // 分享异常
            ToastUtil.toastShow(mActivity,"分享失败");
        }

        @Override
        public void onCancel() {
            //分享取消
            ToastUtil.toastShow(mActivity,"取消分享");
        }

    }
}
