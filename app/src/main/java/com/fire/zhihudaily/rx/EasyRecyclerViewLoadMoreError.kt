package com.fire.zhihudaily.rx

import android.annotation.SuppressLint
import com.fire.zhihudaily.R
import com.jakewharton.rxbinding2.internal.Preconditions
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

/**
 * Created by fire on 2017/12/11.
 * Date：2017/12/11
 * Author: fire
 * Description:
 */
class EasyRecyclerViewLoadMoreError<T>  : Observable<Any> {

    @SuppressLint("RestrictedApi")
    override fun subscribeActual(observer: Observer<in Any>) {
        if (!Preconditions.checkMainThread(observer)) {
            return
        }
        val listener = ItemClickListener(observer)
        observer.onSubscribe(listener)
        adapter.setError(R.layout.view_error_loadmore,listener)
    }

    private val adapter: RecyclerArrayAdapter<T>

    constructor(adapter: RecyclerArrayAdapter<T>){
        this.adapter = adapter
    }


    internal class ItemClickListener(private val observer: Observer<in Any>) : MainThreadDisposable(), RecyclerArrayAdapter.OnErrorListener{
        override fun onErrorShow() {

        }

        override fun onErrorClick() {
            if (!isDisposed) {
                observer.onNext(Any())
            }
        }

        override fun onDispose() {
        }
    }
}