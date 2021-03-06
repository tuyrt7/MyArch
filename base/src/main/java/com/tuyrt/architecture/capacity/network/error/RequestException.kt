package com.tuyrt.architecture.capacity.network.error

import com.tuyrt.architecture.capacity.network.response.BaseResponse
import java.lang.Exception

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：
 */
data class RequestException(
    val code: Int? = 0,
    val errorMsg: String? = "",
    val error: String? = null
) : Exception() {

    constructor(response: BaseResponse<*>) : this(
        response.getResCode(),
        response.getResMsg(),
        response.getResMsg()
    )

    constructor(httpError: HttpError, error: String?) : this(
        httpError.code,
        httpError.message,
        error
    )
}
