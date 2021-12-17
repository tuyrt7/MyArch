package com.tuyrt.myarch.logic.data.http

import com.tuyrt.myarch.logic.network.RetrofitClient
import com.tuyrt.myarch.logic.network.api.ApiService

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：
 */
object MyNetwork {

    private val apiService = RetrofitClient.getService(ApiService::class.java, ApiService.BASE_URL)

    suspend fun login(pwd: String) = apiService.login(password = pwd)

    suspend fun getBannerData() = apiService.getBanner()

    suspend fun getHomeListData(page: Int) = apiService.getHomeList(page)
}