package com.fire.zhihudaily.ui.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.fire.zhihudaily.R
import com.fire.zhihudaily.R.layout
import com.fire.zhihudaily.adapter.BannerAdapter
import com.fire.zhihudaily.adapter.BannerAdapter.ClickBack
import com.fire.zhihudaily.adapter.LatestAdapter
import com.fire.zhihudaily.base.BaseFragment
import com.fire.zhihudaily.entity.LatestNews
import com.fire.zhihudaily.entity.Story
import com.fire.zhihudaily.entity.Top_story
import com.fire.zhihudaily.network.HttpClient
import com.fire.zhihudaily.rx.DataTransformer
import com.fire.zhihudaily.rx.DefaultButtonTransformer
import com.fire.zhihudaily.rx.OtherObser
import com.fire.zhihudaily.rx.RecyclerNetObser
import com.fire.zhihudaily.rx.RxViews
import com.fire.zhihudaily.rx.SchedulerTransformer
import com.fire.zhihudaily.ui.activity.ArticledetialActivity
import com.fire.zhihudaily.utils.DateUtils
import com.fire.zhihudaily.view.MyIconHint
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.jude.rollviewpager.RollPagerView
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.content_main.mRecyclerView
import retrofit2.Response
import io.reactivex.android.schedulers.AndroidSchedulers


/**
 * Created by fire on 2017/12/9.
 * Date：2017/12/9
 * Author: fire
 * Description:
 */
class HomeFragment : BaseFragment(), ClickBack {


    private var mAdapter: LatestAdapter? = null
    private var position: Int = 0
    private var mHeaderView: View? = null
    private var bannerAdapter: BannerAdapter? = null
    private var banner: RollPagerView? = null
    private var mTopStories: List<Top_story>? = null

    override fun getLayout(): Int {
        return R.layout.content_main
    }

    override fun initView() {
        mHeaderView = layoutInflater.inflate(R.layout.item_banner_header, mRecyclerView, false)
        mAdapter = LatestAdapter(context, HashMap<Int, String>())
        bannerAdapter = BannerAdapter(ArrayList(), ArrayList())
        bannerAdapter!!.clickBack = this
        mRecyclerView.setLayoutManager(LinearLayoutManager(activity))
        mRecyclerView.adapter = mAdapter
        mRecyclerView.setRefreshingColorResources(R.color.colorPrimaryDark,R.color.colorPrimary)
        mAdapter!!.setNoMore(layout.view_nomore)
        mAdapter!!.addHeader(object : RecyclerArrayAdapter.ItemView {
            override fun onBindView(headerView: View) {
                banner = headerView.findViewById<RollPagerView>(R.id.banner)
                banner!!.setHintView(MyIconHint(activity))
            }

            override fun onCreateView(parent: ViewGroup): View {
                return mHeaderView!!
            }
        })
    }

    override fun initData() {

        RxViews.setLoadMore(mAdapter!!)
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(object : OtherObser<Any>() {
                    override fun onNext(t: Any) {
                        loadData(false)
                    }
                })

        RxViews.onItemClick(mAdapter!!)
                .compose(DefaultButtonTransformer<Int>())
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(object : OtherObser<Int>() {
                    override fun onNext(it: Int) {
                        var intent = Intent(activity, ArticledetialActivity::class.java);
                        intent!!.putExtra("data", mAdapter!!.getItem(it))
                        startActivity(intent)
                    }
                })

        RxViews.adapterLoadMoreError(mAdapter!!)
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(object : OtherObser<Any>() {
                    override fun onNext(t: Any) {
                        loadData(false)
                    }
                })

        RxViews.EasyRecyclererrorViewClick(mRecyclerView!!)
                .compose(RxViews.transBOOL(true))
                .startWith(true)
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(object : OtherObser<Any>() {
                    override fun onNext(t: Any) {
                        loadData(true)
                    }
                })

        mRecyclerView.setRefreshListener {
            loadData(true)
        }
    }

    private fun loadData(isFirst: Boolean) {
        if (mAdapter!!.allData.size == 0) {
            mRecyclerView.showProgress()
        }
        var observable: Observable<Response<LatestNews>>? = null;
        if (isFirst) {
            position = 0
            observable = HttpClient.getInstance()
                    .service()
                    .latestNews()
        } else {
            observable = HttpClient.getInstance()
                    .service()
                    .beforeNews(DateUtils.formatDateBackDays(-position));
        }
        observable!!.compose(SchedulerTransformer<Response<LatestNews>>())
                .compose(DataTransformer<LatestNews>())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    mRecyclerView.showRecycler()
                    if (isFirst) {
                        bannerAdapter!!.imgs!!.clear()
                        bannerAdapter!!.contents!!.clear()
                        mAdapter!!.map!!.clear()
                        mTopStories = it.top_stories;
                        for (top_story in it.top_stories) {
                            bannerAdapter!!.imgs!!.add(top_story.image)
                            bannerAdapter!!.contents!!.add(top_story.title)
                        }
                        banner!!.setAdapter(bannerAdapter)
                    }
                    mAdapter!!.map!!.put(mAdapter!!.count + 1, it.date)
                    return@map it.stories
                }
                .subscribe(object : RecyclerNetObser<Story>(mRecyclerView) {
                    override fun onNext(it: List<Story>){
                        super.onNext(it)
                        if (isFirst) {
                            mAdapter!!.allData!!.clear()
                        }
                        mAdapter?.addAll(it)
                        position++
                    }
                })
    }

    override fun callBack(position: Int) {
        var intent = Intent(activity, ArticledetialActivity::class.java);
        val top_story = mTopStories!!.get(position)
        var list = ArrayList<String>()
        list.add(top_story.image)
        var story = Story(list,top_story.type,top_story.id,"","",top_story.ga_prefix,top_story.title)
        intent!!.putExtra("data", story)
        startActivity(intent)
    }
}