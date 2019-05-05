package com.dykj.requestcore.retrofit.adapter;


import android.util.Log;

import com.dykj.requestcore.retrofit.Exception.LoginInconsistentException;
import com.dykj.requestcore.retrofit.Exception.NoneResultException;
import com.dykj.requestcore.retrofit.Exception.SessionExpireException;
import com.dykj.requestcore.util.RequestUtils;
import com.google.gson.Gson;
import com.lucky.model.response.ResponseData;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 处理返回数据
 *
 * @author wangxiangyi
 * @Create 2018/8/1.
 */
public class HandleResultDataFunc1<T> implements Function<String, Observable<ResponseData<T>>> {
    private boolean debug;
    private Gson gson;
    private String endPoint;
    private Type type;

    /**
     * @param debug    是否打印log日志
     * @param gson     解析库
     * @param endPoint 请求地址
     * @param type     需要解析的类型
     */
    public HandleResultDataFunc1(boolean debug, Gson gson, String endPoint, Type type) {
        this.debug = debug;
        this.gson = gson;
        this.endPoint = endPoint;
        this.type = type;
    }

    @Override
    public Observable<ResponseData<T>> apply(String s) {
        if (s == null) {
            return Observable.error(new NoneResultException());
        }
        if (debug) {
            Logger.w("原始数据..{" + endPoint + "}--> " + s);
        }
        ResponseData<T> stringResponseData = gson.fromJson(s, type);
        if (RequestUtils.sessionExpire(stringResponseData)) {
            return Observable.error(new SessionExpireException());
        } else if (RequestUtils.loginInconsistent(stringResponseData)) {
            return Observable.error(new LoginInconsistentException());
        } /*else if (RequestUtils.isEmpty(stringResponseData)) {
            return Observable.error(new NullDataException());
        }*/
        return Observable.just(stringResponseData);

    }
}
