package com.tuyrt.myarch.logic.data.entity


data class LoginModel(
    val admin: Boolean?,
    val chapterTops: List<Any>?,
    val email: String?,
    val icon: String?,
    val id: Int?,
    val nickname: String?,
    val publicName: String?,
    val username: String?
)