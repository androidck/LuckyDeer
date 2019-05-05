package com.lucky.deer.recognition.Package.executor;

import com.lucky.deer.recognition.Package.identify.DocumentIdentify;
import com.lucky.deer.recognition.Package.identify.IExecutor;

/**
 * 执行接口
 */
public class BaseProxy {
    //请求执行者接口
    protected IExecutor requestExecutor;


    public BaseProxy() {
        requestExecutor = DocumentIdentify
                .getInstance()
                .initAccessTokenWithAkSk();
    }
}
