package com.fire.zhihudaily.ui.activity

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import com.fire.zhihudaily.R
import com.fire.zhihudaily.R.layout
import com.fire.zhihudaily.adapter.ListEditorAdapter
import com.fire.zhihudaily.base.BaseActivity
import com.fire.zhihudaily.entity.Editor
import com.fire.zhihudaily.rx.DefaultButtonTransformer
import com.fire.zhihudaily.rx.OtherObser
import com.fire.zhihudaily.rx.RxViews
import com.trello.rxlifecycle2.android.ActivityEvent
import kotlinx.android.synthetic.main.content_editor.toolbar
import kotlinx.android.synthetic.main.content_main.mRecyclerView

/**
 * Created by fire on 2017/12/9.
 * Date：2017/12/9
 * Author: fire
 * Description:
 */
class EditorActivity : BaseActivity(){

    private var mAdapter: ListEditorAdapter? = null
    private var data: ArrayList<Editor>? = null

    override fun getLayout(): Int {
        return R.layout.content_editor
    }

    override fun initView() {
        setToolBar(toolbar,"主编")
        mAdapter = ListEditorAdapter(this)
        mRecyclerView.setLayoutManager(LinearLayoutManager(this))
        mRecyclerView.adapter = mAdapter
        mAdapter!!.setNoMore(layout.view_nomore)

        RxViews.onItemClick(mAdapter!!)
                .compose(DefaultButtonTransformer<Int>())
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(object : OtherObser<Int>() {
                    override fun onNext(it: Int) {
                        val uri = Uri.parse(mAdapter!!.getItem(it).url)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    }
                })
    }

    override fun initData() {
        data = intent.getSerializableExtra("data") as ArrayList<Editor>?
        if (data != null) {
            mAdapter!!.addAll(data)
        }
    }
}