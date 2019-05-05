package com.lucky.deer.recognition.Package.identify.adapter;


import com.google.gson.Gson;
import com.lucky.model.response.ResponseData;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 返回数据数据
 */
public class ReturnDataProcessingAdapter<T> implements Function<String, Observable<ResponseData<T>>> {
    private Gson gson;
    private Type type;
    private Boolean isDebug;

    public ReturnDataProcessingAdapter(Gson gson, Type type, Boolean isDebug) {
        this.gson = gson;
        this.type = type;
        this.isDebug = isDebug;
    }


    @Override
    public Observable<ResponseData<T>> apply(String s) {
        try {
            if (isDebug) {
                Logger.w("返回原始数据：" + s);
            }
            ResponseData<T> data = gson.fromJson(s, type);
            return Observable.just(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
