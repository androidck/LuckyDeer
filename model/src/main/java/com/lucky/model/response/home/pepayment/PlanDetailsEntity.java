package com.lucky.model.response.home.pepayment;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.util.List;

/**
 * 代还开卡返回实体
 *
 * @author wangxiangyi
 * @date 2018/12/25
 */
public class PlanDetailsEntity extends SectionEntity {

    /**
     * 计划还款金额
     */
    private String billRepaymentMoney;
    /**
     * 还款日期
     */
    private String repaymentTime;
    /**
     * 账单还款日期
     */
    private String billRepaymentTime;
    /**
     * 还款状态：<p>
     * 1：完成<p>
     * 2：失败<p>
     * 3：强制代付<p>
     * 4：执行中<p>
     * 5：待查询
     */
    private String status;
    /**
     * 实际还款金额
     */
    private String repaymentMoney;
    /**
     * 实际消费金额
     */
    private String consumptionMoney;
//    /**
//     * 详情
//     */
//    private List<ConsumeList> consume;
    /**
     * 详情
     */
    private List<PlanDetailsEntity> consume;

    /**********************************还款详情返回数据****************************************/
//    /**
//     * 消费金额
//     */
//    private String consumptionMoney;
    /**
     * 消费时间
     */
    private String consumptionTime;
//    /**
//     * 还款时间
//     */
//    private String repaymentTime;
//    /**
//     * 预还款时间
//     */
//    private String billRepaymentTime;
    /**
     * 是否以消费<p>
     * 1成功<p>
     * 2失败<p>
     * 4未执行
     */
    private String consumptionStatus;
//    /**
//     * 是否已还款状态<p>
//     * 1：已还款<p>
//     * 2：还款失败<p>
//     * 4：待执行
//     */
//    private String status;
//    /**
//     * 实际还款金额
//     */
//    private String repaymentMoney;

    /**
     * 总条数
     */
    private int lastOne;
    /**
     * 失败原因
     */
    private String exceptionDescribe;

    public PlanDetailsEntity(boolean isHeader, String header, PlanDetailsEntity entity) {
        super(isHeader, header);
        this.billRepaymentMoney = entity.getBillRepaymentMoney();
        this.repaymentTime = entity.getRepaymentTime();
        this.billRepaymentTime = entity.getBillRepaymentTime();
        this.status = entity.getStatus();
        this.repaymentMoney = entity.getRepaymentMoney();
        this.consumptionMoney = entity.getConsumptionMoney();
        this.consumptionTime = entity.getConsumptionTime();
        this.consumptionStatus = entity.getConsumptionStatus();
        this.exceptionDescribe = entity.getExceptionDescribe();
    }

    public String getBillRepaymentMoney() {
        return billRepaymentMoney;
    }

    public void setBillRepaymentMoney(String billRepaymentMoney) {
        this.billRepaymentMoney = billRepaymentMoney;
    }

    public String getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(String repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public String getBillRepaymentTime() {
        return billRepaymentTime;
    }

    public void setBillRepaymentTime(String billRepaymentTime) {
        this.billRepaymentTime = billRepaymentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRepaymentMoney() {
        return repaymentMoney;
    }

    public void setRepaymentMoney(String repaymentMoney) {
        this.repaymentMoney = repaymentMoney;
    }

    public String getConsumptionMoney() {
        return consumptionMoney;
    }

    public void setConsumptionMoney(String consumptionMoney) {
        this.consumptionMoney = consumptionMoney;
    }

    public List<PlanDetailsEntity> getConsume() {
        return consume;
    }

    public void setConsume(List<PlanDetailsEntity> consume) {
        this.consume = consume;
    }

    public String getConsumptionTime() {
        return consumptionTime;
    }

    public void setConsumptionTime(String consumptionTime) {
        this.consumptionTime = consumptionTime;
    }

    public String getConsumptionStatus() {
        return consumptionStatus;
    }

    public void setConsumptionStatus(String consumptionStatus) {
        this.consumptionStatus = consumptionStatus;
    }

    public int getLastOne() {
        return lastOne;
    }

    public void setLastOne(int lastOne) {
        this.lastOne = lastOne;
    }

    public String getExceptionDescribe() {
        return exceptionDescribe;
    }

    public void setExceptionDescribe(String exceptionDescribe) {
        this.exceptionDescribe = exceptionDescribe;
    }
}
