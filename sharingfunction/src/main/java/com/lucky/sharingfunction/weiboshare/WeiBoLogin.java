package com.lucky.sharingfunction.weiboshare;

import android.app.Activity;
import android.content.Intent;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

public class WeiBoLogin {
    String APP_ID = "";
    private static WeiBoLogin instance;
    private static Activity mActivity;
    private SsoHandler ssoHandler;

    public static WeiBoLogin getInstance(Activity activity) {
        mActivity = activity;
        if (instance == null) {
            synchronized (WeiBoLogin.class) {
                if (instance == null) {
                    instance = new WeiBoLogin();
                }
            }
        }
        return instance;
    }

    public WeiBoLogin init() {
        WbSdk.install(mActivity, new AuthInfo(mActivity, "appkey", "redirectUrl", "scope"));
        ssoHandler = new SsoHandler(mActivity);
        return this;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

}
