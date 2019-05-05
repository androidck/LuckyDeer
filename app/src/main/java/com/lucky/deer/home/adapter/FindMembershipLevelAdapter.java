package com.lucky.deer.home.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.model.response.find.LevelUserGroupCountList;
import com.lucky.model.util.GlideUtils;

/**
 * 发现页会员等级适配器
 *
 * @author wangxiangyi
 * @date 2018/11/16
 */
public class FindMembershipLevelAdapter extends BaseQuickAdapter<LevelUserGroupCountList, BaseViewHolder> {
    public FindMembershipLevelAdapter() {
        super(R.layout.item_find_membership_level);
    }

    @Override
    protected void convert(BaseViewHolder helper, LevelUserGroupCountList item) {
        GlideUtils.loadImage(mContext, helper.getView(R.id.iv_grade_logo), item.getLevelIco());
        helper.setText(R.id.tv_grade_name, item.getLevelName())
                .setText(R.id.tv_grade_people, item.getLevelCount() + mContext.getString(R.string.people));

    }
}
