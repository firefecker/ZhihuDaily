package com.fire.zhihudaily.utils

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset

/**
 * Created by fire on 2017/12/9.
 * Date：2017/12/9
 * Author: fire
 * Description:
 */
object FileUtils {

    fun readAssetsTxt(context: Context, fileName: String): String {
        try {
            val `is` = context.assets.open(fileName)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            return String(buffer, Charset.forName("utf-8"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }
}