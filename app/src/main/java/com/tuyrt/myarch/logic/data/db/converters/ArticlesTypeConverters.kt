package com.tuyrt.myarch.logic.data.db.converters

import androidx.room.TypeConverter
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.reflect.TypeToken
import com.tuyrt.myarch.logic.data.entity.ArticlesBean

/**
 * Created by tuyrt7 on 2021/12/16.
 * 说明：
 */
class ArticlesTypeConverters {

    @TypeConverter
    fun stringToArticles(json: String): List<ArticlesBean> {
        val type = object : TypeToken<List<ArticlesBean>>() {}.type
        return GsonUtils.fromJson(json, type)
    }

    @TypeConverter
    fun articlesToString(data: List<ArticlesBean>): String {
        return GsonUtils.toJson(data)
    }
}