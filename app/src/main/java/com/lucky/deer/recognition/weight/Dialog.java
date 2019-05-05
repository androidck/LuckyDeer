package com.lucky.deer.recognition.weight;

import android.content.Context;
import android.support.v7.app.AlertDialog;


public class Dialog {
    private static Dialog instance;
    private static Context mContext;
    private AlertDialog.Builder alertDialog;

    public static Dialog getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            synchronized (Dialog.class) {
                if (instance == null) {
                    instance = new Dialog();
                }
            }
        }
        return instance;
    }

    public void infoPopText(final String result) {
        alertText("", result);
    }

    public void alertText(final String title, final String message) {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(mContext);
        }
        alertDialog.setTitle(title)
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();
    }
}
