package com.dykj.requestcore.retrofit.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.dykj.requestcore.retrofit.interfaces.IExecutorAdapter;
import com.dykj.requestcore.util.SystemUtil;
import com.dykj.requestcore.util.header.EnumHttpHeaderParam;
import com.dykj.requestcore.util.header.EnumService;
import com.dykj.requestcore.util.header.TokenUtils;
import com.google.gson.Gson;
import com.lucky.model.request.BaseReq;
import com.lucky.model.util.HawkUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 数据处理适配器。
 *
 * @author wangxiangyi
 * @Create 2018/7/31.
 */
public class RetrofitDefaultExecutorAdapter implements IExecutorAdapter {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public HashMap<String, Object> dealWithParams(Context context, BaseReq param, HashMap<String, Object> param2, List<File> files) {
        HashMap<String, Object> handleParam = new HashMap<>();
        if (param2 != null && param2.size() > 0) {
            handleParam.put("mapParam", param2);
        } else if (param != null) {
            HashMap<String, Object> mapParam = objectMap(param);
            handleParam.put("mapParam", mapParam);
        }
        if (files != null && files.size() > 0) {
            //文件参数
            Map<String, RequestBody> fileParams = new HashMap<>();
            //封装文件参数
            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                fileParams.put("file" + (i + 1) + "\"; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("Accept: text/html, application/xhtml+xml, image/jxr, */*"), file));
            }
            handleParam.put("fileParams", fileParams);
        }
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        /*UserId*/
        handleParam.put(EnumHttpHeaderParam.getHeaderParam(0), param == null ? "" : param.getUserId());
        handleParam.put(EnumHttpHeaderParam.getHeaderParam(1), currentTimeMillis);
        handleParam.put(EnumHttpHeaderParam.getHeaderParam(2), EnumService.getEnumServiceByServiceName(1));
        handleParam.put(EnumHttpHeaderParam.getHeaderParam(3), TokenUtils.getSign((param2 == null ? objectMap(param) : param2), EnumService.getEnumServiceByServiceName(1), currentTimeMillis));
        /*推送token*/
        handleParam.put(EnumHttpHeaderParam.getHeaderParam(4), HawkUtil.getInstance().getSaveData(HawkUtil.REGISTRATION_ID) == null ? "" : HawkUtil.getInstance().getSaveData(HawkUtil.REGISTRATION_ID));
        /*设备类型：1：Android、2：ios*/
        handleParam.put(EnumHttpHeaderParam.getHeaderParam(5), "1");
        handleParam.put(EnumHttpHeaderParam.getHeaderParam(6), SystemUtil.getInstance().getGenerateIMEI(context));
        handleParam.put(EnumHttpHeaderParam.getHeaderParam(7), SystemUtil.getInstance().getModel());
        /*appid*/
        handleParam.put(EnumHttpHeaderParam.getHeaderParam(8), param == null ? "" : param.getAppId());
        /* 阿里云四要素认证appCode*/
        handleParam.put(EnumHttpHeaderParam.getHeaderParam(9), "APPCODE " + "6f9fa795d7424b0f9c8f0e4b5eb8b469");
        return handleParam;
    }

    /**
     * 实体对象转成Map
     *
     * @param obj 实体对象
     * @return
     */
    private HashMap<String, Object> objectMap(Object obj) {
        HashMap<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Map<String, String> map1 = (Map<String, String>) JSON.parse(new Gson().toJson(obj));
        for (String key : map1.keySet()) {
            if (!TextUtils.isEmpty(String.valueOf(map1.get(key)))) {
                map.put(key, map1.get(key));
            }
        }
//        Class clazz = obj.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//        try {
//            for (Field field : fields) {
//                if (field.get(obj) != null) {
//                    field.setAccessible(true);
//                    map.put(field.getName(), field.get(obj));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return map;
    }

    public static void main(String[] args) {
        String str = "{\"0\":\"zhangsan\",\"1\":\"lisi\",\"2\":\"wangwu\",\"3\":\"maliu\"}";
        Map<String, String> map1 = (Map<String, String>) JSON.parse(str);
        for (String key : map1.keySet()) {
            System.out.println(key + ":" + map1.get(key));
        }
    }
}
