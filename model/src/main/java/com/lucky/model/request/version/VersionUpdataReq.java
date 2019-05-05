package com.lucky.model.request.version;

import com.lucky.model.request.BaseReq;

/**
 * 检查版本更新参数
 *
 * @author wangxiangyi
 * @date 2018/11/05
 */
public class VersionUpdataReq extends BaseReq {
    /**
     * 版本号
     */
    private String versionNumber;

    public String getVersionNumber() {
        return versionNumber;
    }

    private String versionUrl;//

    private String versionDescription;// 版本说明

    private String  deviceType;//设备类型

    private String  isNewest;//是否为最新

    private String isMandatoryUpdate;//是否强制更新

    private String companyId;//公司id

    private String  createDate;//创建时间

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

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

/*    |versionNumber|String |版本号|
            |versionUrl|String |版本下载地址|
            |versionDescription|String |版本说明|
            |deviceType|String |版本设备类型1.android  2.ios|
            |isNewest|String |是否是最新|
            |isMandatoryUpdate|String ||
            |companyId|String |公司id|
            |createDate|String |创建时间|*/

    @Override
    public String toString() {
        return "VersionUpdataReq{" +
                "versionNumber='" + versionNumber + '\'' +
                ", versionUrl='" + versionUrl + '\'' +
                ", versionDescription='" + versionDescription + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", isNewest='" + isNewest + '\'' +
                ", isMandatoryUpdate='" + isMandatoryUpdate + '\'' +
                ", companyId='" + companyId + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
