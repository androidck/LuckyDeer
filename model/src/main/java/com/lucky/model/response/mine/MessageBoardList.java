package com.lucky.model.response.mine;

import java.io.Serializable;

/**
 * 留言板信息列表
 *
 * @author wangxiangyi
 * @date 2018/11/30
 */
public class MessageBoardList implements Serializable {

    /**
     * id : 844f400c3ec54e8a878afe3d46df200c
     * leaveMessageContent : 周军长你好！请问一下什么时候有卡下？
     * updateDate : 1543531634000
     * userNo : 000305
     * phone : 15588837763
     */
    /**
     * id
     */
    private String id;
    /**
     * 头像
     */
    private String userHead;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 留言内容
     */
    private String leaveMessageContent;
    /**
     * 创建时间
     */
    private long updateDate;
    /**
     * 用户账号
     */
    private String userNo;
    /**
     * 电话
     */
    private String phone;
    /**
     * 是否查看<p>
     * 1：已查看
     * 2：未查看
     */
    private String readState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickMame) {
        this.nickName = nickName;
    }

    public String getLeaveMessageContent() {
        return leaveMessageContent;
    }

    public void setLeaveMessageContent(String leaveMessageContent) {
        this.leaveMessageContent = leaveMessageContent;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReadState() {
        return readState;
    }

    public void setReadState(String readState) {
        this.readState = readState;
    }
}
