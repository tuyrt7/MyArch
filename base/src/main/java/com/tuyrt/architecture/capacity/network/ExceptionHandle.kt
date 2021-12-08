package com.tuyrt.architecture.capacity.network

import com.google.gson.JsonParseException
import retrofit2.HttpException
import java.net.UnknownHostException

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：处理返回的异常
 */
fun handleException(throwable: Throwable) = when (throwable) {
    is UnknownHostException -> RequestException(HttpError.NETWORK_ERROR, throwable.message)
    is HttpException -> RequestException(throwable.code(), throwable.message, throwable.message)
    is JsonParseException -> RequestException(HttpError.JSON_PARSE_ERROR, throwable.message)
    is RequestException -> throwable
    else -> RequestException(HttpError.UNKNOWN, throwable.message)
}