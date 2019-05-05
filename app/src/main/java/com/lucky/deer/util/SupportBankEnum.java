package com.lucky.deer.util;

import android.text.TextUtils;

/**
 * 支持的银行
 *
 * @author wangxiangyi
 * @date 2019/03/06
 */
public enum SupportBankEnum {

    /*支持的结算卡*/

    工商银行("工商银行", "ICBC"),
    农业银行("农业银行", "ABC"),
    中国银行("中国银行", "BOC"),
    建设银行("建设银行", "CCB"),
    招商银行("招商银行", "CMB"),
    兴业银行("兴业银行", "CIB"),
    平安银行("平安银行", "SPABANK"),
    光大银行("光大银行", "CEB"),
    华夏银行("华夏银行", "HXBANK"),
    广发银行("广发银行", "GDB"),
    交通银行("交通银行", "COMM"),
    中信银行("中信银行", "CITIC"),
    民生银行("民生银行", "CMBC"),
    北京银行("北京银行", "BJBANK");


    /**
     * 银行卡名称
     */
    private String bankCardName;
    /**
     * 银行卡缩写
     */
    private String bankCardAbbreviation;

    SupportBankEnum(String bankCardName, String bankCardAbbreviation) {
        this.bankCardName = bankCardName;
        this.bankCardAbbreviation = bankCardAbbreviation;
    }

    public String getBankCardName() {
        return bankCardName;
    }

    public String getBankCardAbbreviation() {
        return bankCardAbbreviation;
    }

    /**
     * 检测是否支持此银行
     *
     * @param bankCardAbbreviation 银行缩写
     * @return true：支持 false：不支持
     */
    public static boolean isSupportTheBank(String bankCardAbbreviation) {
        if (TextUtils.isEmpty(bankCardAbbreviation)) {
            return false;
        }
        for (SupportBankEnum value : values()) {
            while (value.getBankCardAbbreviation().equals(bankCardAbbreviation)) {
                return true;
            }
        }
        return false;
    }
}
