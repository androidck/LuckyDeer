package com.lucky.deer.common;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.home.MainActivity;
import com.lucky.model.common.WebViewBean;
import com.lucky.model.util.HawkUtil;
import com.lucky.model.util.HintUtil;
import com.lucky.sharingfunction.dialog.SharingFunctionDialog;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;


/**
 * 公共webview
 *
 * @author wangxiangyi
 * @date 2018/09/20
 */
@SuppressLint("Registered")
@Route(path = "/common/WebViewActivity")
public class WebViewActivity extends BaseActivity {


    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.mWebview)
    WebView mWebview;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rootlayout)
    LinearLayout rootlayout;
    /**
     * mPageType：页面类型<p>
     * mTitle：标题<p>
     * mContent：内容<p>
     * mUrl：链接<p>
     * mHtml：thml
     */

    @Autowired
    WebViewBean webViewBean;

    private String mPageType, mTitle, mContent, mUrl, mHtml;
    /**
     * 调用js方法名
     */
    private String mCallJsMethod;
    private boolean mIsShowBack;

    @Override
    protected int initLayout() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {
        if (getIntent() != null) {
            WebViewBean webViewBean = (WebViewBean) getIntent().getSerializableExtra(mEntity);
            if (webViewBean != null) {
                mPageType = webViewBean.getPageType();
                mTitle = webViewBean.getWebTitle();
                mContent = webViewBean.getContent();
                mUrl = webViewBean.getWebUrl();
                mHtml = webViewBean.getWebHtml();
                mCallJsMethod = webViewBean.getCallJsMethod();
                mIsShowBack = webViewBean.isShowBack();
            }
        }
        initTopBar(topBar, true, TextUtils.isEmpty(mTitle) ? mWebview.getTitle() : mTitle);
        if (1 == HawkUtil.getInstance().getSaveData(KeyConstant.IS_ADVERTISING, 0)) {
            topBar.addRightImageButton(R.mipmap.share_btn, 0)
                    .setOnClickListener(v -> {
                        new SharingFunctionDialog
                                .WebSharingBuilder(mContext)
                                .setAppName(R.string.app_name)
                                .setTitle("测试标题")
                                .setDescription("测试内容")
                                .setText("测试文本内容")
                                .setImageUrl("http://imgcache.AlipayApiEventListener.com/qzone/space_item/pre/0/66768.gif")
                                .setUrl("https://bbs.csdn.net/topics/390815002")
                                .create()
                                .show();
                    });
            topBar.addLeftImageButton(R.mipmap.login_close, 0)
                    .setOnClickListener(v -> {
                        jumpActivity(mActivity, MainActivity.class, true);
                    });
        } else if (!TextUtils.isEmpty(mPageType) && KeyConstant.MY_VIDEO.equals(mPageType)) {
            topBar.addRightImageButton(R.mipmap.share_btn, 0)
                    .setOnClickListener(v -> {
                        new SharingFunctionDialog
                                .WebSharingBuilder(mContext)
                                .setAppName(R.string.app_name)
                                .setTitle(mTitle)
                                .setDescription(mContent)
                                .setUrl(mUrl)
                                .create()
                                .show();
                    });
            topBar.addLeftImageButton(R.mipmap.back, 0)
                    .setOnClickListener(v -> {
                        overridePendingTransition(false, true);
                    });

        } else {
            topBar.addLeftImageButton(mIsShowBack ? R.mipmap.login_close : R.mipmap.back, 0)
                    .setOnClickListener(v -> {
                        if (!TextUtils.isEmpty(mCallJsMethod)) {
                            /*条用js方法*/
                            mWebview.loadUrl("javascript:" + mCallJsMethod);
                        }
                        /*判断是否有上一页*/
                        if (mWebview.canGoBack()) {
                            /* 返回前一个页面*/
                            mWebview.goBack();
                        }
                        overridePendingTransition(false, true);
                    });
        }
        init();
    }

    /**
     * 初始化web控件
     */

    @SuppressLint("SetJavaScriptEnabled")
    public void init() {
        //自适应屏幕
        mWebview.getSettings().setPluginState(WebSettings.PluginState.ON);
//        webView.getSettings().setPluginsEnabled(true);//可以使用插件
        mWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        /*设置允许文件访问*/
        mWebview.getSettings().setAllowFileAccess(true);
        /*设置字体大小*/
//        mWebview.getSettings().setTextSize(WebSettings.TextSize.LARGER);
        /*设置字符编码格式*/
        mWebview.getSettings().setDefaultTextEncodingName("UTF-8");
        /*设置文本缩放大小*/
        mWebview.getSettings().setTextZoom(100);
        /**
         * setAllowFileAccess 启用或禁止WebView访问文件数据
         * setBlockNetworkImage 是否显示网络图像
         * setBuiltInZoomControls 设置是否支持缩放
         * setCacheMode 设置缓冲的模式
         * setDefaultFontSize 设置默认的字体大小
         * setDefaultTextEncodingName 设置在解码时使用的默认编码
         * setFixedFontFamily 设置固定使用的字体
         * setJavaSciptEnabled 设置是否支持Javascript
         * setLayoutAlgorithm 设置布局方式
         * setLightTouchEnabled 设置用鼠标激活被选项
         * setSupportZoom 设置是否支持变焦
         * */
        /*隐藏缩放按钮*/
        mWebview.getSettings().setBuiltInZoomControls(true);
        /*可任意比例缩放*/
        mWebview.getSettings().setUseWideViewPort(true);
        /*setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。*/
        mWebview.getSettings().setLoadWithOverviewMode(true);
        mWebview.getSettings().setSavePassword(true);
        /* 保存表单数据*/
        mWebview.getSettings().setSaveFormData(true);
        /*设置Java脚本已启用*/
        mWebview.getSettings().setJavaScriptEnabled(true);
        /* 启用地理定位*/
        mWebview.getSettings().setGeolocationEnabled(true);
        /*设置定位的数据库路径*/
        mWebview.getSettings().setGeolocationDatabasePath("/data/data/" + getPackageName() + "/databases/");
        /*设置Dom存储已启用*/
        mWebview.getSettings().setDomStorageEnabled(true);


        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                    view.reload();
                    return true;
                } else if (url.startsWith(KeyConstant.WEB_VIEW_HTTP_BAIDU_COM)) {
                    showSuccessDialog(R.string.dialog_receipt_success, KeyConstant.WEB_VIEW_HTTP_BAIDU_COM);
                    return true;
                } else if (url.startsWith(KeyConstant.WEB_VIEW_HTTP_GOOGLE_CN)) {
                    showSuccessDialog(R.string.dialog_successful_card_opening, KeyConstant.WEB_VIEW_HTTP_GOOGLE_CN);
                    return true;
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (error.getErrorCode() == 404) {

                }

            }
        });
        mWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);

                } else {
                    if (progressBar.getVisibility() == View.GONE) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (title != null) {
                    initTopBar(topBar, true, TextUtils.isEmpty(mTitle) ? title : mTitle);
                }
            }
        });
        if (TextUtils.isEmpty(mUrl) && TextUtils.isEmpty(mHtml)) {
            HintUtil.showErrorWithToast(mContext, "获取链接失败");
        } else if (TextUtils.isEmpty(mHtml)) {
            mWebview.loadUrl(mUrl);
        } else {
//            String s = mHtml.replaceAll("\\n", "").replaceAll("\\\\", "").replaceAll("<!--", "").replaceAll("//-->", "");
            mWebview.loadDataWithBaseURL(null, mHtml, "text/html", "UTF-8", null);
        }
    }

//    @Override
//    public void carriedOutMethod() {
//        jumpActivity(mContext, MainActivity.class, true);
//    }


    @Override
    public <T> void carriedOutMethod(T executionParam) {
        switch ((String) executionParam) {
            case KeyConstant.WEB_VIEW_HTTP_BAIDU_COM:
                jumpActivity(mContext, MainActivity.class, true);
                break;
            case KeyConstant.WEB_VIEW_HTTP_GOOGLE_CN:
                finish();
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            rootlayout.removeView(mWebview);
            mWebview.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebview.canGoBack()) {
                mWebview.goBack();// 返回前一个页面
                return true;
            } else {
                if (!TextUtils.isEmpty(mCallJsMethod)) {
                    /*条用js方法*/
                    mWebview.loadUrl("javascript:" + mCallJsMethod);
                }
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SharingFunctionDialog.getInstance(mContext).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SharingFunctionDialog.getInstance(mContext).onNewIntent(intent);
    }

//    public static void main(String[] arg) {
//        String aa = "<html>\\n<head>\\n<meta http-equiv=\\\"Content-Type\\\" content=\\\"text/html; charset=UTF-8\\\">\\n<title>Gateway-Response</title>\\n</head>\\n<body onload=\\\"OnLoadSubmit();\\\">\\n<form id=\\\"gatewayform\\\" action=\\\"https://cashier.95516.com/b2c/api/Activate.action\\\" method=\\\"post\\\">\\n<input type=\\\"hidden\\\" name=\\\"txnType\\\" id=\\\"txnType\\\" value=\\\"79\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"payTimeout\\\" id=\\\"payTimeout\\\" value=\\\"20181225152802\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"frontUrl\\\" id=\\\"frontUrl\\\" value=\\\"http://portal.ronghuijinfubj.com/middlepayportal/merchant/rytPayKuai/pageNotify?bankOrderId=UnionPayDH671Ysv594Z6xBW9020\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"channelType\\\" id=\\\"channelType\\\" value=\\\"07\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"merId\\\" id=\\\"merId\\\" value=\\\"900100048160419\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"tokenPayData\\\" id=\\\"tokenPayData\\\" value=\\\"{trId=62000001549&tokenType=01}\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"txnSubType\\\" id=\\\"txnSubType\\\" value=\\\"00\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"customerInfo\\\" id=\\\"customerInfo\\\" value=\\\"e2NlcnRpZklkPTYyMDQyMjE5OTAwMjEyMTkxMSZjZXJ0aWZUcD0wMSZjdXN0b21lck5tPei0vuW9puW5syZlbmNyeXB0ZWRJbmZvPWlHSU82S2Q0WjcvSjdkVXF1akpTc3RDZ3M4cTFRbE1Vcm94OFNLR29VaEVtK01ZZE1MeHFUZDdPUGlweHVkWFN5bU5PTUZIdFpISjBtMFVHeVk1QXlyTFgrUHlXLzdCTndIVzBTVGRSckdwMXdVb1BZbjB4Z0t5SlQ4ZUV1MEpmZzdZK3NCYytJYU96NDYwaWxxdW9WZm5UMzBITzNXRFNscEhmalVpNXpmR1Q5Z2NsdEM2SFNhbk1ZdkNsc1ZsTGRnT3ZsWERLc3hranJsOVZDMXU2V0JDSHU1bmRlK2dpVTYyQ3BLUzZwTGtBNUdBRXBuUTR2N3V6eVl3eHNBbS9oYi8vYzdvdWlSMm44QlZoaXI0U2h4QWJSMEp6cDc0MnZBNG51Z3Z4TXJ6Z3ZhNlZJTDhmM1U5RG9kck1WNGEwaldDVURBdGNod2JKVFpvQTZndmwzZz09fQ==\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"version\\\" id=\\\"version\\\" value=\\\"5.0.0\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"accType\\\" id=\\\"accType\\\" value=\\\"02\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"signMethod\\\" id=\\\"signMethod\\\" value=\\\"01\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"accNo\\\" id=\\\"accNo\\\" value=\\\"LYxYwEJHITEsgo894eXbLknuB5GtJgyCPhf+PSKyRjOmWaRIREEedT8IuFdCHEdCxIps9V9j7lEV8rNa0vrggvVaosJRksdqrrXz+xxk1NSNlWgIniK9XKxbEITNhflFVwVPngwUzq3Qhh8u0uAekRRyzBfpEUUBwDbEjlv1wsST69GixP5WxUDB0/r1oPqMVy6+BUYJDirz0gJRcrCtLi3HlpmASZy2WotTWOS2Q5jLXyB3Ne4d9gJd2+HJ2VYVY7sIBWF3kZyyDQL2ZwO7eRXLA38EMXhiGa3e0BgRFJR8Yud1BnLExqmAGEE3+iTVHp7EvnKx6k7MkgsJn6AYOw==\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"backUrl\\\" id=\\\"backUrl\\\" value=\\\"http://trx.ronghuijinfubj.com/middlepaytrx/online/openKuaiReceiveChannelServerNotify/UnionPayDH\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"certId\\\" id=\\\"certId\\\" value=\\\"235975\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"internal.merReferer\\\" id=\\\"internal.merReferer\\\" value=\\\"null\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"internal.origReqInfo\\\" id=\\\"internal.origReqInfo\\\" value=\\\"|07|7900\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"encoding\\\" id=\\\"encoding\\\" value=\\\"UTF-8\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"bizType\\\" id=\\\"bizType\\\" value=\\\"000902\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"encryptCertId\\\" id=\\\"encryptCertId\\\" value=\\\"69042905377\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"internal.logId\\\" id=\\\"internal.logId\\\" value=\\\"AC10181225151151973e0123708180\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"signature\\\" id=\\\"signature\\\" value=\\\"LT0IveKHbFvrBaaD0u+0g9JkFj5WKmVBynu69qOuYxmlr/QNVwX9ooEat4LppD2YdzEQ/aik82Db5TRMN0MN50cthM3IseGy/PCYA/wmSn11QnWW/gXWdQ1qh1BuiSl5okdLuxBYNVsuBW82yAkEwYjePnX+E7Aox/TKM4mtSAnhpmU/qIqBQd6fgo73AsBIk8HqSp9xcBJInC2fgC0nc0AyU9ibPtAlUFeskgNaJCYfQUYbAPyUkn/RXiAXjI5WBVqlg6Kjo+tYEgT1Tpam+0GlM6VaeIaj2M2aAy16FO0RBMsLV1qrBWzWLCsoD9ReMKh26GWgqf7V8IvEI/oAFw==\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"orderId\\\" id=\\\"orderId\\\" value=\\\"UnionPayDH671Ysv594Z6xBW9020\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"accessType\\\" id=\\\"accessType\\\" value=\\\"0\\\"/>\\n<input type=\\\"hidden\\\" name=\\\"txnTime\\\" id=\\\"txnTime\\\" value=\\\"20181225151302\\\"/>\\n</form>\\n<script type=\\\"text/javascript\\\">\\n<!--\\nfunction OnLoadSubmit()\\n{\\ndocument.getElementById(\\\"gatewayform\\\").submit();\\n}\\n//-->\\n</script>\\n</body>\\n</html>";
//        System.out.println(aa.replaceAll("\n", "").replaceAll("\\\\", "").replaceAll("-->","").replaceAll("<!--","").replaceAll("//",""));
//
//    }
}
