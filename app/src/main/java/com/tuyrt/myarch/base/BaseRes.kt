package com.tuyrt.myarch.base

import com.tuyrt.architecture.capacity.network.BaseResponse

/**
 * Created by tuyrt7 on 2021/12/6.
 * 说明： 自定义后台数据结构
 */
data class BaseRes<T>(
    val data: T?,
    val code: Int,
    val msg: String?
) : BaseResponse<T> {

    override fun isSuccess() = code == 0

    override fun getResData(): T? = data

    override fun getResCode(): Int? = code

    override fun getResMsg(): String? = msg
}
