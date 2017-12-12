package com.fire.zhihudaily.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.fire.zhihudaily.R
import com.fire.zhihudaily.adapter.CommentAdapter
import com.fire.zhihudaily.base.BaseActivity
import com.fire.zhihudaily.entity.Comment
import com.fire.zhihudaily.entity.CommentsCount
import com.fire.zhihudaily.entity.ShortComments
import com.fire.zhihudaily.network.HttpClient
import com.fire.zhihudaily.rx.DataTransformer
import com.fire.zhihudaily.rx.RecyclerNetObser
import com.fire.zhihudaily.rx.SchedulerTransformer
import kotlinx.android.synthetic.main.activity_comment.toolbar
import kotlinx.android.synthetic.main.content_main.mRecyclerView
import retrofit2.Response

/**
 * Created by fire on 2017/12/11.
 * Date：2017/12/11
 * Author: fire
 * Description:
 */
class CommentActivity : BaseActivity(){
    private var commentCounts: CommentsCount? = null
    private var id = 0
    private var mAdapter: CommentAdapter? = null

    override fun getLayout(): Int {
        return R.layout.activity_comment
    }

    override fun initView() {
        commentCounts = intent.getSerializableExtra("data") as CommentsCount
        id = intent.getIntExtra("id", 0);
        if (null == commentCounts) {
            setToolBar(toolbar,"评论")
        } else{
            setToolBar(toolbar,String.format("%d 条评论",commentCounts!!.comments))
        }
        mRecyclerView.showEmpty()
        mAdapter = CommentAdapter(this)
        mRecyclerView.setLayoutManager(LinearLayoutManager(this))
        mRecyclerView.adapter = mAdapter
    }

    override fun initData() {
        if (commentCounts == null) {
            return
        }
        HttpClient.getInstance()
                .service()
                .getShortComments(String.format("%d",id))
                .compose(SchedulerTransformer<Response<ShortComments>>())
                .compose(DataTransformer<ShortComments>())
                .map {

                    return@map it.comments }
                .subscribe(object : RecyclerNetObser<Comment>(mRecyclerView){
                    override fun onNext(t: List<Comment>) {
                        super.onNext(t)
                        if (t == null || t.isEmpty()) {
                            if (mAdapter!!.allData.size == 0) {
                                mRecyclerView.showEmpty()
                            }
                        } else{
                            mRecyclerView.showRecycler()
                            mAdapter!!.addAll(t)
                        }
                    }
                })
        HttpClient.getInstance()
                .service()
                .getLongComments(String.format("%d",id))
                .compose(SchedulerTransformer<Response<ShortComments>>())
                .compose(DataTransformer<ShortComments>())
                .map { return@map it.comments }
                .subscribe(object : RecyclerNetObser<Comment>(mRecyclerView){
                    override fun onNext(t: List<Comment>) {
                        super.onNext(t)
                        if (t == null || t.isEmpty()) {
                            if (mAdapter!!.allData.size == 0) {
                                mRecyclerView.showEmpty()
                            }
                        } else{
                            mRecyclerView.showRecycler()
                            mAdapter!!.addAll(t)
                        }
                    }
                })
    }
}