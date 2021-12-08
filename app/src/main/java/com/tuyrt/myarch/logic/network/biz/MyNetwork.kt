package com.tuyrt.myarch.logic.network.biz

import com.tuyrt.myarch.logic.network.ApiService
import com.tuyrt.myarch.logic.network.RetrofitClient

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：
 */
object MyNetwork {

    private val apiService = RetrofitClient.service

    suspend fun login(pwd:String) = apiService.login(password = pwd)
}