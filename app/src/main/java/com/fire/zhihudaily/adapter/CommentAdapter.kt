package com.fire.zhihudaily.adapter

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fire.zhihudaily.R
import com.fire.zhihudaily.entity.Comment
import com.fire.zhihudaily.utils.DateUtils
import com.fire.zhihudaily.utils.ImageLoader
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import de.hdodenhof.circleimageview.CircleImageView
import android.graphics.Typeface
import android.text.style.StyleSpan



/**
 * Created by fire on 2017/12/11.
 * Date：2017/12/11
 * Author: fire
 * Description:
 */
class CommentAdapter : EasyRecyclerAdapter<Comment>{

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return CommentViewHolder(parent)
    }

    constructor(context: Context) : super(context) {

    }

    class CommentViewHolder : BaseViewHolder<Comment> {
        private var imgPic: CircleImageView? = null
        private var tvName: TextView? = null
        private var tvContent: TextView? = null
        private var tvThumb: TextView? = null
        private var tvReply: TextView? = null
        private var tvTime: TextView? = null

        constructor(parent: ViewGroup) : super(parent, R.layout.item_comment) {
            imgPic = itemView.findViewById<CircleImageView>(R.id.img_pic)
            tvName = itemView.findViewById<TextView>(R.id.tv_name)
            tvContent = itemView.findViewById<TextView>(R.id.tv_content)
            tvThumb = itemView.findViewById<TextView>(R.id.tv_thumb)
            tvReply = itemView.findViewById<TextView>(R.id.tv_reply)
            tvTime = itemView.findViewById<TextView>(R.id.tv_time)
        }

        override fun setData(data: Comment) {
            super.setData(data)
            ImageLoader.loadImage(data.avatar,R.color.colordefault).centerCrop().into(imgPic)
            tvContent!!.text =data.content
            tvName!!.text = data.author

            if (data.reply_to == null) {
                tvReply!!.visibility = View.GONE
            } else {
                tvReply!!.visibility = View.VISIBLE
                var spannableString = SpannableString(data.reply_to.author + "：" +data.reply_to.content)
                val styleSpan_B = StyleSpan(Typeface.BOLD)
                var colorSpan = ForegroundColorSpan(context.resources.getColor(R.color.text_color1));
                spannableString.setSpan(styleSpan_B, 0, data.reply_to.author.length + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(colorSpan, 0, data.reply_to.author.length + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                tvReply!!.text = spannableString
            }
            tvTime!!.text = DateUtils.formatDateBackDays(data.time,DateUtils.dateFormat9)
        }
    }
}