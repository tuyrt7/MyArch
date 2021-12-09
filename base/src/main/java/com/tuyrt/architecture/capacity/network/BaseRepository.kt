package com.tuyrt.architecture.capacity.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：
 */
abstract class BaseRepository {

    protected suspend fun <T> water(
        block: suspend () -> BaseResponse<T>
    ): Flow<BaseResponse<T>> = flow {
        val result = block.invoke()
        emit(
            if (result.isSuccess()) {
                checkEmptyResponse(result.getResData())
            } else {
                handleServerExceptions(result.getResCode() ?: -1, result.getResMsg() ?: "服务器出现了错误")
                FailureResponse(handleException(RequestException(result)))
            }
        )
    }.onStart {
        emit(StartResponse())
    }.onCompletion {
        emit(CompletionResponse())
    }.catch { throwable: Throwable ->
        emit(FailureResponse(handleException(throwable)))
    }.flowOn(Dispatchers.IO)

    protected suspend fun <T> fire(
        block: suspend () -> BaseResponse<T>
    ): BaseResponse<T> = withContext(Dispatchers.IO) {
        var response: BaseResponse<T> = EmptyResponse()
        kotlin.runCatching {
            block.invoke()
        }.onSuccess {
            response = if (it.isSuccess()) {
                checkEmptyResponse(it.getResData())
            } else {
                handleServerExceptions(it.getResCode() ?: -1, it.getResMsg() ?: "服务器出现了错误")
                FailureResponse(handleException(RequestException(it)))
            }
        }.onFailure { throwable ->
            response = FailureResponse(handleException(throwable))
        }
        response
    }

    open fun handleServerExceptions(errorCode: Int, errorMsg: String) {
        // 处理后台返回特殊错误码，比如token过期...
    }

    /**
     * data 为 null，或者 data 是集合类型，但是集合为空都会进入 onEmpty 回调
     */
    private fun <T> checkEmptyResponse(data: T?): ApiResponse<T> =
        if (data == null || (data is List<*> && (data as List<*>).isEmpty())) {
            EmptyResponse()
        } else {
            SuccessResponse(data)
        }
}