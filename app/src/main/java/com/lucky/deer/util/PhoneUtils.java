package com.lucky.deer.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.dykj.requestcore.util.PermissionsUtils;

/**
 * 打电话功能
 *
 * @author wangxiangyi
 * @date 2018/10/29
 */
public class PhoneUtils {
    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public static void callPhone(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNum));
        context.startActivity(intent);
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    @SuppressLint({"MissingPermission", "CheckResult"})
    public static void callPhoneDirectly(Context context, String phoneNum) {
        PermissionsUtils
                .getInstance(context)
                .setPermissionsRequest(Manifest.permission.CALL_PHONE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + phoneNum));
                        context.startActivity(intent);
                    } else {
                        PermissionsUtils.getInstance(context).openSettingDetail();
                    }
                });
    }

}
