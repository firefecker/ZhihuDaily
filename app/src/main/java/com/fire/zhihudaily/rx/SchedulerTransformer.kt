package com.fire.zhihudaily.rx

import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by fire on 2017/12/8.
 * Date：2017/12/8
 * Author: fire
 * Description:
 */
class SchedulerTransformer<T> : ObservableTransformer<T, T> {
    override fun apply(observable: io.reactivex.Observable<T>): ObservableSource<T> {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}