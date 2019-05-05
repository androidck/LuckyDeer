package com.dykj.requestcore.execute;


import com.dykj.requestcore.retrofit.interfaces.IExecutor;
import com.dykj.requestcore.retrofit.RetrofitExecutor;

/**
 * Created by wangxiangyi on 2018/09/20.
 * 请求代理基类。
 * 采用的是接口编程，如果后期采用volley;
 * 比如是VolleyExecutor继承IExecutor实现接口里的方法。
 * 在这个直接替换掉RetrofitExecutor就可以啦，
 * 可以做到 并不关心具体的请求者是谁（retrofit、volley）。
 */
public class BaseProxy {
    //请求执行者接口
    protected IExecutor executor;

    public BaseProxy() {
        executor = new RetrofitExecutor()
                .init();
    }
}
