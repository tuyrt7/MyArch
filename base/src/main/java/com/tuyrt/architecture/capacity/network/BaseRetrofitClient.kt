package com.tuyrt.architecture.capacity.network

import androidx.viewbinding.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明： Retrofit 构建基类
 */
abstract class BaseRetrofitClient {

    companion object CLIENT {
        private const val TIME_OUT = 5
    }

    private val client: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            //.addInterceptor(getHttpLoggingInterceptor())
        initLoggingInterceptor()?.also {
            builder.addInterceptor(it)
        }
        handleBuilder(builder)
        builder.build()
    }

    abstract fun handleBuilder(builder: OkHttpClient.Builder)

    /**
     * 配置日志拦截器
     */
    abstract fun initLoggingInterceptor(): Interceptor?

    // okhttp-logger
    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.BASIC
        }
        return logging
    }

    open fun <Service> getService(serviceClass: Class<Service>, baseUrl: String): Service {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(serviceClass)
    }
}