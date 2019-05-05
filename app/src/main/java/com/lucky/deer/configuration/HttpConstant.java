package com.lucky.deer.configuration;


/**
 * 接口常量
 *
 * @author wangxiangyi
 * @date 2018/09/17
 */
public class HttpConstant {
    /**
     * 请求基础地址
     */

    /*李荣洲*/
    public static final String BASE_URL = "http://admin.zhonghuatech.com/";
    /*吴冬林*/
    //public static final String BASE_URL = "http://192.168.1.254:8080/";
    /*贾艳平*/
    // public static final String BASE_URL = "http://192.168.1.4:8080/";
    /*刘骐毓*/
    //public static final String BASE_URL = "http://192.168.1.9:8080/";
    /*王皓*/
    //public static final String BASE_URL = "http://192.168.1.7:8080/";
    /*请求基础地址：本地服务区ip地址*/
    // public static final String BASE_URL = "http://192.168.1.254:8080/";

     public static final String BASE_SHOP_URL="http://118.190.146.220:8083/";

    public static final String BUCKET = "bucket-one";
    /* 请求基础地址：外网测试服务器地址*/
//    public static final String BASE_URL = "http://120.27.18.104:8080/";
    /* 请求基础地址：外网正式服务器地址*/
//    public static final String BASE_URL = "http://admin.minmai1688.com/";
//    public static final String BASE_URL = "http://47.92.237.148:80/";


    /**
     * 连接地址
     */
     private static final String PRJ = "app/";
    // private static final String PRJ = "qike/app/";
    /**
     *
     */
    private static final String MALL = "shoppingmall/";
    /**
     * 传值的key
     */
    public static final String ENTITY = "entity";
    /**
     * 七牛云域名
     */
    public static final String QINIU_URL = "http://img.zhonghuatech.com/";

    public static final String QINIU_AK = "Ob4ET-G7l6ysV3RWoUqIE7Ke56ZDPiQawK-14-zA";

    public static final String QIUNIU_SK = "unXoTd5CS8t18vGQ43RuTA0We-Qs9D7E_3llY_Zh";

    /**
     * 阿里云的按着银行卡号获取银行缩写基础地址
     */
    private static final String BASE_BANK_ABBREV_URL = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?";
    /**
     * 阿里云的按着银行卡号获取银行缩写参数
     */
    public static final String BANK_ABBREV_URL = BASE_BANK_ABBREV_URL + "_input_charset=utf-8&cardBinCheck=true&cardNo=";
    /**
     * 阿里云四要素认证
     */
    public static final String FOUR_FACTOR_AUTHENTICATION = "https://yunyidata.market.alicloudapi.com/bankAuthenticate4";
    /**
     * 京东万象四要素认证
     */
    public static final String JINGDONG_VIENTIANE_FOUR_FACTOR_AUTHENTICATION = "https://v.apihome.org/bankfour";
    /**
     * 四要素认证
     */
    public static final String MY_FOUR_FACTOR_AUTHENTICATION = BASE_URL + PRJ + "element/elementsValidate";

    /**
     * 检查更新升级
     */
    public static final String USER_CHECK_THE_UPDATE = BASE_URL + PRJ + "user/checkTheUpdate";
    /**
     * 查询用户使命认证信息
     */
    public static final String USER_QUERY_IDENTITY_AUTH = BASE_URL + PRJ + "user/queryIdentityAuth";
    /**
     * 保存四要素验证数据
     */
    public static final String BANK_AUTHENTICATE_SAVE = BASE_URL + PRJ + "bankAuthenticate/save";
    /**
     * 验证手机号是否注册接口
     */
    public static final String URL_USER_ID_PHONE = BASE_URL + PRJ + "user/getUserIdByPhone";
    /**
     * 请求注册时验证码接口
     */
    public static final String URL_BIND_SEND_CODE = BASE_URL + PRJ + "code/bindSendCode";
    /**
     * 请求公共验证码接口
     */
    public static final String URL_USER_SEND_CODE = BASE_URL + PRJ + "code/userSendCode";
    /**
     * 请求验证验证码接口
     */
    public static final String URL_AHAX_VALIDATE_CODE = BASE_URL + PRJ + "code/ajaxValidateCode";
    /**
     * 注册、登录、验证码公用接口
     */
    public static final String URL_REGISTER = BASE_URL + PRJ + "user/userRegister";
    /**
     * 查询用户注册状态
     */
    public static final String URL_QUERY_REGISTER_STATE = BASE_URL + PRJ + "user/queryRegisterState";
    /**
     * 密码登录接口
     */
    public static final String URL_USER_LOGIN = BASE_URL + PRJ + "user/userLogin";
    /**
     * 验证码登录接口
     */
    public static final String URL_PHONE_USER_LOGIN = BASE_URL + PRJ + "user/phoneUserLogin";
    /**
     * 退出登录接口
     */
    public static final String URL_USER_SIGN_OUT = BASE_URL + PRJ + "user/userSignOut";
    /**
     * 找回密码接口
     */
    public static final String URL_FORGET_USER_PWWD = BASE_URL + PRJ + "user/forgetUserPwwd";
    /**
     * 首页banner 查询
     */
    public static final String BANNER_QUERY_BANNER = BASE_URL + PRJ + "banner/queryBanner";
    /**
     * 查询轮播消息
     */
    public static final String TRADE_QUERY_APP_ROLL_MESSAGE = BASE_URL + PRJ + "trade/queryAppRollMessage";
    /**
     * 卡生活接口
     */
    public static final String FOUND_USER_ASSETS_INFO = BASE_URL + PRJ + "found/getUserAssetsInfo";
    /**
     * 我的计划列表
     */
    public static final String BILLPLAN_LIST_MY_PLAN = BASE_URL + PRJ + "billplan/listMyPlan";
    /**
     * 制定计划账单（日期养卡：制定计划）
     */
    public static final String BILLPLAN_MAKE_BILL_PLAN = BASE_URL + PRJ + "billplan/makeBillPlan";
    /**
     * 终止制定计划账单（日期养卡：制定计划）
     */
    public static final String BILLPLAN_CLOSE_BILL_PLAN = BASE_URL + PRJ + "billplan/closeBillPlan";
    /**
     * 明日养卡计划是否正常执行（日期养卡,异常处理）
     */
    public static final String BILL_EXCEPTION_YES_NO = BASE_URL + PRJ + "promptLog/billExceptionYesNo";
    /**
     * 执行中计划列表（日期养卡：执行计划）
     */
    public static final String BILLPLAN_LIST_EXECUTING_PLAN = BASE_URL + PRJ + "billplan/listExecutingPlan";
    /**
     * 查询已完成计划列表（日期养卡：历史计划）
     */
    public static final String BILLPLAN_LIST_MY_COMPLETED_PLAN = BASE_URL + PRJ + "billplan/listMyCompletedPlan";
    /**
     * 查询异常计划列表（日期养卡：异常计划）
     */
    public static final String BILLPLAN_BILL_EXCEPTION_LIST = BASE_URL + PRJ + "billplan/billExceptionList";
    /**
     * 查询账单详情（日期养卡：计划详情）
     */
    public static final String BILLPLAN_LIST_BILL_DETAILS = BASE_URL + PRJ + "billplan/billDetails";
    /**
     * 代还开卡接口（日期养卡：计划详情）
     */
    public static final String DAI_HUAN_OPEN_CARD = BASE_URL + PRJ + "daiHuan/openCard";
    /**
     * 代还开卡接口（日期养卡：计划详情）
     */
    public static final String DAI_HUAN_OPEN_CARD_CONFIRM = BASE_URL + PRJ + "daiHuan/openCardConfirm";
    /**
     * 提交计划（日期养卡：计划详情）
     */
    public static final String BILLPLAN_SUBMIT_PLAN = BASE_URL + PRJ + "billplan/submitPlan";
    /**
     * 完美养卡列表（完美养卡：列表查询 ）
     */
    public static final String QUERY_PERFECT_PLAN_BY_STATUS = BASE_URL + PRJ + "perfectbillplan/queryPerfectPlanByStatus";
    /**
     * 提交计划（完美养卡：提交制定计划）
     */
    public static final String MAKE_PERFECT_BILL_PLAN = BASE_URL + PRJ + "perfectbillplan/makePerfectBillPlan";
    /**
     * 完美养卡详情列表（完美养卡：详情列表）
     */
    public static final String QUERY_PERFECT_PLAN_DETAIL = BASE_URL + PRJ + "perfectbillplan/queryPerfectPlanDetail";

    /**
     * 我的商户接口
     */
    public static final String FOUND_MERCHANT_INFO = BASE_URL + PRJ + "found/merchantInfo";
    /**
     * 查询默认储蓄卡接口
     */
    public static final String URL_DEFAULT_DEBIT_CARD_VO = BASE_URL + PRJ + "user/getDefaultDebitCardVo";
    /**
     * 获取授权状态
     */
    public static final String GET_AUTHORIZATION_STATUS = BASE_URL + PRJ + "user/getAuthorizationStatus";
    /**
     * 获取支付宝授权加密串
     */
    public static final String AUTHORIZATION_ENCRYPTED = BASE_URL + PRJ + "alipay/authorizationEncrypted";
    /**
     * 余额信息包含提现手续费率
     */
    public static final String USER_WALLET = BASE_URL + PRJ + "user/userWallet";
    /**
     * 查询储蓄卡接口
     */
    public static final String URL_QUERY_DEBIT_CARD = BASE_URL + PRJ + "user/queryDebitCard";
    /**
     * 修改用户默认储蓄卡
     */
    public static final String URL_MODIFY_DEFAULT_DEBIT_CARD = BASE_URL + PRJ + "trade/modifyDefaultDebitCard";
    /**
     * 查询信用卡接口
     */
    public static final String URL_QUERY_CREDIT_CARD = BASE_URL + PRJ + "trade/queryCreditCard";
    /**
     * 修改信用卡昵称接口
     */
    public static final String UNDATA_NICKNAME_CREDIT_CARD = BASE_URL + PRJ + "trade/updateCreditAlias";
    /**
     * 解除信用卡绑定
     */
    public static final String UNDATA_DEL_CREDIT_CARD = BASE_URL + PRJ + "trade/delCreditCard";
    /**
     * 完善信息第一步：身份证认证
     */
    public static final String USER_REAL_NAME_AUTHENTICATION_ONE = BASE_URL + PRJ + "user/userRealNameAuthenticationOne";
    /**
     * 完善信息第二步：手持身份证
     */
    public static final String USER_REAL_NAME_AUTHENTICATION_TWO = BASE_URL + PRJ + "user/userRealNameAuthenticationTwo";
    /**
     * 完善信息第三步：绑定储蓄卡
     */
    public static final String USER_BANK_CARD_BINDING = BASE_URL + PRJ + "user/userBankCardBinding";
    /**
     * 完善信息第三步：获取所有的总行信息
     */
    public static final String USER_BANK_INFO_VO = BASE_URL + PRJ + "user/getBankInfoVo";
    /**
     * 完善信息第三步：获取所有城市信息
     */
    public static final String USER_ALL_AREA_LIST = BASE_URL + PRJ + "area/getAllAreaList";
//    /**
//     * 完善信息第三步：获取区域id
//     */
//    public static final String USER_AREA_LIST = BASE_URL + PRJ + "user/areaList";
    /**
     * 完善信息第三步：获取支行信息列表
     */
    public static final String USER_BANK_BRANCH = BASE_URL + PRJ + "user/bankBranch";
    /**
     * 用户个人中心
     */
    public static final String USER_PERSONAL_CENTER = BASE_URL + PRJ + "user/getUserPersonalCenter";
    /**
     * 修改用户头像
     */
    public static final String USER_UPDATE_USER_HEAD = BASE_URL + PRJ + "user/updateUserHead";
    /**
     * 修改用户昵称
     */
    public static final String USER_UPDATE_USER_NICE_NAME = BASE_URL + PRJ + "user/updateUserNiceName";
    /**
     * 上传视频
     */
    public static final String VIDEO_INSERT_VIDEO = BASE_URL + PRJ + "video/insertVideo";
    /**
     * 视频列表
     */
    public static final String VIDEO_LIST_VIDEOS = BASE_URL + PRJ + "video/userListVideos";
    /**
     * 删除视频
     */
    public static final String VIDEO_DELETE_VIDEO = BASE_URL + PRJ + "video/deleteVideo";
    /**
     * 获取推荐人信息
     */
    public static final String USER_REFEREE_USER_INFO = BASE_URL + PRJ + "user/getRefereeUserInfo";
    /**
     * 用户给推荐人发送留言消息
     */
    public static final String USER_LEAVE_MESSAGE = BASE_URL + PRJ + "user/userLeaveMessage";
    /**
     * 用户留言板
     */
    public static final String USER_QUERY_MESSAGE_BOARD = BASE_URL + PRJ + "user/listLevMessage";
    /**
     * 用户留言板查看接口
     */
    public static final String USER_UPDATE_READ_STATE = BASE_URL + PRJ + "user/updateReadState";
    /**
     * 用户交易记录
     */
    public static final String TRADE_QUERY_TRADING_RECORD = BASE_URL + PRJ + "trade/queryTradingRecord";
    /**
     * 添加/更新金融服务业务员接口
     */
    public static final String FINANCIAL_SAVE_OR_UPDATE_SERVICE_SALES_MAN = BASE_URL + PRJ + "financial/saveOrUpdateServiceSalesMan";
    /**
     * 获取业务员详细信息接口
     */
    public static final String FINANCIAL_GET_SERVICE_SALES_MAN_BY_ID = BASE_URL + PRJ + "financial/getServiceSalesManById";
    /**
     * 新增招聘信息
     */
    public static final String RECRUIT_RELEASE_RECRUIT = BASE_URL + PRJ + "recruit/releaseRecruit";
    /**
     * 获取用户发布的招聘列表
     */
    public static final String RECRUIT_LIST_USER_FOR_RECRUITS = BASE_URL + PRJ + "recruit/listUserForRecruits";
    /**
     * 删除招聘信息
     */
    public static final String RECRUIT_DELETE_RECRUIT = BASE_URL + PRJ + "recruit/deleteRecruit";
    /**
     * 开始/关闭接单
     */
    public static final String USER_OPEN_OR_OFF_RECEIPT = BASE_URL + PRJ + "financial/openOrOffReceipt";
    /**
     * 修改用户手机号
     */
    public static final String USER_UPDATE_USER_PHONE = BASE_URL + PRJ + "user/updateUserPhone";
    /**
     * 用户修改登录密码（以旧密码修改新密码）
     */
    public static final String USER_UP_USER_PWWD = BASE_URL + PRJ + "user/uPUserPwwd";
    /**
     * 用户设置支付密码
     */
    public static final String USER_ADD_PAYMENT_PASSWORD = BASE_URL + PRJ + "user/addPaymentPassword";
    /**
     * 更多更多设置：用户是否允许下级查看手机号
     */
    public static final String USER_UPDATE_EXTEND_ONE = BASE_URL + PRJ + "user/updateExtendOne";
    /**
     * 管理账号：检查是否设置支付密码
     */
    public static final String USER_IS_SETPAYMENT_PASSWORD = BASE_URL + PRJ + "user/isSetpaymentPassword";
    /**
     * 修改支付密码
     */
    public static final String USER_UP_PAYMENT_PASSWORD = BASE_URL + PRJ + "user/uPPaymentPassword";
    /**
     * 修改/重置支付密码（手机验证修改新密码）
     */
    public static final String USER_RESET_PAYMENT_PASSWORD = BASE_URL + PRJ + "user/resetPaymentPassword";
    /**
     * 帮助列表
     */
    public static final String CMS_LIST = BASE_URL + PRJ + "cms/cmsList";
    /**
     * 提交反馈信息
     */
    public static final String USER_FEEDBACK = BASE_URL + PRJ + "user/userFeedback";
    /**
     * 通道列表接口
     */
    public static final String USER_QUERY_CHANNEL = BASE_URL + PRJ + "trade/queryChannel";
    /**
     * 获取银行提现的限额
     */
    public static final String TRADE_QUERY_BANK_LIMIT = BASE_URL + PRJ + "trade/queryBankLimit";
    /**
     * 查询是否开通快捷支付
     */
    public static final String TRADE_OPEN_QUICK_PAY_QUERY = BASE_URL + PRJ + "trade/openQuickPayQuery";
    /**
     * 刷卡交易接口
     */
    public static final String USER_CREATE_QUICKPAY = BASE_URL + PRJ + "trade/createQuickpay";
    /**
     * 添加储蓄卡
     */
    public static final String USER_ADD_USER_BANK_CARD = BASE_URL + PRJ + "user/addUserBankCard";
    /**
     * 修改储蓄卡
     */
    public static final String USER_UPDATE_USER_BANK_CARD = BASE_URL + PRJ + "user/updateUserBankCard";
    /**
     * 根据银行卡号获取银行的背景
     */
    public static final String USER_BANK_BACKGROUND_VO = BASE_URL + PRJ + "user/getBankBackgroundVo";
    /**
     * 添加信用卡
     */
    public static final String USER_ADD_CREDIT_CARD = BASE_URL + PRJ + "trade/addCreditCard";
    /**
     * 修改信用卡开始日或者结束日
     */
    public static final String USER_UPDATE_CREDIT_CARD_DATE = BASE_URL + PRJ + "user/updateCreditCardDate";
    /**
     * 获取所有的app菜单
     */
    public static final String MENU_MENU_TREE_DATE = BASE_URL + PRJ + "menu/menuTreeDate";
    /**
     * 获取网申列表
     */
    public static final String QUERY_ONLINE_APPLICATION_LIST = BASE_URL + PRJ + "onlineApplication/queryOnlineApplicationList";
    /**
     * 获取网贷列表
     */
    public static final String QUERY_ONLINE_LOAN_LIST = BASE_URL + PRJ + "onlineLoan/queryOnlineLoanList";
    /**
     * 获取全部文章列表
     */
    public static final String QUERY_APP_INTRODUCE_CMS_LIST = BASE_URL + PRJ + "appIntroduceCms/queryAppIntroduceCmsList";
    /**
     * 获取文章阅读量
     */
    public static final String GET_APP_INTRODUCE_CMS = BASE_URL + PRJ + "appIntroduceCms/getAppIntroduceCms";
    /**
     * 发现页数据接口
     */
    public static final String FOUND_PAGE_ALL_INIT_INFO = BASE_URL + PRJ + "found/getFoundPageAllInitInfo";
    /**
     * 获取商户下指定等级中的推荐的人员记录接口
     */
    public static final String FOUND_RECOMMEND_USER_INFO_LIST = BASE_URL + PRJ + "found/getRecommendUserInfoList";
    /**
     * 会员中心：查询等级信息和价格
     */
    public static final String UPGRADE_SEARCH = BASE_URL + PRJ + "upgrade/search";
    /**
     * 购买会员等级：加密app支付交易信息
     */
    public static final String ALIPAY_CALL_ORDER_PAY = BASE_URL + PRJ + "alipay/callOrderPay";
    /**
     * 购买会员等级：用户升级申请
     */
    public static final String UPGRADE_APPLY = BASE_URL + PRJ + "upgrade/upgradeApply";
    /**
     * 个人中心：升级码升级接口
     */
    public static final String USE_UPGRADE_CODE = BASE_URL + PRJ + "upgradeCode/useUpgradeCode";
    /**
     * 我的分润接口
     */
    public static final String RECORD_LIST_FOR_RECORDS = BASE_URL + PRJ + "record/listForRecords";
    /**
     * 支付宝会员授权获取会员信息
     */
    public static final String GET_ALIPAY_USER_INFO = BASE_URL + PRJ + "user/getAlipayUserInfo";
    /**
     * 用户提现申请
     */
    public static final String USER_WITHDRAW = BASE_URL + PRJ + "user/userWithdraw";
    /**
     * 查询用户提现状态
     */
    public static final String USER_QUERY_WITHDRAW_DETAILS = BASE_URL + PRJ + "user/queryWithdrawDetails";
    /**
     * 提现明细分页查询
     */
    public static final String USER_WITHDRAW_RECORD = BASE_URL + PRJ + "user/userWithdrawRecord";
    /**
     * 获取推广信息
     */
    public static final String SPREAD_SEARCH = BASE_URL + PRJ + "spread/search";
    /**
     * 查询APP广告
     */
    public static final String SPREAD_ADVERT = BASE_URL + PRJ + "advertisement/query";

    /**
     * 商城支付成功接口
     */
    public static final String SYNC_NOTIFY = BASE_SHOP_URL + "AMC/app/alipay/syncNotify";

    /*********************************************h5链接*******************************************************/
    /**
     * 商城H5链接
     */
    public static final String H5_MALL = BASE_SHOP_URL +MALL+ "shoppingmall/homePage.html?openId=";
    /**
     * 商城订单信息
     * All  全部订单
     * -2 待付款
     * 0 待发货
     * -1 待收货
     * toEvaluate  待评价
     */
    public static final String H5_MALL_ORDER_INFORMATION = BASE_SHOP_URL + MALL + "shoppingmall/myordermall.html?openId=";
    /**
     * 商城订单信息：购物车
     */
    public static final String H5_MALL_SHOPPING_CART = BASE_SHOP_URL + MALL + "shoppingmall/buycar.html?openId=";
    /**
     * 商城订单信息：退款/售后
     */
    public static final String H5_MALL_REFUND_OR_AFTER_SALE = BASE_SHOP_URL + MALL + "shoppingmall/afterservice.html?openId=";
    /**
     * 商城订单信息：我的收藏
     */
    public static final String H5_MALL_PRODUCT_COLLECTION = BASE_SHOP_URL + MALL + "shoppingmall/goodscollect.html?openId=";
    /**
     * 商城订单信息：浏览历史
     */
    public static final String H5_MALL_BROWSING_HISTORY = BASE_SHOP_URL + MALL + "shoppingmall/browsehistory.html?openId=";
    /**
     * 商城订单信息：收货地址管理
     */
    public static final String H5_MALL_HARVEST_ADDRESS = BASE_SHOP_URL + MALL + "shoppingmall/paddress.html?openId=";
    /**
     * 在线客服H5链接
     */
    public static final String H5_ONLINE_SERVICE = BASE_URL + "H5/chatApp/index.html?userId=";
    /**
     * 观看视频H5链接
     */
    public static final String H5_WATCH_VIDEO = BASE_URL + "H5/H5/video.html?id=";
    /**
     * 招聘信息详情H5链接
     */
    public static final String H5_RECRUITMENT_DETAILS = BASE_URL + "H5/recruit.html?id=";
    /**
     * 注册申请条款H5链接
     */
    public static final String H5_REGISTRATION_APPLICATION_TERMS = BASE_URL + "H5/MinMai_wx/protocol.html";
    /**
     * 日期养卡制定计划操作说明H5链接
     */
    public static final String H5_DATE_REPAYMENT_MAKING_PLANS_INSTRUCTIONS = BASE_URL + "H5/MinMai_wx/Generation.html";
    /**
     * 使用教程
     */
    public static final String H5_USE_TUTORIAL = BASE_URL + "H5/tutorial.html";
    /**
     * 我要网申h5
     */
    public static final String H5_MY_NEED_NET_APPLICATION = "https://creditcard.cmbc.com.cn/wsv2/";


    /**
     * 帮助详情连接
     */
    //public static final String H5_HELP_DETAILS_CONNECT = BASE_URL + "vo/openSelectSysCmsView/viewContent?id=%s";
//    public static final String H5_HELP_DETAILS_CONNECT = "http://192.168.1.6/qike/vo/openSelectSysCmsView/viewContent?id=%s";
    public static final String H5_HELP_DETAILS_CONNECT = BASE_URL + "vo/openSelectSysCmsView/viewContent?id=%s";

    /**
     * 获取限制领取红包广告的字典
     */
    public static final String GET_RED_ENVELOPE_AVERTISING_CONFIGURE_DICT = BASE_URL + PRJ + "configureDict/getRedEnvelopeAvertisingConfigureDict";

    /**
    * 查询指定用户的(全部/已使用/未使用)升级码
    */
    public static final String QUERY_USER_UPGRADE_CODE = BASE_URL + PRJ + "upgradeCode/queryUserUpgradeCode";

 /**
  * 获取指定用户直推下vip等级人数
  */
    public static final String USERGET_USER_DIRECT_PUSH_VIP_BY_NUM = BASE_URL + PRJ + "user/getUserDirectPushVipByNum";

    /**
     * 用户管理奖申请
     */
    public static final String UPGRADEUSER_MANAGER_BONUS_APPLY = BASE_URL + PRJ + "upgrade/userManagerBonusApply";


    /**
     * 获取红包列表（商机列表）
     */
    public static final String GET_BUSINESS_OPPORTUNITY_LIST = BASE_URL + PRJ + "redEnvelopeAdvertising/list";
    /**
     *判断是否可以领取红包
     */
    public static final String IS_RECEIPT_CONDITIONS = BASE_URL + PRJ + "redEnvelopeAdvertising/isReceiptConditions";
    /**
     * 领取红包（抢红包）
     */
    public static final String START_GRABBING_RED_PACKETS = BASE_URL + PRJ + "redEnvelopeAdvertising/receive";
    /**
     * 获取红包动态详情（文章详情）
     */
    public static final String GET_ARTICLE_DETAILS = BASE_URL + PRJ + "redEnvelopeAdvertising/details";
    /**
     * 发送红包广告
     */
    public static final String SEND_RED_ENVELOPE = BASE_URL + PRJ + "redEnvelopeAdvertising/send";
    /**
     * 查询用户领取红包明细
     */
    public static final String USER_RECEIVE_RED_ENVELOPE = BASE_URL + PRJ + "redEnvelopeRecord/userReceiveRedEnvelope";
    /**
     * 查询用户发放红包明细
     */
    public static final String USER_ISSUE_RED_ENVELOPE = BASE_URL + PRJ + "redEnvelopeAdvertising/userIssueRedEnvelope";

    /**
     * 红包钱包提现
     */
    public static final String RED_WITHDRAW_USER_WITHDRAW = BASE_URL + PRJ + "redWithdraw/userWithdraw";

    /**
     * 购买会员成功接口
     */
    public static final String ALIPAY_SYNCHRONOUS_CALLBACK = BASE_URL + PRJ + "alipay/synchronousCallback";

    /**
     * 分享红包文章详情链接
     */
    public static final String H5_SHARE_RED_ENVELOPE_ARTICLES = BASE_URL + "H5/redpacket.html?id=%1$s&recommendCode=%2$s";
}
