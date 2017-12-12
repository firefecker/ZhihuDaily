package com.fire.zhihudaily.view

import android.content.Context
import com.fire.zhihudaily.R
import com.jude.rollviewpager.Util
import com.jude.rollviewpager.hintview.IconHintView

/**
 * Created by fire on 2017/12/9.
 * Date：2017/12/9
 * Author: fire
 * Description:
 */
class MyIconHint : IconHintView {

    constructor(context: Context?, focusResId: Int, normalResId: Int) : super(context, focusResId,
            normalResId)

    constructor(context: Context?) : super(context,
            R.drawable.selected_dot, R.drawable.no_selected_dot, Util.dip2px(context, 22f))
}