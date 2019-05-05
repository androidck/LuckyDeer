package com.lucky.deer.util;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.lucky.deer.R;
import com.lucky.deer.util.watcher.BankCardNumAddSpaceWatcher;
import com.lucky.deer.util.watcher.DecimalInputTextWatcher;
import com.lucky.deer.util.watcher.PhoneWatcher;
import com.orhanobut.logger.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


/**
 * 工具类
 *
 * @author wangxiangyi
 * @date 2018/10/12
 */
public class StringUtil {
    @SuppressLint("SimpleDateFormat")
    public static String y_m_d_h_m_s = "yyyy-MM-dd HH:mm:ss";
    public static String ymdhms = "yyyy年MM月dd日 HH:mm:ss";

    private static Integer mRotation = 1;

    /**
     * 更改制定位置文字颜色
     *
     * @param text  更改的文字
     * @param color 要改的颜色
     * @param start 开始位置
     * @param end   结束位置
     * @return 更改后的结果
     */
    @SuppressLint("ResourceAsColor")
    public static String setFontColor(String text, int color, int start, int end) {
        if (!StringUtils.isEmpty(text)) {
            /* 要设置文本的字体颜色*/
            SpannableString sp = new SpannableString(text);
            /*设置要改变颜色的位置*/
            sp.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return sp.toString();
        }
        return "";
    }

    /**
     * 本地图片地址转bitMap类型
     *
     * @param filePath 文件路径
     * @return 更改后的结果
     */
    @SuppressLint("ResourceAsColor")
    public static Bitmap decodeStream(String filePath) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(fis);
    }

    /**
     * 截取卡号中间几位换成****
     *
     * @param number
     * @return
     */
    public static String replaceCardNumber(String number) {
        return replaceNumber(number, 0, 4, " **** **** ");
    }

    /**
     * 截取手机号中间几位换成*
     *
     * @param number
     * @return
     */
    public static String replacePhoneNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return "";
        } else {
            /*获得前四个字符*/
            return number.substring(0, 3) +
                    /*获得中间的部分*/
                    number.substring(3, number.length() - 4).replace(number.substring(3, number.length() - 4), " ***** ") +
                    /*获得最后四个字符*/
                    number.substring(number.length() - 4);
        }
    }

    /**
     * 截取字符串中间几位换成*
     *
     * @param number
     * @return
     */
    public static String replaceNumber(String number, int beginIndex, int endIndex, String replacement) {
        if (TextUtils.isEmpty(number)) {
            return "";
        } else {
            /*获得前四个字符*/
            return number.substring(beginIndex, endIndex) +
                    /*获得中间的部分*/
                    number.substring(endIndex, number.length() - endIndex).replace(number.substring(endIndex, number.length() - endIndex), replacement) +
                    /*获得最后四个字符*/
                    number.substring(number.length() - endIndex);
        }
    }

    /**
     * 截取手机号中间几位换成*
     *
     * @param number
     * @return
     */
    public static String substringData(String number, int beginIndex, int endIndex) {
        if (TextUtils.isEmpty(number)) {
            return number;
        } else if (number.length() < beginIndex) {
            return number;
        }
        return number.substring(beginIndex, endIndex);
    }

    /**
     * 计算刷卡消费手续费
     *
     * @param amount    金额
     * @param rate      费率
     * @param singleFee 单笔收费
     * @return 手续费金额
     */
    public static String getFeeCalculation(String amount, String rate, String singleFee) {
        if (TextUtils.isEmpty(amount) ||
                ".".equals(amount) ||
                TextUtils.isEmpty(rate) ||
                ".".equals(rate) ||
                TextUtils.isEmpty(singleFee) ||
                ".".equals(singleFee)) {
            return "0 元";
        }
        BigDecimal bigDecimalAmount = new BigDecimal(amount);
        BigDecimal bigDecimalRate = new BigDecimal(rate);
        BigDecimal bigDecimalSingleFee = new BigDecimal(singleFee);
        BigDecimal bigDecimal100 = new BigDecimal("100");
        BigDecimal multiply = bigDecimalAmount.multiply(bigDecimalRate);
        BigDecimal divide = multiply.divide(bigDecimal100);
        return divide.add(bigDecimalSingleFee).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "元";
    }


    /**
     * 按标识分割字符传
     *
     * @param data 要分割的字符传
     * @param sign 分割符
     * @return 分割的数量
     */
    public static String[] getSplit(String data, String sign) {
        String[] strings = new String[0];
        if (!TextUtils.isEmpty(data) && !TextUtils.isEmpty(sign)) {
            strings = data.split(sign);
        }
        return strings;
    }

    /**
     * 按标识分割字符传
     *
     * @param data 要分割的字符传
     * @param sign 分割符
     * @return 分割的数量
     */
    public static int getSplitNumber(String data, String sign) {
        if (getSplit(data, sign) != null && getSplit(data, sign).length > 0) {
            return getSplit(data, sign).length;
        } else {
            return 0;
        }
    }

    /**
     * 加法计算
     *
     * @param addend   加数
     * @param beAddend 被加数
     * @return 和
     */
    public static float additionCalculation(String addend, String beAddend) {
        if (TextUtils.isEmpty(addend) && TextUtils.isEmpty(beAddend)) {
            return Float.valueOf("0");
        } else if (TextUtils.isEmpty(addend)) {
            return Float.valueOf(beAddend);
        } else if (TextUtils.isEmpty(beAddend)) {
            return Float.valueOf(addend);
        }
        BigDecimal mAddend = new BigDecimal(addend);
        BigDecimal mBeAddend = new BigDecimal(beAddend);
        return mAddend.add(mBeAddend).floatValue();
    }

    /**
     * 减法计算
     *
     * @param subtracted  被减数
     * @param subtraction 减数
     * @return 差
     */
    public static String getSubtract(String subtracted, String subtraction) {
        if (TextUtils.isEmpty(subtracted)) {
            return subtraction;
        } else if (TextUtils.isEmpty(subtraction)) {
            return subtracted;
        }
        BigDecimal mSubtracted = new BigDecimal(subtracted);
        BigDecimal mSubtraction = new BigDecimal(subtraction);
        mSubtracted.subtract(mSubtraction);
        return mSubtracted.subtract(mSubtraction).toString();
    }

    /**
     * 乘法计算
     *
     * @param beMultiplier 被乘数
     * @param multiplier   乘数
     * @return 积
     */
    public static float multiplicationCalculation(float beMultiplier, float multiplier) {
        BigDecimal mBeMultiplier = new BigDecimal(beMultiplier);
        BigDecimal mMultiplier = new BigDecimal(multiplier);
        return mBeMultiplier.multiply(mMultiplier).floatValue();
    }

    /**
     * 除法计算
     *
     * @param beDivisor 被除数
     * @param divisor   除数
     * @return 商
     */
    public static float divideCalculation(float beDivisor, float divisor) {
        if (divisor <= 0) {
            return beDivisor;
        }
        BigDecimal mBeMultiplier = new BigDecimal(beDivisor);
        BigDecimal mMultiplier = new BigDecimal(divisor);
        return mBeMultiplier.divide(mMultiplier, 6).floatValue();
    }


    /**
     * 计算百分比
     *
     * @param beDivisor 被除数
     * @param divisor   除数
     * @return 百分比
     */
    public static int getPercentage(int beDivisor, int divisor) {
        if (divisor <= 0) {
            return beDivisor;
        }
        BigDecimal mBeDivisor = new BigDecimal(beDivisor);
        BigDecimal mDivisor = new BigDecimal(divisor);
        BigDecimal yibai = new BigDecimal(100);
        return mBeDivisor.divide(mDivisor, 2, BigDecimal.ROUND_HALF_UP).multiply(yibai).intValue();
    }

    /**
     * 比较大小
     *
     * @param v1 比较数1
     * @param v2 比较数2
     * @return true：v1小于或等于v2<p> false：v1大于v2
     */
    public static boolean setComparison(String v1, String v2) {
        BigDecimal money1 = new BigDecimal(v1);
        BigDecimal money2 = new BigDecimal(v2);
        if (money1.compareTo(money2) == -1) {
            /*小于 */
            return true;
        } else if (money1.compareTo(money2) == 0) {
            /*等于 */
            return true;
        } else {
            /*大于*/
            return false;
        }
    }

    /**
     * 比较大小
     *
     * @param v1 比较数1
     * @param v2 比较数2
     * @return 0：v1等于v2<p> -1：v1小于v2<p> 1：v1大于v2
     */
    public static int setComparison1(String v1, String v2) {
        BigDecimal money1 = new BigDecimal(v1);
        BigDecimal money2 = new BigDecimal(v2);
        return money1.compareTo(money2);
    }

    /**
     * 格式化数字小数点位数（默认两位）
     *
     * @param s 需要格式化的数字
     * @return
     */
    public static String setNumberFormatting(String s) {
        return setNumberFormatting(s, 2);
    }

    /**
     * 格式化数字小数点位数
     *
     * @param s            需要格式化的数字
     * @param numberDigits 小数点位数
     * @return
     */
    public static String setNumberFormatting(String s, int numberDigits) {
        if (TextUtils.isEmpty(s)) {
            return "0.00";
        }
        BigDecimal bg = new BigDecimal(s);
        return bg.setScale(numberDigits, BigDecimal.ROUND_HALF_UP).toString();
    }


    /**
     * 清除数据
     *
     * @param views 要清除的控件
     */
    public static void clearData(View... views) {
        for (View view : views) {
            if (view != null) {
                if (view instanceof EditText) {
                    ((EditText) view).setText("");
                    view.setTag("");
                } else if (view instanceof TextView) {
                    ((TextView) view).setText("");
                    view.setTag("");
                }
            }
        }
    }

    /**
     * 手动设置hint字体大小(默认是：16sp)
     *
     * @param view     要设置的控件
     * @param hintText 设置的内容
     */
    public static void setHintSize(View view, String hintText) {
        setHintSize(view, hintText, 16);
    }

    /**
     * 手动设置hint字体大小
     *
     * @param view     要设置的控件
     * @param hintText 设置的内容
     * @param size     字体的大小
     */
    public static void setHintSize(View view, String hintText, Integer size) {
        /*定义hint的值*/
        SpannableString ss = new SpannableString(hintText);
        /*设置字体大小 true表示单位是sp*/
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (view instanceof EditText) {
            ((EditText) view).setHint(new SpannedString(ss));
        } else if (view instanceof TextView) {
            ((TextView) view).setHint(new SpannedString(ss));
        }
    }

    /**
     * 组建旋转功能
     *
     * @param imageView 要旋转的组建
     * @param textView  展开的组建
     */
    public static void setImageViewRotation(ImageView imageView, TextView textView) {
        switch (mRotation) {
            case 1:
                mRotation = 2;
                imageView.setImageResource(R.mipmap.shuaka_zhankai);
                textView.setSingleLine();
                break;
            case 2:
                mRotation = 1;
                imageView.setImageResource(R.mipmap.shuaka_close);
                textView.setSingleLine(false);
                break;
            default:
        }
    }

    /**
     * 货币换算
     *
     * @param money 钱数
     * @return
     */
    public static String setMoneyConverter(String money) {
        if (TextUtils.isEmpty(money)) {
            return "";
        }
        BigDecimal bigDecimalMoney = new BigDecimal(money);
        BigDecimal bigDecimal10000 = new BigDecimal("10000");
        if (bigDecimalMoney.compareTo(bigDecimal10000) == -1) {
            return bigDecimalMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "元";
        } else {
            return bigDecimalMoney.divide(bigDecimal10000).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "万元";
        }
    }

    /**
     * 将时间戳转换为时间
     *
     * @param timestamp 时间戳
     * @param format    要转换的格式
     */
    @SuppressLint("SimpleDateFormat")
    public static String stampToDate(long timestamp, String format) {
        Date date = new Date(timestamp);
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 输入手机号码时，自动添加空格
     *
     * @param editText
     */
    public static void setPhoneWatcher(EditText editText) {
        if (editText != null) {
            editText.addTextChangedListener(new PhoneWatcher(editText));
        }
    }

    /**
     * 格式化银行卡号，自动添加空格
     *
     * @param editText
     */
    public static void setBankCardNumAddSpace(EditText editText) {
        if (editText != null) {
            editText.addTextChangedListener(new BankCardNumAddSpaceWatcher(editText));
        }
    }

    /**
     * 去除文本所有空格
     *
     * @param text 要去除空格的文本
     */
    public static String removeAllSpace(String text) {
        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(text.trim())) {
            return "";
        }
        return text.trim().replaceAll(" ", "");
    }

    /**
     * 设置文本缩进（默认两位）
     *
     * @param text 要缩进的文本
     */
    public static String setTextIndentation(String text) {
        return setTextIndentation(text, 2);
    }

    /**
     * 设置文本缩进
     *
     * @param text           要去除空格的文本
     * @param indentedDigits 缩进位数
     */
    public static String setTextIndentation(String text, int indentedDigits) {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < indentedDigits; i++) {
            stringBuffer.append("\u3000");
        }
        stringBuffer.append(text);
        return stringBuffer.toString();
    }

    /**
     * 限制输入内容的整数和小数各自默认的长度
     *
     * @param view 要限制的组件
     * @return 数据
     */
    public static void restrictionLength(View... view) {
        restrictionLength(12, 4, view);
    }

    /**
     * 限制输入内容的整数和小数各自默认的长度
     *
     * @param view 要限制的组件
     * @return 数据
     */
    public static void restrictionLength(List<EditText> view) {
        for (EditText editText : view) {
            restrictionLength(12, 4, editText);
        }
    }

    /**
     * 限制档案编号输入长度
     *
     * @param view 要限制的组件
     */
    public static void restrictionLengtheditFileNum(View... view) {
        restrictionLength(-1, 20, view);
    }

    /**
     * 限制输入内容小数长度
     *
     * @param decimalsLength 限制小数位数
     * @param view           要限制的组件
     */
    public static void restrictionLength(int decimalsLength, View... view) {
        restrictionLength(-1, decimalsLength, view);
    }

    /**
     * * 限制输入内容的整数和小数各自的长度
     *
     * @param integerLength  整数长度
     * @param decimalsLength 小数长度
     * @param view           要限制的组件
     */
    public static void restrictionLength(int integerLength, int decimalsLength, View... view) {
        for (View item : view) {
            if (item instanceof TextView) {
                if (-1 == integerLength) {
                    ((TextView) item).addTextChangedListener(new DecimalInputTextWatcher(item, decimalsLength));
                } else {
                    ((TextView) item).addTextChangedListener(new DecimalInputTextWatcher(item, integerLength, decimalsLength));
                }
            } else if (item instanceof EditText) {
                if (-1 == integerLength) {
                    ((EditText) item).addTextChangedListener(new DecimalInputTextWatcher(item, decimalsLength));
                } else {
                    ((EditText) item).addTextChangedListener(new DecimalInputTextWatcher(item, integerLength, decimalsLength));
                }
            }
        }
    }

    /**
     * 限制输入内容的小数长度
     * 限制输入内容的数值最大值 该值必须大于0
     *
     * @param view   要限制的组件
     * @param maxStr 最大值限制 该值必须大于0
     * @return 数据
     */
    public static void restrictionScope(EditText view, String maxStr) {
        List<EditText> editTexts = new ArrayList<>();
        editTexts.add(view);
        restrictionScope(editTexts, 2, maxStr);
    }

    /**
     * 限制输入内容的小数长度
     * 限制输入内容的数值最大值 该值必须大于0
     *
     * @param view           要限制的组件
     * @param decimalsLength 小数长度
     * @param maxStr         最大值限制 该值必须大于0
     * @return 数据
     */
    public static void restrictionScope(List<EditText> view, int decimalsLength, String maxStr) {
        for (View item : view) {
            if (item instanceof TextView) {
                ((TextView) item).addTextChangedListener(new DecimalInputTextWatcher(item, decimalsLength, maxStr));
            } else if (item instanceof EditText) {
                ((EditText) item).addTextChangedListener(new DecimalInputTextWatcher(item, decimalsLength, maxStr));
            }
        }
    }

    /**
     * 限制输入框最大长度
     *
     * @param length
     * @param editTexts
     */
    public static void setStringLength(int length, EditText... editTexts) {
        if (length <= 0) {
            throw new RuntimeException("length must > 0");
        }
        for (EditText editText : editTexts) {
            setStringLength(length, editText);
        }
    }


    /**
     * 限制输入框最大长度
     *
     * @param editText
     * @param length
     */
    public static void setStringLength(int length, EditText editText) {
        if (length <= 0) {
            throw new RuntimeException("length must > 0");
        }

        InputFilter[] filters = {new InputFilter.LengthFilter(length)};
        editText.setFilters(filters);
    }

    /**
     * 复制功能
     *
     * @param context 上下文
     * @param text    复制的内容
     */
    public static boolean copy(Context context, String text) {
        try {
            ClipboardManager myClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData myClip = ClipData.newPlainText("text", text);
            myClipboard.setPrimaryClip(myClip);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 粘贴
     *
     * @param view
     */
    public static <T extends TextView> void paste(Context context, T view) {
        ClipboardManager myClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (myClipboard != null) {
            ClipData abc = myClipboard.getPrimaryClip();
            ClipData.Item item = abc.getItemAt(0);
            view.setText(item.getText().toString());
        }
    }

    /**
     * 数据排序(默认升序)
     *
     * @param list 需要排序数据
     * @return 返回排序后的数据
     */
    public static List dataSorting(List list) {
        return dataSorting(list, true);
    }

    /**
     * 数据排序
     *
     * @param list               需要排序数据
     * @param isPositiveSequence 排序方式：<p>
     *                           true：升序<p>
     *                           false：降序
     * @return 返回排序后的数据
     */
    public static List dataSorting(List list, boolean isPositiveSequence) {
        Collections.sort(list, isPositiveSequence ? null : Collections.reverseOrder());
        return list;
    }

    /**
     * 自定义数据排序
     *
     * @param list       需要排序数据
     * @param comparator 自定义排序
     * @return 返回排序后的数据
     */
    public static <T> void dataSorting(List list, Comparator<T> comparator) {
        Collections.sort(list, comparator);
    }

    /**
     * 设置控件的透明度
     *
     * @param slidingHeight 滑动的高度
     * @param title         控件
     * @param views         要设置的控件
     */
    public static <T extends ViewGroup> void setViewTransparency(int slidingHeight, ViewGroup title, T... views) {
        if (views != null && views.length > 0) {
            for (T view : views) {
                float transparency = 0;
                if (slidingHeight <= title.getMeasuredHeight()) {
                    transparency = StringUtil.multiplicationCalculation(StringUtil.divideCalculation((float) slidingHeight, (float) title.getMeasuredHeight()), 255);
                }
                view.getBackground().mutate().setAlpha(slidingHeight >= title.getMeasuredHeight() ? 255 : (int) transparency);
                Logger.e("滑动位置：" + slidingHeight + "--透明度：" + transparency + "--设置透明度：" + (transparency >= 255 ? 255 : transparency));
            }
        }
    }


    public static void main(String args[]) {
//        List<Person> list = new ArrayList<>();
//        dataSorting(list, new Comparator<Person>() {
//            @Override
//            public int compare(Person o1, Person o2) {
//                return 0;
//            }
//
//        });

//        System.out.println(StringUtil.stampToDate(1543387303, StringUtil.ymdhms));
//        System.out.println(getPercentage(5, 8));
//        System.out.println(getPercentage(1, 0));


//        BigDecimal a = null;
//        Integer faultRate = 7;
//        a = new BigDecimal("5.7500000000000001");
////        a = BigDecimal.valueOf(faultRate.doubleValue() / 3);
//        /*保留两位小数*/
////        BigDecimal b = a.setScale(2, RoundingMode.HALF_UP);
//        BigDecimal b = a.setScale(5, RoundingMode.CEILING);
//        System.out.println("结果是" + b);
//        //下面将结果转化成百分比
//        NumberFormat percent = NumberFormat.getPercentInstance();
//        percent.setMaximumFractionDigits(3);
//
//
//        System.out.println(percent.format(b.doubleValue()));
    }
}
