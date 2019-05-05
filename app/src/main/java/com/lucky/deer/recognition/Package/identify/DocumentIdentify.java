package com.lucky.deer.recognition.Package.identify;

import android.annotation.SuppressLint;
import android.content.Context;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.BankCardParams;
import com.baidu.ocr.sdk.model.BankCardResult;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.sdk.model.OcrRequestParams;
import com.baidu.ocr.sdk.model.OcrResponseResult;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.lucky.deer.recognition.Package.identify.adapter.ReturnDataProcessingAdapter;
import com.lucky.model.response.ResponseData;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * 初始化识别器
 *
 * @author wangxiangyi
 * @date 2018/10/17
 */
public class DocumentIdentify implements IExecutor {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static String mAk, mSk;
    private static Boolean mIsDebug = false;
    private static Boolean isTokenCertification;
    @SuppressLint("StaticFieldLeak")
    private static DocumentIdentify instance;
    private Gson gson;

    /**
     * 创建信息
     *
     * @param context   上下文
     * @param apiKey    文字识别apiKey
     * @param secretKey 文字识别secretKey
     * @param isDebug   是否有日志
     */
    public static void onCreate(Context context, String apiKey, String secretKey, boolean isDebug) {
        mContext = context;
        mAk = apiKey;
        mSk = secretKey;
        mIsDebug = isDebug;
    }

    public static DocumentIdentify getInstance() {
        if (instance == null) {
            synchronized (DocumentIdentify.class) {
                if (instance == null) {
                    instance = new DocumentIdentify();
                }
            }
        }
        return instance;
    }

    /**
     * 用明文ak，sk初始化
     */
    public DocumentIdentify initAccessTokenWithAkSk() {
        Logger.w(getApiKey() + "=====" + getSecretKey());
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .registerTypeAdapter(Double.class, (JsonSerializer<Double>) (src, typeOfSrc, context) -> {
                    if (src == src.longValue()) {
                        return new JsonPrimitive(src.longValue());
                    }
                    return new JsonPrimitive(src);
                })
                .create();
        OCR.getInstance()
                .initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
                    @Override
                    public void onResult(AccessToken result) {
                        initLicense();
                        if (isDebug()) {
                            Logger.i("初始化认证成功。\n" +
                                    "获取token：" + gson.toJson(result));
                        }
                        isTokenCertification = true;
                    }

                    @Override
                    public void onError(OCRError error) {
                        error.printStackTrace();
                        if (isDebug()) {
                            Logger.e(new Throwable("初始化认证失败,请检查 key"), "失败原因：" + error.getMessage());
                        }
                        isTokenCertification = false;
                    }
                }, getContext(), getApiKey(), getSecretKey());
        return this;
    }

    /**
     * 初始化文字许可证
     */
    private void initLicense() {
        CameraNativeHelper.init(getContext(), OCR.getInstance().getLicense(),
                (errorCode, e) -> {
                    final String msg;
                    switch (errorCode) {
                        case CameraView.NATIVE_SOLOAD_FAIL:
                            msg = "加载so失败，请确保apk中存在ui部分的so";
                            break;
                        case CameraView.NATIVE_AUTH_FAIL:
                            msg = "授权本地质量控制token获取失败";
                            break;
                        case CameraView.NATIVE_INIT_FAIL:
                            msg = "本地质量控制";
                            break;
                        default:
                            msg = String.valueOf(errorCode);
                    }
                    if (isDebug()) {
                        Logger.e(e, "错误原因：" + msg);
                    }
                });
    }

    public static Context getContext() {
        return mContext;
    }

    private static String getApiKey() {
        return mAk;
    }

    private static String getSecretKey() {
        return mSk;
    }

    private static Boolean isDebug() {
        return mIsDebug;
    }

    public static Boolean isTokenCertification() {
        return isTokenCertification;
    }


    @Override
    public <T> Observable<ResponseData<T>> executor(String idCardSide, String obtainName, String filePath, Type type) {
        return executor(idCardSide, obtainName, new File(filePath), type);
    }

    @Override
    public <T> Observable<ResponseData<T>> executor(String obtainName, String filePath, Type type) {
        return executor(null, obtainName, new File(filePath), type);
    }

    @Override
    public <T> Observable<ResponseData<T>> executor(String obtainName, File file, Type type) {
        return executor(null, obtainName, file, type);
    }

    @Override
    public <T> Observable<ResponseData<T>> executor(String idCardSide, final String obtainName, final File file, final Type type) {
        final OCR instance = OCR.getInstance();
        return Observable.create((ObservableOnSubscribe<String>) emitter -> {
            ResponseData responseData = new ResponseData();
            switch (obtainName) {
                case "身份证":
                    // 身份证识别参数设置
                    final IDCardParams idCardParams = new IDCardParams();
                    idCardParams.setImageFile(file);
                    // 设置身份证正反面
                    idCardParams.setIdCardSide(idCardSide);
                    // 设置方向检测
                    idCardParams.setDetectDirection(true);
                    // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
                    idCardParams.setImageQuality(40);
                    if (instance != null) {
                        instance.recognizeIDCard(idCardParams, new OnResultListener<IDCardResult>() {
                            @Override
                            public void onResult(IDCardResult idCardResult) {
                                if (idCardResult == null) {
                                    responseData.setMsg("未返回身份证信息。");
                                    emitter.onNext(gson.toJson(responseData));
                                } else {
                                    responseData.setCode(200);
                                    responseData.setData(idCardResult);
                                    emitter.onNext(gson.toJson(responseData));
                                }
                            }

                            @Override
                            public void onError(OCRError ocrError) {
                                ocrError.printStackTrace();
                                responseData.setMsg("身份证认证失败！请重试");
                                emitter.onNext(gson.toJson(responseData));
                            }
                        });
                    } else {
                        responseData.setMsg("身份证认证失败！请重试");
                        emitter.onNext(gson.toJson(responseData));
                    }
                    break;
                case "银行卡":
                    // 身份证识别参数设置
                    BankCardParams bankCardParams = new BankCardParams();
                    bankCardParams.setImageFile(file);
                    if (instance != null) {
                        instance.recognizeBankCard(bankCardParams, new OnResultListener<BankCardResult>() {
                            @Override
                            public void onResult(BankCardResult result) {
                                if (result == null) {
                                    responseData.setMsg("未返回银行卡信息。");
                                    emitter.onNext(gson.toJson(responseData));
                                } else {
                                    responseData.setCode(200);
                                    responseData.setData(result);
                                    emitter.onNext(gson.toJson(responseData));
                                }
                            }

                            @Override
                            public void onError(OCRError error) {
                                error.printStackTrace();
                                responseData.setMsg("银行卡认证失败！请重试");
                                emitter.onNext(gson.toJson(responseData));
                            }
                        });
                    } else {
                        responseData.setMsg("银行卡认证失败！请重试");
                        emitter.onNext(gson.toJson(responseData));
                    }
                    break;
                case "驾驶证":
                    // 驾驶证识别参数设置
                    OcrRequestParams ocrRequestParams = new OcrRequestParams();
                    // 设置image参数
                    ocrRequestParams.setImageFile(file);
                    // 设置其他参数
                    ocrRequestParams.putParam("detect_direction", true);
                    if (instance != null) {
                        // 调用驾驶证识别服务
                        instance.recognizeDrivingLicense(ocrRequestParams, new OnResultListener<OcrResponseResult>() {
                            @Override
                            public void onResult(OcrResponseResult result) {
                                if (result == null) {
                                    responseData.setMsg("未返回驾驶证信息。");
                                    emitter.onNext(gson.toJson(responseData));
                                } else {
                                    responseData.setCode(200);
                                    responseData.setData(result.getJsonRes());
                                    emitter.onNext(gson.toJson(responseData));
                                }
                            }

                            @Override
                            public void onError(OCRError error) {
                                error.printStackTrace();
                                responseData.setMsg("驾驶证认证失败！请重试");
                                emitter.onNext(gson.toJson(responseData));
                            }
                        });
                    } else {
                        responseData.setMsg("驾驶证认证失败！请重试");
                        emitter.onNext(gson.toJson(responseData));
                    }
                    break;
                case "行驶证":
                    OcrRequestParams ocrRequestParams1 = new OcrRequestParams();
                    ocrRequestParams1.setImageFile(file);
                    if (instance != null) {
                        instance.recognizeVehicleLicense(ocrRequestParams1, new OnResultListener<OcrResponseResult>() {
                            @Override
                            public void onResult(OcrResponseResult result) {
                                if (result == null) {
                                    responseData.setMsg("未返回行驶证信息。");
                                    emitter.onNext(gson.toJson(responseData));
                                } else {
                                    responseData.setCode(200);
                                    responseData.setData(result.getJsonRes());
                                    emitter.onNext(gson.toJson(responseData));
                                }
                            }

                            @Override
                            public void onError(OCRError error) {
                                error.printStackTrace();
                                responseData.setMsg("行驶证认证失败！请重试");
                                emitter.onNext(gson.toJson(responseData));
                            }
                        });
                    } else {
                        responseData.setMsg("行驶证认证失败！请重试");
                        emitter.onNext(gson.toJson(responseData));
                    }
                    break;
                default:
            }
        })
                //在异步 上跑
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new ReturnDataProcessingAdapter(gson, type, isDebug()));
    }
}
