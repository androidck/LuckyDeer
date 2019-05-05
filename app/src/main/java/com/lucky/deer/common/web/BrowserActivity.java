package com.lucky.deer.common.web;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.find.PayResult;
import com.lucky.deer.home.MainActivity;
import com.lucky.model.response.home.alipay.AlipayResponse;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;
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

public class BrowserActivity extends BaseActivity {



    public final static String PARAM_URL = "param_url";

    public final static String PARAM_MODE = "param_mode";

    public final static String DETAILS_TITLE= KeyConstant.DETAILS_TITLE;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.ll_top_bar_background)
    LinearLayout llTopBarBackground;
    @BindView(R.id.mWebview)
    WebView webView;
    @BindView(R.id.rootlayout)
    LinearLayout rootlayout;

    private SonicSession sonicSession;


    @Override
    protected int initLayout() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {
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
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra(PARAM_URL);
        String title=intent.getStringExtra(DETAILS_TITLE);
        initTopBar(topBar,title);
        int mode = intent.getIntExtra(PARAM_MODE, -1);
        if (TextUtils.isEmpty(url) || -1 == mode) {
            finish();
            return;
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(getApplication()), new SonicConfig.Builder().build());
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
                        return new OfflinePkgSessionConnection(BrowserActivity.this, session, intent);
                    }
                });
            }

            sonicSession = SonicEngine.getInstance().createSession(url, sessionConfigBuilder.build());
            if (null != sonicSession) {
                sonicSession.bindClient(sonicSessionClient = new SonicSessionClientImpl());
            } else {
                Toast.makeText(this, "create sonic session fail!", Toast.LENGTH_LONG).show();
            }
        }

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String title = view.getTitle();
                if (!TextUtils.isEmpty(title)) {
                }
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

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("shoppingUrl",url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis());
        webView.addJavascriptInterface(new SonicJavaScriptInterface(sonicSessionClient, intent), "sonic");


        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(webView);
            sonicSessionClient.clientReady();
        } else { // default mode
            webView.loadUrl(url);
        }

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress);//设置进度值
                }
                super.onProgressChanged(view, newProgress);


            }
        });

    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
    }*/

    @Override
    protected void onDestroy() {
        if (null != sonicSession) {
            sonicSession.destroy();
            sonicSession = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();// 返回前一个页面
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
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

    /**
     * 支付标志
     */
    public int sdkPayFlag = 1;

    private class InJavaScriptLocalObj {

        @JavascriptInterface
        public void settingScroll(String height) {

        }
        @JavascriptInterface
        public void sendPayment(String paySign){
            Log.d("dasdasd",paySign);
            Runnable payRunnable = () -> {
                Map<String, String> result = new PayTask(BrowserActivity.this).payV2(paySign, true);
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

                        webView.loadUrl(String.format(HttpConstant.H5_MALL_ORDER_INFORMATION, (getUserInfo() != null ? getUserInfo().getId() : ""), "all"));
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
