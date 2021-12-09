package com.tuyrt.architecture.capacity.network

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：响应数据结构
 */
interface BaseResponse<T> {

    fun isSuccess(): Boolean

    fun getResData(): T?

    fun getResCode(): Int?

    fun getResMsg(): String?
}

