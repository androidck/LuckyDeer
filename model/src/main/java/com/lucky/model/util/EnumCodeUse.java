package com.lucky.model.util;

import com.lucky.model.R;

/**
 * 验证码使用场景
 *
 * @author wangxiangyi
 * @date 2018/10/10
 */

public enum EnumCodeUse {

    BINDCODEUSE("1", R.string.registered),
    UPDATELOGINPWDCODEUSE("2", R.string.modify_login_pwd),
    UPDATEPAYMENTPWDCODEUSE("3", R.string.modify_Pay_pwd),
    LOGINCODEUSE("4", R.string.login),
    UPDATEPHONE("5", R.string.replace_phone_number),;
    String code;
    int desc;

    /**
     * @param code 使用场景标记
     * @param desc 使用场景
     */
    EnumCodeUse(String code, int desc) {
        this.code = code;
        this.desc = desc;

    }

    public static String getEnumCodeUse(int desc) {
        for (EnumCodeUse e : EnumCodeUse.values()) {
            if (e.getDesc() == desc) {
                return e.getCode();
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public int getDesc() {
        return desc;
    }
}
