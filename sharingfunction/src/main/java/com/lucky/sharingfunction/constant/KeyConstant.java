package com.lucky.sharingfunction.constant;

/**
 * 常量
 *
 * @author wangxiangyi
 * @date 2018/09/29
 */
public class KeyConstant {

    /**
     * QQ分享kay
     */
    public static final String APP_ID_QQ = "101557006";
    public static final String SCOPE_QQ = "all";
    /**
     * 微信分享key
     */
    public static final String APP_ID_WE_CHAT = "wx99455f271a8f661c";
    public static final String SCOPE_WE_CHAT = "snsapi_userinfo";
    public static final String STATE_WE_CHAT = "wechat_sdk_demo";
    /**
     * 微博分享key
     */
    public static final String APP_ID_WEI_BO = "3427725421";
    /**
     * 重定向网址
     */
    public static final String REDIRECT_URL_WEI_BO = "https://api.weibo.com/oauth2/default.html";
    /**
     * 微博分享范围
     */
    public static final String SCOPE_WEI_BO = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
    /**
     * 钉钉分享key
     */
    public static final String APP_ID_NAIL = "dingoay88dnrtmoplgmpj9";
    /**
     * 支付宝分享key
     */
    public static final String APP_ID_ALIPAY = "2015060900117932";
//    public static final String APP_ID_ALIPAY = "2018092861542758";


    /**
     * 微信
     */
    public static final int TAG_SHARE_WE_CHAT = 0;
    /**
     * 朋友圈
     */
    public static final int TAG_SHARE_FRIENDS_CIRCLE = 1;
    /**
     * QQ
     */
    public static final int TAG_SHARE_QQ_SPACE = 2;
    /**
     * QQ空间
     */
    public static final int TAG_SHARE_QQ_ZONE = 3;
    /**
     * 微博
     */
    public static final int TAG_SHARE_WEI_BO = 5;
    /**
     * 钉钉
     */
    public static final int TAG_SHARE_NAIL = 6;
    /**
     * 支付宝
     */
    public static final int TAG_SHARE_ALIPAY = 7;


    /**
     * 私信
     */
    public static final int TAG_SHARE_CHAT = 300;
    /**
     * 本地
     */
    public static final int TAG_SHARE_LOCAL = 400;


}
