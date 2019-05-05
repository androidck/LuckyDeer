package com.demo.cloudstorage.executor;


import com.demo.cloudstorage.qiniuyun.IExecutor;
import com.demo.cloudstorage.qiniuyun.RequestExecutot;

/**
 * 执行接口
 */
public class BaseProxy {
    //请求执行者接口
    protected IExecutor executor;

    public BaseProxy() {
        executor = new RequestExecutot();
    }
}
