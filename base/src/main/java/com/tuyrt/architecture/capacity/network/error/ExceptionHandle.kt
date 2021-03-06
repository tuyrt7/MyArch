package com.tuyrt.architecture.capacity.network.error

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.tuyrt.architecture.capacity.network.error.HttpError.*
import retrofit2.HttpException
import java.net.UnknownHostException

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：处理返回的异常
 */
fun handleException(throwable: Throwable) = when (throwable) {
    is UnknownHostException -> RequestException(NETWORK_ERROR, throwable.message)
    is HttpException -> {
        // RequestException(throwable.code(), throwable.message, throwable.message)
        val errorModel = throwable.response()?.errorBody()?.string()?.run {
            Gson().fromJson(this, ErrorBodyModel::class.java)
        } ?: ErrorBodyModel()
        RequestException(errorMsg = errorModel.message, error = errorModel.error)
    }
    is JsonParseException -> RequestException(JSON_PARSE_ERROR, throwable.message)
    is RequestException -> throwable
    else -> RequestException(UNKNOWN, throwable.message)
}