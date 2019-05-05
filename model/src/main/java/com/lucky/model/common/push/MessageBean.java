package com.lucky.model.common.push;

import java.io.Serializable;

/**
 * 推送信息
 *
 * @author wangxiangyi
 * @date 2018/11/21
 */
public class MessageBean implements Serializable {
    /**
     * title : 我的通知消息
     * msg_content : 您好，您信用卡于2018-10-15被他人刷卡套现清空！
     */
    /**
     * 通知标题
     */
    private String title;
    /**
     * 通知内容
     */
    private String msg_content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

}
