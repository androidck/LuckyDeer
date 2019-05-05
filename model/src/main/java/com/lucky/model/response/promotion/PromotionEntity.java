package com.lucky.model.response.promotion;

/**
 * 推广数据
 *
 * @author wangxiangyi
 * @date 2018/11/13
 */
public class PromotionEntity {

    /**
     * id
     */
    private String id;
    /**
     * 大图
     */
    private String bigPicture;
    /**
     * 小图
     */
    private String smallPicture;
    /**
     * 二维码类型<p>
     * 1 ：二维码图片<p>
     * 2 ：链接由前台生成<p>
     * 3：大图中自带二维码
     */
    private String qrcodeType;
    /**
     * 二维码
     */
    private String qrcode;
    /**
     *
     */
    private String createBy;
    /**
     * 创建时间戳
     */
    private long createDate;
    /**
     *
     */
    private String updateBy;
    /**
     * 更新时间戳
     */
    private long updateDate;
    /**
     * 备注
     */
    private String remarks;
    /**
     *
     */
    private String delFlag;
    /**
     * 是否选中
     */
    private boolean isSelect;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBigPicture() {
        return bigPicture;
    }

    public void setBigPicture(String bigPicture) {
        this.bigPicture = bigPicture;
    }

    public String getSmallPicture() {
        return smallPicture;
    }

    public void setSmallPicture(String smallPicture) {
        this.smallPicture = smallPicture;
    }

    public String getQrcodeType() {
        return qrcodeType;
    }

    public void setQrcodeType(String qrcodeType) {
        this.qrcodeType = qrcodeType;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
