package com.fire.zhihudaily.utils

import android.content.Intent
import com.fire.zhihudaily.R.string

/**
 * Created by fire on 2017/12/11.
 * Date：2017/12/11
 * Author: fire
 * Description:
 */
object IntentUtils {

    fun openSystemShare (content : String) : Intent{
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, content)
        intent.type = "text/plain"
        return Intent.createChooser(intent, "分享到")
    }

}