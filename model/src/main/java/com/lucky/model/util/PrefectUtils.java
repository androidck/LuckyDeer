package com.lucky.model.util;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018/6/25 0025.
 */

public class PrefectUtils {


    public static String[] getPayDates(String sourData) throws java.text.ParseException {
        String[] result = new String[28];
        String pattern = "yyyy-MM-dd ";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = sdf.parse(sourData);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        for (int i = 0, j1 = 0; i < 16; i++) {
            String todayDay = sdf.format(calendar.getTime());
            if ((i - 3) % 4 == 0) {
                result[j1] = todayDay + " " + getRandomHHmmss("2");
                j1++;

            } else {
                result[j1] = todayDay + " " + getRandomHHmmss("0");
                j1++;
                result[j1] = todayDay + " " + getRandomHHmmss("1");
                j1++;
            }

            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }


        return result;
    }

    public static String[] getPayDatesByzidiying(ArrayList<String> myData) throws java.text.ParseException {
        String[] result = new String[28];
        for (int i = 0, j = 0; i < 16; i++) {
            if ((i - 3) % 4 == 0) {
                result[j] = myData.get(i) + " " + getRandomHHmmss("2");
                j++;
            } else {
                result[j] = myData.get(i) + " " + getRandomHHmmss("0");
                j++;
                result[j] = myData.get(i) + " " + getRandomHHmmss("1");
                j++;
            }
        }
        return result;
    }


    /**
     * 获取随机时间
     *
     * @param type
     * @return
     */
    public static String getRandomHHmmss(String type) {
        long amtime = 0;
        long noontime = 14400000;
        long pmtime = 43200000;
        long max = 0;
        long min = 0;


        switch (type) {
            /*上午*/
            case "0":
                min = amtime;
                max = noontime;

                break;
            /*下午*/
            case "1":
                min = noontime;
                max = pmtime;
                break;
            /*全天*/
            case "2":
                min = amtime;
                max = pmtime;
                break;
            default:
                min = amtime;
                max = pmtime;
                break;
        }

        long num = (long) ((Math.random() * (max - min)) + min);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(num);
    }


    public static int[] getPayArray(int min, int max, int count) {
        int[] result = new int[count + 1];
        int sumCount = 0;
        for (int i = 0; i < count; i++) {
            int num = (int) (Math.random() * (max - min)) + min;
            result[i + 1] = num;
            sumCount += num;
        }
        result[0] = sumCount;
        return result;

    }


    //得到算完的数据
    public static String[] getPayArrayRateMath(int[] myData, Double rateMath, Double allMoney) {
        DecimalFormat df = new DecimalFormat("#.00");
        String[] doubles = new String[myData.length];
        for (int i = 0; i < myData.length; i++) {
            doubles[i] = df.format(myData[i] / rateMath);
            if (i != 0) {
                allMoney += Double.parseDouble(doubles[i]);
            }
        }
        Log.e("__________12", allMoney + "");
        return doubles;
    }
//    public static double[] getPayArrayRateMath(int[] myData,Double rateMath){
//        double[] doubles = new double[myData.length];
//        for (int i = 0;i < myData.length; i ++){
//                doubles[i] = myData[i] / rateMath;
//        }
//        return doubles;
//    }

    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     *
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n   随机数个数
     */
    public static int[] getPayArray1(int min, int max, int n) {
        int sum = 0;
//	    if (n > (max - min + 1) || max < min) {
//	    	return null;
//
//	    }

        int[] result = new int[n + 1];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {

                result[count + 1] = num;
                count++;
                sum += num;
            }
        }

        result[0] = sum;
        return result;
    }
}
