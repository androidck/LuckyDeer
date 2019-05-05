package com.dykj.requestcore.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 系统工具类
 *
 * @author wangxiangyi
 * @Create 2018/8/16.
 */
public class SystemUtil {
    private static SystemUtil instance;

    public static SystemUtil getInstance() {
        if (instance == null) {
            synchronized (SystemUtil.class) {
                if (instance == null) {
                    instance = new SystemUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 获取设备序列号
     *
     * @param ctx
     * @return
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public String getGenerateIMEI(Context ctx) {
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }

        String code = null;
        try {
            TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
            code = tm.getDeviceId();
            if (code == null) {
                // android pad
                code = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
            if (!TextUtils.isEmpty(code)) {
                code = MD5(code);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return code;
    }

    /**
     * 获取16位序列号
     *
     * @param sourceStr
     * @return
     */
    private String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取时间戳
     */
    public long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public String getModel() {
        return Build.MODEL;
    }
}
