package com.tuyrt.myarch.logic.network.biz

import com.tuyrt.myarch.logic.network.RetrofitClient
import com.tuyrt.myarch.logic.network.XkApi

/**
 * Created by tuyrt7 on 2021/12/9.
 * 说明：
 */
object XkNetwork {

    private val apiService = RetrofitClient.getService(XkApi::class.java, XkApi.BASE_URL)

    suspend fun getKey(code:String) = apiService.getKey(code)
}