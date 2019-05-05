package com.lucky.model.common;

import java.io.Serializable;

/**
 * WebView参数
 *
 * @author wangxiangyi
 * @date 2018/09/20
 */
public class WebViewBean implements Serializable {
    /**
     * 页面类型<p>
     * MyVideo：我的视频页面
     */
    private String pageType;
    /**
     * 标题
     */
    private String webTitle;
    /**
     * 描述
     */
    private String content;
    /**
     * 链接地址
     */
    private String webUrl;
    /**
     * Html页面
     */
    private String webHtml;
    /**
     * 调用js方法名
     */
    private String callJsMethod;
    /**
     * 是否显示返回或叉号按钮标记<p>
     * false ：显示返回按钮<p>
     * true ：显示叉号按钮
     */
    private boolean isShowBack = false;

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getWebHtml() {
        return webHtml;
    }

    public void setWebHtml(String webHtml) {
        this.webHtml = webHtml;
    }

    public boolean isShowBack() {
        return isShowBack;
    }

    public void setShowBack(boolean showBack) {
        isShowBack = showBack;
    }

    public String getCallJsMethod() {
        return callJsMethod;
    }

    public void setCallJsMethod(String callJsMethod) {
        this.callJsMethod = callJsMethod;
    }
}
