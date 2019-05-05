package com.lucky.deer.weight.enums;

import android.text.TextUtils;

/**
 * 公共枚举类
 *
 * @author wangxiangyi
 * @date 2018/10/23
 */
public enum PublicEnum {

    /*通道接口*/
    /**
     * 刷卡
     */
    swipe("1", "刷卡", "通道接口"),
    /**
     * 代还
     */
    generation("2", "代还", "通道接口"),
    /*支付类型*/
    /**
     * 开通快捷支付
     */
    quickPayment("1", "开通快捷支付", "支付类型"),

    /**
     * 交易
     */
    determinePayment("2", "确定支付", "支付类型"),
    /*银行卡类型*/
    /**
     * 未知卡
     */
    unknownCard("Unknown", "未知卡", "银行卡类型", 0),
    /**
     * 储蓄卡
     */
    debitCard("Debit", "储蓄卡", "银行卡类型", 1),
    /**
     * 信用卡
     */
    creditCard("Credit", "信用卡", "银行卡类型", 2),
    /* 高德验证银行卡信息*/
    /**
     * 储蓄卡<p>
     * cardType:DC 代表储蓄卡
     */
    gao_de_debitCard("DC", "储蓄卡", "银行卡类型", 1),
    /**
     * 信用卡<p>
     * cardType:CC 代表信用卡
     */
    gao_de_creditCard("CC", "信用卡", "银行卡类型", 2),

    /***************是否是业务员***************/
    /**
     * 普通用户<p>
     * userType:1 代表普通用户
     */
    GENERAL_USER("1", "普通用户", "是否是业务员", false),

    /**
     * 业务员<p>
     * userType:2 代表业务员
     */
    SALESMAN("2", "业务员", "是否是业务员", true);


    private String mType;
    private String mTitleName;
    private boolean mYesOrNo;
    private String mInterfaceTypes;
    private Integer mCreditCard;

    /**
     * @param type           类型
     * @param titleName      显示名称
     * @param interfaceTypes 接口类型
     */
    PublicEnum(String type, String titleName, String interfaceTypes) {
        this.mType = type;
        this.mTitleName = titleName;
        this.mInterfaceTypes = interfaceTypes;
    }

    /**
     * @param type           类型
     * @param titleName      显示名称
     * @param interfaceTypes 接口类型
     * @param yesOrNo        true：是，false：否
     */
    PublicEnum(String type, String titleName, String interfaceTypes, boolean yesOrNo) {
        this.mType = type;
        this.mTitleName = titleName;
        this.mInterfaceTypes = interfaceTypes;
        this.mYesOrNo = yesOrNo;
    }

    /**
     * @param type           类型
     * @param titleName      显示名称
     * @param interfaceTypes 接口类型
     */
    PublicEnum(String type, String titleName, String interfaceTypes, Integer creditCard) {
        this.mType = type;
        this.mTitleName = titleName;
        this.mInterfaceTypes = interfaceTypes;
        this.mCreditCard = creditCard;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public String getTitleName() {
        return mTitleName;
    }

    public void setTitleName(String titleName) {
        this.mTitleName = titleName;
    }

    public boolean ismYesOrNo() {
        return mYesOrNo;
    }

    public String getInterfaceTypes() {
        return mInterfaceTypes;
    }

    public void setInterfaceTypes(String interfaceTypes) {
        this.mInterfaceTypes = interfaceTypes;
    }

    public Integer getCreditCard() {
        return mCreditCard;
    }

    public static String getEnumTitleName(String type) {
        return getEnumTitleName(type, "");
    }

    public static String getEnumTitleName(String type, String interfaceTypes) {
        for (PublicEnum publicEnum : PublicEnum.values()) {
            if (TextUtils.isEmpty(interfaceTypes)) {
                if (publicEnum.getType().equals(type)) {
                    return publicEnum.getTitleName();
                }
            } else {
                if (publicEnum.getInterfaceTypes().equals(interfaceTypes) &&
                        publicEnum.getType().equals(type)) {
                    return publicEnum.getTitleName();
                }
            }
        }
        return "";
    }

    /**
     * 判断是否是储蓄卡
     *
     * @param type 卡类型
     * @return 返回是或否<p>
     * 0：未知卡种<p>
     * 1：储蓄卡<p>
     * 2：信用卡
     */
    public static Integer getEnumCardType(String type) {
        for (PublicEnum publicEnum : PublicEnum.values()) {
            if (!TextUtils.isEmpty(type)) {
                if (publicEnum.getType().equals(type)) {
                    return publicEnum.getCreditCard();
                }
            }
        }
        return 0;
    }

    /**
     * 是否是业务员
     *
     * @param type           类型
     * @param interfaceTypes 功能类型
     * @return
     */
    public static boolean getEnumIsSalesman(String type, String interfaceTypes) {
        for (PublicEnum publicEnum : PublicEnum.values()) {
            if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(interfaceTypes)) {
                if (publicEnum.getType().equals(type) && publicEnum.getInterfaceTypes().equals(interfaceTypes)) {
                    return publicEnum.ismYesOrNo();
                }
            }
        }
        return false;
    }

}
