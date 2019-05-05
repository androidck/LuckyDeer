package com.dykj.requestcore.util;


import com.dykj.requestcore.retrofit.BusinessCode;
import com.lucky.model.response.ResponseData;

import java.util.Collection;


/**
 * 返回标志判断
 *
 * @author wangxiangyi
 * @date 2018/09/20
 */
public class RequestUtils {
    /**
     * 阿里云四要素认证
     *
     * @param responseData
     * @return
     */
    public static boolean isFourElementsSuccess(ResponseData<?> responseData) {
        return "0000".equals(responseData.getRespCode()) ;
    }

    /**
     * 请求是否成功
     *
     * @param responseData
     * @return
     */
    public static boolean isRequestSuccess(ResponseData<?> responseData) {
        return responseData.getCode() == BusinessCode.SUCCESS.getCode();
    }

    /**
     * session失效，或者没有登录。
     *
     * @param responseData
     * @return
     */
    public static boolean sessionExpire(ResponseData<?> responseData) {
        return responseData.getCode() == BusinessCode.PLEASE_LOG_FIRST.getCode();
    }

    /**
     * 账号在另一个设备登录
     *
     * @param responseData
     * @return
     */
    public static boolean loginInconsistent(ResponseData<?> responseData) {
        return responseData.getCode() == BusinessCode.INCONSISTENT.getCode();
    }

    /**
     * 参数错误
     *
     * @param responseData
     * @return
     */
    public static boolean parameterError(ResponseData<?> responseData) {
        return responseData.getCode() == BusinessCode.PARAMETER_ERROR.getCode();
    }
//
//    /**
//     * 账号禁止
//     *
//     * @param responseData
//     * @return
//     */
//    public static boolean accountBan(ResponseData<?> responseData) {
//        return TextUtils.equals(responseData.getCode(), "U10007") || TextUtils.equals(responseData.getCode(), "401");
//    }

    /**
     * 无数据
     *
     * @param responseData
     * @return
     */
    public static boolean isEmpty(ResponseData<?> responseData) {
        if (responseData.getCode() == BusinessCode.NO_DATA.getCode()) {
            return true;
        } else {
            if (isRequestSuccess(responseData)) {
                return null == responseData.getData();
            } else {
                return false;
            }
        }
    }

    /**
     * 集合的,成功并且是有数据的
     *
     * @param responseData
     * @return
     */
    public static boolean isReqSuccAndHasData(ResponseData<?> responseData) {
        if (isRequestSuccess(responseData)) {
            if (null != responseData.getData()) {
                if (responseData.getData() instanceof Collection) {
                    return !collectionIsEmpty((Collection) responseData.getData());
                } else {
                    return null != responseData.getData();
                }
            }
        }
        return false;
    }

    /**
     * 集合是否为空
     *
     * @param collection
     * @return
     */
    public static boolean collectionIsEmpty(Collection collection) {
        return (null == collection || collection.isEmpty() || collection.size() == 0);
    }
}
