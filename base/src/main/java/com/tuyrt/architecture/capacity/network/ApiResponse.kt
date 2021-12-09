package com.tuyrt.architecture.capacity.network

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：临时解析接口 数据结构
 */
open class ApiResponse<T>(
    open val data: T? = null,
    private val errorCode: Int? = null,
    private val errorMsg: String? = null
) : BaseResponse<T> {

    constructor(exception: RequestException) : this(null, exception.code, exception.errorMsg)

    override fun isSuccess() = errorCode == 0

    override fun getResData(): T? = data

    override fun getResCode(): Int? = errorCode

    override fun getResMsg(): String? = errorMsg
}

class StartResponse<T> : ApiResponse<T>()

class CompletionResponse<T> : ApiResponse<T>()

data class SuccessResponse<T>(override val data: T) : ApiResponse<T>(data)

class EmptyResponse<T> : ApiResponse<T>()

data class FailureResponse<T>(val exception: RequestException) : ApiResponse<T>(exception = exception)
