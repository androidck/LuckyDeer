package com.dykj.requestcore.retrofit.Exception;

/**
 * @author wangxiangyi
 * @Create 2018/8/3.
 */
public class NoneResultException extends RuntimeException{
    private String code;

    public NoneResultException(String code, String message) {
        super(message);
        this.code = code;
    }

    public NoneResultException() {
    }

    public NoneResultException(String message) {
        super(message);
    }

    public NoneResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoneResultException(Throwable cause) {
        super(cause);
    }
}
