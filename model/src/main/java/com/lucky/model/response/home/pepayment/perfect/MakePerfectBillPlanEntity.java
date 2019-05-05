package com.lucky.model.response.home.pepayment.perfect;

import java.io.Serializable;
import java.util.List;

/**
 * 完美养卡制定返回实体
 *
 * @author wangxiangyi
 * @date 2019/02/26
 */
public class MakePerfectBillPlanEntity implements Serializable {

    /**
     * 页面标志
     */
    private String pageMark;
    /**
     * 信用卡id
     */
    private String creditId;
    /**
     * 计划账单主键
     */
    private String id;
    /**
     * 计划编号
     */
    private String planNo;
    /**
     * 通道id
     */
    private String channelId;
    /**
     * 银行logo
     */
    private String bankLogo;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行卡号
     */
    private String carNumber;
    /**
     * 计划养卡金额
     */
    private String planRepaymentMoney;
    /**
     * 预留养卡金额
     */
    private String actualRepaymentMoney;
    /**
     * 状态
     */
    private String status;
    /**
     * 是否警告<p>
     * 1：提示
     */
    private String isWarning;
    /**
     * 警告内容
     */
    private String warningContent;
    /**
     * 制定计划详情列表：养卡列表
     */
    private List<PerfectBillPlanListEntity> repaymentList;
    /**
     * 制定计划详情列表：消费列表
     */
    private List<PerfectBillPlanListEntity> consumptionList;


//    /**
//     * 养卡笔数
//     */
//    private String repaymentNum;
//    /**
//     * 计划执行时间
//     */
//    private String planRunTime;
//    /**
//     * 养卡类型
//     */
//    private String repaymentType;
//    /**
//     * 计划结束时间
//     */
//    private String planStopTime;
//    /**
//     * 银行logo
//     */
//    private String extendOne;
//    /**
//     * 提交时间
//     */
//    private String createDate;
//    /**
//     * 实际消费金额
//     */
//    private String consumptionMoney;
//    /**
//     * 真实姓名
//     */
//    private String realName;
//    /**
//     * 开始日期
//     */
//    private String billDate;
//    /**
//     * 结束日期
//     */
//    private String repaymentDate;
//    /**
//     *
//     */
//    private String calculationMoney;
//    /**
//     *
//     */
//    private String consumptionStatus;
//    /**
//     *
//     */
//    private String keepMoney;
//    /**
//     * 计划手续费
//     */
//    private String planFee;


    public String getPageMark() {
        return pageMark;
    }

    public void setPageMark(String pageMark) {
        this.pageMark = pageMark;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getPlanRepaymentMoney() {
        return planRepaymentMoney;
    }

    public void setPlanRepaymentMoney(String planRepaymentMoney) {
        this.planRepaymentMoney = planRepaymentMoney;
    }

    public String getActualRepaymentMoney() {
        return actualRepaymentMoney;
    }

    public void setActualRepaymentMoney(String actualRepaymentMoney) {
        this.actualRepaymentMoney = actualRepaymentMoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String isWarning() {
        return isWarning;
    }

    public void setIsWarning(String isWarning) {
        this.isWarning = isWarning;
    }

    public String getWarningContent() {
        return warningContent;
    }

    public void setWarningContent(String warningContent) {
        this.warningContent = warningContent;
    }

    public List<PerfectBillPlanListEntity> getRepaymentList() {
        return repaymentList;
    }

    public void setRepaymentList(List<PerfectBillPlanListEntity> repaymentList) {
        this.repaymentList = repaymentList;
    }

    public List<PerfectBillPlanListEntity> getConsumptionList() {
        return consumptionList;
    }

    public void setConsumptionList(List<PerfectBillPlanListEntity> consumptionList) {
        this.consumptionList = consumptionList;
    }
}


