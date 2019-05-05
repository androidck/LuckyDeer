package com.lucky.deer.home.announcement.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.model.response.home.CarouselMessageEntity;

/**
 * 公告适配器
 *
 * @author wangxiangyi
 * @date 2019/01/16
 */
public class AnnouncementAdapter extends BaseQuickAdapter<CarouselMessageEntity, BaseViewHolder> {
    public AnnouncementAdapter() {
        super(R.layout.item_announcement);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarouselMessageEntity item) {
        helper.setText(R.id.tv_release_time, item.getUpdateDate())
                .setText(R.id.tv_release_content, item.getMessage());
    }
}
