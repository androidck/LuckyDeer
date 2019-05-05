package com.lucky.deer.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;

import com.lucky.model.common.date.MonthInfos;
import com.orhanobut.logger.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import cn.addapp.pickers.picker.DatePicker;

/**
 * 日期处理
 *
 * @author wangxiangyi
 * @date 2018/12/12
 */
public class DateUtil {
    /**
     * 日期格式（星期）
     */
    public static String eeee = "EEEE";
    /**
     * 日期格式（年）
     */
    public static String yyyy = "yyyy";
    /**
     * 日期格式（月）
     */
    public static String MM = "MM";
    /**
     * 日期格式（日）
     */
    public static String dd = "dd";
    /**
     * 日期格式（0000-00）
     */
    public static String y_m = "yyyy-MM";
    /**
     * 日期格式（0000年00月）
     */
    public static String ym = "yyyy年MM月";
    /**
     * 日期格式（00-00）
     */
    public static String m_d = "MM-dd";
    /**
     * 日期格式（00月00日）
     */
    public static String md = "MM月dd日";
    /**
     * 日期格式（0000-00-00）
     */
    public static String y_m_d = "yyyy-MM-dd";
    /**
     * 日期格式（0000年00月00日）
     */
    public static String ymd = "yyyy年MM月dd日";
    /**
     * 日期格式（00:00）
     */
    public static String h_m = "HH:mm";
    /**
     * 日期格式（0000-00-00 00:00）
     */
    public static String y_m_d_h_m = "yyyy-MM-dd HH:mm";
    /**
     * 日期格式（0000年00月00日 00:00）
     */
    public static String ymdhm = "yyyy年MM月dd日 HH:mm";
    /**
     * 日期格式（0000-00-00 00:00:00）
     */
    public static String y_m_d_h_m_s = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式（0000年00月00日 00:00:00）
     */
    public static String ymdhms = "yyyy年MM月dd日 HH:mm:ss";
    /**
     * 加法
     */
    public static String add = "+";
    /**
     * 减法
     */
    public static String less = "-";

    /**
     * 一秒钟的毫秒数
     */
    public static long oneSecondNumberMilliseconds = 1000;
    /**
     * 一分钟的毫秒数
     */
    public static long oneMinuteNumberMilliseconds = oneSecondNumberMilliseconds * 60;
    /**
     * 一小时的毫秒数
     */
    public static long oneHourNumberMilliseconds = oneMinuteNumberMilliseconds * 60;
    /**
     * 一天的毫秒数
     */
    public static long oneDayNumberMilliseconds = oneHourNumberMilliseconds * 24;

    /**
     * 判断两个日期的大小
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 比较结果<p>
     * 1：date1大于date2<p>
     * 0：date1等于date2<p>
     * -1：date1小于date2
     */
    @SuppressLint("SimpleDateFormat")
    public static int compareDate(String date1, String date2, String dateScheme) {
        SimpleDateFormat df = new SimpleDateFormat(dateScheme);
        try {
            /*当前日期*/
            Date dt1 = df.parse(date1);
            /*开始日期*/
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() == dt2.getTime()) {
                return 0;
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 判断当前日期和选择的日期相差几天
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param format    时间格式
     * @return 天数
     */
    @SuppressLint("SimpleDateFormat")
    public static long dateDiff(String startTime, String endTime, String format) {
        /*按照传入的格式生成一个simpledateformate对象*/
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long diff;
        long day;
        try {
            // 获得两个时间的毫秒时间差异
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            /*计算差多少天*/
            day = diff / oneDayNumberMilliseconds;
            /*计算差多少小时*/
            long hour = diff % oneDayNumberMilliseconds / oneHourNumberMilliseconds;
            /* 计算差多少分钟*/
            long min = diff % oneDayNumberMilliseconds % oneHourNumberMilliseconds / oneMinuteNumberMilliseconds;
            /*计算差多少秒*/
            long sec = diff % oneDayNumberMilliseconds % oneHourNumberMilliseconds % oneMinuteNumberMilliseconds / oneSecondNumberMilliseconds;
            /*输出结果*/
            Logger.w("时间相差：" + day + "天" + hour + "小时" + min
                    + "分钟" + sec + "秒。");
            if (day >= 1) {
                return day;
            } else {
                if (day == 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 按照时间格式获取当前时间
     *
     * @param dateFormat 时间格式
     * @return 时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDate(String dateFormat) {
        return getCurrentDate(null, dateFormat);
    }

    /**
     * 按照时间格式获取当前时间
     *
     * @param dateFormat 时间格式
     * @return 时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDate(String designationDate, String dateFormat) {
        Date date;
        if (TextUtils.isEmpty(designationDate)) {
            date = new Date();
        } else {
            date = new Date(Long.valueOf(DateUtil.dateToStamp(designationDate, dateFormat)));
        }
        if (TextUtils.isEmpty(dateFormat)) {
            return "";
        }
        return new SimpleDateFormat(dateFormat).format(date);
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getYear() {
        Date date = new Date();
        String year = new SimpleDateFormat("yyyy").format(date);
        return Integer.parseInt(year);
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getMonth() {
        Date date = new Date();
        String month = new SimpleDateFormat("MM").format(date);
        return Integer.parseInt(month);
    }

    /**
     * 返回当前天
     *
     * @return
     */
    public static int getDays() {
        Date date = new Date();
        String month = new SimpleDateFormat("dd").format(date);
        return Integer.parseInt(month);
    }

    /**
     * 获取单个日期
     *
     * @param date         时间
     * @param startFormat  原版格式
     * @param returnFormat 返回格式
     * @return 单个日期
     */
    public static int getsingleDate(String date, String startFormat, String returnFormat) {
        SimpleDateFormat df = new SimpleDateFormat(startFormat);
        Date newDate = null;
        try {
            newDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(new SimpleDateFormat(returnFormat).format(newDate))) {
            return 0;
        }
        return Integer.parseInt(new SimpleDateFormat(returnFormat).format(newDate));
    }

    //返回当月天数
    public static int getDays(int year, int month) {
        int days;
        int febday = 28;
        if (isLeap(year)) {
            febday = 29;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                days = febday;
                break;
            default:
                days = 0;
                break;
        }
        return days;
    }

    /**
     * 判断闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeap(int year) {
        return ((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0);
    }

    /**
     * 转换日期格式
     *
     * @param days
     * @return
     */
    public static String dayFormat(String days) {
        String str = null;
        int day = Integer.parseInt(days);
        if (day >= 1 && day < 10) {
            str = "0" + days;
        } else {
            str = String.valueOf(days);
        }
        return str;
    }

//    /**
//     * 获取间隔日期时间
//     *
//     * @param str
//     * @param str2
//     * @return
//     */
//    public static int getformat(String str, String str2) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date date1 = null;
//        Date date2 = null;
//        try {
//            date1 = format.parse(str);
//            date2 = format.parse(str2);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        int a = (int) ((date1.getTime() - date2.getTime()) / (1000 * 3600 * 24));
//        return a;
//    }

    /**
     * 将时间戳转换为时间
     *
     * @param timestamp 时间戳
     * @param format    要转换的格式
     */
    @SuppressLint("SimpleDateFormat")
    public static String stampToDate(long timestamp, String format) {
        Date date = new Date(timestamp);
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 将时间转换为时间戳
     *
     * @param date   时间
     * @param format 时间格式
     */
    @SuppressLint("SimpleDateFormat")
    public static String dateToStamp(String date, String format) {
        Date mDate = null;
        try {
            mDate = new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDate == null ? "" : String.valueOf(mDate.getTime());
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static Date setStrToDate(String str, String format) {
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 日期转换成字符串
     */
    public static String setDateToStr(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }


    /**
     * 转换时间格式
     *
     * @param date        要转换的日期
     * @param startFormat 开始日期格式
     * @param endFormat   转换的日期格式
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String dateConversionFormat(String date, String startFormat, String endFormat) {
        Date data1;
        SimpleDateFormat format1 = new SimpleDateFormat(startFormat);
        SimpleDateFormat format2 = new SimpleDateFormat(endFormat);
        if (TextUtils.isEmpty(date)) {
            return "";
        }
        try {
            data1 = format1.parse(date);
            return format2.format(data1);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 转换时间格式
     *
     * @param date        要转换的日期
     * @param startFormat 开始日期格式
     * @param endFormat   转换的日期格式
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static int dateConversionFormatInt(String date, String startFormat, String endFormat) {
        Date data1;
        SimpleDateFormat format1 = new SimpleDateFormat(startFormat);
        SimpleDateFormat format2 = new SimpleDateFormat(endFormat);
        if (TextUtils.isEmpty(date)) {
            return Integer.parseInt(getCurrentDate(endFormat));
        }
        try {
            data1 = format1.parse(date);
            return Integer.parseInt(format2.format(data1));
        } catch (ParseException e) {
            e.printStackTrace();
            return Integer.parseInt(getCurrentDate(endFormat));
        }
    }

    /**
     * 日期计算
     *
     * @param date               日期
     * @param dateFormat         日期格
     * @param calculatedQuantity 要计算的数量;
     * @param addOrless          计算方式（加法或减法）;
     * @return 结算结果
     */
    public static String dateCalculation(String date, long calculatedQuantity, String dateFormat, String addOrless) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        Date nowDate = null;
        try {
            nowDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time;
        if (add.equals(addOrless)) {
            time = nowDate.getTime() + calculatedQuantity;
        } else {
            time = nowDate.getTime() - calculatedQuantity;
        }
        Date newDate2 = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String dateOk = simpleDateFormat.format(newDate2);
        return dateOk;
    }

    /**
     * 获取某日期区间的所有日期  日期倒序
     *
     * @param startDate  开始日期
     * @param endDate    结束日期
     * @param dateFormat 日期格式
     * @return 区间内所有日期
     */
    public static List<String> getPerDaysByStartAndEndDate(String startDate, String endDate, String dateFormat) {
        DateFormat format = new SimpleDateFormat(dateFormat);
        try {
            Date sDate = format.parse(startDate);
            Date eDate = format.parse(endDate);
            long start = sDate.getTime();
            long end = eDate.getTime();
            if (start > end) {
                return null;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(eDate);
            List<String> res = new ArrayList<>();
            while (end >= start) {
                res.add(format.format(calendar.getTime()));
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                end = calendar.getTimeInMillis();
            }
            return res;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取开始日和结束日
     *
     * @param billingDate   账单号
     * @param repaymentDate 养卡号
     */
    public static Map<String, String> getBillingDateAndRepaymentDate(String billingDate, String repaymentDate) {
        Map<String, String> map = new HashMap<>();
        int bill, repay;
        if (TextUtils.isEmpty(billingDate) || TextUtils.isEmpty(repaymentDate)) {
            return map;
        } else {
            if (billingDate.endsWith("日")) {
                bill = Integer.parseInt(DateUtil.dateConversionFormat(billingDate, DateUtil.ymd, DateUtil.dd));
            } else {
                bill = Integer.parseInt(billingDate);
            }
            if (repaymentDate.endsWith("日")) {
                repay = Integer.parseInt(DateUtil.dateConversionFormat(repaymentDate, DateUtil.ymd, DateUtil.dd));
            } else {
                repay = Integer.parseInt(repaymentDate);
            }
        }
        Logger.w("开始日：" + bill + "\n结束日：" + repay);

        int newYear;
        int newMonth;
        //年
        int year = getYear();
        //月
        int month = getMonth();
        //日
        int day = getDays();

        //判断日当前日大于开始日
        if (bill > day) {
            if ((month - 1) == 0) {
                newYear = year - 1;
                newMonth = 12;
                System.out.println("开始日1：" + newYear + "-" + newMonth + "-" + bill);
                /*开始日*/
                map.put("billingDate", newYear + "年" + newMonth + "月" + bill + "日");
            } else {
                newYear = year;
                newMonth = month;
                System.out.println("开始日2：" + newYear + "-" + newMonth + "-" + bill);
                /*开始日*/
                map.put("billingDate", newYear + "年" + newMonth + "月" + bill + "日");
            }
            //否则
        } else {
            newYear = year;
            newMonth = month;
            System.out.println("开始日3：" + newYear + "-" + newMonth + "-" + bill);
            /*开始日*/
            map.put("billingDate", newYear + "年" + newMonth + "月" + bill + "日");
        }
        //结束日大于
        if (repay > bill) {
            System.out.println("结束日1：" + newYear + "-" + newMonth + "-" + repay);
            /*结束日*/
            map.put("repaymentDate", newYear + "年" + newMonth + "月" + repay + "日");
        } else {
            //新算的时间大于12，年+1
            if (newMonth + 1 > 12) {
                System.out.println("结束日2：" + (newYear + 1) + "-" + 1 + "-" + repay);
                /*结束日*/
                map.put("repaymentDate", (newYear + 1) + "年" + 1 + "月" + repay + "日");
            } else {
                System.out.println("结束日3：" + newYear + "-" + (newMonth + 1) + "-" + repay);
                /*结束日*/
                map.put("repaymentDate", newYear + "年" + (newMonth + 1) + "月" + repay + "日");
            }
        }
        return map;
    }

    public static void main(String[] args) {
//        System.out.println(new Gson().toJson(getPerDaysByStartAndEndDate("2019年02月01日","2019年03月29日",ymd)));

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入开始日");
        int bill = scanner.nextInt();
        System.out.println("请输入结束日");
        int repay = scanner.nextInt();

        int newYear;
        int newMonth;

        //获取当前日期
        String current = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        System.out.println("当前日期：" + current);

        String[] str = current.split("-");
        //当前日期：年
        int year = Integer.valueOf(str[0]);
        //当前日期：月
        int month = Integer.valueOf(str[1]);
        //当前日期：日
        int day = Integer.valueOf(str[2]);
        //判断日当前日大于开始日
        if (bill > day) {
            if ((month - 1) == 0) {
                newYear = year - 1;
                newMonth = 12;
                System.out.println("开始日1：" + newYear + "-" + newMonth + "-" + bill);
            } else {
                newYear = year;
                newMonth = month;
                System.out.println("开始日2：" + newYear + "-" + newMonth + "-" + bill);
            }
            //否则
        } else {
            newYear = year;
            newMonth = month;
            System.out.println("开始日3：" + newYear + "-" + newMonth + "-" + bill);
        }

        //结束日大于
        if (repay > bill) {
            System.out.println("结束日1：" + newYear + "-" + newMonth + "-" + repay);
        } else {
            //新算的时间大于12，年+1
            if (newMonth + 1 > 12) {
                System.out.println("结束日2：" + (newYear + 1) + "-" + 1 + "-" + repay);
            } else {
                System.out.println("结束日3：" + newYear + "-" + (newMonth + 1) + "-" + repay);
            }
        }


        /***************************************以前计算日期的方法*********************************************/
//        //判断日当前日大于开始日
//        if (bill > day) {
//            if ((month - 1) == 0) {
//                newYear = year - 1;
//                newMonth = 12;
//                System.out.println("开始日1：" + newYear + "-" + newMonth + "-" + bill);
//            } else {
//                newYear = year;
//                newMonth = month;
//                System.out.println("开始日2：" + newYear + "-" + newMonth + "-" + bill);
//            }
//            //否则
//        } else {
//            newYear = year;
//            newMonth = month;
//            System.out.println("开始日3：" + newYear + "-" + newMonth + "-" + bill);
//        }
//
//        //结束日大于
//        if (repay > bill) {
//            System.out.println("结束日1：" + newYear + "-" + newMonth + "-" + repay);
//        } else {
//            //新算的时间大于12，年+1
//            if (newMonth + 1 > 12) {
//                System.out.println("结束日2：" + (newYear + 1) + "-" + 1 + "-" + repay);
//            } else {
//                System.out.println("结束日3：" + newYear + "-" + (newMonth + 1) + "-" + repay);
//            }
//        }
    }


    public static List<MonthInfos> getMonths(String startTime, String endTime, String dateFormat) throws Exception {
        List<MonthInfos> list = new ArrayList<>();
        /*定义起始日期*/
        Date d1 = new SimpleDateFormat(dateFormat).parse(startTime);
        /*定义结束日期*/
        Date d2 = new SimpleDateFormat(dateFormat).parse(endTime);
        /*定义日期实例*/
        Calendar dd = Calendar.getInstance();
        /*设置日期起始时间*/
        dd.setTime(d1);
        /*如果为当前月份，直接写入*/
        if (!dd.getTime().before(d2)) {
            MonthInfos infos = new MonthInfos();
            infos.setStartTime(startTime);
            infos.setEndTime(endTime);
            SimpleDateFormat month = new SimpleDateFormat(dateFormat);
            infos.setMonth(month.format(dd.getTime()));
            list.add(infos);
        }
        /*判断是否到结束日期*/
        while (dd.getTime().before(d2)) {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            String str = sdf.format(dd.getTime());
            MonthInfos infos = new MonthInfos();
            infos.setMonth(str);
            //获取年和月份
            int year = dd.get(Calendar.YEAR);
            int month = dd.get(Calendar.MONTH) + 1;
            String firtDate = getFirstDayOfMonth(year, month, dateFormat);
            String lastDate = getLastDayOfMonth(year, month, dateFormat);
            infos.setStartTime(firtDate);
            infos.setEndTime(lastDate);
            list.add(infos);
            /*进行当前日期月份加1*/
            dd.add(Calendar.MONTH, 1);
            /*如果为最后一个月，写入信息*/
            if (!dd.getTime().before(d2)) {
                year = dd.get(Calendar.YEAR);
                month = dd.get(Calendar.MONTH) + 1;
                firtDate = getFirstDayOfMonth(year, month, dateFormat);
                MonthInfos info = new MonthInfos();
                info.setStartTime(firtDate);
                info.setEndTime(endTime);
                info.setMonth(sdf.format(dd.getTime()));
                list.add(info);
            }
        }
        if (list.size() > 1) {
            list.get(0).setStartTime(startTime);
        }
        return list;
    }

    /**
     * 获取第一天
     *
     * @param date       要获取第一天的日期
     * @param dateFormat 格式
     * @return
     */
    public static String getFirstDayOfMonth(String date, String dateFormat) {
        return getFirstDayOfMonth(DateUtil.dateConversionFormatInt(date, dateFormat, yyyy), DateUtil.dateConversionFormatInt(date, dateFormat, MM), dateFormat);
    }

    /**
     * 获取第一天
     *
     * @param year       年
     * @param month      月
     * @param dateFormat 格式
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month, String dateFormat) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return new SimpleDateFormat(dateFormat).format(cal.getTime());
    }

    /**
     * 获取最后一天
     *
     * @param date       要获取最后一天的日期
     * @param dateFormat 格式
     * @return
     */
    public static String getLastDayOfMonth(String date, String dateFormat) {
        return getLastDayOfMonth(DateUtil.dateConversionFormatInt(date, dateFormat, yyyy), DateUtil.dateConversionFormatInt(date, dateFormat, MM), dateFormat);
    }

    /**
     * 获取最后一天
     *
     * @param year       年
     * @param month      月
     * @param dateFormat 格式
     * @return
     */
    public static String getLastDayOfMonth(int year, int month, String dateFormat) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return new SimpleDateFormat(dateFormat).format(cal.getTime());
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param date   日期
     * @param format 日期格式
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(String date, String format) {
        return getWeekOfDate(new Date(Long.valueOf(DateUtil.dateToStamp(date, format))));
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param date
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date date) {
        return dateWeek.dateGetWeek(date);
    }

    /**
     * 获取星期的索引<br>
     *
     * @param week
     * @return 当前星期的索引
     */
    public static Integer getWeekOfIndex(String week) {
        return dateWeek.weekGetWeekIndex(week);
    }


    public enum dateWeek {
        /*星期日*/
        sunday("星期日", 0, "日"),
        /*星期一*/
        monday("星期一", 1, "一"),
        /*星期二*/
        tuesday("星期二", 2, "二"),
        /*星期三*/
        wednesday("星期三", 3, "三"),
        /*星期四*/
        thursday("星期四", 4, "四"),
        /*星期五*/
        friday("星期五", 5, "五"),
        /*星期六*/
        saturday("星期六", 6, "六");
        /**
         * 星期
         */
        public String week;
        /**
         * 星期
         */
        public String week2;
        /**
         * 索引
         */
        public int mIndex;
        /**
         * 星期一开头
         */
        public int mMonday;
        /**
         * 星期六开头
         */
        public int mSaturday;
        /**
         * 星期日开头
         */
        public int mSunday;

        dateWeek(String week, int index, String week2) {
            this.week = week;
            this.mIndex = index;
            this.week2 = week2;
        }

        dateWeek(String week, int sunday, int monday, int saturday) {
            this.week = week;
            this.mSunday = sunday;
            this.mMonday = monday;
            this.mSaturday = saturday;
        }

        public String getWeek() {
            return week;
        }

        public String getWeek2() {
            return week2;
        }

        public int getIndex() {
            return mIndex;
        }

        public int getMonday() {
            return mMonday;
        }

        public int getSaturday() {
            return mSaturday;
        }

        public int getSunday() {
            return mSunday;
        }

        /**
         * 根据时间获取星期几
         */
        public static String dateGetWeek(Date date) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            for (dateWeek value : values()) {
                if (w == value.getIndex()) {
                    return value.getWeek();
                }
            }
            return sunday.getWeek();
        }

        /**
         * 根据星期几获取星期几的索引
         */
        public static Integer weekGetWeekIndex(String date) {
            int index = sunday.getIndex();
            if (TextUtils.isEmpty(date)) {
                return index;
            }
            for (dateWeek value : values()) {
                if (value.getWeek().equals(date)) {
                    index = value.getIndex();
                }
            }
            return index;
        }
    }

    /**
     * 时间选择器
     *
     * @param rangeStart 开始选择器开始时间
     * @param selectDate 选择日期
     */
    public static void onOptionPicker(Activity activity, String rangeStart, String selectDate, DatePicker.OnYearMonthDayPickListener listener) {
        try {
            String times = getCurrentDate(DateUtil.ymd);
            DatePicker picker = new DatePicker(activity);
            picker.setCanLoop(true);
            picker.setWheelModeEnable(true);
            picker.setTopPadding(15);
            /*设置列表开始时间*/
            picker.setRangeStart(
                    /*年*/
                    DateUtil.getsingleDate(rangeStart, DateUtil.ymd, DateUtil.yyyy),
                    /*月*/
                    DateUtil.getsingleDate(rangeStart, DateUtil.ymd, DateUtil.MM),
                    /*日*/
                    DateUtil.getsingleDate(rangeStart, DateUtil.ymd, DateUtil.dd));
            /*设置列表结束时间*/
            picker.setRangeEnd(
                    /*年*/
                    DateUtil.getsingleDate(rangeStart, DateUtil.ymd, DateUtil.yyyy) + 5,
                    /*月*/
                    DateUtil.getsingleDate(rangeStart, DateUtil.ymd, DateUtil.MM),
                    /*日*/
                    DateUtil.getsingleDate(rangeStart, DateUtil.ymd, DateUtil.dd));
            if (TextUtils.isEmpty(selectDate)) {
                if (compareDate(times, rangeStart, DateUtil.ymd) == 1) {
                    /*设置当前时间*/
                    picker.setSelectedItem(
                            DateUtil.getsingleDate(times, DateUtil.ymd, DateUtil.yyyy),
                            DateUtil.getsingleDate(times, DateUtil.ymd, DateUtil.MM),
                            DateUtil.getsingleDate(times, DateUtil.ymd, DateUtil.dd));
                } else {
                    /*设置默认时间*/
                    picker.setSelectedItem(
                            DateUtil.getsingleDate(rangeStart, DateUtil.ymd, DateUtil.yyyy),
                            DateUtil.getsingleDate(rangeStart, DateUtil.ymd, DateUtil.MM),
                            DateUtil.getsingleDate(rangeStart, DateUtil.ymd, DateUtil.dd));
                }
            } else {
                /*设置选择选中的时间*/
                picker.setSelectedItem(
                        DateUtil.getsingleDate(selectDate, DateUtil.ymd, DateUtil.yyyy) <= 0 ? DateUtil.getsingleDate(times, DateUtil.ymd, DateUtil.yyyy) : DateUtil.getsingleDate(selectDate, DateUtil.ymd, DateUtil.yyyy),
                        DateUtil.getsingleDate(selectDate, DateUtil.ymd, DateUtil.MM) <= 0 ? DateUtil.getsingleDate(times, DateUtil.ymd, DateUtil.MM) : DateUtil.getsingleDate(selectDate, DateUtil.ymd, DateUtil.MM),
                        DateUtil.getsingleDate(selectDate, DateUtil.ymd, DateUtil.dd) <= 0 ? DateUtil.getsingleDate(times, DateUtil.ymd, DateUtil.dd) : DateUtil.getsingleDate(selectDate, DateUtil.ymd, DateUtil.dd));
            }
            picker.setWeightEnable(true);
            picker.setLineColor(Color.BLACK);
            if (listener != null) {
                picker.setOnDatePickListener(listener);
            }
            if (!picker.isShowing()) {
                picker.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 最后点击时间
     */
    private static long mLastClickTime = 0;
    /**
     * 防止按钮重复点击
     */
    public static boolean isRepeatedClicks() {
        return true;
//        /*获取当前时间戳*/
//        long nowTime = System.currentTimeMillis();
//        if (nowTime - mLastClickTime > (oneSecondNumberMilliseconds * 2)) {
//            // do something
//            mLastClickTime = nowTime;
//            return true;
//        } else {
//            return false;
//        }
    }


}
