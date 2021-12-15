package com.tuyrt.architecture.capacity.network

import com.tuyrt.architecture.capacity.log.KLog
import com.tuyrt.architecture.capacity.network.data.*
import com.tuyrt.architecture.capacity.network.error.RequestException
import com.tuyrt.architecture.capacity.network.error.handleException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：
 */
abstract class BaseRepository {

    /**
     *  普通协程请求
     */
    protected suspend fun <T> fire(
        block: suspend () -> BaseResponse<T>
    ): BaseResponse<T> = withContext(Dispatchers.IO) {
        var response: BaseResponse<T> = EmptyResponse()
        kotlin.runCatching {
            block.invoke()
        }.onSuccess { data: BaseResponse<T> ->
            response = handleHttpOk(data)
        }.onFailure { throwable ->
            response = handleHttpError(throwable)
        }
        response
    }

    /**
     *  Flow 请求
     */
    protected suspend fun <T> water(
        block: suspend () -> BaseResponse<T>
    ): Flow<BaseResponse<T>> = flow {
        emit(
            handleHttpOk(block.invoke())
        )
    }.onStart {
        emit(StartResponse())
    }.onCompletion {
        emit(CompletionResponse())
    }.catch { throwable: Throwable ->
        emit(FailureResponse(handleException(throwable)))
    }.flowOn(Dispatchers.IO)

    private fun <T> handleHttpError(throwable: Throwable): BaseResponse<T> {
        return FailureResponse(handleException(throwable))
    }

    /**
     * 返回200，但是还要判断isSuccess
     */
    private fun <T> handleHttpOk(data: BaseResponse<T>): BaseResponse<T> {
        return if (data.isSuccess()) {
            getHttpSuccessResponse(data.getResData())
        } else {
            handleServerExceptions(data.getResCode() ?: -1, data.getResMsg() ?: "服务器出现了错误")
            FailureResponse(handleException(RequestException(data)))
        }
    }

    /**
     * 成功和数据为空的处理
     */
    private fun <T> getHttpSuccessResponse(data: T?): ApiResponse<T> {
        return if (data == null || (data is List<*> && (data as List<*>).isEmpty())) {
            EmptyResponse()
        } else {
            SuccessResponse(data)
        }
    }

    /**
     *  处理服务器 API Error
     */
    open fun handleServerExceptions(errorCode: Int, errorMsg: String) {
        // 处理后台返回特殊错误码，比如token过期...
    }

}