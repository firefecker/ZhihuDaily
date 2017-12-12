package com.fire.zhihudaily.utils

import java.math.BigDecimal

/**
 * Created by fire on 2017/12/11.
 * Date：2017/12/11
 * Author: fire
 * Description:
 */
object DecimalUtils {
    /**
     * 默认两位小数
     * @param value
     * @return  返回值为double
     */
    fun setScaleDefaultDouble(value: Double): Double {
        return setScale2Double(value, 2)
    }

    /**
     * 默认两位小数
     * @param value
     * @return  返回值为double
     */
    fun setScaleDefaultDouble(value: String): Double {
        return setScale2Double(value, 2)
    }

    /**
     * 默认两位小数
     * @param value
     * @return  返回值为double
     */
    fun setScaleDefaultDouble(value: Int): Double {
        return setScale2Double(value, 2)
    }

    /**
     * 默认两位小数
     * @param value
     * @return  返回值为double
     */
    fun setScaleDefaultDouble(value: Float): Double {
        return setScale2Double(value, 2)
    }

    /**
     * 默认两位小数
     * @param value
     * @return  返回值为String
     */
    fun setScaleDefaultString(value: Double): String {
        return setScale2toString(value, 2)
    }

    /**
     * 默认两位小数
     * @param value
     * @return  返回值为String
     */
    fun setScaleDefaultString(value: String): String {
        return setScale2toString(value, 2)
    }

    /**
     * 默认两位小数
     * @param value
     * @return  返回值为String
     */
    fun setScaleDefaultString(value: Int): String {
        return setScale2toString(value, 2)
    }

    /**
     * 默认两位小数
     * @param value
     * @return  返回值为String
     */
    fun setScaleDefaultString(value: Float): String {
        return setScale2toString(value, 2)
    }

    /**
     *
     * @param value 原值
     * @param count 保留的小数个数
     * @return  返回值为double
     */
    fun setScale2Double(value: Float, count: Int): Double {
        val bigDecimal = BigDecimal(value as Double)
        return bigDecimal.setScale(count, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    /**
     *
     * @param value 原值
     * @param count 保留的小数个数
     * @return  返回值为double
     */
    fun setScale2Double(value: String, count: Int): Double {
        val bigDecimal = BigDecimal(value)
        return bigDecimal.setScale(count, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    /**
     *
     * @param value 原值
     * @param count 保留的小数个数
     * @return  返回值为double
     */
    fun setScale2Double(value: Int, count: Int): Double {
        val bigDecimal = BigDecimal(value)
        return bigDecimal.setScale(count, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    /**
     *
     * @param value 原值
     * @param count 保留的小数个数
     * @return  返回值为double
     */
    fun setScale2Double(value: Double, count: Int): Double {
        val bigDecimal = BigDecimal(value)
        return bigDecimal.setScale(count, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    /**
     *
     * @param value 原值
     * @param count 保留的小数个数
     * @return  返回值为String
     */
    fun setScale2toString(value: Double, count: Int): String {
        return setScale2Double(value, count).toString() + ""
    }

    /**
     *
     * @param value 原值
     * @param count 保留的小数个数
     * @return  返回值为String
     */
    fun setScale2toString(value: String, count: Int): String {
        return setScale2Double(value, count).toString() + ""
    }

    /**
     *
     * @param value 原值
     * @param count 保留的小数个数
     * @return  返回值为String
     */
    fun setScale2toString(value: Int, count: Int): String {
        return setScale2Double(value, count).toString() + ""
    }

    /**
     *
     * @param value 原值
     * @param count 保留的小数个数
     * @return  返回值为String
     */
    fun setScale2toString(value: Float, count: Int): String {
        return setScale2Double(value, count).toString() + ""
    }

}