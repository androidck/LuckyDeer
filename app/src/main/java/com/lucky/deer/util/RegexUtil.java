package com.lucky.deer.util;

import java.util.regex.Pattern;


/**
 * 正则验证数据
 *
 * @author wangxiangyi
 * @date 2019/01/29
 */
public class RegexUtil {
    /**
     * 验证1开头的11位数字
     */
    private static String REGEX_MOBILE_EXACT = "^1\\d{10}$";

    /**
     * 返回输入是否与精确移动的正则表达式匹配。
     *
     * @param input 要验证的手机号
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isMobileExact(final CharSequence input) {
        return isMatch(REGEX_MOBILE_EXACT, input);
    }

    /**
     * 返回输入是否与正则表达式匹配。
     *
     * @param regex 正则表达式
     * @param input 要验证的信息
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isMatch(final String regex, final CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }


}
