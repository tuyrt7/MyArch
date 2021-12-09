package com.tuyrt.architecture.capacity.network

/**
 * Created by tuyrt7 on 2021/12/9.
 * 说明：Retrofit请求码为非200时的ErrorBody
 */
data class ErrorBodyModel(
    val error: String? = "",
    val message: String? = "",
    val success: Boolean? = false
)