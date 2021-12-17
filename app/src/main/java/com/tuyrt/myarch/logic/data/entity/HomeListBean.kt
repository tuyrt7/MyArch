package com.tuyrt.myarch.logic.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tuyrt.myarch.logic.data.db.converters.ArticlesTypeConverters

/**
 * Created by tuyrt7 on 2021/12/16.
 * 说明：
 */
@Entity(tableName = "home_data")
@TypeConverters(ArticlesTypeConverters::class)
data class HomeListBean(
    @PrimaryKey
    val curPage: Int,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val datas: MutableList<ArticlesBean>
)
