package com.lucky.model.response.version;

/**
 * 检查版本更新返回参数
 *
 * @author wangxiangyi
 * @date 2018/11/05
 */
public class VersionUpdataEntity {

    /**
     * versionNumber : 版本号
     * versionUrl : 版本下载地址
     * versionDescription : 版本说明
     * deviceType : 版本设备类型1.android  2.ios
     * isNewest : 是否是最新
     * isMandatoryUpdate : 是否强制更新
     * companyId : 公司id
     * createDate : 创建时间
     */
    /**
     * 版本号
     */
    private int versionNumber;
    /**
     * 版本下载地址
     */
    private String versionUrl;
    /**
     * 版本说明
     */
    private String versionDescription;
    /**
     * 版本设备类型<P>
     * 1：android<P>
     * 2：ios
     */
    private String deviceType;
    /**
     * 是否是最新<p>
     * 0：否<p>
     * 1：是
     */
    private String isNewest;
    /**
     * 是否是强制更新<p>
     * 0：否<p>
     * 1：是
     */
    private String isMandatoryUpdate;
    /**
     * 公司id
     */
    private String companyId;
    /**
     * 创建时间
     */
    private String createDate;

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getVersionUrl() {
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl;
    }

    public String getVersionDescription() {
        return versionDescription;
    }

    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getIsNewest() {
        return isNewest;
    }

    public void setIsNewest(String isNewest) {
        this.isNewest = isNewest;
    }

    public String getIsMandatoryUpdate() {
        return isMandatoryUpdate;
    }

    public void setIsMandatoryUpdate(String isMandatoryUpdate) {
        this.isMandatoryUpdate = isMandatoryUpdate;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
