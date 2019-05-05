package com.lucky.sharingfunction.alipayshare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.alipay.share.sdk.openapi.APAPIFactory;
import com.alipay.share.sdk.openapi.APMediaMessage;
import com.alipay.share.sdk.openapi.APTextObject;
import com.alipay.share.sdk.openapi.APWebPageObject;
import com.alipay.share.sdk.openapi.IAPApi;
import com.alipay.share.sdk.openapi.SendMessageToZFB;
import com.lucky.sharingfunction.R;
import com.lucky.sharingfunction.constant.KeyConstant;

/**
 * 支付宝分享功能
 * *
 *
 * @author wangxiangyi
 * @date 2018/09/29
 */
public class AlipayShare {


    private static AlipayShare instance;
    private static Context mContext;

    private static IAPApi zfbApi;

    public AlipayShare() {
        init();
    }

    public static AlipayShare getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            synchronized (AlipayShare.class) {
                if (instance == null) {
                    instance = new AlipayShare();
                }
            }
        }
        init();
        return instance;
    }

    private static void init() {
        if (zfbApi == null) {
            zfbApi = APAPIFactory.createZFBApi(mContext, KeyConstant.APP_ID_ALIPAY, false);
        }
    }

    /**
     * 网页分享
     *
     * @param title      标题
     * @param desc       描述
     * @param imgUrl     图片地址
     * @param webpageUrl 分享内容地址
     */
    public void shareWebPageMessage(String title, String desc, String imgUrl, String webpageUrl) {
//        APWebPageObject webPageObject = new APWebPageObject();
//        webPageObject.webpageUrl = webpageUrl;
//        APMediaMessage webMessage = new APMediaMessage();
//        webMessage.title = title;
//        webMessage.description = desc;
//        webMessage.mediaObject = webPageObject;
//        webMessage.thumbUrl = imgUrl;
//        SendMessageToZFB.Req webReq = new SendMessageToZFB.Req();
//        webReq.message = webMessage;
//        webReq.transaction = buildTransaction("webpage");
//        //在支付宝版本会合并分享渠道的情况下,不需要传递分享场景参数
//        webReq.scene = SendMessageToZFB.Req.ZFBSceneSession;
////                    ? SendMessageToZFB.Req.ZFBSceneTimeLine
////                    : SendMessageToZFB.Req.ZFBSceneSession;
//
//        zfbApi.sendReq(webReq);

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_web_page, null);
        final EditText mtitle = (EditText) view.findViewById(R.id.title);
        final EditText mdesc = (EditText) view.findViewById(R.id.desc);
        final EditText thumbUrl = (EditText) view.findViewById(R.id.thumb_url);
        final EditText url = (EditText) view.findViewById(R.id.url);
        final CheckBox timelineChecked = (CheckBox) view.findViewById(R.id.timelineChecked);
        //在支付宝版本会合并分享渠道的情况下,没必要露出选择分享渠道的选项
        if (!isAlipayIgnoreChannel()) {
            timelineChecked.setVisibility(View.VISIBLE);
        } else {
            timelineChecked.setVisibility(View.GONE);
        }
        new AlertDialog.Builder(mContext).setTitle("分享网页").setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        APWebPageObject webPageObject = new APWebPageObject();
                        webPageObject.webpageUrl = url.getText().toString();
                        APMediaMessage webMessage = new APMediaMessage();
                        webMessage.title = mtitle.getText().toString();
                        webMessage.description = mdesc.getText().toString();
                        webMessage.mediaObject = webPageObject;
                        webMessage.thumbUrl = thumbUrl.getText().toString();
                        SendMessageToZFB.Req webReq = new SendMessageToZFB.Req();
                        webReq.message = webMessage;
                        webReq.transaction = buildTransaction("webpage");

                        //在支付宝版本会合并分享渠道的情况下,不需要传递分享场景参数
                        if (!isAlipayIgnoreChannel()) {
                            webReq.scene = timelineChecked.isChecked()
                                    ? SendMessageToZFB.Req.ZFBSceneTimeLine
                                    : SendMessageToZFB.Req.ZFBSceneSession;

                        }
                        zfbApi.sendReq(webReq);
                    }
                }).setNegativeButton("取消", null).show();
    }

    /**
     * 文本信息分享
     *
     * @param text 分享文本内容
     */
    public void shareTextMessage(String text) {
        //初始化一个APTextObject对象
        APTextObject textObject = new APTextObject();
        textObject.text = text;
        //用APTextObject对象初始化一个APMediaMessage对象
        APMediaMessage mediaMessage = new APMediaMessage();
        mediaMessage.mediaObject = textObject;
        //构造一个Req
        SendMessageToZFB.Req req = new SendMessageToZFB.Req();
        req.message = mediaMessage;
        //调用api接口发送消息到支付宝
        zfbApi.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private boolean isAlipayIgnoreChannel() {
        return zfbApi.getZFBVersionCode() >= 101;
    }

    /**
     * 设置回掉
     *
     * @param intent
     */
    public void onNewIntent(Intent intent) {
        if (zfbApi != null && intent != null) {
            zfbApi.handleIntent(intent, new AlipayApiEventListener(mContext));
        }
    }
}
