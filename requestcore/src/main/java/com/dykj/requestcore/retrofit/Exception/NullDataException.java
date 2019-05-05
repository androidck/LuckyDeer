package com.dykj.requestcore.retrofit.Exception;

/**
 * 返回成功data没有数据异常
 *
 * @author wangxiangyi
 * @date 2018/11/10
 */
public class NullDataException extends RuntimeException {
    private Integer code;

    public NullDataException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public NullDataException() {
    }

    public NullDataException(String message) {
        super(message);
    }

    public NullDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullDataException(Throwable cause) {
        super(cause);
    }

    public Integer getCode() {
        return code;
    }
}
