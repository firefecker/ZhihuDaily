package com.fire.zhihudaily.rx

import android.util.Log
import android.widget.TextView
import com.fire.zhihudaily.R
import com.jude.easyrecyclerview.EasyRecyclerView
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter

/**
 * Created by fire on 2017/12/11.
 * Date：2017/12/11
 * Author: fire
 * Description:
 */
open class RecyclerNetObser<T> : NetObser<List<T>> {

    override fun onNext(t: List<T>) {
        var adapter = recyclerView?.adapter as RecyclerArrayAdapter<T>;
        if (adapter == null) {
            recyclerView?.showEmpty()
            return
        }
    }

    private var recyclerView : EasyRecyclerView? = null

    constructor(recyclerView : EasyRecyclerView) {
        this.recyclerView = recyclerView
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        var adapter = recyclerView?.adapter as RecyclerArrayAdapter<Any>;
        if (adapter == null) {
            recyclerView?.showEmpty()
            return
        }
        if (adapter.count > 0) {
            adapter.pauseMore()
        } else{
            if (null != recyclerView?.errorView) {
                if (e == null) {
                    recyclerView?.showError()
                    return
                }
                val mTvContent = recyclerView?.getErrorView()?.findViewById<TextView>(
                        R.id.tv_error_content)
                if (null != mTvContent) {
                    mTvContent.text = e.message
                }
                recyclerView?.showError()
            }
        }
    }

}