package com.dykj.requestcore.server;


import com.dykj.requestcore.util.header.EnumHttpHeaderParam;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

/**
 * 定义请求方法
 *
 * @author wangxiangyi
 * @Create 2018/7/31.
 */
public interface RequestServes {
    @GET
    Observable<String> executor(@Url String url);

    /**
     * @param url 请求地址
     */
    @POST
    @FormUrlEncoded
    Observable<String> executor(
            @Url String url,
            @Field("field") String params
    );

    /**
     * @param url    请求地址
     * @param params 请求参数
     */
    @POST
    @FormUrlEncoded
    Observable<String> executor(
            @Url String url,
            @FieldMap HashMap<String, Object> params
    );

    @POST
    @FormUrlEncoded
    Observable<String> executor(
            @Url String url,
            @Header("X_UserId") String userId,
            @Header("X_Timestamp") Long timestamp,
            @Header("X_ServiceName") String serviceName,
            @Header("X_Signature") String signature,
            @Header("X_Devicetoken") String deviceToken,
            @Header("X_DeviceType") String deviceType,
            @Header("X_DeviceIMEL") String deviceIMEL,
            @Header("X_DeviceModel") String deviceModel,
            @Header("X_APPID") String appId,
            @Header("Authorization") String appCode,
            @FieldMap HashMap<String, Object> params
    );

    /**
     * @param url    请求地址
     * @param params 请求参数
     * @param files  上传文件
     */
    @POST
    @FormUrlEncoded
    Observable<String> executor(
            @Url String url,
            @Header("X_UserId") String userId,
            @Header("X_Timestamp") Long timestamp,
            @Header("X_ServiceName") String serviceName,
            @Header("X_Signature") String signature,
            @Header("X_Devicetoken") String deviceToken,
            @Header("X_DeviceType") String deviceType,
            @Header("X_DeviceIMEL") String deviceIMEL,
            @Header("X_DeviceModel") String deviceModel,
            @Header("X_APPID") String appId,
            @Header("Authorization") String appCode,
            @FieldMap HashMap<String, Object> params,
            @FieldMap HashMap<String, RequestBody> files
    );

    /**
     * @param url 请求地址
     */
    @POST
    @FormUrlEncoded
    Observable<String> executor(
            @Url String url,
            @Field("field") String params,
            @PartMap Map<String, RequestBody> files
    );

    /**
     * @param url   请求地址
     * @param files 上传文件
     */
    @POST
    @Multipart
    Observable<String> executor(
            @Url String url,
            @Header("X_UserId") String userId,
            @Header("X_Timestamp") Long timestamp,
            @Header("X_ServiceName") String serviceName,
            @Header("X_Signature") String signature,
            @Header("X_Devicetoken") String devicetoken,
            @Header("X_DeviceType") String deviceType,
            @Header("X_DeviceIMEL") String deviceIMEL,
            @Header("X_DeviceModel") String deviceModel,
            @Header("X_APPID") String appId,
            @Header("Authorization") String appCode,
            @PartMap Map<String, RequestBody> files
    );

    @POST
    Observable<String> executor(
            @Url String url,
            @Body RequestBody file);


}