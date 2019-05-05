package com.lucky.model.response.find;

import java.io.Serializable;
import java.util.List;

/**
 * 红包详情
 *
 * @author wangxiangyi
 * @date 2019/03/28
 */
public class ArticleDetailsEntity implements Serializable {
    /**
     * 领取状态
     */
    private String receivingRemarks;
   /**
     * 红包详情
     */
    private BusinessList redEnvelopeAdvertisingVO;

    /**
     * 附件图片list
     */
    private List<ReceiveRedEnvelopesUserVOListBean> receiveRedEnvelopesUserVOList;

    public String getReceivingRemarks() {
        return receivingRemarks;
    }

    public void setReceivingRemarks(String receivingRemarks) {
        this.receivingRemarks = receivingRemarks;
    }

    public BusinessList getRedEnvelopeAdvertisingVO() {
        return redEnvelopeAdvertisingVO;
    }

    public void setRedEnvelopeAdvertisingVO(BusinessList redEnvelopeAdvertisingVO) {
        this.redEnvelopeAdvertisingVO = redEnvelopeAdvertisingVO;
    }

    public List<ReceiveRedEnvelopesUserVOListBean> getReceiveRedEnvelopesUserVOList() {
        return receiveRedEnvelopesUserVOList;
    }

    public void setReceiveRedEnvelopesUserVOList(List<ReceiveRedEnvelopesUserVOListBean> receiveRedEnvelopesUserVOList) {
        this.receiveRedEnvelopesUserVOList = receiveRedEnvelopesUserVOList;
    }
}
