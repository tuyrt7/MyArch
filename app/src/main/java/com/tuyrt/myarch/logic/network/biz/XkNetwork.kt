package com.tuyrt.myarch.logic.network.biz

import com.tuyrt.myarch.logic.network.RetrofitClient

/**
 * Created by tuyrt7 on 2021/12/9.
 * 说明：
 */
object XkNetwork {

    private val apiService = RetrofitClient.xkService

    suspend fun getKey(code:String) = apiService.getKey(code)
}