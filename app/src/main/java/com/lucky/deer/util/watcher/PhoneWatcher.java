package com.lucky.deer.util.watcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 输入手机号码时，自动添加空格
 *
 * @author wangxiangyi
 * @Create 2018/4/7.
 */
public class PhoneWatcher implements TextWatcher {
    private EditText mText;

    public PhoneWatcher(EditText text) {
        this.mText = text;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s == null || s.length() == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                continue;
            } else {
                sb.append(s.charAt(i));
                if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                    sb.insert(sb.length() - 1, ' ');
                }
            }
        }
        if (!sb.toString().equals(s.toString())) {
            int index = start + 1;
            if (sb.charAt(start) == ' ') {
                if (before == 0) {
                    index++;
                } else {
                    index--;
                }
            } else {
                if (before == 1) {
                    index--;
                }
            }
            mText.setText(sb.toString());
            mText.setSelection(index);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}