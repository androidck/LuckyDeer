package com.dykj.requestcore.util;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;


/**
 * 权限申请
 *
 * @author wangxiangyi
 * @Create 2018/8/16.
 */
public class PermissionsUtils {
    private static PermissionsUtils instance;
    private static Context mContext;

    public static PermissionsUtils getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            synchronized (PermissionsUtils.class) {
                if (instance == null) {
                    instance = new PermissionsUtils();
                }
            }
        }
        return instance;
    }

    @SuppressLint("CheckResult")
    public Observable<Boolean> setPermissionsRequest(String... permissions) {
        return new RxPermissions((FragmentActivity) mContext).request(permissions);
    }

    /**
     * 打开设置界面
     */
    public void openSettingDetail() {
        new QMUIDialog.MessageDialogBuilder(mContext)
                .setTitle("提示")
                .setMessage("app需要开启写存储的权限才能使用此功能")
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction(0, "设置", QMUIDialogAction.ACTION_PROP_NEGATIVE, (dialog, index) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + mContext.getPackageName()));
                    mContext.startActivity(intent);
                    dialog.dismiss();
                })
                .show();
    }
}
