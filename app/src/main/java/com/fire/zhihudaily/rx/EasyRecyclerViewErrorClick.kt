package com.fire.zhihudaily.rx

import android.annotation.SuppressLint
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView
import com.fire.zhihudaily.R
import com.jakewharton.rxbinding2.internal.Preconditions
import com.jude.easyrecyclerview.EasyRecyclerView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

/**
 * Created by fire on 2017/12/11.
 * Date：2017/12/11
 * Author: fire
 * Description:
 */
class EasyRecyclerViewErrorClick : Observable<Any> {

    @SuppressLint("RestrictedApi", "WrongViewCast")
    override fun subscribeActual(observer: Observer<in Any>) {
        if (!Preconditions.checkMainThread(observer)) {
            return
        }
        val errorButton = recycler.errorView.findViewById<TextView>(R.id.tv_button_retry)

        val listener = ItemClickListener(errorButton,observer)
        observer.onSubscribe(listener)
        errorButton.setOnClickListener(listener)
    }

    private val recycler: EasyRecyclerView

    constructor(recycler: EasyRecyclerView){
        this.recycler = recycler
    }


    internal class ItemClickListener(private val errorButton: TextView, private val observer: Observer<in Any>) : MainThreadDisposable(), OnClickListener{
        override fun onClick(v: View?) {
            if (!isDisposed) {
                observer.onNext(Any())
            }
        }

        override fun onDispose() {
            errorButton.setOnClickListener(null)
        }
    }
}