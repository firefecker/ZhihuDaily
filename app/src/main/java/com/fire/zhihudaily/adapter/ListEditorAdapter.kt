package com.fire.zhihudaily.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import com.fire.zhihudaily.R
import com.fire.zhihudaily.entity.Editor
import com.fire.zhihudaily.utils.ImageLoader
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by fire on 2017/12/9.
 * Date：2017/12/9
 * Author: fire
 * Description:
 */
class ListEditorAdapter : EasyRecyclerAdapter<Editor>{

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return EditorViewHolder(parent)
    }

    constructor(context: Context) : super(context)

    class EditorViewHolder : BaseViewHolder<Editor> {
        private var mImgOne: CircleImageView? = null
        private var mTvName: TextView? = null
        private var mTvContent: TextView? = null

        constructor(parent: ViewGroup) : super(parent, R.layout.item_editor1) {
            mImgOne = itemView.findViewById<CircleImageView>(R.id.img1)
            mTvName = itemView.findViewById<TextView>(R.id.tv_name)
            mTvContent = itemView.findViewById<TextView>(R.id.tv_bio)
        }

        override fun setData(data: Editor) {
            super.setData(data)
            if (mImgOne != null) {
                ImageLoader.loadImage(data?.avatar,R.color.default1)
                        .into(mImgOne)
            }
            mTvName?.text = data?.name
            mTvContent?.text = data?.bio
        }
    }
}