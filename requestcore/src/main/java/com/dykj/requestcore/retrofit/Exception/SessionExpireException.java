package com.dykj.requestcore.retrofit.Exception;

/**
 * 登录失效异常
 *
 * @author wangxiangyi
 * @date 2018/11/15
 */
public class SessionExpireException extends RuntimeException {
    private String code;

    public SessionExpireException(String code, String message) {
        super(message);
        this.code = code;
    }

    public SessionExpireException() {
    }

    public SessionExpireException(String message) {
        super(message);
    }

    public SessionExpireException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionExpireException(Throwable cause) {
        super(cause);
    }
}
