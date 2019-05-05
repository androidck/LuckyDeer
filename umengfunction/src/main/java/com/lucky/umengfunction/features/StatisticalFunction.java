package com.lucky.umengfunction.features;

import android.content.Context;
import android.text.TextUtils;

import com.umeng.analytics.MobclickAgent;

/**
 * 统计功能
 *
 * @author wangxiangyi
 * @date 2018/12/07
 */
public class StatisticalFunction {
    private static StatisticalFunction initance;
    private static Context mContext;
    /**
     * 正常
     */
    public static final String E_UM_NORMAL = "E_UM_NORMAL";
    /**
     * 比赛场景
     */
    public static final String E_UM_GAME = "E_UM_GAME";
    public static final String E_DUM_NORMAL = "E_DUM_NORMAL";
    /**
     * 游戏场景
     */
    public static final String E_DUM_GAME = "E_DUM_GAME";

    public static StatisticalFunction getInstance(Context context) {
        mContext = context;
        if (initance == null) {
            synchronized (StatisticalFunction.class) {
                if (initance == null) {
                    initance = new StatisticalFunction();
                }
            }
        }
        return initance;
    }

    /**
     * 设置使用场景
     *
     * @param type 使用场景：<p>
     *             E_UM_NORMAL: 正常<p>
     *             E_UM_GAME：比赛场景<p>
     *             E_DUM_GAME：游戏场景
     */
    public StatisticalFunction setScenarioType(String type) {
        MobclickAgent.EScenarioType mType;
        switch (type) {
            case E_UM_NORMAL:
                mType = MobclickAgent.EScenarioType.E_UM_NORMAL;
                break;
            case E_UM_GAME:
                mType = MobclickAgent.EScenarioType.E_UM_GAME;
                break;
            case E_DUM_NORMAL:
                mType = MobclickAgent.EScenarioType.E_DUM_NORMAL;
                break;
            case E_DUM_GAME:
                mType = MobclickAgent.EScenarioType.E_DUM_GAME;
                break;
            default:
                mType = MobclickAgent.EScenarioType.E_UM_NORMAL;
                break;
        }
        /*使用场景：普通统计场景，如果您在埋点过程中没有使用到U-Game统计接口，请使用普通统计场景。 */
        MobclickAgent.setScenarioType(mContext, mType);
//        /*设置测试模式*/
//        MobclickAgent.setDebugMode(true);
//        /*检测设备*/
//        MobclickAgent.setCheckDevice(true);
        return this;
    }

    /**
     * 设置使用场景
     *
     * @param time 间隔时长（单位：秒）
     */
    public StatisticalFunction setSessionContinueMillis(int time) {
        /*将默认Session间隔时长改为60秒。*/
        MobclickAgent.setSessionContinueMillis(1000 * time);
        return this;
    }

    /**
     * 当用户使用自有账号登录时
     *
     * @param userID 用户id
     */
    public void setProfileSignIn(String userID) {
        if (TextUtils.isEmpty(userID)) {
            return;
        }
        MobclickAgent.onProfileSignIn(userID);
    }

    /**
     * 当用户使用自有账号登录时
     *
     * @param loginName 第三方登录名称
     * @param userID    用户id
     */
    public void setProfileSignIn(String loginName, String userID) {
        if (TextUtils.isEmpty(loginName) || TextUtils.isEmpty(userID)) {
            return;
        }
        /*当用户使用第三方账号登录*/
        MobclickAgent.onProfileSignIn(loginName, userID);
    }

    /**
     * 登出账户统计
     */
    public void setProfileSignOff() {
        /*登出*/
        MobclickAgent.onProfileSignOff();
    }

    public void onResume() {
        MobclickAgent.onResume(mContext);
    }

    public void onPause() {
        MobclickAgent.onPause(mContext);
    }
}
