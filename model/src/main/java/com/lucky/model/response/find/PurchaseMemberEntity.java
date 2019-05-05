package com.lucky.model.response.find;

import java.io.Serializable;

/**
 * 会员等级列表
 *
 * @author wangxiangyi
 * @date 2018/11/15
 */
public class PurchaseMemberEntity implements Serializable {
/**
 * {
 *             "levelId": "222",
 *             "levelName": "VIP会员",
 *             "price": 0.000000,
 *             "introduce": "推荐1人即可享受0.5%的费率和被推荐人刷卡分润",
 *             "transferPoundage": "400.00",
 *             "ico": "http://img.minmai1688.com/83c2a1c764dc47568cb822fecf0e2407gold_members@2x.png",
 *             "backgroundColor": "#cd9e41\r\n",
 *             "applicationButton": "1"
 *         }
 */
    /**
     * levelId : 111
     * levelName : 青铜
     * price : 100
     * introduce : 注册即可享受该等级待遇
     * transferPoundage : 0.50
     * ico :
     */
    /**
     * 会员等级ID
     */
    private String levelId;
    /**
     * 会员等级名称
     */
    private String levelName;
    /**
     * 会员等级价格 单位:元
     */
    private String price;
    /**
     * 会员等级介绍
     */
    private String introduce;
    /**
     * 会员等级费率
     */
    private String transferPoundage;
    /**
     * 会员等级图标
     */
    private String ico;
    /**
     * 购买会员等级颜色
     */
    private String backgroundColor;
    /**
     * 申请按钮 1：显示 0：不显示
     */
    private String applicationButton;

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getTransferPoundage() {
        return transferPoundage;
    }

    public void setTransferPoundage(String transferPoundage) {
        this.transferPoundage = transferPoundage;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getApplicationButton() {
        return applicationButton;
    }

    public void setApplicationButton(String applicationButton) {
        this.applicationButton = applicationButton;
    }
}
