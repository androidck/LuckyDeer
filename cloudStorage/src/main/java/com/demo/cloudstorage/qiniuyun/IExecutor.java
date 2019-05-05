package com.demo.cloudstorage.qiniuyun;


import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 执行接口
 */
public interface IExecutor {


    /**
     * 上传文件请求
     *
     * @param map      文件信息
     * @param fileName 文件名称
     * @param filePath 文件路径
     * @param prefix   前缀名
     * @param suffix   后缀名
     * @param type     数据解析类型
     */
    <T> Observable<T> executor(Map<String, String> map, String fileName, String filePath, String prefix, String suffix, Type type);

    /**
     * 上传文件请求
     *
     * @param map      文件信息
     * @param fileName 文件名称
     * @param filePath 文件路径
     * @param prefix   前缀名
     * @param type     数据解析类型
     */
    <T> Observable<T> executor(Map<String, String> map, String fileName, String filePath, String prefix, Type type);

    /**
     * 上传文件请求
     *
     * @param map      文件信息
     * @param fileName 文件名称
     * @param filePath 文件路径
     * @param type     数据解析类型
     */
    <T> Observable<T> executor(Map<String, String> map, String fileName, String filePath, Type type);

    /**
     * 上传文件请求
     *
     * @param fileName 文件名称
     * @param filePath 文件路径
     * @param prefix   前缀名
     * @param suffix   后缀名
     * @param type     数据解析类型
     */
    <T> Observable executor(String fileName, String filePath, String prefix, String suffix, Type type);

    /**
     * 上传文件请求
     *
     * @param fileName 文件名称
     * @param filePath 文件路径
     * @param prefix   前缀名
     * @param type     数据解析类型
     */
    <T> Observable executor(String fileName, String filePath, String prefix, Type type);


    /**
     * 上传文件请求
     *
     * @param fileName 文件名称
     * @param filePath 文件路径
     * @param type     数据解析类型
     */
    <T> Observable executor(String fileName, String filePath, Type type);
    /**
     * 上传文件请求
     *  @param map      文件信息
     * @param fileName 文件名称
     * @param file 文件
     * @param prefix   前缀名
     * @param suffix   后缀名
     * @param type     数据解析类型
     */
    <T> Observable executor(Map<String, String> map, String fileName, File file, String prefix, String suffix, Type type);

    /**
     * 上传文件请求
     *
     * @param map      文件信息
     * @param fileName 文件名称
     * @param file 文件
     * @param prefix   前缀名
     * @param type     数据解析类型
     */
    <T> Observable<T> executor(Map<String, String> map, String fileName, File file, String prefix, Type type);

    /**
     * 上传文件请求
     *
     * @param map      文件信息
     * @param fileName 文件名称
     * @param file 文件
     * @param type     数据解析类型
     */
    <T> Observable<T> executor(Map<String, String> map, String fileName,File file,Type type);

    /**
     * 上传文件请求
     *
     * @param fileName 文件名称
     * @param file 文件
     * @param prefix   前缀名
     * @param suffix   后缀名
     * @param type     数据解析类型
     */
    <T> Observable executor(String fileName,  File file, String prefix, String suffix, Type type);

    /**
     * 上传文件请求
     *
     * @param fileName 文件名称
     * @param file 文件
     * @param prefix   前缀名
     * @param type     数据解析类型
     */
    <T> Observable executor(String fileName,  File file, String prefix, Type type);


    /**
     * 上传文件请求
     *
     * @param fileName 文件名称
     * @param file 文件
     * @param type     数据解析类型
     */
    <T> Observable executor(String fileName, File file, Type type);


}
