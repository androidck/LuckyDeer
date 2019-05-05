package com.lucky.deer.weight;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lucky.deer.R;
import com.lucky.deer.util.DateUtil;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.adapter.SelectRepaymentDateAdapter;
import com.lucky.deer.weight.interfaces.OnSelectRepaymentDateClickListener;
import com.lucky.model.common.dialog.SelectRepaymentDateEntity;
import com.lucky.model.util.HintUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * 选择还款日期对话框
 */
public class SelectRepaymentDateDialog extends Dialog implements View.OnClickListener {
    /**
     * 全局上下文
     */
    private final Activity mContext;

    /**
     * mStartRepayment：账单日
     * mEndRepayment：还款日
     */
    private String mStartRepayment, mEndRepayment;
    /**
     * 点击监听
     */
    private OnSelectRepaymentDateClickListener mListener;

    /**
     * itemEsc：取消
     * itemOk：确定
     */
    private TextView itemEsc, itemOk;
    /**
     * 日期列表
     */
    private RecyclerView rvDateList;
    /**
     * 重组数据
     */
    List<SelectRepaymentDateEntity> list;
    /**
     * 选中的数据
     */
    private List<SelectRepaymentDateEntity> mSelectedList;
    private SelectRepaymentDateAdapter mAdapter;

    public SelectRepaymentDateDialog(@NonNull Activity context) {
        super(context);
        this.mContext = context;
    }

    public SelectRepaymentDateDialog(@NonNull Activity context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    protected SelectRepaymentDateDialog(@NonNull Activity context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    /**
     * @param context      上下文
     * @param endRepayment 还款日（格式 0000年00月00日）
     * @param themeResId   主题
     * @param listener     点击监听
     */
    public SelectRepaymentDateDialog(Activity context, String endRepayment, int themeResId, OnSelectRepaymentDateClickListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.mEndRepayment = endRepayment;
        this.mListener = listener;
    }

    /**
     * @param context      上下文
     * @param endRepayment 还款日（格式 0000年00月00日）
     * @param selectedList 选中的日期
     * @param themeResId   主题
     * @param listener     点击监听
     */
    public SelectRepaymentDateDialog(Activity context, String endRepayment, List<SelectRepaymentDateEntity> selectedList, int themeResId, OnSelectRepaymentDateClickListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.mEndRepayment = endRepayment;
        this.mSelectedList = selectedList;
        this.mListener = listener;
    }

    /**
     * @param context        上下文
     * @param startRepayment 开始时间（格式 0000年00月00日）
     * @param endRepayment   结束（格式 0000年00月00日）
     * @param themeResId     主题
     * @param listener       点击监听
     */
    public SelectRepaymentDateDialog(Activity context, String startRepayment, String endRepayment, int themeResId, OnSelectRepaymentDateClickListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.mStartRepayment = startRepayment;
        this.mEndRepayment = endRepayment;
        this.mListener = listener;
    }

    /**
     * @param context        上下文
     * @param startRepayment 开始时间（格式 0000年00月00日）
     * @param endRepayment   结束（格式 0000年00月00日）
     * @param selectedList   选中的日期
     * @param themeResId     主题
     * @param listener       点击监听
     */
    public SelectRepaymentDateDialog(Activity context, String startRepayment, String endRepayment, List<SelectRepaymentDateEntity> selectedList, int themeResId, OnSelectRepaymentDateClickListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.mStartRepayment = startRepayment;
        this.mEndRepayment = endRepayment;
        this.mSelectedList = selectedList;
        this.mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repayment_date_dialog);
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

    /**
     * 视图初始化
     */
    private void initView() {
        itemEsc = findViewById(R.id.item_esc);
        itemOk = findViewById(R.id.item_ok);
        rvDateList = findViewById(R.id.rv_date_list);
        rvDateList.setLayoutManager(new GridLayoutManager(mContext, 7));
        list = new ArrayList<>();
        mAdapter = new SelectRepaymentDateAdapter(list);
        rvDateList.setAdapter(mAdapter);
        if (TextUtils.isEmpty(mStartRepayment)) {
            mStartRepayment = DateUtil.getCurrentDate(DateUtil.ymd);
        }
        initData();
        initListener();
    }

    public static void main(String arg[]) {
        System.out.println("开执行时间" + new SimpleDateFormat(DateUtil.ymdhms).format(new Date()));

        List<String> intervalDate = DateUtil.getPerDaysByStartAndEndDate("2019年2月28日", "2019年3月4日", DateUtil.ymd);
        /*时间排序*/
        StringUtil.dataSorting(intervalDate);
        String startDate = "2019年1月25日";
        List<SelectRepaymentDateEntity> listAll = new ArrayList<>();
        System.out.println("\n计算前时间：" + new SimpleDateFormat(DateUtil.ymdhms).format(new Date()));
        StringBuffer stringBuffer = new StringBuffer();
        for (String itemDate : intervalDate) {
            stringBuffer.append(itemDate);
            stringBuffer.append(",");
        }
        System.out.println("列表变成字符传==" + stringBuffer + "\n计算后时间：" + new SimpleDateFormat(DateUtil.ymdhms).format(new Date()));
        String[] split1 = stringBuffer.toString().split(DateUtil.getLastDayOfMonth(startDate, DateUtil.ymd));
        if (listAll.size() <= 0) {
            System.out.println("第一个月日期==" + split1[0] + DateUtil.getLastDayOfMonth(startDate, DateUtil.ymd) + "\n计算后时间：" + new SimpleDateFormat(DateUtil.ymdhms).format(new Date()));
            String[] split = (split1[0] + DateUtil.getLastDayOfMonth(startDate, DateUtil.ymd)).split(",");
            listAll.add(new SelectRepaymentDateEntity(true, split[0]));
            for (String date : split) {
                if (!"".equals(date)) {
                    listAll.add(new SelectRepaymentDateEntity(false, date, date));
                }
            }
            System.out.println("第一个月日期==" + new Gson().toJson(listAll) + "\n计算后时间：" + new SimpleDateFormat(DateUtil.ymdhms).format(new Date()));
        }
        if (split1.length >= 2) {
            System.out.println("第二个月日期==" + split1[1] + DateUtil.getLastDayOfMonth(listAll.get(listAll.size() - 1).getDate(), DateUtil.ymd) + "\n计算后时间：" + new SimpleDateFormat(DateUtil.ymdhms).format(new Date()));
            String[] split = (split1[1] + DateUtil.getLastDayOfMonth(listAll.get(listAll.size() - 1).getDate(), DateUtil.ymd)).split(",");
            for (String date : split) {
                if (!"".equals(date)) {
                    if (date.endsWith("01日")) {
                        listAll.add(new SelectRepaymentDateEntity(true, date));
                    }
                    listAll.add(new SelectRepaymentDateEntity(false, date, date));
                }
            }
            System.out.println("第二个月日期原数据==" + new Gson().toJson(listAll) + "\n计算后时间：" + new SimpleDateFormat(DateUtil.ymdhms).format(new Date()));
            listAll.remove(listAll.size() - 1);
            System.out.println("第二个月日期处理后数据==" + new Gson().toJson(listAll) + "\n计算后时间：" + new SimpleDateFormat(DateUtil.ymdhms).format(new Date()));
        }else {
            listAll.remove(listAll.size() - 1);
        }
        System.out.println("每个月==" + new Gson().toJson(listAll) + "\n计算后时间：" + new SimpleDateFormat(DateUtil.ymdhms).format(new Date()));

    }


    /**
     * 初始化数据
     */
    private void initData() {
        if (mSelectedList != null && mSelectedList.size() > 0) {
            list.addAll(mSelectedList);
        } else {
            List<String> intervalDate = DateUtil.getPerDaysByStartAndEndDate(mStartRepayment, mEndRepayment, DateUtil.ymd);
            if (intervalDate != null && intervalDate.size() > 0) {
                /*时间排序*/
                StringUtil.dataSorting(intervalDate);
                /*去除掉当天*/
                intervalDate.remove(0);
                StringBuffer stringBuffer = new StringBuffer();
                for (String itemDate : intervalDate) {
                    stringBuffer.append(itemDate);
                    stringBuffer.append(",");
                }
                list = new ArrayList<>();
                String[] split1 = stringBuffer.toString().split(DateUtil.getLastDayOfMonth(mStartRepayment, DateUtil.ymd));
                if (list.size() <= 0) {
                    String[] split = (split1[0] + DateUtil.getLastDayOfMonth(mStartRepayment, DateUtil.ymd)).split(",");
                    list.add(new SelectRepaymentDateEntity(true, split[0], true));
                    for (String date : split) {
                        if (!TextUtils.isEmpty(date)) {
                            list.add(new SelectRepaymentDateEntity(false, date, date));
                        }
                    }
                }
                if (split1.length >= 2) {
                    String[] split = (split1[1] + DateUtil.getLastDayOfMonth(list.get(list.size() - 1).getDate(), DateUtil.ymd)).split(",");
                    for (String date : split) {
                        if (!TextUtils.isEmpty(date)) {
                            if (date.endsWith("01日")) {
                                list.add(new SelectRepaymentDateEntity(true, date));
                            }
                            list.add(new SelectRepaymentDateEntity(false, date, date));
                        }
                    }
                    list.remove(list.size() - 1);
                }else {
                    list.remove(list.size() - 1);
                }
            }
        }
        mAdapter.setNewData(list);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        /*取消*/
        itemEsc.setOnClickListener(this);
        /*确定*/
        itemOk.setOnClickListener(this);
        /*点击全选监听*/
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.item_select_all_one) {
                if (mAdapter.getData().get(position).isAllSelect()) {
                    for (SelectRepaymentDateEntity item : mAdapter.getData()) {
                        item.setSelect(false);
                        item.setAllSelect(false);
                    }
                } else {
                    for (SelectRepaymentDateEntity item : mAdapter.getData()) {
                        item.setSelect(true);
                        item.setAllSelect(true);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        /*点击单个监听*/
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (!mAdapter.getData().get(position).isHeader) {
                mAdapter.getData().get(position).setSelect(!mAdapter.getData().get(position).isSelect());
                /*月份个数*/
                int mHeaderNumber = 0;
                /*日期个数*/
                int mChooseNumber = 0;
                for (SelectRepaymentDateEntity item : mAdapter.getData()) {
                    if (item.isHeader) {
                        mHeaderNumber += 1;
                    } else if (item.isSelect()) {
                        mChooseNumber += 1;
                    } else {
                        mChooseNumber -= 1;
                    }
                }
                mAdapter.notifyDataSetChanged();
                if (mChooseNumber != 0 && (mAdapter.getItemCount() - mHeaderNumber) == mChooseNumber) {
                    for (SelectRepaymentDateEntity item : mAdapter.getData()) {
                        item.setAllSelect(true);
                    }
                } else {
                    if (((mAdapter.getItemCount() - mHeaderNumber) - mChooseNumber) >= 1) {
                        for (SelectRepaymentDateEntity item : mAdapter.getData()) {
                            item.setAllSelect(false);
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /*取消*/
            case R.id.item_esc:
                dismiss();
                break;
            /*确定*/
            case R.id.item_ok:
                if (mListener != null) {
                    StringBuffer selectedDate = new StringBuffer();
                    StringBuffer selectedDay = new StringBuffer();
                    for (SelectRepaymentDateEntity item : mAdapter.getData()) {
                        if (!item.isHeader && item.isSelect()) {
                            if (TextUtils.isEmpty(selectedDate)) {
                                selectedDate.append(DateUtil.dateConversionFormat(item.getDate(), DateUtil.ymd, DateUtil.y_m_d));
                                selectedDay.append(DateUtil.dateConversionFormat(item.getDate(), DateUtil.ymd, DateUtil.dd));
                            } else {
                                selectedDate.append("," + DateUtil.dateConversionFormat(item.getDate(), DateUtil.ymd, DateUtil.y_m_d));
                                selectedDay.append("," + DateUtil.dateConversionFormat(item.getDate(), DateUtil.ymd, DateUtil.dd));
                            }
                        }
                    }
                    if (TextUtils.isEmpty(selectedDate) && TextUtils.isEmpty(selectedDay)) {
                        HintUtil.showErrorWithToast(mContext, "请选择还款日期");
                        return;
                    }
                    mListener.onSelectRepaymentDatemClick(mAdapter.getData(), selectedDate.toString(), selectedDay.toString(), StringUtil.getSplitNumber(selectedDate.toString(), ","));
                    dismiss();
                }
                break;
            default:
        }
    }
}
