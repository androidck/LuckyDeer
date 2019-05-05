package com.lucky.deer.find.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.deer.util.DateUtil;
import com.lucky.model.common.date.CheckInCalendarEntity;

import java.util.List;

/**
 * 签到日历
 *
 * @author wangxiangyi
 * @date 2019/02/12
 */
public class CheckInCalendarAdapter extends BaseMultiItemQuickAdapter<CheckInCalendarEntity, BaseViewHolder> {


    public CheckInCalendarAdapter(List<CheckInCalendarEntity> data) {
        super(data);
        /*设置日历星期*/
        addItemType(0, R.layout.item_content_check_in_calendar);
        /*设置日历号*/
        addItemType(1, R.layout.item_content_check_in_calendar);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckInCalendarEntity item) {
        helper.setText(R.id.tv_week, item.getWeek())
                /*判断是否是显示周*/
                .setGone(R.id.tv_week, helper.getItemViewType() == 0)
                /*判断是否显示日期*/
                .setGone(R.id.cb_number, helper.getItemViewType() == 1)
                /*判断是否是占位符*/
                .setVisible(R.id.cb_number, item.isShowCheckInControl())
                .setText(R.id.cb_number, String.valueOf(DateUtil.dateConversionFormatInt(item.getIntervalDate(), DateUtil.y_m_d, DateUtil.dd)))
                .setChecked(R.id.cb_number, item.isCheckIn());
    }
}
