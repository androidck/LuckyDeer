package com.dykj.requestcore.util.header;

/**
 *
 */
public enum EnumService {
    /**
     *
     */
    CARTRADE(1, "cartradeService", "kzvi1cplku2scqfy1mszv53funhkbj5h", "");

    private Integer type;
    private String serviceName;
    private String appkey;
    private String secretkey;

    EnumService(Integer type, String serviceName, String appkey, String secretkey) {
        this.type = type;
        this.serviceName = serviceName;
        this.appkey = appkey;
        this.secretkey = secretkey;
    }

    /**
     * 获取appkey
     *
     * @param serviceName 标识
     */
    public static String getEnumServiceByAppKey(String serviceName) {

        for (EnumService enumService : EnumService.values()) {
            if (enumService.getServiceName().equals(serviceName)) {
                return enumService.getAppkey();
            }
        }
        return null;
    }

    /**
     * 获取ServiceName
     *
     * @param type 获取的类型
     */
    public static String getEnumServiceByServiceName(Integer type) {
        for (EnumService enumService : EnumService.values()) {
            if (enumService.getType().equals(type)) {
                return enumService.getServiceName();
            }
        }
        return null;
    }

    private Integer getType() {
        return type;
    }


    private String getServiceName() {
        return serviceName;
    }

    private String getAppkey() {
        return appkey;
    }
}
