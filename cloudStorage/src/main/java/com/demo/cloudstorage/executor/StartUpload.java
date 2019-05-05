package com.demo.cloudstorage.executor;


import com.demo.cloudstorage.model.BaseReq;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;


/**
 * 请求上传信息
 */
public class StartUpload extends BaseProxy {
    public static StartUpload mInstance = null;

    public static StartUpload getInstance() {
        if (mInstance == null) {
            synchronized (StartUpload.class) {
                if (mInstance == null) {
                    mInstance = new StartUpload();
                }
            }
        }
        return mInstance;
    }

    public <T> Observable<BaseReq> upload(String fileName, String file, String prefix, String suffix) {
        return executor.executor(fileName, file, prefix, suffix, new TypeToken<BaseReq>() {
        }.getType());
    }

    public <T> Observable<BaseReq> upload(String fileName, String file) {
        return executor.executor(fileName, file, new TypeToken<BaseReq>() {
        }.getType());
    }
}
