package com.lucky.deer.execute;

import com.dykj.requestcore.execute.BaseProxy;
import com.google.gson.reflect.TypeToken;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.model.common.fourelements.FourElementsEntity;
import com.lucky.model.request.BaseReq;
import com.lucky.model.response.Advert;
import com.lucky.model.response.ResponseData;
import com.lucky.model.response.cardspending.CreateQuickpayEntity;
import com.lucky.model.response.cardspending.ManageAward;
import com.lucky.model.response.cardspending.QueryBankLimitEntity;
import com.lucky.model.response.cardspending.SwipeChannelEntity;
import com.lucky.model.response.financial.FinancialServicesEntity;
import com.lucky.model.response.find.ArticleDetailsEntity;
import com.lucky.model.response.find.BusinessDetailsEntity;
import com.lucky.model.response.find.BusinessEntity;
import com.lucky.model.response.find.FindEntity;
import com.lucky.model.response.find.FindWithdrawalEntity;
import com.lucky.model.response.find.MemberCentreEntity;
import com.lucky.model.response.find.MyVideoEntity;
import com.lucky.model.response.find.WhetherReceiveRedEnvelopeEntity;
import com.lucky.model.response.find.redEnvelope.ActivationCodeChildEntiviyt;
import com.lucky.model.response.find.redEnvelope.RedEnvelopeCollectionRangeEntity;
import com.lucky.model.response.find.redEnvelope.RedEnvelopesDetailEntity;
import com.lucky.model.response.home.CarouselMessageEntity;
import com.lucky.model.response.home.DividedOrWithdrawalDetailsEntity;
import com.lucky.model.response.home.FindArticleEntity;
import com.lucky.model.response.home.OnlineApplicationEntity;
import com.lucky.model.response.home.OnlineLoanEntity;
import com.lucky.model.response.home.QueryBannerEntity;
import com.lucky.model.response.home.QueryRegisterStateEntity;
import com.lucky.model.response.home.cardLife.CardLifeEntity;
import com.lucky.model.response.home.cardLife.MyBusinessEntity;
import com.lucky.model.response.home.pepayment.GenerationOpenCardEntity;
import com.lucky.model.response.home.pepayment.HistoryPlanEntity;
import com.lucky.model.response.home.pepayment.OpenCardConfirmEntity;
import com.lucky.model.response.home.pepayment.PepaymentPlanEntity;
import com.lucky.model.response.home.pepayment.PepaymentPlanList;
import com.lucky.model.response.home.pepayment.PlanDetailsEntity;
import com.lucky.model.response.home.pepayment.perfect.MakePerfectBillPlanEntity;
import com.lucky.model.response.home.pepayment.perfect.PerfectRepaymentEntity;
import com.lucky.model.response.mine.HelpEntity;
import com.lucky.model.response.mine.MessageBoardEntity;
import com.lucky.model.response.mine.PerfectInfoEntity;
import com.lucky.model.response.mine.QueryIdentityAuthEntity;
import com.lucky.model.response.mine.RecruitmentInfoListEntity;
import com.lucky.model.response.mine.TransactionRecordEntity;
import com.lucky.model.response.perfectinformation.AccountOpeningAreaEntity;
import com.lucky.model.response.perfectinformation.BankBranchEntity;
import com.lucky.model.response.perfectinformation.BankInfoVoEntity;
import com.lucky.model.response.promotion.PromotionEntity;
import com.lucky.model.response.selectbankcard.CashWithdrawalRate;
import com.lucky.model.response.selectbankcard.SelectBankCardBean;
import com.lucky.model.response.selectbankcard.SelectBankCardList;
import com.lucky.model.response.userinfo.ModifySetpaymentPassword;
import com.lucky.model.response.userinfo.PersonalCenterInfo;
import com.lucky.model.response.userinfo.RefereeUserInfo;
import com.lucky.model.response.userinfo.UserInfo;
import com.lucky.model.response.version.VersionUpdataEntity;

import java.util.List;

import io.reactivex.Observable;


/**
 * 请求网络
 */
public class RequestInternet extends BaseProxy {
    private static volatile RequestInternet mInstance;

    /**
     * 单利模式
     *
     * @return
     */
    public static RequestInternet getInstance() {
        if (mInstance == null) {
            synchronized (RequestInternet.class) {
                if (mInstance == null) {
                    mInstance = new RequestInternet();
                }
            }
        }
        return mInstance;
    }

    /**
     * 阿里云四要素认证
     *
     * @param params 参数
     */
    public Observable<ResponseData<String>> bankAuthenticate(BaseReq params) {
        return executor.executor(HttpConstant.FOUR_FACTOR_AUTHENTICATION, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 京东万象四要素认证
     *
     * @param params 参数
     */
    public Observable<ResponseData<FourElementsEntity>> jingdongBankAuthenticate(BaseReq params) {
        return executor.executor(HttpConstant.JINGDONG_VIENTIANE_FOUR_FACTOR_AUTHENTICATION, params, new TypeToken<ResponseData<FourElementsEntity>>() {
        }.getType());
    }

    /**
     * 京东万象四要素认证
     *
     * @param params 参数
     */
    public Observable<ResponseData<String>> elementsValidate(BaseReq params) {
        return executor.executor(HttpConstant.MY_FOUR_FACTOR_AUTHENTICATION, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 检查更新升级
     *
     * @param params 参数
     */
    public Observable<ResponseData<VersionUpdataEntity>> checkTheUpdate(BaseReq params) {
        return executor.executor(HttpConstant.USER_CHECK_THE_UPDATE, params, new TypeToken<ResponseData<VersionUpdataEntity>>() {
        }.getType());
    }

    /**
     * 查询用户使命认证信息
     */
    public Observable<ResponseData<QueryIdentityAuthEntity>> queryIdentityAuth() {
        return executor.executor(HttpConstant.USER_QUERY_IDENTITY_AUTH, new BaseReq(), new TypeToken<ResponseData<QueryIdentityAuthEntity>>() {
        }.getType());
    }

    /**
     * 保存四要素验证数据
     */
    public Observable<ResponseData<String>> saveVerifyData(BaseReq params) {
        return executor.executor(HttpConstant.BANK_AUTHENTICATE_SAVE, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 验证手机号是否注册
     *
     * @param params 参数
     */
    public Observable<ResponseData<String>> getUserIdPhone(BaseReq params) {
        return executor.executor(HttpConstant.URL_USER_ID_PHONE, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 注册时验证码
     *
     * @param params 参数
     */
    public Observable<ResponseData<String>> getMobile(BaseReq params) {
        return executor.executor(HttpConstant.URL_BIND_SEND_CODE, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 找回密码验证码
     *
     * @param params 参数
     */
    public Observable<ResponseData<String>> getPublicMobile(BaseReq params) {
        return executor.executor(HttpConstant.URL_USER_SEND_CODE, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 验证验证码
     *
     * @param params 参数
     */
    public Observable<ResponseData<String>> getAjaxValidateCode(BaseReq params) {
        return executor.executor(HttpConstant.URL_AHAX_VALIDATE_CODE, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 注册
     *
     * @param params 参数
     */
    public Observable<ResponseData<UserInfo>> registered(BaseReq params) {
        return executor.executor(HttpConstant.URL_REGISTER, params, new TypeToken<ResponseData<UserInfo>>() {
        }.getType());
    }

    /**
     * 查询用户注册状态
     */
    public Observable<ResponseData<QueryRegisterStateEntity>> queryRegisterState() {
        return executor.executor(HttpConstant.URL_QUERY_REGISTER_STATE, new BaseReq(), new TypeToken<ResponseData<QueryRegisterStateEntity>>() {
        }.getType());
    }

    /**
     * 登录
     *
     * @param params     参数
     * @param isPwdLogin 判断是否是快速登录 （true：快速登录，false：密码登录）
     */
    public Observable<ResponseData<UserInfo>> userLogin(BaseReq params, boolean isPwdLogin) {
        return executor.executor(isPwdLogin ? HttpConstant.URL_PHONE_USER_LOGIN : HttpConstant.URL_USER_LOGIN, params, new TypeToken<ResponseData<UserInfo>>() {
        }.getType());
    }

    /**
     * 退出登录接口
     */
    public Observable<ResponseData<String>> userSignOut() {
        return executor.executor(HttpConstant.URL_USER_SIGN_OUT, new BaseReq(), new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 找回密码
     *
     * @param params 参数
     */
    public Observable<ResponseData<String>> forgetUserPwwd(BaseReq params) {
        return executor.executor(HttpConstant.URL_FORGET_USER_PWWD, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 首页banner 查询
     */
    public Observable<ResponseData<List<QueryBannerEntity>>> queryBanner() {
        return executor.executor(HttpConstant.BANNER_QUERY_BANNER, new BaseReq(), new TypeToken<ResponseData<List<QueryBannerEntity>>>() {
        }.getType());
    }

    /**
     * 查询轮播消息
     */
    public Observable<ResponseData<List<CarouselMessageEntity>>> queryAppRollMessage() {
        return executor.executor(HttpConstant.TRADE_QUERY_APP_ROLL_MESSAGE, new BaseReq(), new TypeToken<ResponseData<List<CarouselMessageEntity>>>() {
        }.getType());
    }

    /**
     * 卡生活接口
     */
    public Observable<ResponseData<CardLifeEntity>> getUserAssetsInfo() {
        return executor.executor(HttpConstant.FOUND_USER_ASSETS_INFO, new BaseReq(), new TypeToken<ResponseData<CardLifeEntity>>() {
        }.getType());
    }

    /**
     * 我的计划列表
     */
    public Observable<ResponseData<PepaymentPlanEntity>> listMyPlan(BaseReq params) {
        return executor.executor(HttpConstant.BILLPLAN_LIST_MY_PLAN, params, new TypeToken<ResponseData<PepaymentPlanEntity>>() {
        }.getType());
    }

    /**
     * 制定计划账单（日期养卡：制定计划）
     */
    public Observable<ResponseData<PepaymentPlanList>> makeBillPlan(BaseReq params) {
        return executor.executor(HttpConstant.BILLPLAN_MAKE_BILL_PLAN, params, new TypeToken<ResponseData<PepaymentPlanList>>() {
        }.getType());
    }

    /**
     * 终止制定计划账单（日期养卡：制定计划）
     */
    public Observable<ResponseData<String>> closeBillPlan(BaseReq params) {
        return executor.executor(HttpConstant.BILLPLAN_CLOSE_BILL_PLAN, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 明日养卡计划是否正常执行（日期养卡,异常处理）
     */
    public Observable<ResponseData<String>> billExceptionYesNo(BaseReq params) {
        return executor.executor(HttpConstant.BILL_EXCEPTION_YES_NO, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 执行中计划列表（日期养卡：历史计划）
     */
    public Observable<ResponseData<PepaymentPlanEntity>> listExecutingPlan(BaseReq params) {
        return executor.executor(HttpConstant.BILLPLAN_LIST_EXECUTING_PLAN, params, new TypeToken<ResponseData<PepaymentPlanEntity>>() {
        }.getType());
    }

    /**
     * 查询已完成计划列表（日期养卡：历史计划）
     */
    public Observable<ResponseData<HistoryPlanEntity>> listMyCompletedPlan(BaseReq params) {
        return executor.executor(HttpConstant.BILLPLAN_LIST_MY_COMPLETED_PLAN, params, new TypeToken<ResponseData<HistoryPlanEntity>>() {
        }.getType());
    }

    /**
     * 查询已完成计划列表（日期养卡：历史计划）
     */
    public Observable<ResponseData<PepaymentPlanEntity>> billExceptionList(BaseReq params) {
        return executor.executor(HttpConstant.BILLPLAN_BILL_EXCEPTION_LIST, params, new TypeToken<ResponseData<PepaymentPlanEntity>>() {
        }.getType());
    }

    /**
     * 查询账单详情（计划详情）
     */
    public Observable<ResponseData<List<PlanDetailsEntity>>> billDetails(BaseReq param) {
        return executor.executor(HttpConstant.BILLPLAN_LIST_BILL_DETAILS, param, new TypeToken<ResponseData<List<PlanDetailsEntity>>>() {
        }.getType());
    }

    /**
     * 代还开卡接口（日期养卡：计划详情）
     */
    public Observable<ResponseData<GenerationOpenCardEntity>> openCard(BaseReq param) {
        return executor.executor(HttpConstant.DAI_HUAN_OPEN_CARD, param, new TypeToken<ResponseData<GenerationOpenCardEntity>>() {
        }.getType());
    }

    /**
     * 代还开卡接口（日期养卡：计划详情）
     */
    public Observable<ResponseData<OpenCardConfirmEntity>> openCardConfirm(BaseReq param) {
        return executor.executor(HttpConstant.DAI_HUAN_OPEN_CARD_CONFIRM, param, new TypeToken<ResponseData<OpenCardConfirmEntity>>() {
        }.getType());
    }

    /**
     * 提交计划（日期养卡：计划详情）
     */
    public Observable<ResponseData<String>> submitPlan(BaseReq param) {
        return executor.executor(HttpConstant.BILLPLAN_SUBMIT_PLAN, param, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 完美养卡列表（完美养卡：列表查询 ）
     */
    public Observable<ResponseData<PerfectRepaymentEntity>> queryPerfectPlanByStatus(BaseReq param) {
        return executor.executor(HttpConstant.QUERY_PERFECT_PLAN_BY_STATUS, param, new TypeToken<ResponseData<PerfectRepaymentEntity>>() {
        }.getType());
    }

    /**
     * 提交计划（完美养卡：提交制定计划）
     */
    public Observable<ResponseData<MakePerfectBillPlanEntity>> makePerfectBillPlan(BaseReq param) {
        return executor.executor(HttpConstant.MAKE_PERFECT_BILL_PLAN, param, new TypeToken<ResponseData<MakePerfectBillPlanEntity>>() {
        }.getType());
    }

    /**
     * 完美养卡详情列表（完美养卡：详情列表）
     */
    public Observable<ResponseData<MakePerfectBillPlanEntity>> queryPerfectPlanDetail(BaseReq param) {
        return executor.executor(HttpConstant.QUERY_PERFECT_PLAN_DETAIL, param, new TypeToken<ResponseData<MakePerfectBillPlanEntity>>() {
        }.getType());
    }

    /**
     * 我的商户接口
     */
    public Observable<ResponseData<MyBusinessEntity>> merchantInfo() {
        return executor.executor(HttpConstant.FOUND_MERCHANT_INFO, new BaseReq(), new TypeToken<ResponseData<MyBusinessEntity>>() {
        }.getType());
    }

    /**
     * 查询默认储蓄卡
     */
    public Observable<ResponseData<SelectBankCardList>> getDefaultDebitCardVo() {
        return executor.executor(HttpConstant.URL_DEFAULT_DEBIT_CARD_VO, new BaseReq(), new TypeToken<ResponseData<SelectBankCardList>>() {
        }.getType());
    }

    /**
     * 获取授权状态
     */
    public Observable<ResponseData<String>> getAuthorizationStatus() {
        return executor.executor(HttpConstant.GET_AUTHORIZATION_STATUS, new BaseReq(), new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 获取支付宝授权加密串
     */
    public Observable<ResponseData<String>> authorizationEncrypted() {
        return executor.executor(HttpConstant.AUTHORIZATION_ENCRYPTED, new BaseReq(), new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 余额信息包含提现手续费率
     */
    public Observable<ResponseData<CashWithdrawalRate>> userWallet() {
        return executor.executor(HttpConstant.USER_WALLET, new BaseReq(), new TypeToken<ResponseData<CashWithdrawalRate>>() {
        }.getType());
    }

    /**
     * 查询储蓄卡
     *
     * @param params 参数
     */
    public Observable<ResponseData<SelectBankCardBean>> queryDebitCard(BaseReq params) {
        return executor.executor(HttpConstant.URL_QUERY_DEBIT_CARD, params, new TypeToken<ResponseData<SelectBankCardBean>>() {
        }.getType());
    }

    /**
     * 修改用户默认储蓄卡
     *
     * @param params 参数
     */
    public Observable<ResponseData<SelectBankCardBean>> modifyDefaultDebitCard(BaseReq params) {
        return executor.executor(HttpConstant.URL_MODIFY_DEFAULT_DEBIT_CARD, params, new TypeToken<ResponseData<SelectBankCardBean>>() {
        }.getType());
    }

    /**
     * 查询信用卡
     *
     * @param params 参数
     */
    public Observable<ResponseData<SelectBankCardBean>> queryCreditCard(BaseReq params) {
        return executor.executor(HttpConstant.URL_QUERY_CREDIT_CARD, params, new TypeToken<ResponseData<SelectBankCardBean>>() {
        }.getType());
    }

    /**
     * 修改信用卡昵称接口
     *
     * @param params 参数
     */
    public Observable<ResponseData<String>> updataNickname(BaseReq params) {
        return executor.executor(HttpConstant.UNDATA_NICKNAME_CREDIT_CARD, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 解除信用卡绑定
     *
     * @param params 参数
     */
    public Observable<ResponseData<String>> delCreditCard(BaseReq params) {
        return executor.executor(HttpConstant.UNDATA_DEL_CREDIT_CARD, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 完善信息第一步：身份证认证
     *
     * @param params 参数
     */
    public Observable<ResponseData<String>> userRealNameAuthenticationOne(BaseReq params) {
        return executor.executor(HttpConstant.USER_REAL_NAME_AUTHENTICATION_ONE, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 完善信息第二步：手持身份证
     *
     * @param params 参数
     */
    public Observable<ResponseData<String>> userRealNameAuthenticationTwo(BaseReq params) {
        return executor.executor(HttpConstant.USER_REAL_NAME_AUTHENTICATION_TWO, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 完善信息第三步：绑定储蓄卡
     *
     * @param params 参数
     */
    public Observable<ResponseData<String>> userBankCardBinding(BaseReq params) {
        return executor.executor(HttpConstant.USER_BANK_CARD_BINDING, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 完善信息第三步：获取所有的总行信息
     */
    public Observable<ResponseData<List<BankInfoVoEntity>>> getBankInfoVo() {
        return executor.executor(HttpConstant.USER_BANK_INFO_VO, new BaseReq(), new TypeToken<ResponseData<List<BankInfoVoEntity>>>() {
        }.getType());
    }

    /**
     * 完善信息第三步：获取区域id
     */
    public Observable<ResponseData<List<AccountOpeningAreaEntity>>> getAllAreaList() {
        return executor.executor(HttpConstant.USER_ALL_AREA_LIST, new BaseReq(), new TypeToken<ResponseData<List<AccountOpeningAreaEntity>>>() {
        }.getType());
    }

    /**
     * 完善信息第三步：高德按着银行卡号获取银行缩写参数
     */
    public Observable<ResponseData<String>> getBankAbbreviation(String bankCardNumber) {
        return executor.getExecutor(HttpConstant.BANK_ABBREV_URL + bankCardNumber.replace(" ", ""), new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 完善信息第三步：获取支行信息列表
     *
     * @param params 参数
     */
    public Observable<ResponseData<List<BankBranchEntity>>> bankBranch(BaseReq params) {
        return executor.executor(HttpConstant.USER_BANK_BRANCH, params, new TypeToken<ResponseData<List<BankBranchEntity>>>() {
        }.getType());
    }

    /**
     * 获取用户个人中心信息
     */
    public Observable<ResponseData<PersonalCenterInfo>> getUserPersonalCenter() {
        return executor.executor(HttpConstant.USER_PERSONAL_CENTER, new BaseReq(), new TypeToken<ResponseData<PersonalCenterInfo>>() {
        }.getType());
    }


    /**
     * 修改用户头像
     */
    public Observable<ResponseData<String>> updateUserHead(BaseReq params) {
        return executor.executor(HttpConstant.USER_UPDATE_USER_HEAD, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 修改用户昵称
     */
    public Observable<ResponseData<String>> updateUserNiceName(BaseReq params) {
        return executor.executor(HttpConstant.USER_UPDATE_USER_NICE_NAME, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 上传视频
     */
    public Observable<ResponseData<String>> insertVideo(BaseReq params) {
        return executor.executor(HttpConstant.VIDEO_INSERT_VIDEO, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 视频列表
     */
    public Observable<ResponseData<MyVideoEntity>> listVideos(BaseReq params) {
        return executor.executor(HttpConstant.VIDEO_LIST_VIDEOS, params, new TypeToken<ResponseData<MyVideoEntity>>() {
        }.getType());
    }

    /**
     * 删除视频
     */
    public Observable<ResponseData<String>> deleteVideo(BaseReq params) {
        return executor.executor(HttpConstant.VIDEO_DELETE_VIDEO, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 获取推荐人信息
     */
    public Observable<ResponseData<RefereeUserInfo>> getRefereeUserInfo() {
        return executor.executor(HttpConstant.USER_REFEREE_USER_INFO, new BaseReq(), new TypeToken<ResponseData<RefereeUserInfo>>() {
        }.getType());
    }

    /**
     * 用户给推荐人发送留言消息
     */
    public Observable<ResponseData<String>> userLeaveMessage(BaseReq params) {
        return executor.executor(HttpConstant.USER_LEAVE_MESSAGE, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 用户留言板
     */
    public Observable<ResponseData<MessageBoardEntity>> queryMessageBoard(BaseReq params) {
        return executor.executor(HttpConstant.USER_QUERY_MESSAGE_BOARD, params, new TypeToken<ResponseData<MessageBoardEntity>>() {
        }.getType());
    }

    /**
     * 用户留言板查看接口
     */
    public Observable<ResponseData<String>> updateReadState(BaseReq params) {
        return executor.executor(HttpConstant.USER_UPDATE_READ_STATE, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 用户交易记录
     */
    public Observable<ResponseData<TransactionRecordEntity>> queryTradingRecord(BaseReq params) {
        return executor.executor(HttpConstant.TRADE_QUERY_TRADING_RECORD, params, new TypeToken<ResponseData<TransactionRecordEntity>>() {
        }.getType());
    }

    /**
     * 添加/更新金融服务业务员接口
     */
    public Observable<ResponseData<PerfectInfoEntity>> saveOrUpdateServiceSalesMan(BaseReq params) {
        return executor.executor(HttpConstant.FINANCIAL_SAVE_OR_UPDATE_SERVICE_SALES_MAN, params, new TypeToken<ResponseData<PerfectInfoEntity>>() {
        }.getType());
    }

    /**
     * 获取业务员详细信息接口
     */
    public Observable<ResponseData<PerfectInfoEntity>> getServiceSalesManById() {
        return executor.executor(HttpConstant.FINANCIAL_GET_SERVICE_SALES_MAN_BY_ID, new BaseReq(), new TypeToken<ResponseData<PerfectInfoEntity>>() {
        }.getType());
    }

    /**
     * 新增招聘信息
     */
    public Observable<ResponseData<String>> releaseRecruit(BaseReq params) {
        return executor.executor(HttpConstant.RECRUIT_RELEASE_RECRUIT, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 获取用户发布的招聘列表
     */
    public Observable<ResponseData<RecruitmentInfoListEntity>> listUserForRecruits(BaseReq params) {
        return executor.executor(HttpConstant.RECRUIT_LIST_USER_FOR_RECRUITS, params, new TypeToken<ResponseData<RecruitmentInfoListEntity>>() {
        }.getType());
    }

    /**
     * 删除招聘信息
     */
    public Observable<ResponseData<String>> deleteRecruit(BaseReq params) {
        return executor.executor(HttpConstant.RECRUIT_DELETE_RECRUIT, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 开始/关闭接单
     */
    public Observable<ResponseData<String>> openOrOffReceipt(BaseReq params) {
        return executor.executor(HttpConstant.USER_OPEN_OR_OFF_RECEIPT, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 修改用户手机号
     */
    public Observable<ResponseData<String>> updateUserPhone(BaseReq params) {
        return executor.executor(HttpConstant.USER_UPDATE_USER_PHONE, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 用户修改登录密码
     */
    public Observable<ResponseData<String>> modifyUserPwwd(BaseReq params) {
        return executor.executor(HttpConstant.USER_UP_USER_PWWD, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 用户设置支付密码
     */
    public Observable<ResponseData<String>> settingPaymentPassword(BaseReq params) {
        return executor.executor(HttpConstant.USER_ADD_PAYMENT_PASSWORD, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 更多更多设置：用户是否允许下级查看手机号
     */
    public Observable<ResponseData<String>> updateExtendOne(BaseReq params) {
        return executor.executor(HttpConstant.USER_UPDATE_EXTEND_ONE, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 检查是否设置支付密码
     */
    public Observable<ResponseData<ModifySetpaymentPassword>> isSetpaymentPassword() {
        return executor.executor(HttpConstant.USER_IS_SETPAYMENT_PASSWORD, new BaseReq(), new TypeToken<ResponseData<ModifySetpaymentPassword>>() {
        }.getType());
    }

    /**
     * 修改支付密码
     */
    public Observable<ResponseData<String>> modifyPaymentPassword(BaseReq params) {
        return executor.executor(HttpConstant.USER_UP_PAYMENT_PASSWORD, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 重置支付密码（手机验证修改新密码）
     */
    public Observable<ResponseData<String>> resetPaymentPassword(BaseReq params) {
        return executor.executor(HttpConstant.USER_RESET_PAYMENT_PASSWORD, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 帮助列表
     */
    public Observable<ResponseData<HelpEntity>> cmsList(BaseReq params) {
        return executor.executor(HttpConstant.CMS_LIST, params, new TypeToken<ResponseData<HelpEntity>>() {
        }.getType());
    }

    /**
     * 提交反馈信息
     */
    public Observable<ResponseData<String>> userFeedback(BaseReq params) {
        return executor.executor(HttpConstant.USER_FEEDBACK, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 通道列表接口
     */
    public Observable<ResponseData<List<SwipeChannelEntity>>> queryChannel() {
        return executor.executor(HttpConstant.USER_QUERY_CHANNEL, new BaseReq(), new TypeToken<ResponseData<List<SwipeChannelEntity>>>() {
        }.getType());
    }

    /**
     * 获取银行提现的限额
     */
    public Observable<ResponseData<QueryBankLimitEntity>> queryBankLimit(BaseReq params) {
        return executor.executor(HttpConstant.TRADE_QUERY_BANK_LIMIT, params, new TypeToken<ResponseData<QueryBankLimitEntity>>() {
        }.getType());
    }

    /**
     * 查询是否开通快捷支付
     */
    public Observable<ResponseData<CreateQuickpayEntity>> openQuickPayQuery(BaseReq params) {
        return executor.executor(HttpConstant.TRADE_OPEN_QUICK_PAY_QUERY, params, new TypeToken<ResponseData<CreateQuickpayEntity>>() {
        }.getType());
    }

    /**
     * 刷卡交易接口
     */
    public Observable<ResponseData<CreateQuickpayEntity>> createQuickpay(BaseReq params) {
        return executor.executor(HttpConstant.USER_CREATE_QUICKPAY, params, new TypeToken<ResponseData<CreateQuickpayEntity>>() {
        }.getType());
    }

    /**
     * 添加储蓄卡
     */
    public Observable<ResponseData<String>> addUserBankCard(BaseReq params) {
        return executor.executor(HttpConstant.USER_ADD_USER_BANK_CARD, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 修改储蓄卡
     */
    public Observable<ResponseData<String>> updateUserBankCard(BaseReq params) {
        return executor.executor(HttpConstant.USER_UPDATE_USER_BANK_CARD, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 根据银行卡号获取银行的背景
     */
    public Observable<ResponseData<SelectBankCardList>> getBankBackgroundVo(BaseReq params) {
        return executor.executor(HttpConstant.USER_BANK_BACKGROUND_VO, params, new TypeToken<ResponseData<SelectBankCardList>>() {
        }.getType());
    }

    /**
     * 添加信用卡
     */
    public Observable<ResponseData<String>> addCreditCard(BaseReq params) {
        return executor.executor(HttpConstant.USER_ADD_CREDIT_CARD, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 修改信用卡开始日或者结束日
     */
    public Observable<ResponseData<String>> updateCreditCardDate(BaseReq params) {
        return executor.executor(HttpConstant.USER_UPDATE_CREDIT_CARD_DATE, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 获取所有的app菜单
     */
    public Observable<ResponseData<List<FinancialServicesEntity>>> menuTreeDate(BaseReq params) {
        return executor.executor(HttpConstant.MENU_MENU_TREE_DATE, params, new TypeToken<ResponseData<List<FinancialServicesEntity>>>() {
        }.getType());
    }

    /**
     * 获取网申列表
     */
    public Observable<ResponseData<OnlineApplicationEntity>> queryOnlineApplicationList(BaseReq params) {
        return executor.executor(HttpConstant.QUERY_ONLINE_APPLICATION_LIST, params, new TypeToken<ResponseData<OnlineApplicationEntity>>() {
        }.getType());
    }

    /**
     * 获取网贷列表
     */
    public Observable<ResponseData<List<OnlineLoanEntity>>> queryOnlineLoanList(BaseReq params) {
        return executor.executor(HttpConstant.QUERY_ONLINE_LOAN_LIST, params, new TypeToken<ResponseData<List<OnlineLoanEntity>>>() {
        }.getType());
    }

    /**
     * 获取全部文章列表
     */
    public Observable<ResponseData<List<FindArticleEntity>>> queryAppIntroduceCmsList(BaseReq params) {
        return executor.executor(HttpConstant.QUERY_APP_INTRODUCE_CMS_LIST, params, new TypeToken<ResponseData<List<FindArticleEntity>>>() {
        }.getType());
    }

    /**
     * 获取文章阅读量
     */
    public Observable<ResponseData<List<FindArticleEntity>>> getAppIntroduceCms(BaseReq params) {
        return executor.executor(HttpConstant.GET_APP_INTRODUCE_CMS, params, new TypeToken<ResponseData<List<FindArticleEntity>>>() {
        }.getType());
    }


    /**
     * 发现页数据加载接口
     */
    public Observable<ResponseData<FindEntity>> getFoundPageAllInitInfo() {
        return executor.executor(HttpConstant.FOUND_PAGE_ALL_INIT_INFO, new BaseReq(), new TypeToken<ResponseData<FindEntity>>() {
        }.getType());
    }

    /**
     * 获取商户下指定等级中的推荐的人员记录接口
     */
    public Observable<ResponseData<List<BusinessDetailsEntity>>> getRecommendUserInfoList(BaseReq params) {
        return executor.executor(HttpConstant.FOUND_RECOMMEND_USER_INFO_LIST, params, new TypeToken<ResponseData<List<BusinessDetailsEntity>>>() {
        }.getType());
    }

    /**
     * 我的分润接口
     */
    public Observable<ResponseData<DividedOrWithdrawalDetailsEntity>> listForRecords(BaseReq params) {
        return executor.executor(HttpConstant.RECORD_LIST_FOR_RECORDS, params, new TypeToken<ResponseData<DividedOrWithdrawalDetailsEntity>>() {
        }.getType());
    }

    /**
     * 查询等级信息和价格
     */
    public Observable<ResponseData<MemberCentreEntity>> search() {
        return executor.executor(HttpConstant.UPGRADE_SEARCH, new BaseReq(), new TypeToken<ResponseData<MemberCentreEntity>>() {
        }.getType());
    }

    /**
     * 购买会员等级：加密app支付交易信息
     */
    public Observable<ResponseData<String>> callOrderPay(BaseReq params) {
        return executor.executor(HttpConstant.ALIPAY_CALL_ORDER_PAY, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 购买会员等级：加密app支付交易信息
     */
    public Observable<ResponseData<String>> upgradeApply(BaseReq params) {
        return executor.executor(HttpConstant.UPGRADE_APPLY, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 个人中心：升级码升级接口
     */
    public Observable<ResponseData<String>> useUpgradeCode(BaseReq params) {
        return executor.executor(HttpConstant.USE_UPGRADE_CODE, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 支付宝会员授权获取会员信息
     */
    public Observable<ResponseData<String>> getAlipayUserInfo(BaseReq params) {
        return executor.executor(HttpConstant.GET_ALIPAY_USER_INFO, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 用户提现申请
     */
    public Observable<ResponseData<String>> userWithdraw(BaseReq params) {
        return executor.executor(HttpConstant.USER_WITHDRAW, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 查询用户提现状态
     */
    public Observable<ResponseData<FindWithdrawalEntity>> queryWithdrawDetails(BaseReq params) {
        return executor.executor(HttpConstant.USER_QUERY_WITHDRAW_DETAILS, params, new TypeToken<ResponseData<FindWithdrawalEntity>>() {
        }.getType());
    }

    /**
     * 提现明细分页查询
     */
    public Observable<ResponseData<DividedOrWithdrawalDetailsEntity>> getWithdrawRecord(BaseReq params) {
        return executor.executor(HttpConstant.USER_WITHDRAW_RECORD, params, new TypeToken<ResponseData<DividedOrWithdrawalDetailsEntity>>() {
        }.getType());
    }

    /**
     * 获取推广信息
     */
    public Observable<ResponseData<List<PromotionEntity>>> getSearch() {
        return executor.executor(HttpConstant.SPREAD_SEARCH, new BaseReq(), new TypeToken<ResponseData<List<PromotionEntity>>>() {
        }.getType());
    }

    /**
     * 查询APP广告
     */
    public Observable<ResponseData<Advert>> getAdvert() {
        return executor.executor(HttpConstant.SPREAD_ADVERT, new BaseReq(), new TypeToken<ResponseData<Advert>>() {
        }.getType());
    }

    /**
     * 商城支付成功接口
     */
    public Observable<ResponseData<String>> syncNotify(BaseReq params) {
        return executor.executor(HttpConstant.SYNC_NOTIFY, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 获取限制领取红包广告的字典
     */
    public Observable<ResponseData<RedEnvelopeCollectionRangeEntity>> getRedEnvelopeAvertisingConfigureDict() {
        return executor.executor(HttpConstant.GET_RED_ENVELOPE_AVERTISING_CONFIGURE_DICT, new BaseReq(), new TypeToken<ResponseData<RedEnvelopeCollectionRangeEntity>>() {
        }.getType());
    }

    /**
     * 查询指定用户的(全部/已使用/未使用)升级码
     */
    public Observable<ResponseData<List<ActivationCodeChildEntiviyt>>> queryUserUpgradeCode(BaseReq param) {
        return executor.executor(HttpConstant.QUERY_USER_UPGRADE_CODE, param, new TypeToken<ResponseData<List<ActivationCodeChildEntiviyt>>>() {
        }.getType());
    }

    /**
     * 获取指定用户直推下vip等级人数
     */
    public Observable<ResponseData<ManageAward>> getUserDirectPushVipByNum() {
        return executor.executor(HttpConstant.USERGET_USER_DIRECT_PUSH_VIP_BY_NUM, new BaseReq(), new TypeToken<ResponseData<ManageAward>>() {
        }.getType());
    }

    /**
     * 用户管理奖申请
     */
    public Observable<ResponseData<String>> userManagerBonusApply() {
        return executor.executor(HttpConstant.UPGRADEUSER_MANAGER_BONUS_APPLY, new BaseReq(), new TypeToken<ResponseData<String>>() {
        }.getType());
    }


    /**
     * 获取红包列表（商机列表）
     */
    public Observable<ResponseData<BusinessEntity>> getBusinessOpportunityList(BaseReq params) {
        return executor.executor(HttpConstant.GET_BUSINESS_OPPORTUNITY_LIST, params, new TypeToken<ResponseData<BusinessEntity>>() {
        }.getType());
    }

    /**
     * 判断是否可以领取红包
     */
    public Observable<ResponseData<WhetherReceiveRedEnvelopeEntity>> isReceiptConditions(BaseReq params) {
        return executor.executor(HttpConstant.IS_RECEIPT_CONDITIONS, params, new TypeToken<ResponseData<WhetherReceiveRedEnvelopeEntity>>() {
        }.getType());
    }

    /**
     * 领取红包（抢红包）
     */
    public Observable<ResponseData<String>> startGrabbingRedPackets(BaseReq params) {
        return executor.executor(HttpConstant.START_GRABBING_RED_PACKETS, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 红包信息（文章详情）
     */
    public Observable<ResponseData<ArticleDetailsEntity>> getArticleDetails(BaseReq params) {
        return executor.executor(HttpConstant.GET_ARTICLE_DETAILS, params, new TypeToken<ResponseData<ArticleDetailsEntity>>() {
        }.getType());
    }



    /**
     * 发送红包广告
     */
    public Observable<ResponseData<String>> sendRedEnvelope(BaseReq params) {
        return executor.executor(HttpConstant.SEND_RED_ENVELOPE, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 查询用户领取红包明细
     */
    public Observable<ResponseData<RedEnvelopesDetailEntity>> userReceiveRedEnvelope(BaseReq params) {
        return executor.executor(HttpConstant.USER_RECEIVE_RED_ENVELOPE, params, new TypeToken<ResponseData<RedEnvelopesDetailEntity>>() {
        }.getType());
    }
    /**
     * 查询用户发放红包明细
     */
    public Observable<ResponseData<RedEnvelopesDetailEntity>> userIssueRedEnvelope(BaseReq params) {
        return executor.executor(HttpConstant.USER_ISSUE_RED_ENVELOPE, params, new TypeToken<ResponseData<RedEnvelopesDetailEntity>>() {
        }.getType());
    }

    /**
     * 红包钱包提现
     */
    public Observable<ResponseData<String>> redWithdrawUserWithdraw(BaseReq params) {
        return executor.executor(HttpConstant.RED_WITHDRAW_USER_WITHDRAW, params, new TypeToken<ResponseData<String>>() {
        }.getType());
    }

    /**
     * 购买会员成功接口
     */
    public Observable<ResponseData<String>> synchronousCallback(BaseReq param) {
        return executor.executor(HttpConstant.ALIPAY_SYNCHRONOUS_CALLBACK, param, new TypeToken<ResponseData<String>>() {
        }.getType());
    }
}
