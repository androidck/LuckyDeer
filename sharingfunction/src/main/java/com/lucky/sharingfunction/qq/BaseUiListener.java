package com.lucky.sharingfunction.qq;

import android.content.Context;

import com.google.gson.Gson;
import com.lucky.sharingfunction.qq.entity.LoginInfoEntity;
import com.lucky.sharingfunction.qq.entity.QQUserInfoEntity;
import com.lucky.sharingfunction.util.ToastUtil;
import com.orhanobut.logger.Logger;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;


/**
 * QQ分享返回监听
 *
 * @author wangxinagyi
 * @date 2018/09/27
 */
public class BaseUiListener implements IUiListener {
    private final Context mContext;
    private final Tencent mTencent;
    private final int mFlag;
    private boolean boo = false;

    /**
     * @param context 上下文
     * @param flag    1：登录、2：分享
     */
    public BaseUiListener(Context context, Tencent tencent, int flag) {
        this.mContext = context;
        this.mTencent = tencent;
        this.mFlag = flag;
    }

    public BaseUiListener(Context context, Tencent tencent, int flag, boolean boo) {
        this.mContext = context;
        this.mTencent = tencent;
        this.mFlag = flag;
        this.boo = boo;
    }


    @Override
    public void onComplete(Object o) {
        switch (mFlag) {
            /*登陆*/
            case 1:
                if (boo) {
                    Logger.i("登陆信息QQUserInfo：" + new Gson().toJson(o));
                    QQUserInfoEntity userInfo = new Gson().fromJson(String.valueOf(o), QQUserInfoEntity.class);
                } else {
                    Logger.i("登陆信息：" + new Gson().toJson(o));
                    LoginInfoEntity logininfo = new Gson().fromJson(o.toString(), LoginInfoEntity.class);
                    mTencent.setOpenId(logininfo.getOpenid());
                    mTencent.setAccessToken(logininfo.getAccess_token(), logininfo.getExpires_in());
                    UserInfo userInfo = new UserInfo(mContext, mTencent.getQQToken());
                 //   userInfo.getUserInfo(new BaseUiListener(mContext, mTencent, 1, true));
                   // userInfo.getUserInfo(new QqFeatures.ShareUiListener(mContext, mTencent));
                }
                break;
            /*分享*/
            case 2:
                ToastUtil.toastShow(mContext, "分享成功");
                break;
            default:
        }
    }

    @Override
    public void onError(UiError uiError) {
        switch (mFlag) {
            /*登录*/
            case 1:
                ToastUtil.toastShow(mContext, "登录失败");
                break;
            /*分享*/
            case 2:
                ToastUtil.toastShow(mContext, "分享失败");
                break;
            default:
        }
        Logger.e(new Throwable(uiError.errorCode + "：" + uiError.errorMessage + "：" + uiError.errorDetail),
                "QQ失败信息");
    }

    @Override
    public void onCancel() {
        switch (mFlag) {
            /*登录*/
            case 1:
                ToastUtil.toastShow(mContext, "取消登录");
                break;
            /*分享*/
            case 2:
                ToastUtil.toastShow(mContext, "分享成功");
                break;
            default:
        }

    }
}
