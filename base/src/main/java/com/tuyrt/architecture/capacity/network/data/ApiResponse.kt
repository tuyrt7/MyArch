package com.tuyrt.architecture.capacity.network.data

import com.tuyrt.architecture.capacity.network.error.RequestException

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：临时解析接口 数据结构
 * 注意：如果状态码改变，需要对应修改 errorCode
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

/**
 *  配置errorCode=0，是为了cacheNetCall之后再次经过 协程和Flow 也能再次按照既定条件走下去
 */
data class SuccessResponse<T>(override val data: T) : ApiResponse<T>(data, errorCode = 0)

class EmptyResponse<T> : ApiResponse<T>()

data class FailureResponse<T>(val exception: RequestException) : ApiResponse<T>(exception = exception)
