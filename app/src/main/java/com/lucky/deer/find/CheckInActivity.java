package com.lucky.deer.find;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.find.adapter.CheckInCalendarAdapter;
import com.lucky.deer.util.DateUtil;
import com.lucky.deer.util.StringUtil;
import com.lucky.model.common.date.CheckInCalendarEntity;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 签到页面
 *
 * @author wangxiangyi
 * @date 2019/01/22
 */
public class CheckInActivity extends BaseActivity {
    /**
     * 标题
     */
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    /**
     * 签到积分
     */
    @BindView(R.id.tv_check_points)
    TextView tvCheckPoints;
    /**
     * 签到天数
     */
    @BindView(R.id.tv_check_days)
    TextView tvCheckDays;
    /**
     * 当前月份
     */
    @BindView(R.id.tv_current_month)
    TextView tvCurrentMonth;
    /**
     * 签到日历列表
     */
    @BindView(R.id.rv_calendar_list)
    RecyclerView rvCalendarList;

    public List<CheckInCalendarEntity> list = new ArrayList<>();
    /**
     * 日历适配器
     */
    private CheckInCalendarAdapter mAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_check_in;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_check_in);
        /*签到积分*/
        tvCheckPoints.setText("1000");
        /*签到天数*/
        tvCheckDays.setText("10");
        rvCalendarList.setLayoutManager(new GridLayoutManager(mContext, 7));
    }

    @Override
    protected void initData() {
        /*获取当前日期*/
        String currentDate = DateUtil.getCurrentDate(DateUtil.y_m_d);
        /*日期转换格式*/
        String currentYearMonth = DateUtil.dateConversionFormat(currentDate, DateUtil.y_m_d, DateUtil.y_m);
        /*设置当前年月*/
        tvCurrentMonth.setText(currentYearMonth);
        /*获取当前月份的第一天*/
        String firstDayOfMonth = DateUtil.getFirstDayOfMonth(currentDate, DateUtil.y_m_d);
        /*获取星期的索引*/
        int weekIndex = DateUtil.getWeekOfIndex(DateUtil.getCurrentDate(firstDayOfMonth, DateUtil.y_m_d));
        if (weekIndex >= 2) {
            /*计算每个月1号星期几*/
            firstDayOfMonth = DateUtil.dateCalculation(firstDayOfMonth, weekIndex * DateUtil.oneDayNumberMilliseconds, DateUtil.y_m_d, DateUtil.less);
        }
        /*获取当前日期区间*/
        List<String> intervalDate = DateUtil.getPerDaysByStartAndEndDate(
                /*获取当前月份的第一天*/
                firstDayOfMonth,
                /*获取当前月份的最后一天*/
                DateUtil.getLastDayOfMonth(currentDate, DateUtil.y_m_d),
                DateUtil.y_m_d);
        /*时间排序*/
        StringUtil.dataSorting(intervalDate);
        list = new ArrayList<>();
        list.add(new CheckInCalendarEntity(0, "日"));
        list.add(new CheckInCalendarEntity(0, "一"));
        list.add(new CheckInCalendarEntity(0, "二"));
        list.add(new CheckInCalendarEntity(0, "三"));
        list.add(new CheckInCalendarEntity(0, "四"));
        list.add(new CheckInCalendarEntity(0, "五"));
        list.add(new CheckInCalendarEntity(0, "六"));
        for (String date : intervalDate) {
            list.add(new CheckInCalendarEntity(1, currentYearMonth.equals(DateUtil.dateConversionFormat(date, DateUtil.y_m_d, DateUtil.y_m)), date));
        }
        mAdapter = new CheckInCalendarAdapter(list);
        rvCalendarList.setAdapter(mAdapter);
    }

    @OnClick(R.id.tv_continuous_check_in_heaven)
    public void onViewClicked() {

    }
}
