package com.lucky.sharingfunction.ddshare;

import android.content.Context;

import com.android.dingtalk.share.ddsharemodule.IDDAPIEventHandler;
import com.android.dingtalk.share.ddsharemodule.ShareConstant;
import com.android.dingtalk.share.ddsharemodule.message.BaseReq;
import com.android.dingtalk.share.ddsharemodule.message.BaseResp;
import com.android.dingtalk.share.ddsharemodule.message.SendAuth;
import com.lucky.sharingfunction.util.ToastUtil;
import com.orhanobut.logger.Logger;

/**
 * 钉钉分享监听
 *
 * @author wangxiangyi
 * @date 2018/09/28
 */
public class IDDAPIEventListener implements IDDAPIEventHandler {
    private final Context context;

    public IDDAPIEventListener(Context context) {
        this.context = context;
    }


    @Override
    public void onReq(BaseReq baseReq) {
        Logger.d(baseReq.mTransaction + "\n" + baseReq.getSupportVersion() + "\n" + baseReq.checkArgs());
    }

    @Override
    public void onResp(BaseResp baseResp) {
        int errCode = baseResp.mErrCode;
        Logger.e(new Throwable("错误编码：" + errCode + "：" + baseResp.mErrStr), "钉钉异常信息");
        if (baseResp.getType() == ShareConstant.COMMAND_SENDAUTH_V2 && (baseResp instanceof SendAuth.Resp)) {
            SendAuth.Resp authResp = (SendAuth.Resp) baseResp;
            switch (errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    ToastUtil.toastShow(context, "授权成功，授权码为:" + authResp.code);
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    ToastUtil.toastShow(context, "授权取消");
                    break;
                default:
                    ToastUtil.toastShow(context, "授权异常");
                    break;
            }
        } else {
            switch (errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    ToastUtil.toastShow(context, "钉钉分享成功");
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    ToastUtil.toastShow(context, "钉钉分享取消");
                    break;
                default:
                    ToastUtil.toastShow(context, "钉钉分享失败");
                    break;
            }
        }
    }
}
