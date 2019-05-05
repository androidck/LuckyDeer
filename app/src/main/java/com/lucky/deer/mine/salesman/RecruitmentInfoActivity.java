package com.lucky.deer.mine.salesman;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.util.RegexUtil;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.model.common.dialog.WheeInfoBean;
import com.lucky.model.request.mine.RecruitmentInfoReq;
import com.lucky.model.response.perfectinformation.AccountOpeningAreaEntity;
import com.lucky.model.response.perfectinformation.BankBranchEntity;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 招聘信息
 *
 * @author wangxiangyi
 * @date 2018/12/19
 */
public class RecruitmentInfoActivity extends BaseActivity {
    /**
     * 标题
     */
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    /**
     * 招聘职位
     */
    @BindView(R.id.tv_recruitment_position)
    TextView tvRecruitmentPosition;
    /**
     * 招聘要求
     */
    @BindView(R.id.tv_recruitment_requirements)
    TextView tvRecruitmentRequirements;
    /**
     * 招聘人数
     */
    @BindView(R.id.tv_company_number_people)
    TextView tvCompanyNumberPeople;
    /**
     * 招聘电话
     */
    @BindView(R.id.et_contact_number)
    EditText etContactNumber;
    /**
     * 招聘地址
     */
    @BindView(R.id.tv_jobs_address)
    TextView tvJobsAddress;
    /**
     * 城市列表
     */
    private List<AccountOpeningAreaEntity> regionalData;
    /**
     * mRecruitmentPositionView：招聘职位列表视图
     * mList3：工作地点
     */
    private View mList3;

    private QMUIBottomSheet wheelDialog;
    /**
     * 招聘类型
     * 1：招聘职位
     * 2：招聘人数
     */
    private int mRecruitmentType = 0;
    /**
     * 招聘职位编号
     */
    private String mRecruitmentPositionType;
    /**
     * 编辑弹窗
     */
    private QMUIDialog mEditDialog;

    public RecruitmentInfoActivity() {
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_recruitment_info;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_recruitment_info);
        /*格式化手机号*/
        StringUtil.setPhoneWatcher(etContactNumber);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
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
    }

    @Override
    protected void initListener() {
        PublicDialog
                .getInstance()
                .setOnClickListener((OnClickListener<WheeInfoBean>) (status, useType, isPhoneNumber, text) -> {
                    switch (status) {
                        case OK:
                            if ("1".equals(text.getTypd())) {
                                if (mRecruitmentType == 1) {
                                    mRecruitmentPositionType = text.getCode();
                                    tvRecruitmentPosition.setText(text.getName());
                                } else if (mRecruitmentType == 2) {
                                    tvCompanyNumberPeople.setText(text.getName());
                                }
                            } else if ("3".equals(text.getTypd())) {
                                tvJobsAddress.setText(text.getName());
                            }
                            break;
                        default:
                    }
                    if (wheelDialog != null && wheelDialog.isShowing()) {
                        wheelDialog.dismiss();
                        wheelDialog = null;
                    }
                });

    }

    @OnClick({R.id.rl_recruitment_position, R.id.rl_recruitment_requirements,
            R.id.rl_company_number_people, R.id.rl_jobs_address, R.id.tv_release_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*招聘职位*/
            case R.id.rl_recruitment_position:
                if (getdata(R.array.recruitment_position_list) != null) {
                    List<BankBranchEntity> mRecruitmentPosition = new ArrayList<>();
                    for (int i = 0; i < getdata(R.array.recruitment_position_list).length; i++) {
                        BankBranchEntity entity = new BankBranchEntity();
                        entity.setName(getdata(R.array.recruitment_position_list)[i]);
                        entity.setId(String.valueOf(i + 1));
                        mRecruitmentPosition.add(entity);
                    }
                    View mRecruitmentPositionView = PublicDialog.getInstance().getWheelList(mContext, mRecruitmentPosition, 1);
                    wheelDialog = PublicDialog.getInstance().addHeaderView(mContext, mRecruitmentPositionView);
                }
                mRecruitmentType = 1;
                if (wheelDialog != null) {
                    wheelDialog.show();
                }
                break;
            /*招聘要求*/
            case R.id.rl_recruitment_requirements:
                Bundle bundle = new Bundle();
                bundle.putInt(CompanySynopsisActivity.TYPE, KeyConstant.BUSINESS_PROFILE);
                jumpActivityForResult2(mActivity, CompanySynopsisActivity.class, KeyConstant.RECRUITMENT_REQUIREMENTS, bundle);
                break;
            /*招聘人数*/
            case R.id.rl_company_number_people:
                /*招聘人数*/
                List<BankBranchEntity> mNumberPeople = new ArrayList<>();
                for (int i = 1; i < 21; i++) {
                    BankBranchEntity entity = new BankBranchEntity();
                    entity.setName(String.valueOf(i));
                    mNumberPeople.add(entity);
                }
                View mCompanyNumberPeopleView = PublicDialog.getInstance().getWheelList(mContext, mNumberPeople, 1);
                wheelDialog = PublicDialog.getInstance().addHeaderView(mContext, mCompanyNumberPeopleView);
                mRecruitmentType = 2;
                if (wheelDialog != null) {
                    wheelDialog.show();
                }
                break;
            /*招聘地址*/
            case R.id.rl_jobs_address:
                wheelDialog = PublicDialog.getInstance().addHeaderView(mContext, mList3);
                if (wheelDialog != null && regionalData != null) {
                    wheelDialog.show();
                }
                break;
            /*发布消息按钮*/
            case R.id.tv_release_info:
                examineRequiredVerification();
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == KeyConstant.RECRUITMENT_REQUIREMENTS) {
            tvRecruitmentRequirements.setText(data.getStringExtra(mEntity));
        }
    }

    @Override
    protected boolean examineRequiredVerification() {
        if (TextUtils.isEmpty(tvRecruitmentPosition.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_select_recruitment_position);
            return false;
        } else if (TextUtils.isEmpty(tvRecruitmentRequirements.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_write_recruitment_position);
            return false;
        } else if (TextUtils.isEmpty(tvCompanyNumberPeople.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_select_company_number_people);
            return false;
        } else if (!RegexUtil.isMobileExact(StringUtil.removeAllSpace(etContactNumber.getText().toString().trim()))) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_please_ok_enter_phone_number);
            return false;
        } else if (TextUtils.isEmpty(tvJobsAddress.getText().toString().trim())) {
            HintUtil.showErrorWithToast(mContext, R.string.toast_select_jobs_address);
            return false;
        }
        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        RecruitmentInfoReq recruitmentInfoReq = new RecruitmentInfoReq();
        /*招聘职位类型*/
        recruitmentInfoReq.setRecruitJob(mRecruitmentPositionType);
        /*招聘要求*/
        recruitmentInfoReq.setRecruitRequirement(tvRecruitmentRequirements.getText().toString().trim());
        /*招聘人*/
        recruitmentInfoReq.setRecruitNumber(tvCompanyNumberPeople.getText().toString().trim());
        /*联系电话*/
        recruitmentInfoReq.setRecruitPhone(StringUtil.removeAllSpace(etContactNumber.getText().toString().trim()));
        /*工作地点*/
        recruitmentInfoReq.setRecruitPosition(tvJobsAddress.getText().toString().trim());
        showLoadingDialog();
        mNetworkRequestInstance.releaseRecruit(recruitmentInfoReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        showSuccessDialog(R.string.dialog_submit_review_released);
                    } else {
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }

    @Override
    public void carriedOutMethod() {
        overridePendingTransition(false, true);
    }
}
