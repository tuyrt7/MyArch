package com.tuyrt.myarch.logic.network.api

import com.tuyrt.architecture.capacity.network.response.ApiResponse
import com.tuyrt.myarch.logic.data.entity.BannerBean
import com.tuyrt.myarch.logic.data.entity.HomeListBean
import com.tuyrt.myarch.logic.data.entity.LoginModel
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
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

    /**
     * 玩安卓轮播图
     */
    @GET("banner/json")
    suspend fun getBanner(): ApiResponse<List<BannerBean>>


    /**
     * 项目列表
     * @param page 页码，从0开始
     */
    @GET("article/listproject/{page}/json")
    suspend fun getHomeList(@Path("page") page: Int): ApiResponse<HomeListBean>
}