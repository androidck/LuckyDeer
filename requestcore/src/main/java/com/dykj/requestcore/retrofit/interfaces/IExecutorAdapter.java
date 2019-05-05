package com.dykj.requestcore.retrofit.interfaces;


import android.content.Context;


import com.lucky.model.request.BaseReq;

import java.io.File;
import java.util.HashMap;
import java.util.List;


/**
 * @author wangxiangyi
 * @名称 数据处理接口。
 * @Create 2018/7/31.
 */

public interface IExecutorAdapter {
    HashMap<String, Object> dealWithParams(Context context, BaseReq param, HashMap<String, Object> param2, List<File> files) ;
}
