package com.fire.zhihudaily.utils

import java.text.ParseException
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Calendar


/**
 * Created by fire on 2017/12/8.
 * Date：2017/12/8
 * Author: fire
 * Description:
 */
object DateUtils {

    /**
     * yyyy-MM-dd
     */
    val dateFormat1 = "yyyy-MM-dd"
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    val dateFormat2 = "yyyy-MM-dd HH:mm:ss"
    /**
     * yyyy-MM-dd-HH-mm-ss
     */
    val dateFormat3 = "yyyy-MM-dd-HH-mm-ss"
    /**
     * yyyy/MM/dd HH:mm:ss
     */
    val dateFormat4 = "yyyy/MM/dd HH:mm:ss"
    /**
     * MM-dd,HH:mm
     */
    val dateFormat5 = "MM-dd,HH:mm"
    /**
     * yyyyMMddHHmmssSSS
     */
    val dateFormat6 = "yyyyMMddHHmmssSSS"
    /**
     * yyyyMMdd_HHmmss
     */
    val dateFormat7 = "yyyyMMdd_HHmmss"
    /**
     * yyyy-MM-dd HH:mm
     */
    val dateFormat8 = "yyyy-MM-dd HH:mm"
    /**
     * yyyy-MM-dd HH:mm
     */
    val dateFormat9 = "MM-dd HH:mm"
    /**
     * HHmmssSSS
     */
    val dateFormat10 = "ssSSS"
    /**
     * yyyyMMdd_HHmmssSSS
     */
    val dateFormat11 = "yyyyMMdd_HHmmssSSS"

    val dateFormat13 = "yyyyMMdd"
    /**
     * yyyy-MM-dd HH:mm
     */
    val dateFormat12 = "yyyy.MM.dd HH:mm"

    val dateFormate14 = "MM月dd日 EEEE"

    fun formatDateToString(date: Date, format: String): String {
        val dateFormat = SimpleDateFormat(format)
        return dateFormat.format(date)
    }

    fun formatDateToString(date: String, format: String): String {
        return formatDateToString(java.lang.Long.parseLong(date), format)
    }

    fun formatDateToString(date: Long, format: String): String {
        return formatDateToString(Date(date), format)
    }

    /**
     * 字符串转换成日期
     * @param str
     * @return date
     */
    fun parseToDate(str: String, format: String): Date? {

        val dateFormat = SimpleDateFormat(format)
        var date: Date? = null
        try {
            date = dateFormat.parse(str)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date
    }

    fun parseToString(str: String, format: String): String {
        val date = parseToDate(str, format)
        return if (date == null) "" else String.format("%d",date!!.getTime())
    }

    fun formatDateInWeek(date: String, format: String, format2: String): String {
        var dateStr = ""
        try {
            val formatter = SimpleDateFormat(format, Locale.CHINA)
            val pos = ParsePosition(0)
            val formatter2 = SimpleDateFormat(format2, Locale.CHINA)
            dateStr = formatter2.format(formatter.parse(date, pos))
        } catch (e: Exception) {
            dateStr = ""
        }

        return dateStr
    }

    fun formatDateBackDays(days: Int,format: String) : String {
        val sdf = SimpleDateFormat(format)
        var date = Date()
        val calendar = Calendar.getInstance()
        calendar.setTime(date)
        calendar.add(Calendar.DAY_OF_MONTH, days)
        date = calendar.getTime()
        return sdf.format(date)
    }

    fun formatDateBackDays(days: Int) : String{
        return formatDateBackDays(days, dateFormat13)
    }

    fun getFormatWeek(date: Date): String {
        return getFormatWeek(date,dateFormate14)
    }

    fun getFormatWeek(date: Date,format: String): String {
        val dateFormat = SimpleDateFormat("MM月dd日 EEEE")
        val format = dateFormat.format(date)
        return format + ""
    }

}