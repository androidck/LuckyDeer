package com.lucky.model.request.home.cardLife;


import com.lucky.model.request.BaseReq;

/**
 * 购买会员等级
 *
 * @author wangxiangyi
 * @date 2018/11/19
 */
public class PurchaseMembershipLevelReq extends BaseReq {
    /**
     * 交易描述
     */
    private String body;
    /**
     * 商品的标题/交易标题/订单标题/订单关键字
     */
    private String subject;
    /**
     * 绝对超时时间 30m
     */
    private String timeExpire;
    /**
     * 商品类型：<p>
     * 0：虚拟商品<p>
     * 1：实体商品
     */
    private String goodsType;
    /**
     * 订单总金额（默认单位：元）
     */
    private String totalAmount;
    /**
     * 订单类型：<p>
     * 1：购买会员<p>
     * 2：红包支付
     */
    private String type;
    /**
     * 购买等级id
     */
    private String memberLevelId;

    /****************************************提交申请**************************************************/
    /**
     * 要申请的会员等级id
     */
    private String applyMemberLevelId;
    /****************************************升级码升级会员**************************************************/
    /**
     * 升级码
     */
    private String upgradeCode;
    /************************************************发红包id**************************************************/
    /**
     * 红包id
     */
    private String productId;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMemberLevelId() {
        return memberLevelId;
    }

    public void setMemberLevelId(String memberLevelId) {
        this.memberLevelId = memberLevelId;
    }

    public String getApplyMemberLevelId() {
        return applyMemberLevelId;
    }

    public void setApplyMemberLevelId(String applyMemberLevelId) {
        this.applyMemberLevelId = applyMemberLevelId;
    }

    public String getUpgradeCode() {
        return upgradeCode;
    }

    public void setUpgradeCode(String upgradeCode) {
        this.upgradeCode = upgradeCode;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
