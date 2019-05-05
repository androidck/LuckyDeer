package com.demo.cloudstorage.qiniuyun;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.demo.cloudstorage.CloudstorageKeyConstant;
import com.demo.cloudstorage.R;
import com.demo.cloudstorage.qiniuyun.Exception.OnErrorReturnFunc1;
import com.demo.cloudstorage.token.ObtainToken;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.NetReadyHandler;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 请求上传
 *
 * @author wangxiangyi
 * @date 2018/09/06
 */
public class RequestExecutot implements IExecutor {


    @Override
    public <T> Observable<T> executor(Map<String, String> map, String fileName, String filePath, String prefix, String suffix, Type type) {
        return executor(map, fileName, new File(filePath), prefix, suffix, type);
    }

    @Override
    public <T> Observable<T> executor(Map<String, String> map, String fileName, String filePath, String prefix, Type type) {
        return executor(map, fileName, new File(filePath), prefix, null, type);
    }

    @Override
    public <T> Observable<T> executor(Map<String, String> map, String fileName, String filePath, Type type) {
        return executor(map, fileName, new File(filePath), null, null, type);
    }

    @Override
    public <T> Observable<T> executor(String fileName, String filePath, String prefix, String suffix, Type type) {
        return executor(new HashMap<>(), fileName, new File(filePath), prefix, suffix, type);
    }

    @Override
    public <T> Observable<T> executor(String fileName, String filePath, String prefix, Type type) {
        return executor(new HashMap<>(), fileName, new File(filePath), prefix, null, type);
    }

    @Override
    public <T> Observable<T> executor(String fileName, String filePath, Type type) {
        return executor(new HashMap<>(), fileName, new File(filePath), null, null, type);
    }

    @Override
    public <T> Observable<T> executor(Map<String, String> map, String fileName, File file, String prefix, Type type) {
        return executor(map, fileName, file, prefix, null, type);
    }

    @Override
    public <T> Observable<T> executor(Map<String, String> map, String fileName, File file, Type type) {
        return executor(map, fileName, file, null, null, type);
    }

    @Override
    public <T> Observable<T> executor(String fileName, File file, String prefix, String suffix, Type type) {
        return executor(null, fileName, file, prefix, null, type);
    }

    @Override
    public <T> Observable<T> executor(String fileName, File file, String prefix, Type type) {
        return executor(null, fileName, file, prefix, null, type);
    }

    @Override
    public <T> Observable<T> executor(String fileName, File file, Type type) {
        return executor(null, fileName, file, null, null, type);
    }

    @Override
    public <T> Observable<T> executor(Map<String, String> map,
                                      String fileName,
                                      File fileType,
                                      String prefix,
                                      String suffix,
                                      final Type type) {
        /*获取文件名*/
        String originalFileName = fileType.getName();
        /*拆分字符串*/
        int end = originalFileName.lastIndexOf(".");
        String originalPrefix = originalFileName.substring(0, end);
        String originalSuffix = originalFileName.substring(end, originalFileName.length());
        /*获取请求初始化*/
        UploadManager uploadManager = InitFileExecutor.getInstance()
                .initUploadManager(
                        TextUtils.isEmpty(prefix) ? originalPrefix : prefix,
                        TextUtils.isEmpty(suffix) ? originalSuffix : suffix);
        /*获取文件备注内容*/
        HashMap<String, String> hashMap = new HashMap<>();
        for (String s : map.keySet()) {
            hashMap.put(("x:" + s), hashMap.get(s));
        }
        return Observable.create((ObservableOnSubscribe<T>) emitter -> {
            /*
              第一个参数：	数据，可以是 byte 数组、文件路径、文件
              第二个参数：保存的文件名称
              第三个参数：token
              第四个参数：上传回调函数
              第五个参数：如果需要进度通知、crc 校验、中途取消、指定 mimeType，则需要填写相应字段
             */
            uploadManager.put(
                    fileType,
                    TextUtils.isEmpty(fileName) ? originalPrefix : fileName,
                    ObtainToken.getToken(CloudstorageKeyConstant.QINIU_SPACE_NAME),
                    new UpCompletionHandler() {
                        @SuppressLint("CheckResult")
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject response) {
                            if (InitFileExecutor.isDebug()) {
                                Logger.w(InitFileExecutor.getContext().getString(R.string.img_info) + key +
                                        InitFileExecutor.getContext().getString(R.string.response_info) + info +
                                        InitFileExecutor.getContext().getString(R.string.return_data) + response);
                            }
                            if (info.isOK()) {
                                emitter.onNext(new Gson().fromJson(response.toString(), type));
                            } else {
                                emitter.onError(new Throwable(info.error));
                            }
                        }
                    },
                    new UploadOptions(map, null, false,
                            new UpProgressHandler() {
                                @Override
                                public void progress(String key, double percent) {
//                                    (percent * 1000);
                                }
                            },
                            new UpCancellationSignal() {
                                @Override
                                public boolean isCancelled() {
                                    return CloudstorageKeyConstant.isCancelled;
                                }
                            },
                            new NetReadyHandler() {
                                @Override
                                public void waitReady() {
                                    emitter.onError(new Throwable("network anomaly"));
                                }
                            })
            );
        })
                //在异步 上跑
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                /*异常处理*/
                .onErrorReturn(new OnErrorReturnFunc1());
    }
}
