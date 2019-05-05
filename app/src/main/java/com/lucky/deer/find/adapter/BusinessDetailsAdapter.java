package com.lucky.deer.find.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.enums.ExecutionStatusEnum;
import com.lucky.model.response.find.BusinessDetailsEntity;
import com.lucky.model.util.GlideUtils;

/**
 * 商户详情适配器
 *
 * @author admin
 * @date 2018/12/3
 */
public class BusinessDetailsAdapter extends BaseQuickAdapter<BusinessDetailsEntity, BaseViewHolder> {
    private String mType;

    public BusinessDetailsAdapter() {
        super(R.layout.item_business_details);
    }

    @Override
    protected void convert(BaseViewHolder helper, BusinessDetailsEntity item) {
        /*判断用户是否有头像*/
        if (TextUtils.isEmpty(item.getUserHead())) {
            /*没有头像时根据男女区分默认头像，若为获取到性别设置男为默认头像*/
            GlideUtils.loadImage(mContext, helper.getView(R.id.iv_avatar), ExecutionStatusEnum.getSearchStatusDefaultAvatar(ExecutionStatusEnum.defaultAvatar, item.getSex()));
        } else {
            /*加载网络头像*/
            GlideUtils.loadImage(mContext, helper.getView(R.id.iv_avatar), item.getUserHead());
        }
        /*是否隐藏推荐人控件*/
        helper.setGone(R.id.ll_referrer, "2".equals(mType))
                /*推荐人电话*/
                .setText(R.id.tv_referrer, String.format(mContext.getString(R.string.text_referrer), item.getRefereePhone()))
                .setText(R.id.tv_nickname, item.getNickName())
                .setText(R.id.tv_vip_grade, item.getLevelName())
                .setText(R.id.tv_phone, "1".equals(item.isShowPhone()) ? item.getPhone() : StringUtil.replacePhoneNumber(item.getPhone()))
                .setImageResource(R.id.iv_real_name_system, "1".equals(item.getIsRealNameAuth()) ? R.mipmap.real_name_btn : R.mipmap.no_real_btn);
    }

    /**
     * 设置商户类型
     *
     * @param type
     */
    public void setBusinessType(String type) {
        mType = type;
    }
}
