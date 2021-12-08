package com.tuyrt.architecture.capacity.network

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：接口数据结构
 */
open class ApiResponse<T>(
    override val data: T? = null,
    override val errorCode: Int? = null,
    override val errorMsg: String? = null,
    open val exception: RequestException? = null,
) : BaseResponse<T>() {

    override val success: Boolean
        get() = errorCode == 0
}

class StartResponse<T> : ApiResponse<T>()

class CompletionResponse<T> : ApiResponse<T>()

data class SuccessResponse<T>(override val data: T) : ApiResponse<T>(data)

class EmptyResponse<T> : ApiResponse<T>()

data class FailureResponse<T>(override val exception: RequestException) :
    ApiResponse<T>(exception = exception)
