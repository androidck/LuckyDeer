package com.lucky.sharingfunction.weiboshare;

import android.content.Context;

import com.lucky.sharingfunction.util.ToastUtil;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.share.WbShareCallback;

/**
 * 微博分享监听器
 *
 * @author wangxingyi
 * @date 2018/09/28
 */
public class SelfWbAuthListener implements WbAuthListener, WbShareCallback {
    private final Context context;

    public SelfWbAuthListener(Context context) {
        this.context = context;
    }

    /*登陆返回监听*/
    @Override
    public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
    }

    @Override
    public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
    }

    @Override
    public void cancel() {
    }
    /*微博分享*/

    @Override
    public void onWbShareSuccess() {
        ToastUtil.toastShow(context, "分享成功");
    }

    @Override
    public void onWbShareFail() {
        ToastUtil.toastShow(context, "分享失败");
    }

    @Override
    public void onWbShareCancel() {
        ToastUtil.toastShow(context, "取消分享");
    }
}
