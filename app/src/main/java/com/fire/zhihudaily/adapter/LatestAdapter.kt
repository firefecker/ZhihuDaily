package com.fire.zhihudaily.adapter

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.fire.zhihudaily.R
import com.fire.zhihudaily.R.string
import com.fire.zhihudaily.entity.Story
import com.fire.zhihudaily.utils.DateUtils
import com.fire.zhihudaily.utils.ImageLoader
import com.jude.easyrecyclerview.adapter.BaseViewHolder

/**
 * Created by fire on 2017/12/8.
 * Date：2017/12/8
 * Author: fire
 * Description:
 */
class LatestAdapter : EasyRecyclerAdapter<Story> {

    var map : HashMap<Int,String>? = null

    constructor(context: Context,map: HashMap<Int,String>) : super(context) {
        this.map = map;
    }

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return LatestViewHolder(parent,map)
    }

    class LatestViewHolder : BaseViewHolder<Story> {

        var mTvContent : TextView? = null
        var mTvType : TextView? = null
        var mTvTime : TextView? = null
        var mImgPic : ImageView? = null
        var map : HashMap<Int,String>? = null

        constructor(parent: ViewGroup, map: HashMap<Int,String>?) : super(parent, R.layout.item_latest) {
            this.map = map
            mTvContent = itemView.findViewById<TextView>(R.id.tv_content)
            mTvType = itemView.findViewById<TextView>(R.id.tv_type)
            mTvTime = itemView.findViewById<TextView>(R.id.tv_time)
            mImgPic = itemView.findViewById<ImageView>(R.id.img_pic)
        }

        override fun setData(data: Story) {
            super.setData(data)
            if (map!!.contains(position)) {
                mTvType!!.visibility = View.VISIBLE
                if (position == 1) {
                    mTvType!!.text = context.getString(string.latestnews)
                } else {
                    mTvType!!.text = DateUtils.getFormatWeek(DateUtils.parseToDate(map!!.get(position)!!,DateUtils.dateFormat13)!!)
                }
            } else{
                mTvType!!.visibility = View.GONE
            }
            if (data.images == null || data.images.size == 0) {
                mImgPic!!.visibility = View.GONE
            } else{
                mImgPic!!.visibility = View.VISIBLE
                ImageLoader.loadImage(data.images.get(0),R.color.default1).centerCrop().into(mImgPic)
            }
            if (TextUtils.isEmpty(data.display_date)) {
                mTvTime?.visibility = View.GONE
            } else{
                mTvTime?.visibility = View.VISIBLE
                mTvTime?.text = data.display_date
            }
            mTvContent?.text = data.title
        }
    }
}