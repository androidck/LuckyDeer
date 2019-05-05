package com.lucky.deer.home.business.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.model.response.find.redEnvelope.ActivationCodeChildEntiviyt;
import com.lucky.model.util.GlideUtils;

import java.util.List;

/**
 * 我的升级码适配器
 *
 * @author wangxiangyi
 * @date 2019/03/28
 */
public class MyActivationCodeChildAdapter extends BaseMultiItemQuickAdapter<ActivationCodeChildEntiviyt, BaseViewHolder> {
    /**
     * 全部或未使用
     */
    public static final int ALL = 0;
    /**
     * 已使用
     */
    public static final int USED = 1;


    public MyActivationCodeChildAdapter(List<ActivationCodeChildEntiviyt> data) {
        super(data);
        addItemType(ALL, R.layout.item_child_my_activation_code_all);
        addItemType(USED, R.layout.item_child_my_activation_code_used);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActivationCodeChildEntiviyt item) {
        switch (item.getItemType()) {
            case ALL:
                helper.setText(R.id.tv_activation_code, item.getCode())
                        .setGone(R.id.btn_copy_upgrade_code, "1".equals(item.getCodeStatus()))
                        .setGone(R.id.iv_has_used, "2".equals(item.getCodeStatus()))
                        .addOnClickListener(R.id.btn_copy_upgrade_code);
                break;
            case USED:
                GlideUtils.loadImage(mContext, helper.getView(R.id.btn_avatar), item.getUseCodeUserHead());
                helper.setText(R.id.tv_time, item.getUseTime())
                        .setText(R.id.tv_user, item.getUseCodeUserNickName())
                        .setText(R.id.btn_phone_number, item.getUseCodeUserPhone())
                        .addOnClickListener(R.id.btn_phone_number);
                break;
            default:
        }
    }
}
