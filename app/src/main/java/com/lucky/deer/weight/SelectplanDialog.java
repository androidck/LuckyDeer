package com.lucky.deer.weight;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lucky.deer.R;
import com.lucky.deer.util.DateUtil;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.adapter.StampAdapter;
import com.lucky.model.common.dialog.Day;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * 选择计划弹窗
 * Created by Administrator on 2018/4/14.
 */

public class SelectplanDialog extends Dialog implements View.OnClickListener {
    private Activity mContext;

    private OnItemClickListener mListener;

    private OnCommitClickListener commitClickListener;

    List<Day> list1;

    List<Day> list2;


    SwipeRecyclerView recyclerView;

    SwipeRecyclerView recyclerView1;

    StampAdapter adapter1;

    StampAdapter adapter2;

    TextView item_select_all_one;//全选 一

    TextView item_select_all_two;//全选 二


    private String bill;//账单日

    private String repayment;//还款日
    /**
     * 已选择
     */
    private String mChosen;

    LinearLayout month_one;//第一个月

    LinearLayout month_two;//第二个月

    private boolean mSelect;

    TextView item_month_one;//一月

    TextView item_month_two;//二月
    /**
     * 加载完成监听
     */
    private OnDataLoadingCompletedListener mLoadingCompletedListener;


    public SelectplanDialog(Activity context) {
        super(context);
        this.mContext = context;
    }


    public SelectplanDialog(Activity context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public SelectplanDialog(Activity context, String bill, String repayment, int themeResId, OnCommitClickListener mListener) {
        super(context, themeResId);
        this.mContext = context;
        this.commitClickListener = mListener;
        this.bill = bill;
        this.repayment = repayment;
    }

    public SelectplanDialog(Activity context, String bill, String repayment, String chosen, int themeResId, OnCommitClickListener mListener) {
        super(context, themeResId);
        this.mContext = context;
        this.commitClickListener = mListener;

        this.bill = bill;
        this.repayment = repayment;
        this.mChosen = chosen;
    }

    protected SelectplanDialog(Activity context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repayment_dialog);
        setCanceledOnTouchOutside(false);
        initView();
        /*设置窗口弹出动画*/
        getWindow().setWindowAnimations(R.style.ActionSheetDialogAnimation);
        //全屏处理
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        WindowManager wm = mContext.getWindowManager();
        /*设置宽度*/
        lp.width = wm.getDefaultDisplay().getWidth();

        getWindow().setAttributes(lp);
    }

    private void initView() {
        findViewById(R.id.item_esc).setOnClickListener(this);
        findViewById(R.id.item_ok).setOnClickListener(this);
        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView1 = findViewById(R.id.recyclerView2);
        item_select_all_one = findViewById(R.id.item_select_all_one);
        item_select_all_two = findViewById(R.id.item_select_all_two);
        item_month_one = findViewById(R.id.item_month_one);
        item_month_two = findViewById(R.id.item_month_two);
        month_one = findViewById(R.id.month_one);
        month_two = findViewById(R.id.month_two);
        item_month_one.setText(DateUtil.getMonth() + "月");
        if (DateUtil.getMonth() == 12) {
            item_month_two.setText("01月");
        } else {
            item_month_two.setText((DateUtil.getMonth() + 1) + "月");
        }
        initData();
    }

    //初始化数据
    private void initData() {
        /* 账单日( 当前时间)*/
        int inBill = Integer.parseInt(bill);
        /*还款日*/
        int inRepayment = Integer.parseInt(repayment);
        String yearStr = new SimpleDateFormat("yyyy").format(new Date());
        String monthStr = new SimpleDateFormat("MM").format(new Date());
        int days = DateUtil.getDays(Integer.parseInt(yearStr), Integer.parseInt(monthStr));
        Log.d("xxxx", days + "----" + bill);
        /*判断账单日大于还款日加载第二月数据*/
        if (inBill > inRepayment) {
            if (inBill == days) {
                month_one.setVisibility(View.GONE);
            } else {
                month_one.setVisibility(View.VISIBLE);
            }
            month_two.setVisibility(View.VISIBLE);
            list2 = new ArrayList<>();
            for (int i = 1; i <= inRepayment; i++) {
                Day day = new Day(String.valueOf(i), String.valueOf(DateUtil.getYear()), String.valueOf((DateUtil.getMonth() + 1)));
                if (TextUtils.isEmpty(mChosen)) {
                    day.setSelect(false);
                } else {
                    /*拆分已选日期*/
                    String[] selectedDate = StringUtil.getSplit(mChosen, ",");
                    if (selectedDate.length > 0) {
                        /*遍历已选择日期*/
                        for (String itemSelectedDate : selectedDate) {
                            /*判断要设置的月和选择的月是否是同一个月*/
                            if (!TextUtils.isEmpty(DateUtil.dateConversionFormat(itemSelectedDate, DateUtil.y_m_d, DateUtil.MM)) &&
                                    (DateUtil.getMonth() + 1) == Integer.parseInt(DateUtil.dateConversionFormat(itemSelectedDate, DateUtil.y_m_d, DateUtil.MM)) &&
                                    !TextUtils.isEmpty(DateUtil.dateConversionFormat(itemSelectedDate, DateUtil.y_m_d, DateUtil.dd)) &&
                                    /*判断选择的日是否和当前的日相同*/
                                    i == Integer.parseInt(DateUtil.dateConversionFormat(itemSelectedDate, DateUtil.y_m_d, DateUtil.dd))) {
                                /*设置选中的日期*/
                                day.setSelect(true);
                            } else {
                                /*设置未选中的日期*/
//                                day.setSelect(false);
                            }
                        }
                    }
                }
                list2.add(day);
            }
            list1 = new ArrayList<>();
            Locale.setDefault(Locale.ENGLISH);
            GregorianCalendar d = new GregorianCalendar();
            /*用于循环打印当前月份的月历__月份判断*/
            int month = d.get(Calendar.MONTH);
            /*用于给当前日期后面加"*"*/
            int today = d.get(Calendar.DAY_OF_MONTH);
            /*保存7个星期名*/
            String weekdays[] = new DateFormatSymbols().getShortWeekdays();
            /*设置d的日期为当月1号*/
            d.set(Calendar.DAY_OF_MONTH, today + 1);
            do {
                int day = d.get(Calendar.DAY_OF_MONTH);
                System.out.printf("%3s", "");
                /*判断是否需要换行打印*/
                if (weekdays[d.get(Calendar.DAY_OF_WEEK)] == weekdays[7]) {
                    System.out.println();
                }
                d.add(Calendar.DAY_OF_MONTH, 1);
//                list1.add(new Day(String.valueOf(day), String.valueOf(DateUtil.getYear()), String.valueOf(DateUtil.getMonth()), false));
                Day day1 = new Day(String.valueOf(day), String.valueOf(DateUtil.getYear()), String.valueOf((DateUtil.getMonth())));
                if (TextUtils.isEmpty(mChosen)) {
                    day1.setSelect(false);
                } else {
                    /*拆分已选日期*/
                    String[] selectedDate = StringUtil.getSplit(mChosen, ",");
                    if (selectedDate.length > 0) {
                        /*遍历已选择日期*/
                        for (String itemSelectedDate : selectedDate) {
                            /*判断要设置的月和选择的月是否是同一个月*/
                            if (!TextUtils.isEmpty(DateUtil.dateConversionFormat(itemSelectedDate, DateUtil.y_m_d, DateUtil.MM)) &&
                                    DateUtil.getMonth() == Integer.parseInt(DateUtil.dateConversionFormat(itemSelectedDate, DateUtil.y_m_d, DateUtil.MM)) &&
                                    !TextUtils.isEmpty(DateUtil.dateConversionFormat(itemSelectedDate, DateUtil.y_m_d, DateUtil.dd)) &&
                                    /*判断选择的日是否和当前的日相同*/
                                    day == Integer.parseInt(DateUtil.dateConversionFormat(itemSelectedDate, DateUtil.y_m_d, DateUtil.dd))) {
                                /*设置选中的日期*/
                                day1.setSelect(true);
                            } else {
                                /*设置未选中的日期*/
//                                day.setSelect(false);
                            }
                        }
                    }
                }
                list1.add(day1);
            } while (d.get(Calendar.MONTH) == month);
        } else {
            month_two.setVisibility(View.GONE);
            list1 = new ArrayList<>();
            for (int i = inBill; i < inRepayment; i++) {
//                list1.add(new Day(String.valueOf(i + 1), String.valueOf(DateUtil.getYear()), String.valueOf((DateUtil.getMonth())), false));
                Day day = new Day(String.valueOf(i + 1), String.valueOf(DateUtil.getYear()), String.valueOf((DateUtil.getMonth())));
                if (TextUtils.isEmpty(mChosen)) {
                    day.setSelect(false);
                } else {
                    /*拆分已选日期*/
                    String[] selectedDate = StringUtil.getSplit(mChosen, ",");
                    if (selectedDate.length > 0) {
                        /*遍历已选择日期*/
                        for (String itemSelectedDate : selectedDate) {
                            /*判断要设置的月和选择的月是否是同一个月*/
                            if (!TextUtils.isEmpty(DateUtil.dateConversionFormat(itemSelectedDate, DateUtil.y_m_d, DateUtil.MM)) &&
                                    DateUtil.getMonth() == Integer.parseInt(DateUtil.dateConversionFormat(itemSelectedDate, DateUtil.y_m_d, DateUtil.MM)) &&
                                    !TextUtils.isEmpty(DateUtil.dateConversionFormat(itemSelectedDate, DateUtil.y_m_d, DateUtil.dd)) &&
                                    /*判断选择的日是否和当前的日相同*/
                                    i == Integer.parseInt(DateUtil.dateConversionFormat(itemSelectedDate, DateUtil.y_m_d, DateUtil.dd))) {
                                /*设置选中的日期*/
                                day.setSelect(true);
                            } else {
                                /*设置未选中的日期*/
//                                day.setSelect(false);
                            }
                        }
                    }
                }
                list1.add(day);
            }
        }
        initList();
    }

    private void initList() {
        adapter1 = new StampAdapter(mContext, list1);
        recyclerView.setAdapter(adapter1);

        adapter2 = new StampAdapter(mContext, list2);
        recyclerView1.setAdapter(adapter2);

        if (mLoadingCompletedListener != null) {
            mLoadingCompletedListener.onDataLoading(1);
        }
        GridLayoutManager layoutManager1 = new GridLayoutManager(mContext, 7) {
            @Override
            public boolean canScrollVertically() {
                /*直接禁止垂直滑动*/
                return false;
            }
        };
        GridLayoutManager layoutManager2 = new GridLayoutManager(mContext, 7) {
            @Override
            public boolean canScrollVertically() {
                /* 直接禁止垂直滑动*/
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager1);
        recyclerView1.setLayoutManager(layoutManager2);
        /*全选/反选*/
        item_select_all_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelect = !mSelect;
                if (mSelect) {
                    for (int i = 0; i < list1.size(); i++) {
                        list1.get(i).setSelect(true);
                    }
                } else {
                    for (int i = 0; i < list1.size(); i++) {
                        list1.get(i).setSelect(false);
                    }
                }
                adapter1.notifyDataSetChanged();
            }
        });

        //全选/反选
        item_select_all_two.setOnClickListener(v -> {
            mSelect = !mSelect;
            if (mSelect) {
                for (int i = 0; i < list2.size(); i++) {
                    list2.get(i).setSelect(true);
                }
            } else {
                for (int i = 0; i < list2.size(); i++) {
                    list2.get(i).setSelect(false);
                }
            }
            adapter2.notifyDataSetChanged();
        });
        adapter1.setRefreshListener(isSelect -> {

        });
        adapter2.setRefreshListener(isSelect -> {

        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_esc:
                dismiss();
                break;
            case R.id.item_ok:
                //拼接字符串
                List<Day> dayList = new ArrayList<>();
                StringBuilder builder = new StringBuilder();
                /*账单日*/
                int inBill = Integer.parseInt(bill);
                /*还款日*/
                int inRepayment = Integer.parseInt(repayment);
                /*判断账单日是否大于还款日*/
                if (inBill > inRepayment) {
                    for (int i = 0; i < list1.size(); i++) {
                        if (list1.get(i).getIsSelect()) {
                            dayList.add(new Day(list1.get(i).getDays(), list1.get(i).getYear(), list1.get(i).getMonth(), true));
                        }
                    }
                    for (int i = 0; i < list2.size(); i++) {
                        if (list2.get(i).getIsSelect()) {
                            dayList.add(new Day(list2.get(i).getDays(), list2.get(i).getYear(), list2.get(i).getMonth(), true));
                        }
                    }
                    for (int i = 0; i < dayList.size(); i++) {
                        if (i == 0) {
                            builder.append(((!TextUtils.isEmpty(dayList.get(i).getMonth()) && Integer.parseInt(dayList.get(i).getMonth()) > 12) ? DateUtil.getYear() + 1 : DateUtil.getYear()) + "-" + DateUtil.dayFormat((!TextUtils.isEmpty(dayList.get(i).getMonth()) && Integer.parseInt(dayList.get(i).getMonth()) > 12) ? "1" : dayList.get(i).getMonth()) + "-" + DateUtil.dayFormat(dayList.get(i).getDays()));
                        } else {
                            builder.append("," + ((!TextUtils.isEmpty(dayList.get(i).getMonth()) && Integer.parseInt(dayList.get(i).getMonth()) > 12) ? DateUtil.getYear() + 1 : DateUtil.getYear()) + "-" + DateUtil.dayFormat((!TextUtils.isEmpty(dayList.get(i).getMonth()) && Integer.parseInt(dayList.get(i).getMonth()) > 12) ? "1" : dayList.get(i).getMonth()) + "-" + DateUtil.dayFormat(dayList.get(i).getDays()));
                        }
                    }
                    commitClickListener.onClick(builder.toString());
                    /*保存时间*/
//                    TimeManager.getInstance().saveTimeInfo(mContext, builder.toString());
                } else {
                    for (int i = 0; i < list1.size(); i++) {
                        if (list1.get(i).getIsSelect()) {
                            dayList.add(new Day(list1.get(i).getDays(), list1.get(i).getYear(), list1.get(i).getMonth(), true));
                        }
                    }
                    for (int i = 0; i < dayList.size(); i++) {
                        if (i == 0) {
                            builder.append(((!TextUtils.isEmpty(dayList.get(i).getMonth()) && Integer.parseInt(dayList.get(i).getMonth()) > 12) ? DateUtil.getYear() + 1 : DateUtil.getYear()) + "-" + DateUtil.dayFormat((!TextUtils.isEmpty(dayList.get(i).getMonth()) && Integer.parseInt(dayList.get(i).getMonth()) > 12) ? "1" : dayList.get(i).getMonth()) + "-" + DateUtil.dayFormat(dayList.get(i).getDays()));
                        } else {
                            builder.append("," + ((!TextUtils.isEmpty(dayList.get(i).getMonth()) && Integer.parseInt(dayList.get(i).getMonth()) > 12) ? DateUtil.getYear() + 1 : DateUtil.getYear()) + "-" + DateUtil.dayFormat((!TextUtils.isEmpty(dayList.get(i).getMonth()) && Integer.parseInt(dayList.get(i).getMonth()) > 12) ? "1" : dayList.get(i).getMonth()) + "-" + DateUtil.dayFormat(dayList.get(i).getDays()));
                        }
                    }
                    /*保存时间*/
//                    TimeManager.getInstance().saveTimeInfo(mContext, builder.toString());
                    commitClickListener.onClick(builder.toString());
                    Log.d("builder", builder.toString());
                }
                dismiss();
                break;
            default:
        }
    }

    public interface OnItemClickListener {
        void onClick(Dialog dialog, int position);
    }

    public interface OnCommitClickListener {
        void onClick(String position);
    }

    public interface OnDataLoadingCompletedListener {
        void onDataLoading(int type);
    }

    public void OnCommitClickListener(OnCommitClickListener commitClickListener) {
        this.commitClickListener = commitClickListener;
    }

    public void setOnDataLoadingCompletedListener(OnDataLoadingCompletedListener listener) {
        this.mLoadingCompletedListener = listener;
    }


}
