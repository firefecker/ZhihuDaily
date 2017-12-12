package com.fire.zhihudaily.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.widget.Toolbar
import com.fire.zhihudaily.R
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * Created by fire on 2017/12/8.
 * Date：2017/12/8
 * Author: fire
 * Description:
 */
abstract class BaseActivity : RxAppCompatActivity() {

    private var preference: SharedPreferences? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        initView()
        initData()
    }

    @LayoutRes
    abstract fun getLayout() : Int

    abstract fun initView()

    abstract fun initData()

    fun getPreference() : SharedPreferences{
        if (preference == null) {
            preference = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        }
        return preference!!
    }

    fun setToolBar(toolbar: Toolbar, title: String) {
        toolbar.title = title + ""
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    fun setToolBarNoBack(toolbar: Toolbar, title: String) {
        toolbar.title = title + ""
        setSupportActionBar(toolbar)
    }

}