package com.dykj.requestcore.util.header;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 *
 */
public enum EnumHttpHeaderParam {
    /**
     * 用户id
     */
    USER_ID(0, "X_UserId"),
    /**
     * 时间戳
     */
    TIMESTAMP(1, "X_Timestamp"),
    /**
     * 服务名称
     */
    SERVICENAME(2, "X_ServiceName"),
    /**
     * 签名
     */
    SIGNATURE(3, "X_Signature"),
    /**
     * 推送token
     */
    DEVICETOKEN(4, "X_Devicetoken"),
    /**
     * 设备类型
     */
    DEVICETYPE(5, "X_DeviceType"),
    /**
     * 设备编码
     */
    DEVICEIMEL(6, "X_DeviceIMEL"),
    /**
     * 设备型号
     */
    DEVICEMODEL(7, "X_DeviceModel"),
    /**
     * 项目id
     */
    APPID(8, "X_APPID"),
    /**
     * 阿里云四要素认证appCode
     */
    APPCODE(9, "Authorization");


    private Integer code;
    private String param;

    EnumHttpHeaderParam(Integer code, String param) {
        this.code = code;
        this.param = param;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getHeaderParam(Integer code) {
        for (EnumHttpHeaderParam enumHttpHeaderParam : EnumHttpHeaderParam.values()) {
            if (enumHttpHeaderParam.code.equals(code)) {
                return enumHttpHeaderParam.getParam();
            }
        }
        return null;
    }


    public Integer getCode() {
        return code;
    }

    public String getParam() {
        return this.param;
    }
}
