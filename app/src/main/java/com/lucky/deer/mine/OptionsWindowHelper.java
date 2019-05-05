package com.lucky.deer.mine;

import android.app.Activity;
import android.text.TextUtils;

import com.lucky.model.response.perfectinformation.AccountOpeningAreaEntity;
import com.lucky.model.response.perfectinformation.AreaVoListBean;
import com.lucky.model.response.perfectinformation.BankBranchEntity;

import java.util.ArrayList;
import java.util.List;

import cn.jeesoft.widget.pickerview.CharacterPickerView;
import cn.jeesoft.widget.pickerview.CharacterPickerWindow;
import cn.jeesoft.widget.pickerview.OnOptionChangedListener;

public class OptionsWindowHelper {
    private static List<String> options1Items, options1ItemsCode = null;
    private static List<List<String>> options2Items, options2ItemsCode = null;
    private static List<List<List<String>>> options3Items, options3ItemsCode = null;

    /**
     * provinceName：省名称
     * provinceCode：省code
     * cityName：市名称
     * cityCode：市code
     * areaName：区/县名称
     * areaCode：区/县code
     * oneLevelLinkageCode：支行信息code
     * oneLevelLinkageName：支行信息名称
     */
    private static String provinceName,
            provinceCode,
            cityName, cityCode,
            areaName,
            areaCode,
            oneLevelLinkageCode,
            oneLevelLinkageName = "";


    public interface OnOptionsSelectListener {
        void onOptionsSelect(String province, String city, String area);
    }

    private OptionsWindowHelper() {
    }

    public static CharacterPickerWindow builder(Activity activity, final OnOptionsSelectListener listener) {
        //选项选择器
        CharacterPickerWindow mOptions = new CharacterPickerWindow(activity);
        //初始化选项数据
//        setPickerData(mOptions.getPickerView());
        //设置默认选中的三级项目
        mOptions.setCurrentPositions(0, 0, 0);
        //监听确定选择按钮
        mOptions.setOnoptionsSelectListener(new OnOptionChangedListener() {
            @Override
            public void onOptionChanged(int option1, int option2, int option3) {
                if (listener != null) {
                    String province = options1Items.get(option1);
                    String city = options2Items.get(option1).get(option2);
                    String area = options3Items.get(option1).get(option2).get(option3);
                    listener.onOptionsSelect(province, city, area);
                }
            }
        });
        return mOptions;
    }

    /**
     * 设置一级滚轮
     *
     * @param view
     * @param data
     */
    public static void setOneLevelLinkagePickerData(CharacterPickerView view, List<BankBranchEntity> data) {
        List<String> options1ItemsCode = new ArrayList<>();
        List<String> options1Items = new ArrayList<>();
        for (BankBranchEntity datum : data) {
            options1Items.add(datum.getName());
            options1ItemsCode.add(datum.getId());
        }
        view.setPicker(options1Items);
        //初始化选中项
        view.setSelectOptions(0);
        if (options1Items.size() > 0) {
            oneLevelLinkageCode = options1ItemsCode.get(0);
            oneLevelLinkageName = options1Items.get(0);
        }
        view.setOnOptionChangedListener((option1, option2, option3) -> {
            if (options1Items.size() > 0) {
                oneLevelLinkageCode = options1ItemsCode.get(option1);
                oneLevelLinkageName = options1Items.get(option1);
            }
        });
    }

    static CharacterPickerView mView;

    /**
     * 初始化选项数据
     * 设置三级联动
     */
    public static void setThreeLevelLinkagePickerData(CharacterPickerView view, List<AccountOpeningAreaEntity> data) {
        mView = view;
        /*三级联动效果*/
        options1ItemsCode = new ArrayList<>();
        options1Items = new ArrayList<>();
        options2ItemsCode = new ArrayList<>();
        options2Items = new ArrayList<>();
        options3ItemsCode = new ArrayList<>();
        options3Items = new ArrayList<>();

        /*遍历省份*/
        for (AccountOpeningAreaEntity accountOpeningAreaEntity : data) {
            /*添加省份编号*/
            options1ItemsCode.add(accountOpeningAreaEntity.getCode());
            /*添加省份名称*/
            options1Items.add(accountOpeningAreaEntity.getName());
            /*创建城市第一层列表*/
            List<String> options2itemsCode01 = new ArrayList<>();
            List<String> options2items01 = new ArrayList<>();
            /*创建县/区第一层列表*/
            List<List<String>> options3itemsCode01 = new ArrayList<>();
            List<List<String>> options3items01 = new ArrayList<>();
            /*判断城市是否是空*/
            if (accountOpeningAreaEntity.getAreaVoList() != null && accountOpeningAreaEntity.getAreaVoList().size() > 0) {
                /*遍历城市*/
                for (AreaVoListBean areaVoListBean : accountOpeningAreaEntity.getAreaVoList()) {
                    /*添加城市编号*/
                    options2itemsCode01.add(areaVoListBean.getCode());
                    /*添加城市名称*/
                    options2items01.add(areaVoListBean.getName());
                    /*创建区县第二层列表*/
                    List<String> options3itemsCoed02 = new ArrayList<>();
                    List<String> options3items02 = new ArrayList<>();
                    /*判断区县是否是空*/
                    if (areaVoListBean.getAreaVoList() != null && areaVoListBean.getAreaVoList().size() > 0) {
                        /*遍历曲线*/
                        for (AreaVoListBean voListBean : areaVoListBean.getAreaVoList()) {
                            options3itemsCoed02.add(voListBean.getCode());
                            options3items02.add(voListBean.getName());
                        }
                        options3itemsCode01.add(options3itemsCoed02);
                        options3items01.add(options3items02);
                    } else {
                        options3itemsCode01.add(options3itemsCoed02);
                        options3items01.add(options3items02);
                    }
                }
                /*添加城市列表*/
                options2ItemsCode.add(options2itemsCode01);
                options2Items.add(options2items01);
                /*添加区县列表*/
                options3ItemsCode.add(options3itemsCode01);
                options3Items.add(options3items01);
            } else {
                /*添加城市空列表*/
                options2ItemsCode.add(options2itemsCode01);
                options2Items.add(options2items01);
                /*添加区县空列表*/
                options3ItemsCode.add(options3itemsCode01);
                options3Items.add(options3items01);
            }
        }
        view.setPicker(options1Items, options2Items, options3Items);

        //初始化选中项
        view.setCurrentPositions(0, 0, 0);
    }

    /**
     * 获取省市县名称
     *
     * @return 省市县名称
     */
    public static String getNameAll() {
        if (mView != null && mView.getCurrentPositions() != null) {
            int[] currentPositions = mView.getCurrentPositions();
            if (currentPositions.length > 0) {
                if (options1Items != null && options1Items.size() > 0) {
                    provinceName = options1Items.get(currentPositions[0]);
                    provinceCode = options1ItemsCode.get(currentPositions[0]);
                } else {
                    provinceName = "";
                    provinceCode = "";
                }
                if (options2Items != null &&
                        options2Items.size() > 0 &&
                        options2Items.get(currentPositions[0]).size() > 0 &&
                        options2Items.get(currentPositions[0]).get(currentPositions[1]).length() > 0) {
                    cityName = options2Items.get(currentPositions[0]).get(currentPositions[1]);
                    cityCode = options2ItemsCode.get(currentPositions[0]).get(currentPositions[1]);
                } else {
                    cityName = "";
                    cityCode = "";
                }
                if (options3Items != null &&
                        options3Items.size() > 0 &&
                        options3Items.get(currentPositions[0]).size() > 0 &&
                        options3Items.get(currentPositions[0]).get(currentPositions[1]).size() > 0 &&
                        options3Items.get(currentPositions[0]).get(currentPositions[1]).get(currentPositions[2]).length() > 0) {
                    areaName = options3Items.get(currentPositions[0]).get(currentPositions[1]).get(currentPositions[2]);
                    areaCode = options3ItemsCode.get(currentPositions[0]).get(currentPositions[1]).get(currentPositions[2]);
                } else {
                    areaName = "";
                    areaCode = "";
                }
            }
        }
        return provinceName + cityName + areaName;
    }

    /**
     * 获取支行名称
     *
     * @return 支行名称
     */
    public static String getSelectBankpickerName() {
        return oneLevelLinkageName;
    }

    /**
     * 获取省市县id
     *
     * @return 省市县id
     */
    public static String getCode() {
        if (!TextUtils.isEmpty(areaCode)) {
            return areaCode;
        } else if (!TextUtils.isEmpty(cityCode)) {
            return cityCode;
        } else {
            return provinceCode;
        }
    }

    /**
     * 获取支行id
     *
     * @return 支行id
     */
    public static String getSelectBankpickerCode() {
        return oneLevelLinkageCode;
    }


    public static void setCurrentPositions(CharacterPickerWindow window, String province, String city, String area) {
        int option1 = indexOf(options1Items, province);
        int option2 = option1 >= 0 ? indexOf(options2Items.get(option1), city) : -1;
        int option3 = option2 >= 0 ? indexOf(options3Items.get(option1).get(option2), area) : -1;

        window.setCurrentPositions(option1, option2, option3);
    }

    private static int indexOf(List<String> data, String key) {
        if (data == null || data.isEmpty()) {
            return -1;
        }
        if (TextUtils.isEmpty(key)) {
            return -1;
        }
        return data.indexOf(key);
    }
}
