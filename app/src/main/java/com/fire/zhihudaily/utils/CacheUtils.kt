package com.fire.zhihudaily.utils

import android.content.Context
import android.os.Environment
import java.io.File
import java.math.BigDecimal


/**
 * Created by fire on 2017/12/11.
 * Date：2017/12/11
 * Author: fire
 * Description:
 */
object CacheUtils {

    fun getTotalCacheSize(context: Context) : String{
        var cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize * 1.0);
    }

    private fun deleteDir(dir: File?): Boolean {
        if (dir != null && dir!!.isDirectory()) {
            val children = dir!!.list()
            var size = 0
            if (children != null) {
                size = children!!.size
                for (i in 0 until size) {
                    val success = deleteDir(File(dir, children!![i]))
                    if (!success) {
                        return false
                    }
                }
            }
        }
        return if (dir == null) {
            true
        } else {
            dir!!.delete()
        }
    }

    fun clearAllCache(context: Context) {
        deleteDir(context.cacheDir)
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            deleteDir(context.externalCacheDir)
        }
    }

    @Throws(Exception::class)
    fun getFolderSize(file: File): Long {
        var size: Long = 0
        try {
            val fileList = file.listFiles()
            var size2 = 0
            if (fileList != null) {
                size2 = fileList.size
                for (i in 0 until size2) {
                    if (fileList[i].isDirectory) {
                        size += getFolderSize(fileList[i])
                    } else {
                        size += fileList[i].length()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

    /**
     * 格式化单位
     * 计算缓存的大小
     * @param size
     * @return
     */
    fun getFormatSize(size: Double): String {
        val kiloByte = size / 1024
        if (kiloByte < 1) {
            return "0K"
        }
        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            val result1 = BigDecimal(java.lang.Double.toString(kiloByte))
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB"
        }
        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            val result2 = BigDecimal(java.lang.Double.toString(megaByte))
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB"
        }
        val teraBytes = gigaByte / 1024
        if (teraBytes < 1) {
            val result3 = BigDecimal(java.lang.Double.toString(gigaByte))
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB"
        }
        val result4 = BigDecimal(teraBytes)
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
    }

}