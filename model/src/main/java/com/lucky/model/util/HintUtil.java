package com.lucky.model.util;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lucky.model.R;

/**
 * @author wangxiangyi
 * @date 2018/09/17
 */
public final class HintUtil {
    private HintUtil() {
    }

    private static Toast toast;

    /**
     * 显示错误
     */
    public static void showError(Context context, TextView tipView, String tip, EditText editText) {
        showError(context, tipView, -1, tip, editText, true);
    }

    /**
     * 显示错误
     */
    public static void showError(Context context, TextView tip, int resId, EditText editText) {
        showError(context, tip, resId, null, editText, true);
    }

    /**
     * 显示错误
     */
    public static void showError(Context context, TextView tip, EditText editText) {
        showError(context, tip, -1, null, editText, true);
    }

    /**
     * 显示错误
     */
    public static void showError(Context context, TextView tip) {
        showError(context, tip, -1, null, null, true);
    }


    /**
     * 显示错误
     */
    public static <T extends EditText> void showError(Context context, T editText, String errorTip) {
        if (null != editText) {
            hiddenSoftKeyboard(context, editText);
        }

        if (null != editText && !TextUtils.isEmpty(errorTip)) {
            editText.setError(errorTip);
            editText.requestFocus();
        }
        if (null != editText) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(editText, "translationX", 0f, 10f, 0f).setDuration(100);
            animator.setRepeatCount(3);
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.start();
        }
    }

    /**
     * 显示错误
     */
    public static <T extends TextView> void showError(Context context, T editText, int errorTip) {
        if (null != editText) {
            hiddenSoftKeyboard(context, editText);
        }

        if (null != editText && errorTip > 0) {
            editText.setError(context.getResources().getString(errorTip));
            editText.requestFocus();
        }
        if (null != editText) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(editText, "translationX", 0f, 10f, 0f).setDuration(100);
            animator.setRepeatCount(3);
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.start();
        }
    }

    /**
     * 显示错误
     */
    public static void showErrorWithToast(Context context, String tip) {
        showErrorWithToast(context, tip, null);
    }

    @SuppressLint("ShowToast")
    private static void checkToast(Context context, String tip, EditText editText, int duration) {
        if (TextUtils.isEmpty(tip)) {
            throw new RuntimeException("tip is null !!!");
        }
        if (null == toast) {
            toast = Toast.makeText(context, tip, Toast.LENGTH_SHORT);
        }

        toast.setDuration(duration < 0 ? Toast.LENGTH_SHORT : duration);
        /*这是弹出位置*/
        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.setText(tip);
        toast.setView(getView(context, tip));
        toast.show();
        if (null != editText) {
            hiddenSoftKeyboard(context, editText);
        }
    }

    private static View getView(Context context, String tip) {
        //获取LayoutInflater对象
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //获得屏幕的宽度
        int width = wm.getDefaultDisplay().getWidth();
        //由layout文件创建一个View对象
        View view = inflater.inflate(R.layout.layout_bottom_toast, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = 16;
        TextView toastTextView = (TextView) view.findViewById(R.id.tv_toast);
        //设置TextView的宽度为 屏幕宽度
        toastTextView.setLayoutParams(layoutParams);
        toastTextView.setText(tip);
        return view;
    }

    /**
     * 显示错误
     */
    private static void showErrorWithToast(Context context, String tip, EditText editText) {
        checkToast(context, tip, editText, Toast.LENGTH_SHORT);
    }


    /**
     * 显示错误
     */
    public static void showErrorWithToast(Context context, String tip, int duration) {
        checkToast(context, tip, null, duration);
    }

    /**
     * 显示错误
     */
    public static void showErrorWithToast(Context context, Integer resId) {
        checkToast(context, context.getResources().getString(resId), null, -1);
    }

    /**
     * @param context
     * @param tipView
     * @param tip
     * @param resId
     * @param editText
     * @param shake
     */
    public static void showError(Context context, TextView tipView, int resId, String tip, EditText editText, boolean shake) {
        if (null != editText) {
            hiddenSoftKeyboard(context, editText);
        }
        if (resId != -1) {
            tipView.setText(resId);
        }
        if (!TextUtils.isEmpty(tip)) {
            tipView.setText(tip);
        }
        tipView.setVisibility(View.VISIBLE);
        if (shake) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(tipView, "translationX", 0f, 10f, 0f).setDuration(100);
            animator.setRepeatCount(3);
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.start();
        }

    }

    /**
     * 隐藏键盘
     */
    public static void hiddenSoftKeyboard(Context context, TextView editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 隐藏键盘
     *
     * @param activity
     */
    public static void hideSoftInputFromWindow(Activity activity) {
        ((InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(
                        activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS
                );
    }

    /**
     * 获取焦点并显示软键盘
     *
     * @param activity 上下文
     * @param editText 输入框View
     */
    public static void showSoftInputFromInputMethod(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        //显示软键盘
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        //如果上面的代码没有弹出软键盘 可以使用下面另一种方式
        //InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        // imm.showSoftInput(editText, 0);
    }
}
