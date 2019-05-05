package com.lucky.model.util;

import android.text.TextUtils;

import com.orhanobut.hawk.Hawk;


/**
 * 数据保存工具类
 *
 * @author wangxiangyi
 * @date 2018/09/17
 */
public class HawkUtil {
    /**
     * 用户信息
     */
    public static final String USER_INFO = "UserInfo";
    /**
     * 推送id
     */
    public static final String REGISTRATION_ID = "registrationID";


    private static HawkUtil instances;

    public static HawkUtil getInstance() {
        if (instances == null) {
            synchronized (HawkUtil.class) {
                if (instances == null) {
                    instances = new HawkUtil();
                }
            }
        }
        return instances;
    }

    /**
     * 保存数据
     *
     * @param dataKey 保存key值
     * @param value   需要保存值
     */
    public <T> boolean saveData(String dataKey, T value) {
        return !TextUtils.isEmpty(dataKey) && value != null && Hawk.put(dataKey, value);
    }

    /**
     * 按key值获取数据
     *
     * @param dataKey 获取数据的key值
     * @return 获取的数据
     */
    public <T> T getSaveData(String dataKey) {
        return TextUtils.isEmpty(dataKey) ? null : (T) Hawk.get(dataKey);
    }

    /**
     * 按key值获取数据
     *
     * @param dataKey      获取数据的key值
     * @param defaultValue 数据默认值
     * @return 获取的数据
     */
    public <T> T getSaveData(String dataKey, T defaultValue) {
        return TextUtils.isEmpty(dataKey) && defaultValue == null ? null : Hawk.get(dataKey, defaultValue);
    }

    /**
     * 删除数据（按着key）
     *
     * @param dataKey 要删除数据的key
     */
    public boolean remove(String dataKey) {
        return !TextUtils.isEmpty(dataKey) && Hawk.delete(dataKey);
    }

    /**
     * 清楚所有保存的信息
     */
    public boolean deleteAll() {
        return Hawk.deleteAll();
    }

    /**
     * 查询保存的条数
     */
    public long countAll() {
        return Hawk.count();
    }

    /**
     * 判断保存数据是否包含当前的key
     */
    public boolean isContains(String key) {
        return Hawk.contains(key);
    }
}
