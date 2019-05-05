package com.lucky.sharingfunction.weiboshare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.lucky.sharingfunction.constant.KeyConstant;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareHandler;

/**
 * 微博分享
 *
 * @author wangxingyi
 * @date 2018/09/28
 */
public class WeiBoShare {
    private static WeiBoShare instance;
    private static Activity mActivity;
    private static WbShareHandler wbShareHandler;

    public WeiBoShare() {
        init();
    }

    public static WeiBoShare getInstance(Activity activity) {
        mActivity = activity;
        if (instance == null) {
            synchronized (WeiBoShare.class) {
                if (instance == null) {
                    instance = new WeiBoShare();
                }
            }
        }
        init();
        return instance;
    }

    private static void init() {
        if (wbShareHandler == null) {
            WbSdk.install(mActivity, new AuthInfo(mActivity, KeyConstant.APP_ID_WEI_BO, KeyConstant.REDIRECT_URL_WEI_BO, KeyConstant.SCOPE_WEI_BO));
            wbShareHandler = new WbShareHandler(mActivity);
            wbShareHandler.registerApp();
        }
    }

    /**
     * 分享功能
     */
    public void wbShare(Object obj) {
        WeiboMultiMessage weiboAppManager = new WeiboMultiMessage();
        if (obj instanceof TextObject) {
            weiboAppManager.textObject = (TextObject) obj;
        } else if (obj instanceof ImageObject) {
            weiboAppManager.imageObject = (ImageObject) obj;
        }
        wbShareHandler.shareMessage(weiboAppManager, false);
    }

    /**
     * 分享本地图片
     *
     * @param imagePath 图片地址
     * @return
     */
    public ImageObject getImageObj(String imagePath, Bitmap bitmap/*, String webpageUrl*/) {
        ImageObject imageObject = new ImageObject();
        if (!TextUtils.isEmpty(imagePath)) {
            imageObject.imagePath = imagePath;
        } else if (bitmap != null) {
            imageObject.setImageObject(bitmap);
        }/* else if (!TextUtils.isEmpty(webpageUrl)) {
            imageObject.actionUrl = webpageUrl;
        }*/
        return imageObject;
    }

    /**
     * 分享网页
     *
     * @param title      分享标题
     * @param desc       分享内容描述
     * @param text       分享文本内容
     * @param webpageUrl 分享的网页url
     * @return
     */
    public TextObject getTextObj(String title, String desc, String text, String webpageUrl) {
        TextObject textObject = new TextObject();
        /*分享标题*/
        textObject.title = title;
        /*分享描述*/
        textObject.description = desc;
        /*分享文本*/
        textObject.text = text;
        /*分享内容链接*/
        textObject.actionUrl = webpageUrl;
        return textObject;
    }

    /**
     * 设置回掉
     *
     * @param intent
     */
    public void onNewIntent(Intent intent) {
        if (wbShareHandler != null) {
            wbShareHandler.doResultIntent(intent, new SelfWbAuthListener(mActivity));
        }
    }
}
