package com.tuyrt.myarch.logic.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stx.xhb.androidx.entity.SimpleBannerInfo

/*
    "desc": "一起来做个App吧",
    "id": 10,
    "imagePath": "https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png",
    "isVisible": 1,
    "order": 1,
    "title": "一起来做个App吧",
    "type": 0,
    "url": "https://www.wanandroid.com/blog/show/2"
 */
@Entity(tableName = "banner")
data class BannerBean(
    @PrimaryKey
    val id: Int,
    val desc: String,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
) : SimpleBannerInfo() {
    override fun getXBannerUrl(): Any {
        return imagePath
    }
}