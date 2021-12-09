package com.tuyrt.architecture.capacity.network

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：接口数据结构
 */
open class ApiResponse<T>(
    open val data: T? = null,
    val errorCode: Int? = null,
    val errorMsg: String? = null,
    open val exception: RequestException? = null,
) : BaseResponse<T> {

    override fun isSuccess() = errorCode == 0

    override fun getResData(): T? = data

    override fun getResCode(): Int? = errorCode

    override fun getResMsg(): String? = errorMsg
}

class StartResponse<T> : ApiResponse<T>()

class CompletionResponse<T> : ApiResponse<T>()

data class SuccessResponse<T>(override val data: T) : ApiResponse<T>(data)

class EmptyResponse<T> : ApiResponse<T>()

data class FailureResponse<T>(override val exception: RequestException) : ApiResponse<T>(exception = exception)
