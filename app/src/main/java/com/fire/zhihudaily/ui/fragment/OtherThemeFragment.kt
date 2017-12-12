package com.fire.zhihudaily.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.fire.zhihudaily.R
import com.fire.zhihudaily.R.layout
import com.fire.zhihudaily.adapter.EditorAdapter
import com.fire.zhihudaily.adapter.LatestAdapter
import com.fire.zhihudaily.base.BaseFragment
import com.fire.zhihudaily.entity.Editor
import com.fire.zhihudaily.entity.InternetNews
import com.fire.zhihudaily.entity.Story
import com.fire.zhihudaily.network.HttpClient
import com.fire.zhihudaily.rx.DataTransformer
import com.fire.zhihudaily.rx.DefaultButtonTransformer
import com.fire.zhihudaily.rx.OtherObser
import com.fire.zhihudaily.rx.RecyclerNetObser
import com.fire.zhihudaily.rx.RxViews
import com.fire.zhihudaily.rx.SchedulerTransformer
import com.fire.zhihudaily.ui.activity.ArticledetialActivity
import com.fire.zhihudaily.ui.activity.EditorActivity
import com.fire.zhihudaily.utils.ImageLoader
import com.jakewharton.rxbinding2.view.RxView
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.content_main.mRecyclerView
import retrofit2.Response

/**
 * Created by fire on 2017/12/9.
 * Date：2017/12/9
 * Author: fire
 * Description:
 */
class OtherThemeFragment : BaseFragment() {

    private var item : String? = null
    private var mHeaderView: View? = null
    private var mImgBg: ImageView? = null
    private var mTvContent: TextView? = null
    private var mRecyclerViewEditor: RecyclerView? = null
    private var mEditorAdapter: EditorAdapter? = null
    private var mLayoutEditor: LinearLayout? = null
    private var editors: List<Editor>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            item = args.getString("item")
        }
    }

    private var mAdapter : LatestAdapter? = null

    override fun getLayout(): Int {
        return R.layout.content_main
    }

    override fun initView() {

        mHeaderView = layoutInflater.inflate(R.layout.item_other_header,mRecyclerView,false)
        mAdapter = LatestAdapter(activity, HashMap<Int,String>())
        mEditorAdapter = EditorAdapter(context)
        mRecyclerView.setLayoutManager(LinearLayoutManager(activity))
        mRecyclerView.adapter = mAdapter
        mAdapter!!.setNoMore(layout.view_nomore)

        mAdapter!!.addHeader(object : RecyclerArrayAdapter.ItemView {
            override fun onBindView(headerView: View) {
                mImgBg = headerView.findViewById<ImageView>(R.id.img_bg)
                mTvContent = headerView.findViewById<TextView>(R.id.tv_content)
                mLayoutEditor = headerView.findViewById<LinearLayout>(R.id.layout_editor)
                mRecyclerViewEditor = headerView.findViewById<RecyclerView>(R.id.mRecyclerViewEditor)
                mRecyclerViewEditor!!.adapter = mEditorAdapter
                RxView.clicks(this@OtherThemeFragment?.mLayoutEditor!!)
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(this@OtherThemeFragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                        .subscribe({
                            var intent = Intent(activity, EditorActivity::class.java);
                            intent!!.putExtra("data", ArrayList(editors))
                            startActivity(intent)
                        })
            }

            override fun onCreateView(parent: ViewGroup?): View {
                return mHeaderView!!
            }
        })
    }

    override fun initData() {
        RxViews.onItemClick(mAdapter!!)
                .compose(DefaultButtonTransformer<Int>())
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(object : OtherObser<Int>() {
                    override fun onNext(it: Int) {
                        var intent = Intent(activity,ArticledetialActivity::class.java);
                        intent!!.putExtra("data", mAdapter!!.getItem(it))
                        startActivity(intent)
                    }
                })

        RxViews.onItemClick(mEditorAdapter!!)
                .compose(DefaultButtonTransformer<Int>())
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(object : OtherObser<Int>() {
                    override fun onNext(it: Int) {
                        var intent = Intent(activity, EditorActivity::class.java);
                        intent!!.putExtra("data", ArrayList(editors))
                        startActivity(intent)
                    }
                })

        RxViews.adapterLoadMoreError(mAdapter!!)
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(object : OtherObser<Any>() {
                    override fun onNext(t: Any) {
                        loadData(item!!)
                    }
                })

        RxViews.EasyRecyclererrorViewClick(mRecyclerView!!)
                .compose(RxViews.transBOOL(true))
                .startWith(true)
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(object : OtherObser<Any>() {
                    override fun onNext(t: Any) {
                        loadData(item!!)
                    }
                })

        mRecyclerView.setRefreshListener {
            loadData(item!!)
        }
    }

    private fun loadData(item : String) {
        if (mAdapter!!.allData.size == 0) {
            mRecyclerView.showProgress()
        }
        HttpClient.getInstance()
                .service()
                .otherNews(item)
                .compose(SchedulerTransformer<Response<InternetNews>>())
                .compose(DataTransformer<InternetNews>())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    mRecyclerView.showRecycler()
                    ImageLoader.loadImage(it.background,R.color.colordefault)
                            .centerCrop()
                            .into(mImgBg)
                    mTvContent!!.text = it.description
                    editors = it.editors
                    if (editors != null || editors!!.size != 0) {
                        mEditorAdapter!!.clear()
                        editors;
                        mEditorAdapter!!.addAll(editors)
                    }
                    return@map it.stories
                }
                .subscribe(object : RecyclerNetObser<Story>(mRecyclerView) {
                    override fun onNext(it: List<Story>){
                        super.onNext(it)
                        mAdapter!!.addAll(it)
                    }
                })
    }

    object Inner {
        fun newInstance(item: String): OtherThemeFragment {
            val fragment = OtherThemeFragment()
            val args = Bundle()
            args.putString("item", item)
            fragment.arguments = args
            return fragment
        }
    }
}