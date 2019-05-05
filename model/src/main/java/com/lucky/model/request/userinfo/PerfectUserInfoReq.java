package com.lucky.model.request.userinfo;

import com.lucky.model.request.BaseReq;

/**
 * 完善用户信息
 */
public class PerfectUserInfoReq extends BaseReq {

    /**************************************完善信息第一步*********************************************/
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 民族
     */
    private String nation;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 家庭详细地址
     */
    private String detailedAddress;
    /**
     * 有效时间
     */
    private String effectiveDate;
    /**
     * 身份证人像页
     */
    private String cardFrontPic;
    /**
     * 身份证国徽页
     */
    private String cardBackPic;
    /**************************************完善信息第二步*********************************************/
    /**
     * 身份证人像页
     */
    private String handIdCard;
    /**************************************完善信息第三步*********************************************/
    /**
     * 修改的储蓄卡ID
     */
    private String oldDebitCardId;
    /**
     * 银行卡号
     */
    private String carNumber;
    /**
     * 开户银行名称
     */
    private String openBank;
    /**
     * 预留手机号
     */
    private String phone;
    /**
     * 区域id
     */
    private String areaCode;
    /**
     * 银行卡照片
     */
    private String photo;
    /**
     * 支银行卡id不能为空
     */
    private String bankId;
    /**
     * 是否默认使用
     */
    private String isDefault;

    /**************************************获取支行信息列表*********************************************/
    /**
     * 总行的id
     */
    private String parentId;
    /**
     * 区域id
     */
    private String cityId;


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getCardFrontPic() {
        return cardFrontPic;
    }

    public void setCardFrontPic(String cardFrontPic) {
        this.cardFrontPic = cardFrontPic;
    }

    public String getCardBackPic() {
        return cardBackPic;
    }

    public void setCardBackPic(String cardBackPic) {
        this.cardBackPic = cardBackPic;
    }

    public String getHandIdCard() {
        return handIdCard;
    }

    public void setHandIdCard(String handIdCard) {
        this.handIdCard = handIdCard;
    }

    public String getOldDebitCardId() {
        return oldDebitCardId;
    }

    public void setOldDebitCardId(String oldDebitCardId) {
        this.oldDebitCardId = oldDebitCardId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
