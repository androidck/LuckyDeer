package com.lucky.deer.find.business.view.util;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class AppUtil {

    /**
     * 关闭该控件的软键盘
     * */
    public static void closeKeyBoard(Context ctx, EditText editText) {
        try {
            InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏软键盘
     * */
    public static void hideSoftKeyboard(Activity act){
        InputMethodManager manager = (InputMethodManager) act.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View focus = act.getCurrentFocus();
        manager.hideSoftInputFromWindow(
                focus == null ? null : focus.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void hideSoftKeyboard(IBinder token, Context context){
        InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
