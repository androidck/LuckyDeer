package com.lucky.deer.util;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class KeyboardUtil {
    public interface OnSoftKeyboardChangeListener {
        void onSoftKeyBoardChange(int softKeyboardHeight, boolean visible);
    }

    /**
     * 监听软键盘高度和状态
     */
    public static ViewTreeObserver.OnGlobalLayoutListener observeSoftKeyboard(Activity activity, final OnSoftKeyboardChangeListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            int previousKeyboardHeight = -1;
            Rect rect = new Rect();
            boolean lastVisibleState = false;

            @Override
            public void onGlobalLayout() {
                rect.setEmpty();
                decorView.getWindowVisibleDisplayFrame(rect);
                int displayHeight = rect.bottom - rect.top;
                /*考虑上状态栏的高度*/
                int height = decorView.getHeight() - rect.top;
                int keyboardHeight = height - displayHeight;
                if (previousKeyboardHeight != keyboardHeight) {
                    boolean hide = (double) displayHeight / height > 0.8;
                    if (hide != lastVisibleState) {
                        listener.onSoftKeyBoardChange(keyboardHeight, !hide);
                        lastVisibleState = hide;
                    }
                }
                previousKeyboardHeight = height;
            }
        };
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        return onGlobalLayoutListener;
    }

    public static void removeSoftKeyboardObserver(Activity activity, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (listener == null) {
            return;
        }
        final View decorView = activity.getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            decorView.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        } else {
            decorView.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        }
    }
    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    public static void showInput(Activity activity, final EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏键盘
     */
    public static void hideInput(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        View v = activity.getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
