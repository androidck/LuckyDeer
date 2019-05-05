package com.lucky.deer.mine.salesman;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.model.common.dialog.WheeInfoBean;
import com.lucky.model.request.mine.PerfectInfoReq;
import com.lucky.model.response.mine.PerfectInfoEntity;
import com.lucky.model.response.perfectinformation.AccountOpeningAreaEntity;
import com.lucky.model.response.perfectinformation.BankBranchEntity;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我是业务员：完善信息
 *
 * @author wangxiangyi
 * @date 2018/12/18
 */
public class PerfectInfoActivity extends BaseActivity {
    /**
     * 标题
     */
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    /**
     * 用户id
     */
    @BindView(R.id.tv_user_id)
    TextView tvUserId;
    /**
     * 公司名称
     */
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    /**
     * 公司介绍
     */
    @BindView(R.id.tv_company_synopsis)
    TextView tvCompanySynopsis;
    /**
     * 公司地址
     */
    @BindView(R.id.tv_customer_address)
    TextView tvCustomerAddress;
    /**
     * 业务介绍
     */
    @BindView(R.id.tv_business_introduction)
    TextView tvBusinessIntroduction;
    /**
     * 业务员了类型
     */
    @BindView(R.id.tv_salesperson_type)
    TextView tvSalespersonType;
    /**
     * 公司地址信息
     */
    private List<AccountOpeningAreaEntity> regionalData;
    private View mList1, mList3;
    private QMUIBottomSheet dialogAddress;
    /**
     * salespersonType：业务员类型
     * customerAddress：公司地址
     */
    private String salespersonType, customerAddress;
    /**
     * 业务员信息
     */
    private PerfectInfoEntity data;


    @Override
    protected int initLayout() {
        return R.layout.activity_perfect_info;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_perfect_info);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        if (getUserInfo() != null) {
            tvUserId.setText(getUserInfo().getUserNo());
        }
        /*获取区域id*/
        showLoadingDialog();
        mNetworkRequestInstance.getAllAreaList()
                .subscribe(listResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(listResponseData)) {
                        regionalData = listResponseData.getData();
                        if (regionalData != null) {
                            mList3 = PublicDialog.getInstance().getWheelList(mContext, regionalData, 3);
                        }
                    } else {
                        HintUtil.showErrorWithToast(mActivity, listResponseData.getMsg());
                    }
                });
        /*获取业务员信息*/
        mNetworkRequestInstance.getServiceSalesManById()
                .subscribe(returnData -> {
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        if (returnData.getData() != null) {
                            data = returnData.getData();
                            tvCompanyName.setText(data.getFinancialCompanyId());
                            tvCompanySynopsis.setText(data.getFinancialCompanyDesc());
                            tvCustomerAddress.setText(data.getFinancialCompanyAddress());
                            tvBusinessIntroduction.setText(data.getBusinessIntroduction());
                            if (!TextUtils.isEmpty(data.getSalesmanType()) &&
                                    Integer.parseInt(data.getSalesmanType()) > 0 &&
                                    getdata(R.array.salesperson_type_data) != null &&
                                    getdata(R.array.salesperson_type_data).length > 0 &&
                                    Integer.parseInt(data.getSalesmanType()) <= getdata(R.array.salesperson_type_data).length) {
                                salespersonType = data.getSalesmanType();
                                tvSalespersonType.setText(getdata(R.array.salesperson_type_data)[Integer.parseInt(data.getSalesmanType()) - 1]);
                            }
                        }
                    } else {
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
        if (getdata(R.array.salesperson_type_data) != null) {
            List<BankBranchEntity> list = new ArrayList<>();
            for (int i = 0; i < getdata(R.array.salesperson_type_data).length; i++) {
                BankBranchEntity entity = new BankBranchEntity();
                entity.setName(getdata(R.array.salesperson_type_data)[i]);
                entity.setId(String.valueOf(i + 1));
                list.add(entity);
            }
            mList1 = PublicDialog.getInstance().getWheelList(mContext, list, 1);
        }
    }

    @Override
    protected void initListener() {
        PublicDialog
                .getInstance()
                .setOnClickListener((OnClickListener<WheeInfoBean>) (status,useType, isPhoneNumber, text) -> {
                    switch (status) {
                        case OK:
                            if ("1".equals(text.getTypd())) {
                                salespersonType = text.getCode();
                                tvSalespersonType.setText(text.getName());

                            } else if ("3".equals(text.getTypd())) {
                                customerAddress = text.getCode();
                                tvCustomerAddress.setText(text.getName());
                            }
                            break;
                        default:
                    }
                    if (dialogAddress != null && dialogAddress.isShowing()) {
                        dialogAddress.dismiss();
                        dialogAddress = null;
                    }
                });
    }

    @OnClick({R.id.rl_company_name, R.id.rl_company_synopsis, R.id.ll_customer_address,
            R.id.rl_business_introduction, R.id.ll_salesperson_type, R.id.tv_submit_Info})
    public void onViewClicked(View view) {
        Class mClass = null;
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            /*公司名称*/
            case R.id.rl_company_name:
                mClass = CompanySynopsisActivity.class;
                bundle.putInt(CompanySynopsisActivity.TYPE, KeyConstant.COMPANY_NAME);
                bundle.putString(CompanySynopsisActivity.CONTENT, tvCompanyName.getText().toString().trim());
                break;
            /*公司简介*/
            case R.id.rl_company_synopsis:
                mClass = CompanySynopsisActivity.class;
                bundle.putInt(CompanySynopsisActivity.TYPE, KeyConstant.COMPANY_PROFILE);
                bundle.putString(CompanySynopsisActivity.CONTENT, tvCompanySynopsis.getText().toString().trim());
                break;
            /*公司地址*/
            case R.id.ll_customer_address:
                dialogAddress = PublicDialog.getInstance().addHeaderView(mContext, mList3);
                if (dialogAddress != null && regionalData != null) {
                    dialogAddress.show();
                }
                break;
            /*业务介绍*/
            case R.id.rl_business_introduction:
                mClass = CompanySynopsisActivity.class;
                bundle.putInt(CompanySynopsisActivity.TYPE, KeyConstant.BUSINESS_PROFILE);
                bundle.putString(CompanySynopsisActivity.CONTENT, tvBusinessIntroduction.getText().toString().trim());
                break;
            /*业务员类型*/
            case R.id.ll_salesperson_type:
                dialogAddress = PublicDialog.getInstance().addHeaderView(mContext, mList1);
                if (dialogAddress != null && regionalData != null) {
                    dialogAddress.show();
                }
                break;
            /*提交信息*/
            case R.id.tv_submit_Info:
                examineRequiredVerification();
                break;
            default:
        }
        if (mClass != null) {
            jumpActivityForResult2(mActivity, mClass, bundle.getInt(CompanySynopsisActivity.TYPE), bundle);
        }
    }


    @Override
    protected boolean examineRequiredVerification() {
        if (TextUtils.isEmpty(tvCompanyName.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_company_name_not_empty);
            return false;
        } else if (TextUtils.isEmpty(tvCompanySynopsis.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_company_synopsis_not_empty);
            return false;
        } else if (TextUtils.isEmpty(tvCustomerAddress.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_select_customer_address);
            return false;
        } else if (TextUtils.isEmpty(tvBusinessIntroduction.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_business_introduction_not_empty);
            return false;
        } else if (TextUtils.isEmpty(tvSalespersonType.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_select_salesperson_type);
            return false;
        }
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        PerfectInfoReq perfectInfoReq = new PerfectInfoReq();
        if (data != null && !TextUtils.isEmpty(data.getId())) {
            perfectInfoReq.setId(data.getId());
            perfectInfoReq.setStatus(data.getStatus());
        }
        /*公司名称*/
        perfectInfoReq.setFinancialCompanyId(tvCompanyName.getText().toString().trim());
        /*公司简介*/
        perfectInfoReq.setFinancialCompanyDesc(tvCompanySynopsis.getText().toString().trim());
        /*公司地址*/
        perfectInfoReq.setFinancialCompanyAddress(tvCustomerAddress.getText().toString().trim());
        /*业务介介绍*/
        perfectInfoReq.setBusinessIntroduction(tvBusinessIntroduction.getText().toString().trim());
        /*业务员类型*/
        perfectInfoReq.setSalesmanType(salespersonType);
        /*提交完善信息接口*/
        showLoadingDialog();
        mNetworkRequestInstance.saveOrUpdateServiceSalesMan(perfectInfoReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        showSuccessDialog(R.string.dialog_perfect_info_success);
                    } else {
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }

    @Override
    public void carriedOutMethod() {
        overridePendingTransition(false, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                /*公司名称*/
                case KeyConstant.COMPANY_NAME:
                    tvCompanyName.setText(data.getStringExtra(mEntity));
                    break;
                /*公司介绍*/
                case KeyConstant.COMPANY_PROFILE:
                    tvCompanySynopsis.setText(data.getStringExtra(mEntity));
                    break;
                /*业务介绍*/
                case KeyConstant.BUSINESS_PROFILE:
                    tvBusinessIntroduction.setText(data.getStringExtra(mEntity));
                    break;
                default:
            }
        }

    }

}
