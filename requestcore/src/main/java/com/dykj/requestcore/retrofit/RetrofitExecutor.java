package com.dykj.requestcore.retrofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.dykj.requestcore.retrofit.Exception.OnErrorReturnFunc1;
import com.dykj.requestcore.retrofit.adapter.HandleResultDataFunc1;
import com.dykj.requestcore.retrofit.adapter.RetrofitDefaultExecutorAdapter;
import com.dykj.requestcore.retrofit.converter.ConverterFactoryPro;
import com.dykj.requestcore.retrofit.interfaces.IExecutor;
import com.dykj.requestcore.retrofit.interfaces.IExecutorAdapter;
import com.dykj.requestcore.server.RequestServes;
import com.dykj.requestcore.util.header.EnumHttpHeaderParam;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.lucky.model.request.BaseReq;
import com.lucky.model.response.ResponseData;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 初始化网络请求
 *
 * @author wangxiangyi
 * @Create 2018/7/31.
 */
public class RetrofitExecutor implements IExecutor {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static String mBaseUrl;
    private static Boolean mDebug;
    private RequestServes iService;
    private Gson gson;
    private IExecutorAdapter iExecutorAdapter;

    public static void onCreate(Context context, String baseUrl, Boolean debug) {
        mContext = context;
        mBaseUrl = baseUrl;
        mDebug = debug;
    }

    public RetrofitExecutor init() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
//                .registerTypeAdapter(Double.class, (JsonSerializer<Double>) (src, typeOfSrc, context) -> {
//                    if (src == src.longValue()) {
//                        return new JsonPrimitive(src.longValue());
//                    }
//                    return new JsonPrimitive(src);
//                })
                .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                    @Override
                    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                        if (src == src.longValue()) {
                            return new JsonPrimitive(src.longValue());
                        }
                        return new JsonPrimitive(src);
                    }
                })
                .create();
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        /*添加JSON解析的工厂*/
        retrofitBuilder.addConverterFactory(ConverterFactoryPro.create(gson));
        /*Retrofit添加RxJava支持*/
        retrofitBuilder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        // 针对rxjava2.x
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                /*设置连接超时时间*/
                .connectTimeout(60, TimeUnit.SECONDS)
                /*设置写入超时时间*/
                .writeTimeout(60, TimeUnit.SECONDS)
                /*设置读取超时时间*/
                .readTimeout(60, TimeUnit.SECONDS);
        okHttpClientBuilder.build().dispatcher().cancelAll();
        okHttpClientBuilder.build().connectionPool().evictAll();
        //判断请求地址是否是空
        if (TextUtils.isEmpty(mBaseUrl)) {
            throw new RuntimeException("baseUrl is null !!! ");
        }
        /*设置url*/
        retrofitBuilder.baseUrl(mBaseUrl);
        if (mDebug) {
            /*添加拦截器*/
            okHttpClientBuilder.addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        //把okhttp添加到retrofitBuilder里面
        retrofitBuilder.client(okHttpClientBuilder.build());
        //获取iService
        iService = retrofitBuilder.build().create(RequestServes.class);
        return this;
    }

    @Override
    public IExecutor setIExecutorAdapter(IExecutorAdapter executorAdapter) {
        this.iExecutorAdapter = executorAdapter;
        return this;
    }

    @Override
    public <T> Observable<ResponseData<T>> getExecutor(String endPoint, Type type) {
        return executor(endPoint, new BaseReq(), new HashMap<String, Object>(), new ArrayList<File>(), type, "GET");
    }

    /**
     * @param <T>      返回的数据的类型
     * @param endPoint 接口地址
     * @param param    以map形式传参
     */
    @Override
    public <T> Observable<ResponseData<T>> executor(String endPoint, HashMap<String, Object> param) {
        return executor(endPoint, new BaseReq(), param, null, null, "post");
    }

    @Override
    public <T> Observable<ResponseData<T>> executor(String endPoint, List<File> files, Type type) {
        return executor(endPoint, new BaseReq(), null, files, type, "post");
    }

    @Override
    public <T> Observable<ResponseData<T>> executor(String endPoint, BaseReq param, Type type) {
        return executor(endPoint, param, null, null, type, "post");
    }

    @Override
    public <T> Observable<ResponseData<T>> executor(String endPoint, HashMap<String, Object> param, Type type) {
        return executor(endPoint, new BaseReq(), param, null, type, "post");
    }

    @Override
    public <T> Observable<ResponseData<T>> executor(String endPoint, BaseReq param, List<File> files, Type type) {
        return executor(endPoint, param, null, files, type, "post");
    }

    @Override
    public <T> Observable<ResponseData<T>> executor(String endPoint, HashMap<String, Object> param, List<File> files, Type type) {
        return executor(endPoint, new BaseReq(), param, files, type, "post");
    }

    /**
     * @param <T>      返回的数据的类型
     * @param endPoint 接口地址
     * @param param    以map形式传参
     */
    @Override
    public <T> Observable<ResponseData<T>> executor(String endPoint, BaseReq param) {
        return executor(endPoint, param, null, null, null, "post");
    }

    @SuppressLint("NewApi")
    private <T> Observable<ResponseData<T>> executor(String url, BaseReq param, HashMap<String, Object> param2, List<File> files, final Type type, String isPost) {
        //数据处理适配器
        if (null == iExecutorAdapter) {
            iExecutorAdapter = new RetrofitDefaultExecutorAdapter();
        }
        HashMap<String, Object> handleParam = iExecutorAdapter.dealWithParams(mContext, param, param2, files);
        if (mDebug) {
            Object logFiles = "";
            if (handleParam.get("fileParams") != null) {
                logFiles = "\n 文件：" + handleParam.get("fileParams");
            }
            Logger.w("请求参数：{" + url + "} --> " + handleParam.get("mapParam") + logFiles);
        }
        Observable<String> observable = null;
        if ("GET".equals(isPost)) {
            observable = iService.executor(url);
        } else {
            /*判断是否传文件*/
            if (handleParam.get("fileParams") == null) {
                /*判断是否是传json数据*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    observable = iService.executor(url,
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(0)),
                            (Long) handleParam.get(EnumHttpHeaderParam.getHeaderParam(1)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(2)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(3)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(4)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(5)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(6)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(7)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(8)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(9)),
                            (HashMap<String, Object>) handleParam.get("mapParam"));
                }
            } else {
                if (handleParam.get("mapParam") == null) {
                    observable = iService.executor(url,
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(0)),
                            (Long) handleParam.get(EnumHttpHeaderParam.getHeaderParam(1)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(2)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(3)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(4)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(5)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(6)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(7)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(8)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(9)),
                            (HashMap<String, RequestBody>) handleParam.get("fileParams"));
                } else {
                    observable = iService.executor(url,
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(0)),
                            (Long) handleParam.get(EnumHttpHeaderParam.getHeaderParam(1)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(2)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(3)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(4)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(5)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(6)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(7)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(8)),
                            (String) handleParam.get(EnumHttpHeaderParam.getHeaderParam(9)),
                            (HashMap<String, Object>) handleParam.get("mapParam"),
                            (HashMap<String, RequestBody>) handleParam.get("fileParams"));
                }
            }
        }

        return observable
                //在异步 上跑
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                /*处理数据*/
                .flatMap(new HandleResultDataFunc1(mDebug, gson, url, type))
                /*异常处理*/
                .onErrorReturn(new OnErrorReturnFunc1(mContext));
    }
}
