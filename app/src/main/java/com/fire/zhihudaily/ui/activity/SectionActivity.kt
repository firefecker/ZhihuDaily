package com.fire.zhihudaily.ui.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.fire.zhihudaily.R
import com.fire.zhihudaily.R.layout
import com.fire.zhihudaily.adapter.LatestAdapter
import com.fire.zhihudaily.base.BaseActivity
import com.fire.zhihudaily.entity.LisSections
import com.fire.zhihudaily.entity.Section
import com.fire.zhihudaily.entity.Story
import com.fire.zhihudaily.network.HttpClient
import com.fire.zhihudaily.rx.DataTransformer
import com.fire.zhihudaily.rx.DefaultButtonTransformer
import com.fire.zhihudaily.rx.OtherObser
import com.fire.zhihudaily.rx.RecyclerNetObser
import com.fire.zhihudaily.rx.RxViews
import com.fire.zhihudaily.rx.SchedulerTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import kotlinx.android.synthetic.main.content_editor.toolbar
import retrofit2.Response
import java.util.Date
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.content_main.mRecyclerView


/**
 * Created by fire on 2017/12/10.
 * Date：2017/12/10
 * Author: fire
 * Description:
 */
class SectionActivity : BaseActivity(){

    private var mAdapter: LatestAdapter? = null
    private var section: Section? = null
    private var timestamp: String? = ""

    override fun getLayout(): Int {
        return R.layout.content_editor
    }

    override fun initView() {
        section = intent.getSerializableExtra("data") as Section?
        if (section == null) {
            setToolBar(toolbar,"合集")
        }
        setToolBar(toolbar,section!!.name)

        mAdapter = LatestAdapter(this, HashMap<Int, String>())
        mRecyclerView.setLayoutManager(LinearLayoutManager(this))
        mRecyclerView.adapter = mAdapter
        mRecyclerView.setRefreshingColorResources(R.color.colorPrimaryDark,R.color.colorPrimary)
        mAdapter!!.setNoMore(layout.view_nomore)
    }

    override fun initData() {

        RxViews.setLoadMore(mAdapter!!)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(object : OtherObser<Any>() {
                    override fun onNext(t: Any) {
                        setData(false)
                    }
                })

        RxViews.onItemClick(mAdapter!!)
                .compose(DefaultButtonTransformer<Int>())
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(object : OtherObser<Int>() {
                    override fun onNext(it: Int) {
                        var intent = Intent(this@SectionActivity, ArticledetialActivity::class.java);
                        intent!!.putExtra("data", mAdapter!!.getItem(it))
                        startActivity(intent)
                    }
                })

        RxViews.adapterLoadMoreError(mAdapter!!)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(object : OtherObser<Any>() {
                    override fun onNext(t: Any) {
                        setData(false)
                    }
                })

        RxViews.EasyRecyclererrorViewClick(mRecyclerView!!)
                .compose(RxViews.transBOOL(true))
                .startWith(true)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(object : OtherObser<Any>() {
                    override fun onNext(t: Any) {
                        setData(true)
                    }
                })

        mRecyclerView.setRefreshListener {
            setData(true)
        }
    }

    private fun setData(isFirst: Boolean) {
        var time = ""
        if (isFirst) {
            time = String.format("%d",Date().time / 1000)
        } else{
            time = timestamp!!
        }
        HttpClient.getInstance()
                .service()
                .getSection(String.format("%d", section!!.id), time)
                .compose(SchedulerTransformer<Response<LisSections>>())
                .compose(DataTransformer<LisSections>())
                .observeOn(AndroidSchedulers.mainThread())
                .map({
                    timestamp = String.format("%d",it.timestamp)
                    return@map it.stories
                })
                .subscribe(object : RecyclerNetObser<Story>(mRecyclerView) {
                    override fun onNext(it: List<Story>){
                        super.onNext(it)
                        if (isFirst) {
                            mAdapter!!.allData!!.clear()
                        }
                        mAdapter!!.addAll(it)
                    }
                })
    }
}