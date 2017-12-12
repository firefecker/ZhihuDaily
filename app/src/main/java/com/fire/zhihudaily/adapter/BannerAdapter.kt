package com.fire.zhihudaily.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.jude.rollviewpager.adapter.StaticPagerAdapter
import android.widget.ImageView.ScaleType
import android.widget.TextView
import com.fire.zhihudaily.R
import com.fire.zhihudaily.utils.ImageLoader


/**
 * Created by fire on 2017/12/9.
 * Date：2017/12/9
 * Author: fire
 * Description:
 */
class BannerAdapter : StaticPagerAdapter {
    var clickBack: ClickBack? = null

    constructor(list: ArrayList<String>,list1: ArrayList<String>) {
        this.imgs = list
        this.contents = list1
    }



    var imgs : ArrayList<String> ? = null
    var contents : ArrayList<String> ? = null
    override fun getCount(): Int {
        return imgs!!.size
    }

    override fun getView(container: ViewGroup, position: Int): View {

        var inflate = LayoutInflater.from(container!!.context).inflate(R.layout.item_banner,
                container, false);
        var mImageView = inflate.findViewById<ImageView>(R.id.imageView)
        var mTvContent = inflate.findViewById<TextView>(R.id.tv_content)
        mTvContent.text = contents!!.get(position)
        ImageLoader.loadImage(imgs!!.get(position),R.color.colordefault)
                .into(mImageView)

        inflate.setOnClickListener {
            clickBack?.callBack(position)
        }
        return inflate
    }

    interface ClickBack {
        fun callBack(position: Int)
    }
}