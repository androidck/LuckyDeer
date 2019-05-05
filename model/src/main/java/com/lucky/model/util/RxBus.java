package com.lucky.model.util;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * 初始化通知
 *
 * @author wangxiangyi
 * @date 2018/11/3
 */

public final class RxBus {
    private static volatile RxBus instance;
    private final Subject<Object> mBus;

    private RxBus() {
        /* toSerialized method made bus thread safe*/
        mBus = PublishSubject.create().toSerialized();
    }

    /**
     * 获取单利
     */
    public static RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    /**
     * 发送通知
     */
    public void post() {
        post("");
    }

    /**
     * 发送通知
     */
    public <T> void post(T t) {
        if (isHasObservers()) {
            mBus.onNext(t);
        }
    }

    /**
     * 通知监听
     *
     * @param tClass 接收的实体
     */
    public <T> Observable<T> toObservable(Class<T> tClass) {
        return mBus.subscribeOn(Schedulers.io())
                .ofType(tClass)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new ConsumerError());
    }

    /**
     * 通知监听
     */
    public Observable<String> toObservable() {
        return mBus.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Object, String>() {
                    @Override
                    public String apply(Object o) {
                        return String.valueOf(o);
                    }
                })
                .doOnError(new ConsumerError());
    }

    /**
     * 判断是否有观察者
     *
     * @return true：有观察者
     */
    private boolean isHasObservers() {
        return mBus.hasObservers();
    }

    /**
     * 异常处理
     */
    static class ConsumerError implements Consumer<Throwable> {

        @Override
        public void accept(Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
