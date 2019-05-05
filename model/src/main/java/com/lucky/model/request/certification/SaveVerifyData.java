package com.lucky.model.request.certification;

import com.lucky.model.request.BaseReq;

/**
 * 保存验证信息
 *
 * @author wangxiangyi
 * @date 2018/12/12
 */
public class SaveVerifyData extends BaseReq {
    /**
     * 银行卡号
     */
    private String cardNo;
    /**
     * 身份证号
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
    /**
     * 返回结果
     */
    private String resultJson;
    /**
     * 公司ID
     */
    private String companyId;

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

    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
