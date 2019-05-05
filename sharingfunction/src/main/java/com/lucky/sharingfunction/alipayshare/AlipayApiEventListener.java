package com.lucky.sharingfunction.alipayshare;

import android.content.Context;

import com.alipay.share.sdk.openapi.BaseReq;
import com.alipay.share.sdk.openapi.BaseResp;
import com.alipay.share.sdk.openapi.IAPAPIEventHandler;
import com.lucky.sharingfunction.util.ToastUtil;
import com.orhanobut.logger.Logger;

/**
 * 支付宝分享回掉方法
 *
 * @author wangxiangyi
 * @date 2018/09/29
 */
public class AlipayApiEventListener implements IAPAPIEventHandler {
    private final Context mContext;

    public AlipayApiEventListener(Context context) {
        mContext = context;
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        int errCode = baseResp.errCode;
        Logger.e(new Throwable("错误编码：" + errCode + "：" + baseResp.errStr), "支付宝分享信息");
        switch (errCode) {
            case BaseResp.ErrCode.ERR_OK:
                ToastUtil.toastShow(mContext, "分享成功");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                ToastUtil.toastShow(mContext, "分享取消");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                ToastUtil.toastShow(mContext, "分享被拒绝");
                break;
            default:
                ToastUtil.toastShow(mContext, "分享失败");
                break;
        }
    }
}
