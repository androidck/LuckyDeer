package com.lucky.model.request.certification;

import com.lucky.model.request.BaseReq;

/**
 * 阿里云四要素认证请求参数
 */
public class FourFactorAuthenticationReq extends BaseReq {
    /**
     * 是否返回银行信息<p>
     * YES：返回银行信息<p>
     * NO：不返回银行信息
     */
    private String ReturnBankInfo = "YES";
    /**
     * 银行卡号
     */
    private String cardNo;
    /**
     * 身份证号码
     */
    private String idNo;
    /**
     * 开户名
     */
    private String name;
    /**
     * 手机号
     */
    private String phoneNo;


    /****************************************************京东万象四要素认证******************************************************/
    /**
     * 身份证号码
     */
    private String idcard;
    /**
     * 开户姓名
     */
    private String realname;
    /**
     * 银行卡号
     */
    private String bankcard;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 开发者秘钥
     */
    private String key = "be02d5f33afd481facdbed6b5d6d8e29";
    /****************************************************四要素认证******************************************************/
    /**
     * 公司id
     */
    private String companyId;

    public String getReturnBankInfo() {
        return ReturnBankInfo;
    }

    public void setReturnBankInfo(String returnBankInfo) {
        ReturnBankInfo = returnBankInfo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
