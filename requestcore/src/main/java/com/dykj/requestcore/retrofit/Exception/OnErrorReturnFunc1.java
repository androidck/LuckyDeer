package com.dykj.requestcore.retrofit.Exception;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.dykj.requestcore.retrofit.BusinessCode;
import com.google.gson.JsonParseException;
import com.lucky.model.response.ResponseData;
import com.lucky.model.util.HawkUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;

import java.net.ConnectException;
import java.text.ParseException;

import io.reactivex.functions.Function;
import retrofit2.HttpException;

/**
 * 异常数据处理
 *
 * @author wangxiangyi
 * @Create 2018/8/1.
 */
public class OnErrorReturnFunc1<T> implements Function<Throwable, ResponseData<T>> {


    private Context context;

    public OnErrorReturnFunc1(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public ResponseData<T> apply(Throwable throwable) {
        Logger.e(new Throwable("数据请求异常!") + throwable.getMessage());
        throwable.printStackTrace();
        ResponseData<T> responseData = new ResponseData<>();
        if (throwable instanceof HttpException) {
            /*HTTP错误*/
            HttpException httpException = (HttpException) throwable;
            if (httpException.code() == BusinessCode.UNAUTHORIZED.getCode()
                    || httpException.code() == BusinessCode.FORBIDDEN.getCode()
                    || httpException.code() == BusinessCode.NOT_FOUND.getCode()
                    || httpException.code() == BusinessCode.REQUEST_TIMEOUT.getCode()
                    || httpException.code() == BusinessCode.GATEWAY_TIMEOUT.getCode()
                    || httpException.code() == BusinessCode.INTERNAL_SERVER_ERROR.getCode()
                    || httpException.code() == BusinessCode.BAD_GATEWAY.getCode()
                    || httpException.code() == BusinessCode.SERVICE_UNAVAILABLE.getCode()) {
                /* 均视为网络错误*/
                responseData.setCode(BusinessCode.NETWORD_ERROR.getCode());
                responseData.setMsg(BusinessCode.NETWORD_ERROR.getMessage());
            } else {
                responseData.setCode(httpException.code());
                responseData.setMsg(BusinessCode.getServerStatusValue(httpException.code()));
            }
            return responseData;
        } else if (throwable instanceof JsonParseException
                || throwable instanceof JSONException
                || throwable instanceof ParseException) {
            /*均视为解析错误*/
            responseData.setCode(BusinessCode.PARSE_ERROR.getCode());
            responseData.setMsg(BusinessCode.PARSE_ERROR.getMessage());
            return responseData;
        } else if (throwable instanceof ConnectException) {
            /*均视为网络错误*/
            responseData.setCode(BusinessCode.NETWORD_ERROR.getCode());
            responseData.setMsg(BusinessCode.NETWORD_ERROR.getMessage());
            return responseData;

        }
        /*登录失效*/
        else if (throwable instanceof SessionExpireException) {
            //跳转到登录页面
            try {
                if (HawkUtil.getInstance().isContains(HawkUtil.USER_INFO)) {
                    //清除登录信息
                    HawkUtil.getInstance().remove(HawkUtil.USER_INFO);
                }
                context.startActivity(
                        new Intent(context, Class.forName("com.lucky.deer.login.LoginActivity"))
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .putExtra("entity", BusinessCode.PLEASE_LOG_FIRST.getCode()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            /*请求成功*/
            responseData.setCode(BusinessCode.PLEASE_LOG_FIRST.getCode());
            responseData.setMsg(BusinessCode.PLEASE_LOG_FIRST.getMessage());
            return responseData;
        }
        /*账号在异地登录*/
        else if (throwable instanceof LoginInconsistentException) {
            //跳转到登录页面
            try {
                if (HawkUtil.getInstance().isContains(HawkUtil.USER_INFO)) {
                    //清除登录信息
                    HawkUtil.getInstance().remove(HawkUtil.USER_INFO);
                }
                context.startActivity(
                        new Intent(context, Class.forName("com.lucky.deer.login.LoginActivity"))
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .putExtra("entity", BusinessCode.INCONSISTENT.getCode()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            /*访问设备和登录设备不一致*/
            responseData.setCode(BusinessCode.INCONSISTENT.getCode());
            responseData.setMsg(BusinessCode.INCONSISTENT.getMessage());
            return responseData;
        }
        /*数据为空*/
        else if (throwable instanceof NullDataException) {
            /*数据为空*/
            responseData.setCode(BusinessCode.NO_DATA.getCode());
            responseData.setMsg(BusinessCode.NO_DATA.getMessage());
            return responseData;
        } else {
            /*未知错误*/
            responseData.setCode(BusinessCode.PLEASE_CHECK_NETWORK.getCode());
            responseData.setMsg(BusinessCode.PLEASE_CHECK_NETWORK.getMessage());
            return responseData;
        }
    }
}
