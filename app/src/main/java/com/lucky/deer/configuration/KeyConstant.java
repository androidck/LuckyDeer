package com.lucky.deer.configuration;

/**
 * 传值或保存数据key常量
 */
public class KeyConstant {
    /**
     * 文字识别AK
     */
    public static final String API_KEY = "eyUBnYtHGmbxSvXV76uu034u";
    /**
     * 文字识别SK
     */
    public static final String SECRET_KEY = "DQk7GzDvwCR2f7zMCdSY2FNC7fY4kWU1";
    /**
     * 身份证正面
     */
    public static final int REQUEST_CODE_CAMERA_POSITIVE = 1;

    public static final int REQUEST_CODE_CAMERA_NEGATIVE = 2;
    /**
     * 手持身份证拍照
     */
    public static final int HAND_HELD_IDENTITY_CARD = 3;
    /**
     * 储蓄卡正面
     */
    public static final int DEBIT_CARD_POSITIVE = 4;
    /**
     * 信用卡正面
     */
    public static final int CREDIT_CARD_POSITIVE = 5;
    /**
     * 查看头像
     */
    public static final int VIEW_AVATAR = 6;
    /**
     * 拍摄头像
     */
    public static final int SHOOTING_AVATAR = 7;
    /**
     * 相册选择头像
     */
    public static final int ALBUM_SELECT_AVATAR = 8;
    /**
     * 推广页面拍照
     */
    public static final int PROMOTION_PICTURE = 9;
    /**
     * 识别成功回调，通用文字识别（含位置信息）
     */
    public static final int REQUEST_CODE_GENERAL = 105;
    /**
     * 识别成功回调，通用文字识别
     */
    public static final int REQUEST_CODE_GENERAL_BASIC = 106;
    /**
     * 识别成功回调，通用文字识别（高精度版）
     */
    public static final int REQUEST_CODE_ACCURATE_BASIC = 107;
    /**
     * 识别成功回调，通用文字识别（含位置信息高精度版）
     */
    public static final int REQUEST_CODE_ACCURATE = 108;
    /**
     * 识别成功回调，通用文字识别（含生僻字版）
     */
    public static final int REQUEST_CODE_GENERAL_ENHANCED = 109;
    /**
     * 识别成功回调，网络图片文字识别
     */
    public static final int REQUEST_CODE_GENERAL_WEBIMAGE = 110;
    /**
     * 识别成功回调，银行卡识别
     */
    public static final int REQUEST_CODE_BANKCARD = 111;
    /**
     * 识别成功回调，行驶证识别
     */
    public static final int REQUEST_CODE_VEHICLE_LICENSE = 120;
    /**
     * 识别成功回调，驾驶证识别
     */
    public static final int REQUEST_CODE_DRIVING_LICENSE = 121;
    /**
     * 识别成功回调，车牌识别
     */
    public static final int REQUEST_CODE_LICENSE_PLATE = 122;
    /**
     * 识别成功回调，营业执照识别
     */
    public static final int REQUEST_CODE_BUSINESS_LICENSE = 123;
    /**
     * 识别成功回调，通用票据识别
     */
    public static final int REQUEST_CODE_RECEIPT = 124;
    /**
     * 识别成功回调，护照
     */
    public static final int REQUEST_CODE_PASSPORT = 125;
    /**
     * 识别成功回调，数字
     */
    public static final int REQUEST_CODE_NUMBERS = 126;
    /**
     * 识别成功回调，二维码
     */
    public static final int REQUEST_CODE_QRCODE = 127;
    /**
     * 识别成功回调，名片
     */
    public static final int REQUEST_CODE_BUSINESSCARD = 128;
    /**
     * 识别成功回调，手写
     */
    public static final int REQUEST_CODE_HANDWRITING = 129;
    /**
     * 识别成功回调，彩票
     */
    public static final int REQUEST_CODE_LOTTERY = 130;
    /**
     * 识别成功回调，增值税发票
     */
    public static final int REQUEST_CODE_VATINVOICE = 131;
    /**
     * 识别成功回调，自定义模板
     */
    public static final int REQUEST_CODE_CUSTOM = 132;

    /**
     * 手动识别身份证正面
     */
    public static final int REQUEST_CODE_PICK_IMAGE_FRONT = 201;
    /**
     *
     */
    public static final int REQUEST_CODE_PICK_IMAGE_BACK = 202;
    /**
     * 手动拍照身份证正面拍照
     */
    public static final int REQUEST_CODE_CAMERA = 102;
    public static final String DETAILS_TITLE = "details_title";

    /**
     * 文字识别是否获得token
     */
    public static boolean isGotToken = false;


    /*****************************************app内部key****************************************/
    /**
     * 保存版本编码
     */
    public static final String VERSION_CODE = "VersionCode";
    /**
     * 是否查看启动页广告
     * 0：没看广告
     * 1：看广告
     * 2：我的视频
     */
    public static final String IS_ADVERTISING = "isAdvertising";
    /**
     * 获取通讯录信息key
     */
    public static final int SELECT_ADDRESS_BOOK_INFO = 0;
    /**
     * 会员中心页面key
     */
    public static final String MEMBER_CENTRE = "memberCentre";
    /**
     * 刷卡消费页面key
     */
    public static final String CARD_SPENDING = "sardSpending";
    /**
     * 日期养卡页面key
     */
    public static final String DATE_REPAYMENT = "dateRepayment";
    /**
     * 完美养卡页面key
     */
    public static final String PERFECT_REPAYMENT = "perfectRepayment";
    /**
     * 日期养卡：制定计划页面key
     */
    public static final String MAKING_PLANS = "makingPlans";
    /**
     * 选择信用卡key
     */
    public static final int SELECT_CREDIT_CARD = 1;
    /**
     * 选择储蓄卡key
     */
    public static final int SELECT_DEBIT_CARD = 2;
    /**
     * 添加储蓄卡key
     */
    public static final String ADD_DEBIT_CARD = "addDebitCard";
    /**
     * 修改储蓄卡key
     */
    public static final String MODIFY_DEBIT_CARD = "modifyDebitCard";
    /**
     * 选择刷卡通道key
     */
    public static final int SELECT_AISLE = 3;
    /**
     * 总行所有信息
     */
    public static final String BANK_INFO = "BankInfo";
    /**
     * 通道ID
     */
    public static final String CHANNEL_ID = "channelId";
    /**
     * 信用卡ID
     */
    public static final String CREDIT_CARD_ID = "creditCardId";
    /**
     * 是否开通快捷支付
     */
    public static final String IS_OPEN_FAST_PAYMENT = "isOpenFastPayment";
    /**
     * 消费金额Key
     */
    public static final String AMOUNT_CONSUMPTION = "AmountConsumption";
    /**
     * 消费金额手续费key
     */
    public static final String HANDLING_FEE = "handlingFee";
    /**
     * 定位信息key
     */
    public static final String LOCATION_INFORMATION = "locationInformation";
    /**
     * 保存定位信息key
     */
    public static final String SAVE_LOCATION_INFORMATION = "100000";
    /**
     * 我的视频页面key
     */
    public static final String MY_VIDEO = "MyVideo";
    /**
     * 发布视频页面key
     */
    public static final String POST_VIDEO = "postVideo";
    /**
     * 我要网申key
     */
    public static final String MY_NEED_NET_APPLICATION = "myNeedNetApplication";
    /**
     * 我是业务员：完善信息——公司名称
     */
    public static final int COMPANY_NAME = 1;
    /**
     * 我是业务员：完善信息——公司介绍
     */
    public static final int COMPANY_PROFILE = 2;
    /**
     * 我是业务员：完善信息——业务介绍
     */
    public static final int BUSINESS_PROFILE = 3;
    /**
     * 我是业务员：招聘信息——招聘要求
     */
    public static final int RECRUITMENT_REQUIREMENTS = 4;
    /**
     * 定位检索key
     */
    public static final int POSITIONING_SEARCH_KEY = 1001;


    /****************************************自定义弹出框站屏幕高度key****************************************/
    /**
     * 不设置屏幕高度
     */
    public static final float CUSTOMIZE_DIALOG_NO_SCREEN_HEIGHT = 0;
    /**
     * 清理缓存：高度设置站屏幕的三分之一
     */
    public static final float CUSTOMIZE_DIALOG_SCREEN_HEIGHT_3 = 3;
    /**
     * 清理缓存：高度设置站屏幕的四分之一
     */
    public static final float CUSTOMIZE_DIALOG_SCREEN_HEIGHT_4 = 4;
    /**
     * 使用默认：高度设置站屏幕的五分之一
     */
    public static final float CUSTOMIZE_DIALOG_SCREEN_HEIGHT_5 = 5;

    /***************************************RxBus通知key*******************************************************/

    /**
     * 添加银行卡监听
     */
    public static final String NOTICE_ADD_BANK_CARD = "addBankCard";
    /**
     * 发布招聘信息
     */
    public static final String NOTICE_RECRUITMENT_INFORMATION = "recruitmentInformation";
    /**
     * 更新日期养卡列表
     */
    public static final String NOTICE_UPDATE_DATE_REPAYMENT_LIST = "updateDateRepaymentList";
    /**
     * 提现成功更新卡生活页面接口
     */
    public static final String UPDATE_CARD_LIFE = "updateCardLife";
    /**
     * 提现成功更新红包明细页面接口
     */
    public static final String UPDATE_RED_ENVELOPE_DETAILS = "updateRedEnvelopeDetails";

    /*********************************************web页面监听链接*********************************************/
    /**
     * 首款成功
     */
    public static final String WEB_VIEW_HTTP_BAIDU_COM = "https://www.baidu.com/";
    /**
     * 代还开卡成功链接
     */
    public static final String WEB_VIEW_HTTP_GOOGLE_CN = "http://www.google.cn";

    /*********************************************公共弹出框使用类型*********************************************/
    /**
     * 没有状态
     */
    public static final String NO_TYPE = "noType";
    /**
     * 验证码状态
     */
    public static final String TYPE_VERIFICATION_CODE = "verificationCode";
    /**
     * 终止计划
     */
    public static final String TYPE_TERMINATION_PLAN = "terminationPlan";
    /**
     * 提交计划
     */
    public static final String TYPE_SUBMIT_PLAN = "submitPlan";
    /**
     * 个人中心：升级码key
     */
    public static final String TYPE_UPGRADE_CODE = "upgradeCode";

    /********************************************开启定位使用场景***********************************************/
    /**
     * 定位场景（业务员定位：10001）
     */
    public static final int POSITIONING_SCENE_SALESPERSON_POSITIONING = 10001;
    /**
     * 定位场景（定位回掉：10002）
     */
    public static final int POSITIONING_SCENE_POSITIONING_BACK = 10002;
    /**
     * 定位场景（H5页面定位回掉：10003）
     */
    public static final int POSITIONING_SCENE_H5_POSITIONING_BACK = 10003;
    /**
     * 定位场景（柜台征信页面：10004）
     */
    public static final int POSITIONING_SCENE_CREDIT_COUNTER = 10004;

}
