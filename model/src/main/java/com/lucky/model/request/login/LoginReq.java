package com.lucky.model.request.login;


import com.lucky.model.request.BaseReq;

/**
 * 登录、注册参数
 *
 * @author wangxiangyi
 * @date 2018/9/19.
 */
public class LoginReq extends BaseReq {
    /**
     * 标志位<p>
     * 1：账号管理：设置登录密码<p>
     * 2：账号管理：修改登录密码<p>
     * 3：账号管理：忘记登录密码<p>
     * 4：账号管理：设置支付密码<p>
     * 5：账号管理：修改支付密码<p>
     * 6：账号管理：忘记支付密码
     */
    private int flag=0;
    /**
     * 账号（手机号）
     */
    private String loginName;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 新密码
     */
    private String newPwd;
    /**
     * 验证码id
     */
    private String codeId;
    /**
     * 验证码
     */
    private String code;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 推荐人id
     */
    private String recommendCode;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 手机号
     */
    public String phone;
    /**
     * 验证码使用场景
     */
    private String codeUse;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCodeUse() {
        return codeUse;
    }

    public void setCodeUse(String codeUse) {
        this.codeUse = codeUse;
    }
}
