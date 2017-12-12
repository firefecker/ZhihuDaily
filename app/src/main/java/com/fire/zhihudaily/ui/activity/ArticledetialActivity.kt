package com.fire.zhihudaily.ui.activity

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.fire.zhihudaily.R
import com.fire.zhihudaily.base.BaseActivity
import com.fire.zhihudaily.entity.NewsDetail
import com.fire.zhihudaily.network.HttpClient
import com.fire.zhihudaily.rx.DataTransformer
import com.fire.zhihudaily.rx.SchedulerTransformer
import com.fire.zhihudaily.utils.ImageLoader
import kotlinx.android.synthetic.main.activity_detail.img_bg
import kotlinx.android.synthetic.main.activity_detail.toolbar
import kotlinx.android.synthetic.main.activity_detail.tv_tag
import kotlinx.android.synthetic.main.activity_detail.tv_title
import kotlinx.android.synthetic.main.activity_detail.webView
import retrofit2.Response
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.fire.zhihudaily.R.id
import com.fire.zhihudaily.entity.CommentsCount
import com.fire.zhihudaily.entity.Story
import com.fire.zhihudaily.entity.Top_story
import com.fire.zhihudaily.rx.DefaultButtonTransformer
import com.fire.zhihudaily.rx.NetObser
import com.fire.zhihudaily.utils.DecimalUtils
import com.fire.zhihudaily.utils.FileUtils
import com.fire.zhihudaily.utils.IntentUtils
import com.jakewharton.rxbinding2.view.RxView
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_detail.img_avater
import kotlinx.android.synthetic.main.activity_detail.layout_avater
import kotlinx.android.synthetic.main.activity_detail.layout_header
import kotlinx.android.synthetic.main.activity_detail.tv_avater_title
import kotlinx.android.synthetic.main.activity_detail.tv_comments
import kotlinx.android.synthetic.main.activity_detail.tv_popularity

/**
 * Created by fire on 2017/12/9.
 * Date：2017/12/9
 * Author: fire
 * Description:
 */
class ArticledetialActivity : BaseActivity() {

    var story: Story? = null
    var topStory: Top_story? = null

    private var newsDetail: NewsDetail? = null
    private var commentsCount: CommentsCount? = null

    override fun getLayout(): Int {
        return R.layout.activity_detail
    }

    override fun initView() {
        setToolBar(toolbar, "")
        val webSettings = webView.settings
        webView.isVerticalScrollBarEnabled = false;
        webView.isHorizontalScrollBarEnabled = false;
        webSettings.javaScriptEnabled = true

        // init webview settings
        webSettings.allowContentAccess = true
        webSettings.databaseEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.setAppCacheEnabled(true)
        webSettings.savePassword = false
        webSettings.saveFormData = false
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                val uri = Uri.parse(url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                if (newsDetail == null) {
                    return
                }
                if (newsDetail!!.section != null) {
                    layout_avater.visibility = View.VISIBLE
                    ImageLoader.loadImage(newsDetail!!.section.thumbnail,R.color.default1)
                            .centerCrop()
                            .into(img_avater)
                    tv_avater_title.text = String.format("本文来自：%s· 合集", newsDetail!!.section.name)
                } else {
                    layout_avater.visibility = View.GONE
                }
            }
        }

    }

    override fun initData() {
        val serializable = intent.getSerializableExtra("data")
        if (serializable is Story) {
            story = serializable
        } else{
            topStory = serializable as Top_story
        }
        RxView.clicks(layout_avater)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(DefaultButtonTransformer())
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe({
                    if (newsDetail == null) {
                        return@subscribe
                    }
                    var intent = Intent(this@ArticledetialActivity, SectionActivity::class.java)
                    intent.putExtra("data", newsDetail!!.section)
                    startActivity(intent)
                })

        RxView.clicks(tv_comments)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(DefaultButtonTransformer())
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe({
                    if (commentsCount == null) {
                        return@subscribe
                    }
                    var intent = Intent(this@ArticledetialActivity, CommentActivity::class.java)
                    intent.putExtra("data", commentsCount)
                    if (story == null) {
                        intent.putExtra("id", topStory!!.id)
                    } else{
                        intent.putExtra("id", story!!.id)
                    }
                    startActivity(intent)
                })

        HttpClient.getInstance()
                .service()
                .newsDetail(String.format("%d", story!!.id))
                .compose(SchedulerTransformer<Response<NewsDetail>>())
                .compose(DataTransformer<NewsDetail>())
                .subscribe(object : NetObser<NewsDetail>() {
                    override fun onNext(t: NewsDetail) {
                        setData(t)
                    }
                })

        HttpClient.getInstance()
                .service()
                .getCommetsCount(String.format("%d", story!!.id))
                .compose(SchedulerTransformer<Response<CommentsCount>>())
                .compose(DataTransformer<CommentsCount>())
                .subscribe(object : NetObser<CommentsCount>() {
                    override fun onNext(it: CommentsCount) {
                        commentsCount = it
                        tv_comments.text = String.format("%d", it.comments)
                        if (it.popularity < 1000) {
                            tv_popularity.text = String.format("%d", it.popularity)
                        } else {
                            tv_popularity.text = String.format("%sk", "" + DecimalUtils.setScale2Double(it.popularity / 1000.0,3))
                        }
                    }
                })

    }

    private fun setData(it: NewsDetail) {
        newsDetail = it;
        if (!TextUtils.isEmpty(it.image)) {
            layout_header.visibility = View.VISIBLE
            ImageLoader.loadImage(it.image,R.color.colordefault)
                    .centerCrop()
                    .into(img_bg)
            tv_title.text = it.title
            tv_tag.text = it.image_source
        } else {
            layout_header.visibility = View.GONE
        }
        val assetsTxt = FileUtils.readAssetsTxt(applicationContext, "article.html")
        val replace = assetsTxt.replace("webview_content", it.body)
        webView.loadData(replace, "text/html; charset=UTF-8", null)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.article_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            id.action_share -> {
                val intent = IntentUtils.openSystemShare(
                        String.format("标题：%s\n地址：%s，来自：", newsDetail!!.title,
                                newsDetail!!.share_url, getString(R.string.app_name)))
                startActivity(intent)
                return true
            }
            id.action_collection -> {
                Toast.makeText(this,"收藏",Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}