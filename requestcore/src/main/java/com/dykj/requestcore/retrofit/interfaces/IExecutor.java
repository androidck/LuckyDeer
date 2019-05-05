package com.dykj.requestcore.retrofit.interfaces;


import com.lucky.model.request.BaseReq;
import com.lucky.model.response.ResponseData;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;


/**
 * 调用接口
 *
 * @author wangxiangyi
 * @Create 2018/7/31.
 */
public interface IExecutor {

    IExecutor setIExecutorAdapter(IExecutorAdapter executorAdapter);

    /**
     * @param endPoint 接口地址
     */
    <T> Observable<ResponseData<T>> getExecutor(String endPoint, Type type);

    /**
     * @param endPoint 接口地址
     * @param param    参数
     */
    <T> Observable<ResponseData<T>> executor(String endPoint, BaseReq param);

    /**
     * @param url  接口地址
     * @param type 返回的数据的类型
     */
    <T> Observable<ResponseData<T>> executor(String url, List<File> files, Type type);

    /**
     * @param endPoint 接口地址
     * @param param    参数
     */
    <T> Observable<ResponseData<T>> executor(String endPoint, HashMap<String, Object> param);

    /**
     * @param endPoint 接口地址
     * @param param    参数
     * @param type     返回的数据的类型
     */
    <T> Observable<ResponseData<T>> executor(String endPoint, BaseReq param, Type type);

    /**
     * @param endPoint 接口地址
     * @param param    参数
     * @param type     返回的数据的类型
     */
    <T> Observable<ResponseData<T>> executor(String endPoint, HashMap<String, Object> param, Type type);

    /**
     * 请求执行者
     *
     * @param endPoint 接口地址
     * @param param    参数
     * @param files    文件
     * @param type     返回的数据的类型
     */
    <T> Observable<ResponseData<T>> executor(String endPoint, BaseReq param, List<File> files, Type type);

    /**
     * @param url   接口地址
     * @param param 参数
     * @param type  返回的数据的类型
     */
    <T> Observable<ResponseData<T>> executor(String url, HashMap<String, Object> param, List<File> files, Type type);


}