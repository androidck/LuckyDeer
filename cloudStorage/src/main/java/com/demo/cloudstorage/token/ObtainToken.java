package com.demo.cloudstorage.token;

import com.demo.cloudstorage.qiniuyun.InitFileExecutor;
import com.demo.cloudstorage.R;
import com.orhanobut.logger.Logger;
import com.qiniu.android.utils.UrlSafeBase64;

import org.json.JSONObject;


import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 获取token
 *
 * @author wangxiangyi
 * @date 2018/09/26
 */
public class ObtainToken {

    /**
     * 获取七牛token
     *
     * @param scope 存储空间名称
     * @return token
     */
    public static String getToken(String scope) {
        String token = null;
        try {
            // 1 构造上传策略
            JSONObject json = new JSONObject();
            long dataline = System.currentTimeMillis() / 1000 + 3600;
            /*有效时间为一个小时*/
            json.put("deadline", dataline);
            /*七牛空间名*/
            json.put("scope", scope);
            String encodedPutPolicy = UrlSafeBase64.encodeToString(json.toString().getBytes());
            byte[] sign = hmacsha1encrypt(encodedPutPolicy, InitFileExecutor.getSk());
            String encodedsign = UrlSafeBase64.encodeToString(sign);
            token = InitFileExecutor.getAk() + ':' + encodedsign + ':' + encodedPutPolicy;
            if (InitFileExecutor.isDebug()) {
                Logger.w(InitFileExecutor.getContext().getString(R.string.qiniukongjian) + scope);
                Logger.w(InitFileExecutor.getContext().getString(R.string.token) + token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * @param encryptText 被签名的字符串
     * @param encryptKey  密钥
     * @return
     * @throws Exception
     */
    private static byte[] hmacsha1encrypt(String encryptText, String encryptKey)
            throws Exception {
        String MAC_NAME = "HmacSHA1";
        /*字符集*/
        String ENCODING = "UTF-8";
        byte[] data = encryptKey.getBytes(ENCODING);
        /* 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称*/
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        /*生成一个指定 Mac 算法 的 Mac 对象*/
        Mac mac = Mac.getInstance(MAC_NAME);
        /*用给定密钥初始化 Mac 对象*/
        mac.init(secretKey);
        byte[] text = encryptText.getBytes(ENCODING);
        /*完成 Mac 操作*/
        return mac.doFinal(text);
    }

}
