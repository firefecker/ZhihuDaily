package com.fire.zhihudaily.rx

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import java.util.concurrent.TimeUnit

/**
 * Created by fire on 2017/12/8.
 * Date：2017/12/8
 * Author: fire
 * Description:
 */
class DefaultButtonTransformer<T>: ObservableTransformer<T, T> {
    override fun apply(observable: Observable<T>): ObservableSource<T> {
        return observable.throttleFirst(500, TimeUnit.MILLISECONDS)
    }


}