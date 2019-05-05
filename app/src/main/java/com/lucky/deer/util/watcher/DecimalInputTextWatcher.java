package com.lucky.deer.util.watcher;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;

/**
 * 限制小数和整数各自的长度
 *
 * @author wangxiangyi
 * @Create 2018/4/7.
 */
public class DecimalInputTextWatcher implements TextWatcher {
    /**
     * 需要设置该 DecimalInputTextWatcher 的 View
     */
    private View view = null;

    /**
     * 默认  小数的位数   2 位
     */
    private static final int DEFAULT_DECIMAL_DIGITS = 2;
    /**
     * 小数的位数
     */
    private int decimalDigits;
    /**
     * 整数的位数
     */
    private int integerDigits;

    /**
     * 最大数
     * 该数的值必须大于0
     * */
    private String maxStr;

    /**
     * 默认限制两位小数
     *
     * @param view
     */
    public DecimalInputTextWatcher(View view) {
        this.view = view;
        this.decimalDigits = DEFAULT_DECIMAL_DIGITS;
    }

    /**
     * 限制小数位数
     *
     * @param view          view
     * @param decimalDigits 小数的位数
     */
    public DecimalInputTextWatcher(View view, int decimalDigits) {
        this.view = view;
        if (decimalDigits <= 0) {
            throw new RuntimeException("decimalDigits must > 0");
        }
        this.decimalDigits = decimalDigits;
    }

    /**
     * 显示小数和整数各自的长度
     *
     * @param view
     * @param integerDigits 整数的位数
     * @param decimalDigits 小数的位数
     */
    public DecimalInputTextWatcher(View view, int integerDigits, int decimalDigits) {
        this.view = view;
        if (integerDigits <= 0) {
            throw new RuntimeException("integerDigits must > 0");
        }
        if (decimalDigits <= 0) {
            throw new RuntimeException("decimalDigits must > 0");
        }
        this.integerDigits = integerDigits;
        this.decimalDigits = decimalDigits;
    }

    /**
     * 限制小数位数，且有最大值限制
     * @param view          view
     * @param decimalDigits 小数的位数
     * @param maxStr 最大值限制
     */
    public DecimalInputTextWatcher(View view, int decimalDigits, String maxStr) {
        this.view = view;
        this.maxStr = maxStr;
        if (decimalDigits <= 0) {
            throw new RuntimeException("decimalDigits must > 0");
        }
        this.decimalDigits = decimalDigits;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String s = editable.toString();
        if (view instanceof TextView) {
            ((TextView) view).removeTextChangedListener(this);
        } else if (view instanceof EditText) {
            ((EditText) view).removeTextChangedListener(this);
        }

        if (s.contains(".")) {
            if (integerDigits > 0) {
                if (view instanceof TextView) {
                    ((TextView) view).setFilters(new InputFilter[]{new InputFilter.LengthFilter(integerDigits + decimalDigits + 1)});
                } else if (view instanceof EditText) {
                    ((EditText) view).setFilters(new InputFilter[]{new InputFilter.LengthFilter(integerDigits + decimalDigits + 1)});
                }
            }
            if (s.length() - 1 - s.indexOf(".") > decimalDigits) {
                s = s.substring(0,
                        s.indexOf(".") + decimalDigits + 1);
                editable.replace(0, editable.length(), s.trim());
            }
        } else {
            if (integerDigits > 0) {
                if (view instanceof TextView) {
                    ((TextView) view).setFilters(new InputFilter[]{new InputFilter.LengthFilter(integerDigits + 1)});
                } else if (view instanceof EditText) {
                    ((EditText) view).setFilters(new InputFilter[]{new InputFilter.LengthFilter(integerDigits + 1)});
                }
                if (s.length() > integerDigits) {
                    s = s.substring(0, integerDigits);
                    editable.replace(0, editable.length(), s.trim());
                }
            }

        }
        if (s.trim().equals(".")) {
            s = "0" + s;
            editable.replace(0, editable.length(), s.trim());
        }
        if (s.startsWith("0")
                && s.trim().length() > 1) {
            if (!s.substring(1, 2).equals(".")) {
                editable.replace(0, editable.length(), "0");
            }
        }

        if ((!TextUtils.isEmpty(maxStr)) && (!TextUtils.isEmpty(editable.toString()))) {
            //有最大值限制
            BigDecimal bigDecimalValue = new BigDecimal(editable.toString());
            BigDecimal bigDecimalMax = new BigDecimal(maxStr);
            BigDecimal bigDecimalZero = new BigDecimal("0");
            if (bigDecimalMax.compareTo(bigDecimalZero)==1) {
                //限制的值必须大于0
                if (bigDecimalValue.compareTo(bigDecimalMax)==1) {
                    //文本框中的值大于最大值
                    String str = editable.toString();
                    str = str.substring(0,str.length()-1);
                    editable.replace(0, editable.length(), str.trim());
                }
            }
        }

        if (view instanceof TextView) {
            ((TextView) view).addTextChangedListener(this);
        } else if (view instanceof EditText) {
            ((EditText) view).addTextChangedListener(this);
        }
    }

}