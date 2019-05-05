package com.lucky.sharingfunction.ddshare;

import android.content.Context;
import android.content.Intent;

import com.android.dingtalk.share.ddsharemodule.DDShareApiFactory;
import com.android.dingtalk.share.ddsharemodule.IDDShareApi;
import com.android.dingtalk.share.ddsharemodule.message.DDMediaMessage;
import com.android.dingtalk.share.ddsharemodule.message.DDWebpageMessage;
import com.android.dingtalk.share.ddsharemodule.message.SendMessageToDD;
import com.lucky.sharingfunction.constant.KeyConstant;
import com.lucky.sharingfunction.util.ToastUtil;
import com.orhanobut.logger.Logger;

/**
 * QQ分享功能
 *
 * @author wangxingyi
 * @date 2018/09/27
 */
public class DingDingShare {


    private static DingDingShare instance;
    private static Context mContext;

    private static IDDShareApi ddShareApi;

    public DingDingShare() {
        init();
    }

    public static DingDingShare getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            synchronized (DingDingShare.class) {
                if (instance == null) {
                    instance = new DingDingShare();
                }
            }
        }
        init();
        return instance;
    }

    private static void init() {
        if (ddShareApi == null) {
            try {
                ddShareApi = DDShareApiFactory.createDDShareApi(mContext, KeyConstant.APP_ID_NAIL, false);
            } catch (Exception e) {
                e.printStackTrace();
                Logger.e(new Throwable(e), "初始化异常");
            }
        }
    }

    /**
     * 分享网页消息
     *
     * @param title      分享标题
     * @param desc       分享内容描述
     * @param imgUrl     分享图片
     * @param webpageUrl 分享的网页url
     */
    public void sendWebPageMessage(String title, String desc, String imgUrl, String webpageUrl, boolean isSendDing) {
        //初始化一个DDWebpageMessage并填充网页链接地址
        DDWebpageMessage webPageObject = new DDWebpageMessage();
        webPageObject.mUrl = webpageUrl;
        //构造一个DDMediaMessage对象
        DDMediaMessage webMessage = new DDMediaMessage();
        webMessage.mMediaObject = webPageObject;

        //填充网页分享必需参数，开发者需按照自己的数据进行填充
        webMessage.mTitle = title;
        webMessage.mContent = desc;
        webMessage.mThumbUrl = imgUrl;
        //构造一个Req
        SendMessageToDD.Req webReq = new SendMessageToDD.Req();
        webReq.mMediaMessage = webMessage;
        if (!isDDAppInstalled()) {
            ToastUtil.toastShow(mContext, "请安装钉钉app");
            return;
        } else if (!isDDSupportAPI()) {
            ToastUtil.toastShow(mContext, "该设备不支持钉钉分享功能");
            return;
        }
        //调用api接口发送消息到支付宝
        if (isSendDing) {
            ddShareApi.sendReqToDing(webReq);
        } else {
            ddShareApi.sendReq(webReq);
        }
    }

    /**
     * 设置回掉
     *
     * @param intent
     */
    public void onNewIntent(Intent intent) {
        if (ddShareApi != null && intent != null) {
            ddShareApi.handleIntent(intent, new IDDAPIEventListener(mContext));
        }
    }

    /**
     * 判断钉钉是否安装
     *
     * @return
     */
    public boolean isDDAppInstalled() {
        return ddShareApi.isDDAppInstalled();
    }

    /**
     * 判断当前设备是否支持分享到钉钉
     *
     * @return
     */
    public boolean isDDSupportAPI() {
        return ddShareApi.isDDSupportAPI();
    }
}
