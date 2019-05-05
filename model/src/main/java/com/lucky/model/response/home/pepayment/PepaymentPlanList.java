package com.lucky.model.response.home.pepayment;

import java.io.Serializable;
import java.util.List;

/**
 * 计划养卡菜单实体
 *
 * @author wangxingyi
 * @date 2018/12/11
 */
public class PepaymentPlanList implements Serializable {
/**
 * {
 *             "id": "257658efc0164d2990732e78bd4cebb2",
 *             "repaymentNum": "1",
 *             "alreadyRepaymentNum": "1",
 *             "planRunTime": "01月05日",
 *             "status": "2",
 *             "repaymentType": "1",
 *             "planRepaymentMoney": "1000",
 *             "planStopTime": "01月05日",
 *             "extendOne": "http://system.minmai1688.com/guangda.png",
 *             "bankName": "光大银行",
 *             "carNumber": "6259760159605397",
 *             "createDate": "2019.01.04 15:18",
 *             "consumptionMoney": "0",
 *             "realName": "王祥意",
 *             "billDate": "01月04日",
 *             "repaymentDate": "01月22日",
 *             "actualRepaymentMoney": "1026.55",
 *             "calculationMoney": "0",
 *             "keepMoney": "110000.0000",
 *             "billRepaymentMoney": "0",
 *             "planFee": "6.55",
 *             "repaymentMoney": "0"
 *         }
 */
    /**
     * 页面标志
     */
    private String pageMark;
    /**
     * 计划唯一id
     */
    private String id;
    /**
     * 信用卡id
     */
    private String creditId;
    /**
     * 通道id
     */
    private String channelId;
    /**
     * 开始日
     */
    private String billDate;
    /**
     * 结束日
     */
    private String repaymentDate;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 养卡笔数
     */
    private String repaymentNum;
    /**
     * 已还笔数
     */
    private String alreadyRepaymentNum;
    /**
     * 开始时间
     */
    private String planRunTime;
    /**
     * 状态：<p>
     * 1：执行中<p>
     * 2：已完成<p>
     * 3：用户撤销(用户操作)<p>
     * 4：异常关闭(系统操作)<p>
     * 5：制定中（制定完成后执行中）<p>
     * 6：执行异常<p>
     * 7：未完全成功<p>
     * 8：终止<p>
     * 规范：1：执行中、2：已完成、3：已中止、4和6：异常
     */
    private String status;
    /**
     * 养卡类型<p>
     * 1：日期养卡
     */
    private String repaymentType;
    /**
     * 计划养卡金额
     */
    private String planRepaymentMoney;
    /**
     * 结束时间
     */
    private String planStopTime;
    /**
     * 银行icon（图标）
     */
    private String extendOne;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行卡号
     */
    private String carNumber;
    /**
     * 实际消费金额
     */
    private String actualRepaymentMoney;
    /**
     * 未知
     */
    private String calculationMoney;
    /**
     * 需预留金额
     */
    private String keepMoney;
    /**
     * 用户选择养卡时间，用于计算开始时间和结束时间
     */
    private String chooseRepaymentDate;
    /**
     * 手续费
     */
    private String planFee;
    /**
     * 创建时间（提交时间）
     */
    private String createDate;
    /**
     * 已养卡金额
     */
    private String alreadyRepaymentMoney;
    /**
     * 已消费金额
     */
    private String alreadyConsumptionMoney;
    /**
     * 计划列表
     */
    private List<PlanDetailsEntity> list;
    /*******************************************************************制定计划参数********************************************************/
    /**
     * 消费金额
     */
    private String consumptionMoney;
    /**
     * 是否警告<p>
     * 1：提示
     */
    private String isWarning;
    /**************************************************************************************************************/

    /**
     * 日志id
     */
    private String billPromptLogId;
    /**
     * 警告内容
     */
    private String warningContent;

    public String getPageMark() {
        return pageMark;
    }

    public void setPageMark(String pageMark) {
        this.pageMark = pageMark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRepaymentNum() {
        return repaymentNum;
    }

    public void setRepaymentNum(String repaymentNum) {
        this.repaymentNum = repaymentNum;
    }

    public String getAlreadyRepaymentNum() {
        return alreadyRepaymentNum;
    }

    public void setAlreadyRepaymentNum(String alreadyRepaymentNum) {
        this.alreadyRepaymentNum = alreadyRepaymentNum;
    }

    public String getPlanRunTime() {
        return planRunTime;
    }

    public void setPlanRunTime(String planRunTime) {
        this.planRunTime = planRunTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType;
    }

    public String getPlanRepaymentMoney() {
        return planRepaymentMoney;
    }

    public void setPlanRepaymentMoney(String planRepaymentMoney) {
        this.planRepaymentMoney = planRepaymentMoney;
    }

    public String getPlanStopTime() {
        return planStopTime;
    }

    public void setPlanStopTime(String planStopTime) {
        this.planStopTime = planStopTime;
    }

    public String getExtendOne() {
        return extendOne;
    }

    public void setExtendOne(String extendOne) {
        this.extendOne = extendOne;
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

    public String getActualRepaymentMoney() {
        return actualRepaymentMoney;
    }

    public void setActualRepaymentMoney(String actualRepaymentMoney) {
        this.actualRepaymentMoney = actualRepaymentMoney;
    }

    public String getCalculationMoney() {
        return calculationMoney;
    }

    public void setCalculationMoney(String calculationMoney) {
        this.calculationMoney = calculationMoney;
    }

    public String getKeepMoney() {
        return keepMoney;
    }

    public void setKeepMoney(String keepMoney) {
        this.keepMoney = keepMoney;
    }

    public String getChooseRepaymentDate() {
        return chooseRepaymentDate;
    }

    public void setChooseRepaymentDate(String chooseRepaymentDate) {
        this.chooseRepaymentDate = chooseRepaymentDate;
    }

    public String getPlanFee() {
        return planFee;
    }

    public void setPlanFee(String planFee) {
        this.planFee = planFee;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getAlreadyRepaymentMoney() {
        return alreadyRepaymentMoney;
    }

    public void setAlreadyRepaymentMoney(String alreadyRepaymentMoney) {
        this.alreadyRepaymentMoney = alreadyRepaymentMoney;
    }

    public String getAlreadyConsumptionMoney() {
        return alreadyConsumptionMoney;
    }

    public void setAlreadyConsumptionMoney(String alreadyConsumptionMoney) {
        this.alreadyConsumptionMoney = alreadyConsumptionMoney;
    }

    public List<PlanDetailsEntity> getList() {
        return list;
    }

    public void setList(List<PlanDetailsEntity> list) {
        this.list = list;
    }

    public String getConsumptionMoney() {
        return consumptionMoney;
    }

    public void setConsumptionMoney(String consumptionMoney) {
        this.consumptionMoney = consumptionMoney;
    }

    public String getIsWarning() {
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

    public String getBillPromptLogId() {
        return billPromptLogId;
    }

    public void setBillPromptLogId(String billPromptLogId) {
        this.billPromptLogId = billPromptLogId;
    }
}
