package com.tuyrt.myarch.logic.data.entity


data class ArticlesBean(
/*
    "apkLink": "",
    "audit": 1,
    "author": "panpf",
    "canEdit": false,
    "chapterId": 539,
    "chapterName": "未分类",
    "collect": false,
    "courseId": 13,
    "desc": "AssemblyAdapter 是 Android 上的一个为各种 Adapter 提供开箱即用实现的库。支持 Item 复用、多类型、Paging 分页、ViewPager2、RecyclerView Divider、以及 BaseAdapter 的 Concat 支持",
    "descMd": "",
    "envelopePic": "https://www.wanandroid.com/resources/image/pc/default_project_img.jpg",
    "fresh": false,
    "host": "",
    "id": 20274,
    "link": "https://www.wanandroid.com/blog/show/3089",
    "niceDate": "2021-10-24 22:50",
    "niceShareDate": "2021-10-24 22:50",
    "origin": "",
    "prefix": "",
    "projectLink": "https://github.com/panpf/assembly-adapter",
    "publishTime": 1635087017000,
    "realSuperChapterId": 293,
    "selfVisible": 0,
    "shareDate": 1635087017000,
    "shareUser": "",
    "superChapterId": 294,
    "superChapterName": "开源项目主Tab",
    "tags": [
    {
        "name": "项目",
        "url": "/project/list/1?cid=539"
    }
    ],
    "title": "组合式多类型 Adapter",
    "type": 0,
    "userId": -1,
    "visible": 1,
    "zan": 0
*/
    val apkLink: String?= null,
    val audit: Int = 0,
    val author: String,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val descMd: String,
    val envelopePic: String,
    val fresh: Boolean,
    val host: String,
    val id: Int,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val realSuperChapterId: Int,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
    val title: String,
    val tags: List<Tag>,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
) {
    data class Tag(
        val name: String,
        val url: String
    )
}