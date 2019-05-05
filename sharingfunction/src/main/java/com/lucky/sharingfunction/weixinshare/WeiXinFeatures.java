package com.lucky.sharingfunction.weixinshare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.lucky.sharingfunction.R;
import com.lucky.sharingfunction.constant.KeyConstant;
import com.lucky.sharingfunction.util.ToastUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信分享
 *
 * @author wangxinagyi
 * @date 2018/09/27
 */
public class WeiXinFeatures {
    private static WeiXinFeatures instance;
    private static Activity mActivity;
    private static IWXAPI api;
    /**
     * 适用类型<p>
     * 1：微信登录
     * 2：网页分享
     * 3：图片分享
     */
    private int flags;


    public WeiXinFeatures() {
        init();
    }

    public static WeiXinFeatures getInstance(Activity activity) {
        mActivity = activity;
        if (instance == null) {
            synchronized (WeiXinFeatures.class) {
                if (instance == null) {
                    instance = new WeiXinFeatures();
                }
            }
        }
        init();
        return instance;
    }

    private static void init() {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(mActivity.getApplicationContext(), KeyConstant.APP_ID_WE_CHAT, true);
            api.registerApp(KeyConstant.APP_ID_WE_CHAT);
        }
    }

    /**
     * 微信授权登录
     */
    public WeiXinFeatures doLogin() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = KeyConstant.SCOPE_WE_CHAT;
        req.state = KeyConstant.STATE_WE_CHAT;
        if (api != null) {
            api.sendReq(req);
            flags = 1;
        }
        return this;
    }

    /**
     * 分享图片到微信、朋友圈
     *
     * @param title     标题
     * @param info      分享内容
     * @param bitmapImg 图片
     * @param pathImg   本地分享图片路径
     * @param flag      1是朋友圈，0是好友，
     */
    public WeiXinFeatures shareImgToWechat(String title, String info, Bitmap bitmapImg, String pathImg, int flag) {
        if (!api.isWXAppInstalled()) {
            ToastUtil.toastShow(mActivity, R.string.no_weixin);

        } else {
            WXImageObject imgObj;
            if (TextUtils.isEmpty(pathImg)) {
                imgObj = new WXImageObject(bitmapImg);
            } else {
                imgObj = new WXImageObject();
                imgObj.setImagePath(pathImg);
                Bitmap bmp = BitmapFactory.decodeFile(pathImg);
                bmp.recycle();
            }
            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = imgObj;
            msg.description = info;
            msg.title = title;
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = "img" + String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = flag;
            api.sendReq(req);
            flags = 3;
        }
        return this;
    }


    /**
     * 分享网址到微信、朋友圈
     *
     * @param title 标题
     * @param info  分享内容
     * @param url   分享地址url
     * @param flag  1是朋友圈，0是好友，
     */
    public WeiXinFeatures shareToWechat(String title, String info, String url, int flag) {
        if (!api.isWXAppInstalled()) {
            ToastUtil.toastShow(mActivity, R.string.no_weixin);
        } else {
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = url;
            WXMediaMessage msg = new WXMediaMessage(webpage);

            msg.title = title;
            msg.description = info;
            Bitmap thumb = BitmapFactory.decodeResource(mActivity.getResources(),
                    R.mipmap.ic_launcher);
            msg.setThumbImage(thumb);
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = flag;
            api.sendReq(req);
            flags = 2;
        }
        return this;
    }


    /**
     * 设置回掉
     *
     * @param intent
     */
    public void onNewIntent(Intent intent) {
        onNewIntent(intent, null);
    }

    /**
     * 设置回掉
     *
     * @param intent
     */
    public void onNewIntent(Intent intent, WeiXinListener listener) {
        if (api != null) {
            api.handleIntent(intent, new IWXAPIEventHandlerLinstener(mActivity, flags, listener));
        }
    }
}
