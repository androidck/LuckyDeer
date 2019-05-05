package com.lucky.model.response.find;

/**
 * 提现进度实体
 *
 * @author wamgxiangyi
 * @date 2018/11/16
 */
public class SubmitFindWithdrawalEntity {
    /**
     * 是否显示 上边虚线
     */
    private boolean lineTopView;
    /**
     * 上边虚线 是否显示绿色
     */
    private boolean isLineTopViewGreen;
    /**
     * 是否显示下边虚线
     */
    private boolean lineButtomView;
    /**
     * 下边虚线 是否显示绿色
     */
    private boolean isLineButtomViewGreen;
    /**
     * 进度状态图片
     */
    private int scheduleDot;

    /**
     * 进度状态结果
     */
    private String dealWithStatus;
    /**
     * 进度状态原因
     */
    private String dealWithReason;

    public boolean isLineTopView() {
        return lineTopView;
    }

    public void setLineTopView(boolean lineTopView) {
        this.lineTopView = lineTopView;
    }

    public boolean isLineTopViewGreen() {
        return isLineTopViewGreen;
    }

    public void setLineTopViewGreen(boolean lineTopViewGreen) {
        isLineTopViewGreen = lineTopViewGreen;
    }

    public boolean isLineButtomView() {
        return lineButtomView;
    }

    public void setLineButtomView(boolean lineButtomView) {
        this.lineButtomView = lineButtomView;
    }

    public boolean isLineButtomViewGreen() {
        return isLineButtomViewGreen;
    }

    public void setLineButtomViewGreen(boolean lineButtomViewGreen) {
        isLineButtomViewGreen = lineButtomViewGreen;
    }

    public int getScheduleDot() {
        return scheduleDot;
    }

    public void setScheduleDot(int scheduleDot) {
        this.scheduleDot = scheduleDot;
    }

    public String getDealWithStatus() {
        return dealWithStatus;
    }

    public void setDealWithStatus(String dealWithStatus) {
        this.dealWithStatus = dealWithStatus;
    }

    public String getDealWithReason() {
        return dealWithReason;
    }

    public void setDealWithReason(String dealWithReason) {
        this.dealWithReason = dealWithReason;
    }
}
