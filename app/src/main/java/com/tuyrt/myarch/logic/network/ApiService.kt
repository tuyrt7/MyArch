package com.tuyrt.myarch.logic.network

import com.tuyrt.architecture.capacity.network.ApiResponse
import com.tuyrt.myarch.logic.model.LoginModel
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：
 */
interface ApiService {

    companion object {
        const val BASE_URL = "https://wanandroid.com/"
    }

    @POST("user/login")
    suspend fun login(
        @Query("username") userName: String = "requestpractice",
        @Query("password") password: String
    ): ApiResponse<LoginModel>


}