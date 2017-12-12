package com.fire.zhihudaily.widget

import android.graphics.Bitmap
import android.net.http.SslError
import android.webkit.ClientCertRequest
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * Created by fire on 2017/12/10.
 * Date：2017/12/10
 * Author: fire
 * Description:
 */
class MyWebViewClient : WebViewClient(){

    /**
     * 加载资源
     */
    override fun onLoadResource(view: WebView?, url: String?) {
        super.onLoadResource(view, url)
    }

    /**
     * 完成加载
     */
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
    }

    override fun onReceivedClientCertRequest(view: WebView?, request: ClientCertRequest?) {
        super.onReceivedClientCertRequest(view, request)
    }

    /**
     * 载页面的服务器出现错误时（如404）调用
     */
    override fun onReceivedError(view: WebView?, request: WebResourceRequest?,
            error: WebResourceError?) {
        super.onReceivedError(view, request, error)
    }

    /**
     * 设置加载前的函数
     */
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
    }

    /**
     *设置不用系统浏览器打开,直接显示在当前Webview
     */
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return super.shouldOverrideUrlLoading(view, request)
    }

    /**
     *
     */
    override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?,
            errorResponse: WebResourceResponse?) {
        super.onReceivedHttpError(view, request, errorResponse)
    }

    /**
     * 处理Https错误
     */
    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)
    }

}