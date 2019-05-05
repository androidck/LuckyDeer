package com.lucky.deer.recognition.Package.executor;

import com.baidu.ocr.sdk.model.BankCardResult;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.sdk.model.OcrResponseResult;
import com.google.gson.reflect.TypeToken;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.recognition.Package.model.DriverLicenseResult;
import com.lucky.model.response.ResponseData;

import io.reactivex.Observable;

public class StartObtainInfo extends BaseProxy {
    private static StartObtainInfo instance;

    public static StartObtainInfo getInstance() {
        if (instance == null) {
            synchronized (StartObtainInfo.class) {
                if (instance == null) {
                    instance = new StartObtainInfo();
                }
            }
        }
        return instance;
    }

    /**
     * 解析身份证
     *
     * @param filePath 身份证图片路径
     * @return 返回信息
     */
    public <T> Observable<ResponseData<IDCardResult>> getIDCardInfo(int idCardSide, String filePath) {
        String isIdCardSide = null;
        switch (idCardSide) {
            /*正面*/
            case KeyConstant.REQUEST_CODE_CAMERA_POSITIVE:
                isIdCardSide = IDCardParams.ID_CARD_SIDE_FRONT;
                break;
            /*反面*/
            case KeyConstant.REQUEST_CODE_CAMERA_NEGATIVE:
                isIdCardSide = IDCardParams.ID_CARD_SIDE_BACK;
                break;
            default:
        }
        return requestExecutor.executor(isIdCardSide, "身份证", filePath, new TypeToken<ResponseData<IDCardResult>>() {
        }.getType());
    }

    /**
     * 解析银行卡
     *
     * @param filePath 银行卡图片路径
     * @return 返回信息
     */
    public <T> Observable<ResponseData<BankCardResult>> getBankCardInfo(String filePath) {
        return requestExecutor.executor("银行卡", filePath, new TypeToken<ResponseData<BankCardResult>>() {
        }.getType());
    }

    /**
     * 解析驾驶证
     *
     * @param filePath 驾驶证图片路径
     * @return 返回信息
     */
    public <T> Observable<ResponseData<DriverLicenseResult>> getDriverLicenseInfo(String filePath) {
        return requestExecutor.executor("驾驶证", filePath, new TypeToken<ResponseData<DriverLicenseResult>>() {
        }.getType());
    }

    /**
     * 解析行驶证
     *
     * @param filePath 行驶证图片路径
     * @return 返回信息
     */
    public <T> Observable<ResponseData<OcrResponseResult>> getDrivingLicenseInfo(String filePath) {
        return requestExecutor.executor("行驶证", filePath, new TypeToken<ResponseData<OcrResponseResult>>() {
        }.getType());
    }
}
