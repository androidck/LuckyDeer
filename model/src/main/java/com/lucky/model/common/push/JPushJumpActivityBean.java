package com.lucky.model.common.push;

import java.io.Serializable;

/**
 * 推送通知参数
 *
 * @author wangxiangyi
 * @date 2018/11/21
 */
public class JPushJumpActivityBean implements Serializable {
    /**
     * platform : all
     * audience : {"registration_id":["170976fa8aaf8c34793"]}
     * notification : {"ios":{"alert":"我的通知消息","extras":{"schema":""},"badge":"+1","sound":"sound.caf","content-available":1},"android":{"alert":"我的通知消息","extras":{"sss":""},"title":"您有新办消息，请注意查收！"}}
     * message : {"title":"我的通知消息","msg_content":"您好，您信用卡于2018-10-15被他人刷卡套现清空！","extras":{"message extras key":""}}
     * options : {"sendno":1,"time_to_live":86400,"apns_production":false}
     */
    /**
     * 推送送方式
     */
    private String platform;
    /**
     * 听众信息
     */
    private AudienceBean audience;
    /**
     * 通知信息
     */
    private NotificationBean notification;
    /**
     * 推送信息
     */
    private MessageBean message;
    /**
     * 选项
     */
    private OptionsBean options;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public AudienceBean getAudience() {
        return audience;
    }

    public void setAudience(AudienceBean audience) {
        this.audience = audience;
    }

    public NotificationBean getNotification() {
        return notification;
    }

    public void setNotification(NotificationBean notification) {
        this.notification = notification;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public OptionsBean getOptions() {
        return options;
    }

    public void setOptions(OptionsBean options) {
        this.options = options;
    }
}
