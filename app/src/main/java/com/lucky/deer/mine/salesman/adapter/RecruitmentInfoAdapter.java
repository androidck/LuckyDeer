package com.lucky.deer.mine.salesman.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.model.response.mine.RecruitmentInfoList;

/**
 * 招聘信息适配器
 *
 * @author wangxiangyi
 * @date 2018/12/19
 */
public class RecruitmentInfoAdapter extends BaseQuickAdapter<RecruitmentInfoList, BaseViewHolder> {


    public RecruitmentInfoAdapter() {
        super(R.layout.item_recruitment_info);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecruitmentInfoList item) {
        String[] stringArray = mContext.getResources().getStringArray(R.array.recruitment_position_list);
        String recruitmentTitle = "";
        if (stringArray.length > 0 &&
                !TextUtils.isEmpty(item.getRecruitJob()) &&
                Integer.parseInt(item.getRecruitJob()) > 0 &&
                (Integer.parseInt(item.getRecruitJob()) - 1) <= stringArray.length) {
            recruitmentTitle = stringArray[Integer.parseInt(item.getRecruitJob()) - 1];
        }
        String mTypeText = "";
        int mTypeColor;
        switch (item.getStatus()) {
            /*审核中*/
            case "1":
                mTypeText = "审核中";
                mTypeColor = R.color.color_golden;
                break;
            /*已发布*/
            case "2":
                mTypeText = "已发布";
                mTypeColor = R.color.colorGreen;
                break;
            /*未审核通过*/
            case "3":
                mTypeText = "未审核通过";
                mTypeColor = R.color.color_hint;
                break;
            default:
                mTypeColor = R.color.color_hint;
        }
        helper.setText(R.id.tv_recruitment_title, recruitmentTitle)
                .setText(R.id.tv_recruitment_status, mTypeText)
                .setTextColor(R.id.tv_recruitment_status, mContext.getResources().getColor(mTypeColor))
                .setText(R.id.tv_recruitment_address, item.getRecruitPosition())
                .setText(R.id.tv_release_time, item.getRecruitPublishTime())
                .setText(R.id.tv_recruitment_description, item.getRecruitRequirement())
               /* .setText(R.id.tv_education, item.getRecruitMan())
                .setText(R.id.tv_release_time, item.getRecruitMan())
                .setText(R.id.tv_company_benefits, item.getRecruitMan())*/;

    }
}
