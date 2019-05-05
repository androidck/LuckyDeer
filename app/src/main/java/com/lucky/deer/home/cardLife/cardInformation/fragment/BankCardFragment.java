package com.lucky.deer.home.cardLife.cardInformation.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseFragment;
import com.lucky.deer.common.dialog.NickNameDialog;
import com.lucky.deer.common.dialog.UntyingCardDialog;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.home.adapter.SelectBankCardAdapter;
import com.lucky.deer.home.cardspending.AddCreditCardActivity;
import com.lucky.deer.home.cardspending.AddDebitCardActivity;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.WheelMenuDialog;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.model.request.BaseReq;
import com.lucky.model.request.quickpay.AddCardReq;
import com.lucky.model.request.quickpay.UpdataCardReq;
import com.lucky.model.response.selectbankcard.SelectBankCardList;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import xyz.yhsj.loadstatusview.LoadStatusView;

import static com.lucky.deer.configuration.KeyConstant.SELECT_CREDIT_CARD;
import static com.lucky.deer.configuration.KeyConstant.SELECT_DEBIT_CARD;

/**
 * 卡列表
 *
 * @author wangxiangyi
 * @date 2019/03/20
 */
public class BankCardFragment extends BaseFragment {

    /**
     * 下拉刷新
     */
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    /**
     * 空白页
     */
  /*  @BindView(R.id.lsv_load_status)
    LoadStatusView lsvLoadStatus;*/
    /**
     * 列表
     */
    @BindView(R.id.rv_list)
    RecyclerView rvList;



    @BindView(R.id.iv_add_card_img)
    ImageView ivAddCardImg;
    /**
     * 页面值
     */
    private int mPage;
    private SelectBankCardAdapter mAdapter;
    private PublicDialog inistanceView;
    /**
     * 添加银行卡页面
     */
    private View viewAddCard;
    /**
     * 信用卡信息
     */
    private List<SelectBankCardList> list;
    /**
     * 点击银行卡条数
     */
    private int mPosition;

    private QMUIDialog qmuiDialog, qmuiDialogUntied;

    public static BankCardFragment newInstance(Bundle args) {
        BankCardFragment fragment = new BankCardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(mEntity);
        }
    }

    @Override
    protected int initlayout() {
        return R.layout.fragment_bank_card_list;
    }

    @Override
    protected void initView() {
        rvList.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new SelectBankCardAdapter(new ArrayList<>());
        rvList.setAdapter(mAdapter);
        /*获取单利*/
        inistanceView = PublicDialog.getInstance();
        /*添加银行卡布局*/
        viewAddCard = LinearLayout.inflate(mActivity, R.layout.add_card, null);

        srlRefresh.setEnableLoadMore(false);
        srlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        refreshLayout.finishRefresh();
                    }
                },150);
            }
        });

    }

    @Override
    protected void initListener() {
        /*添加银行卡点击监听*/
        viewAddCard.setOnClickListener(v -> {
            if (getRegisterState(getUserInfo().getRegisterState())) {
                addCard();
            }
        });
        /*选择银行卡监听*/
        mAdapter.setOnItemClickListener((adapter, view, position) -> {

        });
        /*点击银行卡控件监听*/
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mPosition = position;
            switch (view.getId()) {
                /*信用卡昵称*/
                case R.id.tv_card_type2:
                    new NickNameDialog(getActivity(), list.get(position).getCreditId(), false, new NickNameDialog.OnNickNameListenter() {
                        @Override
                        public void setNickName(String str) {
                            BaseReq baseReq = new BaseReq();
                            baseReq.setPageSize(999);
                            getCreaditCard(baseReq);
                        }
                    }).show();
                    break;
                /*解绑信用卡或修改储蓄卡*/
                case R.id.tv_untied:
                    /*判断是否是储蓄*/
                    if (KeyConstant.SELECT_DEBIT_CARD == mAdapter.getCardType()) {
                        SelectBankCardList bankCardInfo = mAdapter.getData().get(position);
                        bankCardInfo.setDebitCardType(KeyConstant.MODIFY_DEBIT_CARD);
                        /*修改储蓄卡信息*/
                        startActivity(new Intent(mActivity, AddDebitCardActivity.class).putExtra(mEntity, bankCardInfo));
                    } else {
                        /*信用卡解绑弹窗*/
                        new UntyingCardDialog(getActivity(), false, list.get(position).getCreditId(), new UntyingCardDialog.OnUntyingCardListener() {
                            @Override
                            public void onSuccess(String msg) {
                                BaseReq baseReq = new BaseReq();
                                baseReq.setPageSize(999);
                                getCreaditCard(baseReq);
                                adapter.remove(position);
                            }
                        }).show();
                    }
                    break;
                /*开始日按钮*/
                case R.id.rl_billing_day:
                    selectBillingDateOrRepaymentDate(1, mAdapter.getData().get(position).getCreditId(), mAdapter.getData().get(position).getBillDate());
                    break;
                /*开始日按钮*/
                case R.id.rl_repayment_date:
                    selectBillingDateOrRepaymentDate(2, mAdapter.getData().get(position).getCreditId(), mAdapter.getData().get(position).getRepaymentDate());
                    break;
//                /*去养卡*/
//                case R.id.tv_repayment:
//                    SelectBankCardList selectBankCardInfo = selectBankCardAdapter.getData().get(position);
//                    Map<String, String> date = DateUtil.getBillingDateAndRepaymentDate(selectBankCardInfo.getBillDate(), selectBankCardInfo.getRepaymentDate());
//                    /*开始日*/
//                    selectBankCardInfo.setBillDate(date.get("billingDate"));
//                    /*结束日*/
//                    selectBankCardInfo.setRepaymentDate(date.get("repaymentDate"));
//                    /*日期养卡页面执行方法*/
//                    jumpActivity(mContext, MakingPlansActivity.class, selectBankCardInfo);
//                    break;
                default:
            }
        });
        /*修改信用卡昵称点击监听*/
        inistanceView.setOnClickListener((OnClickListener<String>) (status, useType, isPhoneNumber, text) -> {
            if (qmuiDialog != null) {
                switch (status) {
                    case OK:
                        qmuiDialog.dismiss();
                        updataNickname(mAdapter.getData().get(mPosition).getCreditId(), text);
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
                        untiedCreditCard(mAdapter.getData().get(mPosition).getCreditId());
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
    

    @Override
    protected void initData() {
        mAdapter.setCardType(mPage);
        switch (mPage) {
            case SELECT_DEBIT_CARD:
                ivAddCardImg.setImageResource(R.mipmap.choice_debit_card);
                /*隐藏开始日和结束日布局*/
                mAdapter.setShowBillingDate(false);
                /*隐藏去养卡按钮*/
                mAdapter.setShowRepayment(false);
                break;
            case SELECT_CREDIT_CARD:
                ivAddCardImg.setImageResource(R.mipmap.choice_credit_card);
                /*把布局添加到底部*/
                mAdapter.addFooterView(viewAddCard);
                /*隐藏去养卡按钮*/
                mAdapter.setShowRepayment(false);
                break;
            default:
        }
        getBankCardList();
    }

    /**
     * 添加银行卡
     */
    private void addCard() {
        switch (mPage) {
            case KeyConstant.SELECT_CREDIT_CARD:
                startActivity(new Intent(mActivity, AddCreditCardActivity.class));
                break;
            case KeyConstant.SELECT_DEBIT_CARD:
                startActivity(new Intent(mActivity, AddDebitCardActivity.class));
                break;
            default:
        }
    }

    /**
     * 获取银行卡列表
     */
    private void getBankCardList() {
        BaseReq baseReq = new BaseReq();
        baseReq.setPageSize(999);
        switch (mPage) {
            case SELECT_CREDIT_CARD:
                getCreaditCard(baseReq);
                break;
            case SELECT_DEBIT_CARD:
                getDebitCard(baseReq);
                break;
            default:
        }
        getRegistrationStatus();
    }

    /**
     * 获取储蓄卡信息
     *
     * @param baseReq 参数
     */
    @SuppressLint("CheckResult")
    private void getDebitCard(BaseReq baseReq) {
        mNetworkRequestInstance.queryDebitCard(baseReq)
                .subscribe(stringResponseData -> {
                    //lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        if (stringResponseData.getData().getList() == null ||
                                stringResponseData.getData().getList().size() < 0) {
                            mAdapter.notifyDataSetChanged();
                           // lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                            /*显示添加储蓄卡按钮*/
                        } else {
                            list = stringResponseData.getData().getList();
                            mAdapter.setNewData(list);
                            /*隐藏添加储蓄卡按钮*/

                        }
                    } else {
                    /*    if (lsvLoadStatus.getViewState() == LoadStatusView.VIEW_STATE_CONTENT) {
                            *//*显示添加储蓄卡按钮*//*
                            rlAddCard.setVisibility(View.VISIBLE);
                        }*/
                        HintUtil.showErrorWithToast(mActivity, stringResponseData.getMsg());
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
                .subscribe(stringResponseData -> {
                 //   lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        if (stringResponseData.getData().getList() == null ||
                                stringResponseData.getData().getList().size() < 0) {
                            mAdapter.notifyDataSetChanged();
                        } else {
                            list = stringResponseData.getData().getList();
                            mAdapter.setNewData(list);
                        }
                    } else {
                        HintUtil.showErrorWithToast(mActivity, stringResponseData.getMsg());
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
                .subscribe(stringResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        for (SelectBankCardList bankCardList : mAdapter.getData()) {
                            bankCardList.isDefault("0");
                            if (bankCardList.getDebitCardId().equals(debitCardId)) {
                                bankCardList.isDefault("1");
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        HintUtil.showErrorWithToast(mActivity, stringResponseData.getMsg());
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
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        mAdapter.getData().get(mPosition).setCreditAlias(creditAlias);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
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
        BaseReq baseReq = new BaseReq();
        baseReq.setPageSize(999);
        AddCardReq addCardReq = new AddCardReq();
        addCardReq.setCreditId(creditId);
        showLoadingDialog();
        mNetworkRequestInstance.delCreditCard(addCardReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        showSuccessDialog(R.string.dialog_credit_card_untied_success);
                        getCreaditCard(baseReq);
                    } else {
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                    }
                });
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
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        if (mark == 1) {
                            showSuccessDialog(R.string.dialog_successful_modification_billing_date);
                            /*设置开始日*/
                            mAdapter.getData().get(mPosition).setBillDate(date);
                        } else {
                            showSuccessDialog(R.string.dialog_successful_modification_repayment_date);
                            /*设置结束日*/
                            mAdapter.getData().get(mPosition).setRepaymentDate(date);
                        }
                        /*通知数据已更改数据*/
                        mAdapter.notifyDataSetChanged();
                    } else {
                        HintUtil.showErrorWithToast(mActivity, returnData.getMsg());
                    }
                });
    }
}
