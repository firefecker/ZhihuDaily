package com.fire.zhihudaily.rx

import android.annotation.SuppressLint
import com.fire.zhihudaily.R.layout
import com.jakewharton.rxbinding2.internal.Preconditions
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

/**
 * Created by fire on 2017/12/10.
 * Date：2017/12/10
 * Author: fire
 * Description:
 */
class EasyRecyclerViewLoadMore <T> : Observable<Any> {

    @SuppressLint("RestrictedApi")
    override fun subscribeActual(observer: Observer<in Any>) {
        if (!Preconditions.checkMainThread(observer)) {
            return
        }
        val listener = ItemClickListener(observer)
        observer.onSubscribe(listener)
        adapter.setMore(layout.view_loadmore1,listener)
    }

    private val adapter: RecyclerArrayAdapter<T>

    constructor(adapter: RecyclerArrayAdapter<T>){
        this.adapter = adapter
    }


    internal class ItemClickListener( private val observer: Observer<in Any>) : MainThreadDisposable(),RecyclerArrayAdapter.OnLoadMoreListener{
        override fun onLoadMore() {
            if (!isDisposed) {
                observer.onNext(Any())
            }
        }

        override fun onDispose() {
        }
    }
}