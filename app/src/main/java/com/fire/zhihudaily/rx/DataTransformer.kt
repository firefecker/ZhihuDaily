package com.fire.zhihudaily.rx

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import retrofit2.Response

/**
 * Created by fire on 2017/12/8.
 * Date：2017/12/8
 * Author: fire
 * Description:
 */
class DataTransformer<T> : ObservableTransformer<Response<T>, T> {
    override fun apply(upstream: Observable<Response<T>>): ObservableSource<T> {
        return upstream.map {

            return@map it.body();
        }
    }

}