package com.lucky.model.response;

/**
 * 数据响应类
 *
 * @author wangxiangyi
 * @date 2018/9/18.
 */
public class ResponseData<T> {
    /**
     * 返回码
     */
    private int code;
    /**
     * 返回码描述
     */
    private String msg;
    /**
     * 数据
     */
    private T data;
    /**
     * cardType : DC
     * bank : ICBC
     * key : 6212261602009500457
     * messages : []
     * validated : true
     * stat : ok
     */
    /**
     * 卡的种类
     */
    private String cardType;
    /**
     * 银行卡名称缩写
     */
    private String bank;
    /**
     * 银行卡号
     */
    private String key;
    /**
     * 验证银行卡号是否正确
     */
    private boolean validated;
    /**
     * 返回状态
     */
    private String stat;

    //总条数
    private int totalCount;


    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public String getStat() {
        return stat;
    }
    /****************************************************阿里四要素认证返回参数************************************************/
    /**
     * 姓名
     */
    private String name;
    /**
     * 银行卡号
     */
    private String cardNo;
    /**
     * 身份证号
     */
    private String idNo;
    /**
     * 手机号
     */
    private String phoneNo;
    /**
     * 匹配结果信息
     */
    private String respMessage;
    /**
     * 认证编码
     */
    private String respCode;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行种类
     */
    private String bankKind;
    /**
     * 银行卡类型
     */
    private String bankType;
    /**
     * 银行编码
     */
    private String bankCode;

    /******************************************************京东万象四要素认证返回值*********************************************************/

    /**
     * 银行编码
     */
    private String ordersign;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(String respMessage) {
        this.respMessage = respMessage;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankKind() {
        return bankKind;
    }

    public void setBankKind(String bankKind) {
        this.bankKind = bankKind;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getOrdersign() {
        return ordersign;
    }

    public void setOrdersign(String ordersign) {
        this.ordersign = ordersign;
    }
}
