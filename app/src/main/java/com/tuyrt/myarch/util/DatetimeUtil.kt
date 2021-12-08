package com.tuyrt.myarch.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by tuyrt7 on 2021/12/7.
 * 说明：时间工具类
 */
object DatetimeUtil {

    private const val DATE_PATTERN = "yyyy-MM-dd"
    private const val DATE_PATTERN_MM = "yyyy-MM-dd HH:mm"
    private const val DATE_PATTERN_SS = "yyyy-MM-dd HH:mm:ss"

    /**
     *  获取现在时刻 ： 精确到 秒
     */
    fun nowTimeStr(): String {
        return formatDate(Date(), DATE_PATTERN_SS)
    }

    /**
     * 时间 转 字符串
     */
    fun formatDate(date: Date?, formatStyle: String): String {
        return if (date != null) {
            val sdf = SimpleDateFormat(formatStyle, Locale.CHINA)
            sdf.format(date)
        } else {
            ""
        }
    }

    /**
     *  时间戳 转 字符串
     */
    fun formatDate(date: Long, formatStyle: String): String {
        val sdf = SimpleDateFormat(formatStyle, Locale.CHINA)
        return sdf.format(Date(date))
    }


    /**
     *  字符串转 date
     */
    fun stringToDate(dateStr: String, formatStyle: String = DATE_PATTERN_SS): Date? {
        val sdf = SimpleDateFormat(formatStyle, Locale.CHINA)
        return try {
            sdf.parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    /**
     *  字符串转 时间戳 Long
     */
    fun stringToTimeMs(dateStr: String, formatStyle: String = DATE_PATTERN_SS): Long {
        val sdf = SimpleDateFormat(formatStyle, Locale.CHINA)
        return try {
            sdf.parse(dateStr).time
        } catch (e: ParseException) {
            e.printStackTrace()
            0L
        }
    }

    /**
     *  自定义 转化 时间字符串
     */
    fun formatTimeStr(time: Long): String {
        var tempTime = time
        val timeInMillis = Calendar.getInstance().timeInMillis

        //兼容脏数据。抓取的数据有些帖子的时间戳不是标准的十三位
        val valueOf = tempTime.toString()
        if (valueOf.length < 13) {
            tempTime *= 1000
        }
        val diff = (timeInMillis - tempTime) / 1000
        return when {
            diff <= 5 -> "刚刚"
            diff < 60 -> "${diff}秒前"
            diff < 3600 -> "${diff / 60}分钟前"
            diff < 3600 * 24 -> "${diff / 3600}小时前"
            else -> "${diff / (3600 * 24)}天前"
        }
    }
}
