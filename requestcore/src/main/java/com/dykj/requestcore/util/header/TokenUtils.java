package com.dykj.requestcore.util.header;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class TokenUtils {
    /**
     * 验证签名
     *
     * @param signature   返回的签名
     * @param signMap     请求参数
     * @param serviceName serviceName
     * @param timestamp   时间戳
     * @return
     */

    public static boolean checkToken(String signature, Map<String, Object> signMap, String serviceName, Long timestamp) {
        return signature.equals(getSign(signMap, serviceName, timestamp));
    }

    public static void main(String[] arg) {
        try {
            System.out.println(sha1("kzvi1cplku2scqfy1mszv53funhkbj5h","appId&e18911500bd243e390fb32d86adf1076loginName&18231712823pageCurrent&0pageSize&10pwd&83aa400af464c76d"+"1539761612"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取签名
     *
     * @param signMap     得到的参数
     * @param serviceName serviceName
     * @param timestamp   时间戳
     * @return 得到签名
     */
    public static String getSign(Map<String, Object> signMap, String serviceName, Long timestamp) {
        String appKey = EnumService.getEnumServiceByAppKey(serviceName);
        try {
            return sha1(appKey, signMapConverStr(signMap) + timestamp);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String signMapConverStr(Map<String, Object> signMap) {
        Set<String> set = signMap.keySet();

        List<String> sortlist = new ArrayList<>(set);
        Collections.sort(sortlist);
        StringBuilder builder = new StringBuilder("");

        for (String key : sortlist) {
            builder.append(key).append("&").append(signMap.get(key));
        }
        return builder.toString();
    }

    //签名算法
    private static String sha1(String secret, String content) throws NoSuchAlgorithmException,
            UnsupportedEncodingException, InvalidKeyException {

        SecretKey secretKey;
        secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(secretKey);

        byte[] text = content.getBytes("UTF-8");
        byte[] finalText = mac.doFinal(text);


        StringBuilder enText = new StringBuilder();
        for (byte b : finalText) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            enText.append(hex);
        }

        String finalStr = enText.toString();

        // 1和4互换  	485cdf848bb044fd57b44199cd8fbb67a31ab2e2
        //			c854df848bb044fd57b44192cd8fbb67a31ab2e9
        finalStr = transfer(finalStr, 1, 4);
        // 8和21互换
        finalStr = transfer(finalStr, 8, 21);
        // 24和40互换
        finalStr = transfer(finalStr, 24, 40);
        return finalStr;
    }

    //乱序方法
    private static String transfer(String str, int index1, int index2) {
        char array[] = str.toCharArray();

        index1 = index1 == 0 ? 0 : index1 - 1;
        index2 = index2 == 0 ? 0 : index2 - 1;

        char temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;

        StringBuilder result = new StringBuilder();
        for (char c : array) {
            result.append(c);
        }
        return result.toString();
    }

}
