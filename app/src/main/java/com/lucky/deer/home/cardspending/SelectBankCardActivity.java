package com.lucky.deer.home.cardspending;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.home.adapter.SelectBankCardAdapter;
import com.lucky.deer.home.pepayment.date.MakingPlansActivity;
import com.lucky.deer.home.pepayment.perfect.PerfectMakingPlansActivity;
import com.lucky.deer.util.DateUtil;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.WheelMenuDialog;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.model.request.BaseReq;
import com.lucky.model.request.quickpay.AddCardReq;
import com.lucky.model.request.quickpay.UpdataCardReq;
import com.lucky.model.response.selectbankcard.SelectBankCardList;
import com.lucky.model.util.HintUtil;
import com.lucky.model.util.RxBus;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import xyz.yhsj.loadstatusview.LoadStatusView;

/**
 * 选择银行卡
 *
 * @author wangxingyi
 * @date 2018/10/10
 */
public class SelectBankCardActivity extends BaseActivity {
    /**
     * 获取当前（选择银行卡）Activity
     */
    @SuppressLint("StaticFieldLeak")
    public static SelectBankCardActivity mSelectBankCardActivity;

    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    @BindView(R.id.rv_list_card)
    SwipeRecyclerView rvListCard;
    @BindView(R.id.lsv_load_status)
    LoadStatusView lsvLoadStatus;
    @BindView(R.id.rl_add_card)
    RelativeLayout rlAddCard;
    @BindView(R.id.tv_add_card_name)
    TextView tvAddCardName;
    @BindView(R.id.iv_add_card_img)
    ImageView ivAddCardImg;
    private SelectBankCardAdapter selectBankCardAdapter;
    private SelectBankCardList selectBankCardList;
    private List<SelectBankCardList> list;
    private PublicDialog inistanceView;
    private QMUIDialog qmuiDialog, qmuiDialogUntied;
    private String debitCardId;
    private SwipeMenuBridge mMenuBridge;
    private View viewAddCard;
    /**
     * 点击银行卡条数
     */
    private int mPosition;

    @Override
    protected int initLayout() {
        return R.layout.activity_add_bank_card;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        mSelectBankCardActivity = this;
        selectBankCardList = (SelectBankCardList) getSerializableData();
        /*添加银行卡页面*/
        if (selectBankCardList != null) {
            initTopBar(topBar,
                    selectBankCardList.getFlag() == KeyConstant.SELECT_CREDIT_CARD ? R.string.title_activity_select_credit_card : R.string.title_activity_select_debit_card,
                    R.mipmap.choice_add_card, v -> {
                        if (getRegisterState(getUserInfo().getRegisterState())) {
                            addCard();
                        }
                    });
        }
        rvListCard.setLayoutManager(new LinearLayoutManager(mContext));
        selectBankCardAdapter = new SelectBankCardAdapter(list);
        if (selectBankCardList != null) {
            /*刷卡消费*/
            if (KeyConstant.CARD_SPENDING.equals(selectBankCardList.getEnterAisleFlag())) {
                /*隐藏绑定按钮*/
                selectBankCardAdapter.setShowUntiedCard(false);
                /*隐藏开始日和结束日布局*/
                selectBankCardAdapter.setShowBillingDate(false);
                /*隐藏去养卡按钮*/
                selectBankCardAdapter.setShowRepayment(false);
            }
            /*判断日期养卡*/
            else if (KeyConstant.DATE_REPAYMENT.equals(selectBankCardList.getEnterAisleFlag()) ||
                    KeyConstant.PERFECT_REPAYMENT.equals(selectBankCardList.getEnterAisleFlag())) {
                /*隐藏绑定按钮*/
                selectBankCardAdapter.setShowUntiedCard(false);
                /*隐藏去养卡按钮*/
                selectBankCardAdapter.setShowRepayment(false);
            }

        }
//        if (selectBankCardList != null && KeyConstant.SELECT_DEBIT_CARD == selectBankCardList.getFlag()) {
//            /*添加侧滑按钮*/
//            rvListCard.setSwipeMenuCreator((swipeLeftMenu, swipeRightMenu, viewType) -> {
//                /*添加菜单到右侧。*/
//                swipeRightMenu.addMenuItem(new SwipeMenuItem(mContext)
//                        .setImage(R.mipmap.set_default_bg)
//                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
//                        .setWidth(getResources().getDimensionPixelSize(R.dimen.dp_70)));
//            });
//            /*点击监听*/
//            rvListCard.setSwipeMenuItemClickListener(menuBridge -> {
//                mMenuBridge = menuBridge;
//                if (!"1".equals(selectBankCardAdapter.getData().get(menuBridge.getAdapterPosition()).isDefault())) {
//                    qmuiDialog = inistanceView.setCustomizeView(inistanceView.initEtOrTvView(mContext, getString(R.string.dialog_prompt), getString(R.string.dialog_setting_default), true), 0.25f);
//                    debitCardId = selectBankCardAdapter.getData().get(menuBridge.getAdapterPosition()).getDebitCardId();
//                } else {
//                    menuBridge.closeMenu();
//                }
//            });
//        }
        rvListCard.setAdapter(selectBankCardAdapter);
        /*获取单利*/
        inistanceView = PublicDialog.getInstance();
        /*添加银行卡布局*/
        viewAddCard = LinearLayout.inflate(mContext, R.layout.add_card, null);
        /*通知监听*/
        RxBus.getInstance()
                .toObservable()
                .subscribe(s -> {
                    if (KeyConstant.NOTICE_ADD_BANK_CARD.equals(s)) {
                        getBankCardList();
                    }
                });

    }

    @Override
    protected void initData() {
        if (selectBankCardList != null) {
            selectBankCardAdapter.setCardType(selectBankCardList.getFlag());
            switch (selectBankCardList.getFlag()) {
                case KeyConstant.SELECT_CREDIT_CARD:
                    tvAddCardName.setText(R.string.title_activity_add_credit_card);
                    /*清楚标题右边控件*/
                    topBar.removeAllRightViews();
                    /*把布局添加到底部*/
                    rvListCard.addFooterView(viewAddCard);
                    ivAddCardImg.setImageResource(R.mipmap.choice_credit_card);
                    break;
                case KeyConstant.SELECT_DEBIT_CARD:
                    tvAddCardName.setText(R.string.title_activity_add_debit_card);
                    ivAddCardImg.setImageResource(R.mipmap.choice_debit_card);
                    /*隐藏开始日和结束日布局*/
                    selectBankCardAdapter.setShowBillingDate(false);
                    /*隐藏去养卡按钮*/
                    selectBankCardAdapter.setShowRepayment(false);
                    break;
                default:
            }
        }
        getBankCardList();
    }

    @Override
    protected void initListener() {
        /*下拉刷新监听*/
        srlRefresh.setOnRefreshListener(this::getBankCardList);
        /*选择银行卡监听*/
        selectBankCardAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (selectBankCardList != null) {
                if (KeyConstant.CARD_SPENDING.equals(selectBankCardList.getEnterAisleFlag())) {
                    /*刷卡消费页面执行方法*/
                    Intent intent = new Intent();
                    intent.putExtra(mEntity, selectBankCardAdapter.getData().get(position));
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else if (KeyConstant.DATE_REPAYMENT.equals(selectBankCardList.getEnterAisleFlag())) {
                    SelectBankCardList selectBankCardInfo = selectBankCardAdapter.getData().get(position);
                    Map<String, String> date = DateUtil.getBillingDateAndRepaymentDate(selectBankCardInfo.getBillDate(), selectBankCardInfo.getRepaymentDate());
                    /*开始日*/
                    selectBankCardInfo.setBillDate(date.get("billingDate"));
                    /*结束日*/
                    selectBankCardInfo.setRepaymentDate(date.get("repaymentDate"));
                    /*日期养卡页面执行方法*/
                    jumpActivity(mContext, MakingPlansActivity.class, selectBankCardInfo);
                }
                /*完美养卡*/
                else if (KeyConstant.PERFECT_REPAYMENT.equals(selectBankCardList.getEnterAisleFlag())) {
                    SelectBankCardList selectBankCardInfo = selectBankCardAdapter.getData().get(position);
                    Map<String, String> date = DateUtil.getBillingDateAndRepaymentDate(selectBankCardInfo.getBillDate(), selectBankCardInfo.getRepaymentDate());
                    /*开始日*/
                    selectBankCardInfo.setBillDate(date.get("billingDate"));
                    /*结束日*/
                    selectBankCardInfo.setRepaymentDate(date.get("repaymentDate"));
                    /*完美养卡页面执行方法*/
                    jumpActivity(mContext, PerfectMakingPlansActivity.class, selectBankCardInfo);
                }
            }
        });
        /*添加银行卡点击监听*/
        viewAddCard.setOnClickListener(v -> {
            if (getRegisterState(getUserInfo().getRegisterState())) {
                addCard();
            }
        });
        /*点击银行卡控件监听*/
        selectBankCardAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mPosition = position;
            switch (view.getId()) {
                case R.id.tv_card_type2:
                    qmuiDialog = inistanceView.setCustomizeView(
                            inistanceView.initEtOrTvView(mActivity,
                                    selectBankCardAdapter.getData().get(position).getCreditAlias(),
                                    false),
                            true,
                            KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_5);
                    break;
                case R.id.tv_untied:
                    /*判断是否是储蓄*/
                    if (KeyConstant.SELECT_DEBIT_CARD == selectBankCardAdapter.getCardType()) {
                        SelectBankCardList bankCardInfo = selectBankCardAdapter.getData().get(position);
                        bankCardInfo.setDebitCardType(KeyConstant.MODIFY_DEBIT_CARD);
                        /*修改储蓄卡信息*/
                        jumpActivity(mContext, AddDebitCardActivity.class, bankCardInfo);
                    } else {
                        /*信用卡解绑弹窗*/
                        qmuiDialogUntied = inistanceView.setCustomizeView(
                                inistanceView.initTitleEtOrTvView(
                                        mContext, getString(R.string.dialog_untied_credit_card_title), getString(R.string.dialog_untied_credit_card),
                                        true),
                                KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_4);
                    }
                    break;
                /*开始日按钮*/
                case R.id.rl_billing_day:
                    selectBillingDateOrRepaymentDate(1, selectBankCardAdapter.getData().get(position).getCreditId(), selectBankCardAdapter.getData().get(position).getBillDate());
                    break;
                /*开始日按钮*/
                case R.id.rl_repayment_date:
                    selectBillingDateOrRepaymentDate(2, selectBankCardAdapter.getData().get(position).getCreditId(), selectBankCardAdapter.getData().get(position).getRepaymentDate());
                    break;
                /*去养卡*/
                case R.id.tv_repayment:
                    SelectBankCardList selectBankCardInfo = selectBankCardAdapter.getData().get(position);
                    Map<String, String> date = DateUtil.getBillingDateAndRepaymentDate(selectBankCardInfo.getBillDate(), selectBankCardInfo.getRepaymentDate());
                    /*开始日*/
                    selectBankCardInfo.setBillDate(date.get("billingDate"));
                    /*结束日*/
                    selectBankCardInfo.setRepaymentDate(date.get("repaymentDate"));
                    /*日期养卡页面执行方法*/
                    jumpActivity(mContext, MakingPlansActivity.class, selectBankCardInfo);
                    break;
                default:
            }
        });
        /*修改信用卡昵称点击监听*/
        inistanceView.setOnClickListener((OnClickListener<String>) (status, useType, isPhoneNumber, text) -> {
            if (qmuiDialog != null) {
                switch (status) {
                    case OK:
                        qmuiDialog.dismiss();
                        updataNickname(selectBankCardAdapter.getData().get(mPosition).getCreditId(), text);
                        break;
                    case CANCEL:
                        qmuiDialog.dismiss();
                        break;
                    default:
                }
                qmuiDialog = null;
            } else if (qmuiDialogUntied != null) {
                switch (status) {
                    case OK:
                        qmuiDialogUntied.dismiss();
                        untiedCreditCard(selectBankCardAdapter.getData().get(mPosition).getCreditId());
                        break;
                    case CANCEL:
                        qmuiDialogUntied.dismiss();
                        break;
                    default:
                }
                qmuiDialogUntied = null;
            }
        });
    }

    /**
     * 获取银行卡列表
     */
    private void getBankCardList() {
        if (selectBankCardList != null) {
            BaseReq baseReq = new BaseReq();
            baseReq.setPageSize(999);
            if (!srlRefresh.isRefreshing()) {
                showLoadingDialog();
            }
            switch (selectBankCardList.getFlag()) {
                case KeyConstant.SELECT_CREDIT_CARD:
                    getCreaditCard(baseReq);
                    break;
                case KeyConstant.SELECT_DEBIT_CARD:
                    getDebitCard(baseReq);
                    break;
                default:
            }
            getRegistrationStatus();
        }
    }

    /**
     * 获取储蓄卡信息
     *
     * @param baseReq 参数
     */
    @SuppressLint("CheckResult")
    private void getDebitCard(BaseReq baseReq) {
        mNetworkRequestInstance.queryDebitCard(baseReq)
                .compose(bindToLifecycle())
                .subscribe(stringResponseData -> {
                    lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        if (stringResponseData.getData().getList() == null ||
                                stringResponseData.getData().getList().size() < 0) {
                            selectBankCardAdapter.notifyDataSetChanged();
                            lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                            /*显示添加储蓄卡按钮*/
                            rlAddCard.setVisibility(View.VISIBLE);
                        } else {
                            list = stringResponseData.getData().getList();
                            selectBankCardAdapter.setNewData(list);
                            /*清楚标题右边控件*/
                            topBar.removeAllRightViews();
                            /*隐藏添加储蓄卡按钮*/
                            rlAddCard.setVisibility(View.GONE);
                        }
                    } else {
                        if (lsvLoadStatus.getViewState() == LoadStatusView.VIEW_STATE_CONTENT) {
                            /*显示添加储蓄卡按钮*/
                            rlAddCard.setVisibility(View.VISIBLE);
                        }
                        HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                    }
                });
    }

    /**
     * 获取信用卡信息
     *
     * @param baseReq 参数
     */
    @SuppressLint("CheckResult")
    private void getCreaditCard(BaseReq baseReq) {
        mNetworkRequestInstance.queryCreditCard(baseReq)
                .compose(bindToLifecycle())
                .subscribe(stringResponseData -> {
                    lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        if (stringResponseData.getData().getList() == null ||
                                stringResponseData.getData().getList().size() < 0) {
                            selectBankCardAdapter.notifyDataSetChanged();
                            lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                            /*显示添加信用卡按钮*/
                            rlAddCard.setVisibility(View.VISIBLE);
                        } else {
                            list = stringResponseData.getData().getList();
                            selectBankCardAdapter.setNewData(list);
                            /*隐藏添加信用卡按钮*/
                            rlAddCard.setVisibility(View.GONE);
                        }
                    } else {
                        if (lsvLoadStatus.getViewState() == LoadStatusView.VIEW_STATE_CONTENT) {
                            /*隐藏添加信用卡按钮*/
                            rlAddCard.setVisibility(View.GONE);
                        } else {
                            /*显示添加信用卡按钮*/
                            rlAddCard.setVisibility(View.VISIBLE);
                        }
                        HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                    }
                });
    }

    /**
     * 修改默认储蓄卡
     *
     * @param debitCardId
     */
    @SuppressLint("CheckResult")
    public void defaultSavingsCard(String debitCardId) {
        AddCardReq addCardReq = new AddCardReq();
        addCardReq.setDebitCardId(debitCardId);
        showLoadingDialog();
        mNetworkRequestInstance.modifyDefaultDebitCard(addCardReq)
                .compose(bindToLifecycle())
                .subscribe(stringResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        for (SelectBankCardList bankCardList : selectBankCardAdapter.getData()) {
                            bankCardList.isDefault("0");
                            if (bankCardList.getDebitCardId().equals(debitCardId)) {
                                bankCardList.isDefault("1");
                            }
                        }
                        selectBankCardAdapter.notifyDataSetChanged();
                    } else {
                        HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                    }
                });
    }

    /**
     * 修改信用卡昵称
     *
     * @param creditId    信用卡id
     * @param creditAlias 修改的昵称
     */
    @SuppressLint("CheckResult")
    private void updataNickname(String creditId, String creditAlias) {
        AddCardReq addCardReq = new AddCardReq();
        addCardReq.setCreditId(creditId);
        addCardReq.setCreditAlias(creditAlias);
        showLoadingDialog();
        mNetworkRequestInstance.updataNickname(addCardReq)
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        selectBankCardAdapter.getData().get(mPosition).setCreditAlias(creditAlias);
                        selectBankCardAdapter.notifyDataSetChanged();
                    } else {
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }

    /**
     * 解绑信用卡接口
     *
     * @param creditId
     */
    @SuppressLint("CheckResult")
    private void untiedCreditCard(String creditId) {
        AddCardReq addCardReq = new AddCardReq();
        addCardReq.setCreditId(creditId);
        showLoadingDialog();
        mNetworkRequestInstance.delCreditCard(addCardReq)
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        showSuccessDialog(R.string.dialog_credit_card_untied_success);
                        selectBankCardAdapter.remove(mPosition);
                        selectBankCardAdapter.notifyDataSetChanged();
                        if (selectBankCardAdapter.getItemCount() <= 0) {
                            lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                            /*显示添加信用卡按钮*/
                            rlAddCard.setVisibility(View.VISIBLE);
                        }
                    } else {
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }

    @OnClick(R.id.rl_add_card)
    public void onViewClicked() {
        if (getRegisterState(getUserInfo().getRegisterState())) {
            addCard();
        }
    }

    /**
     * 添加银行卡
     */
    private void addCard() {
        if (selectBankCardList != null) {
            switch (selectBankCardList.getFlag()) {
                case KeyConstant.SELECT_CREDIT_CARD:
                    jumpActivity(mContext, AddCreditCardActivity.class);
                    break;
                case KeyConstant.SELECT_DEBIT_CARD:
                    jumpActivity(mContext, AddDebitCardActivity.class);
                    break;
                default:
            }
        }
    }

    /**
     * 选择开始日或结束日
     *
     * @param mark 1:开始日
     *             2：结束日
     */
    public void selectBillingDateOrRepaymentDate(int mark, String creditId, String selectedNumber) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            String s;
            if (i < 10) {
                s = "0" + i;
            } else {
                s = String.valueOf(i);
            }
            list.add(s);
        }
        WheelMenuDialog.getInstance()
                .level1WheelMenu(mActivity,
                        list,
                        selectedNumber,
                        "日",
                        (i, s) -> {
                            switch (mark) {
                                /*开始日*/
                                case 1:
                                    updateCreditCardDate(mark, creditId, s);
                                    break;
                                /*结束日*/
                                case 2:
//                                    if (TextUtils.isEmpty(tvBillingDay.getText().toString().trim())) {
//                                        HintUtil.showErrorWithToast(mContext, R.string.toast_please_choose_credit_card_billing_day);
//                                    } else if (TextUtils.isEmpty(s)) {
//                                        HintUtil.showErrorWithToast(mContext, R.string.toast_please_choose_credit_card_repayment_day);
//                                        return;
//                                    }
//                                    /*开始日小于结束日时*/
//                                    else if (StringUtil.setComparison(tvBillingDay.getText().toString().trim(), s) &&
//                                            /*结束日减去开始日不能小于16天*/
//                                            Integer.parseInt(StringUtil.getSubtract(s, tvBillingDay.getText().toString().trim())) < 15) {
//                                        HintUtil.showErrorWithToast(mContext, "开始日与结束日间隔不能小于十六天");
//                                        return;
//                                    }
//                                    tvRepaymentDay.setText(s);
                                    updateCreditCardDate(mark, creditId, s);
                                    break;
                                default:
                            }
                        });
    }

    /**
     * 修改信用卡开始日和结束日接口
     *
     * @param mark   1:开始日
     *               2：结束日
     * @param credId 信用卡id
     * @param date   要修改的日期
     */
    @SuppressLint("CheckResult")
    private void updateCreditCardDate(int mark, String credId, String date) {
        UpdataCardReq updataCardReq = new UpdataCardReq();
        updataCardReq.setType(String.valueOf(mark));
        updataCardReq.setId(credId);
        updataCardReq.setDate(date);
        showLoadingDialog();
        mNetworkRequestInstance.updateCreditCardDate(updataCardReq)
                .compose(bindToLifecycle())
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        if (mark == 1) {
                            showSuccessDialog(R.string.dialog_successful_modification_billing_date);
                            /*设置开始日*/
                            selectBankCardAdapter.getData().get(mPosition).setBillDate(date);
                        } else {
                            showSuccessDialog(R.string.dialog_successful_modification_repayment_date);
                            /*设置结束日*/
                            selectBankCardAdapter.getData().get(mPosition).setRepaymentDate(date);
                        }
                        /*通知数据已更改数据*/
                        selectBankCardAdapter.notifyDataSetChanged();
                    } else {
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }
}
