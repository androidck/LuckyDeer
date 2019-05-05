package com.lucky.model.response.home;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 我要网申和网贷返回数据实体
 *
 * @author wangxiangyi
 * @date 2019/03/16
 */
public class OnlineApplicationEntity implements Serializable, MultiItemEntity {
    /**
     * 配置<br>
     * 1：显示<br>
     * 2：不显示
     */
    private String homeConfig;
    private List<OnlineApplicationEntity> onlineApplicationList;

    /**
     * 备注
     */
    private String remarks;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 更新时间
     */
    private String updateDate;
    /**
     * 主键
     */
    private String id;
    /**
     * 编号
     */
    private String seqNumber;
    /**
     * 银行主键
     */
    private String bankId;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 卡片名称
     */
    private String cardName;
    /**
     * 卡片图片
     */
    private String cardImage;
    /**
     * 卡片备注
     */
    private String cardRemarks;
    /**
     * 地址
     */
    private String address;
    /**
     * 是否置顶<p>
     * 1：是<p>
     * 0：否
     */
    private String isTop;
    /**
     * 是否推荐<p>
     * 1：是<p>
     * 0：否
     */
    private String isRecommend;

    private int itemType = 1;

    public String getHomeConfig() {
        return homeConfig;
    }

    public void setHomeConfig(String homeConfig) {
        this.homeConfig = homeConfig;
    }

    public List<OnlineApplicationEntity> getOnlineApplicationList() {
        return onlineApplicationList;
    }

    public void setOnlineApplicationList(List<OnlineApplicationEntity> onlineApplicationList) {
        this.onlineApplicationList = onlineApplicationList;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(String seqNumber) {
        this.seqNumber = seqNumber;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardImage() {
        return cardImage;
    }

    public void setCardImage(String cardImage) {
        this.cardImage = cardImage;
    }

    public String getCardRemarks() {
        return cardRemarks;
    }

    public void setCardRemarks(String cardRemarks) {
        this.cardRemarks = cardRemarks;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
