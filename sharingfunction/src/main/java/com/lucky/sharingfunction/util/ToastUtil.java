package com.lucky.sharingfunction.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 公共Toast弹出框
 */
public class ToastUtil {
    private static Toast toast;

    @SuppressLint("ShowToast")
    public static void toastShow(Context context, int textID) {
        toastShow(context, context.getString(textID));
    }

    @SuppressLint("ShowToast")
    public static void toastShow(Context context, String text) {
        if (TextUtils.isEmpty(text)) {
            throw new RuntimeException("tip is null !!!");
        }
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

}
