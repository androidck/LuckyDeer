package com.lucky.deer.find.withdraw;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.find.adapter.SubmitFindWithdrawalAdapter;
import com.lucky.deer.util.StringUtil;
import com.lucky.model.request.find.FindWithdrawalReq;
import com.lucky.model.response.find.FindWithdrawalEntity;
import com.lucky.model.response.find.SubmitFindWithdrawalEntity;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 提交提现页面
 *
 * @author wangxiangyi
 * @date 2018/11/15
 */
public class SubmitFindWithdrawalActivity extends BaseActivity {
    /**
     * 标题
     */
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    /**
     * 提现进度
     */
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    /**
     * 显示提现金额布局
     */
    @BindView(R.id.ll_withdraw_info)
    LinearLayout llWithdrawInfo;
    /**
     * 总金额
     */
    @BindView(R.id.tv_withdrawal_amount)
    TextView tvWithdrawalAmount;
    /**
     * 手续费
     */
    @BindView(R.id.tv_handling_fee)
    TextView tvHandlingFee;
    /**
     * 尾号和银行
     */
    @BindView(R.id.tv_arrival_bank_card)
    TextView tvArrivalBankCard;
    /**
     * 完成或重复提交按钮
     */
    @BindView(R.id.tv_commit)
    TextView tvCommit;

    private String data;
    private SubmitFindWithdrawalAdapter adapter;
    private List<SubmitFindWithdrawalEntity> list = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_find_submit_withdrawal_adapter;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, true, R.string.title_activity_find_withdrawal);
        if (getSerializableData() != null) {
            data = getStringData();
        }
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new SubmitFindWithdrawalAdapter();
        rvList.setAdapter(adapter);
        SubmitFindWithdrawalEntity entity = new SubmitFindWithdrawalEntity();
        entity.setLineTopView(false);
        entity.setLineButtomView(true);
        entity.setLineButtomViewGreen(true);
        entity.setDealWithStatus(getString(R.string.start_withdrawal_application));
        entity.setScheduleDot(R.drawable.rectangle_green_full_12);
        list.add(entity);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        FindWithdrawalReq findWithdrawalReq = new FindWithdrawalReq();
        findWithdrawalReq.setId(data);
        showLoadingDialog();
        mNetworkRequestInstance.queryWithdrawDetails(findWithdrawalReq)
                .subscribe(stringResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        tvCommit.setVisibility(View.VISIBLE);
                        FindWithdrawalEntity data = stringResponseData.getData();
                        if (!TextUtils.isEmpty(data.getWithdrawMoney()) ||
                                !TextUtils.isEmpty(data.getTransferPoundage()) ||
                                !TextUtils.isEmpty(data.getCarNumber())) {
                            tvWithdrawalAmount.setText("￥" + data.getWithdrawMoney());
                            tvHandlingFee.setText(StringUtil.setNumberFormatting(data.getTransferPoundage(), 2));
                            tvArrivalBankCard.setText(data.getOpenBank() + " " +
                                    StringUtil.substringData(data.getCarNumber(), data.getCarNumber().length() - 4, data.getCarNumber().length()));
                        }
                        switch (data.getWithdrawStatus()) {
                            /*未审核*/
                            case "1":
                                /*已审核*/
                            case "2":
                                isCashWithdrawalSuccess(1, data.getPaymentDate());
                                break;
                            /*审核失败*/
                            case "3":
                                /*转账失败 已转账为转账成功*/
                            case "5":
                                isCashWithdrawalSuccess(3, data.getPaymentDate());
                                break;
                            /*已转账*/
                            case "4":
                                isCashWithdrawalSuccess(2, data.getPaymentDate());
                                break;
                            default:
                        }
                    } else {
                        HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                    }
                });
    }

    /**
     * 提现处理状态
     *
     * @param status 1:正在处理
     *               2：到账成功
     *               3：审核失败
     * @param reason 显示原因或预计时间
     */
    private void isCashWithdrawalSuccess(int status, String reason) {
        /*成功*/
        switch (status) {
            case 1:
                /*展示提现金额布局*/
                llWithdrawInfo.setVisibility(View.VISIBLE);
                tvCommit.setText(R.string.carry_out);
                /*正在处理中*/
                SubmitFindWithdrawalEntity processing = new SubmitFindWithdrawalEntity();
                processing.setLineTopView(true);
                processing.setLineTopViewGreen(true);
                processing.setLineButtomView(true);
                processing.setDealWithStatus(getString(R.string.bank_processing));
                processing.setDealWithReason(reason);
                processing.setScheduleDot(R.mipmap.bank_time);
                list.add(processing);
                SubmitFindWithdrawalEntity notArrival = new SubmitFindWithdrawalEntity();
                notArrival.setLineTopView(true);
                notArrival.setLineButtomView(false);
                notArrival.setDealWithStatus(getString(R.string.successful_arrival));
                notArrival.setScheduleDot(R.drawable.rectangle_gray_full_12);
                list.add(notArrival);
                adapter.setNewData(list);
                break;
            case 2:
                /*展示提现金额布局*/
                llWithdrawInfo.setVisibility(View.VISIBLE);
                tvCommit.setText(R.string.carry_out);
                /*到账成功*/
                SubmitFindWithdrawalEntity processingSuccess = new SubmitFindWithdrawalEntity();
                processingSuccess.setLineTopView(true);
                processingSuccess.setLineTopViewGreen(true);
                processingSuccess.setLineButtomView(true);
                processingSuccess.setLineButtomViewGreen(true);
                processingSuccess.setDealWithStatus(getString(R.string.bank_processing));
                processingSuccess.setDealWithReason(reason);
                processingSuccess.setScheduleDot(R.mipmap.bank_time);
                list.add(processingSuccess);
                SubmitFindWithdrawalEntity successfulArrival = new SubmitFindWithdrawalEntity();
                successfulArrival.setLineTopView(true);
                successfulArrival.setLineTopViewGreen(true);
                successfulArrival.setLineButtomView(false);
                successfulArrival.setDealWithStatus(getString(R.string.successful_arrival));
                successfulArrival.setScheduleDot(R.drawable.rectangle_green_full_12);
                list.add(successfulArrival);
                adapter.setNewData(list);
                break;
            case 3:
                /*隐藏提现金额布局*/
                llWithdrawInfo.setVisibility(View.INVISIBLE);
                tvCommit.setText(R.string.resubmit);
                /*失败*/
                SubmitFindWithdrawalEntity auditFailure = new SubmitFindWithdrawalEntity();
                auditFailure.setLineTopView(true);
                auditFailure.setLineTopViewGreen(true);
                auditFailure.setLineButtomView(false);
                auditFailure.setDealWithStatus(getString(R.string.audit_failure));
                auditFailure.setDealWithReason(reason);
                auditFailure.setScheduleDot(R.mipmap.bank_time_fail);
                list.add(auditFailure);
                adapter.setNewData(list);
                break;
            default:
        }
    }


    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        /*判断是否是提现完成*/
        if (getString(R.string.carry_out).equals(tvCommit.getText().toString().trim())) {
            finish();
        } else {
            /*重复提交*/
            jumpActivity(mContext, FindWithdrawalActivity.class, true);
        }
    }
}
