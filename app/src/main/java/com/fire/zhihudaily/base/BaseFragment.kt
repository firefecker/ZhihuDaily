package com.fire.zhihudaily.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fire.zhihudaily.R
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * Created by fire on 2017/12/9.
 * Date：2017/12/9
 * Author: fire
 * Description:
 */
abstract class BaseFragment : RxFragment() {

    var isVisible: Boolean? = null
    var isPrepared: Boolean = false
    private var preference: SharedPreferences? = null;

    abstract fun getLayout() : Int

    abstract fun initView()

    abstract fun initData()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = layoutInflater.inflate(getLayout(),null)
        isPrepared = true;
        lazyLoad();
        return inflate
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    fun getPreference() : SharedPreferences {
        if (preference == null) {
            preference = activity.getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        }
        return preference!!
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isVisible = true
            onVisible()
        } else {
            isVisible = false
            onInvisible()
        }
    }

    protected fun onVisible() {
        lazyLoad()
    }

    protected fun lazyLoad() {}
    protected fun onInvisible() {}

}