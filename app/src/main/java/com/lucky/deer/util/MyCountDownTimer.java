package com.lucky.deer.util;

import android.os.CountDownTimer;

/**
 * 倒计时管理器
 */
public class MyCountDownTimer {
    private static MyCountDownTimer instance;
    CountDownTimer count;

    public static MyCountDownTimer getInstance() {
        if (instance == null) {
            synchronized (MyCountDownTimer.class) {
                if (instance == null) {
                    instance = new MyCountDownTimer();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化倒计时
     *
     * @param millisInFuture 倒计时时间
     * @param listener       监听
     */
    public MyCountDownTimer initCountDownTimer(long millisInFuture, onCountDownTimerListener listener) {
        count = new CountDownTimer(millisInFuture, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                listener.onTick(millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                listener.onFinish();
            }
        };
        return this;
    }

    /**
     * 启动倒计时
     */
    public void start() {
        count.start();
    }

    /**
     * 取消倒计时
     */
    public void cancel() {
        count.cancel();
    }


}
