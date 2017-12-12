package com.fire.zhihudaily.adapter

import android.content.Context
import android.view.ViewGroup
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
class EditorAdapter : EasyRecyclerAdapter<Editor>{

    constructor(context: Context?) : super(context)


    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Editor> {
        return EditorViewHolder(parent)
    }

    class EditorViewHolder : BaseViewHolder<Editor> {
        private var mImgOne: CircleImageView? = null

        constructor(parent: ViewGroup) : super(parent, R.layout.item_editor) {
            mImgOne = itemView.findViewById<CircleImageView>(R.id.img1)
        }

        override fun setData(data: Editor) {
            super.setData(data)
            ImageLoader.loadImage(data.avatar,R.color.default1)
                    .into(mImgOne)
        }
    }
}