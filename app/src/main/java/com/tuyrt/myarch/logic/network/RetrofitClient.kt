package com.tuyrt.myarch.logic.network

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.tuyrt.architecture.capacity.network.BaseRetrofitClient
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：
 */
object RetrofitClient : BaseRetrofitClient() {

    private const val LOG_TAG_HTTP_REQUEST = "okhttp_request"
    private const val LOG_TAG_HTTP_RESULT = "okhttp_result"

    val service by lazy { getService(ApiService::class.java, ApiService.BASE_URL) }

    override fun handleBuilder(builder: OkHttpClient.Builder) = Unit

    override fun initLoggingInterceptor() = LoggingInterceptor
        .Builder()
        .setLevel(Level.BASIC)
        .log(Platform.INFO)
        .request(LOG_TAG_HTTP_REQUEST)
        .response(LOG_TAG_HTTP_RESULT)
        .build()

}