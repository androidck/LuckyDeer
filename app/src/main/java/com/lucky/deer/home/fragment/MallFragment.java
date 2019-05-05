package com.lucky.deer.home.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseFragment;
import com.lucky.deer.common.web.SonicRuntimeImpl;
import com.lucky.deer.common.web.SonicSessionClientImpl;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.find.PayResult;
import com.lucky.deer.home.MainActivity;
import com.lucky.model.response.home.alipay.AlipayResponse;
import com.lucky.model.util.HawkUtil;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.sonic.sdk.SonicCacheInterceptor;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicConstants;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.tencent.sonic.sdk.SonicSessionConnection;
import com.tencent.sonic.sdk.SonicSessionConnectionInterceptor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * 发现模块
 *
 * @author wangxiangyi
 * @date 2018/09/20
 */
public class MallFragment extends BaseFragment {


    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.top_bar)
    QMUITopBar topBar;

    private SonicSession sonicSession;

    int mode = 1;
    public String ShopUrl;


    @Override
    protected int initlayout() {
        return R.layout.fragment_mall;
    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void initView() {
        topBar.setMinimumHeight(getStatusBar());
        //url = "http://shop.minmai1688.com/shoppingmall/shoppingmall/homePage.html?openId="+getUserInfo().getId();
        // url = "http://shop.minmai1688.com/shoppingmall/shoppingmall/homePage.html?openId=c263ac9d674e46558f89d1cb76cb9951";
       // ShopUrl = "http://192.168.1.16:8080/shoppingmall/homePage.html?openId=c263ac9d674e46558f89d1cb76cb9951";
        //url = "http://m.baidu.com";
        if (HawkUtil.getInstance().isContains(HawkUtil.USER_INFO)) {
            Log.d("asdasdasd",getUserInfo().getId());
              ShopUrl= HttpConstant.H5_MALL+getUserInfo().getId();
             // ShopUrl = "http://shop.minmai1688.com/shoppingmall/shoppingmall/homePage.html?openId="+getUserInfo().getId();
        }


       /* LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lyShopping.getLayoutParams();
        params.setMargins(0,getStatusBar(),0,0);
        lyShopping.setLayoutParams(params);
*/
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        //webView.removeJavascriptInterface("searchBoxJavaBridge_");
        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSavePassword(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        webSettings.setSaveFormData(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setLoadWithOverviewMode(true);
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "Android");
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        // 设置子视图是否允许滚动到顶部
        refreshLayout.setEnableLoadMore(false);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initData() {
        super.initData();
        if (HawkUtil.getInstance().isContains(HawkUtil.USER_INFO)) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            if (!SonicEngine.isGetInstanceAllowed()) {
                SonicEngine.createInstance(new SonicRuntimeImpl(getContext()), new SonicConfig.Builder().build());
            }
            SonicSessionClientImpl sonicSessionClient = null;
            if (MainActivity.MODE_DEFAULT != mode) { // sonic mode
                SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
                sessionConfigBuilder.setSupportLocalServer(true);
                if (MainActivity.MODE_SONIC_WITH_OFFLINE_CACHE == mode) {
                    sessionConfigBuilder.setCacheInterceptor(new SonicCacheInterceptor(null) {
                        @Override
                        public String getCacheData(SonicSession session) {
                            return null; // offline pkg does not need cache
                        }
                    });

                    sessionConfigBuilder.setConnectionInterceptor(new SonicSessionConnectionInterceptor() {
                        @Override
                        public SonicSessionConnection getConnection(SonicSession session, Intent intent) {
                            return new OfflinePkgSessionConnection(getContext(), session, intent);
                        }
                    });
                }

                sonicSession = SonicEngine.getInstance().createSession(ShopUrl, sessionConfigBuilder.build());
                if (null != sonicSession) {
                    sonicSession.bindClient(sonicSessionClient = new SonicSessionClientImpl());
                } else {

                }
            }

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    String title = view.getTitle();
                    if (sonicSession != null) {
                        sonicSession.getSessionClient().pageFinish(url);
                    }
                }


                @TargetApi(21)
                public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                    return shouldInterceptRequest(view, request.getUrl().toString());
                }

                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                    if (sonicSession != null) {
                        return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
                    }
                    return null;
                }
            });

            // intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis());
            // webView.addJavascriptInterface(new SonicJavaScriptInterface(sonicSessionClient, intent), "sonic");


            if (sonicSessionClient != null) {
                sonicSessionClient.bindWebView(webView);
                sonicSessionClient.clientReady();
            } else { // default mode

                webView.loadUrl(ShopUrl);
                refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                    @Override
                    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                        refreshLayout.getLayout().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                webView.reload();
                                refreshLayout.finishRefresh();
                            }
                        }, 150);
                    }
                });
            }

            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (newProgress == 100) {
                        progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                        webView.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                                    //按返回键操作并且能回退网页
                                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                                        //后退
                                        webView.goBack();
                                        return true;
                                    }
                                }
                                return false;
                            }
                        });
                        if (MainActivity.mAMapLocation != null) {
                            webView.loadUrl(String.format("javascript:addressnow('%s')", MainActivity.mAMapLocation.getCity()));
                        }
                    } else {
                        progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                        progressBar.setProgress(newProgress);//设置进度值
                    }
                    super.onProgressChanged(view, newProgress);
                }
            });
        }


    }

    @Override
    public void onDestroy() {
        if (null != sonicSession) {
            sonicSession.destroy();
            sonicSession = null;
        }
        super.onDestroy();
    }


    private static class OfflinePkgSessionConnection extends SonicSessionConnection {

        private final WeakReference<Context> context;

        public OfflinePkgSessionConnection(Context context, SonicSession session, Intent intent) {
            super(session, intent);
            this.context = new WeakReference<Context>(context);
        }

        @Override
        protected int internalConnect() {
            Context ctx = context.get();
            if (null != ctx) {
                try {
                    InputStream offlineHtmlInputStream = ctx.getAssets().open("sonic-demo-index.html");
                    responseStream = new BufferedInputStream(offlineHtmlInputStream);
                    return SonicConstants.ERROR_CODE_SUCCESS;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            return SonicConstants.ERROR_CODE_UNKNOWN;
        }

        @Override
        protected BufferedInputStream internalGetResponseStream() {
            return responseStream;
        }

        @Override
        protected String internalGetCustomHeadFieldEtag() {
            return SonicSessionConnection.CUSTOM_HEAD_FILED_ETAG;
        }

        @Override
        public void disconnect() {
            if (null != responseStream) {
                try {
                    responseStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public int getResponseCode() {
            return 200;
        }

        @Override
        public Map<String, List<String>> getResponseHeaderFields() {
            return new HashMap<>(0);
        }

        @Override
        public String getResponseHeaderField(String key) {
            return "";
        }
    }

    public int getStatusBar() {
        /**
         * 获取状态栏高度
         * */
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }


    /**
     * 支付标志
     */
    public int sdkPayFlag = 1;


    private class InJavaScriptLocalObj {

        @JavascriptInterface
        public void settingScroll(String height) {
            toast(height);
            if (height.equals("0")) {
                refreshLayout.setEnableRefresh(true);
            } else {
                refreshLayout.setEnableRefresh(false);
            }
        }
        @JavascriptInterface
        public void sendPayment(String paySign){
            Runnable payRunnable = () -> {
                Map<String, String> result = new PayTask(mActivity).payV2(paySign, true);
                Message msg = new Message();
                msg.what = sdkPayFlag;
                msg.obj = result;
                mHandler.sendMessage(msg);
            };
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }
    }

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @SuppressLint("CheckResult")
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /*对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。*/
                    /*同步返回需要验证的信息*/
                    String resultInfo = payResult.getResult();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(payResult.getResultStatus(), "9000")) {
                        AlipayResponse response = new AlipayResponse();
                        Type type = new TypeToken<Map<String, String>>() {
                        }.getType();
                        JsonObject jsonObject = new JsonParser().parse(resultInfo).getAsJsonObject();
                        Map<String, String> map = new Gson().fromJson(jsonObject.getAsJsonObject("alipay_trade_app_pay_response"), type);
                        response.setTradeNo(map.get("trade_no"));
                        response.setOutTradeNo(map.get("out_trade_no"));
                        mNetworkRequestInstance.syncNotify(response)
                                .subscribe(returnData -> {

                                });
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        jumpWebView("全部订单", String.format(HttpConstant.H5_MALL_ORDER_INFORMATION, (getUserInfo() != null ? getUserInfo().getId() : ""), "all"));
                    } else if (TextUtils.equals(payResult.getResultStatus(), "6001")) {
                        HintUtil.showErrorWithToast(mActivity, "取消支付");
                    }
                    break;
                default:
                    break;
            }
        }
    };
}
