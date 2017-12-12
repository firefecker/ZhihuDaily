package com.fire.zhihudaily.rx

import android.util.Log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by fire on 2017/12/10.
 * Date：2017/12/10
 * Author: fire
 * Description:
 */
abstract class NetObser<T> : Observer<T> {



    override fun onError(e: Throwable) {
        if (e == null) {
            return
        }
    }

    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {

    }

}