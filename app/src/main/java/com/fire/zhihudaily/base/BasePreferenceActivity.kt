package com.fire.zhihudaily.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.XmlRes
import android.support.v7.widget.Toolbar
import com.fire.zhihudaily.R

/**
 * Created by fire on 2017/12/11.
 * Date：2017/12/11
 * Author: fire
 * Description:
 */
open abstract class BasePreferenceActivity : RxPreferenceActivity(){

    private var preference: SharedPreferences? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(getXml())
        setContentView(getLayout())
        initView()
        initData()
    }

    @LayoutRes
    abstract fun getLayout() : Int

    @XmlRes
    abstract fun getXml() : Int

    abstract fun initView()

    abstract fun initData()

    fun getPreference() : SharedPreferences {
        if (preference == null) {
            preference = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        }
        return preference!!
    }

    fun setToolBar(toolbar: Toolbar, title: String) {
        toolbar.title = title + ""
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    fun setToolBarNoBack(toolbar: Toolbar, title: String) {
        toolbar.title = title + ""
    }

}