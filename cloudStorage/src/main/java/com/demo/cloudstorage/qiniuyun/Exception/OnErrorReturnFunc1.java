package com.demo.cloudstorage.qiniuyun.Exception;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.demo.cloudstorage.model.BaseReq;

import io.reactivex.functions.Function;

/**
 * 异常数据处理
 *
 * @author wangxiangyi
 * @Create 2018/9/7.
 */
public class OnErrorReturnFunc1<T> implements Function<Throwable, T> {


    private Context context;

    public OnErrorReturnFunc1() {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public T apply(Throwable throwable) {
        throwable.printStackTrace();
        return (T) new BaseReq();
    }
}
