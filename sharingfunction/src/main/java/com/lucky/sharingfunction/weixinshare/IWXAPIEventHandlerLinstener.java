package com.lucky.sharingfunction.weixinshare;

import android.content.Context;

import com.google.gson.Gson;
import com.lucky.sharingfunction.util.ToastUtil;
import com.lucky.sharingfunction.weixinshare.entity.WeiXinEntity;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class IWXAPIEventHandlerLinstener implements IWXAPIEventHandler {
    private final Context context;
    private final int mFiags;
    private final WeiXinListener mListener;

    /**
     * @param context 上下文
     * @param fiags   标识 1：登录；2：分享
     */
    public IWXAPIEventHandlerLinstener(Context context, int fiags, WeiXinListener listener) {
        this.context = context;
        this.mFiags = fiags;
        this.mListener = listener;
    }


    @Override
    public void onReq(BaseReq baseReq) {
        Logger.i("onReq微信信息：" + new Gson().toJson(baseReq));
    }

    @Override
    public void onResp(BaseResp baseResp) {
        int errCode = baseResp.errCode;
        Logger.e(new Throwable("错误编码：" + errCode + "：" + baseResp.errStr), "微信异常信息");
        WeiXinEntity weiXinEntity = new WeiXinEntity();
        switch (mFiags) {
            case 1:
                weiXinEntity.setOpenId(baseResp.openId);
                weiXinEntity.setOpenId(baseResp.transaction);
                switch (errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        weiXinEntity.setCode(1);
                        weiXinEntity.setStr("登录成功");
                        mListener.onWeiXinData(weiXinEntity);
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        weiXinEntity.setCode(2);
                        weiXinEntity.setStr("取消登录");
                        mListener.onWeiXinData(weiXinEntity);
                        break;
                    case BaseResp.ErrCode.ERR_AUTH_DENIED:
                        weiXinEntity.setCode(3);
                        weiXinEntity.setStr("登录被拒绝");
                        mListener.onWeiXinData(weiXinEntity);
                        break;
                    default:
                        weiXinEntity.setCode(0);
                        weiXinEntity.setStr("登录返回");
                        break;
                }
                Logger.i("onResp微信信息：" + new Gson().toJson(baseResp));
                break;
            case 2:
                switch (errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        ToastUtil.toastShow(context, "分享成功");
                        weiXinEntity.setCode(1);
                        weiXinEntity.setStr("分享成功");
                        mListener.onWeiXinData(weiXinEntity);
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        ToastUtil.toastShow(context, "分享取消");
                        weiXinEntity.setCode(2);
                        weiXinEntity.setStr("分享取消");
                        mListener.onWeiXinData(weiXinEntity);
                        break;
                    default:
                        ToastUtil.toastShow(context, "分享失败");
                        weiXinEntity.setCode(0);
                        weiXinEntity.setStr("分享失败");
                        mListener.onWeiXinData(weiXinEntity);
                        break;
                }
                break;
            default:
        }
    }
}
