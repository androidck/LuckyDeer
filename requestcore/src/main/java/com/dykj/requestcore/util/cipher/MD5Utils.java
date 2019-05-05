package com.dykj.requestcore.util.cipher;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by gb on 2018/1/24.
 */
public class MD5Utils {
    /**
     * 十六位
     */
    public static final String NUMBER_DIGITS_16 = "16";
    /**
     * 三十二位
     */
    public static final String NUMBER_DIGITS_32 = "32";

    /**
     * 大写
     */
    public static final String CAPITALIZATION_BIG = "Big";
    /**
     * 小写
     */
    public static final String CAPITALIZATION_SMALL = "small";

    public static void main(String[] sar) {
        System.out.println("小写16：" + stringToMD5("123456789"));
    }

    /**
     * 将字符串转成MD5值(默认是16位全部小写)
     *
     * @param string
     * @return
     */
    public static String stringToMD5(String string) {
        return stringToMD5(string, NUMBER_DIGITS_16, CAPITALIZATION_SMALL);
    }

    /**
     * 将字符串转成MD5值(默认是16位)
     *
     * @param string 自定义大小写
     * @return
     */
    public static String stringToMD5(String string, String capitalization) {
        return stringToMD5(string, NUMBER_DIGITS_16, capitalization);
    }

    /**
     * 将字符串转成MD5值(全部小写)
     *
     * @param string
     * @return
     */
    public static String stringToMD532(String string) {
        return stringToMD5(string, NUMBER_DIGITS_32, CAPITALIZATION_SMALL);
    }

    /**
     * 将字符串转成MD5值(全部小写)
     *
     * @param string
     * @return
     */
    public static String stringToMD532(String string, String capitalization) {
        return stringToMD5(string, NUMBER_DIGITS_32, capitalization);
    }

    /**
     * 将字符串转成MD5值
     *
     * @param string
     * @return
     */
    private static String stringToMD5(String string, String numberDigits, String capitalization) {
        String md5Data = null;
        if (!TextUtils.isEmpty(getMd5Value(string))) {
            if (NUMBER_DIGITS_16.equals(numberDigits)) {
                md5Data = getMd5Value(string).substring(8, 24);
            } else if (NUMBER_DIGITS_32.equals(numberDigits)) {
                md5Data = getMd5Value(string);
            }
            /*大写*/
            if (CAPITALIZATION_BIG.equals(capitalization)) {
                md5Data = md5Data.toUpperCase();
            }
        }
        return md5Data;
    }


    private static String getMd5Value(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
