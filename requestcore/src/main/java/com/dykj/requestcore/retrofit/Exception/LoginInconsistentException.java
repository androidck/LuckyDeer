package com.dykj.requestcore.retrofit.Exception;

/**
 * 账号在另一个设备登录
 *
 * @author wangxiangyi
 * @date 2018/11/15
 */
public class LoginInconsistentException extends RuntimeException {
    private String code;

    public LoginInconsistentException(String code, String message) {
        super(message);
        this.code = code;
    }

    public LoginInconsistentException() {
    }

    public LoginInconsistentException(String message) {
        super(message);
    }

    public LoginInconsistentException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginInconsistentException(Throwable cause) {
        super(cause);
    }
}
