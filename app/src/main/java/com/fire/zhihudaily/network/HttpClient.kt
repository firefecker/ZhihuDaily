package com.fire.zhihudaily.network

import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Logger
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit.SECONDS

/**
 * Created by fire on 2017/12/8.
 * Date：2017/12/8
 * Author: fire
 * Description:
 */
class HttpClient {
    private var mCookieManager: CookieManager
    private var mOkHttpClient: OkHttpClient
    private var mRetrofit: Retrofit
    private val api:Api

    private constructor() {
        var logging = HttpLoggingInterceptor(object : Logger{
            override fun log(message: String?) {
            }
        })
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        mCookieManager = CookieManager()
        mCookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        mOkHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(20, SECONDS)
                .cookieJar(JavaNetCookieJar(mCookieManager))
                .build()
        var url = "http://news-at.zhihu.com/api/4/"
        mRetrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build()
        api = mRetrofit.create(Api::class.java)
    }

    fun service(): Api {
        return api
    }

    companion object {
        fun getInstance():HttpClient {
            return Inner.mHttpClient
        }
    }

    private object Inner {
        val mHttpClient = HttpClient()
    }
}