package com.tuyrt.myarch.base

import com.tuyrt.architecture.capacity.network.ApiResponse

/**
 * Created by tuyrt7 on 2021/12/6.
 * 说明： 自定义后台数据结构
 */
data class BaseRes<T>(
    override val data: T?,
    val code: Int,
    val msg: String?
) : ApiResponse<T>(data, errorCode = code, errorMsg = msg)
