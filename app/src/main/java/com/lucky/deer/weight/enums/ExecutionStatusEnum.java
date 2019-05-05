package com.lucky.deer.weight.enums;

import android.text.TextUtils;

import com.lucky.deer.R;


/**
 * 日期养卡执行状态
 */
public enum ExecutionStatusEnum {
    /*执行计划状态：
    状态:1.执行中、2.已完成、3.用户撤销(用户操作)、4.异常关闭(系统操作)、5.制定中（制定完成后执行中）、6.执行异常、7：未完全成功、8：用户中止
    规范：1 执行中  2&7：已完成 3&8：已中止   4&6 异常*/
    /*1：执行中*/
    Repayment("1", "执行中", R.mipmap.payment_btn, "1", "blue"),
    /*2：已完成*/
    completed("2", "已完成", R.mipmap.complete_btn, "1", "green"),
    /*3：用户撤销(用户操作)*/
    User_revocation("3", "用户撤销", R.mipmap.terminated_btn, "1", "red"),
    /*4：异常关闭(系统操作)*/
    Abnormal_shutdown("4", "异常关闭", R.mipmap.abnormal_btn, "1", "red"),
    /*5：制定中（制定完成后执行中）*/
    Under_development("5", "制定中", R.mipmap.develop_btn, "1", "blue"),
    /*6：执行异常*/
    Execution_exception("6", "执行异常", R.mipmap.abnormal_btn, "1", "red"),
    /*7：未完全成功*/
    NOT_COMPLETELY_SUCCESSFUL("7", "未完全成功", R.mipmap.complete_btn, "1", "green"),
    /*8：用户中止*/
    TERMINATION("8", "终止", R.mipmap.terminated_btn, "1", "red"),
    /*9：已完成终止*/
    TERMINATION_complete("9", "已完成终止", R.mipmap.termination_btn, "1", "red"),
    /*10：已终止，退款成功*/
    TERMINATION_Refund_successfully("10", " 已终止，退款成功", R.mipmap.refund_success_btn, "1", "red"),
    /*11：已终止，退款失败*/
    TERMINATION_Refund_failed("11", "已终止，退款失败", R.mipmap.refund_failure_btn, "1", "red"),
    /*12：已终止，退款处理中*/
    TERMINATION_Refund_processing("12", "已终止，退款处理中", R.mipmap.refund_processing_btn, "1", "red"),


    /*养卡名称*/
    date_repayment("1", R.string.date_repayment, "2"),
    perfect_repayment("2", R.string.perfect_repayment, "2"),
    balance_repayment("3", R.string.balance_repayment, "2"),
    custom_repayment("4", R.string.custom_repayment, "2"),
    /*养卡状态*/
    Repayment_status_carry_out("1", "完成", "3", "green"),
    Repayment_status_failure("2", "失败", "3", "red"),
    Repayment_status_Mandatory_payment("3", "强制代付", "3", "red"),
    Repayment_status_Executing("4", "执行中", "3", "blue"),
    Repayment_status_Pending_inquiry("5", "待查询", "3", "blue"),

    /*消费状态（账单详情）
     消费表
     状态：1：完成、2：失败、3：挂起、4：执行中、5：待查询、6：已处理
     规范：1：完成、2：失败、3&5：待处理、4&6 执行中*/
    /*1：完成*/
    Consumption_status_already_consumed("1", "已消费", "4", R.color.color_green_19db08),
    /*2：失败*/
    Consumption_status_consumption_failure("2", "消费失败", "4", R.color.color_red_ff0000),
    /*3：挂起*/
    Consumption_status_consumption_failure1("3", "消费失败，待处理", "4", R.color.color_red_ff0000),
    /*4：执行中*/
    Consumption_status_pending_execution("4", "待执行", "4", R.color.color_blue_005eff),
    /*5：待查询*/
    Consumption_status_pending_query("5", "消费失败，待处理", "4", R.color.color_red_ff0000),
    /*6：已处理*/
    Consumption_status_processed("6", "待执行", "4", R.color.color_blue_005eff),
    /*养卡状态（账单详情）
      养卡表：
      状态：1：完成、2：挂起、3：强制代付、4：执行中、5：待查询、6：关闭、7：关闭
      规范：1&3 完成    2&6 失败  4&5&7 执行中*/
    Consumption_status_already_consumed1("1", "已养卡", "5", R.color.color_green_19db08),
    Consumption_status_consumption_failure2("2", "养卡失败", "5", R.color.color_red_ff0000),
    Consumption_status_consumption_failure13("3", "已养卡", "5", R.color.color_green_19db08),
    Consumption_status_pending_execution4("4", "待执行", "5", R.color.color_blue_005eff),
    Consumption_status_pending_execution5("5", "待执行", "5", R.color.color_blue_005eff),
    Consumption_status_shut_down("6", "养卡失败", "5", R.color.color_red_ff0000),
    Consumption_status_pending_execution7("7", "待执行", "5", R.color.color_blue_005eff),

    /*获取默认头像*/
    default_avatar_male("1", "男", "6", R.mipmap.photo_default),
    default_avatar_female("2", "女", "6", R.mipmap.photo_default2);


    /**
     * 执行计划状态
     */
    public static String implementation_plan = "1";
    /**
     * 养卡计划名称
     */
    public static String repayment_name = "2";
    /**
     * 养卡详情状态
     */
    public static String repayment_status = "3";
    /**
     * 消费状态（账单详情）
     */
    public static String consumption_status = "4";
    /**
     * 养卡状态（账单详情）
     */
    public static String consumption_status1 = "5";
    /**
     * 获取默认头像
     */
    public static String defaultAvatar = "6";
    /**
     * 执行类型
     */
    private String type;
    /**
     * 执行编号
     */
    private String status;
    /**
     * 执行名称
     */
    private String statusName;
    /**
     * 执行背景样式
     */
    private int statusStyle;
    /**
     * 执行背景颜色
     */
    private String statusColor;
    /**
     * 执行状态字体颜色
     */
    private int textStatusColor;
    /**
     * 菜单名称
     */
    private int menuName;

    ExecutionStatusEnum(String status, int menuName, String type) {
        this.status = status;
        this.menuName = menuName;
        this.type = type;
    }

    ExecutionStatusEnum(String status, String statusName, String type, String statusColor) {
        this.status = status;
        this.statusName = statusName;
        this.type = type;
        this.statusColor = statusColor;
    }

    ExecutionStatusEnum(String status, String statusName, String type, int textStatusColor) {
        this.status = status;
        this.statusName = statusName;
        this.type = type;
        this.textStatusColor = textStatusColor;
    }

    ExecutionStatusEnum(String status, String statusName, int statusStyle, String type, String statusColor) {
        this.status = status;
        this.statusName = statusName;
        this.statusStyle = statusStyle;
        this.statusColor = statusColor;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusName() {
        return statusName;
    }

    public int getStatusStyle() {
        return statusStyle;
    }

    public String getStatusColor() {
        return statusColor;
    }

    public int getTextStatusColor() {
        return textStatusColor;
    }

    public int getMenuName() {
        return menuName;
    }

    /**
     * 根据执行编码获取执行状态
     *
     * @return
     */
    public static String getSearchStatusName(String type, String status) {
        if (!TextUtils.isEmpty(status)) {
            for (ExecutionStatusEnum item : ExecutionStatusEnum.values()) {
                if (item.getType().equals(type) && item.getStatus().equals(status)) {
                    return item.getStatusName();
                }
            }
        }
        return "";
    }

    /**
     * 根据执行编码获取执行状态背景
     *
     * @return
     */
    public static int getSearchStatusStyle(String type, String status) {
        if (!TextUtils.isEmpty(status)) {
            for (ExecutionStatusEnum item : ExecutionStatusEnum.values()) {
                if (item.getType().equals(type) && item.getStatus().equals(status)) {
                    return item.getStatusStyle();
                }
            }
        }
        return 0;
    }

    /**
     * 根据执行编码获取执行状态字体颜色
     *
     * @return
     */
    public static int getSearchStatusTextColor(String type, String status) {
        if (!TextUtils.isEmpty(status)) {
            for (ExecutionStatusEnum item : ExecutionStatusEnum.values()) {
                if (item.getType().equals(type) && item.getStatus().equals(status)) {
                    return item.getTextStatusColor();
                }
            }
        }
        return Consumption_status_pending_execution.getTextStatusColor();
    }

    /**
     * 根据执行编码获取默认头像
     *
     * @return
     */
    public static int getSearchStatusDefaultAvatar(String type, String status) {
        if (!TextUtils.isEmpty(status)) {
            for (ExecutionStatusEnum item : ExecutionStatusEnum.values()) {
                if (item.getType().equals(type) && item.getStatus().equals(status)) {
                    return item.getTextStatusColor();
                }
            }
        }
        return default_avatar_male.getTextStatusColor();
    }

    /**
     * 根据执行编码获取执行状态颜色
     *
     * @return
     */
    public static String getSearchStatusColor(String type, String status) {
        if (!TextUtils.isEmpty(status)) {
            for (ExecutionStatusEnum item : ExecutionStatusEnum.values()) {
                if (item.getType().equals(type) && item.getStatus().equals(status)) {
                    return item.getStatusColor();
                }
            }
        }
        return "";
    }

    /**
     * 根据执行编码获取养卡菜单名称
     *
     * @return
     */
    public static int getSearchMenuName(String type, String status) {
        if (!TextUtils.isEmpty(status)) {
            for (ExecutionStatusEnum item : ExecutionStatusEnum.values()) {
                if (item.getType().equals(type) && item.getStatus().equals(status)) {
                    return item.getMenuName();
                }
            }
        }
        return 0;
    }


}
